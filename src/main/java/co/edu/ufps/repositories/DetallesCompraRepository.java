package co.edu.ufps.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.ufps.entities.DetallesCompra;

public interface DetallesCompraRepository extends JpaRepository<DetallesCompra,Integer>{
	public DetallesCompra findByProducto_Nombre(String nombre);
}
