package co.edu.ufps.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.ufps.entities.TipoDocumento;

public interface TipoDocumentoRepository extends JpaRepository<TipoDocumento,Integer>{
	public TipoDocumento findByNombre(String nombre);
}
