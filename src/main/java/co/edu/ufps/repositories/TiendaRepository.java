package co.edu.ufps.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.ufps.entities.Tienda;

public interface TiendaRepository extends JpaRepository<Tienda,Integer>{
	public Tienda findByUuid(String uuid);
	public boolean existsByUuid (String uuid);
}
