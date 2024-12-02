package co.edu.ufps.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.ufps.dto.CajeroDTO;
import co.edu.ufps.dto.CompraDTO;
import co.edu.ufps.dto.DetallesCompraDTO;
import co.edu.ufps.dto.PagoDTO;
import co.edu.ufps.dto.TipoPagoDTO;
import co.edu.ufps.dto.VendedorDTO;
import co.edu.ufps.repositories.ClienteRepository;
import co.edu.ufps.repositories.CompraRepository;
import co.edu.ufps.repositories.ProductoRepository;
import co.edu.ufps.repositories.TipoPagoRepository;
import co.edu.ufps.repositories.TipoDocumentoRepository;
import co.edu.ufps.repositories.DetallesCompraRepository;
import co.edu.ufps.entities.Cajero;
import co.edu.ufps.entities.Cliente;
import co.edu.ufps.entities.Compra;
import co.edu.ufps.entities.DetallesCompra;
import co.edu.ufps.entities.Pago;
import co.edu.ufps.entities.TipoDocumento;
import co.edu.ufps.entities.TipoPago;
import co.edu.ufps.entities.Vendedor;

@Service
public class CompraService {

    @Autowired
    private CompraRepository compraRepository;
    
    @Autowired
    private TipoDocumentoRepository tipoDocumentoRepository;
    
    @Autowired
    private DetallesCompraRepository detallesCompraRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private TipoPagoRepository tipoPagoRepository;

    @Autowired
    private ProductoRepository productoRepository;
    
    public CompraDTO crear (Integer tiendaUuid,CompraDTO compraDTO) {
    	Compra compra = new Compra();
    	compra.setImpuestos(compraDTO.getImpuesto());
    	Cliente cliente = new Cliente();
    	cliente.setDocumento(compraDTO.getCliente().getDocumento());
    	cliente.setNombre(compraDTO.getCliente().getNombre());
    	TipoDocumento tipoDocumento = tipoDocumentoRepository.findByNombre(compraDTO.getCliente().getTipoDocumento());
    	cliente.setTipoDocumento(tipoDocumento);
    	
    	compra.setCliente(cliente);
    	
    	List<DetallesCompra> productos;
    	List<DetallesCompraDTO> productosDTO = compraDTO.getProductos();
    	for (DetallesCompraDTO producto : productosDTO) {
    		
    	}
    	
    	//compra.setDetallesCompra(compraDTO.getProductos());
    	
    	return null;
    }   
}