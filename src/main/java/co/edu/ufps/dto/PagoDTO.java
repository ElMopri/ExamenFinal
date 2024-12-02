package co.edu.ufps.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class PagoDTO {
	private TipoPagoDTO tipo_pago;
	private TarjetaTipo tipo_targeta;
	private int cuotas;
	private BigDecimal valor;
	
    enum TarjetaTipo {
        VISA, MASTERCARD;
    }
}
