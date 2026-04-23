package com.hospihelp.hospihelp.service;

import com.hospihelp.hospihelp.model.Pat;
import com.hospihelp.hospihelp.repository.PatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PatService {

    private final PatRepository patRepository;

    public List<Pat> getToatePaturile() {
        return patRepository.findAll();
    }

    public Pat getPatById(Integer id) {
        return patRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Patul cu id-ul " + id + " nu a fost gasit"));
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
            throw new RuntimeException("Patul cu id-ul " + id + " nu exista");
        }
        patRepository.deleteById(id);
    }
}