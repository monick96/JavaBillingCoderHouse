package org.coderhouse.billing.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
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

    //@NonNull //verify that the value is not null
    private String name;

    //@NonNull
    private String lastName;

    // @NonNull
    private String dni;

    //@NonNull
    private LocalDate birthdate;

    private Integer age;

    private Boolean isActive;

    //@JsonIgnore //using a DTO there is no recursion
    @OneToMany (mappedBy="client", fetch=FetchType.LAZY)
    private Set<Sale> sales = new HashSet<>();

    //constructor


    public Client(String name,String lastName, String dni, LocalDate birthdate) {
        this.name = name;
        this.lastName = lastName;
        this.dni = dni;
        this.birthdate = birthdate;
    }

    public void addSale(Sale sale){
        sale.setClient(this); //in Sale class set this client
        sales.add(sale); // in collection sales add the sale object
    }

    @Override
    public String toString() {

        return "name= " + this.name + ", lastName= " + this.lastName + ", age= " + this.age;

    }
}
