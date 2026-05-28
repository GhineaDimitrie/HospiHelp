package com.hospihelp.hospihelp.dto;

import com.hospihelp.hospihelp.model.enums.RolAngajat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AngajatRequest {

    @NotBlank(message = "Numele este obligatoriu")
    private String nume;

    @NotBlank(message = "Prenumele este obligatoriu")
    private String prenume;

    @NotBlank(message = "Email-ul este obligatoriu")
    @Email(message = "Email-ul nu este valid")
    private String email;

    @NotBlank(message = "Parola este obligatorie")
    @Size(min = 6, message = "Parola trebuie sa aiba cel putin 6 caractere")
    private String parolaCriptata; // frontendl trimite parola in acest camp

    @NotNull(message = "Rolul este obligatoriu")
    private RolAngajat rol;
}