package ro.sn.frontend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import ro.sn.frontend.model.UserProfile;

import java.util.Collection;
import java.util.Map;

@Service
public class UserProfileService {

    private final RestTemplate restTemplate;

    @Value("${backend.url}")
    private String backendUrl;

    public UserProfileService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public UserProfile getProfile(Authentication authentication) {
        if (authentication == null) {
            return new UserProfile("guest", "Vizitator", "GUEST", "Vizitator");
        }

        String username = authentication.getName();
        String roleCode = resolveRole(authentication.getAuthorities());

        // Incearca sa preia numele real din backend
        try {
            String token = getTokenFromAuthentication(authentication);
            if (token != null) {
                HttpHeaders headers = new HttpHeaders();
                headers.set("Authorization", "Bearer " + token);
                HttpEntity<Void> entity = new HttpEntity<>(headers);

                ResponseEntity<Map> response = restTemplate.exchange(
                        backendUrl + "/api/profil",
                        HttpMethod.GET,
                        entity,
                        Map.class
                );

                if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                    Map body = response.getBody();
                    String nume = (String) body.get("nume");
                    String prenume = (String) body.get("prenume");
                    String displayName = nume + " " + prenume;
                    return new UserProfile(username, displayName, roleCode, roleLabel(roleCode));
                }
            }
        } catch (Exception e) {
            // Fallback la username daca backend-ul nu raspunde
        }

        return new UserProfile(username, username, roleCode, roleLabel(roleCode));
    }

    private String getTokenFromAuthentication(Authentication authentication) {
        // Tokenul JWT este stocat in credentials dupa autentificare
        if (authentication.getCredentials() instanceof String) {
            return (String) authentication.getCredentials();
        }
        return null;
    }

    public String dashboardPath(Authentication authentication) {
        String roleCode = resolveRole(authentication.getAuthorities());
        return switch (roleCode) {
            case "MEDIC" -> "/dashboard/medic";
            case "RECEPTIONIST" -> "/dashboard/receptionist";
            case "FARMACIST" -> "/dashboard/farmacist";
            case "ADMINISTRATOR" -> "/dashboard/administrator";
            default -> "/login";
        };
    }

    private String resolveRole(Collection<? extends GrantedAuthority> authorities) {
        return authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .filter(a -> a.startsWith("ROLE_"))
                .map(a -> a.substring(5))
                .findFirst()
                .orElse("GUEST");
    }

    private String roleLabel(String roleCode) {
        return switch (roleCode) {
            case "MEDIC" -> "Medic";
            case "RECEPTIONIST" -> "Recepționist";
            case "FARMACIST" -> "Farmacist";
            case "ADMINISTRATOR" -> "Administrator";
            default -> "Utilizator";
        };
    }
}