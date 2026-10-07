package com.facturacion.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;   //agregado que pide el TP -Consigna 1
@Getter              //agregado que pide el TP -Consigna 1
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)

@Entity
@Table(name = "condicion_iva")
public class CondicionIva extends AuditoriaApp {

    @Column(name = "codigo_afip", nullable = false)
    private int codigoAfip;

    @Column(nullable = false)
    private String denominacion;

}