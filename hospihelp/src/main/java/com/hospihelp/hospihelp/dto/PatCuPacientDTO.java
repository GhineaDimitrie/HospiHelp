package com.hospihelp.hospihelp.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
public class PatCuPacientDTO {
    private Integer idPat;
    private Integer nrSalon;
    private Boolean ocupat;
    private Map<String, String> pacient;

    public PatCuPacientDTO(Integer idPat, Integer nrSalon, Boolean ocupat,
                           String cnp, String nume, String prenume) {
        this.idPat = idPat;
        this.nrSalon = nrSalon;
        this.ocupat = ocupat;
        if (nume != null) {
            this.pacient = new HashMap<>();
            this.pacient.put("cnp", cnp);
            this.pacient.put("nume", nume);
            this.pacient.put("prenume", prenume);
        } else {
            this.pacient = null;
        }
    }
}