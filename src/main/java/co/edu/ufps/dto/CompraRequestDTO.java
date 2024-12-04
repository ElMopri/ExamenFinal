package co.edu.ufps.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

@Data
public class CompraRequestDTO {
    private BigDecimal impuesto = null;
    private ClienteDTO cliente = null;
    private List<DetallesCompraDTO> productos = null;
    private List<PagoDTO> medios_pago = null;
    private VendedorDTO vendedor = null;
    private CajeroDTO cajero = null;
}
