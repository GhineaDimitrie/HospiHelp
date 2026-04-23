package com.hospihelp.hospihelp.service;

import com.hospihelp.hospihelp.model.NodHarta;
import com.hospihelp.hospihelp.model.enums.TipLocatie;
import com.hospihelp.hospihelp.repository.NodHartaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NodHartaService {

    private final NodHartaRepository nodHartaRepository;

    public List<NodHarta> getToateNodurile() {
        return nodHartaRepository.findAll();
    }

    public NodHarta getNodById(Integer id) {
        return nodHartaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Nodul cu id-ul " + id + " nu a fost gasit"));
    }

    public NodHarta getNodByRfid(String codRfid) {
        return nodHartaRepository.findByCodRfid(codRfid)
                .orElseThrow(() -> new RuntimeException("Nodul cu RFID " + codRfid + " nu a fost gasit"));
    }

    public List<NodHarta> getNoduriDeTip(TipLocatie tip) {
        return nodHartaRepository.findByTipLocatie(tip);
    }

    public NodHarta adaugaNod(NodHarta nod) {
        return nodHartaRepository.save(nod);
    }

    public void stergeNod(Integer id) {
        if (!nodHartaRepository.existsById(id)) {
            throw new RuntimeException("Nodul cu id-ul " + id + " nu exista");
        }
        nodHartaRepository.deleteById(id);
    }
}