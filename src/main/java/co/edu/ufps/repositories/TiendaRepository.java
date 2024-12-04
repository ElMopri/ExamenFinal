package co.edu.ufps.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.ufps.entities.Tienda;

public interface TiendaRepository extends JpaRepository<Tienda,Integer>{
	public Optional<Tienda> findFirstByUuid(String uuid);
	public boolean existsByUuid (String uuid);
}
