package it.univr;

import it.univr.User.Researcher;
import jakarta.persistence.Embeddable;
import jakarta.persistence.ManyToOne;

@Embeddable
public class WorkingTime {

    @ManyToOne
    private Researcher researcher;

    private Long workedHours;
    private boolean validated;

    protected WorkingTime() {}

    public WorkingTime(Researcher researcher, Long workedHours, boolean validated) {
        this.researcher = researcher;
        this.workedHours = workedHours;
        this.validated = validated;
    }

    public void setResearcher(Researcher researcher){
        this.researcher = researcher;
    }

    public Researcher getResearcher(){
        return researcher;
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
