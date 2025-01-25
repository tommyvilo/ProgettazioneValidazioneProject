package it.univr.Model.User;

import jakarta.persistence.Entity;

@Entity
public class Administrator extends Utente {
    protected Administrator(){}

    public Administrator(String username, String password, String name, String surname, String cf){
        super(username, password, name, surname, cf);
    }

    @Override
    public String toString(){
        return "Administrator [id=" + getId() + ", username=" + getUsername() + ", name=" + getName() + ", surname=" + getSurname() + ", cf=" + getCf() + "]";
    }
}
