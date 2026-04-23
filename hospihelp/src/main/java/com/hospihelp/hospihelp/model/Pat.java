package com.hospihelp.hospihelp.model;



import jakarta.persistence.*;
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

    @Column(name = "nr_salon", nullable = false)
    private Integer nrSalon;

    @Column(name = "ocupat", nullable = false)
    private Boolean ocupat = false;
}
