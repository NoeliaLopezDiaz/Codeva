package com.facturacion.controller;

import com.facturacion.DTO.FacturaReporteDTO;
import com.facturacion.service.FacturaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/facturas")
@RequiredArgsConstructor
public class FacturaRestController {

    private final FacturaService service;

    @GetMapping
    public List<FacturaReporteDTO> listar (@RequestParam(required = false) Date fechaDesde,
                                           @RequestParam(required = false) Date fechaHasta,
                                           @RequestParam(required = false) String estado,
                                           @RequestParam(required = false) Double montoMinimo) {

        return service.buscarFacturasFiltradas(fechaDesde, fechaHasta, estado, montoMinimo);
    }

}
