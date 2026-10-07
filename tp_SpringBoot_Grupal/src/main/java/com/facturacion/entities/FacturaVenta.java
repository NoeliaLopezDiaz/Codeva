package com.facturacion.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(
        callSuper = true,
        exclude = {"detalles", "cliente", "condicionIva", "puntoVenta"}
)
@ToString(
        exclude = {"detalles", "cliente", "condicionIva", "puntoVenta"}
)
@Entity
@Table(name = "factura_venta")
public class FacturaVenta extends AuditoriaApp {

    private Long numero;

    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_emision", nullable = false)
    private Date fechaEmision;

    @ManyToOne(
            optional = false,
            cascade = CascadeType.PERSIST,
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "punto_venta_id", nullable = false)
    private PuntoVenta puntoVenta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = true)
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "condicion_iva_id", nullable = false)
    private CondicionIva condicionIva;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipo_moneda_id" ,nullable = false)
    private TipoMoneda tipoMoneda;

    @Column(name = "importe_cobrado")
    private double importeCobrado;

    @Column(name = "importe_saldo")
    private double importeSaldo;

    @Column(name = "importe_total", nullable = false)
    private double importeTotal;

    private String cae;

    @Temporal(TemporalType.DATE)
    @Column(name = "cae_fecha_vencimiento")
    private Date caeFechaVencimiento;

    @Column(name = "resultado_afip")
    private String resultadoAfip;

    @Column(name = "motivo_rechazo")
    private String motivoRechazo;

    @Column(nullable = false)
    private String estado;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_anulacion")
    private Date fechaAnulacion;

    private String observaciones;

    @OneToMany(
            mappedBy = "factura",
            cascade = CascadeType.ALL
    )
    private List<FacturaVentaDetalle> detalles = new ArrayList<>();

    public void agregarDetalle(FacturaVentaDetalle detalle) {
        detalles.add(detalle);
        detalle.setFactura(this);
    }

    public void calcularImportes() {
        importeTotal = 0.0;

        for (FacturaVentaDetalle detalle : detalles) {
            importeTotal += detalle.getImporteSubtotal();
        }

        importeSaldo = importeTotal - importeCobrado;
    }
}