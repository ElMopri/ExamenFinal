package co.edu.ufps.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.ufps.entities.Cajero;

public interface CajeroRepository extends JpaRepository<Cajero,Integer>{
	public Cajero findByToken (String token);
}
