package co.edu.ufps.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Data;

@Data
public class FacturaDTO {
	private int numero;
	private BigDecimal total;
	private LocalDate fecha;
}
