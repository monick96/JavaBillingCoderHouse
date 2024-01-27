package org.coderhouse.billing.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

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
    private LocalDate birthdate;

    private Integer age;

    @JsonIgnore
    @OneToMany (mappedBy="client", fetch=FetchType.EAGER)
    private Set<Sale> sales = new HashSet<>();

    public void addSale(Sale sale){
        sale.setClient(this); //in Sale class set this client
        sales.add(sale); // in collection sales add the sale object
    }

    @Override
    public String toString() {

        return "name= " + this.name + ", lastName= " + this.lastName + ", age= " + this.age;

    }
}
