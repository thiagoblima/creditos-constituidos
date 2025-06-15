package com.lima.software.creditos_constituidos.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class CreditoTest {

    @Test
    void testNoArgsConstructor() {
        // Act
        Credito credito = new Credito();
        
        // Assert
        assertNotNull(credito);
        assertNull(credito.getId());
        assertNull(credito.getNumeroCredito());
        assertNull(credito.getNumeroNfse());
        assertNull(credito.getDataConstituicao());
        assertNull(credito.getValorIssqn());
        assertNull(credito.getTipoCredito());
        assertFalse(credito.isSimplesNacional());
        assertNull(credito.getAliquota());
        assertNull(credito.getValorFaturado());
        assertNull(credito.getValorDeducao());
        assertNull(credito.getBaseCalculo());
    }

    @Test
    void testAllArgsConstructor() {
        // Arrange
        Long id = 1L;
        String numeroCredito = "CR001";
        String numeroNfse = "NFS001";
        LocalDate dataConstituicao = LocalDate.of(2024, 1, 15);
        BigDecimal valorIssqn = new BigDecimal("1500.75");
        String tipoCredito = "ISSQN";
        boolean simplesNacional = true;
        BigDecimal aliquota = new BigDecimal("5.0");
        BigDecimal valorFaturado = new BigDecimal("30000.00");
        BigDecimal valorDeducao = new BigDecimal("5000.00");
        BigDecimal baseCalculo = new BigDecimal("25000.00");
        
        // Act
        Credito credito = new Credito(
            id, numeroCredito, numeroNfse, dataConstituicao, valorIssqn, tipoCredito,
            simplesNacional, aliquota, valorFaturado, valorDeducao, baseCalculo
        );
        
        // Assert
        assertEquals(id, credito.getId());
        assertEquals(numeroCredito, credito.getNumeroCredito());
        assertEquals(numeroNfse, credito.getNumeroNfse());
        assertEquals(dataConstituicao, credito.getDataConstituicao());
        assertEquals(valorIssqn, credito.getValorIssqn());
        assertEquals(tipoCredito, credito.getTipoCredito());
        assertEquals(simplesNacional, credito.isSimplesNacional());
        assertEquals(aliquota, credito.getAliquota());
        assertEquals(valorFaturado, credito.getValorFaturado());
        assertEquals(valorDeducao, credito.getValorDeducao());
        assertEquals(baseCalculo, credito.getBaseCalculo());
    }

    @Test
    void testSettersAndGetters() {
        // Arrange
        Credito credito = new Credito();
        Long id = 1L;
        String numeroCredito = "CR001";
        String numeroNfse = "NFS001";
        LocalDate dataConstituicao = LocalDate.of(2024, 1, 15);
        BigDecimal valorIssqn = new BigDecimal("1500.75");
        String tipoCredito = "ISSQN";
        boolean simplesNacional = true;
        BigDecimal aliquota = new BigDecimal("5.0");
        BigDecimal valorFaturado = new BigDecimal("30000.00");
        BigDecimal valorDeducao = new BigDecimal("5000.00");
        BigDecimal baseCalculo = new BigDecimal("25000.00");
        
        // Act
        credito.setId(id);
        credito.setNumeroCredito(numeroCredito);
        credito.setNumeroNfse(numeroNfse);
        credito.setDataConstituicao(dataConstituicao);
        credito.setValorIssqn(valorIssqn);
        credito.setTipoCredito(tipoCredito);
        credito.setSimplesNacional(simplesNacional);
        credito.setAliquota(aliquota);
        credito.setValorFaturado(valorFaturado);
        credito.setValorDeducao(valorDeducao);
        credito.setBaseCalculo(baseCalculo);
        
        // Assert
        assertEquals(id, credito.getId());
        assertEquals(numeroCredito, credito.getNumeroCredito());
        assertEquals(numeroNfse, credito.getNumeroNfse());
        assertEquals(dataConstituicao, credito.getDataConstituicao());
        assertEquals(valorIssqn, credito.getValorIssqn());
        assertEquals(tipoCredito, credito.getTipoCredito());
        assertEquals(simplesNacional, credito.isSimplesNacional());
        assertEquals(aliquota, credito.getAliquota());
        assertEquals(valorFaturado, credito.getValorFaturado());
        assertEquals(valorDeducao, credito.getValorDeducao());
        assertEquals(baseCalculo, credito.getBaseCalculo());
    }

    @Test
    void testEqualsAndHashCode() {
        // Arrange
        Credito credito1 = new Credito(
            1L, "CR001", "NFS001", LocalDate.of(2024, 1, 15),
            new BigDecimal("1500.75"), "ISSQN", true,
            new BigDecimal("5.0"), new BigDecimal("30000.00"),
            new BigDecimal("5000.00"), new BigDecimal("25000.00")
        );
        
        Credito credito2 = new Credito(
            1L, "CR001", "NFS001", LocalDate.of(2024, 1, 15),
            new BigDecimal("1500.75"), "ISSQN", true,
            new BigDecimal("5.0"), new BigDecimal("30000.00"),
            new BigDecimal("5000.00"), new BigDecimal("25000.00")
        );
        
        Credito credito3 = new Credito(
            2L, "CR002", "NFS002", LocalDate.of(2024, 1, 20),
            new BigDecimal("2250.50"), "ISSQN", false,
            new BigDecimal("5.0"), new BigDecimal("45000.00"),
            new BigDecimal("0.00"), new BigDecimal("45000.00")
        );
        
        // Act & Assert
        assertEquals(credito1, credito2);
        assertEquals(credito1.hashCode(), credito2.hashCode());
        
        assertNotEquals(credito1, credito3);
        assertNotEquals(credito1.hashCode(), credito3.hashCode());
        
        assertNotEquals(credito1, null);
        assertNotEquals(credito1, new Object());
    }

    @Test
    void testToString() {
        // Arrange
        Credito credito = new Credito(
            1L, "CR001", "NFS001", LocalDate.of(2024, 1, 15),
            new BigDecimal("1500.75"), "ISSQN", true,
            new BigDecimal("5.0"), new BigDecimal("30000.00"),
            new BigDecimal("5000.00"), new BigDecimal("25000.00")
        );
        
        // Act
        String toStringResult = credito.toString();
        
        // Assert
        assertTrue(toStringResult.contains("id=1"));
        assertTrue(toStringResult.contains("numeroCredito=CR001"));
        assertTrue(toStringResult.contains("numeroNfse=NFS001"));
        assertTrue(toStringResult.contains("dataConstituicao=2024-01-15"));
        assertTrue(toStringResult.contains("valorIssqn=1500.75"));
        assertTrue(toStringResult.contains("tipoCredito=ISSQN"));
        assertTrue(toStringResult.contains("simplesNacional=true"));
        assertTrue(toStringResult.contains("aliquota=5.0"));
        assertTrue(toStringResult.contains("valorFaturado=30000.00"));
        assertTrue(toStringResult.contains("valorDeducao=5000.00"));
        assertTrue(toStringResult.contains("baseCalculo=25000.00"));
    }
}