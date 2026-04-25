package com.hospihelp.hospihelp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hospihelp.hospihelp.model.enums.RolAngajat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "angajati")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Angajat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_angajat")
    private Integer idAngajat;

    @NotBlank(message = "Numele este obligatoriu")
    @Column(name = "nume", nullable = false, length = 100)
    private String nume;

    @NotBlank(message = "Prenumele este obligatoriu")
    @Column(name = "prenume", nullable = false, length = 100)
    private String prenume;

    @JsonIgnore
    @NotBlank(message = "Parola este obligatorie")
    @Size(min = 6, message = "Parola trebuie sa aiba cel putin 6 caractere")
    @Column(name = "parola_criptata", nullable = false)
    private String parolaCriptata;

    @NotBlank(message = "Email-ul este obligatoriu")
    @Email(message = "Email-ul nu este valid")
    @Column(name = "email", nullable = false, unique = true, length = 150)
    private String email;

    @NotNull(message = "Rolul este obligatoriu")
    @Enumerated(EnumType.STRING)
    @Column(name = "rol", nullable = false)
    private RolAngajat rol;
}