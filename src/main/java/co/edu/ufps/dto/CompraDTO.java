package co.edu.ufps.dto;

import lombok.Data;

@Data
public class CompraDTO {
	private String status;
	private String message;
	private FacturaDTO data;
}