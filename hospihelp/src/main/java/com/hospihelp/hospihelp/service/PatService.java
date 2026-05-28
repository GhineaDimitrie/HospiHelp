package com.hospihelp.hospihelp.service;

import com.hospihelp.hospihelp.dto.PatCuPacientDTO;
import com.hospihelp.hospihelp.model.Pacient;
import com.hospihelp.hospihelp.model.Pat;
import com.hospihelp.hospihelp.repository.PacientRepository;
import com.hospihelp.hospihelp.repository.PatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PatService {

    private final PatRepository patRepository;
    private final PacientRepository pacientRepository;

    public List<Pat> getToatePaturile() {
        return patRepository.findAll();
    }

    public Pat getPatById(Integer id) {
        return patRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Patul cu id-ul " + id + " nu a fost gasit"));
    }

    public List<Pat> getPaturiDinSalon(Integer nrSalon) {
        return patRepository.findByNrSalon(nrSalon);
    }

    public List<Pat> getPaturiLibere() {
        return patRepository.findByOcupat(false);
    }

    public List<Pat> getPaturiLibereDinSalon(Integer nrSalon) {
        return patRepository.findByNrSalonAndOcupat(nrSalon, false);
    }

    public Pat adaugaPat(Pat pat) {
        pat.setOcupat(false);
        return patRepository.save(pat);
    }

    public Pat marcheazaOcupat(Integer id) {
        Pat pat = getPatById(id);
        pat.setOcupat(true);
        return patRepository.save(pat);
    }

    public Pat marcheazaLiber(Integer id) {
        Pat pat = getPatById(id);
        pat.setOcupat(false);
        return patRepository.save(pat);
    }

    public void stergePat(Integer id) {
        if (!patRepository.existsById(id)) {
            throw new RuntimeException(
                    "Patul cu id-ul " + id + " nu exista");
        }
        patRepository.deleteById(id);
    }

    // ─── Endpoint special cu pacienti inclusi ────────────────────────────────
    public List<PatCuPacientDTO> getPaturiCuPacienti() {
        List<Pat> toatePaturile = patRepository.findAll();
        List<Pacient> totiPacientii = pacientRepository.findAll();

        // Map idPat -> Pacient pentru lookup rapid
        Map<Integer, Pacient> patToPacient = totiPacientii.stream()
                .filter(p -> p.getPat() != null)
                .collect(Collectors.toMap(
                        p -> p.getPat().getIdPat(),
                        p -> p,
                        (p1, p2) -> p1 // daca sunt mai multi pacienti pe pat, ia primul
                ));

        return toatePaturile.stream()
                .map(pat -> {
                    Pacient pacient = patToPacient.get(pat.getIdPat());
                    return new PatCuPacientDTO(
                            pat.getIdPat(),
                            pat.getNrSalon(),
                            pat.getOcupat(),
                            pacient != null ? pacient.getCnp() : null,
                            pacient != null ? pacient.getNume() : null,
                            pacient != null ? pacient.getPrenume() : null
                    );
                })
                .toList();
    }
}