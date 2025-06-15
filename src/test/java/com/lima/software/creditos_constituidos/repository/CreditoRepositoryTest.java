package com.lima.software.creditos_constituidos.repository;

import com.lima.software.creditos_constituidos.model.Credito;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class CreditoRepositoryTest {

    @Autowired
    private CreditoRepository creditoRepository;

    @Test
    void findByNumeroNfse_shouldReturnCreditos() {
        // Arrange
        Credito credito1 = createCredito("CR001", "NFS001");
        Credito credito2 = createCredito("CR002", "NFS001");
        Credito credito3 = createCredito("CR003", "NFS002");
        
        creditoRepository.save(credito1);
        creditoRepository.save(credito2);
        creditoRepository.save(credito3);
        
        // Act
        List<Credito> result = creditoRepository.findByNumeroNfse("NFS001");
        
        // Assert
        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(c -> c.getNumeroCredito().equals("CR001")));
        assertTrue(result.stream().anyMatch(c -> c.getNumeroCredito().equals("CR002")));
    }
    
    @Test
    void findByNumeroNfse_shouldReturnEmptyList_whenNoMatch() {
        // Arrange
        Credito credito = createCredito("CR001", "NFS001");
        creditoRepository.save(credito);
        
        // Act
        List<Credito> result = creditoRepository.findByNumeroNfse("NFS999");
        
        // Assert
        assertTrue(result.isEmpty());
    }

    @Test
    void findByNumeroCredito_shouldReturnCredito() {
        // Arrange
        Credito credito = createCredito("CR001", "NFS001");
        creditoRepository.save(credito);
        
        // Act
        Optional<Credito> result = creditoRepository.findByNumeroCredito("CR001");
        
        // Assert
        assertTrue(result.isPresent());
        assertEquals("NFS001", result.get().getNumeroNfse());
    }
    
    @Test
    void findByNumeroCredito_shouldReturnEmpty_whenNoMatch() {
        // Arrange
        Credito credito = createCredito("CR001", "NFS001");
        creditoRepository.save(credito);
        
        // Act
        Optional<Credito> result = creditoRepository.findByNumeroCredito("CR999");
        
        // Assert
        assertTrue(result.isEmpty());
    }
    
    @Test
    void findAll_shouldReturnAllCreditos() {
        // Arrange
        Credito credito1 = createCredito("CR001", "NFS001");
        Credito credito2 = createCredito("CR002", "NFS002");
        
        creditoRepository.save(credito1);
        creditoRepository.save(credito2);
        
        // Act
        Iterable<Credito> resultIterable = creditoRepository.findAll();
        List<Credito> result = StreamSupport.stream(resultIterable.spliterator(), false)
                                           .collect(Collectors.toList());
        
        // Assert
        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(c -> c.getNumeroCredito().equals("CR001")));
        assertTrue(result.stream().anyMatch(c -> c.getNumeroCredito().equals("CR002")));
    }
    
    @Test
    void save_shouldPersistCredito() {
        // Arrange
        Credito credito = createCredito("CR001", "NFS001");
        
        // Act
        Credito savedCredito = creditoRepository.save(credito);
        
        // Assert
        assertNotNull(savedCredito.getId());
        
        Optional<Credito> foundCredito = creditoRepository.findById(savedCredito.getId());
        assertTrue(foundCredito.isPresent());
        assertEquals("CR001", foundCredito.get().getNumeroCredito());
    }
    
    @Test
    void deleteById_shouldRemoveCredito() {
        // Arrange
        Credito credito = createCredito("CR001", "NFS001");
        Credito savedCredito = creditoRepository.save(credito);
        
        // Act
        creditoRepository.deleteById(savedCredito.getId());
        
        // Assert
        Optional<Credito> foundCredito = creditoRepository.findById(savedCredito.getId());
        assertFalse(foundCredito.isPresent());
    }
    
    private Credito createCredito(String numeroCredito, String numeroNfse) {
        Credito credito = new Credito();
        credito.setNumeroCredito(numeroCredito);
        credito.setNumeroNfse(numeroNfse);
        credito.setDataConstituicao(LocalDate.now());
        credito.setValorIssqn(new BigDecimal("1500.75"));
        credito.setTipoCredito("ISSQN");
        credito.setSimplesNacional(true);
        credito.setAliquota(new BigDecimal("5.0"));
        credito.setValorFaturado(new BigDecimal("30000.00"));
        credito.setValorDeducao(new BigDecimal("5000.00"));
        credito.setBaseCalculo(new BigDecimal("25000.00"));
        return credito;
    }
}