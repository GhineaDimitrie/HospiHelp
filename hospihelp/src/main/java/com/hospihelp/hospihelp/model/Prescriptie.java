package com.hospihelp.hospihelp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "prescriptii")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Prescriptie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_prescriptie")
    private Integer idPrescriptie;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CNP", referencedColumnName = "CNP", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "pat"})
    @NotNull(message = "Pacientul este obligatoriu")
    private Pacient pacient;

    @NotNull(message = "Cantitatea este obligatorie")
    @Min(value = 1, message = "Cantitatea trebuie sa fie cel putin 1")
    @Column(name = "cantitate", nullable = false)
    private Integer cantitate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_medicament", referencedColumnName = "id_medicament", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @NotNull(message = "Medicamentul este obligatoriu")
    private Medicament medicament;
}