package it.univr.User;

import jakarta.persistence.*;

@Entity
public abstract class Utente {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique = true)
    private String username;
    private String password;
    private String name;
    private String surname;
    private String cf;

    protected Utente() {
    }

    public Utente(String username, String password, String name, String surname, String cf) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.cf = cf;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public String getName() {
        return this.name;
    }

    public String getSurname() {
        return this.surname;
    }

    public String getCf() {
        return this.cf;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setCf(String cf) {
        this.cf = cf;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString(){
        return "Utente [id=" + id + ", username=" + username + ", name=" + name + ", surname=" + surname + ", cf=" + cf + "]";
    }
}