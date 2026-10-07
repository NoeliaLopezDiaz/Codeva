package com.facturacion.consultas;

import com.facturacion.entities.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Date;
import java.util.List;
import java.util.Arrays;

public class ConsultasJPQL {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("FacturacionPU");
        EntityManager em = emf.createEntityManager();

        try {

            System.out.println("\n=== CONSULTA 1: Entidades completas ===");
            String jpql1 = "SELECT f FROM FacturaVenta f";
            List<FacturaVenta> facturas = em.createQuery(jpql1, FacturaVenta.class)
                    .getResultList();
            System.out.println("Total de facturas encontradas: " + facturas.size());

            System.out.println("\n=== CONSULTA 2: Proyección de atributos ===");
            String jpql2 = "SELECT f.numero, f.fechaEmision, f.importeTotal FROM FacturaVenta f";
            List<Object[]> resultados = em.createQuery(jpql2, Object[].class)
                    .getResultList();
            for (Object[] fila : resultados) {
                Long numero = (Long) fila[0];
                Date fechaEmision = (Date) fila[1];
                Double importeTotal = (Double) fila[2];
                System.out.println("Factura Nro: " + numero + " | Fecha: " + fechaEmision + " | Total: $" + importeTotal);
            }

            System.out.println("\n=== CONSULTA 3: Filtrado por igualdad ===");
            String jpql3 = "SELECT a FROM Articulo a WHERE a.rubro.denominacion = :denominacionRubro";
            List<Articulo> articulos = em.createQuery(jpql3, Articulo.class)
                    .setParameter("denominacionRubro", "Computacion")
                    .getResultList();
            System.out.println("Artículos en 'Computacion': " + articulos.size());

            System.out.println("\n=== CONSULTA 4: Rango de fechas ===");
            Date fechaInicio = new Date(System.currentTimeMillis() - 86400000L * 7);
            Date fechaFin = new Date();
            String jpql4 = "SELECT f FROM FacturaVenta f WHERE f.fechaEmision BETWEEN :fechaInicio AND :fechaFin";
            List<FacturaVenta> facturasRango = em.createQuery(jpql4, FacturaVenta.class)
                    .setParameter("fechaInicio", fechaInicio)
                    .setParameter("fechaFin", fechaFin)
                    .getResultList();
            System.out.println("Facturas en el rango de fechas: " + facturasRango.size());

            System.out.println("\n=== CONSULTA 5: Condicionales y nulos ===");
            String jpql5 = "SELECT f FROM FacturaVenta f WHERE f.estado = :estado AND f.importeTotal > :importeMinimo AND f.fechaAnulacion IS NULL";
            List<FacturaVenta> facturasFiltradas = em.createQuery(jpql5, FacturaVenta.class)
                    .setParameter("estado", "EMITIDA")
                    .setParameter("importeMinimo", 10000.0)
                    .getResultList();
            System.out.println("Facturas válidas encontradas: " + facturasFiltradas.size());

            System.out.println("\n=== CONSULTA 6: LIKE y LOWER ===");
            String textoBuscado = "Juan Perez";
            String jpql6 = "SELECT c FROM Cliente c WHERE LOWER(c.denominacion) LIKE :patron OR c.cuitCuil LIKE :cuitInicio";
            List<Cliente> clientes = em.createQuery(jpql6, Cliente.class)
                    .setParameter("patron", "%" + textoBuscado.toLowerCase() + "%")
                    .setParameter("cuitInicio", "20-%")
                    .getResultList();
            System.out.println("Clientes encontrados: " + clientes.size());

            System.out.println("\n=== CONSULTA 7: DISTINCT y ORDER BY ===");
            String jpql7 = "SELECT DISTINCT f.estado FROM FacturaVenta f ORDER BY f.estado ASC";
            List<String> estados = em.createQuery(jpql7, String.class)
                    .getResultList();
            System.out.println("Estados distintos encontrados: " + estados.size());

            System.out.println("\n=== CONSULTA 8: COUNT, SUM, AVG ===");
            String jpql8 = "SELECT COUNT(f), SUM(f.importeTotal), AVG(f.importeTotal) FROM FacturaVenta f";
            Object[] resultado = em.createQuery(jpql8, Object[].class)
                    .getSingleResult();
            Long cantidadFacturas = (Long) resultado[0];
            Double sumaImportes = (Double) resultado[1];
            Double promedioImportes = (Double) resultado[2];
            System.out.println("Cantidad: " + cantidadFacturas + " | Suma: " + sumaImportes + " | Promedio: " + promedioImportes);

            System.out.println("\n=== CONSULTA 9: IN ===");
            String jpql9 = "SELECT p FROM PuntoVenta p WHERE p.numero IN :numeros";
            List<PuntoVenta> puntosVenta = em.createQuery(jpql9, PuntoVenta.class)
                    .setParameter("numeros", Arrays.asList(1, 2, 5))
                    .getResultList();
            System.out.println("Puntos de venta encontrados: " + puntosVenta.size());

            System.out.println("\n=== CONSULTA 10: Path Expressions ===");
            String jpql10 = "SELECT f FROM FacturaVenta f WHERE f.usuarioCarga.usuario = :nombreUsuario";
            List<FacturaVenta> facturasUsuario = em.createQuery(jpql10, FacturaVenta.class)
                    .setParameter("nombreUsuario", "Admin")
                    .getResultList();
            System.out.println("Facturas encontradas: " + facturasUsuario.size());

            System.out.println("\n=== CONSULTA 11: INNER JOIN ===");
            String jpql11 = "SELECT d FROM FacturaVentaDetalle d JOIN d.factura f WHERE f.puntoVenta.numero = :nroPuntoVenta";
            List<FacturaVentaDetalle> detalles = em.createQuery(jpql11, FacturaVentaDetalle.class)
                    .setParameter("nroPuntoVenta", 1)
                    .getResultList();
            System.out.println("Detalles encontrados: " + detalles.size());

            System.out.println("\n=== CONSULTA 12: LEFT JOIN ===");
            String jpql12 = "SELECT a.denominacion, m.denominacion FROM Articulo a LEFT JOIN a.marca m";
            List<Object[]> resultados12 = em.createQuery(jpql12, Object[].class)
                    .getResultList();
            System.out.println("Artículos listados con marca: " + resultados12.size());

            System.out.println("\n=== CONSULTA 13: JOINs combinados ===");
            String jpql13 = "SELECT DISTINCT f FROM FacturaVenta f JOIN f.detalles d JOIN d.listaPrecioArticulo lpa JOIN lpa.articulo a WHERE a.marca.denominacion = :nombreMarca";
            List<FacturaVenta> facturasMarca = em.createQuery(jpql13, FacturaVenta.class)
                    .setParameter("nombreMarca", "Logitech")
                    .getResultList();
            System.out.println("Facturas con artículos de la marca: " + facturasMarca.size());

            System.out.println("\n=== CONSULTA 14: Subconsulta ===");
            String jpql14 = "SELECT f FROM FacturaVenta f WHERE f.importeTotal > (SELECT AVG(f2.importeTotal) FROM FacturaVenta f2)";
            List<FacturaVenta> facturasAltas = em.createQuery(jpql14, FacturaVenta.class)
                    .getResultList();
            System.out.println("Facturas mayores al promedio: " + facturasAltas.size());

            System.out.println("\n=== CONSULTA 15: GROUP BY ===");
            String jpql15 = "SELECT pv.descripcion, COUNT(f), SUM(f.importeTotal) FROM FacturaVenta f JOIN f.puntoVenta pv GROUP BY pv.descripcion";
            List<Object[]> resultados15 = em.createQuery(jpql15, Object[].class)
                    .getResultList();
            System.out.println("Puntos de venta agrupados: " + resultados15.size());

            System.out.println("\n=== CONSULTA 16: HAVING ===");
            String jpql16 = "SELECT u.usuario, COUNT(f) FROM FacturaVenta f JOIN f.usuarioCarga u GROUP BY u.usuario HAVING COUNT(f) > 5";
            List<Object[]> resultados16 = em.createQuery(jpql16, Object[].class)
                    .getResultList();
            System.out.println("Usuarios con más de 5 facturas: " + resultados16.size());

            System.out.println("\n=== CONSULTA 17: Agregación por marca ===");
            String jpql17 = "SELECT m.denominacion, SUM(d.cantidad), SUM(d.importeSubtotal) FROM FacturaVentaDetalle d JOIN d.listaPrecioArticulo lpa JOIN lpa.articulo a JOIN a.marca m GROUP BY m.denominacion";
            List<Object[]> resultados17 = em.createQuery(jpql17, Object[].class)
                    .getResultList();
            System.out.println("Marcas agrupadas por ventas: " + resultados17.size());

            System.out.println("\n=== CONSULTA 18: EXISTS ===");
            String jpql18 = "SELECT m FROM Marca m WHERE EXISTS (SELECT a FROM Articulo a WHERE a.marca = m AND a.id IN (SELECT d.listaPrecioArticulo.articulo.id FROM FacturaVentaDetalle d))";
            List<Marca> marcasFacturadas = em.createQuery(jpql18, Marca.class)
                    .getResultList();
            System.out.println("Marcas con artículos facturados: " + marcasFacturadas.size());

            System.out.println("\n=== CONSULTA 19: NOT EXISTS ===");
            String jpql19 = "SELECT a FROM Articulo a WHERE NOT EXISTS (SELECT d FROM FacturaVentaDetalle d WHERE d.listaPrecioArticulo.articulo = a)";
            List<Articulo> articulosSinVender = em.createQuery(jpql19, Articulo.class)
                    .getResultList();
            System.out.println("Artículos sin vender: " + articulosSinVender.size());

            System.out.println("\n=== CONSULTA 20: CASE WHEN ===");
            String jpql20 = "SELECT f.numero, f.importeTotal, CASE WHEN f.importeTotal > 50000 THEN 'ALTO VALOR' WHEN f.importeTotal BETWEEN 10000 AND 50000 THEN 'MEDIO VALOR' ELSE 'BAJO VALOR' END FROM FacturaVenta f ORDER BY f.importeTotal DESC";
            List<Object[]> resultados20 = em.createQuery(jpql20, Object[].class)
                    .getResultList();
            System.out.println("Facturas clasificadas: " + resultados20.size());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
            emf.close();
        }
    }
}