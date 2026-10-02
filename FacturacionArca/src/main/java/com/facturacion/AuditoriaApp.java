package com.facturacion;

import javax.persistence.*;
import java.util.Date;
@MappedSuperclass
public abstract class AuditoriaApp extends EntityId{
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    protected Date fechaAlta;

    @Temporal(TemporalType.TIMESTAMP)
    protected Date fechaBaja;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    protected Date fechaModificacion;

    @ManyToOne
    @JoinColumn(name = "usuario_carga_id", nullable = false)
    protected Usuario usuarioCarga;

    @ManyToOne
    @JoinColumn(name = "usuario_baja_id")
    protected Usuario usuarioBaja;

    @ManyToOne
    @JoinColumn(name = "usuario_modificacion_id", nullable = false)
    protected Usuario usuarioModificacion;

    public Date getFechaAlta(){
        return fechaAlta;
    }
    public void setFechaAlta(Date fechaAlta){
        this.fechaAlta = fechaAlta;
    }
    public Date getFechaBaja(){
        return fechaBaja;
    }
    public void setFechaBaja(Date fechaBaja){
        this.fechaBaja = fechaBaja;
    }
    public Date getFechaModificacion(){
        return fechaModificacion;
    }
    public void setFechaModificacion(Date fechaModificacion){
        this.fechaModificacion = fechaModificacion;
    }
    public Usuario getUsuarioCarga(){
        return usuarioCarga;
    }
    public void setUsuarioCarga(Usuario usuarioCarga){
        this.usuarioCarga = usuarioCarga;
    }
    public Usuario getUsuarioBaja(){
        return usuarioBaja;
    }
    public void setUsuarioBaja(Usuario usuarioBaja){
        this.usuarioBaja = usuarioBaja;
    }
    public Usuario getUsuarioModificacion(){
        return usuarioModificacion;
    }
    public void setUsuarioModificacion(Usuario usuarioModificacion){
        this.usuarioModificacion = usuarioModificacion;
    }
}
