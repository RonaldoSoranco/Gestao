package gilberto.sytem.Sistema.service;

import gilberto.sytem.Sistema.model.Cliente;
import org.apache.poi.xwpf.usermodel.*;
import org.springframework.stereotype.Service;
import java.io.*;

@Service
public class DocumentoService {

    public byte[] preencherWord(Cliente cliente, InputStream template) throws IOException {
        XWPFDocument doc = new XWPFDocument(template);

        for (XWPFParagraph p : doc.getParagraphs()) {
            for (XWPFRun r : p.getRuns()) {
                String text = r.getText(0);
                if (text != null) {
                    // Substitui as tags automáticas
                    text = text.replace("{{NOME}}", cliente.getNome());
                    text = text.replace("{{CPF}}", cliente.getCpf());
                    text = text.replace("{{STATUS}}", cliente.getStatusPagamento());
                    r.setText(text, 0);
                }
            }
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        doc.write(out);
        doc.close();
        return out.toByteArray();
    }
}