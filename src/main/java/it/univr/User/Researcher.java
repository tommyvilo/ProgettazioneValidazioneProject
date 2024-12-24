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
    protected Researcher() {
    }

    public Researcher(String username, String password, String name, String surname, String cf){
        super(username, password, name, surname, cf);
    }

}
