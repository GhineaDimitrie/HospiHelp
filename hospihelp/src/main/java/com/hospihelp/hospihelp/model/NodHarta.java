package com.hospihelp.hospihelp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hospihelp.hospihelp.model.enums.TipLocatie;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "noduri_harta")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NodHarta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_nod")
    private Integer idNod;

    @Column(name = "cod_rfid", nullable = false, length = 50)
    private String codRfid;

    @Enumerated(EnumType.STRING)
    @Column(name = "tip_locatie", nullable = false)
    private TipLocatie tipLocatie;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pat", referencedColumnName = "id_pat")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Pat pat;
}