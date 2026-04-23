package com.hospihelp.hospihelp.config;

import com.hospihelp.hospihelp.model.Angajat;
import com.hospihelp.hospihelp.repository.AngajatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final AngajatRepository angajatRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Angajat angajat = angajatRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "Nu exista niciun angajat cu emailul: " + email));

        return new User(
                angajat.getEmail(),
                angajat.getParolaCriptata(),
                List.of(new SimpleGrantedAuthority("ROLE_" + angajat.getRol().name()))
        );
    }
}