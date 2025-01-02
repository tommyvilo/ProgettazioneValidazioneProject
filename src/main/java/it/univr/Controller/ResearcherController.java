package it.univr.Controller;

import it.univr.Model.Project;
import it.univr.Model.User.Researcher;
import it.univr.Model.User.Supervisor;
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

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
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

    @RequestMapping("/researcher")
    public String researcher(HttpServletRequest request, Model model) {
        if(!isValidUrl(request)){
            return "redirect:/";
        }
        Cookie cookie = getCookieByName(request,"userLoggedIn");
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
        model.addAttribute("isHoliday",isHoliday(date));
        return "researcher";
    }

    @RequestMapping("/saveWorkingTime")
    public String saveWorkingTime(HttpServletRequest request,
                                  @RequestParam(name="hours", required=false) List<Double> hours,
                                  @RequestParam(name="checkbox", required=false) String checkbox) {
        if(!isValidUrl(request)){
            return "redirect:/";
        }
        Cookie cookie = getCookieByName(request,"userLoggedIn");
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
        if(!isValidUrl(request)){
            return "redirect:/";
        }
        Cookie cookie = getCookieByName(request,"userLoggedIn");
        Project project = projectRepository.findById(id);
        Researcher researcher = (Researcher)userRepository.findByUsername(cookie.getValue()); //Siamo in un punto dove abbiamo per forza un researcher

        model.addAttribute("project", project);
        model.addAttribute("validatedMonths",getWorkedMonth(project, researcher));
        model.addAttribute("username", cookie.getValue());
        return "downloadTimesheet";
    }

    private boolean isHoliday(LocalDate date) {
        int year = date.getYear();

        // Festività fisse
        List<LocalDate> fixedHolidays = List.of(
                LocalDate.of(year, Month.JANUARY, 1),     // Capodanno
                LocalDate.of(year, Month.JANUARY, 6),     // Epifania
                LocalDate.of(year, Month.APRIL, 25),      // Festa della Liberazione
                LocalDate.of(year, Month.MAY, 1),         // Festa dei lavoratori
                LocalDate.of(year, Month.JUNE, 2),        // Festa della Repubblica
                LocalDate.of(year, Month.AUGUST, 15),     // Ferragosto
                LocalDate.of(year, Month.NOVEMBER, 1),    // Tutti i Santi
                LocalDate.of(year, Month.DECEMBER, 8),    // Immacolata Concezione
                LocalDate.of(year, Month.DECEMBER, 25),  // Natale
                LocalDate.of(year, Month.DECEMBER, 26)    // Santo Stefano
        );

        // Controllo festività fisse
        if (fixedHolidays.contains(date)) {
            return true;
        }

        // Controllo Pasqua e Pasquetta (variabili)
        LocalDate easterSunday = getEasterSunday(year);
        LocalDate easterMonday = easterSunday.plusDays(1);
        if (date.equals(easterSunday) || date.equals(easterMonday)) {
            return true;
        }

        // Controllo fine settimana
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) {
            return true;
        }

        return false;
    }

    private LocalDate getEasterSunday(int year) {
        int a = year % 19;
        int b = year / 100;
        int c = year % 100;
        int d = b / 4;
        int e = b % 4;
        int f = (b + 8) / 25;
        int g = (b - f + 1) / 3;
        int h = (19 * a + b - d - g + 15) % 30;
        int i = c / 4;
        int k = c % 4;
        int l = (32 + 2 * e + 2 * i - h - k) % 7;
        int m = (a + 11 * h + 22 * l) / 451;
        int month = (h + l - 7 * m + 114) / 31;
        int day = ((h + l - 7 * m + 114) % 31) + 1;
        return LocalDate.of(year, month, day);
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

    private boolean isValidUrl(HttpServletRequest request){
        String url = "researcher";
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
        Cookie cookie = getCookieByName(request,"userLoggedIn");
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
}