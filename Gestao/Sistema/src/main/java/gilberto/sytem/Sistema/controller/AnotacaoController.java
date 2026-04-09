package gilberto.sytem.Sistema.controller;

import gilberto.sytem.Sistema.model.Anotacao;
import gilberto.sytem.Sistema.repository.AnotacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/anotacoes")
@CrossOrigin(origins = "*")
public class AnotacaoController {

    @Autowired
    private AnotacaoRepository repository;

    @GetMapping
    public List<Anotacao> listar() {
        return repository.findAll();
    }

    @GetMapping("/cliente/{clienteId}")
    public List<Anotacao> porCliente(@PathVariable Long clienteId) {
        return repository.findByClienteIdOrderByDataCriacaoDesc(clienteId);
    }

    @PostMapping
    public Anotacao salvar(@RequestBody Anotacao anotacao) {
        return repository.save(anotacao);
    }

    @PutMapping("/{id}")
    public Anotacao atualizar(@PathVariable Long id, @RequestBody Anotacao anotacao) {
        anotacao.setId(id);
        return repository.save(anotacao);
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Long id) {
        repository.deleteById(id);
    }
}