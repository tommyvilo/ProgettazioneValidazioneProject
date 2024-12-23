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
        Supervisor s1 = new Supervisor("tom","tommyvilo","Tommaso","Vilotto","VLTTMS02B18F861N");
        Administrator a1 = new Administrator("admin","admin","Gianfranco","Sulla Riva","GFTATS02B18F861N");

        userRepository.save(r1);
        userRepository.save(s1);
        userRepository.save(a1);

        Project p1 = new Project("Dux Mea Lux", "Fornite", "1F", "Univr", "97823");
        projectRepository.save(p1);

        s1.addProject(p1);
        userRepository.save(s1);

        r1.addProject(p1);
        userRepository.save(r1);
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
