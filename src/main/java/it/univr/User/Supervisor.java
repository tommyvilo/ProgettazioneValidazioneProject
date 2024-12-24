package it.univr.User;

import it.univr.Project;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Supervisor extends Utente {
    protected Supervisor(){}

    public Supervisor(String username, String password, String name, String surname, String cf){
        super(username, password, name, surname, cf);
    }
}
