package com.hospihelp.hospihelp.repository;

import com.hospihelp.hospihelp.model.Comanda;
import com.hospihelp.hospihelp.model.enums.StatusComanda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ComandaRepository extends JpaRepository<Comanda, Integer> {

    List<Comanda> findByStatus(StatusComanda status);

    List<Comanda> findByAngajatIdAngajat(Integer idAngajat);

    List<Comanda> findByPrescriptiePacientCnp(String cnp);

    @Query("SELECT c FROM Comanda c WHERE c.status = :status ORDER BY c.data DESC, c.ora DESC")
    List<Comanda> findByStatusOrdonat(@Param("status") StatusComanda status);

    @Query("SELECT c FROM Comanda c WHERE c.pat.nrSalon = :nrSalon AND c.status = :status")
    List<Comanda> findBySalonSiStatus(
            @Param("nrSalon") Integer nrSalon,
            @Param("status") StatusComanda status
    );

    @Query("SELECT c FROM Comanda c WHERE c.pat.idPat = :idPat AND c.status = :status")
    List<Comanda> findByPatSiStatus(
            @Param("idPat") Integer idPat,
            @Param("status") StatusComanda status
    );
}