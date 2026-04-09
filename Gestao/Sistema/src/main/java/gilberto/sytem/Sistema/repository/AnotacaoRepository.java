package gilberto.sytem.Sistema.repository;

import gilberto.sytem.Sistema.model.Anotacao;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AnotacaoRepository extends JpaRepository<Anotacao, Long> {
    List<Anotacao> findByClienteIdOrderByDataCriacaoDesc(Long clienteId);
}