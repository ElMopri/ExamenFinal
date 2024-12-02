package co.edu.ufps.entities;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "detalles_compra")
public class DetallesCompra {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@ManyToOne
	@JoinColumn(name = "compra_id")
	private Compra compra;
	@ManyToOne
	@JoinColumn(name = "producto_id")
	private Producto producto;
    @Column(nullable = false)
    private Integer cantidad;
    @Column(name = "precio", precision = 10, scale = 2, nullable = false)
    private BigDecimal precio;
    @Column(name = "descuento", precision = 5, scale = 2)
    private BigDecimal descuento = BigDecimal.ZERO;
}
