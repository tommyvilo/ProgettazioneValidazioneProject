package it.univr.User;
import jakarta.persistence.Entity;

@Entity
public class Supervisor extends Utente {
    protected Supervisor(){}

    public Supervisor(String username, String password, String name, String surname, String cf){
        super(username, password, name, surname, cf);
    }
}
