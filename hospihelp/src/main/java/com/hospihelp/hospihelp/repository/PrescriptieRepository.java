package com.hospihelp.hospihelp.repository;




import com.hospihelp.hospihelp.model.Prescriptie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PrescriptieRepository extends JpaRepository<Prescriptie, Integer> {

    List<Prescriptie> findByPacientCnp(String cnp);

    List<Prescriptie> findByMedicamentIdMedicament(Integer idMedicament);
}