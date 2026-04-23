package com.hospihelp.hospihelp.model;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "medicamente")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Medicament {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_medicament")
    private Integer idMedicament;

    @Column(name = "denumire", nullable = false, length = 200)
    private String denumire;

    @Column(name = "stoc", nullable = false)
    private Integer stoc;
}