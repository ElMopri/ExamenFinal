package co.edu.ufps.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "compra")
public class Compra {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@ManyToOne
	@JoinColumn(name = "cliente_id")
	private Cliente cliente;
	@ManyToOne
	@JoinColumn(name = "tienda_id")
	private Tienda tienda;
	@ManyToOne
	@JoinColumn(name = "vendedor_id")
	private Vendedor vendedor;
	@ManyToOne
	@JoinColumn(name = "cajero_id")
	private Cajero cajero;
    @Column(name = "total", precision = 10, scale = 2, nullable = false)
    private BigDecimal total = BigDecimal.ZERO;
    @Column(name = "impuestos", precision = 5, scale = 2, nullable = false)
    private BigDecimal impuestos = BigDecimal.ZERO;
    @Column(name = "fecha", nullable = false)
    private LocalDateTime fecha = LocalDateTime.now();
	@Column(length = 1000)
	private String observaciones;
	
	@OneToMany(mappedBy = "compra", cascade = CascadeType.ALL)
	@JsonIgnore
	List<DetallesCompra> detallesCompra = null;
	
	@OneToMany(mappedBy = "compra", cascade = CascadeType.ALL)
	@JsonIgnore
	List<Pago> pagos = null;
}
