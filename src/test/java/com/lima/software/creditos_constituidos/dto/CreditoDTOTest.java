package com.lima.software.creditos_constituidos.dto;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class CreditoDTOTest {

    @Test
    void testNoArgsConstructor() {
        // Act
        CreditoDTO creditoDTO = new CreditoDTO();
        
        // Assert
        assertNotNull(creditoDTO);
        assertNull(creditoDTO.getNumeroCredito());
        assertNull(creditoDTO.getNumeroNfse());
        assertNull(creditoDTO.getDataConstituicao());
        assertNull(creditoDTO.getValorIssqn());
        assertNull(creditoDTO.getTipoCredito());
        assertNull(creditoDTO.getSimplesNacional());
        assertNull(creditoDTO.getAliquota());
        assertNull(creditoDTO.getValorFaturado());
        assertNull(creditoDTO.getValorDeducao());
        assertNull(creditoDTO.getBaseCalculo());
    }

    @Test
    void testAllArgsConstructor() {
        // Arrange
        String numeroCredito = "CR001";
        String numeroNfse = "NFS001";
        LocalDate dataConstituicao = LocalDate.of(2024, 1, 15);
        BigDecimal valorIssqn = new BigDecimal("1500.75");
        String tipoCredito = "ISSQN";
        String simplesNacional = "Sim";
        BigDecimal aliquota = new BigDecimal("5.0");
        BigDecimal valorFaturado = new BigDecimal("30000.00");
        BigDecimal valorDeducao = new BigDecimal("5000.00");
        BigDecimal baseCalculo = new BigDecimal("25000.00");
        
        // Act
        CreditoDTO creditoDTO = new CreditoDTO(
            numeroCredito, numeroNfse, dataConstituicao, valorIssqn, tipoCredito,
            simplesNacional, aliquota, valorFaturado, valorDeducao, baseCalculo
        );
        
        // Assert
        assertEquals(numeroCredito, creditoDTO.getNumeroCredito());
        assertEquals(numeroNfse, creditoDTO.getNumeroNfse());
        assertEquals(dataConstituicao, creditoDTO.getDataConstituicao());
        assertEquals(valorIssqn, creditoDTO.getValorIssqn());
        assertEquals(tipoCredito, creditoDTO.getTipoCredito());
        assertEquals(simplesNacional, creditoDTO.getSimplesNacional());
        assertEquals(aliquota, creditoDTO.getAliquota());
        assertEquals(valorFaturado, creditoDTO.getValorFaturado());
        assertEquals(valorDeducao, creditoDTO.getValorDeducao());
        assertEquals(baseCalculo, creditoDTO.getBaseCalculo());
    }

    @Test
    void testSettersAndGetters() {
        // Arrange
        CreditoDTO creditoDTO = new CreditoDTO();
        String numeroCredito = "CR001";
        String numeroNfse = "NFS001";
        LocalDate dataConstituicao = LocalDate.of(2024, 1, 15);
        BigDecimal valorIssqn = new BigDecimal("1500.75");
        String tipoCredito = "ISSQN";
        String simplesNacional = "Sim";
        BigDecimal aliquota = new BigDecimal("5.0");
        BigDecimal valorFaturado = new BigDecimal("30000.00");
        BigDecimal valorDeducao = new BigDecimal("5000.00");
        BigDecimal baseCalculo = new BigDecimal("25000.00");
        
        // Act
        creditoDTO.setNumeroCredito(numeroCredito);
        creditoDTO.setNumeroNfse(numeroNfse);
        creditoDTO.setDataConstituicao(dataConstituicao);
        creditoDTO.setValorIssqn(valorIssqn);
        creditoDTO.setTipoCredito(tipoCredito);
        creditoDTO.setSimplesNacional(simplesNacional);
        creditoDTO.setAliquota(aliquota);
        creditoDTO.setValorFaturado(valorFaturado);
        creditoDTO.setValorDeducao(valorDeducao);
        creditoDTO.setBaseCalculo(baseCalculo);
        
        // Assert
        assertEquals(numeroCredito, creditoDTO.getNumeroCredito());
        assertEquals(numeroNfse, creditoDTO.getNumeroNfse());
        assertEquals(dataConstituicao, creditoDTO.getDataConstituicao());
        assertEquals(valorIssqn, creditoDTO.getValorIssqn());
        assertEquals(tipoCredito, creditoDTO.getTipoCredito());
        assertEquals(simplesNacional, creditoDTO.getSimplesNacional());
        assertEquals(aliquota, creditoDTO.getAliquota());
        assertEquals(valorFaturado, creditoDTO.getValorFaturado());
        assertEquals(valorDeducao, creditoDTO.getValorDeducao());
        assertEquals(baseCalculo, creditoDTO.getBaseCalculo());
    }

    @Test
    void testEqualsAndHashCode() {
        // Arrange
        CreditoDTO creditoDTO1 = new CreditoDTO(
            "CR001", "NFS001", LocalDate.of(2024, 1, 15),
            new BigDecimal("1500.75"), "ISSQN", "Sim",
            new BigDecimal("5.0"), new BigDecimal("30000.00"),
            new BigDecimal("5000.00"), new BigDecimal("25000.00")
        );
        
        CreditoDTO creditoDTO2 = new CreditoDTO(
            "CR001", "NFS001", LocalDate.of(2024, 1, 15),
            new BigDecimal("1500.75"), "ISSQN", "Sim",
            new BigDecimal("5.0"), new BigDecimal("30000.00"),
            new BigDecimal("5000.00"), new BigDecimal("25000.00")
        );
        
        CreditoDTO creditoDTO3 = new CreditoDTO(
            "CR002", "NFS002", LocalDate.of(2024, 1, 20),
            new BigDecimal("2250.50"), "ISSQN", "Não",
            new BigDecimal("5.0"), new BigDecimal("45000.00"),
            new BigDecimal("0.00"), new BigDecimal("45000.00")
        );
        
        // Act & Assert
        assertEquals(creditoDTO1, creditoDTO2);
        assertEquals(creditoDTO1.hashCode(), creditoDTO2.hashCode());
        
        assertNotEquals(creditoDTO1, creditoDTO3);
        assertNotEquals(creditoDTO1.hashCode(), creditoDTO3.hashCode());
        
        assertNotEquals(creditoDTO1, null);
        assertNotEquals(creditoDTO1, new Object());
    }

    @Test
    void testToString() {
        // Arrange
        CreditoDTO creditoDTO = new CreditoDTO(
            "CR001", "NFS001", LocalDate.of(2024, 1, 15),
            new BigDecimal("1500.75"), "ISSQN", "Sim",
            new BigDecimal("5.0"), new BigDecimal("30000.00"),
            new BigDecimal("5000.00"), new BigDecimal("25000.00")
        );
        
        // Act
        String toStringResult = creditoDTO.toString();
        
        // Assert
        assertTrue(toStringResult.contains("numeroCredito=CR001"));
        assertTrue(toStringResult.contains("numeroNfse=NFS001"));
        assertTrue(toStringResult.contains("dataConstituicao=2024-01-15"));
        assertTrue(toStringResult.contains("valorIssqn=1500.75"));
        assertTrue(toStringResult.contains("tipoCredito=ISSQN"));
        assertTrue(toStringResult.contains("simplesNacional=Sim"));
        assertTrue(toStringResult.contains("aliquota=5.0"));
        assertTrue(toStringResult.contains("valorFaturado=30000.00"));
        assertTrue(toStringResult.contains("valorDeducao=5000.00"));
        assertTrue(toStringResult.contains("baseCalculo=25000.00"));
    }
}
