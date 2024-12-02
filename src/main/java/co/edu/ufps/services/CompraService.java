package co.edu.ufps.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.ufps.dto.CompraDTO;
import co.edu.ufps.dto.CompraRequestDTO;
import co.edu.ufps.entities.Cliente;
import co.edu.ufps.entities.Compra;
import co.edu.ufps.entities.Tienda;
import co.edu.ufps.repositories.CompraRepository;
import co.edu.ufps.repositories.TiendaRepository;

@Service
public class CompraService {

    @Autowired
    private CompraRepository compraRepository;
    
    @Autowired
    private TiendaRepository tiendaRepository;
    
    public CompraRequestDTO crear (String uuid, CompraDTO compraDTO) {
    	Compra compra;
    	try {
    		Tienda tiendaOpt = tiendaRepository.findByUuid(uuid);
    		Cliente cliente;
    		
    	} catch (Exception e) {
    		
    	}
    	return null;
    }
    
}