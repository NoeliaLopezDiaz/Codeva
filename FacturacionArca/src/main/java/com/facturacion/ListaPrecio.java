package com.facturacion;
import javax.persistence.*;
@Entity
@Table(name = "lista_precio")
public class ListaPrecio extends AuditoriaApp {
    @Column(nullable = false)
    private String codigo;

    @Column(nullable = false)
    private String denominacion;

    public String getCodigo() {
        return codigo;
    }
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    public String getDenominacion() {
        return denominacion;
    }
    public void setDenominacion(String denominacion) {
        this.denominacion = denominacion;
    }
}
