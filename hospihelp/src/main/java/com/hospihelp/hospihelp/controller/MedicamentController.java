
package com.hospihelp.hospihelp.controller;

import com.hospihelp.hospihelp.model.Medicament;
import com.hospihelp.hospihelp.service.MedicamentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/medicamente")
@RequiredArgsConstructor
public class MedicamentController {

    private final MedicamentService medicamentService;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<Medicament>> getToateMedicamentele() {
        return ResponseEntity.ok(medicamentService.getToateMedicamentele());
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Medicament> getMedicamentById(@PathVariable Integer id) {
        return ResponseEntity.ok(medicamentService.getMedicamentById(id));
    }

    @GetMapping("/cauta")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<Medicament>> cautaDupaDenumire(
            @RequestParam String denumire) {
        return ResponseEntity.ok(medicamentService.cautaDupaDenumire(denumire));
    }

    @GetMapping("/stoc-critic")
    @PreAuthorize("hasAnyRole('FARMACIST', 'ADMIN')")
    public ResponseEntity<List<Medicament>> getMedicamenteStocCritic(
            @RequestParam(defaultValue = "5") Integer stocMinim) {
        return ResponseEntity.ok(medicamentService.getMedicamenteSubStocCritic(stocMinim));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('FARMACIST', 'ADMIN')")
    public ResponseEntity<Medicament> adaugaMedicament(
            @RequestBody Medicament medicament) {
        return ResponseEntity.ok(medicamentService.adaugaMedicament(medicament));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('FARMACIST', 'ADMIN')")
    public ResponseEntity<Medicament> actualizeazaMedicament(
            @PathVariable Integer id,
            @RequestBody Medicament medicament) {
        return ResponseEntity.ok(medicamentService.actualizeazaMedicament(id, medicament));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('FARMACIST', 'ADMIN')")
    public ResponseEntity<Void> stergeMedicament(@PathVariable Integer id) {
        medicamentService.stergeMedicament(id);
        return ResponseEntity.noContent().build();
    }
}