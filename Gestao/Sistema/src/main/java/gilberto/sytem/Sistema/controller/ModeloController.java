package gilberto.sytem.Sistema.controller;

import gilberto.sytem.Sistema.model.Modelo;
import gilberto.sytem.Sistema.repository.ModeloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api/modelos")
@CrossOrigin(origins = "*")
public class ModeloController {

    @Autowired
    private ModeloRepository repository;

    private final String RAIZ = "C:/SistemaGestao/Modelos/";

    @GetMapping
    public List<Modelo> listar() {
        return repository.findAll();
    }

    @PostMapping
    public Modelo salvar(@RequestParam("nome") String nome, 
                        @RequestParam("descricao") String descricao,
                        @RequestParam("file") MultipartFile file) {
        try {
            Path diretorio = Paths.get(RAIZ);
            if (!Files.exists(diretorio)) Files.createDirectories(diretorio);

            String caminho = RAIZ + file.getOriginalFilename();
            Files.write(Paths.get(caminho), file.getBytes());

            Modelo modelo = new Modelo();
            modelo.setNome(nome);
            modelo.setDescricao(descricao);
            modelo.setCaminhoArquivo(caminho);
            return repository.save(modelo);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar modelo: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Long id) {
        repository.deleteById(id);
    }

    @GetMapping("/{id}/download")
    public byte[] download(@PathVariable Long id) throws Exception {
        Modelo modelo = repository.findById(id).orElseThrow();
        return Files.readAllBytes(Paths.get(modelo.getCaminhoArquivo()));
    }
}