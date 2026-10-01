package com.facturacion;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Date;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("FacturacionPU");

        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Date ahora = new Date();

            Usuario usuario = em.merge(
                    new Usuario("Admin", "8721", "Daniel", "Gonzalez")
            );

            PuntoVenta puntoVenta = new PuntoVenta();
            puntoVenta.setNumero(1);
            puntoVenta.setDescripcion("Sucursal central");
            puntoVenta.setTipoEmision("Electronica");
            puntoVenta.setDomicilioComercial("Av. 9 de Julio 823");
            CompletarAuditoria(puntoVenta, usuario, ahora);
            puntoVenta = em.merge(puntoVenta);

            Marca marca = new Marca();
            marca.setCodigo(100);
            marca.setDenominacion("Lenovo");
            CompletarAuditoria(marca, usuario, ahora);
            marca = em.merge(marca);

            Rubro rubro = new Rubro();
            rubro.setCodigo(10);
            rubro.setDenominacion("Computacion");
            CompletarAuditoria(rubro, usuario, ahora);
            rubro = em.merge(rubro);


            Articulo articulo = new Articulo();
            articulo.setCodigo("ART-001");
            articulo.setDenominacion("Mouse Inalámbrico");
            articulo.setRubro(rubro);
            articulo.setMarca(marca);
            CompletarAuditoria(articulo, usuario, ahora);
            articulo = em.merge(articulo);

            ListaPrecio listaPrecio = new ListaPrecio();
            listaPrecio.setCodigo("L1");
            listaPrecio.setDenominacion("Minorista");
            CompletarAuditoria(listaPrecio, usuario, ahora);
            listaPrecio = em.merge(listaPrecio);

            ListaPrecioArticulo listaPrecioArticulo = new ListaPrecioArticulo();
            listaPrecioArticulo.setListaPrecio(listaPrecio);
            listaPrecioArticulo.setArticulo(articulo);
            listaPrecioArticulo.setPrecioVenta(1500.0);
            CompletarAuditoria(listaPrecioArticulo, usuario, ahora);
            listaPrecioArticulo = em.merge(listaPrecioArticulo);

            CondicionIva condicionIva = new CondicionIva();
            condicionIva.setCodigoAfip(1);
            condicionIva.setDenominacion("Responsable inscripto");
            CompletarAuditoria(condicionIva, usuario, ahora);
            condicionIva = em.merge(condicionIva);

            TipoMoneda tipoMoneda = new TipoMoneda();
            tipoMoneda.setCodigoAfip("Peso");
            tipoMoneda.setDenominacion("Peso argentino");
            tipoMoneda.setSimbolo("$");
            CompletarAuditoria(tipoMoneda, usuario, ahora);
            tipoMoneda = em.merge(tipoMoneda);

            FacturaVenta facturaVenta = new FacturaVenta();
            facturaVenta.setNumero(1001L);
            facturaVenta.setFechaEmision(ahora);
            facturaVenta.setPuntoVenta(puntoVenta);
            facturaVenta.setImporteCobrado(0.0);
            facturaVenta.setImporteSaldo(3000.0);
            facturaVenta.setImporteTotal(3000.0);
            facturaVenta.setEstado("EMITIDA");
            facturaVenta.setObservaciones("factura generada");
            facturaVenta.setCondicionIva(condicionIva);
            facturaVenta.setTipoMoneda(tipoMoneda);
            CompletarAuditoria(facturaVenta, usuario, ahora);

            FacturaVentaDetalle detalle1 = new FacturaVentaDetalle();
            detalle1.setListaPrecioArticulo(listaPrecioArticulo);
            detalle1.setDescripcion("Mouse Inalámbrico");
            detalle1.setCantidad(2);
            detalle1.setPrecioUnitario(1500.0);
            detalle1.setPorcentajeBonificacion(0.0);
            detalle1.setImporteNeto(3000.0);
            detalle1.setImporteIva(0.0);
            detalle1.setImporteSubtotal(3000.0);

            facturaVenta.addDetalle(detalle1);

            em.persist(facturaVenta);

            em.getTransaction().commit();
            System.out.println("Factura guardada correctamente." + facturaVenta.getId());


        } catch (Exception e) {

            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }

            e.printStackTrace();

        } finally {

            em.close();
            emf.close();
        }
    }

    public static void CompletarAuditoria(AuditoriaApp entidad, Usuario usuario, Date fecha) {
        entidad.setFechaAlta(fecha);
        entidad.setFechaModificacion(fecha);
        entidad.setUsuarioCarga(usuario);
        entidad.setUsuarioModificacion(usuario);
    }
}
