package co.edu.ufps.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.ufps.entities.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente,Integer>{
	public Cliente findByDocumento (String documento);
	public boolean existsByDocumento (String documento);
}
