package org.coderhouse.billing.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor //generate constructor with all arguments
@NoArgsConstructor //generate constructor without any arguments
@Getter //generate all getters for all properties
@Setter //generate all setters for all properties
@ToString //to get a string representation of the object
@Table(name = "clients") //defines what the entity is called in the DB
public class Client {
    //properties
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter //only getter for id- field-level annotation takes priority
    private Integer id;

    private String name;

    private String lastName;

    private String dni;

    private Integer age;
}
