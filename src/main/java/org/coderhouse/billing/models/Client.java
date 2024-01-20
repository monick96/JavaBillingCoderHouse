package org.coderhouse.billing.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

@Entity
@RequiredArgsConstructor//generate constructor with only required arguments
@NoArgsConstructor //generate constructor without any arguments
@Getter //generate all getters for all properties
@Setter //generate all setters for all properties
@ToString //to get a string representation of the object
@Table(name = "clients") //defines what the entity is called in the DB
public class Client {
    //properties
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @GenericGenerator(name = "native", strategy = "native")
    @Getter //only getter for id- field-level annotation takes priority
    private Integer id;

    @NonNull //verify that the value is not null
    private String name;

    @NonNull
    private String lastName;

    @NonNull
    private String dni;

    @NonNull
    private Integer age;
}
