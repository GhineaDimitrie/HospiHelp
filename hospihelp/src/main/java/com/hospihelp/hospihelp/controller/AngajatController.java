package com.hospihelp.hospihelp.controller;

import com.hospihelp.hospihelp.model.Angajat;
import com.hospihelp.hospihelp.model.enums.RolAngajat;
import com.hospihelp.hospihelp.service.AngajatService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.hospihelp.hospihelp.dto.AngajatRequest;
import java.util.List;

@RestController
@RequestMapping("/api/angajati")
@RequiredArgsConstructor
public class AngajatController {

    private final AngajatService angajatService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Angajat>> getToiAngajatii() {
        return ResponseEntity.ok(angajatService.getToiAngajatii());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Angajat> getAngajatById(@PathVariable Integer id) {
        return ResponseEntity.ok(angajatService.getAngajatById(id));
    }

    @GetMapping("/rol/{rol}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Angajat>> getAngajatiDupaRol(@PathVariable RolAngajat rol) {
        return ResponseEntity.ok(angajatService.getAngajatiDupaRol(rol));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Angajat> creeazaAngajat(
            @Valid @RequestBody AngajatRequest request) {

        Angajat angajat = new Angajat();
        angajat.setNume(request.getNume());
        angajat.setPrenume(request.getPrenume());
        angajat.setEmail(request.getEmail());
        angajat.setParolaCriptata(request.getParolaCriptata());
        angajat.setRol(request.getRol());

        return ResponseEntity.ok(angajatService.creeazaAngajat(angajat));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Angajat> actualizeazaAngajat(
            @PathVariable Integer id,
            @Valid @RequestBody Angajat angajat) {
        return ResponseEntity.ok(angajatService.actualizeazaAngajat(id, angajat));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> stergeAngajat(@PathVariable Integer id) {
        angajatService.stergeAngajat(id);
        return ResponseEntity.noContent().build();
    }
}