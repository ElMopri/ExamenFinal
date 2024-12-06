package co.edu.ufps.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

@Data
public class FacturaResponseDTO {
	private BigDecimal total = BigDecimal.ZERO;
	private BigDecimal impuestos = BigDecimal.ZERO;
	private ClienteDTO cliente = new ClienteDTO();
	private List<DetallesCompraResponseDTO> productos = null;
	private CajeroResponseDTO cajero = new CajeroResponseDTO();
}
