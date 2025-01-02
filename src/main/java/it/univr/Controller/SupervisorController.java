package it.univr.Controller;

import it.univr.Model.Project;
import it.univr.Model.User.Researcher;
import it.univr.Model.User.Supervisor;
import it.univr.Model.User.Utente;
import it.univr.Model.WorkingTime;
import it.univr.ProjectRepository;
import it.univr.UserRepository;
import it.univr.WorkingTimeRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

@Controller
public class SupervisorController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private WorkingTimeRepository wtRepository;

    @RequestMapping("/supervisor")
    public String supervisor(HttpServletRequest request, Model model) {
        if(!isValidUrl(request)){
            return "redirect:/";
        }
        Cookie cookie = getCookieByName(request,"userLoggedIn");
        model.addAttribute("projects", projectRepository.findAllBySupervisor((Supervisor)userRepository.findByUsername(cookie.getValue())));
        model.addAttribute("username", cookie.getValue());
        return "supervisor";
    }

    @RequestMapping("/saveProjectResearcher")
    public String saveProjectResearcher(HttpServletRequest request,
                                        @RequestParam(name="idResearchers") List<Long> ids,
                                        @RequestParam(name="projectId") Long projectId) {
        if(!isValidUrl(request)){
            return "redirect:/";
        }
        Project project = projectRepository.findById(projectId).orElse(null);

        List<Long> currentResearchersProjectIds = project.getResearchers().stream().map(Researcher::getId).toList();
        List<Researcher> newResearchers = new ArrayList<>();

        for(Long id : ids){
            Researcher current = (Researcher) userRepository.findById(id).orElse(null);
            if (currentResearchersProjectIds.contains(id) && !ids.contains(id)) {
                wtRepository.deleteByProjectAndResearcher(project,current);
            }
            else{
                newResearchers.add(current);
            }
        }

        project.setResearchers(newResearchers);
        projectRepository.save(project);

        return "redirect:/projectManagement?id="+projectId;
    }

    @RequestMapping("/validationTimesheet")
    public String validationTimesheet(HttpServletRequest request, Model model, @RequestParam(name="id") long id) {
        if(!isValidUrl(request)){
            return "redirect:/";
        }
        Cookie cookie = getCookieByName(request,"userLoggedIn");
        Project project = projectRepository.findById(id);
        Iterable<Researcher> researchers = projectRepository.findResearchersByProjectId(id);

        ArrayList<WorkingTime> wts = new ArrayList<>();
        for(Researcher researcher : researchers){
            wts.addAll(getWorkedMonthWt(project,researcher));
        }

        model.addAttribute("workedMonthYear",wts);
        model.addAttribute("researchers",researchers);
        model.addAttribute("username", cookie.getValue());
        return "validationTimesheet";
    }

    @RequestMapping("/approveTimesheet")
    public String approveTimesheet(@RequestParam(name="id") long id) {
        WorkingTime wt = wtRepository.findById(id);
        Researcher researcher = wt.getResearcher();
        Project project = wt.getProject();
        LocalDate date = wt.getDate();

        for (WorkingTime everyWt: wtRepository.findAllByDateBetweenAndProjectAndResearcher(LocalDate.of(date.getYear(),date.getMonth(),1),LocalDate.of(date.getYear(),date.getMonth(), YearMonth.of(date.getYear(),date.getMonth()).lengthOfMonth()),project,researcher)){
            everyWt.setValidated(true);
            wtRepository.save(everyWt);
        }
        return "redirect:/validationTimesheet?id="+wt.getProject().getId();
    }

    @RequestMapping("/projectManagement")
    public String projectManagement(HttpServletRequest request, Model model, @RequestParam(name="id") long id) {
        if(!isValidUrl(request)){
            return "redirect:/";
        }
        Cookie cookie = getCookieByName(request,"userLoggedIn");

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
        model.addAttribute("username", cookie.getValue());
        return "superviseProject";
    }

    private boolean isValidUrl(HttpServletRequest request){
        String url = "supervisor";
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("userLoggedIn".equals(cookie.getName())) {
                    return ("redirect:/"+url).equals(getIndexByUser(request));
                }
            }
        }
        return false;
    }

    private String getIndexByUser(HttpServletRequest request){
        Cookie cookie = getCookieByName(request, "userLoggedIn");
        if(userRepository.findByUsername(cookie.getValue()) instanceof Supervisor){
            return "redirect:/supervisor";
        }
        if(userRepository.findByUsername(cookie.getValue()) instanceof Researcher){
            return "redirect:/researcher";
        }
        return "redirect:/administrator";
    }

    private Cookie getCookieByName(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(cookieName)) {
                    return cookie;
                }
            }
        }
        return null;
    }

    private ArrayList<WorkingTime> getWorkedMonthWt(Project project, Researcher researcher){
        Iterable<WorkingTime> wts = wtRepository.findWorkingTimesByResearcherAndProject(researcher, project);
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
