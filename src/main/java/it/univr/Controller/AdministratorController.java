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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class AdministratorController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private WorkingTimeRepository wtRepository;

    @RequestMapping("/administrator")
    public String administrator(HttpServletRequest request, Model model) {
        if(!isValidUrl(request)){
            return "redirect:/";
        }
        Cookie cookie = getCookieByName(request, "userLoggedIn");
        model.addAttribute("username", cookie.getValue());
        return "administrator";
    }

    @RequestMapping("/manageUsers")
    public String manageUsers(HttpServletRequest request, Model model) {
        if(!isValidUrl(request)){
            return "redirect:/";
        }
        System.out.println("ciao");
        Cookie cookie = getCookieByName(request, "userLoggedIn");
        ArrayList<Utente> users = new ArrayList<>();
        for(Utente user : userRepository.findAll()){
            if( user instanceof Researcher || user instanceof Supervisor) {
                users.add(user);
            }
        }
        model.addAttribute("users",users);
        model.addAttribute("username", cookie.getValue());
        return "manageUsers";
    }

    @RequestMapping("/newUser")
    public String newUser(HttpServletRequest request, Model model) {
        if(!isValidUrl(request)){
            return "redirect:/";
        }
        Cookie cookie = getCookieByName(request, "userLoggedIn");
        model.addAttribute("username", cookie.getValue());
        return "newUser";
    }

    @PostMapping("/createUser")
    public String createUser(@RequestParam(name="username") String username, @RequestParam(name="password") String password,
                             @RequestParam(name="name") String name, @RequestParam(name="surname") String surname,
                             @RequestParam(name="cf") String cf, @RequestParam(name="userType") String userType) {

        if(userType.equals("Supervisor")){
            userRepository.save(new Supervisor(username,password,name,surname,cf));
        } else{
            userRepository.save(new Researcher(username,password,name,surname,cf));
        }
        return "redirect:/manageUsers";
    }

    @RequestMapping("/manageProjects")
    public String manageProject(HttpServletRequest request, Model model) {
        if(!isValidUrl(request)){
            return "redirect:/";
        }
        Cookie cookie = getCookieByName(request, "userLoggedIn");
        model.addAttribute("projects", projectRepository.findAll());
        model.addAttribute("username", cookie.getValue());
        return "manageProjects";
    }

    @RequestMapping("/newProject")
    public String newProject(HttpServletRequest request, Model model) {
        if(!isValidUrl(request)){
            return "redirect:/";
        }
        Cookie cookie = getCookieByName(request, "userLoggedIn");
        ArrayList<Supervisor> users = new ArrayList<>();
        for(Utente user : userRepository.findAll()){
            if(user instanceof Supervisor) {
                users.add((Supervisor) user);
            }
        }
        model.addAttribute("supervisors", users);
        model.addAttribute("username", cookie.getValue());
        return "newProject";
    }

    @RequestMapping("/deleteUser")
    public String deleteUser(HttpServletRequest request, @RequestParam(name="id") Long id) {
        if(!isValidUrl(request)){
            return "redirect:/";
        }

        Researcher researcher = (Researcher)userRepository.findById(id).orElse(null);

        for(WorkingTime wt: wtRepository.findByResearcher(researcher)){
            wtRepository.deleteById(wt.getId());
        }

        for(Project pj: projectRepository.findAllByResearchersContains(researcher)){
            List<Researcher> researchers = pj.getResearchers();
            researchers.remove(researcher);
            pj.setResearchers(researchers);
            projectRepository.save(pj);
        }

        userRepository.deleteById(id);
        return "redirect:/manageUsers";
    }


    @PostMapping("/createProject")
    public String createProject(@RequestParam(name="title") String title, @RequestParam(name="cup") String cup,
                                @RequestParam(name="code") String code, @RequestParam(name="denominazioneSoggetto") String denominazioneSoggetto,
                                @RequestParam(name="cfSoggetto") String cfSoggetto, @RequestParam(name="supervisor") Long supervisor // ID del supervisore scelto
    ) {
        Project newProject = new Project(title,cup,code,denominazioneSoggetto,cfSoggetto);
        newProject.setSupervisor((Supervisor) userRepository.findById(supervisor).orElse(null));
        projectRepository.save(newProject);
        return "redirect:/manageProjects";
    }

    private boolean isValidUrl(HttpServletRequest request){
        String url = "administrator";

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
}
