package com.agus.springboot.belajarspringboot.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateContactRequest {

    @NotBlank
    @JsonIgnore
    private String id;
    @NotBlank
    private String firstname;
    private String lastname;
    @Email
    private String email;
    private String phone;
}
