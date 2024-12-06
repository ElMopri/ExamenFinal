package co.edu.ufps.dto;

import lombok.Data;

@Data
public class FacturaRequestDTO {
	private String token;
	private String cliente;
	private String tipo_documento;
	private Integer factura;
}
