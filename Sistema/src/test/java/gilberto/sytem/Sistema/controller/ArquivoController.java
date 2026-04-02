package gilberto.sytem.Sistema.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/arquivos")
@CrossOrigin("*")
public class ArquivoController {

    private final String RAIZ = "C:/SistemaGestao/Documentos/";

    @PostMapping("/upload/{cpf}")
    public String upload(@PathVariable String cpf, @RequestParam("file") MultipartFile file) {
        try {
            Path diretorio = Paths.get(RAIZ + cpf);
            if (!Files.exists(diretorio)) Files.createDirectories(diretorio);
            
            Files.write(diretorio.resolve(file.getOriginalFilename()), file.getBytes());
            return "Sucesso";
        } catch (Exception e) {
            return "Erro: " + e.getMessage();
        }
    }
}