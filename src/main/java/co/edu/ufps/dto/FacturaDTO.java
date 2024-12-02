package co.edu.ufps.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class FacturaDTO {
	private int numero;
	private BigDecimal total;
	private LocalDateTime fecha;
}
