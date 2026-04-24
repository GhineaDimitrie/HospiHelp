package com.hospihelp.hospihelp.controller;

import com.hospihelp.hospihelp.model.Prescriptie;
import com.hospihelp.hospihelp.service.PrescriptieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/prescriptii")
@RequiredArgsConstructor
public class PrescriptieController {

    private final PrescriptieService prescriptieService;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<Prescriptie>> getToatePrescriptiile() {
        return ResponseEntity.ok(prescriptieService.getToatePrescriptiile());
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Prescriptie> getPrescriptieById(@PathVariable Integer id) {
        return ResponseEntity.ok(prescriptieService.getPrescriptieById(id));
    }

    @GetMapping("/pacient/{cnp}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<Prescriptie>> getPrescriptiiPacient(
            @PathVariable String cnp) {
        return ResponseEntity.ok(prescriptieService.getPrescriptiiPacient(cnp));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('MEDIC', 'ADMIN')")
    public ResponseEntity<Prescriptie> adaugaPrescriptie(
            @RequestBody Map<String, Object> body) {
        String cnp = (String) body.get("cnp");
        Integer idMedicament = (Integer) body.get("idMedicament");
        Integer cantitate = (Integer) body.get("cantitate");
        return ResponseEntity.ok(
                prescriptieService.adaugaPrescriptie(cnp, idMedicament, cantitate));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('MEDIC', 'ADMIN')")
    public ResponseEntity<Prescriptie> actualizeazaPrescriptie(
            @PathVariable Integer id,
            @RequestBody Map<String, Integer> body) {
        return ResponseEntity.ok(
                prescriptieService.actualizeazaPrescriptie(id, body.get("cantitate")));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('MEDIC', 'ADMIN')")
    public ResponseEntity<Void> stergePrescriptie(@PathVariable Integer id) {
        prescriptieService.stergePrescriptie(id);
        return ResponseEntity.noContent().build();
    }
}