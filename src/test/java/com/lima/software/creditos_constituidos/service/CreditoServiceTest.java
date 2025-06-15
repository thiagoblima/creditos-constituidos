package com.lima.software.creditos_constituidos.service;

import com.lima.software.creditos_constituidos.dto.CreditoDTO;
import com.lima.software.creditos_constituidos.exception.ResourceNotFoundException;
import com.lima.software.creditos_constituidos.model.Credito;
import com.lima.software.creditos_constituidos.repository.CreditoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CreditoServiceTest {

    @Mock
    private CreditoRepository creditoRepository;

    @Mock
    private KafkaProducerService kafkaProducerService;

    @InjectMocks
    private CreditoService creditoService;

    private Credito credito;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        credito = new Credito();
        credito.setId(1L);
        credito.setNumeroCredito("123456");
        credito.setNumeroNfse("7891011");
        credito.setDataConstituicao(LocalDate.of(2024, 2, 25));
        credito.setValorIssqn(new BigDecimal("1500.75"));
        credito.setTipoCredito("ISSQN");
        credito.setSimplesNacional(true);
        credito.setAliquota(new BigDecimal("5.0"));
        credito.setValorFaturado(new BigDecimal("30000.00"));
        credito.setValorDeducao(new BigDecimal("5000.00"));
        credito.setBaseCalculo(new BigDecimal("25000.00"));
    }

    @Test
    void findByNumeroNfse_shouldReturnCreditoDTOList_whenCreditsExist() {
        // Arrange
        when(creditoRepository.findByNumeroNfse("7891011")).thenReturn(List.of(credito));
        doNothing().when(kafkaProducerService).sendConsultaEvent(anyString());

        // Act
        List<CreditoDTO> result = creditoService.findByNumeroNfse("7891011");

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("123456", result.get(0).getNumeroCredito());
        assertEquals("7891011", result.get(0).getNumeroNfse());
        assertEquals(LocalDate.of(2024, 2, 25), result.get(0).getDataConstituicao());
        assertEquals(new BigDecimal("1500.75"), result.get(0).getValorIssqn());
        assertEquals("ISSQN", result.get(0).getTipoCredito());
        assertEquals("Sim", result.get(0).getSimplesNacional());
        assertEquals(new BigDecimal("5.0"), result.get(0).getAliquota());
        assertEquals(new BigDecimal("30000.00"), result.get(0).getValorFaturado());
        assertEquals(new BigDecimal("5000.00"), result.get(0).getValorDeducao());
        assertEquals(new BigDecimal("25000.00"), result.get(0).getBaseCalculo());
        
        verify(kafkaProducerService, times(1)).sendConsultaEvent("Consulta por NFS-e: 7891011");
    }

    @Test
    void findByNumeroNfse_shouldThrowResourceNotFoundException_whenNoCreditsExist() {
        // Arrange
        when(creditoRepository.findByNumeroNfse("7891011")).thenReturn(new ArrayList<>());

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            creditoService.findByNumeroNfse("7891011");
        });
        
        assertEquals("Nenhum crédito encontrado para a NFS-e: 7891011", exception.getMessage());
        verify(kafkaProducerService, never()).sendConsultaEvent(anyString());
    }

    @Test
    void findByNumeroCredito_shouldReturnCreditoDTO_whenCreditExists() {
        // Arrange
        when(creditoRepository.findByNumeroCredito("123456")).thenReturn(Optional.of(credito));
        doNothing().when(kafkaProducerService).sendConsultaEvent(anyString());

        // Act
        Optional<CreditoDTO> result = creditoService.findByNumeroCredito("123456");

        // Assert
        assertTrue(result.isPresent());
        assertEquals("123456", result.get().getNumeroCredito());
        assertEquals("7891011", result.get().getNumeroNfse());
        assertEquals(LocalDate.of(2024, 2, 25), result.get().getDataConstituicao());
        assertEquals(new BigDecimal("1500.75"), result.get().getValorIssqn());
        assertEquals("ISSQN", result.get().getTipoCredito());
        assertEquals("Sim", result.get().getSimplesNacional());
        assertEquals(new BigDecimal("5.0"), result.get().getAliquota());
        assertEquals(new BigDecimal("30000.00"), result.get().getValorFaturado());
        assertEquals(new BigDecimal("5000.00"), result.get().getValorDeducao());
        assertEquals(new BigDecimal("25000.00"), result.get().getBaseCalculo());
        
        verify(kafkaProducerService, times(1)).sendConsultaEvent("Consulta por Crédito: 123456");
    }

    @Test
    void findByNumeroCredito_shouldThrowResourceNotFoundException_whenNoCreditExists() {
        // Arrange
        when(creditoRepository.findByNumeroCredito("123456")).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            creditoService.findByNumeroCredito("123456");
        });
        
        assertEquals("Crédito não encontrado com o número: 123456", exception.getMessage());
        verify(kafkaProducerService, never()).sendConsultaEvent(anyString());
    }

    @Test
    void convertToDTO_shouldConvertCreditoToDTO() {
        // Arrange
        Credito creditoFalse = new Credito();
        creditoFalse.setId(2L);
        creditoFalse.setNumeroCredito("654321");
        creditoFalse.setNumeroNfse("1101987");
        creditoFalse.setDataConstituicao(LocalDate.of(2024, 3, 15));
        creditoFalse.setValorIssqn(new BigDecimal("2000.50"));
        creditoFalse.setTipoCredito("ISSQN");
        creditoFalse.setSimplesNacional(false);
        creditoFalse.setAliquota(new BigDecimal("5.0"));
        creditoFalse.setValorFaturado(new BigDecimal("40000.00"));
        creditoFalse.setValorDeducao(new BigDecimal("0.00"));
        creditoFalse.setBaseCalculo(new BigDecimal("40000.00"));

        // Act - Usando o método privado através de um método público
        when(creditoRepository.findByNumeroCredito("654321")).thenReturn(Optional.of(creditoFalse));
        doNothing().when(kafkaProducerService).sendConsultaEvent(anyString());
        Optional<CreditoDTO> result = creditoService.findByNumeroCredito("654321");

        // Assert
        assertTrue(result.isPresent());
        assertEquals("654321", result.get().getNumeroCredito());
        assertEquals("1101987", result.get().getNumeroNfse());
        assertEquals(LocalDate.of(2024, 3, 15), result.get().getDataConstituicao());
        assertEquals(new BigDecimal("2000.50"), result.get().getValorIssqn());
        assertEquals("ISSQN", result.get().getTipoCredito());
        assertEquals("Não", result.get().getSimplesNacional());
        assertEquals(new BigDecimal("5.0"), result.get().getAliquota());
        assertEquals(new BigDecimal("40000.00"), result.get().getValorFaturado());
        assertEquals(new BigDecimal("0.00"), result.get().getValorDeducao());
        assertEquals(new BigDecimal("40000.00"), result.get().getBaseCalculo());
    }

    @Test
    void constructor_shouldInitializeDependencies() {
        // Arrange
        CreditoRepository mockRepo = mock(CreditoRepository.class);
        KafkaProducerService mockKafka = mock(KafkaProducerService.class);
        
        // Act
        CreditoService service = new CreditoService(mockRepo, mockKafka);
        
        // Assert - Verificar indiretamente se as dependências foram inicializadas
        when(mockRepo.findByNumeroNfse("test")).thenReturn(List.of(credito));
        doNothing().when(mockKafka).sendConsultaEvent(anyString());
        
        List<CreditoDTO> result = service.findByNumeroNfse("test");
        
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(mockRepo).findByNumeroNfse("test");
        verify(mockKafka).sendConsultaEvent("Consulta por NFS-e: test");
    }
}
