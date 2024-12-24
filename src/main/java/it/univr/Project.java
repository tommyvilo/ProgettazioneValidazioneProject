package it.univr;

import it.univr.User.Researcher;
import it.univr.User.Supervisor;
import jakarta.persistence.*;

import java.util.*;

@Entity
public class Project {

    @Id @GeneratedValue(strategy=GenerationType.AUTO) private Long id;
    private String title;
    private String cup;
    private String code;
    private String denominazioneSoggetto;
    private String cfSoggetto;

    @ManyToOne
    @JoinColumn(name= "id_supervisor")
    private Supervisor supervisor;

    /*@ElementCollection
    private List<Long> researchers = new ArrayList<>();*/

    @ManyToMany
    @JoinTable(
            name = "project_researcher", // Nome della tabella di join
            joinColumns = @JoinColumn(name = "id_researcher"), // Colonna di join per Student
            inverseJoinColumns = @JoinColumn(name = "id_project") // Colonna di join per Course
    )
    private List<Researcher> researchers = new ArrayList<>();

    protected Project() {}

    public Project(String title, String cup, String code, String denominazioneSoggetto, String cfSoggetto) {
        this.title = title;
        this.cup = cup;
        this.code = code;
        this.denominazioneSoggetto = denominazioneSoggetto;
        this.cfSoggetto = cfSoggetto;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getCup(){
        return cup;
    }

    public String getCode(){
        return code;
    }

    public String getDenominazioneSoggetto(){
        return denominazioneSoggetto;
    }

    public String getCfSoggetto(){
        return cfSoggetto;
    }

    public List<Researcher> getResearchers() {
        return researchers;
    }

    public Supervisor getSupervisor() {
        return supervisor;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCup(String cup) {
        this.cup = cup;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setDenominazioneSoggetto(String denominazioneSoggetto) {
        this.denominazioneSoggetto = denominazioneSoggetto;
    }

    public void setCfSoggetto(String CFsoggetto) {
        this.cfSoggetto = CFsoggetto;
    }

    public void setResearchers(List<Researcher> researchers) {
        this.researchers = researchers;
    }

    public void setSupervisor(Supervisor supervisor) {
        this.supervisor = supervisor;
    }

    @Override
    public String toString(){
        return "Project{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", cup='" + cup + '\'' +
                ", code='" + code + '\'' +
                ", denominazioneSoggetto='" + denominazioneSoggetto + '\'' +
                ", cfSoggetto='" + cfSoggetto + '\'' +
                ", supervisor=" + supervisor +
                ", researchers=" + researchers +
                '}';
    }

    public void addResearcher(Researcher researcher) {
        researchers.add(researcher);
    }
}
