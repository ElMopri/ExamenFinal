package co.edu.ufps.dto;

import lombok.Data;

@Data
public class FacturaRequesDTO {
	private String token;
	private String cliente;
	private int factura;
}
