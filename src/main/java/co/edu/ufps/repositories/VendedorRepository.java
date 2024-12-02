package co.edu.ufps.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.ufps.entities.Vendedor;

public interface VendedorRepository extends JpaRepository<Vendedor,Integer>{
	public Vendedor findByDocumento (String documento);
}
