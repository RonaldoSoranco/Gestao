package gilberto.sytem.Sistema.controller;

import gilberto.sytem.Sistema.model.Historico;
import gilberto.sytem.Sistema.repository.HistoricoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/historicos")
@CrossOrigin(origins = "*")
public class HistoricoController {

    @Autowired
    private HistoricoRepository repository;

    @GetMapping
    public List<Historico> listar() {
        return repository.findAll();
    }

    @GetMapping("/cliente/{clienteId}")
    public List<Historico> porCliente(@PathVariable Long clienteId) {
        return repository.findByClienteIdOrderByDataDesc(clienteId);
    }

    @PostMapping
    public Historico salvar(@RequestBody Historico historico) {
        return repository.save(historico);
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Long id) {
        repository.deleteById(id);
    }
}