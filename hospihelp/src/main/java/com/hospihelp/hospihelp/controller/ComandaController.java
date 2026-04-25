package com.hospihelp.hospihelp.controller;

import com.hospihelp.hospihelp.model.Comanda;
import com.hospihelp.hospihelp.model.enums.StatusComanda;
import com.hospihelp.hospihelp.service.ComandaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/comenzi")
@RequiredArgsConstructor
public class ComandaController {

    private final ComandaService comandaService;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<Comanda>> getToateComenzi() {
        return ResponseEntity.ok(comandaService.getToateComenzi());
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Comanda> getComandaById(@PathVariable Integer id) {
        return ResponseEntity.ok(comandaService.getComandaById(id));
    }

    @GetMapping("/status/{status}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<Comanda>> getComenziDupaStatus(
            @PathVariable StatusComanda status) {
        return ResponseEntity.ok(comandaService.getComenziDupaStatus(status));
    }

    @GetMapping("/angajat/{idAngajat}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<Comanda>> getComenziAngajat(
            @PathVariable Integer idAngajat) {
        return ResponseEntity.ok(comandaService.getComenziAngajat(idAngajat));
    }

    @GetMapping("/pacient/{cnp}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<Comanda>> getComenziPacient(@PathVariable String cnp) {
        return ResponseEntity.ok(comandaService.getComenziPacient(cnp));
    }

    @GetMapping("/salon/{nrSalon}/active")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<Comanda>> getComenziActiveSalon(
            @PathVariable Integer nrSalon) {
        return ResponseEntity.ok(comandaService.getComenziActiveSalon(nrSalon));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('MEDIC', 'FARMACIST', 'ADMIN')")
    public ResponseEntity<Comanda> creeazaComanda(
            @RequestBody Map<String, Integer> body) {
        return ResponseEntity.ok(comandaService.creeazaComanda(
                body.get("idAngajat"),
                body.get("idPrescriptie"),
                body.get("idPat")
        ));
    }

    @PutMapping("/{id}/status")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Comanda> actualizeazaStatus(
            @PathVariable Integer id,
            @RequestBody Map<String, String> body) {
        StatusComanda statusNou = StatusComanda.valueOf(body.get("status"));
        return ResponseEntity.ok(comandaService.actualizeazaStatus(id, statusNou));
    }
}