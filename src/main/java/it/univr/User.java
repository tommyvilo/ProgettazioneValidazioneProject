package it.univr;

import jakarta.persistence.*;

@Entity
public class User {

    @Id @GeneratedValue(strategy=GenerationType.AUTO) private Long id;

}
