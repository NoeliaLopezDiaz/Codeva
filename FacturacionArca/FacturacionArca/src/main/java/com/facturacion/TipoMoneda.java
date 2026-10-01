package com.facturacion;
import javax.persistence.*;

@Entity
@Table(name = "tipo_moneda")
public class TipoMoneda extends AuditoriaApp {
    @Column(nullable = false)
    private String codigoAfip;

    @Column(nullable = false)
    private String denominacion;

    @Column(nullable = false)
    private String simbolo;

    public String getCodigoAfip(){
        return codigoAfip;
    }
    public void setCodigoAfip(String codigoAfip){
        this.codigoAfip = codigoAfip;
    }
    public String getDenominacion(){
        return denominacion;
    }
    public void setDenominacion(String denominacion){
        this.denominacion = denominacion;
    }
    public String getSimbolo(){
        return simbolo;
    }
    public void setSimbolo(String simbolo){
        this.simbolo = simbolo;
    }
}
