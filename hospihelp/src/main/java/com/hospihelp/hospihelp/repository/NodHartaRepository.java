package com.hospihelp.hospihelp.repository;




import com.hospihelp.hospihelp.model.NodHarta;
import com.hospihelp.hospihelp.model.enums.TipLocatie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface NodHartaRepository extends JpaRepository<NodHarta, Integer> {

    Optional<NodHarta> findByCodRfid(String codRfid);

    List<NodHarta> findByTipLocatie(TipLocatie tipLocatie);

    List<NodHarta> findByPatIdPat(Integer idPat);
}