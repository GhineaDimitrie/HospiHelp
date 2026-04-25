package com.hospihelp.hospihelp.controller;

import com.hospihelp.hospihelp.model.Pacient;
import com.hospihelp.hospihelp.service.PacientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/pacienti")
@RequiredArgsConstructor
public class PacientController {

    private final PacientService pacientService;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<Pacient>> getTotiPacientii() {
        return ResponseEntity.ok(pacientService.getTotiPacientii());
    }

    @GetMapping("/{cnp}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Pacient> getPacientByCnp(@PathVariable String cnp) {
        return ResponseEntity.ok(pacientService.getPacientByCnp(cnp));
    }

    @GetMapping("/cauta")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<Pacient>> cautaDupaNume(
            @RequestParam String termen) {
        return ResponseEntity.ok(pacientService.cautaDupaNume(termen));
    }

    @GetMapping("/salon/{nrSalon}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<Pacient>> getPacientiDinSalon(
            @PathVariable Integer nrSalon) {
        return ResponseEntity.ok(pacientService.getPacientiDinSalon(nrSalon));
    }

    @PostMapping("/{idPat}")
    @PreAuthorize("hasAnyRole('MEDIC', 'RECEPTIONIST', 'ADMIN')")
    public ResponseEntity<Pacient> adaugaPacient(
            @Valid @RequestBody Pacient pacient,
            @PathVariable Integer idPat) {
        return ResponseEntity.ok(pacientService.adaugaPacient(pacient, idPat));
    }

    @PutMapping("/{cnp}/muta/{idPatNou}")
    @PreAuthorize("hasAnyRole('MEDIC', 'RECEPTIONIST', 'ADMIN')")
    public ResponseEntity<Pacient> mutaPacient(
            @PathVariable String cnp,
            @PathVariable Integer idPatNou) {
        return ResponseEntity.ok(pacientService.mutaPacient(cnp, idPatNou));
    }

    @DeleteMapping("/{cnp}")
    @PreAuthorize("hasAnyRole('MEDIC', 'RECEPTIONIST', 'ADMIN')")
    public ResponseEntity<Void> stergePacient(@PathVariable String cnp) {
        pacientService.stergePacient(cnp);
        return ResponseEntity.noContent().build();
    }
}