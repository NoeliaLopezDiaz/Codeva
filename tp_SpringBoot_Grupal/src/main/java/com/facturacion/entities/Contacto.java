package com.facturacion.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;   //agregado que pide el TP -Consigna 1

@Getter              //agregado que pide el TP -Consigna 1
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)


@Entity
@Table(name = "contacto")
public class Contacto extends EntityId {

    private String email;
    private String telefono;
    private String celular;

}