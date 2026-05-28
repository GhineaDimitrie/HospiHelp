package ro.sn.frontend.config;

import jakarta.servlet.http.HttpSession;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;
import java.util.Map;

@Component
public class RemoteAuthenticationProvider implements AuthenticationProvider {

    private final RestTemplate restTemplate;

    public RemoteAuthenticationProvider(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {
        String email = authentication.getName();
        String password = authentication.getCredentials().toString();

        try {
            String url = "http://localhost:8081/auth/login";
            Map<String, String> request = Map.of("email", email, "parola", password);
            Map<String, Object> response = restTemplate.postForObject(
                    url, request, Map.class);

            if (response != null && response.get("token") != null) {
                String token = (String) response.get("token");
                String rolDeLaBackend = ((String) response.get("rol")).toUpperCase();
                String rolFinal = rolDeLaBackend.equals("ADMIN")
                        ? "ADMINISTRATOR" : rolDeLaBackend;

                // Salvam tokenul in sesiunea HTTP
                ServletRequestAttributes attrs =
                        (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
                if (attrs != null) {
                    HttpSession session = attrs.getRequest().getSession(true);
                    session.setAttribute("jwt_token", token);
                    System.out.println("Token salvat in sesiune: "
                            + token.substring(0, 20) + "...");
                }

                return new UsernamePasswordAuthenticationToken(
                        email,
                        token,
                        List.of(new SimpleGrantedAuthority("ROLE_" + rolFinal))
                );
            }
        } catch (Exception e) {
            System.err.println("Eroare autentificare: " + e.getMessage());
            throw new BadCredentialsException("Email sau parolă incorectă");
        }

        throw new BadCredentialsException("Autentificare eșuată");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}