package gilberto.sytem.Sistema.controller;

import gilberto.sytem.Sistema.model.Cliente;
import gilberto.sytem.Sistema.repository.ClienteRepository;
import gilberto.sytem.Sistema.service.DocumentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
@CrossOrigin(origins = "*")
public class ClienteController {

    @Autowired
    private ClienteRepository repository;

    @Autowired
    private DocumentoService documentoService;

    @GetMapping
    public List<Cliente> listar() {
        return repository.findAll();
    }

    @PostMapping
    public Cliente salvar(@RequestBody Cliente cliente) {
        return repository.save(cliente);
    }

    @PostMapping("/{id}/gerar-doc")
    public ResponseEntity<byte[]> gerarDoc(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        try {
            Cliente c = repository.findById(id).get();
            byte[] conteudo = documentoService.preencherWord(c, file.getInputStream());
            return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=documento.docx")
                .body(conteudo);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}