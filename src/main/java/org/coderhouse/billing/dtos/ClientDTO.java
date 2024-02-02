package org.coderhouse.billing.dtos;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import org.coderhouse.billing.models.Client;
import org.hibernate.annotations.GenericGenerator;

@NoArgsConstructor //generate constructor without any arguments
@Getter //generate all getters for all properties
public class ClientDTO {
    //properties
    private String name;

    private String lastName;

    private Integer age;

    public ClientDTO(Client client){

        this.name = client.getName();

        this.lastName= client.getLastName();

        this.age= client.getAge();

    }



}
