package it.univr;

import it.univr.User.Researcher;
import jakarta.persistence.*;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


@Entity
public class WorkingTime {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO) private Long id;

    @ManyToOne
    private Researcher researcher;

    @ManyToOne
    private Project project;

    private LocalDate date;

    private double workedHours;
    private boolean validated;

    private boolean leave; //Malattie, ferie ecc...

    protected WorkingTime() {}

    public WorkingTime(Researcher researcher, Project project, LocalDate date, double workedHours, boolean validated, boolean leave) {
        this.researcher = researcher;
        this.project = project;
        this.date = date;
        this.workedHours = workedHours;
        this.validated = validated;
        this.leave = leave;
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

    public void setDate(LocalDate date){
        this.date = date;
    }

    public LocalDate getDate(){
        return date;
    }

    public void setWorkedHours(double workedHours){
        this.workedHours = workedHours;
    }

    public double getWorkedHours(){
        return workedHours;
    }

    public void setLeave(boolean leave){
        this.leave = leave;
    }

    public boolean getLeave(){
        return leave;
    }

    public void setValidated(boolean validated){
        this.validated = validated;
    }

    public boolean isValidated(){
        return validated;
    }

    public String getMonthYear() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yyyy");
        return date.format(formatter);
    }

    public long getId(){
        return id;
    }

    public String getFormattedHours(){
        int hours = (int) workedHours;
        int minutes = (int) Math.round((workedHours - hours) * 60);

        // Format minutes with leading zero if necessary
        return String.format("%d:%02d", hours, minutes);
    }


}
