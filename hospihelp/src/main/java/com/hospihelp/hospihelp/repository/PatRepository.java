package com.hospihelp.hospihelp.repository;


import com.hospihelp.hospihelp.model.Pat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PatRepository extends JpaRepository<Pat, Integer> {

    List<Pat> findByNrSalon(Integer nrSalon);

    List<Pat> findByOcupat(Boolean ocupat);

    List<Pat> findByNrSalonAndOcupat(Integer nrSalon, Boolean ocupat);
}