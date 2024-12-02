package co.edu.ufps.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class DetallesCompraDTO {
    private String referencia;
    private Integer cantidad;
    private BigDecimal descuento;
}
