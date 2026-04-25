package com.hospihelp.hospihelp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @NotBlank(message = "Denumirea medicamentului este obligatorie")
    @Column(name = "denumire", nullable = false, length = 200)
    private String denumire;

    @NotNull(message = "Stocul este obligatoriu")
    @Min(value = 0, message = "Stocul nu poate fi negativ")
    @Column(name = "stoc", nullable = false)
    private Integer stoc;
}