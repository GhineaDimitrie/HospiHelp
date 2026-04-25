package com.hospihelp.hospihelp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "alarme")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Alarma {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_alarma")
    private Integer idAlarma;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_comanda", referencedColumnName = "id_comanda", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "prescriptie", "angajat", "pat"})
    private Comanda comanda;

    @Column(name = "tip", nullable = false, length = 100)
    private String tip;

    @Column(name = "mesaj", length = 500)
    private String mesaj;

    @Column(name = "rezolvata")
    private Boolean rezolvata = false;

    @Column(name = "ora_alarma", nullable = false)
    private LocalDateTime oraAlarma;
}