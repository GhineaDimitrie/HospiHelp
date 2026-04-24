package com.hospihelp.hospihelp.controller;

import com.hospihelp.hospihelp.model.Alarma;
import com.hospihelp.hospihelp.service.AlarmaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/alarme")
@RequiredArgsConstructor
public class AlarmaController {

    private final AlarmaService alarmaService;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<Alarma>> getToateAlarmele() {
        return ResponseEntity.ok(alarmaService.getToateAlarmele());
    }

    @GetMapping("/nerezolvate")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<Alarma>> getAlarmeNerezolvate() {
        return ResponseEntity.ok(alarmaService.getAlarmeNerezolvate());
    }

    @GetMapping("/comanda/{idComanda}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<Alarma>> getAlarmeComanda(
            @PathVariable Integer idComanda) {
        return ResponseEntity.ok(alarmaService.getAlarmeComanda(idComanda));
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Alarma> creeazaAlarma(
            @RequestBody Map<String, String> body) {
        Integer idComanda = Integer.parseInt(body.get("idComanda"));
        String tip = body.get("tip");
        String mesaj = body.get("mesaj");
        return ResponseEntity.ok(alarmaService.creeazaAlarma(idComanda, tip, mesaj));
    }

    @PutMapping("/{id}/rezolva")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Alarma> rezolvaAlarma(@PathVariable Integer id) {
        return ResponseEntity.ok(alarmaService.rezolvaAlarma(id));
    }
}