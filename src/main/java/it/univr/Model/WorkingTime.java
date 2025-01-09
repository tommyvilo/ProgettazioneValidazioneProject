package it.univr.Model;

import it.univr.Model.User.Researcher;
import it.univr.Model.User.Utente;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


@Entity
public class WorkingTime {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO) private Long id;

    @ManyToOne
    private Utente utente;

    @ManyToOne
    private Project project;

    private LocalDate date;

    private double workedHours;
    private boolean validated;

    private boolean leave; //Malattie, ferie ecc...

    protected WorkingTime() {}

    public WorkingTime(Utente utente, Project project, LocalDate date, double workedHours, boolean validated, boolean leave) {
        this.utente = utente;
        this.project = project;
        this.date = date;
        this.workedHours = workedHours;
        this.validated = validated;
        this.leave = leave;
    }

    public void setUtente(Utente utente){
        this.utente = utente;
    }

    public Utente getUtente(){
        return utente;
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

    public boolean getValidated(){
        return validated;
    }

    public String getMonthYear() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yyyy");
        return date.format(formatter);
    }

    public Long getId(){
        return id;
    }

    public String getFormattedHours(){
        int hours = (int) workedHours;
        int minutes = (int) Math.round((workedHours - hours) * 60);

        // Format minutes with leading zero if necessary
        return String.format("%d:%02d", hours, minutes);
    }
}
