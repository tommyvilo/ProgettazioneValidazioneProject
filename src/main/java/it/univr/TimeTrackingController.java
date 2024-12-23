package it.univr;

import it.univr.User.Administrator;
import it.univr.User.Researcher;
import it.univr.User.Supervisor;
import it.univr.User.Utente;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;

@Controller
public class TimeTrackingController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProjectRepository projectRepository;

    @RequestMapping("/")
    public String login() {return "login";}

    /*
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

    @PostMapping("/createSupervisor")
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
