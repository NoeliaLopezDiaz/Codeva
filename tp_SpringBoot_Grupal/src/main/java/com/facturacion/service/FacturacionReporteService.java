package com.facturacion.service;
import com.facturacion.DTO.FacturaReporteDTO;
import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.io.FileOutputStream;
import java.util.List;

//Clase encargada de hacer los reportes en pdf y excel
@Service
public class FacturacionReporteService {

    @PersistenceContext
    private EntityManager em;

    public void generarReportes(){
        try
        {
            // 1. CONSULTA JPQL

            String jpql =
                    "SELECT new com.facturacion.DTO.FacturaReporteDTO(" +
                            "f.numero, " +
                            "f.fechaEmision, " +
                            "COALESCE(c.denominacion, 'Consumidor Final'), " +
                            "ci.denominacion, " +
                            "pv.descripcion, " +
                            "f.importeTotal, " +
                            "COUNT(d)) " +
                            "FROM FacturaVenta f " +
                            "LEFT JOIN f.cliente c " +
                            "JOIN f.condicionIva ci " +
                            "JOIN f.puntoVenta pv " +
                            "JOIN f.detalles d " +
                            "GROUP BY f.id, f.numero, f.fechaEmision, " +
                            "c.denominacion, ci.denominacion, " +
                            "pv.descripcion, f.importeTotal";

            List<FacturaReporteDTO> reporte =
                    em.createQuery(jpql, FacturaReporteDTO.class)
                            .getResultList();


            // 2.MOSTRAR RESULTADOS EN CONSOLA

            for (FacturaReporteDTO factura : reporte) {

                System.out.println(
                        "Factura: " + factura.getNumeroFactura() +
                                " | Fecha: " + factura.getFechaEmision() +
                                " | Cliente: " + factura.getClienteDenominacion() +
                                " | Condición IVA: " + factura.getCondicionIva() +
                                " | Punto de venta: " + factura.getPuntoVentaDescripcion() +
                                " | Total: $" + factura.getImporteTotal() +
                                " | Items: " + factura.getCantidadItems()
                );
            }

            // 3. GENERAR PDF
            generarPDF(reporte);

            // 4. GENERAR EXCEL
            generarExcel(reporte);

        } catch(Exception e) {
            e.printStackTrace();
        }

    }

    // MÉTODO PARA GENERAR PDF

    private static void generarPDF(List<FacturaReporteDTO> reporte) {

        String nombreArchivo = "reporte_facturas.pdf";

        try {

            Document documento = new Document();

            PdfWriter.getInstance(
                    documento,
                    new FileOutputStream(nombreArchivo)
            );

            documento.open();

            documento.add(
                    new Paragraph("REPORTE DE FACTURAS")
            );

            documento.add(
                    new Paragraph(" ")
            );

            PdfPTable tabla = new PdfPTable(7);

            tabla.addCell(new Phrase("Factura"));
            tabla.addCell(new Phrase("Fecha"));
            tabla.addCell(new Phrase("Cliente"));
            tabla.addCell(new Phrase("Condición IVA"));
            tabla.addCell(new Phrase("Punto de Venta"));
            tabla.addCell(new Phrase("Importe Total"));
            tabla.addCell(new Phrase("Cantidad Items"));

            for (FacturaReporteDTO factura : reporte) {

                tabla.addCell(
                        String.valueOf(factura.getNumeroFactura())
                );

                tabla.addCell(
                        String.valueOf(factura.getFechaEmision())
                );

                tabla.addCell(
                        factura.getClienteDenominacion()
                );

                tabla.addCell(
                        factura.getCondicionIva()
                );

                tabla.addCell(
                        factura.getPuntoVentaDescripcion()
                );

                tabla.addCell(
                        String.valueOf(factura.getImporteTotal())
                );

                tabla.addCell(
                        String.valueOf(factura.getCantidadItems())
                );
            }

            documento.add(tabla);

            documento.close();

            System.out.println(
                    "PDF generado correctamente: " + nombreArchivo
            );

        } catch (Exception e) {

            System.out.println(
                    "Error al generar el PDF:"
            );

            e.printStackTrace();
        }
    }


    // MÉTODO PARA GENERAR EXCEL


    private static void generarExcel(List<FacturaReporteDTO> reporte) {

        String nombreArchivo = "reporte_facturas.xlsx";

        try (Workbook workbook = new XSSFWorkbook()) {

            Sheet hoja = workbook.createSheet("Facturas");


            // ENCABEZADOS


            Row encabezado = hoja.createRow(0);

            encabezado.createCell(0)
                    .setCellValue("Factura");

            encabezado.createCell(1)
                    .setCellValue("Fecha");

            encabezado.createCell(2)
                    .setCellValue("Cliente");

            encabezado.createCell(3)
                    .setCellValue("Condición IVA");

            encabezado.createCell(4)
                    .setCellValue("Punto de Venta");

            encabezado.createCell(5)
                    .setCellValue("Importe Total");

            encabezado.createCell(6)
                    .setCellValue("Cantidad Items");

            // DATOS

            int fila = 1;

            for (FacturaReporteDTO factura : reporte) {

                Row row = hoja.createRow(fila);

                row.createCell(0)
                        .setCellValue(
                                factura.getNumeroFactura()
                        );

                row.createCell(1)
                        .setCellValue(
                                String.valueOf(
                                        factura.getFechaEmision()
                                )
                        );

                row.createCell(2)
                        .setCellValue(
                                factura.getClienteDenominacion()
                        );

                row.createCell(3)
                        .setCellValue(
                                factura.getCondicionIva()
                        );

                row.createCell(4)
                        .setCellValue(
                                factura.getPuntoVentaDescripcion()
                        );

                row.createCell(5)
                        .setCellValue(
                                factura.getImporteTotal()
                        );

                row.createCell(6)
                        .setCellValue(
                                factura.getCantidadItems()
                        );

                fila++;
            }

            // AJUSTAR ANCHO DE COLUMNAS

            for (int i = 0; i < 7; i++) {
                hoja.autoSizeColumn(i);
            }

            // GUARDAR ARCHIVO

            FileOutputStream archivo =
                    new FileOutputStream(nombreArchivo);

            workbook.write(archivo);

            archivo.close();

            System.out.println(
                    "Excel generado correctamente: " + nombreArchivo
            );

        } catch (Exception e) {

            System.out.println(
                    "Error al generar el Excel:"
            );

            e.printStackTrace();
        }
    }
}
