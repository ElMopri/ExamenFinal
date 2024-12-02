package co.edu.ufps.dto;

import java.util.List;

import lombok.Data;

@Data
public class CompraDTO {
    private double impuesto;
    private ClienteDTO cliente;
    private List<DetallesCompraDTO> productos;
    private List<PagoDTO> medios_pago;
    private VendedorDTO vendedor;
    private CajeroDTO cajero;
}