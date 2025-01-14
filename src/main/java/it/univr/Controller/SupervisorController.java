package it.univr.Controller;

import it.univr.Model.Project;
import it.univr.Model.User.Researcher;
import it.univr.Model.User.Supervisor;
import it.univr.Model.User.Utente;
import it.univr.Model.WorkingTime;
import it.univr.Repository.ProjectRepository;
import it.univr.Repository.UserRepository;
import it.univr.Repository.WorkingTimeRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class SupervisorController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private WorkingTimeRepository wtRepository;
    @Autowired
    private TimeTrackingController ttController;

    @RequestMapping("/supervisor")
    public String supervisor(HttpServletRequest request, HttpServletResponse response, Model model, @RequestParam(name="date", required = false) LocalDate date) {
        if(ttController.isNotValidUrl("supervisor",request)){
            return "redirect:/";
        }
        response.setHeader("Cache-Control","no-store");
        Cookie cookie = ttController.getCookieByName(request,"userLoggedIn");

        assert cookie != null;
        Supervisor supervisor = (Supervisor) userRepository.findByUsername(cookie.getValue());

        if(date==null){
            date = LocalDate.now();
        }

        for(Project project : projectRepository.findAllBySupervisor(supervisor)){
            if (wtRepository.getWorkingTimeByProjectAndUtenteAndDate(project,supervisor,date)==null){
                wtRepository.save(new WorkingTime(supervisor,project,date,0 ,false,false));
            }
        }


        model.addAttribute("hours", wtRepository.findByDateAndUtente(date,supervisor));
        model.addAttribute("username", cookie.getValue());
        model.addAttribute("status", wtRepository.getTopByUtenteAndDate(supervisor,date).getLeave());
        model.addAttribute("selectedDate", date);
        model.addAttribute("isHoliday",ttController.isHoliday(date));

        return "supervisor";
    }

    @RequestMapping("/supervisorAction")
    public String supervisorAction(HttpServletRequest request, HttpServletResponse response, Model model) {
        if(ttController.isNotValidUrl("supervisor",request)){
            return "redirect:/";
        }
        response.setHeader("Cache-Control","no-store");
        Cookie cookie = ttController.getCookieByName(request,"userLoggedIn");
        assert cookie != null;
        model.addAttribute("projects", projectRepository.findAllBySupervisor((Supervisor)userRepository.findByUsername(cookie.getValue())));
        model.addAttribute("username", cookie.getValue());

        return "supervisorAction";
    }


    @RequestMapping("/saveProjectResearcher")
    public String saveProjectResearcher(HttpServletRequest request,
                                        HttpServletResponse response,
                                        @RequestParam(name="idResearchers") List<Long> ids,
                                        @RequestParam(name="projectId") Long projectId) {
        if(ttController.isNotValidUrl("supervisor",request)){
            return "redirect:/";
        }
        response.setHeader("Cache-Control","no-store");
        Project project = projectRepository.findById(projectId).orElse(null);

        assert project != null;
        List<Long> currentResearchersProjectIds = project.getResearchers().stream().map(Researcher::getId).toList();
        Set<Long> unionList = new HashSet<>(currentResearchersProjectIds);
        unionList.addAll(ids);

        List<Researcher> newResearchers = new ArrayList<>();

        for(Long id : unionList){
            Utente current = userRepository.findById(id).orElse(null);
            if (currentResearchersProjectIds.contains(id) && !ids.contains(id)) {
                Iterable<WorkingTime> wts = wtRepository.findAllByUtenteAndProject(current,project);
                wtRepository.deleteAll(wts);
            }
            else{
                newResearchers.add((Researcher) current);
            }
        }

        project.setResearchers(newResearchers);
        projectRepository.save(project);

        return "redirect:/superviseProject?id="+projectId;
    }

    @RequestMapping("/validationTimesheet")
    public String validationTimesheet(HttpServletRequest request, HttpServletResponse response, Model model, @RequestParam(name="id") long id) {
        if(ttController.isNotValidUrl("supervisor",request)){
            return "redirect:/";
        }
        response.setHeader("Cache-Control","no-store");
        Cookie cookie = ttController.getCookieByName(request,"userLoggedIn");

        assert cookie != null;
        Supervisor supervisor = (Supervisor) userRepository.findByUsername(cookie.getValue());
        Project project = projectRepository.findById(id);
        Iterable<Researcher> researchers = projectRepository.findResearchersByProjectId(id);

        ArrayList<WorkingTime> wts = new ArrayList<>();
        for(Researcher researcher : researchers){
            wts.addAll(getWorkedMonthWt(project,researcher));
        }
        wts.addAll(getWorkedMonthWt(project,supervisor));

        model.addAttribute("workedMonthYear",wts);
        model.addAttribute("researchers",researchers);
        model.addAttribute("username", cookie.getValue());

        return "validationTimesheet";
    }

    @RequestMapping("/approveTimesheet")
    public String approveTimesheet(HttpServletRequest request, HttpServletResponse response, @RequestParam(name="id") long id) {
        if(ttController.isNotValidUrl("supervisor",request)){
            return "redirect:/";
        }
        response.setHeader("Cache-Control","no-store");
        WorkingTime wt = wtRepository.findById(id);
        Utente utente = wt.getUtente();
        Project project = wt.getProject();
        LocalDate date = wt.getDate();

        List<Integer> datePresenti = new ArrayList<>();

        for (WorkingTime everyWt: wtRepository.findAllByDateBetweenAndProjectAndUtente(LocalDate.of(date.getYear(),date.getMonth(),1),LocalDate.of(date.getYear(),date.getMonth(), YearMonth.of(date.getYear(),date.getMonth()).lengthOfMonth()),project,utente)){
            everyWt.setValidated(true);
            datePresenti.add(everyWt.getDate().getDayOfMonth());
            wtRepository.save(everyWt);
        }

        for(int i=1; i<=YearMonth.of(date.getYear(),date.getMonth()).lengthOfMonth(); i++){
            if(!datePresenti.contains(i)){
                wtRepository.save(new WorkingTime(utente, project, LocalDate.of(date.getYear(), date.getMonth(), i), 0, true, false));
            }
        }

        return "redirect:/validationTimesheet?id="+wt.getProject().getId();
    }

    @RequestMapping("/superviseProject")
    public String superviseProject(HttpServletRequest request, HttpServletResponse response, Model model, @RequestParam(name="id") long id) {
        if(ttController.isNotValidUrl("supervisor",request)){
            return "redirect:/";
        }
        response.setHeader("Cache-Control","no-store");
        Cookie cookie = ttController.getCookieByName(request,"userLoggedIn");

        ArrayList<Researcher> listaRicercatori = new ArrayList<>();
        for (Utente utente : userRepository.findAll()){
            if(utente instanceof  Researcher){
                listaRicercatori.add((Researcher) utente);
            }
        }

        ArrayList<Long> listaIdRicercatori = new ArrayList<>();
        for (Utente utente : projectRepository.findById(id).getResearchers()){
            listaIdRicercatori.add(utente.getId());
        }

        model.addAttribute("selectedResearcherIds",listaIdRicercatori);
        model.addAttribute("allResearcher",listaRicercatori);
        model.addAttribute("project",projectRepository.findById(id));
        assert cookie != null;
        model.addAttribute("username", cookie.getValue());
        return "superviseProject";
    }

    private ArrayList<WorkingTime> getWorkedMonthWt(Project project, Utente utente){
        Iterable<WorkingTime> wts = wtRepository.findWorkingTimesByUtenteAndProject(utente, project);
        ArrayList<WorkingTime> listWts = new ArrayList<>();

        ArrayList<String> monthYearList = new ArrayList<>();

        for(WorkingTime wt : wts){
            String monthYear = wt.getMonthYear();
            if (!monthYearList.contains(monthYear)){
                monthYearList.add(monthYear);
                listWts.add(wt);
            }
        }
        return listWts;
    }
}
