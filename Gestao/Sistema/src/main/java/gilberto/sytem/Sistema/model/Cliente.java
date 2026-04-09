package gilberto.sytem.Sistema.model;

import jakarta.persistence.*;

@Entity
public class Cliente {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String cpf;
    private String email;

    private Double valorContrato;
    private Double valorPago;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public Double getValorContrato() { return valorContrato; }
    public void setValorContrato(Double valorContrato) { this.valorContrato = valorContrato; }
    public Double getValorPago() { return valorPago; }
    public void setValorPago(Double valorPago) { this.valorPago = valorPago; }

    public Double getSaldoDevedor() {
        return (valorContrato != null ? valorContrato : 0.0) - (valorPago != null ? valorPago : 0.0);
    }

    public String getStatusPagamento() {
        double pago = (valorPago != null ? valorPago : 0.0);
        double total = (valorContrato != null ? valorContrato : 0.0);
        
        if (pago <= 0) return "DEVENDO";
        if (pago < total) return "PARCIAL";
        return "PAGO";
    }
}