package com.facturacion.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;  //agregado que pide el TP -Consigna 1
@Getter              //agregado que pide el TP -Consigna 1
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "tipo_moneda")
public class TipoMoneda extends AuditoriaApp {

    @Column(name = "codigo_afip", nullable = false)
    private String codigoAfip;

    @Column(nullable = false)
    private String denominacion;

    @Column(nullable = false)
    private String simbolo;

}