package com.hospihelp.hospihelp.service;

import com.hospihelp.hospihelp.model.Pacient;
import com.hospihelp.hospihelp.model.Pat;
import com.hospihelp.hospihelp.repository.PacientRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PacientService {

    private final PacientRepository pacientRepository;
    private final PatService patService;

    public List<Pacient> getTotiPacientii() {
        return pacientRepository.findAll();
    }

    public Pacient getPacientByCnp(String cnp) {
        return pacientRepository.findById(cnp)
                .orElseThrow(() -> new RuntimeException("Pacientul cu CNP-ul " + cnp + " nu a fost gasit"));
    }

    public List<Pacient> cautaDupaNume(String termen) {
        return pacientRepository.findByNumeContainingIgnoreCaseOrPrenumeContainingIgnoreCase(termen, termen);
    }

    public List<Pacient> getPacientiDinSalon(Integer nrSalon) {
        return pacientRepository.findByNrSalon(nrSalon);
    }

    @Transactional
    public Pacient adaugaPacient(Pacient pacient, Integer idPat) {
        if (pacientRepository.existsByCnp(pacient.getCnp())) {
            throw new RuntimeException("Exista deja un pacient cu CNP-ul " + pacient.getCnp());
        }
        Pat pat = patService.getPatById(idPat);
        if (pat.getOcupat()) {
            throw new RuntimeException("Patul selectat este deja ocupat");
        }
        pacient.setPat(pat);
        patService.marcheazaOcupat(idPat);
        return pacientRepository.save(pacient);
    }

    @Transactional
    public Pacient mutaPacient(String cnp, Integer idPatNou) {
        Pacient pacient = getPacientByCnp(cnp);
        Pat patNou = patService.getPatById(idPatNou);

        if (patNou.getOcupat()) {
            throw new RuntimeException("Patul destinatie este deja ocupat");
        }

        // Elibereaza patul vechi
        if (pacient.getPat() != null) {
            patService.marcheazaLiber(pacient.getPat().getIdPat());
        }

        // Ocupa patul nou
        pacient.setPat(patNou);
        patService.marcheazaOcupat(idPatNou);
        return pacientRepository.save(pacient);
    }

    @Transactional
    public void stergePacient(String cnp) {
        Pacient pacient = getPacientByCnp(cnp);
        if (pacient.getPat() != null) {
            patService.marcheazaLiber(pacient.getPat().getIdPat());
        }
        pacientRepository.deleteById(cnp);
    }
}