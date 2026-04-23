package com.hospihelp.hospihelp.service;

import com.hospihelp.hospihelp.model.Medicament;
import com.hospihelp.hospihelp.model.Pacient;
import com.hospihelp.hospihelp.model.Prescriptie;
import com.hospihelp.hospihelp.repository.PrescriptieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PrescriptieService {

    private final PrescriptieRepository prescriptieRepository;
    private final PacientService pacientService;
    private final MedicamentService medicamentService;

    public List<Prescriptie> getToatePrescriptiile() {
        return prescriptieRepository.findAll();
    }

    public Prescriptie getPrescriptieById(Integer id) {
        return prescriptieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prescriptia cu id-ul " + id + " nu a fost gasita"));
    }

    public List<Prescriptie> getPrescriptiiPacient(String cnp) {
        return prescriptieRepository.findByPacientCnp(cnp);
    }

    public Prescriptie adaugaPrescriptie(String cnp, Integer idMedicament, Integer cantitate) {
        Pacient pacient = pacientService.getPacientByCnp(cnp);
        Medicament medicament = medicamentService.getMedicamentById(idMedicament);

        Prescriptie prescriptie = new Prescriptie();
        prescriptie.setPacient(pacient);
        prescriptie.setMedicament(medicament);
        prescriptie.setCantitate(cantitate);

        return prescriptieRepository.save(prescriptie);
    }

    public Prescriptie actualizeazaPrescriptie(Integer id, Integer cantitateNoua) {
        Prescriptie prescriptie = getPrescriptieById(id);
        prescriptie.setCantitate(cantitateNoua);
        return prescriptieRepository.save(prescriptie);
    }

    public void stergePrescriptie(Integer id) {
        if (!prescriptieRepository.existsById(id)) {
            throw new RuntimeException("Prescriptia cu id-ul " + id + " nu exista");
        }
        prescriptieRepository.deleteById(id);
    }
}