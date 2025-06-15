package com.lima.software.creditos_constituidos.controller;

import com.lima.software.creditos_constituidos.dto.CreditoDTO;
import com.lima.software.creditos_constituidos.exception.ResourceNotFoundException;
import com.lima.software.creditos_constituidos.service.CreditoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Fail.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

class CreditoControllerTest {

    @Mock
    private CreditoService creditoService;

    @InjectMocks
    private CreditoController creditoController;

    private CreditoDTO creditoDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        creditoDTO = new CreditoDTO();
        creditoDTO.setNumeroCredito("123456");
        creditoDTO.setNumeroNfse("7891011");
        creditoDTO.setDataConstituicao(LocalDate.of(2024, 2, 25));
        creditoDTO.setValorIssqn(new BigDecimal("1500.75"));
        creditoDTO.setTipoCredito("ISSQN");
        creditoDTO.setSimplesNacional("Sim");
        creditoDTO.setAliquota(new BigDecimal("5.0"));
        creditoDTO.setValorFaturado(new BigDecimal("30000.00"));
        creditoDTO.setValorDeducao(new BigDecimal("5000.00"));
        creditoDTO.setBaseCalculo(new BigDecimal("25000.00"));
    }

    @Test
    void findByNumeroNfse() {
        when(creditoService.findByNumeroNfse("7891011")).thenReturn(List.of(creditoDTO));

        ResponseEntity<List<CreditoDTO>> response = creditoController.findByNumeroNfse("7891011");

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals("123456", response.getBody().get(0).getNumeroCredito());
    }

    @Test
    void findByNumeroCredito() {
        when(creditoService.findByNumeroCredito("123456")).thenReturn(Optional.of(creditoDTO));

        ResponseEntity<CreditoDTO> response = creditoController.findByNumeroCredito("123456");

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("7891011", response.getBody().getNumeroNfse());
    }

    @Test
    void findByNumeroCreditoNotFound() {
        when(creditoService.findByNumeroCredito("999999")).thenThrow(
            new ResourceNotFoundException("Crédito não encontrado com o número: 999999"));

        try {
            creditoController.findByNumeroCredito("999999");
        } catch (ResourceNotFoundException ex) {
            assertEquals("Crédito não encontrado com o número: 999999", ex.getMessage());
            return;
        }

        fail("Deveria ter lançado ResourceNotFoundException");
    }
}
