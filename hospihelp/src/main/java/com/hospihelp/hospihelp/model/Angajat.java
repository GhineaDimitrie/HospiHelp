package com.hospihelp.hospihelp.model;
import com.hospihelp.hospihelp.model.enums.RolAngajat;
import jakarta.persistence.*;
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

    @Column(name = "nume", nullable = false, length = 100)
    private String nume;

    @Column(name = "prenume", nullable = false, length = 100)
    private String prenume;

    @Column(name = "parola_criptata", nullable = false)
    private String parolaCriptata;

    @Column(name = "email", nullable = false, unique = true, length = 150)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "rol", nullable = false)
    private RolAngajat rol;
}
