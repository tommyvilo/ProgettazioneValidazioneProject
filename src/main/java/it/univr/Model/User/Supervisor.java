package it.univr.Model.User;
import jakarta.persistence.Entity;

@Entity
public class Supervisor extends Utente {
    protected Supervisor(){}

    public Supervisor(String username, String password, String name, String surname, String cf){
        super(username, password, name, surname, cf);
    }

    @Override
    public String toString(){
        return "Supervisor [id=" + getId() + ", username=" + getUsername() + ", name=" + getName() + ", surname=" + getSurname() + ", cf=" + getCf() + "]";
    }
}
