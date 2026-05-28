package ro.sn.frontend.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Map;

@Service
public class CustomRemoteUserDetailsService implements UserDetailsService {

    private final RestTemplate restTemplate;

    public CustomRemoteUserDetailsService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        try {
            // Întrebăm Backend-ul tău despre acest utilizator
            // ATENȚIE: Trebuie să ai un endpoint care returnează datele de bază
            // sau adaptăm login-ul. Pentru test, momentan, doar definim structura:

            String url = "http://localhost:8081/auth/info?email=" + email;
            // Vom crea acest mic endpoint "/info" în backend imediat

            return User.withUsername(email)
                    .password("") // Parola va fi verificată prin apelul de login
                    .authorities("ROLE_USER")
                    .build();
        } catch (Exception e) {
            throw new UsernameNotFoundException("Utilizatorul nu a fost găsit în Backend");
        }
    }
}