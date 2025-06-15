package com.lima.software.creditos_constituidos.controller;

import com.lima.software.creditos_constituidos.dto.CreditoDTO;
import com.lima.software.creditos_constituidos.service.CreditoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/creditos")
@CrossOrigin(origins = "*")
public class CreditoController {

    private final CreditoService creditoService;

    @Autowired
    public CreditoController(CreditoService creditoService) {
        this.creditoService = creditoService;
    }

    @GetMapping("/{numeroNfse}")
    public ResponseEntity<List<CreditoDTO>> findByNumeroNfse(@PathVariable String numeroNfse) {
        List<CreditoDTO> creditos = creditoService.findByNumeroNfse(numeroNfse);
        return ResponseEntity.ok(creditos);
    }

    @GetMapping("/credito/{numeroCredito}")
    public ResponseEntity<CreditoDTO> findByNumeroCredito(@PathVariable String numeroCredito) {
        CreditoDTO credito = creditoService.findByNumeroCredito(numeroCredito).orElseThrow();
        return ResponseEntity.ok(credito);
    }
}
