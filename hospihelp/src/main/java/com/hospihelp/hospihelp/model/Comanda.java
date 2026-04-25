package com.hospihelp.hospihelp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hospihelp.hospihelp.model.enums.StatusComanda;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "comenzi")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comanda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_comanda")
    private Integer idComanda;

    @Column(name = "ora", nullable = false)
    private LocalTime ora;

    @Column(name = "data", nullable = false)
    private LocalDate data;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_angajat", referencedColumnName = "id_angajat", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "parolaCriptata"})
    private Angajat angajat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pat", referencedColumnName = "id_pat", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Pat pat;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private StatusComanda status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_prescriptie", referencedColumnName = "id_prescriptie", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Prescriptie prescriptie;
}