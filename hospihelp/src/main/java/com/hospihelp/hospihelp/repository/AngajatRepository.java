package com.hospihelp.hospihelp.repository;


import com.hospihelp.hospihelp.model.Angajat;
import com.hospihelp.hospihelp.model.enums.RolAngajat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface AngajatRepository extends JpaRepository<Angajat, Integer> {

    Optional<Angajat> findByEmail(String email);

    boolean existsByEmail(String email);

    List<Angajat> findByRol(RolAngajat rol);
}
