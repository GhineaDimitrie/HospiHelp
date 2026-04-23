package com.hospihelp.hospihelp.model;


import jakarta.persistence.*;
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
    private String cnp;

    @Column(name = "nume", nullable = false, length = 100)
    private String nume;

    @Column(name = "prenume", nullable = false, length = 100)
    private String prenume;

    @Column(name = "judet", nullable = false, length = 50)
    private String judet;

    @Column(name = "localitate", nullable = false, length = 100)
    private String localitate;

    @Column(name = "strada", nullable = false, length = 150)
    private String strada;

    @Column(name = "nr_strada", nullable = false, length = 10)
    private String nrStrada;

    @Column(name = "scara", length = 10)
    private String scara;

    @Column(name = "apartament", length = 10)
    private String apartament;

    @Column(name = "telefon", nullable = false, length = 15)
    private String telefon;

    @Column(name = "email", nullable = false, length = 150)
    private String email;

    @Column(name = "profesie", length = 100)
    private String profesie;

    @Column(name = "loc_de_munca", length = 150)
    private String locDeMunca;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pat", referencedColumnName = "id_pat")
    private Pat pat;
}