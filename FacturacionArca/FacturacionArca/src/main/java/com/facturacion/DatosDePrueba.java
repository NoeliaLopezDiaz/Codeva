package com.facturacion;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Date;

public class DatosDePrueba {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("FacturacionPU");
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            Date ahora = new Date();

            Usuario admin = em.merge(new Usuario("Admin", "8721", "Daniel", "Gonzalez"));
            Usuario vendedor = em.merge(new Usuario("Vendedor1", "1234", "Maria", "Lopez"));

            PuntoVenta pv1 = crearPuntoVenta(em, 1, "Sucursal Central", admin, ahora);
            PuntoVenta pv2 = crearPuntoVenta(em, 2, "Sucursal Norte", admin, ahora);
            PuntoVenta pv5 = crearPuntoVenta(em, 5, "Sucursal Sur", admin, ahora);

            CondicionIva condicionIva = new CondicionIva();
            condicionIva.setCodigoAfip(1);
            condicionIva.setDenominacion("Responsable Inscripto");
            completarAuditoria(condicionIva, admin, ahora);
            condicionIva = em.merge(condicionIva);

            TipoMoneda tipoMoneda = new TipoMoneda();
            tipoMoneda.setCodigoAfip("PES");
            tipoMoneda.setDenominacion("Peso Argentino");
            tipoMoneda.setSimbolo("$");
            completarAuditoria(tipoMoneda, admin, ahora);
            tipoMoneda = em.merge(tipoMoneda);

            Rubro computacion = crearRubro(em, 10, "Computacion", admin, ahora);
            Rubro electronica = crearRubro(em, 20, "Electronica", admin, ahora);

            Marca lenovo = crearMarca(em, 100, "Lenovo", admin, ahora);
            Marca logitech = crearMarca(em, 200, "Logitech", admin, ahora);
            Marca samsung = crearMarca(em, 300, "Samsung", admin, ahora);
            Marca hp = crearMarca(em, 400, "HP", admin, ahora);

            Articulo mouse = crearArticulo(em, "ART-001", "Mouse Inalambrico", computacion, lenovo, admin, ahora);
            Articulo teclado = crearArticulo(em, "ART-002", "Teclado Mecanico", computacion, logitech, admin, ahora);
            Articulo monitor = crearArticulo(em, "ART-003", "Monitor 24 pulgadas", computacion, samsung, admin, ahora);
            Articulo auriculares = crearArticulo(em, "ART-004", "Auriculares Bluetooth", electronica, hp, admin, ahora);
            Articulo cable = crearArticulo(em, "ART-005", "Cable USB", computacion, null, admin, ahora);

            ListaPrecio lista = new ListaPrecio();
            lista.setCodigo("L1");
            lista.setDenominacion("Minorista");
            completarAuditoria(lista, admin, ahora);
            lista = em.merge(lista);

            ListaPrecioArticulo lpaMouse = crearLPA(em, lista, mouse, 1500.0, admin, ahora);
            ListaPrecioArticulo lpaTeclado = crearLPA(em, lista, teclado, 15000.0, admin, ahora);
            ListaPrecioArticulo lpaMonitor = crearLPA(em, lista, monitor, 60000.0, admin, ahora);
            ListaPrecioArticulo lpaAuriculares = crearLPA(em, lista, auriculares, 10000.0, admin, ahora);
            ListaPrecioArticulo lpaCable = crearLPA(em, lista, cable, 500.0, admin, ahora);

            Cliente cliente1 = crearCliente(em, "20-12345678-9", "Juan Perez", admin, ahora);
            Cliente cliente2 = crearCliente(em, "27-87654321-0", "Maria Gomez", admin, ahora);

            crearFactura(em, 1001L, 3000.0, "EMITIDA", admin, pv1, cliente1, condicionIva, tipoMoneda, ahora,
                    new Detalle[]{
                            new Detalle(lpaMouse, "Mouse Inalambrico", 2, 1500.0)
                    });

            crearFactura(em, 1002L, 15000.0, "EMITIDA", admin, pv2, cliente2, condicionIva, tipoMoneda, ahora,
                    new Detalle[]{
                            new Detalle(lpaTeclado, "Teclado Mecanico", 1, 15000.0)
                    });

            crearFactura(em, 1003L, 60000.0, "EMITIDA", admin, pv5, cliente1, condicionIva, tipoMoneda, ahora,
                    new Detalle[]{
                            new Detalle(lpaMonitor, "Monitor 24 pulgadas", 1, 60000.0)
                    });

            crearFactura(em, 1004L, 8000.0, "ANULADA", admin, pv1, cliente2, condicionIva, tipoMoneda, ahora,
                    new Detalle[]{
                            new Detalle(lpaMouse, "Mouse Inalambrico", 2, 1500.0)
                    });

            crearFactura(em, 1005L, 30000.0, "EMITIDA", admin, pv2, cliente1, condicionIva, tipoMoneda, ahora,
                    new Detalle[]{
                            new Detalle(lpaAuriculares, "Auriculares Bluetooth", 3, 10000.0)
                    });

            crearFactura(em, 1006L, 70000.0, "EMITIDA", admin, pv5, cliente2, condicionIva, tipoMoneda, ahora,
                    new Detalle[]{
                            new Detalle(lpaMonitor, "Monitor 24 pulgadas", 1, 60000.0),
                            new Detalle(lpaTeclado, "Teclado Mecanico", 1, 15000.0)
                    });

            crearFactura(em, 1007L, 5000.0, "EMITIDA", vendedor, pv1, cliente1, condicionIva, tipoMoneda, ahora,
                    new Detalle[]{
                            new Detalle(lpaMouse, "Mouse Inalambrico", 1, 1500.0)
                    });

            crearFactura(em, 1008L, 45000.0, "EMITIDA", vendedor, pv2, cliente2, condicionIva, tipoMoneda, ahora,
                    new Detalle[]{
                            new Detalle(lpaMonitor, "Monitor 24 pulgadas", 1, 60000.0)
                    });

            crearFactura(em, 1009L, 12000.0, "EMITIDA", vendedor, pv5, cliente1, condicionIva, tipoMoneda, ahora,
                    new Detalle[]{
                            new Detalle(lpaTeclado, "Teclado Mecanico", 1, 15000.0)
                    });

            crearFactura(em, 1010L, 2000.0, "EMITIDA", vendedor, pv1, cliente2, condicionIva, tipoMoneda, ahora,
                    new Detalle[]{
                            new Detalle(lpaMouse, "Mouse Inalambrico", 1, 1500.0)
                    });

            crearFactura(em, 1011L, 25000.0, "EMITIDA", vendedor, pv2, cliente1, condicionIva, tipoMoneda, ahora,
                    new Detalle[]{
                            new Detalle(lpaAuriculares, "Auriculares Bluetooth", 2, 10000.0)
                    });

            em.getTransaction().commit();
            System.out.println("DataLoader ejecutado correctamente.");
            System.out.println("Insertados: 2 usuarios, 3 puntos de venta, 4 marcas, 5 articulos, 2 clientes, 11 facturas.");
            System.out.println("El articulo 'Cable USB' quedo sin facturar, para la consulta 19.");

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

    private static PuntoVenta crearPuntoVenta(EntityManager em, int numero, String descripcion, Usuario usuario, Date fecha) {
        PuntoVenta pv = new PuntoVenta();
        pv.setNumero(numero);
        pv.setDescripcion(descripcion);
        pv.setTipoEmision("Electronica");
        pv.setDomicilioComercial("Av. Corrientes 1000");
        completarAuditoria(pv, usuario, fecha);
        return em.merge(pv);
    }

    private static Rubro crearRubro(EntityManager em, int codigo, String denominacion, Usuario usuario, Date fecha) {
        Rubro r = new Rubro();
        r.setCodigo(codigo);
        r.setDenominacion(denominacion);
        completarAuditoria(r, usuario, fecha);
        return em.merge(r);
    }

    private static Marca crearMarca(EntityManager em, int codigo, String denominacion, Usuario usuario, Date fecha) {
        Marca m = new Marca();
        m.setCodigo(codigo);
        m.setDenominacion(denominacion);
        completarAuditoria(m, usuario, fecha);
        return em.merge(m);
    }

    private static Articulo crearArticulo(EntityManager em, String codigo, String denominacion, Rubro rubro, Marca marca, Usuario usuario, Date fecha) {
        Articulo a = new Articulo();
        a.setCodigo(codigo);
        a.setDenominacion(denominacion);
        a.setRubro(rubro);
        a.setMarca(marca);
        completarAuditoria(a, usuario, fecha);
        return em.merge(a);
    }

    private static ListaPrecioArticulo crearLPA(EntityManager em, ListaPrecio lista, Articulo articulo, double precio, Usuario usuario, Date fecha) {
        ListaPrecioArticulo lpa = new ListaPrecioArticulo();
        lpa.setListaPrecio(lista);
        lpa.setArticulo(articulo);
        lpa.setPrecioVenta(precio);
        completarAuditoria(lpa, usuario, fecha);
        return em.merge(lpa);
    }

    private static Cliente crearCliente(EntityManager em, String cuit, String denominacion, Usuario usuario, Date fecha) {
        Contacto contacto = new Contacto();
        contacto.setEmail(denominacion.toLowerCase().replace(" ", ".") + "@mail.com");
        contacto.setTelefono("11-5555-5555");
        contacto.setCelular("11-6666-6666");
        contacto = em.merge(contacto);

        Domicilio domicilio = new Domicilio();
        domicilio.setNombreCalle("Calle Falsa");
        domicilio.setNumeroCalle("742");
        domicilio = em.merge(domicilio);

        Cliente c = new Cliente();
        c.setCuitCuil(cuit);
        c.setDenominacion(denominacion);
        c.setContacto(contacto);
        c.setDomicilio(domicilio);
        completarAuditoria(c, usuario, fecha);
        return em.merge(c);
    }

    private static void crearFactura(EntityManager em, Long numero, double importeTotal, String estado,
                                     Usuario usuario, PuntoVenta pv, Cliente cliente, CondicionIva condicionIva,
                                     TipoMoneda tipoMoneda, Date fecha, Detalle[] detalles) {
        FacturaVenta f = new FacturaVenta();
        f.setNumero(numero);
        f.setFechaEmision(fecha);
        f.setPuntoVenta(pv);
        f.setCliente(cliente);
        f.setCondicionIva(condicionIva);
        f.setTipoMoneda(tipoMoneda);
        f.setImporteTotal(importeTotal);
        f.setImporteCobrado(importeTotal);
        f.setImporteSaldo(0.0);
        f.setEstado(estado);
        completarAuditoria(f, usuario, fecha);

        for (Detalle d : detalles) {
            FacturaVentaDetalle det = new FacturaVentaDetalle();
            det.setListaPrecioArticulo(d.lpa);
            det.setDescripcion(d.descripcion);
            det.setCantidad(d.cantidad);
            det.setPrecioUnitario(d.precioUnitario);
            det.setImporteNeto(d.cantidad * d.precioUnitario);
            det.setImporteIva(0.0);
            det.setPorcentajeBonificacion(0.0);
            det.setImporteSubtotal(d.cantidad * d.precioUnitario);
            f.addDetalle(det);
        }

        em.persist(f);
    }

    private static void completarAuditoria(AuditoriaApp entidad, Usuario usuario, Date fecha) {
        entidad.setFechaAlta(fecha);
        entidad.setFechaModificacion(fecha);
        entidad.setUsuarioCarga(usuario);
        entidad.setUsuarioModificacion(usuario);
    }

    private static class Detalle {
        ListaPrecioArticulo lpa;
        String descripcion;
        double cantidad;
        double precioUnitario;

        Detalle(ListaPrecioArticulo lpa, String descripcion, double cantidad, double precioUnitario) {
            this.lpa = lpa;
            this.descripcion = descripcion;
            this.cantidad = cantidad;
            this.precioUnitario = precioUnitario;
        }
    }
}