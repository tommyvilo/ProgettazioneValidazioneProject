package it.univr;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.jdbc.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import it.univr.User.Administrator;
import it.univr.User.Researcher;
import it.univr.User.Supervisor;
import it.univr.User.Utente;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Controller
public class TimeTrackingController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private WorkingTimeRepository wtRepository;

    @PostConstruct
    public void init() {
        Researcher r1 = new Researcher("nicozerman","zermaculo","Nicolò","Zerman","ZRMNCL02S19L781E");
        Researcher r2 = new Researcher("lucasrin", "lucarino", "Luca", "Rinaldi", "RNLCLS01D13L781X");
        Researcher r3 = new Researcher("alessandrab1", "alessandrabb", "Alessandra", "Bianchi", "BNCHLD01E44F861Z");
        Researcher r4 = new Researcher("giovanniferrari", "gioferr", "Giovanni", "Ferrari", "FRRGNN03C19A481S");
        Researcher r5 = new Researcher("serenacapu", "serenacaputo", "Serena", "Caputo", "CPTSRN01B25L761Q");
        Researcher r6 = new Researcher("marcofiore", "marcofiore", "Marco", "Fiore", "FIRMRC01D22N484S");
        Researcher r7 = new Researcher("martina", "martabianchi", "Martina", "Bianchi", "BNCHTN02M55A922L");
        Researcher r8 = new Researcher("lorenzomassimo", "lorenzomassimo", "Lorenzo", "Massimo", "MSSLNZ03L19D919M");
        Researcher r9 = new Researcher("giuliasorriso", "giuliasorriso", "Giulia", "Sorriso", "SRRGIA01P63D876R");
        Researcher r10 = new Researcher("marcoantonio", "marcoantonio", "Marco", "Antonio", "MTNTMR02R15F381A");
        Researcher r11 = new Researcher("giuliafranceschi", "giuliafranceschi", "Giulia", "Franceschi", "FRNCGL02E45N001J");
        Researcher r12 = new Researcher("andreaedvige", "andreaedvige", "Andrea", "Edvige", "EDVADR01C13L989M");
        Researcher r13 = new Researcher("mariateresagrazi", "mariateresagrazi", "Maria Teresa", "Graziani", "GRZMTT01S77B124C");
        Researcher r14 = new Researcher("francescotommaso", "francescotommaso", "Francesco", "Tommaso", "TMMSFC02D23R123A");
        Researcher r15 = new Researcher("mot", "mot", "Sergio", "Ruolo", "RLSRGX01A01B456K");

        userRepository.save(r1);
        userRepository.save(r2);
        userRepository.save(r3);
        userRepository.save(r4);
        userRepository.save(r5);
        userRepository.save(r6);
        userRepository.save(r7);
        userRepository.save(r8);
        userRepository.save(r9);
        userRepository.save(r10);
        userRepository.save(r11);
        userRepository.save(r12);
        userRepository.save(r13);
        userRepository.save(r14);
        userRepository.save(r15);

        Supervisor s1 = new Supervisor("tom","tom","Tommaso","Vilotto","VLTTMS02B18F861N");
        Supervisor s2 = new Supervisor("mattia", "mattevino", "Mattia", "Vino", "VNTMTT01C25L591A");
        Supervisor s3 = new Supervisor("alessandra", "alesforza", "Alessandra", "Forza", "FRZLSN01S57B849P");
        Supervisor s4 = new Supervisor("davide", "davidevitali", "Davide", "Vitali", "VTLDVD01S43F031S");
        Supervisor s5 = new Supervisor("francescospano", "francescospano", "Francesco", "Spano", "SPNFRS01M15E493D");

        userRepository.save(s1);
        userRepository.save(s2);
        userRepository.save(s3);
        userRepository.save(s4);
        userRepository.save(s5);

        Administrator a1 = new Administrator("admin","admin","Gianfranco","Sulla Riva","GFTATS02B18F861N");
        Administrator a2 = new Administrator("superadmin", "superadmin", "Giovanni", "Rossi", "RSSGVN02H18A820L");
        Administrator a3 = new Administrator("support", "support123", "Alessandra", "Bruni", "BRNLSR01K29F481X");

        userRepository.save(a1);
        userRepository.save(a2);
        userRepository.save(a3);

        Project p1 = new Project("NeuroPlus", "1S4DFFG", "1F", "Univr", "97823");
        Project p2 = new Project("BioMedX", "2F5GHHK", "2A", "Polimi", "31425");
        Project p3 = new Project("GreenTech", "3G7HJKL", "3B", "Sapienza", "14276");
        Project p4 = new Project("SmartCity", "4H8KLMN", "4C", "Unipd", "25094");
        Project p5 = new Project("QuantumLab", "5J9LMNO", "5D", "UniTO", "35872");

        p1.setSupervisor(s1);
        p2.setSupervisor(s2);
        p3.setSupervisor(s3);
        p4.setSupervisor(s4);
        p5.setSupervisor(s5);

        p1.addResearcher(r1);
        p1.addResearcher(r2);
        p1.addResearcher(r3);
        p1.addResearcher(r4);
        p1.addResearcher(r5);

        p2.addResearcher(r6);
        p2.addResearcher(r7);
        p2.addResearcher(r8);
        p2.addResearcher(r9);
        p2.addResearcher(r10);

        p3.addResearcher(r11);
        p3.addResearcher(r12);
        p3.addResearcher(r13);
        p3.addResearcher(r14);
        p3.addResearcher(r15);

        p4.addResearcher(r1);
        p4.addResearcher(r2);
        p4.addResearcher(r6);
        p4.addResearcher(r8);
        p4.addResearcher(r15);

        p5.addResearcher(r3);
        p5.addResearcher(r4);
        p5.addResearcher(r9);
        p5.addResearcher(r10);
        p5.addResearcher(r13);

        p1.addResearcher(r15);

        projectRepository.save(p1);
        projectRepository.save(p2);
        projectRepository.save(p3);
        projectRepository.save(p4);
        projectRepository.save(p5);

        WorkingTime w1 = new WorkingTime(r15,p1, LocalDate.now(),2,false,false);
        WorkingTime w2 = new WorkingTime(r15,p3, LocalDate.now(),2.5,false,false);
        WorkingTime w3 = new WorkingTime(r15,p4, LocalDate.now(),3,false,false);

        wtRepository.save(w1);
        wtRepository.save(w2);
        wtRepository.save(w3);
    }

    @PostMapping("/print")
    public String print(){
        for (Utente utente: userRepository.findAll()){
            System.out.println(utente);

        }

        for (Project project: projectRepository.findAll()){
            System.out.println(project);
        }
        return "login";
    }

    @RequestMapping("/")
    public String login(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("userLoggedIn".equals(cookie.getName())) {
                    cookie.setValue(null);  // Imposta il valore del cookie a null
                    cookie.setMaxAge(0);    // Imposta l'età del cookie a 0 per eliminarlo
                    cookie.setPath("/");    // Assicurati che il cookie venga rimosso per tutto il dominio
                    response.addCookie(cookie);  // Aggiungi il cookie modificato nella risposta
                }
            }
        }
        return "login";}

    @RequestMapping("/login")
    public String login() {
        return "login"; // Nome del template Thymeleaf per il login
    }

    @RequestMapping("/supervisor")
    public String supervisor(HttpServletRequest request, Model model) {
        if(!isValidUrl("supervisor",request)){
            return "redirect:/index";
        }
        Cookie cookie = getCookieByName(request, "userLoggedIn");
        model.addAttribute("projects", projectRepository.findAllBySupervisor((Supervisor)userRepository.findByUsername(cookie.getValue())));
        model.addAttribute("username", cookie.getValue());
        return "supervisor";
    }

    @RequestMapping("/researcher")
    public String researcher(HttpServletRequest request, Model model) {
        if(!isValidUrl("researcher",request)){
            return "redirect:/index";
        }
        Cookie cookie = getCookieByName(request, "userLoggedIn");
        Researcher researcher = (Researcher)userRepository.findByUsername(cookie.getValue());

        //model.addAttribute("projects", projectRepository.findAllByResearchersContains(researcher));
        model.addAttribute("hours", wtRepository.findByDateAndResearcher(LocalDate.now(),researcher));
        model.addAttribute("username", cookie.getValue());
        model.addAttribute("status", wtRepository.getTopByResearcherAndDate(researcher,LocalDate.now()).getLeave());
        return "researcher";
    }

    @RequestMapping("/saveWorkingTime")
    public String saveWorkingTime(HttpServletRequest request, Model model,
                                  @RequestParam(name="hours", required=true) List<Double> hours,
                                  @RequestParam(name="checkbox", required=false) String checkbox) {
        Cookie cookie = getCookieByName(request, "userLoggedIn");
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
                //wtRepository.deleteByResearcherAndDateAndProject(researcher,LocalDate.now(),pj);
                wtRepository.deleteById(wt.getId());
                wtRepository.save(new WorkingTime(researcher,pj,LocalDate.now(),0,false,true));
            }
        }
        return "redirect:/researcher";
    }

    @RequestMapping("/downloadtimesheet")
    public String downloadtimesheet(HttpServletRequest request, Model model, @RequestParam(name="id", required = false) long id) {
        if(!isValidUrl("researcher",request)){
            return "redirect:/index";
        }
        Cookie cookie = getCookieByName(request, "userLoggedIn");
        model.addAttribute("project", projectRepository.findById(id));
        model.addAttribute("validatedMonths",new ArrayList<>(Arrays.asList("10/2024","11/2024","12/2024")));
        model.addAttribute("username", cookie.getValue());
        return "downloadtimesheet";
    }

    @RequestMapping("/validationtimesheet")
    public String validationtimesheet(HttpServletRequest request, Model model, @RequestParam(name="id", required = false) long id) {
        if(!isValidUrl("supervisor",request)){
            return "redirect:/index";
        }
        Cookie cookie = getCookieByName(request, "userLoggedIn");

        //forExample
        Researcher r1 = (Researcher) userRepository.findByUsername("mot");
        Researcher r2 = (Researcher) userRepository.findByUsername("nicozerman");
        Project p1 = projectRepository.findByTitle("NeuroPlus");
        WorkingTime t1 = new WorkingTime(r1,p1, LocalDate.of(2024, 11, 1),6L,true,false);
        WorkingTime t2 = new WorkingTime(r1,p1, LocalDate.of(2024,11,1),6L,false,false);
        WorkingTime t3 = new WorkingTime(r2,p1, LocalDate.of(2024,11,1),6L,false,false);

        //ipotizzando che quando supervisor valida, valida tutti i working time di un mese
        //sarà necessario passare una lista di working time, uno per ogni primo del mese tipo, relativi ad un ricercatore, per far funzionare l'html
        //serve anche passare una lista di tutti i ricercatori coinvolti
        model.addAttribute("lista",new ArrayList<>(Arrays.asList(t1,t2,t3)));
        model.addAttribute("researchers",new ArrayList<>(Arrays.asList(r1.getUsername(),r2.getUsername())));
        model.addAttribute("username", cookie.getValue());
        return "validationtimesheet";
    }

    @RequestMapping("/projectmanagement")
    public String projectmanagement(HttpServletRequest request, Model model, @RequestParam(name="id", required = false) long id) {
        if(!isValidUrl("supervisor",request)){
            return "redirect:/index";
        }
        Cookie cookie = getCookieByName(request, "userLoggedIn");

        model.addAttribute("username", cookie.getValue());
        return "projectmanagement";
    }


    @RequestMapping("/administrator")
    public String administrator(HttpServletRequest request, Model model) {
        if(!isValidUrl("administrator",request)){
            return "redirect:/index";
        }
        Cookie cookie = getCookieByName(request, "userLoggedIn");
        return "administrator";
    }

    @RequestMapping("/index")
    public String index(HttpServletRequest request) {
        if (isUserLoggedIn(request)) {
            return getIndexByUser(request);
        } else {
            return "redirect:/";
        }
    }

    @RequestMapping("/loginUser")
    public String create(@RequestParam(name="user", required = true) String user, @RequestParam(name="psw", required = true) String psw, RedirectAttributes redirectAttributes, HttpServletResponse response) {
        if (userRepository.existsByUsername(user)) {
            Utente foundUser = userRepository.findByUsername(user);
            if (foundUser.getPassword().equals(psw)) {
                Cookie cookie = new Cookie("userLoggedIn", foundUser.getUsername());
                cookie.setMaxAge(3600); // 1 ora di durata
                cookie.setHttpOnly(true); // Proteggi il cookie
                cookie.setPath("/"); // Il cookie è accessibile in tutto il dominio
                response.addCookie(cookie);

                return "redirect:/index";
            }
            redirectAttributes.addFlashAttribute("error", "Wrong password");
        }
        else {
            redirectAttributes.addFlashAttribute("error", "User does not exist.");
        }
        return "redirect:/login";
    }

    public Cookie getCookieByName(HttpServletRequest request, String cookieName) {
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

    private boolean isValidUrl(String url,HttpServletRequest request){
        return ("redirect:/"+url).equals(getIndexByUser(request));
    }

    public boolean isUserLoggedIn(HttpServletRequest request) {
        Cookie cookie = getCookieByName(request,"userLoggedIn");
        if (cookie != null) {
            return userRepository.existsByUsername(cookie.getValue());

        }
        return false;
    }



    /*@PostMapping("/createSupervisor")
    public Supervisor createSupervisor(@RequestBody Supervisor user) {
        System.out.println(user);
        return userRepository.save(user);
    }

    @PostMapping("/createResearcher")
    public Researcher createResearcher(@RequestBody Researcher user) {
        return userRepository.save(user);
    }

    @PostMapping("/createAdministrator")
    public Administrator createAdministrator(@RequestBody Administrator user) {
        return userRepository.save(user);
    }

    @PostMapping("/createProject")
    public Project createResearcher(@RequestBody Project progetto) {
        projectRepository.save(progetto);
        return progetto;
    }

    */

}
