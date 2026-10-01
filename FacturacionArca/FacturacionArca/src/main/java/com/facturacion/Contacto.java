package com.facturacion;
import javax.persistence.*;

@Entity
@Table(name = "contacto")

public class Contacto extends EntityId{
    private String email;
    private String telefono;
    private String celular;

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getTelefono() {
        return telefono;
    }
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    public String getCelular() {
        return celular;
    }
    public void setCelular(String celular) {
        this.celular = celular;
    }
}
