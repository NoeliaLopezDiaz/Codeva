package com.facturacion;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class DatosDePrueba {

    private static final Random random = new Random(42);

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("FacturacionPU");
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            Date ahora = new Date();

            List<Usuario> usuarios = new ArrayList<>();
            usuarios.add(em.merge(new Usuario("Admin", "8721", "Daniel", "Gonzalez")));
            for (int i = 1; i < 50; i++) {
                usuarios.add(em.merge(new Usuario("Vendedor" + i, "clave" + i, "Nombre" + i, "Apellido" + i)));
            }
            System.out.println("50 usuarios insertados");

            List<PuntoVenta> puntos = new ArrayList<>();
            for (int i = 1; i <= 50; i++) {
                PuntoVenta pv = new PuntoVenta();
                pv.setNumero(i);
                pv.setDescripcion("Sucursal " + i);
                pv.setTipoEmision("Electronica");
                pv.setDomicilioComercial("Calle " + i + " numero " + (i * 10));
                completarAuditoria(pv, usuarios.get(0), ahora);
                puntos.add(em.merge(pv));
            }
            System.out.println("50 puntos de venta insertados");

            String[] nombresRubros = {"Computacion", "Electronica", "Hogar", "Indumentaria", "Deportes", "Jugueteria", "Libreria", "Ferreteria", "Alimentos", "Bebidas"};
            List<Rubro> rubros = new ArrayList<>();
            for (int i = 1; i <= 50; i++) {
                Rubro r = new Rubro();
                r.setCodigo(i);
                if (i == 1) {
                    r.setDenominacion("Computacion");
                } else {
                    r.setDenominacion(nombresRubros[i % nombresRubros.length] + " " + i);
                }
                completarAuditoria(r, usuarios.get(0), ahora);
                rubros.add(em.merge(r));
            }
            System.out.println("50 rubros insertados");

            String[] nombresMarcas = {"Lenovo", "Logitech", "Samsung", "HP", "Dell", "Asus", "Acer", "Apple", "Xiaomi", "Huawei"};
            List<Marca> marcas = new ArrayList<>();
            for (int i = 1; i <= 50; i++) {
                Marca m = new Marca();
                m.setCodigo(i);
                if (i == 2) {
                    m.setDenominacion("Logitech");   // ← el segundo queda SIN sufijo
                } else {
                    m.setDenominacion(nombresMarcas[i % nombresMarcas.length] + " " + i);
                }
                completarAuditoria(m, usuarios.get(0), ahora);
                marcas.add(em.merge(m));
            }
            System.out.println("50 marcas insertadas");

            List<Articulo> articulos = new ArrayList<>();
            for (int i = 1; i <= 50; i++) {
                Articulo a = new Articulo();
                a.setCodigo("ART-" + String.format("%03d", i));
                a.setDenominacion("Articulo " + i);
                a.setRubro(rubros.get(random.nextInt(rubros.size())));

                if (i == 1) {
                    a.setMarca(marcas.get(0));
                } else if (i % 10 != 0) {
                    a.setMarca(marcas.get(random.nextInt(marcas.size())));
                }

                completarAuditoria(a, usuarios.get(0), ahora);
                articulos.add(em.merge(a));
            }
            System.out.println("50 articulos insertados (algunos sin marca)");

            List<ListaPrecio> listas = new ArrayList<>();
            for (int i = 1; i <= 50; i++) {
                ListaPrecio lp = new ListaPrecio();
                lp.setCodigo("L" + i);
                lp.setDenominacion("Lista " + i);
                completarAuditoria(lp, usuarios.get(0), ahora);
                listas.add(em.merge(lp));
            }
            System.out.println("50 listas de precio insertadas");

            List<ListaPrecioArticulo> lpas = new ArrayList<>();
            for (int i = 0; i < 50; i++) {
                ListaPrecioArticulo lpa = new ListaPrecioArticulo();
                lpa.setListaPrecio(listas.get(random.nextInt(listas.size())));
                lpa.setArticulo(articulos.get(i));
                lpa.setPrecioVenta(100.0 + random.nextInt(9900));
                completarAuditoria(lpa, usuarios.get(0), ahora);
                lpas.add(em.merge(lpa));
            }
            System.out.println("50 lista-precio-articulo insertados");

            List<CondicionIva> condiciones = new ArrayList<>();
            for (int i = 1; i <= 50; i++) {
                CondicionIva c = new CondicionIva();
                c.setCodigoAfip(i);
                c.setDenominacion("Condicion IVA " + i);
                completarAuditoria(c, usuarios.get(0), ahora);
                condiciones.add(em.merge(c));
            }
            System.out.println("50 condiciones IVA insertadas");

            List<TipoMoneda> monedas = new ArrayList<>();
            for (int i = 1; i <= 50; i++) {
                TipoMoneda tm = new TipoMoneda();
                tm.setCodigoAfip("MON" + i);
                tm.setDenominacion("Moneda " + i);
                tm.setSimbolo("$" + i);
                completarAuditoria(tm, usuarios.get(0), ahora);
                monedas.add(em.merge(tm));
            }
            System.out.println("50 tipos de moneda insertados");

            List<Cliente> clientes = new ArrayList<>();
            for (int i = 1; i <= 50; i++) {
                Contacto contacto = new Contacto();
                contacto.setEmail("cliente" + i + "@mail.com");
                contacto.setTelefono("11-5555-" + String.format("%04d", i));
                contacto.setCelular("11-6666-" + String.format("%04d", i));
                contacto = em.merge(contacto);

                Domicilio domicilio = new Domicilio();
                domicilio.setNombreCalle("Calle " + i);
                domicilio.setNumeroCalle(String.valueOf(i * 100));
                domicilio = em.merge(domicilio);

                Cliente c = new Cliente();
                if (i % 2 == 0) {
                    c.setCuitCuil("20-" + String.format("%08d", i) + "-9");
                } else {
                    c.setCuitCuil("27-" + String.format("%08d", i) + "-0");
                }
                c.setDenominacion("Cliente " + i + (i % 3 == 0 ? " Perez" : ""));
                c.setContacto(contacto);
                c.setDomicilio(domicilio);
                completarAuditoria(c, usuarios.get(0), ahora);
                clientes.add(em.merge(c));
            }
            System.out.println("50 clientes insertados");

            String[] estados = {"EMITIDA", "EMITIDA", "EMITIDA", "EMITIDA", "ANULADA"};
            int totalFacturas = 100;
            int totalDetalles = 0;

            for (int i = 0; i < totalFacturas; i++) {
                FacturaVenta f = new FacturaVenta();
                f.setNumero(1000L + i);
                Date fechaEmision = new Date(ahora.getTime() - random.nextInt(30) * 86400000L);
                f.setFechaEmision(fechaEmision);
                f.setPuntoVenta(puntos.get(random.nextInt(puntos.size())));
                f.setCliente(clientes.get(random.nextInt(clientes.size())));
                f.setCondicionIva(condiciones.get(random.nextInt(condiciones.size())));
                f.setTipoMoneda(monedas.get(random.nextInt(monedas.size())));

                double importe = 1000 + random.nextInt(80000);
                f.setImporteTotal(importe);
                f.setImporteCobrado(importe);
                f.setImporteSaldo(0.0);

                String estado = estados[random.nextInt(estados.length)];
                f.setEstado(estado);
                if (estado.equals("ANULADA")) {
                    f.setFechaAnulacion(fechaEmision);
                }

                Usuario usuarioFactura = usuarios.get(random.nextInt(10));
                completarAuditoria(f, usuarioFactura, fechaEmision);

                int numDetalles = 1 + random.nextInt(3);
                for (int j = 0; j < numDetalles; j++) {
                    ListaPrecioArticulo lpa;
                    if (i == 0 && j == 0) {
                        lpa = lpas.get(0);   // ← fuerza el artículo 1 (marca "Logitech")
                    } else {
                        lpa = lpas.get(random.nextInt(lpas.size()));
                    }

                    int cantidad = 1 + random.nextInt(5);
                    double precioUnit = lpa.getPrecioVenta();

                    FacturaVentaDetalle det = new FacturaVentaDetalle();
                    det.setListaPrecioArticulo(lpa);
                    det.setDescripcion(lpa.getArticulo().getDenominacion());
                    det.setCantidad(cantidad);
                    det.setPrecioUnitario(precioUnit);
                    det.setImporteNeto(cantidad * precioUnit);
                    det.setImporteIva(0.0);
                    det.setPorcentajeBonificacion(0.0);
                    det.setImporteSubtotal(cantidad * precioUnit);
                    f.addDetalle(det);
                    totalDetalles++;
                }

                em.persist(f);
            }
            System.out.println("100 facturas insertadas con " + totalDetalles + " detalles");

            em.getTransaction().commit();
            System.out.println("\n=== CARGA COMPLETA ===");
            System.out.println("50 usuarios, 50 puntos de venta, 50 rubros, 50 marcas, 50 articulos");
            System.out.println("50 listas de precio, 50 lista-precio-articulo, 50 condiciones IVA, 50 tipos de moneda");
            System.out.println("50 clientes, 100 facturas con detalles");

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

    private static void completarAuditoria(AuditoriaApp entidad, Usuario usuario, Date fecha) {
        entidad.setFechaAlta(fecha);
        entidad.setFechaModificacion(fecha);
        entidad.setUsuarioCarga(usuario);
        entidad.setUsuarioModificacion(usuario);
    }
}