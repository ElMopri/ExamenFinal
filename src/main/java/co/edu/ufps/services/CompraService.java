package co.edu.ufps.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.ufps.dto.CompraDTO;
import co.edu.ufps.dto.CompraRequestDTO;
import co.edu.ufps.dto.DetallesCompraDTO;
import co.edu.ufps.dto.FacturaDTO;
import co.edu.ufps.dto.PagoDTO;
import co.edu.ufps.entities.Cajero;
import co.edu.ufps.entities.Cliente;
import co.edu.ufps.entities.Compra;
import co.edu.ufps.entities.DetallesCompra;
import co.edu.ufps.entities.Pago;
import co.edu.ufps.entities.Tienda;
import co.edu.ufps.entities.TipoDocumento;
import co.edu.ufps.entities.TipoPago;
import co.edu.ufps.entities.Vendedor;
import co.edu.ufps.repositories.CajeroRepository;
import co.edu.ufps.repositories.ClienteRepository;
import co.edu.ufps.repositories.CompraRepository;
import co.edu.ufps.repositories.DetallesCompraRepository;
import co.edu.ufps.repositories.TiendaRepository;
import co.edu.ufps.repositories.TipoDocumentoRepository;
import co.edu.ufps.repositories.TipoPagoRepository;
import co.edu.ufps.repositories.VendedorRepository;

@Service
public class CompraService {

    @Autowired
    private CompraRepository compraRepository;
    
    @Autowired
    private TiendaRepository tiendaRepository;
    
    @Autowired
    private ClienteRepository clienteRepository;
    
    @Autowired
    private TipoDocumentoRepository tipoDocumentoRepository;
    
    @Autowired
    private VendedorRepository vendedorRepository;
    
    @Autowired
    private CajeroRepository cajeroRepository;
    
    @Autowired
    private DetallesCompraRepository detallesCompraRepository;
    
    @Autowired
    private TipoPagoRepository tipoPagoRepository;
    
    public CompraDTO crear (String uuid, CompraRequestDTO compraRequestDTO) {
    	
    	CompraDTO compraDTO = new CompraDTO();
    	compraDTO.setStatus("error");
    	compraDTO.setData(null);
    	
    	Tienda tienda = tiendaRepository.findByUuid(uuid);
    	if(tienda != null) {
    		
    		Compra compra = new Compra();
    		compra.setTienda(tienda);
    		
    		Cliente cliente = clienteRepository.findByDocumento(compraRequestDTO.getCliente().getDocumento());
    		if(cliente != null) { // el cliente ya existe
    			compra.setCliente(cliente);
    		} else { // el cliente no existe
    			cliente.setDocumento(compraRequestDTO.getCliente().getDocumento());
    			cliente.setNombre(compraRequestDTO.getCliente().getNombre());
    			TipoDocumento tipoDocumento = tipoDocumentoRepository.findByNombre(compraRequestDTO.getCliente().getTipo_documento());
    			if(tipoDocumento != null) { // el tipo de documento ya existe
    				cliente.setTipoDocumento(tipoDocumento);
    				clienteRepository.save(cliente); // crear el cliente
    			} else {
    				compraDTO.setMessage("No existe ese tipo de documento");
    				return compraDTO;//No existe ese tipo de documento
    			}
    		}
    		
    		for (DetallesCompraDTO producto : compraRequestDTO.getProductos()) {
    			DetallesCompra detallesCompra = detallesCompraRepository.findByProducto_Nombre(producto.getReferencia());
    			if (detallesCompra != null) { // el detalles de compra si existe
    				compra.getDetallesCompras().add(detallesCompra);
    			} else {
    				compraDTO.setMessage("No existe ese detalles compra");
    				return compraDTO; // no existe ese detalles compra
    			}
    		}
    		
    		for (PagoDTO mediosPago : compraRequestDTO.getMedios_pago()) {
    			
    			TipoPago tipoPago = tipoPagoRepository.findByNombre(mediosPago.getTipo_pago());
    			if (tipoPago == null) {
    				compraDTO.setMessage("No existe ese tipo de pago");
    				return compraDTO;
    			}
    			
    			TarjetaTipo tarjetaTipo;
    			try {
    			    tarjetaTipo = TarjetaTipo.valueOf(mediosPago.getTipo_tarjeta().toUpperCase());
    			    System.out.println("La tarjeta es: " + tarjetaTipo);
    			} catch (IllegalArgumentException e) {
    				compraDTO.setMessage("No existe ese tipo de tarjeta");
    				return compraDTO;
    			}
    			Pago.TarjetaTipo tarjetaTipoPago = Pago.TarjetaTipo.valueOf(tarjetaTipo.name());
    			
				Pago pago = new Pago();
				pago.setCompra(compra);
				pago.setTipoPago(tipoPago);
				pago.setTarjetaTipo(tarjetaTipoPago);
				pago.setCuotas(mediosPago.getCuotas()<=0 ? 0 : mediosPago.getCuotas());
				pago.setValor(mediosPago.getValor());
				compra.getPagos().add(pago);
				compra.setTotal(compra.getTotal().add(pago.getValor()));
    		}
    		
			Vendedor vendedor = vendedorRepository.findByDocumento(compraRequestDTO.getVendedor().getDocumento());
			if (vendedor != null) {
				compra.setVendedor(vendedor);
			} else {
				compraDTO.setMessage("No existe ese vendedor");
				return compraDTO; // vendedor no existe
			}
			
			Cajero cajero = cajeroRepository.findByToken(compraRequestDTO.getCajero().getToken());
			if (cajero != null) {
				compra.setCajero(cajero);
			} else {
				compraDTO.setMessage("No existe ese cajero");
				return compraDTO; // el cajero no existe
			}
			compra.setImpuestos(compraRequestDTO.getImpuesto());
			compra = compraRepository.save(compra);
			
			FacturaDTO facturaDTO= new FacturaDTO();
			facturaDTO.setNumero(compra.getId());
			facturaDTO.setTotal(compra.getTotal());
			facturaDTO.setFecha(compra.getFecha());
			
			compraDTO.setStatus("succes");
			compraDTO.setMessage("la factura se a creado correctamente con el numero: "+compra.getId());
			compraDTO.setData(facturaDTO);
			
    		return compraDTO;
    	}
    	
    	compraDTO.setMessage("No existe la tienda");
    	
    	return compraDTO;
    }
    
    enum TarjetaTipo {
        VISA, MASTERCARD;
    }
    
}