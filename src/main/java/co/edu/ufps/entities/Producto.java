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
@Table(name = "producto")
public class Producto {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(length = 200)
	private String nombre;
	@Column(length = 1000)
	private String descripcion;
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal precio;
	@ManyToOne
	@JoinColumn(name = "tipo_producto_id")
    private TipoProducto tipoProducto;
    @Column(nullable = false, columnDefinition = "int default 0")
    private Integer cantidad;
    @Column(length = 20)
    private String referencia;
}
