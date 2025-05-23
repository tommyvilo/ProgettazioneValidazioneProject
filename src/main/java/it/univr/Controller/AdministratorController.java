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
    @Autowired
    private TimeTrackingController ttController;

    @RequestMapping("/administrator")
    public String administrator(HttpServletRequest request, HttpServletResponse response, Model model) {
        if(ttController.isNotValidUrl("administrator",request)){
            return "redirect:/";
        }
        response.setHeader("Cache-Control","no-store");
        Cookie cookie = ttController.getCookieByName(request, "userLoggedIn");
        assert cookie != null;
        model.addAttribute("username", cookie.getValue());
        return "administrator";
    }

    @RequestMapping("/manageUsers")
    public String manageUsers(HttpServletRequest request, HttpServletResponse response, Model model) {
        if(ttController.isNotValidUrl("administrator",request)){
            return "redirect:/";
        }
        response.setHeader("Cache-Control","no-store");
        Cookie cookie = ttController.getCookieByName(request, "userLoggedIn");
        ArrayList<Utente> users = new ArrayList<>();
        for(Utente user : userRepository.findAll()){
            if( user instanceof Researcher || user instanceof Supervisor) {
                users.add(user);
            }
        }
        model.addAttribute("users",users);
        assert cookie != null;
        model.addAttribute("username", cookie.getValue());
        return "manageUsers";
    }

    @RequestMapping("/newUser")
    public String newUser(HttpServletRequest request, HttpServletResponse response, Model model) {
        if(ttController.isNotValidUrl("administrator",request)){
            return "redirect:/";
        }
        response.setHeader("Cache-Control","no-store");
        Cookie cookie = ttController.getCookieByName(request, "userLoggedIn");
        assert cookie != null;
        model.addAttribute("username", cookie.getValue());
        return "newUser";
    }

    @PostMapping("/createUser")
    public String createUser(HttpServletResponse response, @RequestParam(name="username") String username, @RequestParam(name="password") String password,
                             @RequestParam(name="name") String name, @RequestParam(name="surname") String surname,
                             @RequestParam(name="cf") String cf, @RequestParam(name="userType") String userType) {
        response.setHeader("Cache-Control","no-store");
        if(userType.equals("Supervisor")){
            userRepository.save(new Supervisor(username,password,name,surname,cf));
        } else{
            userRepository.save(new Researcher(username,password,name,surname,cf));
        }
        return "redirect:/manageUsers";
    }

    @RequestMapping("/manageProjects")
    public String manageProject(HttpServletRequest request,HttpServletResponse response, Model model) {
        if(ttController.isNotValidUrl("administrator",request)){
            return "redirect:/";
        }
        response.setHeader("Cache-Control","no-store");
        Cookie cookie = ttController.getCookieByName(request, "userLoggedIn");
        model.addAttribute("projects", projectRepository.findAll());
        assert cookie != null;
        model.addAttribute("username", cookie.getValue());
        return "manageProjects";
    }

    @RequestMapping("/newProject")
    public String newProject(HttpServletRequest request, HttpServletResponse response, Model model) {
        if(ttController.isNotValidUrl("administrator",request)){
            return "redirect:/";
        }
        response.setHeader("Cache-Control","no-store");
        Cookie cookie = ttController.getCookieByName(request, "userLoggedIn");
        ArrayList<Supervisor> users = new ArrayList<>();
        for(Utente user : userRepository.findAll()){
            if(user instanceof Supervisor) {
                users.add((Supervisor) user);
            }
        }
        model.addAttribute("supervisors", users);
        assert cookie != null;
        model.addAttribute("username", cookie.getValue());
        return "newProject";
    }

    @RequestMapping("/deleteUser")
    public String deleteUser(HttpServletRequest request, HttpServletResponse response, @RequestParam(name="id") Long id) {
        if(ttController.isNotValidUrl("administrator",request)){
            return "redirect:/";
        }
        response.setHeader("Cache-Control","no-store");
        Utente utente = userRepository.findById(id).orElse(null);

        for(WorkingTime wt: wtRepository.findByUtente(utente)){
            wtRepository.deleteById(wt.getId());
        }

        if(utente instanceof Researcher) {
            wtRepository.deleteAll(wtRepository.findByUtente(utente));

            for (Project pj : projectRepository.findAllByResearchersContains((Researcher) utente)) {
                List<Researcher> researchers = pj.getResearchers();
                researchers.remove(utente);
                pj.setResearchers(researchers);
                projectRepository.save(pj);
            }
        } else {
            for (Project pj : projectRepository.findAllBySupervisor((Supervisor) utente)) {
                List<Researcher> researchers = pj.getResearchers();

                for(Researcher researcher : researchers){
                    wtRepository.deleteAll(wtRepository.findAllByUtenteAndProject(researcher,pj));
                }
                projectRepository.delete(pj);
            }
        }

        userRepository.deleteById(id);
        return "redirect:/manageUsers";
    }


    @PostMapping("/createProject")
    public String createProject(HttpServletResponse response, @RequestParam(name="title") String title, @RequestParam(name="cup") String cup,
                                @RequestParam(name="code") String code, @RequestParam(name="denominazioneSoggetto") String denominazioneSoggetto,
                                @RequestParam(name="cfSoggetto") String cfSoggetto, @RequestParam(name="supervisor") Long supervisor // ID del supervisore scelto

    ) {
        response.setHeader("Cache-Control","no-store");
        Project newProject = new Project(title,cup,code,denominazioneSoggetto,cfSoggetto);
        newProject.setSupervisor((Supervisor) userRepository.findById(supervisor).orElse(null));
        projectRepository.save(newProject);
        return "redirect:/manageProjects";
    }
}
