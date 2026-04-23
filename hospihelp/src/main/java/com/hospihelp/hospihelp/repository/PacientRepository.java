package com.hospihelp.hospihelp.repository;


import com.hospihelp.hospihelp.model.Pacient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface PacientRepository extends JpaRepository<Pacient, String> {

    List<Pacient> findByNumeContainingIgnoreCaseOrPrenumeContainingIgnoreCase(
            String nume, String prenume
    );

    Optional<Pacient> findByPatIdPat(Integer idPat);

    @Query("SELECT p FROM Pacient p WHERE p.pat.nrSalon = :nrSalon")
    List<Pacient> findByNrSalon(@Param("nrSalon") Integer nrSalon);

    boolean existsByCnp(String cnp);
}