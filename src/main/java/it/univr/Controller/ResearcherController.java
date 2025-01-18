package it.univr.Controller;

import it.univr.Model.Project;
import it.univr.Model.User.Researcher;
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
import java.util.ArrayList;
import java.util.List;

@Controller
public class ResearcherController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private WorkingTimeRepository wtRepository;
    @Autowired
    private TimeTrackingController ttController;

    @RequestMapping("/researcher")
    public String researcher(HttpServletRequest request, HttpServletResponse response, Model model, @RequestParam(name="date", required = false) LocalDate date) {
        if(ttController.isNotValidUrl("researcher",request)){
            return "redirect:/";
        }
        response.setHeader("Cache-Control","no-store");
        Cookie cookie = ttController.getCookieByName(request,"userLoggedIn");

        assert cookie != null;
        Researcher researcher = (Researcher)userRepository.findByUsername(cookie.getValue());

        if(date==null){
            date = LocalDate.now();
        }

        for(Project project : projectRepository.findAllByResearchersContains(researcher)){
            if (wtRepository.getWorkingTimeByProjectAndUtenteAndDate(project,researcher,date)==null){
                wtRepository.save(new WorkingTime(researcher,project,date,0,false,false));
            }
        }

        model.addAttribute("hours", wtRepository.findByDateAndUtente(date,researcher));
        model.addAttribute("username", cookie.getValue());

        if(wtRepository.getTopByUtenteAndDate(researcher,date)==null){ //Controllo che serve per gli utenti che non hanno nessun progetto assegnato
            model.addAttribute("status",false);
        } else {
            model.addAttribute("status", wtRepository.getTopByUtenteAndDate(researcher, date).getLeave());
        }

        model.addAttribute("selectedDate", date);
        model.addAttribute("isHoliday",ttController.isHoliday(date));
        return "researcher";
    }

    @RequestMapping("/saveWorkingTime")
    public String saveWorkingTime(HttpServletRequest request,
                                  HttpServletResponse response,
                                  @RequestParam(name="hours", required=false) List<Double> hours,
                                  @RequestParam(name="checkbox", required=false) String checkbox,
                                  @RequestParam(name="selectedDate", required=false) LocalDate date) {
        response.setHeader("Cache-Control","no-store");
        String userType;
        if(!ttController.isNotValidUrl("researcher",request)){
            userType = "researcher";
        }
        else if(!ttController.isNotValidUrl("supervisor",request)){
            userType = "supervisor";
        }
        else {
            return "redirect:/";
        }

        Cookie cookie = ttController.getCookieByName(request,"userLoggedIn");

        if(date==null){
            date = LocalDate.now();
        }

        assert cookie != null;
        Utente utente = userRepository.findByUsername(cookie.getValue());
        Iterable<Project> listaProject = wtRepository.findProjectsByDateAndResearcher(date,utente);

        if(checkbox==null){
            int counter = 0;
            for(Project pj : listaProject){
                WorkingTime wt = wtRepository.getWorkingTimeByProjectAndUtenteAndDate(pj,utente,date);
                wt.setWorkedHours(hours.get(counter));
                wt.setLeave(false);
                wtRepository.save(wt);
                counter++;
            }
        } else {
            for(Project pj : listaProject){ //settings all working time as 0 hours
                WorkingTime wt = wtRepository.getWorkingTimeByProjectAndUtenteAndDate(pj,utente,date);
                wtRepository.deleteById(wt.getId());
                wtRepository.save(new WorkingTime(utente,pj,date,0,false,true));
            }
        }

        return "redirect:/"+userType+"?date=" + date;
    }

    @RequestMapping("/downloadTimesheet")
    public String downloadTimesheet(HttpServletRequest request, HttpServletResponse response, Model model, @RequestParam(name="id") long id) {
        if(ttController.isNotValidUrl("researcher",request) && ttController.isNotValidUrl("supervisor",request)){
            return "redirect:/";
        }
        response.setHeader("Cache-Control","no-store");
        Cookie cookie = ttController.getCookieByName(request,"userLoggedIn");
        Project project = projectRepository.findById(id);

        assert cookie != null;
        Utente utente = userRepository.findByUsername(cookie.getValue()); //Siamo in un punto dove abbiamo per forza un researcher

        model.addAttribute("project", project);
        model.addAttribute("validatedMonths",getWorkedMonth(project, utente));
        model.addAttribute("utente", utente);
        return "downloadTimesheet";
    }

    private ArrayList<String> getWorkedMonth(Project project, Utente utente){
        Iterable<WorkingTime> wts = wtRepository.findWorkingTimesByValidatedTrueAndUtenteAndProject(utente, project);
        ArrayList<String> monthYearList = new ArrayList<>();
        for(WorkingTime wt : wts){
            String monthYear = wt.getMonthYear();
            if (!monthYearList.contains(monthYear)){
                monthYearList.add(monthYear);
            }
        }
        return monthYearList;
    }
}