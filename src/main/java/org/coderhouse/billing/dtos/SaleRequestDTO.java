package org.coderhouse.billing.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor //generate constructor without any arguments
@Getter //generate all getters for all properties
public class SaleRequestDTO {

    @Schema(description = "ID of the client", required = true, example = "1")
    private Integer clientId;

    private List<SaleItemDTO> sales = new ArrayList<>();

}
