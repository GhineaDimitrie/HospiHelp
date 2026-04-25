package com.hospihelp.hospihelp.service;

import com.hospihelp.hospihelp.model.*;
import com.hospihelp.hospihelp.model.enums.StatusComanda;
import com.hospihelp.hospihelp.repository.ComandaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ComandaService {

    private final ComandaRepository comandaRepository;
    private final AngajatService angajatService;
    private final PrescriptieService prescriptieService;
    private final PatService patService;
    private final MedicamentService medicamentService;

    public List<Comanda> getToateComenzi() {
        return comandaRepository.findAll();
    }

    public Comanda getComandaById(Integer id) {
        return comandaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comanda cu id-ul " + id + " nu a fost gasita"));
    }

    public List<Comanda> getComenziDupaStatus(StatusComanda status) {
        return comandaRepository.findByStatusOrdonat(status);
    }

    public List<Comanda> getComenziAngajat(Integer idAngajat) {
        return comandaRepository.findByAngajatIdAngajat(idAngajat);
    }

    public List<Comanda> getComenziPacient(String cnp) {
        return comandaRepository.findByPrescriptiePacientCnp(cnp);
    }

    public List<Comanda> getComenziActiveSalon(Integer nrSalon) {
        return comandaRepository.findBySalonSiStatus(nrSalon, StatusComanda.ACTIV);
    }

    @Transactional
    public Comanda creeazaComanda(Integer idAngajat, Integer idPrescriptie, Integer idPat) {
        Angajat angajat = angajatService.getAngajatById(idAngajat);
        Prescriptie prescriptie = prescriptieService.getPrescriptieById(idPrescriptie);
        Pat pat = patService.getPatById(idPat);

        // Verifica stoc inainte de a crea comanda
        Medicament medicament = prescriptie.getMedicament();
        if (medicament.getStoc() < prescriptie.getCantitate()) {
            throw new RuntimeException("Stoc insuficient pentru " + medicament.getDenumire());
        }

        Comanda comanda = new Comanda();
        comanda.setAngajat(angajat);
        comanda.setPrescriptie(prescriptie);
        comanda.setPat(pat);
        comanda.setOra(LocalTime.now());
        comanda.setData(LocalDate.now());
        comanda.setStatus(StatusComanda.IN_ASTEPTARE);

        return comandaRepository.save(comanda);
    }

    @Transactional
    public Comanda actualizeazaStatus(Integer idComanda, StatusComanda statusNou) {
        Comanda comanda = getComandaById(idComanda);
        StatusComanda statusCurent = comanda.getStatus();

        // Masina de stari - tranzitii permise
        boolean tranzitieValida = switch (statusCurent) {
            case IN_ASTEPTARE -> statusNou == StatusComanda.ACTIV
                    || statusNou == StatusComanda.NEFINALIZAT;
            case ACTIV -> statusNou == StatusComanda.FINALIZAT
                    || statusNou == StatusComanda.NEFINALIZAT;
            case FINALIZAT, NEFINALIZAT -> false;
        };

        if (!tranzitieValida) {
            throw new RuntimeException("Tranzitia de la " + statusCurent + " la " + statusNou + " nu este permisa");
        }

        // Scade stocul cand comanda devine ACTIVA
        if (statusNou == StatusComanda.ACTIV) {
            medicamentService.scadeStoc(
                    comanda.getPrescriptie().getMedicament().getIdMedicament(),
                    comanda.getPrescriptie().getCantitate()
            );
        }

        comanda.setStatus(statusNou);
        return comandaRepository.save(comanda);
    }
}