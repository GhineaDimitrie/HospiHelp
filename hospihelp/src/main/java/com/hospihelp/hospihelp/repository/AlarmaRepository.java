package com.hospihelp.hospihelp.repository;



import com.hospihelp.hospihelp.model.Alarma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AlarmaRepository extends JpaRepository<Alarma, Integer> {

    List<Alarma> findByRezolvata(Boolean rezolvata);

    List<Alarma> findByComandaIdComanda(Integer idComanda);

    List<Alarma> findByRezolvataOrderByOraAlarmaDesc(Boolean rezolvata);
}