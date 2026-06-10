    package com.hospihelp.hospihelp.service;

    import com.hospihelp.hospihelp.model.Alarma;
    import com.hospihelp.hospihelp.model.Comanda;
    import com.hospihelp.hospihelp.model.enums.StatusComanda;
    import com.hospihelp.hospihelp.repository.AlarmaRepository;
    import jakarta.transaction.Transactional;
    import lombok.RequiredArgsConstructor;
    import org.springframework.stereotype.Service;
    import java.time.LocalDateTime;
    import java.util.List;

    @Service
    @RequiredArgsConstructor
    public class AlarmaService {

        private final AlarmaRepository alarmaRepository;
        private final ComandaService comandaService;

        public List<Alarma> getToateAlarmele() {
            return alarmaRepository.findAll();
        }

        public List<Alarma> getAlarmeNerezolvate() {
            return alarmaRepository.findByRezolvataOrderByOraAlarmaDesc(false);
        }

        public List<Alarma> getAlarmeComanda(Integer idComanda) {
            return alarmaRepository.findByComandaIdComanda(idComanda);
        }

        @Transactional
        public Alarma creeazaAlarma(Integer idComanda, String tip, String mesaj) {
            Comanda comanda = comandaService.getComandaById(idComanda);

            Alarma alarma = new Alarma();
            alarma.setComanda(comanda);
            alarma.setTip(tip);
            alarma.setMesaj(mesaj);
            alarma.setRezolvata(false);
            alarma.setOraAlarma(LocalDateTime.now());

            // Comanda devine NEFINALIZAT cand apare o alarma
            comandaService.actualizeazaStatus(idComanda, StatusComanda.NEFINALIZAT);

            return alarmaRepository.save(alarma);
        }

        @Transactional
        public Alarma rezolvaAlarma(Integer idAlarma) {
            Alarma alarma = alarmaRepository.findById(idAlarma)
                    .orElseThrow(() -> new RuntimeException("Alarma cu id-ul " + idAlarma + " nu a fost gasita"));
            alarma.setRezolvata(true);
            return alarmaRepository.save(alarma);
        }
    }