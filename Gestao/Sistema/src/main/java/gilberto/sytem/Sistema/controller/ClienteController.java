package gilberto.sytem.Sistema.controller;

import gilberto.sytem.Sistema.model.*;
import gilberto.sytem.Sistema.repository.*;
import gilberto.sytem.Sistema.service.DocumentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api/clientes")
@CrossOrigin(origins = "*")
public class ClienteController {

    @Autowired
    private ClienteRepository repository;

    @Autowired
    private ArquivoRepository arquivoRepository;

    @Autowired
    private HistoricoRepository historicoRepository;

    @Autowired
    private DocumentoService documentoService;

    private final String RAIZ_ARQUIVOS = "C:/SistemaGestao/Documentos/";

    @GetMapping
    public List<Cliente> listar() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Cliente buscar(@PathVariable Long id) {
        return repository.findById(id).orElse(null);
    }

    @PostMapping
    public Cliente salvar(@RequestBody Cliente cliente) {
        Cliente salvo = repository.save(cliente);
        
        Historico h = new Historico();
        h.setCliente(salvo);
        h.setTipo("CADASTRO");
        h.setDescricao("Cliente cadastrado");
        historicoRepository.save(h);
        
        return salvo;
    }

    @PutMapping("/{id}")
    public Cliente atualizar(@PathVariable Long id, @RequestBody Cliente cliente) {
        cliente.setId(id);
        Cliente atualizado = repository.save(cliente);
        
        Historico h = new Historico();
        h.setCliente(atualizado);
        h.setTipo("ATUALIZACAO");
        h.setDescricao("Dados atualizados");
        historicoRepository.save(h);
        
        return atualizado;
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Long id) {
        repository.deleteById(id);
    }

    @PostMapping("/{id}/gerar-doc")
    public ResponseEntity<byte[]> gerarDoc(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        try {
            Cliente c = repository.findById(id).orElse(null);
            if (c == null) {
                return ResponseEntity.notFound().build();
            }
            byte[] conteudo = documentoService.preencherWord(c, file.getInputStream());
            return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=documento.docx")
                .body(conteudo);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{id}/arquivos")
    public List<Arquivo> listarArquivos(@PathVariable Long id) {
        return arquivoRepository.findByClienteId(id);
    }

    @PostMapping("/{id}/arquivos")
    public Arquivo uploadArquivo(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        try {
            Cliente cliente = repository.findById(id).orElse(null);
            if (cliente == null) return null;

            Path diretorio = Paths.get(RAIZ_ARQUIVOS + cliente.getCpf());
            if (!Files.exists(diretorio)) Files.createDirectories(diretorio);

            String caminho = diretorio.resolve(file.getOriginalFilename()).toString();
            Files.write(Paths.get(caminho), file.getBytes());

            Arquivo arquivo = new Arquivo();
            arquivo.setNome(file.getOriginalFilename());
            arquivo.setCaminho(caminho);
            arquivo.setTipo(file.getContentType());
            arquivo.setTamanho(file.getSize());
            arquivo.setCliente(cliente);
            
            Arquivo salvo = arquivoRepository.save(arquivo);
            
            Historico h = new Historico();
            h.setCliente(cliente);
            h.setTipo("ARQUIVO");
            h.setDescricao("Arquivo uploaded: " + file.getOriginalFilename());
            historicoRepository.save(h);
            
            return salvo;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao upload: " + e.getMessage());
        }
    }

    @GetMapping("/{id}/historico")
    public List<Historico> historico(@PathVariable Long id) {
        return historicoRepository.findByClienteIdOrderByDataDesc(id);
    }

    @GetMapping("/relatorio")
    public List<Cliente> relatorio(@RequestParam(required = false) String status) {
        List<Cliente> todos = repository.findAll();
        if (status == null || status.isEmpty()) return todos;
        return todos.stream()
            .filter(c -> status.equalsIgnoreCase(c.getStatusPagamento()))
            .toList();
    }

    @GetMapping("/arquivos/{id}")
    public byte[] downloadArquivo(@PathVariable Long id) throws Exception {
        Arquivo arquivo = arquivoRepository.findById(id).orElseThrow();
        return Files.readAllBytes(Paths.get(arquivo.getCaminho()));
    }
}