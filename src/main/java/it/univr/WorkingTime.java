package it.univr;

import it.univr.User.Researcher;
import jakarta.persistence.*;

import java.util.Date;

@Entity
public class WorkingTime {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO) private Long id;

    @ManyToOne
    private Researcher researcher;

    @ManyToOne
    private Project project;

    private Date date;

    private Long workedHours;
    private boolean validated;

    protected WorkingTime() {}

    public WorkingTime(Researcher researcher, Project project, Date date, Long workedHours, boolean validated) {
        this.researcher = researcher;
        this.project = project;
        this.date = date;
        this.workedHours = workedHours;
        this.validated = validated;
    }

    public void setResearcher(Researcher researcher){
        this.researcher = researcher;
    }

    public Researcher getResearcher(){
        return researcher;
    }

    public void setProject(Project project){
        this.project = project;
    }

    public Project getProject(){
        return project;
    }

    public void setDate(Date date){
        this.date = date;
    }

    public Date getDate(){
        return date;
    }

    public void setWorkedHours(Long workedHours){
        this.workedHours = workedHours;
    }

    public Long getWorkedHours(){
        return workedHours;
    }

    public void setValidated(boolean validated){
        this.validated = validated;
    }

    public boolean isValidated(){
        return validated;
    }


}
