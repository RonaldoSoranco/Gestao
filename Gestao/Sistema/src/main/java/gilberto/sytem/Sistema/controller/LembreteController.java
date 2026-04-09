package gilberto.sytem.Sistema.controller;

import gilberto.sytem.Sistema.model.Lembrete;
import gilberto.sytem.Sistema.repository.LembreteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/lembretes")
@CrossOrigin(origins = "*")
public class LembreteController {

    @Autowired
    private LembreteRepository repository;

    @GetMapping
    public List<Lembrete> listar() {
        return repository.findAll();
    }

    @GetMapping("/ativos")
    public List<Lembrete> ativos() {
        return repository.findByVisualizarTrueOrderByDataLembreteAsc();
    }

    @GetMapping("/cliente/{clienteId}")
    public List<Lembrete> porCliente(@PathVariable Long clienteId) {
        return repository.findByClienteId(clienteId);
    }

    @PostMapping
    public Lembrete salvar(@RequestBody Lembrete lembrete) {
        if (lembrete.getDataLembrete() == null) {
            lembrete.setDataLembrete(LocalDateTime.now().plusDays(1));
        }
        return repository.save(lembrete);
    }

    @PutMapping("/{id}")
    public Lembrete atualizar(@PathVariable Long id, @RequestBody Lembrete lembrete) {
        lembrete.setId(id);
        return repository.save(lembrete);
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Long id) {
        repository.deleteById(id);
    }
}