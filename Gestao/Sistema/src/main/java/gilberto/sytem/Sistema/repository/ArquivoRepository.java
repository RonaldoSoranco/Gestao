package gilberto.sytem.Sistema.repository;

import gilberto.sytem.Sistema.model.Arquivo;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ArquivoRepository extends JpaRepository<Arquivo, Long> {
    List<Arquivo> findByClienteId(Long clienteId);
}