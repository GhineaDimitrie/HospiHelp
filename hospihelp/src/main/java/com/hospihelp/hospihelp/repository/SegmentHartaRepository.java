package com.hospihelp.hospihelp.repository;



import com.hospihelp.hospihelp.model.SegmentHarta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SegmentHartaRepository extends JpaRepository<SegmentHarta, Integer> {

    List<SegmentHarta> findByNodStartIdNod(Integer idNodStart);

    List<SegmentHarta> findByNodStopIdNod(Integer idNodStop);

    @Query("SELECT s FROM SegmentHarta s WHERE s.nodStart.idNod = :idNod OR s.nodStop.idNod = :idNod")
    List<SegmentHarta> findAllByNod(@Param("idNod") Integer idNod);
}