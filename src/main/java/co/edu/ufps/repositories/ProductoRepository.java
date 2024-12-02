package co.edu.ufps.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.ufps.entities.Producto;

public interface ProductoRepository extends JpaRepository<Producto,Integer>{

}
