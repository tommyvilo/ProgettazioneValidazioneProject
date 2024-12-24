package it.univr;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import it.univr.User.Administrator;
import it.univr.User.Researcher;
import it.univr.User.Supervisor;
import it.univr.User.Utente;

import java.util.ArrayList;
import java.util.Arrays;

@Controller
public class TimeTrackingController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProjectRepository projectRepository;

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

    @RequestMapping("/index")
    public String index(HttpServletRequest request) {
        if (isUserLoggedIn(request)) {
            return "index";
        } else {
            return "redirect:/";
        }
    }


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
        Researcher r15 = new Researcher("sergioruolo", "sergioruolo", "Sergio", "Ruolo", "RLSRGX01A01B456K");

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

        Supervisor s1 = new Supervisor("tom","tommyvilo","Tommaso","Vilotto","VLTTMS02B18F861N");
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

        projectRepository.save(p1);
        projectRepository.save(p2);
        projectRepository.save(p3);
        projectRepository.save(p4);
        projectRepository.save(p5);

        s1.addProject(p1);
        userRepository.save(s1);

        s2.addProject(p2);
        userRepository.save(s2);

        s3.addProject(p3);
        userRepository.save(s3);

        s4.addProject(p4);
        userRepository.save(s4);

        s5.addProject(p5);
        userRepository.save(s5);

        r1.addProject(p1);
        r2.addProject(p1);
        r3.addProject(p1);
        r4.addProject(p1);
        r5.addProject(p1);

        r6.addProject(p2);
        r7.addProject(p2);
        r8.addProject(p2);
        r9.addProject(p2);
        r10.addProject(p2);

        r11.addProject(p3);
        r12.addProject(p3);
        r13.addProject(p3);
        r14.addProject(p3);
        r15.addProject(p3);

        r1.addProject(p4);
        r2.addProject(p4);
        r6.addProject(p4);
        r8.addProject(p4);
        r15.addProject(p4);

        r3.addProject(p5);
        r4.addProject(p5);
        r9.addProject(p5);
        r10.addProject(p5);
        r13.addProject(p5);

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


    }

    @PostMapping("/print")
    public String print(){
        for (Utente utente: userRepository.findAll()){
            System.out.println(utente);
            if (utente instanceof Supervisor){
                ((Supervisor) utente).getProjects().forEach(System.out::println);
            }
        }

        for (Project project: projectRepository.findAll()){
            System.out.println(project);
        }
        return "login";
    }

    @RequestMapping("/loginUser")
    public String create(@RequestParam(name="user", required = true) String user, @RequestParam(name="psw", required = true) String psw, RedirectAttributes redirectAttributes, HttpServletResponse response) {
        if (userRepository.existsByUsername(user)) {
            Utente foundUser = userRepository.findByUsername(user);
            if (foundUser != null && foundUser.getPassword().equals(psw)) {
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
        return "redirect:/";
    }


    public boolean isUserLoggedIn(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("userLoggedIn".equals(cookie.getName())) {
                    return true; // L'utente è loggato
                }
            }
        }
        return false; // L'utente non è loggato
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
