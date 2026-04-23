package com.hospihelp.hospihelp.model;




import com.hospihelp.hospihelp.model.enums.DirectieMers;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "segmente_harta")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SegmentHarta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_segment")
    private Integer idSegment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_nod_start", referencedColumnName = "id_nod", nullable = false)
    private NodHarta nodStart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_nod_stop", referencedColumnName = "id_nod", nullable = false)
    private NodHarta nodStop;

    @Enumerated(EnumType.STRING)
    @Column(name = "directie_mers", nullable = false)
    private DirectieMers directieMers;
}