package it.univr.Model.User;
import jakarta.persistence.Entity;


@Entity
public class Researcher extends Utente {
    protected Researcher() {
    }

    public Researcher(String username, String password, String name, String surname, String cf){
        super(username, password, name, surname, cf);
    }

    @Override
    public String toString(){
        return "Researcher [id=" + getId() + ", username=" + getUsername() + ", name=" + getName() + ", surname=" + getSurname() + ", cf=" + getCf() + "]";
    }

}
