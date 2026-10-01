package com.facturacion;
import javax.persistence.*;

@MappedSuperclass
public abstract class EntityId {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    public Long getId(){
        return id;
    }
    public void setId(Long id){
        this.id = id;
    }
}
