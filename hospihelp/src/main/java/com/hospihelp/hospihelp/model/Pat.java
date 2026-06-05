package com.hospihelp.hospihelp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "paturi")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pat")
    private Integer idPat;

    @NotNull(message = "Numarul salonului este obligatoriu")
    @Min(value = 1, message = "Numarul salonului trebuie sa fie cel putin 1")
    @Column(name = "nr_salon", nullable = false)
    private Integer nrSalon;

    @Column(name = "ocupat", nullable = false)
    private Boolean ocupat = false;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cnp_pacient", referencedColumnName = "cnp")

    private Pacient pacient;
}