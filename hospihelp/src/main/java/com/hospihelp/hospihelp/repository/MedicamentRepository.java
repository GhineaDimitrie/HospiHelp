package com.hospihelp.hospihelp.repository;



import com.hospihelp.hospihelp.model.Medicament;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MedicamentRepository extends JpaRepository<Medicament, Integer> {

    List<Medicament> findByDenumireContainingIgnoreCase(String denumire);

    List<Medicament> findByStocGreaterThan(Integer stocMinim);

    List<Medicament> findByStocLessThanEqual(Integer stocCritic);

    @Modifying
    @Query("UPDATE Medicament m SET m.stoc = m.stoc - :cantitate WHERE m.idMedicament = :id AND m.stoc >= :cantitate")
    int scadeStoc(@Param("id") Integer id, @Param("cantitate") Integer cantitate);
}
