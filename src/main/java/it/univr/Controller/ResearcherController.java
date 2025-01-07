package it.univr.Controller;

import it.univr.Model.Project;
import it.univr.Model.User.Researcher;
import it.univr.Model.WorkingTime;
import it.univr.Repository.ProjectRepository;
import it.univr.Repository.UserRepository;
import it.univr.Repository.WorkingTimeRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
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
    public String researcher(HttpServletRequest request, Model model) {
        if(ttController.isValidUrl("researcher",request)){
            return "redirect:/";
        }
        Cookie cookie = getCookieByName(request,"userLoggedIn");

        assert cookie != null;
        Researcher researcher = (Researcher)userRepository.findByUsername(cookie.getValue());
        LocalDate date = LocalDate.now();

        for(Project project : projectRepository.findAllByResearchersContains(researcher)){
            if (wtRepository.getWorkingTimeByProjectAndResearcherAndDate(project,researcher,date)==null){
                wtRepository.save(new WorkingTime(researcher,project,date,0,false,false));
            }
        }

        model.addAttribute("hours", wtRepository.findByDateAndResearcher(date,researcher));
        model.addAttribute("username", cookie.getValue());
        if(wtRepository.getTopByResearcherAndDate(researcher, date)==null){
            model.addAttribute("status",false);
        }else{
            model.addAttribute("status", wtRepository.getTopByResearcherAndDate(researcher,date).getLeave());
        }
        model.addAttribute("isHoliday",ttController.isHoliday(date));
        return "researcher";
    }

    @RequestMapping("/saveWorkingTime")
    public String saveWorkingTime(HttpServletRequest request,
                                  @RequestParam(name="hours", required=false) List<Double> hours,
                                  @RequestParam(name="checkbox", required=false) String checkbox) {
        if(ttController.isValidUrl("researcher",request)){
            return "redirect:/";
        }
        Cookie cookie = getCookieByName(request,"userLoggedIn");

        assert cookie != null;
        Researcher researcher = (Researcher)userRepository.findByUsername(cookie.getValue());
        Iterable<Project> listaProject = wtRepository.findProjectsByDateAndResearcher(LocalDate.now(),researcher);

        if(checkbox==null){
            int counter = 0;
            for(Project pj : listaProject){
                WorkingTime wt = wtRepository.getWorkingTimeByProjectAndResearcherAndDate(pj,researcher,LocalDate.now());
                wt.setWorkedHours(hours.get(counter));
                wt.setLeave(false);
                wtRepository.save(wt);
                counter++;
            }
        } else {
            for(Project pj : listaProject){ //settings all working time as 0 hours
                WorkingTime wt = wtRepository.getWorkingTimeByProjectAndResearcherAndDate(pj,researcher,LocalDate.now());
                wtRepository.deleteById(wt.getId());
                wtRepository.save(new WorkingTime(researcher,pj,LocalDate.now(),0,false,true));
            }
        }
        return "redirect:/researcher";
    }

    @RequestMapping("/downloadTimesheet")
    public String downloadTimesheet(HttpServletRequest request, Model model, @RequestParam(name="id") long id) {
        if(ttController.isValidUrl("researcher",request)){
            return "redirect:/";
        }
        Cookie cookie = getCookieByName(request,"userLoggedIn");
        Project project = projectRepository.findById(id);

        assert cookie != null;
        Researcher researcher = (Researcher)userRepository.findByUsername(cookie.getValue()); //Siamo in un punto dove abbiamo per forza un researcher

        model.addAttribute("project", project);
        model.addAttribute("validatedMonths",getWorkedMonth(project, researcher));
        model.addAttribute("username", cookie.getValue());
        return "downloadTimesheet";
    }

    private ArrayList<String> getWorkedMonth(Project project, Researcher researcher){
        Iterable<WorkingTime> wts = wtRepository.findWorkingTimesByValidatedTrueAndResearcherAndProject(researcher, project);
        ArrayList<String> monthYearList = new ArrayList<>();
        for(WorkingTime wt : wts){
            String monthYear = wt.getMonthYear();
            if (!monthYearList.contains(monthYear)){
                monthYearList.add(monthYear);
            }
        }
        return monthYearList;
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
}