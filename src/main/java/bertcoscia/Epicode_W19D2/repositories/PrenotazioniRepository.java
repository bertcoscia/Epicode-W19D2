package bertcoscia.Epicode_W19D2.repositories;

import bertcoscia.Epicode_W19D2.entities.Dipendente;
import bertcoscia.Epicode_W19D2.entities.Prenotazione;
import bertcoscia.Epicode_W19D2.entities.Viaggio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.UUID;

@Repository
public interface PrenotazioniRepository extends JpaRepository<Prenotazione, UUID> {

    // @Query("SELECT p FROM Prenotazione p WHERE p.dipendente.idDipendente = :id AND p.viaggio.data = :data")
    boolean existsByDipendenteAndViaggioData(Dipendente dipendente, LocalDate data);

    boolean existsByDipendenteAndViaggio(Dipendente dipendente, Viaggio viaggio);
}
