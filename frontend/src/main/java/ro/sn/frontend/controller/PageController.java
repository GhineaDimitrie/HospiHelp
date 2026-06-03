package ro.sn.frontend.controller;

import java.util.List;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ro.sn.frontend.model.UserProfile;
import ro.sn.frontend.service.BackendService;
import ro.sn.frontend.service.UserProfileService;

@Controller
public class PageController {

    private final UserProfileService userProfileService;
    private final BackendService backendService;

    public PageController(UserProfileService userProfileService,
                          BackendService backendService) {
        this.userProfileService = userProfileService;
        this.backendService = backendService;
    }

    @GetMapping("/dashboard")
    public String dashboardRedirect(Authentication authentication) {
        return "redirect:" + userProfileService.dashboardPath(authentication);
    }

    // ─── MEDIC ────────────────────────────────────────────────────────────────
    @GetMapping("/dashboard/medic")
    public String medicDashboard(Model model, Authentication authentication) {
        preparePage(model, authentication, "dashboard",
                "Dashboard", "Panou principal pentru medic");

        model.addAttribute("stats", List.of(
                Map.of("title", "Pacienți activi", "value",
                        String.valueOf(backendService.getNrPacienti()),
                        "caption", "Fișe disponibile", "accent", "blue"),
                Map.of("title", "Prescripții active", "value",
                        String.valueOf(backendService.getNrPrescriptii()),
                        "caption", "Total înregistrate", "accent", "green"),
                Map.of("title", "Alarme robot", "value",
                        String.valueOf(backendService.getNrAlarme()),
                        "caption", "Necesită verificare", "accent", "orange"),
                Map.of("title", "Status robot", "value",
                        backendService.getStatusRobot(),
                        "caption", "Robot 1", "accent", "purple")
        ));
        model.addAttribute("quickLinks", List.of(
                Map.of("name", "Pacienți", "path", "/module/pacienti"),
                Map.of("name", "Prescripții", "path", "/module/prescriptii"),
                Map.of("name", "Status robot", "path", "/module/status-robot"),
                Map.of("name", "Alarme", "path", "/module/alarme"),
                Map.of("name", "Istoric transporturi", "path", "/module/istoric")
        ));
        model.addAttribute("timeline", backendService.getTimelineComenzi());
        return "dashboard";
    }

    @GetMapping("/dashboard/receptionist")
    public String receptionistDashboard(Model model, Authentication authentication) {
        preparePage(model, authentication, "dashboard",
                "Dashboard", "Panou principal pentru recepționist");

        long nrOcupate = backendService.getNrPaturiOcupate();
        long nrTotale = backendService.getNrTotalPaturi();

        model.addAttribute("stats", List.of(
                Map.of("title", "Pacienți înregistrați", "value",
                        String.valueOf(backendService.getNrPacienti()),
                        "caption", "Total pacienți", "accent", "blue"),
                Map.of("title", "Paturi ocupate", "value",
                        String.valueOf(nrOcupate),
                        "caption", "Din " + nrTotale + " disponibile", "accent", "green"),
                Map.of("title", "Paturi libere", "value",
                        String.valueOf(nrTotale - nrOcupate),
                        "caption", "Disponibile acum", "accent", "orange"),
                Map.of("title", "Status recepție", "value",
                        "Activ", "caption", "Toate formularele disponibile", "accent", "purple")
        ));
        model.addAttribute("quickLinks", List.of(
                Map.of("name", "Pacienți", "path", "/module/pacienti"),
                Map.of("name", "Saloane și paturi", "path", "/module/saloane")
        ));
        model.addAttribute("timeline", backendService.getTimelineComenzi());
        return "dashboard";
    }

    @GetMapping("/dashboard/farmacist")
    public String pharmacistDashboard(Model model, Authentication authentication) {
        preparePage(model, authentication, "dashboard",
                "Dashboard", "Panou principal pentru farmacist");

        List<Map<String, Object>> asteptare = backendService.getComenziInAsteptare();
        long nrAsteptare = asteptare != null ? asteptare.size() : 0;

        model.addAttribute("stats", List.of(
                Map.of("title", "Medicamente în stoc", "value",
                        String.valueOf(backendService.getNrMedicamente()),
                        "caption", "Articole active", "accent", "blue"),
                Map.of("title", "Comenzi în așteptare", "value",
                        String.valueOf(nrAsteptare),
                        "caption", "De procesat", "accent", "green"),
                Map.of("title", "Stocuri critice", "value",
                        String.valueOf(backendService.getNrStocCritic()),
                        "caption", "Necesită reaprovizionare", "accent", "orange"),
                Map.of("title", "Ultima comandă", "value",
                        backendService.getUltimaComanda(),
                        "caption", "Robot 1", "accent", "purple")
        ));
        model.addAttribute("quickLinks", List.of(
                Map.of("name", "Stoc medicamente", "path", "/module/medicamente"),
                Map.of("name", "Comenzi robot", "path", "/module/comenzi-robot"),
                Map.of("name", "Istoric transporturi", "path", "/module/istoric")
        ));
        model.addAttribute("timeline", backendService.getTimelineComenzi());
        return "dashboard";
    }

    @GetMapping("/dashboard/administrator")
    public String administratorDashboard(Model model, Authentication authentication) {
        preparePage(model, authentication, "dashboard",
                "Dashboard", "Panou principal pentru administrator");

        model.addAttribute("stats", List.of(
                Map.of("title", "Utilizatori activi", "value",
                        String.valueOf(backendService.getNrAngajati()),
                        "caption", "Conturi în sistem", "accent", "blue"),
                Map.of("title", "Alarme deschise", "value",
                        String.valueOf(backendService.getNrAlarme()),
                        "caption", "Necesită soluționare", "accent", "green"),
                Map.of("title", "Zone pe hartă", "value",
                        String.valueOf(backendService.getNrNoduriHarta()),
                        "caption", "Noduri înregistrate", "accent", "orange"),
                Map.of("title", "Status platformă", "value",
                        "Online", "caption", "Cloud conectat", "accent", "purple")
        ));
        model.addAttribute("quickLinks", List.of(
                Map.of("name", "Utilizatori", "path", "/module/utilizatori"),
                Map.of("name", "Hartă spital", "path", "/module/harta"),
                Map.of("name", "Alarme", "path", "/module/alarme"),
                Map.of("name", "Status robot", "path", "/module/status-robot")
        ));
        model.addAttribute("timeline", backendService.getTimelineComenzi());
        return "dashboard";
    }

    // ─── MODULE ───────────────────────────────────────────────────────────────
    @GetMapping("/module/pacienti")
    public String pacienti(Model model, Authentication authentication) {
        preparePage(model, authentication, "pacienti",
                "Pacienți", "Adăugare, căutare și editare date pacient");
        model.addAttribute("pacienti", backendService.getPacienti());
        model.addAttribute("paturiLibere", backendService.getPaturiLibere());
        return "modules/pacienti";
    }

    @GetMapping("/module/prescriptii")
    public String prescriptii(Model model, Authentication authentication) {
        preparePage(model, authentication, "prescriptii",
                "Prescripții", "Creare și vizualizare prescripții medicale");

        // 1. Datele pentru tabelul de jos (deja funcționează)
        model.addAttribute("prescriptii", backendService.getPrescriptii());

        // 2. DATELE PENTRU DROPDOWN-URI (Asta lipsea!)
        model.addAttribute("pacienti", backendService.getPacienti());
        model.addAttribute("medicamente", backendService.getMedicamente());

        // 3. Datele pentru badge-urile de sumar (ca să nu mai dea eroare de null)
        model.addAttribute("pacientiTratamentNou", new java.util.ArrayList<>());

        return "modules/prescriptii";
    }

    @GetMapping("/module/medicamente")
    public String medicamente(Model model, Authentication authentication) {
        preparePage(model, authentication, "medicamente",
                "Stoc medicamente", "Gestionarea stocurilor din farmacie");
        model.addAttribute("medicamente", backendService.getMedicamente());
        return "modules/medicamente";
    }

    @GetMapping("/module/comenzi-robot")
    public String comenziRobot(Model model, Authentication authentication) {
        preparePage(model, authentication, "comenzi-robot",
                "Comenzi robot", "Controlul livrărilor de medicamente");
        model.addAttribute("comenzi", backendService.getToateComenzi());
        return "modules/comenzi-robot";
    }

    @GetMapping("/module/status-robot")
    public String statusRobot(Model model, Authentication authentication) {
        preparePage(model, authentication, "status-robot",
                "Status robot", "Monitorizare stare și poziție robot");
        model.addAttribute("statusRobot", backendService.getStatusRobot());
        model.addAttribute("ultimaComanda", backendService.getUltimaComanda());
        model.addAttribute("comenziActive", backendService.getComenziActive());
        return "modules/status-robot";
    }

    @GetMapping("/module/alarme")
    public String alarme(Model model, Authentication authentication) {
        preparePage(model, authentication, "alarme",
                "Alarme", "Vizualizare alarme și severitate");
        model.addAttribute("alarme", backendService.getAlarmeNerezolvate());
        return "modules/alarme";
    }

    @GetMapping("/module/istoric")
    public String istoric(Model model, Authentication authentication) {
        preparePage(model, authentication, "istoric",
                "Istoric transporturi", "Livrări realizate de robot");
        List<Map<String, Object>> comenzi = backendService.getToateComenzi();
        model.addAttribute("comenzi", comenzi);
        long nrFinalizate   = comenzi == null ? 0 : comenzi.stream().filter(c -> "FINALIZAT".equals(c.get("status"))).count();
        long nrInAsteptare  = comenzi == null ? 0 : comenzi.stream().filter(c -> "IN_ASTEPTARE".equals(c.get("status"))).count();
        model.addAttribute("totalComenzi",  comenzi == null ? 0 : comenzi.size());
        model.addAttribute("nrFinalizate",  nrFinalizate);
        model.addAttribute("nrInAsteptare", nrInAsteptare);
        return "modules/istoric";
    }
    @GetMapping("/module/saloane")
    public String saloane(Model model, Authentication authentication) {
        preparePage(model, authentication, "saloane",
                "Saloane și paturi",
                "Repartizare pacienți pe saloane și paturi");

        model.addAttribute("saloaneGrupate",
                backendService.getPaturiGrupatePerSalon());
        model.addAttribute("paturiLibere",
                backendService.getPaturiLibere());
        model.addAttribute("pacienti",
                backendService.getPacienti());
        return "modules/saloane";
    }
    @GetMapping("/module/utilizatori")
    public String utilizatori(Model model, Authentication authentication) {
        preparePage(model, authentication, "utilizatori",
                "Utilizatori", "Gestionarea conturilor și rolurilor");
        model.addAttribute("angajati", backendService.getAngajati());
        return "modules/utilizatori";
    }

    // ─── Helper ───────────────────────────────────────────────────────────────
    private void preparePage(Model model, Authentication authentication,
                             String activePage, String title, String subtitle) {
        UserProfile profile = userProfileService.getProfile(authentication);
        model.addAttribute("displayName", profile.displayName());
        model.addAttribute("roleCode", profile.roleCode());
        model.addAttribute("roleLabel", profile.roleLabel());
        model.addAttribute("pageTitle", title);
        model.addAttribute("pageSubtitle", subtitle);
        model.addAttribute("activePage", activePage);
        model.addAttribute("dashboardPath",
                userProfileService.dashboardPath(authentication));
    }



    // ─── POST: Creeaza angajat ────────────────────────────────────────────────
    @PostMapping("/module/utilizatori/creeaza")
    public String creeazaAngajat(
            @RequestParam String numeComplet,
            @RequestParam String email,
            @RequestParam String parola,
            @RequestParam String rol,
            RedirectAttributes redirectAttrs) {

        System.out.println("=== DATE PRIMITE DIN FORM: ===");
        System.out.println("numeComplet: '" + numeComplet + "'");
        System.out.println("email: '" + email + "'");
        System.out.println("parola lungime: " + (parola != null ? parola.length() : "NULL"));
        System.out.println("rol: '" + rol + "'");

        String[] parts = numeComplet.trim().split(" ", 2);
        String nume = parts[0];
        String prenume = parts.length > 1 ? parts[1] : parts[0];

        System.out.println("nume extras: '" + nume + "'");
        System.out.println("prenume extras: '" + prenume + "'");

        Map<String, Object> result = backendService.creeazaAngajat(
                nume, prenume, email, parola, rol);

        if (result != null) {
            redirectAttrs.addFlashAttribute("succes",
                    "Contul a fost creat cu succes!");
        } else {
            redirectAttrs.addFlashAttribute("eroare",
                    "Eroare la crearea contului. Email-ul poate fi deja folosit.");
        }
        return "redirect:/module/utilizatori";
    }

    // ─── POST: Sterge angajat ─────────────────────────────────────────────────
    @PostMapping("/module/utilizatori/sterge")
    public String stergeAngajat(
            @RequestParam Integer id,
            RedirectAttributes redirectAttrs) {
        backendService.stergeAngajat(id);
        redirectAttrs.addFlashAttribute("succes", "Contul a fost șters.");
        return "redirect:/module/utilizatori";
    }

    // ─── POST: Adauga pacient ─────────────────────────────────────────────────
    @PostMapping("/module/pacienti/adauga")
    public String adaugaPacient(
            @RequestParam String cnp,
            @RequestParam String nume,
            @RequestParam String prenume,
            @RequestParam String judet,
            @RequestParam String localitate,
            @RequestParam String strada,
            @RequestParam String nrStrada,
            @RequestParam(required = false) String scara,
            @RequestParam(required = false) String apartament,
            @RequestParam String telefon,
            @RequestParam String email,
            @RequestParam(required = false) String profesie,
            @RequestParam(required = false) String locDeMunca,
            @RequestParam Integer idPat,
            RedirectAttributes redirectAttrs) {

        Map<String, Object> pacientData = new java.util.HashMap<>();
        pacientData.put("cnp", cnp);
        pacientData.put("nume", nume);
        pacientData.put("prenume", prenume);
        pacientData.put("judet", judet);
        pacientData.put("localitate", localitate);
        pacientData.put("strada", strada);
        pacientData.put("nrStrada", nrStrada);
        pacientData.put("scara", scara);
        pacientData.put("apartament", apartament);
        pacientData.put("telefon", telefon);
        pacientData.put("email", email);
        pacientData.put("profesie", profesie);
        pacientData.put("locDeMunca", locDeMunca);

        Map<String, Object> result = backendService.creeazaPacient(pacientData, idPat);

        if (result != null) {
            redirectAttrs.addFlashAttribute("succes",
                    "Pacientul " + nume + " " + prenume + " a fost adăugat cu succes!");
        } else {
            redirectAttrs.addFlashAttribute("eroare",
                    "Eroare la adăugarea pacientului. CNP-ul poate fi deja înregistrat.");
        }
        return "redirect:/module/pacienti";
    }

    // ─── POST: Muta pacient ───────────────────────────────────────────────────
    @PostMapping("/module/pacienti/muta")
    public String mutaPacient(
            @RequestParam String cnp,
            @RequestParam Integer idPatNou,
            RedirectAttributes redirectAttrs) {

        Map<String, Object> result = backendService.mutaPacient(cnp, idPatNou);
        if (result != null) {
            redirectAttrs.addFlashAttribute("succes", "Pacientul a fost mutat cu succes!");
        } else {
            redirectAttrs.addFlashAttribute("eroare",
                    "Eroare la mutarea pacientului. Patul poate fi ocupat.");
        }
        return "redirect:/module/pacienti";
    }

    // ─── POST: Adauga medicament ──────────────────────────────────────────────
    @PostMapping("/module/medicamente/adauga")
    public String adaugaMedicament(
            @RequestParam String denumire,
            @RequestParam Integer stoc,
            RedirectAttributes redirectAttrs) {

        Map<String, Object> result = backendService.adaugaMedicament(denumire, stoc);
        if (result != null) {
            redirectAttrs.addFlashAttribute("succes",
                    "Medicamentul " + denumire + " a fost adăugat cu succes!");
        } else {
            redirectAttrs.addFlashAttribute("eroare",
                    "Eroare la adăugarea medicamentului.");
        }
        return "redirect:/module/medicamente";
    }

    // ─── POST: Adauga prescriptie ─────────────────────────────────────────────
    @PostMapping("/module/prescriptii/adauga")
    public String adaugaPrescriptie(
            @RequestParam String cnp,
            @RequestParam String idMedicament, // Schimbat din Integer în String
            @RequestParam String cantitate,    // Schimbat din Integer în String
            RedirectAttributes redirectAttrs) {

        // Logăm în consolă să vedem EXACT ce trimite browserul
        System.out.println("DEBUG - CNP primit: " + cnp);
        System.out.println("DEBUG - ID Medicament primit: " + idMedicament);
        System.out.println("DEBUG - Cantitate primită: " + cantitate);

        try {
            // Conversie manuală sigură
            Integer idMed = Integer.parseInt(idMedicament);
            Integer cant = Integer.parseInt(cantitate);

            Map<String, Object> result = backendService.adaugaPrescriptie(cnp, idMed, cant);

            if (result != null) {
                redirectAttrs.addFlashAttribute("succes", "Prescripția a fost adăugată!");
            } else {
                redirectAttrs.addFlashAttribute("eroare", "Eroare la adăugarea prescripției.");
            }
        } catch (NumberFormatException e) {
            redirectAttrs.addFlashAttribute("eroare", "Date invalide: " + e.getMessage());
        }

        return "redirect:/module/prescriptii";
    }

    // ─── POST: Creeaza comanda ────────────────────────────────────────────────
    @PostMapping("/module/comenzi/creeaza")
    public String creeazaComanda(
            @RequestParam Integer idAngajat,
            @RequestParam Integer idPrescriptie,
            @RequestParam Integer idPat,
            RedirectAttributes redirectAttrs) {

        Map<String, Object> result = backendService.creeazaComanda(
                idAngajat, idPrescriptie, idPat);
        if (result != null) {
            redirectAttrs.addFlashAttribute("succes",
                    "Comanda a fost creată cu succes!");
        } else {
            redirectAttrs.addFlashAttribute("eroare",
                    "Eroare la crearea comenzii. Verificați stocul medicamentului.");
        }
        return "redirect:/module/comenzi-robot";
    }

    // ─── POST: Actualizeaza status comanda ────────────────────────────────────
    @PostMapping("/module/comenzi/status")
    public String actualizeazaStatus(
            @RequestParam Integer id,
            @RequestParam String status,
            RedirectAttributes redirectAttrs) {

        Map<String, Object> result = backendService.actualizeazaStatusComanda(id, status);
        if (result != null) {
            redirectAttrs.addFlashAttribute("succes",
                    "Statusul comenzii a fost actualizat!");
        } else {
            redirectAttrs.addFlashAttribute("eroare",
                    "Eroare la actualizarea statusului.");
        }
        return "redirect:/module/comenzi-robot";
    }

    // ─── POST: Rezolva alarma ─────────────────────────────────────────────────
    @PostMapping("/module/alarme/rezolva")
    public String rezolvaAlarma(
            @RequestParam Integer id,
            RedirectAttributes redirectAttrs) {

        Map<String, Object> result = backendService.rezolvaAlarma(id);
        if (result != null) {
            redirectAttrs.addFlashAttribute("succes", "Alarma a fost rezolvată!");
        } else {
            redirectAttrs.addFlashAttribute("eroare",
                    "Eroare la rezolvarea alarmei.");
        }
        return "redirect:/module/alarme";
    }
}