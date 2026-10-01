package com.facturacion;
import javax.persistence.*;

@Entity
@Table(name = "punto_venta")
public class PuntoVenta extends AuditoriaApp{
    @Column(nullable = false)
    private int numero;
    private String descripcion;
    private String tipoEmision;
    private String domicilioComercial;

    public int getNumero(){
        return numero;
    }
    public void setNumero(int numero){
        this.numero = numero;
    }
    public String getDescripcion(){
        return descripcion;
    }
    public void setDescripcion(String descripcion){
        this.descripcion = descripcion;
    }
    public String getTipoEmision(){
        return tipoEmision;
    }
    public void setTipoEmision(String tipoEmision){
        this.tipoEmision = tipoEmision;
    }
    public String getDomicilioComercial(){
        return domicilioComercial;
    }
    public void setDomicilioComercial(String domicilioComercial){
        this.domicilioComercial = domicilioComercial;
    }
}
