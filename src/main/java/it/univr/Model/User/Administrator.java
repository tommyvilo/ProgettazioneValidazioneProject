package it.univr.Model.User;

import jakarta.persistence.Entity;

@Entity
public class Administrator extends Utente {
    protected Administrator(){}

    public Administrator(String username, String password, String name, String surname, String cf){
        super(username, password, name, surname, cf);
    }
}
