package it.univr.User;

import jakarta.persistence.Entity;

@Entity
public class Researcher extends Utente {

    protected Researcher() {
    }

    public Researcher(String username, String password, String name, String surname, String cf){
        super(username, password, name, surname, cf);
    }
}
