package co.edu.ufps.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
import co.edu.ufps.entities.Producto;
import co.edu.ufps.entities.Tienda;
import co.edu.ufps.entities.TipoDocumento;
import co.edu.ufps.entities.TipoPago;
import co.edu.ufps.entities.Vendedor;
import co.edu.ufps.repositories.CajeroRepository;
import co.edu.ufps.repositories.ClienteRepository;
import co.edu.ufps.repositories.CompraRepository;
import co.edu.ufps.repositories.ProductoRepository;
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
    private TipoPagoRepository tipoPagoRepository;
    
    @Autowired
    private ProductoRepository productoRepository;
    
    public CompraDTO crear (String uuid, CompraRequestDTO compraRequestDTO) {
    	
    	CompraDTO compraDTO = new CompraDTO();
    	compraDTO.setStatus("error");
    	compraDTO.setData(null);
    	
    	Optional<Tienda> tiendaOpt = tiendaRepository.findFirstByUuid(uuid);
    	if(tiendaOpt.isPresent()) {
    		Tienda tienda = tiendaOpt.get();
    		Compra compra = new Compra();
    		compra.setTienda(tienda);
    		
			compra.setImpuestos(compraRequestDTO.getImpuesto());
			
    		if(compraRequestDTO.getCliente() == null) {
    			compraDTO.setStatus("404");
				compraDTO.setMessage("No hay información del cliente");
				return compraDTO; //Status 404
    		}
    		
    		Cliente cliente = clienteRepository.findByDocumentoAndTipoDocumento_Nombre(compraRequestDTO.getCliente().getDocumento(),compraRequestDTO.getCliente().getTipo_documento());
    		if(cliente != null) { // el cliente ya existe
    			compra.setCliente(cliente);
    		} else { // el cliente no existe
    			cliente = new Cliente();
    			cliente.setId(1);
    			cliente.setDocumento(compraRequestDTO.getCliente().getDocumento());
    			cliente.setNombre(compraRequestDTO.getCliente().getNombre());
    			TipoDocumento tipoDocumento = tipoDocumentoRepository.findByNombre(compraRequestDTO.getCliente().getTipo_documento());
    			if(tipoDocumento != null) { 
    				cliente.setTipoDocumento(tipoDocumento);
    				clienteRepository.save(cliente); 
    			} else {
    				compraDTO.setStatus("404");
    				compraDTO.setMessage("No existe ese tipo de documento");
    				return compraDTO;
    			}
    		}
    		
    		if(compraRequestDTO.getProductos().isEmpty()) {
    			compraDTO.setStatus("404");
				compraDTO.setMessage("No hay productos asignados para esta compra");
				return compraDTO; //Status 404
    		}
    		
    		List<DetallesCompra> detallesCompras = new ArrayList<>();
    		
    		for (DetallesCompraDTO detallesCompraDTO : compraRequestDTO.getProductos()) {
    			Producto producto = productoRepository.findByReferencia(detallesCompraDTO.getReferencia());
    			if (producto != null) {
    				if(detallesCompraDTO.getCantidad()<=producto.getCantidad()) {
	    				DetallesCompra detallesCompra = new DetallesCompra();
	    				detallesCompra.setCompra(compra);
	    				detallesCompra.setProducto(producto);
	    				detallesCompra.setCantidad(detallesCompraDTO.getCantidad());
	    				detallesCompra.setPrecio(producto.getPrecio());
	    				detallesCompra.setDescuento(detallesCompraDTO.getDescuento());
	    				detallesCompras.add(detallesCompra);
	    				
	    				BigDecimal cien = new BigDecimal(100);
	    				BigDecimal descuentoProducto = producto.getPrecio().multiply(detallesCompraDTO.getDescuento().divide(cien));
	    				BigDecimal productoPrecio = producto.getPrecio().subtract(descuentoProducto);
	    				
	    				BigDecimal productoFinal = productoPrecio.multiply(new BigDecimal(detallesCompraDTO.getCantidad()));
	    				
	    				BigDecimal impuestoProductoFinal = productoFinal.multiply(compra.getImpuestos().divide(cien));
	    				
	    				compra.setTotal(compra.getTotal().add(productoFinal.add(impuestoProductoFinal)));
	    				
    				} else {
    					compraDTO.setStatus("403");
        				compraDTO.setMessage("La cantidad a comprar de "+producto.getNombre()+" supera el máximo del producto en tienda");
        				return compraDTO; //Status 403
    				}
    			} else {
    				compraDTO.setStatus("404");
    				compraDTO.setMessage("La referencia del producto "+detallesCompraDTO.getReferencia()+" no existe, por favor revisar los datos");
    				return compraDTO; //Status 404
    			}
    		}
    		
    		compra.setDetallesCompras(detallesCompras);
    		
    		if(compraRequestDTO.getMedios_pago().isEmpty()) {
    			compraDTO.setStatus("404");
				compraDTO.setMessage("No hay medios de pagos asignados para esta compra");
				return compraDTO; //Status 404
    		}
    		
    		List<Pago> pagos = new ArrayList<Pago>();
    		
    		BigDecimal compraTotal = BigDecimal.ZERO;
    		
    		for (PagoDTO mediosPago : compraRequestDTO.getMedios_pago()) {
    			
    			TipoPago tipoPago = tipoPagoRepository.findByNombre(mediosPago.getTipo_pago());
    			if (tipoPago == null) {
    				compraDTO.setStatus("403");
    				compraDTO.setMessage("Tipo de pago "+mediosPago.getTipo_pago()+" no permitido en la tienda");
    				return compraDTO; //Status 403
    			}
    			
    			TarjetaTipo tarjetaTipo = null;
    			try {
    				if(mediosPago.getTipo_tarjeta() != null) {
    					tarjetaTipo = TarjetaTipo.valueOf(mediosPago.getTipo_tarjeta().toUpperCase());
    				}
    			} catch (IllegalArgumentException e) {
    				compraDTO.setStatus("404");
    				compraDTO.setMessage("No existe ese tipo de tarjeta");
    				return compraDTO;
    			}
    			
    			Pago.TarjetaTipo tarjetaTipoPago = null;
    			
    			if(tarjetaTipo != null) {
    				tarjetaTipoPago = Pago.TarjetaTipo.valueOf(tarjetaTipo.name());
    			}
    			
				Pago pago = new Pago();
				pago.setCompra(compra);
				pago.setTipoPago(tipoPago);
				pago.setTarjetaTipo(tarjetaTipoPago);
				pago.setCuotas(mediosPago.getCuotas()<=0 ? 0 : mediosPago.getCuotas());
				pago.setValor(mediosPago.getValor());
				pagos.add(pago);
				compraTotal = compraTotal.add(mediosPago.getValor());
    		}
    		
    		if(compra.getTotal().compareTo(compraTotal)!=0) {
    			compraDTO.setStatus("403");
				compraDTO.setMessage("El valor de la factura no coincide con el valor total de los pagos");
				return compraDTO; //Status 403
    		}
    		
    		compra.setPagos(pagos);
    		
    		if(compraRequestDTO.getVendedor() == null) {
    			compraDTO.setStatus("404");
				compraDTO.setMessage("No hay información del vendedor");
				return compraDTO; //Status 404
    		}
    		
			Vendedor vendedor = vendedorRepository.findByDocumento(compraRequestDTO.getVendedor().getDocumento());
			if (vendedor != null) {
				compra.setVendedor(vendedor);
			} else {
				compraDTO.setStatus("404");
				compraDTO.setMessage("El vendedor no existe en la tienda");
				return compraDTO; //Status 404
			}
			
    		if(compraRequestDTO.getCajero() == null) {
    			compraDTO.setStatus("404");
				compraDTO.setMessage("No hay información del cajero");
				return compraDTO; //Status 404
    		}
			
			Cajero cajero = cajeroRepository.findByToken(compraRequestDTO.getCajero().getToken());
			if (cajero != null) {
				if (cajero.getTienda().getId().equals(tienda.getId())) {
					compra.setCajero(cajero);
				} else {
					compraDTO.setStatus("403");
					compraDTO.setMessage("El cajero no está asignado a esta tienda");
					return compraDTO; //Status 403
				}
			} else {
				compraDTO.setStatus("404");
				compraDTO.setMessage("El token no corresponde a ningún cajero en la tienda");
				return compraDTO; //Status 404
			}
			compra.setObservaciones("Ninguna");
			compra = compraRepository.save(compra);
			
			FacturaDTO facturaDTO= new FacturaDTO();
			facturaDTO.setNumero(compra.getId());
			facturaDTO.setTotal(compra.getTotal());
			facturaDTO.setFecha(compra.getFecha());
			
			compraDTO.setStatus("success");
			compraDTO.setMessage("la factura se a creado correctamente con el numero: "+compra.getId());
			compraDTO.setData(facturaDTO);
			
    		return compraDTO;
    	}
    	
    	compraDTO.setStatus("404");
    	compraDTO.setMessage("No existe la tienda");
    	
    	return compraDTO;
    }
    
    enum TarjetaTipo {
        VISA, MASTERCARD;
    }
    
}