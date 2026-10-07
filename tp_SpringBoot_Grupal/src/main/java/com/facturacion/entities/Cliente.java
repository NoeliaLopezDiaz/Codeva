package com.facturacion.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.*;    //agregado que pide el TP -Consigna 1

@Getter              //agregado que pide el TP -Consigna 1
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)

@Entity
@Table(name = "cliente")
public class Cliente extends AuditoriaApp {

    @Column(name = "cuit_cuil", nullable = false)
    private String cuitCuil;

    @Column(nullable = false)
    private String denominacion;

    @OneToOne(optional = false, cascade = CascadeType.PERSIST)
    @JoinColumn(
            name = "contacto_id",
            nullable = false,
            unique = true
    )
    private Contacto contacto;

    @OneToOne(optional = false, cascade = CascadeType.PERSIST)
    @JoinColumn(
            name = "domicilio_id",
            nullable = false,
            unique = true
    )
    private Domicilio domicilio;

}