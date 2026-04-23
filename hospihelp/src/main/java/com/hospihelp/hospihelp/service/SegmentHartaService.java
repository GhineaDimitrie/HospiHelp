package com.hospihelp.hospihelp.service;

import com.hospihelp.hospihelp.model.NodHarta;
import com.hospihelp.hospihelp.model.SegmentHarta;
import com.hospihelp.hospihelp.model.enums.DirectieMers;
import com.hospihelp.hospihelp.repository.SegmentHartaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SegmentHartaService {

    private final SegmentHartaRepository segmentHartaRepository;
    private final NodHartaService nodHartaService;

    public List<SegmentHarta> getToateSegmentele() {
        return segmentHartaRepository.findAll();
    }

    public List<SegmentHarta> getSegmentePornindDinNod(Integer idNod) {
        return segmentHartaRepository.findByNodStartIdNod(idNod);
    }

    public List<SegmentHarta> getToateSegmentelePentruNod(Integer idNod) {
        return segmentHartaRepository.findAllByNod(idNod);
    }

    public SegmentHarta adaugaSegment(Integer idNodStart, Integer idNodStop, DirectieMers directie) {
        NodHarta nodStart = nodHartaService.getNodById(idNodStart);
        NodHarta nodStop = nodHartaService.getNodById(idNodStop);

        SegmentHarta segment = new SegmentHarta();
        segment.setNodStart(nodStart);
        segment.setNodStop(nodStop);
        segment.setDirectieMers(directie);

        return segmentHartaRepository.save(segment);
    }

    public void stergeSegment(Integer id) {
        if (!segmentHartaRepository.existsById(id)) {
            throw new RuntimeException("Segmentul cu id-ul " + id + " nu exista");
        }
        segmentHartaRepository.deleteById(id);
    }
}