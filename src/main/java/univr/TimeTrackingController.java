package univr;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import univr.User.Administrator;
import univr.User.Researcher;
import univr.User.Supervisor;
import univr.User.Utente;

import java.util.ArrayList;
import java.util.Arrays;

@Controller
public class TimeTrackingController {

    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private UserRepository userRepository;

    @RequestMapping("/")
    public String login() { return "login";}

    @RequestMapping("/index")
    public String index() { return "index";}


    @PostConstruct
    public void init() {
        userRepository.save(new Researcher("nicozerman","zermaculo","Nicolò","Zerman","ZRMNCL02S19L781E"));
        userRepository.save(new Supervisor("tom","tommyvilo","Tommaso","Vilotto","VLTTMS02B18F861N"));
        userRepository.save(new Administrator("admin","admin","Gianfranco","Sulla Riva","GFTATS02B18F861N"));
        Project p1 = new Project("Dux Mea Lux", "Fornite", "1F", "Univr", "97823", new ArrayList<>(Arrays.asList(1L)));
        projectRepository.save(p1);
        //System.out.println((Supervisor) userRepository.findByUsername("tom"));
        Supervisor s1 = ((Supervisor) userRepository.findByUsername("tom"));
        s1.addProject(p1);
        userRepository.save(s1);
        //p1.setSupervisor((Supervisor) userRepository.findByUsername("tom"));
        //p1.setSupervisor((Supervisor) userRepository.findByUsername("tom"));
    }

    @RequestMapping("/loginUser")
    public String create(@RequestParam(name="user", required = true) String user, @RequestParam(name="psw", required = true) String psw, RedirectAttributes redirectAttributes) {

        if (userRepository.existsByUsername(user)) {
            Utente foundUser = userRepository.findByUsername(user);
            if (foundUser != null && foundUser.getPassword().equals(psw)) {
                return "redirect:/index";
            }
            redirectAttributes.addFlashAttribute("error", "Wrong password");
        }
        else {
            redirectAttributes.addFlashAttribute("error", "User does not exist.");
        }
        return "redirect:/";
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



}
