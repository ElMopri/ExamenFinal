package co.edu.ufps.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.ufps.repositories.TiendaRepository;

@Service
public class TiendaService {
	@Autowired TiendaRepository tiendaRepository;
	
	
}
