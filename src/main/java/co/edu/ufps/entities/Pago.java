package co.edu.ufps.entities;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "pago")
public class Pago {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@ManyToOne
	@JoinColumn(name = "compra_id")
	private Compra compra;
	@ManyToOne
	@JoinColumn(name = "tipo_pago_id")
	private TipoPago tipoPago;
	
    @Enumerated(EnumType.STRING)
    @Column(name = "tarjeta_tipo", nullable = false)
    private TarjetaTipo tarjetaTipo;
    
    public enum TarjetaTipo {
        VISA, MASTERCARD;
    }
    
    @Column(name = "valor", precision = 10, scale = 2)
    private BigDecimal valor;
    private int cuotas;
}
