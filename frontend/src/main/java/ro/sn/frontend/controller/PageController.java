package ro.sn.frontend.controller;

import java.util.List;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import ro.sn.frontend.model.UserProfile;
import ro.sn.frontend.service.UserProfileService;

@Controller
public class PageController {

    private final UserProfileService userProfileService;

    public PageController(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    @GetMapping("/dashboard")
    public String dashboardRedirect(Authentication authentication) {
        return "redirect:" + userProfileService.dashboardPath(authentication);
    }

    @GetMapping("/dashboard/medic")
    public String medicDashboard(Model model, Authentication authentication) {
        preparePage(model, authentication, "dashboard", "Dashboard", "Panou principal pentru medic");
        model.addAttribute("stats", List.of(
                Map.of("title", "Pacienți activi", "value", "24", "caption", "Fișe disponibile", "accent", "blue"),
                Map.of("title", "Prescripții active", "value", "9", "caption", "Astăzi", "accent", "green"),
                Map.of("title", "Alarme robot", "value", "3", "caption", "Necesită verificare", "accent", "orange"),
                Map.of("title", "Status robot", "value", "În transport", "caption", "Robot 1", "accent", "purple")
        ));
        model.addAttribute("quickLinks", List.of(
                Map.of("name", "Pacienți", "path", "/module/pacienti"),
                Map.of("name", "Prescripții", "path", "/module/prescriptii"),
                Map.of("name", "Status robot", "path", "/module/status-robot"),
                Map.of("name", "Alarme", "path", "/module/alarme"),
                Map.of("name", "Istoric transporturi", "path", "/module/istoric")
        ));
        model.addAttribute("timeline", List.of(
                "Pacient nou internat în salonul 2",
                "Prescripție nouă pentru Popescu Ion",
                "Robotul a preluat transportul #1254",
                "Alarmă: obstacol detectat pe coridorul B"
        ));
        return "dashboard";
    }

    @GetMapping("/dashboard/receptionist")
    public String receptionistDashboard(Model model, Authentication authentication) {
        preparePage(model, authentication, "dashboard", "Dashboard", "Panou principal pentru recepționist");
        model.addAttribute("stats", List.of(
                Map.of("title", "Pacienți înregistrați", "value", "24", "caption", "Total pacienți", "accent", "blue"),
                Map.of("title", "Paturi ocupate", "value", "12", "caption", "Din 18 disponibile", "accent", "green"),
                Map.of("title", "Mutări programate", "value", "2", "caption", "În următoarea oră", "accent", "orange"),
                Map.of("title", "Status recepție", "value", "Activ", "caption", "Toate formularele disponibile", "accent", "purple")
        ));
        model.addAttribute("quickLinks", List.of(
                Map.of("name", "Pacienți", "path", "/module/pacienti"),
                Map.of("name", "Saloane și paturi", "path", "/module/saloane")
        ));
        model.addAttribute("timeline", List.of(
                "Pacientul Georgescu Ana a fost alocat la Pat 3",
                "Salonul 1 are un pat liber",
                "Dosarul lui Stan Mihai a fost actualizat"
        ));
        return "dashboard";
    }

    @GetMapping("/dashboard/farmacist")
    public String pharmacistDashboard(Model model, Authentication authentication) {
        preparePage(model, authentication, "dashboard", "Dashboard", "Panou principal pentru farmacist");
        model.addAttribute("stats", List.of(
                Map.of("title", "Medicamente în stoc", "value", "84", "caption", "Articole active", "accent", "blue"),
                Map.of("title", "Comenzi robot", "value", "5", "caption", "În așteptare", "accent", "green"),
                Map.of("title", "Stocuri critice", "value", "4", "caption", "Necesită reaprovizionare", "accent", "orange"),
                Map.of("title", "Ultima livrare", "value", "10:25", "caption", "Robot 1", "accent", "purple")
        ));
        model.addAttribute("quickLinks", List.of(
                Map.of("name", "Stoc medicamente", "path", "/module/medicamente"),
                Map.of("name", "Comenzi robot", "path", "/module/comenzi-robot"),
                Map.of("name", "Istoric transporturi", "path", "/module/istoric")
        ));
        model.addAttribute("timeline", List.of(
                "Paracetamol: stoc actualizat la 120 bucăți",
                "Cutiile pentru salonul 2 au fost pregătite",
                "Comanda #1254 este pregătită pentru livrare"
        ));
        return "dashboard";
    }

    @GetMapping("/dashboard/administrator")
    public String administratorDashboard(Model model, Authentication authentication) {
        preparePage(model, authentication, "dashboard", "Dashboard", "Panou principal pentru administrator");
        model.addAttribute("stats", List.of(
                Map.of("title", "Utilizatori activi", "value", "18", "caption", "Conturi în sistem", "accent", "blue"),
                Map.of("title", "Alarme deschise", "value", "3", "caption", "Necesită soluționare", "accent", "green"),
                Map.of("title", "Zone pe hartă", "value", "11", "caption", "Coridoare și saloane", "accent", "orange"),
                Map.of("title", "Status platformă", "value", "Online", "caption", "Cloud conectat", "accent", "purple")
        ));
        model.addAttribute("quickLinks", List.of(
                Map.of("name", "Utilizatori", "path", "/module/utilizatori"),
                Map.of("name", "Hartă spital", "path", "/module/harta"),
                Map.of("name", "Alarme", "path", "/module/alarme"),
                Map.of("name", "Status robot", "path", "/module/status-robot")
        ));
        model.addAttribute("timeline", List.of(
                "A fost creat un cont nou de farmacist",
                "Harta spitalului a fost actualizată",
                "Conexiunea cloud a fost verificată cu succes"
        ));
        return "dashboard";
    }

    @GetMapping("/module/pacienti")
    public String pacienti(Model model, Authentication authentication) {
        preparePage(model, authentication, "pacienti", "Pacienți", "Adăugare, căutare și editare date pacient");
        return "modules/pacienti";
    }

    @GetMapping("/module/saloane")
    public String saloane(Model model, Authentication authentication) {
        preparePage(model, authentication, "saloane", "Saloane și paturi", "Repartizare pacienți pe saloane și paturi");
        return "modules/saloane";
    }

    @GetMapping("/module/prescriptii")
    public String prescriptii(Model model, Authentication authentication) {
        preparePage(model, authentication, "prescriptii", "Prescripții", "Creare și vizualizare prescripții medicale");
        return "modules/prescriptii";
    }

    @GetMapping("/module/medicamente")
    public String medicamente(Model model, Authentication authentication) {
        preparePage(model, authentication, "medicamente", "Stoc medicamente", "Gestionarea stocurilor din farmacie");
        return "modules/medicamente";
    }

    @GetMapping("/module/comenzi-robot")
    public String comenziRobot(Model model, Authentication authentication) {
        preparePage(model, authentication, "comenzi-robot", "Comenzi robot", "Controlul livrărilor de medicamente");
        return "modules/comenzi-robot";
    }

    @GetMapping("/module/status-robot")
    public String statusRobot(Model model, Authentication authentication) {
        preparePage(model, authentication, "status-robot", "Status robot", "Monitorizare stare și poziție robot");
        return "modules/status-robot";
    }

    @GetMapping("/module/alarme")
    public String alarme(Model model, Authentication authentication) {
        preparePage(model, authentication, "alarme", "Alarme", "Vizualizare alarme și severitate");
        return "modules/alarme";
    }

    @GetMapping("/module/istoric")
    public String istoric(Model model, Authentication authentication) {
        preparePage(model, authentication, "istoric", "Istoric transporturi", "Livrări realizate de robot");
        return "modules/istoric";
    }

    @GetMapping("/module/harta")
    public String harta(Model model, Authentication authentication) {
        preparePage(model, authentication, "harta", "Hartă spital", "Configurarea locațiilor și traseelor");
        return "modules/harta";
    }

    @GetMapping("/module/utilizatori")
    public String utilizatori(Model model, Authentication authentication) {
        preparePage(model, authentication, "utilizatori", "Utilizatori", "Gestionarea conturilor și rolurilor");
        return "modules/utilizatori";
    }

    private void preparePage(Model model, Authentication authentication, String activePage, String title, String subtitle) {
        UserProfile profile = userProfileService.getProfile(authentication);
        model.addAttribute("displayName", profile.displayName());
        model.addAttribute("roleCode", profile.roleCode());
        model.addAttribute("roleLabel", profile.roleLabel());
        model.addAttribute("pageTitle", title);
        model.addAttribute("pageSubtitle", subtitle);
        model.addAttribute("activePage", activePage);
        model.addAttribute("dashboardPath", userProfileService.dashboardPath(authentication));
    }
}
