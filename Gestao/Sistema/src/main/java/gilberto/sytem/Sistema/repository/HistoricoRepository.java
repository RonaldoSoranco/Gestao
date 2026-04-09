package gilberto.sytem.Sistema.repository;

import gilberto.sytem.Sistema.model.Historico;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface HistoricoRepository extends JpaRepository<Historico, Long> {
    List<Historico> findByClienteIdOrderByDataDesc(Long clienteId);
}