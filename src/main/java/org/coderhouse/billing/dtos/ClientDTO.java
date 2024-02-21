package org.coderhouse.billing.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(description = "ID of the client", required = true, example = "1")
    private Integer id;

    @Schema(description = "Name of the client", required = true, example = "Juana")
    private String name;

    @Schema(description = "Last Name of the client", required = true, example = "Perez")
    private String lastName;

    @Schema(description = "Age of the client", required = true, example = "25")
    private Integer age;

    public ClientDTO(Client client){
        this.id = client.getId();

        this.name = client.getName();

        this.lastName= client.getLastName();

        this.age= client.getAge();

    }



}
