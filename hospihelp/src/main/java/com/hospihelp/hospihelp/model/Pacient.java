package com.hospihelp.hospihelp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "pacienti")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pacient {

    @Id
    @Column(name = "CNP", length = 13, nullable = false)
    @NotBlank(message = "CNP-ul este obligatoriu")
    @Size(min = 13, max = 13, message = "CNP-ul trebuie sa aiba exact 13 caractere")
    @Pattern(regexp = "^[0-9]{13}$", message = "CNP-ul trebuie sa contina doar cifre")
    private String cnp;

    @NotBlank(message = "Numele este obligatoriu")
    @Column(name = "nume", nullable = false, length = 100)
    private String nume;

    @NotBlank(message = "Prenumele este obligatoriu")
    @Column(name = "prenume", nullable = false, length = 100)
    private String prenume;

    @NotBlank(message = "Judetul este obligatoriu")
    @Column(name = "judet", nullable = false, length = 50)
    private String judet;

    @NotBlank(message = "Localitatea este obligatorie")
    @Column(name = "localitate", nullable = false, length = 100)
    private String localitate;

    @NotBlank(message = "Strada este obligatorie")
    @Column(name = "strada", nullable = false, length = 150)
    private String strada;

    @NotBlank(message = "Numarul strazii este obligatoriu")
    @Column(name = "nr_strada", nullable = false, length = 10)
    private String nrStrada;

    @Column(name = "scara", length = 10)
    private String scara;

    @Column(name = "apartament", length = 10)
    private String apartament;

    @NotBlank(message = "Telefonul este obligatoriu")
    @Pattern(regexp = "^[0-9]{10}$", message = "Telefonul trebuie sa contina exact 10 cifre")
    @Column(name = "telefon", nullable = false, length = 15)
    private String telefon;

    @NotBlank(message = "Email-ul este obligatoriu")
    @Email(message = "Email-ul nu este valid")
    @Column(name = "email", nullable = false, length = 150)
    private String email;

    @Column(name = "profesie", length = 100)
    private String profesie;

    @Column(name = "loc_de_munca", length = 150)
    private String locDeMunca;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pat", referencedColumnName = "id_pat")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Pat pat;
}