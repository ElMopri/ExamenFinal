package co.edu.ufps.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class DetallesCompraDTO {
    private ProductoDTO productoDTO;
    private Integer cantidad;
    private BigDecimal descuento;
}
