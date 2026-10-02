package com.facturacion;
import javax.persistence.*;
@Entity
@Table(name = "condicion_iva")
public class CondicionIva extends AuditoriaApp {
    @Column(nullable = false)
    private int codigoAfip;

    @Column(nullable = false)
    private String denominacion;

    public int getCodigoAfip() {
        return codigoAfip;
    }
    public void setCodigoAfip(int codigoAfip) {
        this.codigoAfip = codigoAfip;
    }
    public String getDenominacion() {
        return denominacion;
    }
    public void setDenominacion(String denominacion) {
        this.denominacion = denominacion;
    }
}
