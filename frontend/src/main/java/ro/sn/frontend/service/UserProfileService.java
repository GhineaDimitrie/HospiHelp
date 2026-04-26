package ro.sn.frontend.service;

import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import ro.sn.frontend.model.UserProfile;

@Service
public class UserProfileService {

    public UserProfile getProfile(Authentication authentication) {
        if (authentication == null) {
            return new UserProfile("guest", "Vizitator", "GUEST", "Vizitator");
        }

        String username = authentication.getName();
        String roleCode = resolveRole(authentication.getAuthorities());

        return switch (username) {
            case "medic@sn.ro" -> new UserProfile(username, "Dr. Ionescu Gheorghe", roleCode, roleLabel(roleCode));
            case "receptie@sn.ro" -> new UserProfile(username, "Ana Popa", roleCode, roleLabel(roleCode));
            case "farmacie@sn.ro" -> new UserProfile(username, "Farm. Maria Ene", roleCode, roleLabel(roleCode));
            case "admin@sn.ro" -> new UserProfile(username, "Alexandru Matei", roleCode, roleLabel(roleCode));
            default -> new UserProfile(username, username, roleCode, roleLabel(roleCode));
        };
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
                .filter(authority -> authority.startsWith("ROLE_"))
                .map(authority -> authority.substring(5))
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
