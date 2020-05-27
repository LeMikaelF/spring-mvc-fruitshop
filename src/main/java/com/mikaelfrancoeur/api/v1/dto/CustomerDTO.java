package com.mikaelfrancoeur.api.v1.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class CustomerDTO {
    private Long id;
    @NotBlank
    @JsonProperty("firstname")
    private String firstName;
    @NotBlank
    @JsonProperty("lastname")
    private String lastName;
    @JsonProperty("customer_url")
    private String customerUrl;

    public CustomerDTO(Long id) {
        this.id = id;
    }
}
