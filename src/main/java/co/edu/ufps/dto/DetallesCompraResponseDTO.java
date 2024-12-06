package co.edu.ufps.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class DetallesCompraResponseDTO {
    private String referencia;
    private String nombre;
    private Integer cantidad = 0;
    private BigDecimal precio = BigDecimal.ZERO;
    private BigDecimal descuento = BigDecimal.ZERO;
    private BigDecimal subtotal = BigDecimal.ZERO;
}
