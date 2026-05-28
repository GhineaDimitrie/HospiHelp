package ro.sn.frontend.service;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;
import java.util.Map;

@Service
public class BackendService {

    private final RestTemplate restTemplate;

    @Value("${backend.url}")
    private String backendUrl;

    public BackendService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // ─── Preia tokenul din sesiunea HTTP ─────────────────────────────────────
    private String getToken() {
        try {
            ServletRequestAttributes attrs =
                    (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attrs != null) {
                HttpSession session = attrs.getRequest().getSession(false);
                if (session != null) {
                    String token = (String) session.getAttribute("jwt_token");
                    if (token != null) {
                        System.out.println("Token preluat din sesiune OK");
                        return token;
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Eroare la preluarea tokenului: " + e.getMessage());
        }
        System.out.println("ATENTIE: Token negasit in sesiune!");
        return null;
    }

    // ─── Construieste headerele cu tokenul ───────────────────────────────────
    private HttpHeaders buildHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String token = getToken();
        if (token != null) {
            headers.set("Authorization", "Bearer " + token);
        }
        return headers;
    }

    // ─── Metoda generica GET ──────────────────────────────────────────────────
    private <T> T get(String path, ParameterizedTypeReference<T> type) {
        try {
            HttpEntity<Void> entity = new HttpEntity<>(buildHeaders());
            ResponseEntity<T> response = restTemplate.exchange(
                    backendUrl + path, HttpMethod.GET, entity, type
            );
            System.out.println("GET " + path + " → " + response.getStatusCode());
            return response.getBody();
        } catch (org.springframework.web.client.HttpClientErrorException e) {
            System.err.println("EROARE CLIENT GET " + path
                    + " → " + e.getStatusCode()
                    + " → " + e.getResponseBodyAsString());
            return null;
        } catch (org.springframework.web.client.HttpServerErrorException e) {
            System.err.println("EROARE SERVER GET " + path
                    + " → " + e.getStatusCode()
                    + " → " + e.getResponseBodyAsString());
            return null;
        } catch (Exception e) {
            System.err.println("EROARE GENERALA GET " + path + " → " + e.getMessage());
            return null;
        }
    }

    // ─── Pacienti ─────────────────────────────────────────────────────────────
    public List<Map<String, Object>> getPacienti() {
        return get("/api/pacienti",
                new ParameterizedTypeReference<>() {});
    }

    public long getNrPacienti() {
        List<Map<String, Object>> list = getPacienti();
        return list != null ? list.size() : 0;
    }

    // ─── Prescriptii ──────────────────────────────────────────────────────────
    public List<Map<String, Object>> getPrescriptii() {
        return get("/api/prescriptii",
                new ParameterizedTypeReference<>() {});
    }

    public long getNrPrescriptii() {
        List<Map<String, Object>> list = getPrescriptii();
        return list != null ? list.size() : 0;
    }

    // ─── Alarme ───────────────────────────────────────────────────────────────
    public List<Map<String, Object>> getAlarmeNerezolvate() {
        return get("/api/alarme/nerezolvate",
                new ParameterizedTypeReference<>() {});
    }

    public long getNrAlarme() {
        List<Map<String, Object>> list = getAlarmeNerezolvate();
        return list != null ? list.size() : 0;
    }

    // ─── Comenzi ──────────────────────────────────────────────────────────────
    public List<Map<String, Object>> getComenziActive() {
        return get("/api/comenzi/status/ACTIV",
                new ParameterizedTypeReference<>() {});
    }

    public List<Map<String, Object>> getComenziInAsteptare() {
        return get("/api/comenzi/status/IN_ASTEPTARE",
                new ParameterizedTypeReference<>() {});
    }

    public List<Map<String, Object>> getToateComenzi() {
        return get("/api/comenzi",
                new ParameterizedTypeReference<>() {});
    }

    // ─── Medicamente ──────────────────────────────────────────────────────────
    public List<Map<String, Object>> getMedicamente() {
        return get("/api/medicamente",
                new ParameterizedTypeReference<>() {});
    }

    public long getNrMedicamente() {
        List<Map<String, Object>> list = getMedicamente();
        return list != null ? list.size() : 0;
    }

    public long getNrStocCritic() {
        List<Map<String, Object>> list = get(
                "/api/medicamente/stoc-critic?stocMinim=10",
                new ParameterizedTypeReference<>() {});
        return list != null ? list.size() : 0;
    }

    // ─── Paturi ───────────────────────────────────────────────────────────────
    public long getNrPaturiOcupate() {
        List<Map<String, Object>> toate = get("/api/paturi",
                new ParameterizedTypeReference<>() {});
        if (toate == null) return 0;
        return toate.stream()
                .filter(p -> Boolean.TRUE.equals(p.get("ocupat")))
                .count();
    }

    public long getNrTotalPaturi() {
        List<Map<String, Object>> toate = get("/api/paturi",
                new ParameterizedTypeReference<>() {});
        return toate != null ? toate.size() : 0;
    }

    // ─── Angajati ─────────────────────────────────────────────────────────────
    public List<Map<String, Object>> getAngajati() {
        return get("/api/angajati",
                new ParameterizedTypeReference<>() {});
    }

    public long getNrAngajati() {
        List<Map<String, Object>> list = getAngajati();
        return list != null ? list.size() : 0;
    }

    // ─── Noduri harta ─────────────────────────────────────────────────────────
    public long getNrNoduriHarta() {
        List<Map<String, Object>> list = get("/api/noduri-harta",
                new ParameterizedTypeReference<>() {});
        return list != null ? list.size() : 0;
    }

    // ─── Status robot ─────────────────────────────────────────────────────────
    public String getStatusRobot() {
        List<Map<String, Object>> active = getComenziActive();
        if (active != null && !active.isEmpty()) return "În transport";
        List<Map<String, Object>> asteptare = getComenziInAsteptare();
        if (asteptare != null && !asteptare.isEmpty()) return "În așteptare";
        return "Inactiv";
    }

    public String getUltimaComanda() {
        List<Map<String, Object>> toate = getToateComenzi();
        if (toate == null || toate.isEmpty()) return "—";
        Map<String, Object> ultima = toate.get(toate.size() - 1);
        return "#" + ultima.get("idComanda");
    }

    // ─── Timeline ─────────────────────────────────────────────────────────────
    public List<String> getTimelineComenzi() {
        List<Map<String, Object>> comenzi = getToateComenzi();
        if (comenzi == null || comenzi.isEmpty()) {
            return List.of("Nu există activitate recentă");
        }
        return comenzi.stream()
                .limit(4)
                .map(c -> "Comanda #" + c.get("idComanda")
                        + " — " + formatStatus((String) c.get("status")))
                .toList();
    }

    private String formatStatus(String status) {
        if (status == null) return "necunoscut";
        return switch (status) {
            case "IN_ASTEPTARE" -> "În așteptare";
            case "ACTIV"        -> "În curs de transport";
            case "FINALIZAT"    -> "Finalizată";
            case "NEFINALIZAT"  -> "Nefinalizată";
            default             -> status;
        };
    }




    // ─── Metoda generica POST ─────────────────────────────────────────────────
    private <T> T post(String path, Object body, Class<T> responseType) {
        try {
            HttpHeaders headers = buildHeaders();
            HttpEntity<Object> entity = new HttpEntity<>(body, headers);
            ResponseEntity<T> response = restTemplate.exchange(
                    backendUrl + path, HttpMethod.POST, entity, responseType
            );
            System.out.println("POST " + path + " → " + response.getStatusCode());
            return response.getBody();
        } catch (org.springframework.web.client.HttpClientErrorException e) {
            System.err.println("EROARE POST " + path
                    + " → " + e.getStatusCode()
                    + " → " + e.getResponseBodyAsString());
            return null;
        } catch (org.springframework.web.client.HttpServerErrorException e) {
            System.err.println("EROARE SERVER POST " + path
                    + " → " + e.getStatusCode()
                    + " → " + e.getResponseBodyAsString());
            return null;
        } catch (Exception e) {
            System.err.println("EROARE GENERALA POST " + path
                    + " → " + e.getMessage());
            return null;
        }
    }

    // ─── Metoda generica PUT ──────────────────────────────────────────────────
    private <T> T put(String path, Object body, Class<T> responseType) {
        try {
            HttpHeaders headers = buildHeaders();
            HttpEntity<Object> entity = new HttpEntity<>(body, headers);
            ResponseEntity<T> response = restTemplate.exchange(
                    backendUrl + path, HttpMethod.PUT, entity, responseType
            );
            System.out.println("PUT " + path + " → " + response.getStatusCode());
            return response.getBody();
        } catch (org.springframework.web.client.HttpClientErrorException e) {
            System.err.println("EROARE PUT " + path + " → " + e.getStatusCode()
                    + " → " + e.getResponseBodyAsString());
            return null;
        } catch (Exception e) {
            System.err.println("EROARE PUT " + path + " → " + e.getMessage());
            return null;
        }
    }

    // ─── Metoda generica DELETE ───────────────────────────────────────────────
    private void delete(String path) {
        try {
            HttpHeaders headers = buildHeaders();
            HttpEntity<Void> entity = new HttpEntity<>(headers);
            restTemplate.exchange(backendUrl + path, HttpMethod.DELETE, entity, Void.class);
            System.out.println("DELETE " + path + " → OK");
        } catch (Exception e) {
            System.err.println("EROARE DELETE " + path + " → " + e.getMessage());
        }
    }

    // ─── Angajati ─────────────────────────────────────────────────────────────
    public Map<String, Object> creeazaAngajat(String nume, String prenume,
                                              String email, String parola, String rol) {
        Map<String, Object> body = new java.util.HashMap<>();
        body.put("nume", nume);
        body.put("prenume", prenume);
        body.put("email", email);
        body.put("parolaCriptata", parola);
        body.put("rol", rol.toUpperCase());
        System.out.println("=== JSON TRIMIS: ===");
        System.out.println("nume: '" + nume + "'");
        System.out.println("prenume: '" + prenume + "'");
        System.out.println("email: '" + email + "'");
        System.out.println("parola lungime: " + (parola != null ? parola.length() : "NULL"));
        System.out.println("rol: '" + rol + "'");
        return post("/api/angajati", body, Map.class);
    }

    // În BackendService.java, înlocuiește getSaloane() cu aceasta:
    public List<Map<String, Object>> getPaturiCuPacienti() {
        return get("/api/paturi/cu-pacienti",
                new ParameterizedTypeReference<>() {});
    }

    public Map<Integer, List<Map<String, Object>>> getPaturiGrupatePerSalon() {
        List<Map<String, Object>> paturi = getPaturiCuPacienti();
        if (paturi == null || paturi.isEmpty()) {
            return new java.util.TreeMap<>();
        }
        return paturi.stream()
                .collect(java.util.stream.Collectors.groupingBy(
                        pat -> (Integer) pat.get("nrSalon"),
                        java.util.TreeMap::new,
                        java.util.stream.Collectors.toList()
                ));
    }

    public Map<String, Object> stergeAngajat(Integer id) {
        delete("/api/angajati/" + id);
        return Map.of("success", true);
    }

    // ─── Pacienti ─────────────────────────────────────────────────────────────
    public Map<String, Object> creeazaPacient(Map<String, Object> pacientData,
                                              Integer idPat) {
        return post("/api/pacienti/" + idPat, pacientData, Map.class);
    }

    public Map<String, Object> mutaPacient(String cnp, Integer idPatNou) {
        return put("/api/pacienti/" + cnp + "/muta/" + idPatNou, null, Map.class);
    }

    // ─── Medicamente ──────────────────────────────────────────────────────────
    public Map<String, Object> adaugaMedicament(String denumire, Integer stoc) {
        Map<String, Object> body = Map.of("denumire", denumire, "stoc", stoc);
        return post("/api/medicamente", body, Map.class);
    }

    public Map<String, Object> actualizeazaMedicament(Integer id,
                                                      String denumire, Integer stoc) {
        Map<String, Object> body = Map.of("denumire", denumire, "stoc", stoc);
        return put("/api/medicamente/" + id, body, Map.class);
    }

    // ─── Prescriptii ──────────────────────────────────────────────────────────
    public Map<String, Object> adaugaPrescriptie(String cnp,
                                                 Integer idMedicament, Integer cantitate) {
        Map<String, Object> body = new java.util.HashMap<>();
        body.put("cnp", cnp);
        body.put("idMedicament", idMedicament);
        body.put("cantitate", cantitate);
        return post("/api/prescriptii", body, Map.class);
    }

    // ─── Comenzi ──────────────────────────────────────────────────────────────
    public Map<String, Object> creeazaComanda(Integer idAngajat,
                                              Integer idPrescriptie, Integer idPat) {
        Map<String, Object> body = new java.util.HashMap<>();
        body.put("idAngajat", idAngajat);
        body.put("idPrescriptie", idPrescriptie);
        body.put("idPat", idPat);
        return post("/api/comenzi", body, Map.class);
    }

    public List<Map<String, Object>> getPaturiLibere() {
        return get("/api/paturi/libere",
                new ParameterizedTypeReference<>() {});
    }

    public Map<String, Object> actualizeazaStatusComanda(Integer id, String status) {
        Map<String, Object> body = Map.of("status", status);
        return put("/api/comenzi/" + id + "/status", body, Map.class);
    }

    // ─── Alarme ───────────────────────────────────────────────────────────────
    public Map<String, Object> rezolvaAlarma(Integer id) {
        return put("/api/alarme/" + id + "/rezolva", null, Map.class);
    }


}