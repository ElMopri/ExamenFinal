package co.edu.ufps.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class PagoDTO {
	private String tipo_pago;
	private String tipo_tarjeta;
	private int cuotas;
	private BigDecimal valor;
}
