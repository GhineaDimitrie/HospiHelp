package com.hospihelp.hospihelp.model;


import jakarta.persistence.*;
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
    private Pacient pacient;

    @Column(name = "cantitate", nullable = false)
    private Integer cantitate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_medicament", referencedColumnName = "id_medicament", nullable = false)
    private Medicament medicament;
}