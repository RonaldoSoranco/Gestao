package gilberto.sytem.Sistema.repository;

import gilberto.sytem.Sistema.model.Lembrete;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LembreteRepository extends JpaRepository<Lembrete, Long> {
    List<Lembrete> findByClienteId(Long clienteId);
    List<Lembrete> findByVisualizarTrueOrderByDataLembreteAsc();
}