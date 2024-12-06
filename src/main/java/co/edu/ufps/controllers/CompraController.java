package co.edu.ufps.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.ufps.dto.CompraDTO;
import co.edu.ufps.dto.CompraRequestDTO;
import co.edu.ufps.dto.FacturaRequestDTO;
import co.edu.ufps.dto.FacturaResponseDTO;
import co.edu.ufps.services.CompraService;

@RestController
@RequestMapping
public class CompraController {
	
    @Autowired
    private CompraService compraService;
	
    @PostMapping("/crear/{uuid}")
    public ResponseEntity<CompraDTO> crear(@PathVariable String uuid,@RequestBody CompraRequestDTO compraRequestDTO) {
    	
    	CompraDTO respuesta = compraService.crear(uuid, compraRequestDTO);
    	
    	if(respuesta.getStatus().equals("404")) {
    		respuesta.setStatus("error");
    		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
    	}
    	if(respuesta.getStatus().equals("403")) {
    		respuesta.setStatus("error");
    		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(respuesta);
    	}
    	
        return ResponseEntity.ok(respuesta);
    }
    
    @PostMapping("/consultar/{uuid}")
    public ResponseEntity<FacturaResponseDTO> consultar(@PathVariable String uuid,@RequestBody FacturaRequestDTO facturaRequestDTO) {
    	return ResponseEntity.ok(compraService.consultar(uuid, facturaRequestDTO));
    }
}
