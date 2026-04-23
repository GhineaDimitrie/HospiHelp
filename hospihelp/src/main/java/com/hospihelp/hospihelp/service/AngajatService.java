package com.hospihelp.hospihelp.service;

import com.hospihelp.hospihelp.model.Angajat;
import com.hospihelp.hospihelp.model.enums.RolAngajat;
import com.hospihelp.hospihelp.repository.AngajatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AngajatService {

    private final AngajatRepository angajatRepository;
    private final PasswordEncoder passwordEncoder;

    public List<Angajat> getToiAngajatii() {
        return angajatRepository.findAll();
    }

    public Angajat getAngajatById(Integer id) {
        return angajatRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Angajatul cu id-ul " + id + " nu a fost gasit"));
    }

    public Angajat getAngajatByEmail(String email) {
        return angajatRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Angajatul cu emailul " + email + " nu a fost gasit"));
    }

    public Angajat creeazaAngajat(Angajat angajat) {
        if (angajatRepository.existsByEmail(angajat.getEmail())) {
            throw new RuntimeException("Exista deja un angajat cu emailul " + angajat.getEmail());
        }
        angajat.setParolaCriptata(passwordEncoder.encode(angajat.getParolaCriptata()));
        return angajatRepository.save(angajat);
    }

    public Angajat actualizeazaAngajat(Integer id, Angajat angajatNou) {
        Angajat angajatExistent = getAngajatById(id);
        angajatExistent.setNume(angajatNou.getNume());
        angajatExistent.setPrenume(angajatNou.getPrenume());
        angajatExistent.setEmail(angajatNou.getEmail());
        angajatExistent.setRol(angajatNou.getRol());
        return angajatRepository.save(angajatExistent);
    }

    public void stergeAngajat(Integer id) {
        if (!angajatRepository.existsById(id)) {
            throw new RuntimeException("Angajatul cu id-ul " + id + " nu exista");
        }
        angajatRepository.deleteById(id);
    }

    public List<Angajat> getAngajatiDupaRol(RolAngajat rol) {
        return angajatRepository.findByRol(rol);
    }

    public void schimbaParola(Integer id, String parolaVeche, String parolaNoua) {
        Angajat angajat = getAngajatById(id);
        if (!passwordEncoder.matches(parolaVeche, angajat.getParolaCriptata())) {
            throw new RuntimeException("Parola veche este incorecta");
        }
        angajat.setParolaCriptata(passwordEncoder.encode(parolaNoua));
        angajatRepository.save(angajat);
    }
}