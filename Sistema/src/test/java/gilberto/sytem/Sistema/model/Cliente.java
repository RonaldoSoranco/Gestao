package gilberto.sytem.Sistema.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Cliente {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String cpf;
    private String email;


    private Double valorContrato;
    private Double valorPago;


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