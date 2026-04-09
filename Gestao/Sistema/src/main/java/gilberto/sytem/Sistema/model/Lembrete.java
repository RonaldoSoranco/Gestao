package gilberto.sytem.Sistema.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Lembrete {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String descricao;
    private LocalDateTime dataLembrete;
    private Boolean visualizar;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @PrePersist
    public void prePersist() {
        if (visualizar == null) visualizar = true;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public LocalDateTime getDataLembrete() { return dataLembrete; }
    public void setDataLembrete(LocalDateTime dataLembrete) { this.dataLembrete = dataLembrete; }
    public Boolean getVisualizar() { return visualizar; }
    public void setVisualizar(Boolean visualizar) { this.visualizar = visualizar; }
    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }
}