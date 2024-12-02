package co.edu.ufps.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.ufps.entities.TipoPago;

public interface TipoPagoRepository extends JpaRepository<TipoPago,Integer>{
	public TipoPago findByNombre(String nombre);
}
