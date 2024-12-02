package co.edu.ufps.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

@Data
public class CompraRequestDTO {
    private BigDecimal impuesto;
    private ClienteDTO cliente;
    private List<DetallesCompraDTO> productos;
    private List<PagoDTO> medios_pago;
    private VendedorDTO vendedor;
    private CajeroDTO cajero;
}
