package com.hospihelp.hospihelp.controller;

import com.hospihelp.hospihelp.model.Pat;
import com.hospihelp.hospihelp.service.PatService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/paturi")
@RequiredArgsConstructor
public class PatController {

    private final PatService patService;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<Pat>> getToatePaturile() {
        return ResponseEntity.ok(patService.getToatePaturile());
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Pat> getPatById(@PathVariable Integer id) {
        return ResponseEntity.ok(patService.getPatById(id));
    }

    @GetMapping("/salon/{nrSalon}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<Pat>> getPaturiDinSalon(@PathVariable Integer nrSalon) {
        return ResponseEntity.ok(patService.getPaturiDinSalon(nrSalon));
    }

    @GetMapping("/libere")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<Pat>> getPaturiLibere() {
        return ResponseEntity.ok(patService.getPaturiLibere());
    }

    @GetMapping("/salon/{nrSalon}/libere")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<Pat>> getPaturiLibereDinSalon(
            @PathVariable Integer nrSalon) {
        return ResponseEntity.ok(patService.getPaturiLibereDinSalon(nrSalon));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Pat> adaugaPat(@Valid @RequestBody Pat pat) {
        return ResponseEntity.ok(patService.adaugaPat(pat));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> stergePat(@PathVariable Integer id) {
        patService.stergePat(id);
        return ResponseEntity.noContent().build();
    }
}