package co.edu.ufps.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.ufps.entities.Pago;

public interface PagoRepository extends JpaRepository<Pago,Integer>{
	public boolean existsByTipoPago_Nombre (String nombre);
	public boolean existsByTarjetaTipo (String tarjetaTipo);
}
