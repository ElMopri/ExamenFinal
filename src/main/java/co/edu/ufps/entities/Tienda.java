package co.edu.ufps.entities;

import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "tienda")
public class Tienda {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(length = 200)
	private String nombre;
	@Column(length = 500)
	private String direccion;
	@Column(length = 50)
	private String uuid;
	
	@OneToMany(mappedBy = "tienda", cascade = CascadeType.ALL)
	@JsonIgnore
	List<Cajero> cajeros = null;
	
	@OneToMany(mappedBy = "tienda", cascade = CascadeType.ALL)
	@JsonIgnore
	List<Compra> compras = null;
	
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Tienda tienda = (Tienda) obj;
        return Objects.equals(id, tienda.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
