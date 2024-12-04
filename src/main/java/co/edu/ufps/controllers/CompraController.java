package co.edu.ufps.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.ufps.dto.CompraDTO;
import co.edu.ufps.dto.CompraRequestDTO;
import co.edu.ufps.services.CompraService;

@RestController
@RequestMapping
public class CompraController {
	
    @Autowired
    private CompraService compraService;
	
    @PostMapping("/crear/{uuid}")
    public ResponseEntity<CompraDTO> crear(@PathVariable String uuid,@RequestBody CompraRequestDTO compraRequestDTO) {
        return ResponseEntity.ok(compraService.crear(uuid, compraRequestDTO));
    }
}
