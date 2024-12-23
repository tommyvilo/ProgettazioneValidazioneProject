package univr;

import jakarta.persistence.Embeddable;

@Embeddable
public class WorkingTime {

    private Long idUser;

    private Long workedHours;
    private boolean validated;

    protected WorkingTime() {}

    public WorkingTime(Long idUser, Long workedHours, boolean validated) {
        this.idUser = idUser;
        this.workedHours = workedHours;
        this.validated = validated;
    }

    public void setIdUser(Long idUser){
        this.idUser = idUser;
    }

    public Long getIdUser(){
        return idUser;
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
