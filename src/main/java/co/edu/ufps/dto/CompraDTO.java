package co.edu.ufps.dto;

import java.math.BigDecimal;
import java.util.List;

import co.edu.ufps.entities.DetallesCompra;
import lombok.Data;

@Data
public class CompraDTO {
    private BigDecimal impuesto;
    private ClienteDTO cliente;
    private List<DetallesCompraDTO> productos;
    private List<PagoDTO> mediosPago;
    private VendedorDTO vendedor;
    private CajeroDTO cajero;
}