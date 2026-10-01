package com.facturacion;
import javax.persistence.*;
@Entity
@Table(name = "domicilio")
public class Domicilio extends EntityId {
    private String nombreCalle;
    private String numeroCalle;

    public String getNombreCalle() {
        return nombreCalle;
    }
    public void setNombreCalle(String nombreCalle) {
        this.nombreCalle = nombreCalle;
    }
    public String getNumeroCalle() {
        return numeroCalle;
    }
    public void setNumeroCalle(String numeroCalle) {
        this.numeroCalle = numeroCalle;
    }
}
