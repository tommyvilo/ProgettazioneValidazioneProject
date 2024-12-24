package it.univr.User;

import it.univr.Project;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Researcher extends Utente {

    @ManyToMany(mappedBy = "researchers", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Project> projects = new ArrayList<>();

    protected Researcher() {
    }

    public Researcher(String username, String password, String name, String surname, String cf){
        super(username, password, name, surname, cf);
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void addProject(Project project){
        this.projects.add(project);
        project.addResearcher(this);
    }

    public String toString() {
        return "Researcher: " + super.toString() + " Projects: " + projects.size();
    }
}
