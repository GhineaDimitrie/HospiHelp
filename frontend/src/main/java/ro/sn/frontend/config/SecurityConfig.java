package ro.sn.frontend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        UserDetails medic = User.withUsername("medic@sn.ro")
                .password(passwordEncoder.encode("medic123"))
                .roles("MEDIC")
                .build();

        UserDetails receptionist = User.withUsername("receptie@sn.ro")
                .password(passwordEncoder.encode("receptie123"))
                .roles("RECEPTIONIST")
                .build();

        UserDetails pharmacist = User.withUsername("farmacie@sn.ro")
                .password(passwordEncoder.encode("farmacie123"))
                .roles("FARMACIST")
                .build();

        UserDetails administrator = User.withUsername("admin@sn.ro")
                .password(passwordEncoder.encode("admin123"))
                .roles("ADMINISTRATOR")
                .build();

        return new InMemoryUserDetailsManager(medic, receptionist, pharmacist, administrator);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http,
                                           CustomAuthenticationSuccessHandler successHandler) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/css/**", "/js/**", "/login").permitAll()
                .requestMatchers("/dashboard/medic").hasRole("MEDIC")
                .requestMatchers("/dashboard/receptionist").hasRole("RECEPTIONIST")
                .requestMatchers("/dashboard/farmacist").hasRole("FARMACIST")
                .requestMatchers("/dashboard/administrator").hasRole("ADMINISTRATOR")
                .requestMatchers("/module/pacienti").hasAnyRole("MEDIC", "RECEPTIONIST")
                .requestMatchers("/module/saloane").hasRole("RECEPTIONIST")
                .requestMatchers("/module/prescriptii").hasRole("MEDIC")
                .requestMatchers("/module/medicamente").hasRole("FARMACIST")
                .requestMatchers("/module/comenzi-robot").hasRole("FARMACIST")
                .requestMatchers("/module/status-robot").hasAnyRole("MEDIC", "ADMINISTRATOR")
                .requestMatchers("/module/alarme").hasAnyRole("MEDIC", "ADMINISTRATOR")
                .requestMatchers("/module/istoric").hasAnyRole("MEDIC", "FARMACIST")
                .requestMatchers("/module/harta").hasRole("ADMINISTRATOR")
                .requestMatchers("/module/utilizatori").hasRole("ADMINISTRATOR")
                .anyRequest().authenticated()
            )
            .formLogin(login -> login
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .successHandler(successHandler)
                .failureUrl("/login?error=true")
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout=true")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
            )
            .csrf(Customizer.withDefaults());

        return http.build();
    }
}
