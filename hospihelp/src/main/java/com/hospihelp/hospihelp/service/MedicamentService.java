package com.hospihelp.hospihelp.service;

import com.hospihelp.hospihelp.model.Medicament;
import com.hospihelp.hospihelp.repository.MedicamentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MedicamentService {

    private final MedicamentRepository medicamentRepository;

    public List<Medicament> getToateMedicamentele() {
        return medicamentRepository.findAll();
    }

    public Medicament getMedicamentById(Integer id) {
        return medicamentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Medicamentul cu id-ul " + id + " nu a fost gasit"));
    }

    public List<Medicament> cautaDupaDenumire(String denumire) {
        return medicamentRepository.findByDenumireContainingIgnoreCase(denumire);
    }

    public List<Medicament> getMedicamenteSubStocCritic(Integer stocCritic) {
        return medicamentRepository.findByStocLessThanEqual(stocCritic);
    }

    public Medicament adaugaMedicament(Medicament medicament) {
        return medicamentRepository.save(medicament);
    }

    public Medicament actualizeazaMedicament(Integer id, Medicament medicamentNou) {
        Medicament existent = getMedicamentById(id);
        existent.setDenumire(medicamentNou.getDenumire());
        existent.setStoc(medicamentNou.getStoc());
        return medicamentRepository.save(existent);
    }

    @Transactional
    public void scadeStoc(Integer idMedicament, Integer cantitate) {
        Medicament medicament = getMedicamentById(idMedicament);
        if (medicament.getStoc() < cantitate) {
            throw new RuntimeException("Stoc insuficient pentru " + medicament.getDenumire()
                    + ". Stoc disponibil: " + medicament.getStoc());
        }
        int rowsUpdated = medicamentRepository.scadeStoc(idMedicament, cantitate);
        if (rowsUpdated == 0) {
            throw new RuntimeException("Scaderea stocului a esuat");
        }
    }

    public void stergeMedicament(Integer id) {
        if (!medicamentRepository.existsById(id)) {
            throw new RuntimeException("Medicamentul cu id-ul " + id + " nu exista");
        }
        medicamentRepository.deleteById(id);
    }
}