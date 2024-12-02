package co.edu.ufps.entities;

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
@Table(name = "cajero")
public class Cajero {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(length = 200)
	private String nombre;
	@Column(length = 20)
	private String documento;
	@ManyToOne
	@JoinColumn(name = "tienda_id")
	private Tienda tienda;
	@Column(length = 50)
	private String email;
	@Column(length = 100)
	private String token;
	
	@OneToMany(mappedBy = "cajero", cascade = CascadeType.ALL)
	@JsonIgnore
	List<Compra> compras = null;
}
