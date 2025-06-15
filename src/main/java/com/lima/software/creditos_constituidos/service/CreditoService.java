package com.lima.software.creditos_constituidos.service;

import com.lima.software.creditos_constituidos.dto.CreditoDTO;
import com.lima.software.creditos_constituidos.exception.ResourceNotFoundException;
import com.lima.software.creditos_constituidos.model.Credito;
import com.lima.software.creditos_constituidos.repository.CreditoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CreditoService {

    private final CreditoRepository creditoRepository;
    private final KafkaProducerService kafkaProducerService;

    @Autowired
    public CreditoService(CreditoRepository creditoRepository, KafkaProducerService kafkaProducerService) {
        this.creditoRepository = creditoRepository;
        this.kafkaProducerService = kafkaProducerService;
    }

    public List<CreditoDTO> findByNumeroNfse(String numeroNfse) {
        List<Credito> creditos = creditoRepository.findByNumeroNfse(numeroNfse);
        
        if (creditos.isEmpty()) {
            throw new ResourceNotFoundException("Nenhum crédito encontrado para a NFS-e: " + numeroNfse);
        }
        
        List<CreditoDTO> creditosDTO = creditos.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        
        kafkaProducerService.sendConsultaEvent("Consulta por NFS-e: " + numeroNfse);
        return creditosDTO;
    }

    public Optional<CreditoDTO> findByNumeroCredito(String numeroCredito) {
        Optional<Credito> credito = creditoRepository.findByNumeroCredito(numeroCredito);
        
        if (credito.isEmpty()) {
            throw new ResourceNotFoundException("Crédito não encontrado com o número: " + numeroCredito);
        }
        
        Optional<CreditoDTO> creditoDTO = credito.map(this::convertToDTO);
        
        kafkaProducerService.sendConsultaEvent("Consulta por Crédito: " + numeroCredito);
        return creditoDTO;
    }

    private CreditoDTO convertToDTO(Credito credito) {
        CreditoDTO dto = new CreditoDTO();
        dto.setNumeroCredito(credito.getNumeroCredito());
        dto.setNumeroNfse(credito.getNumeroNfse());
        dto.setDataConstituicao(credito.getDataConstituicao());
        dto.setValorIssqn(credito.getValorIssqn());
        dto.setTipoCredito(credito.getTipoCredito());
        dto.setSimplesNacional(credito.isSimplesNacional() ? "Sim" : "Não");
        dto.setAliquota(credito.getAliquota());
        dto.setValorFaturado(credito.getValorFaturado());
        dto.setValorDeducao(credito.getValorDeducao());
        dto.setBaseCalculo(credito.getBaseCalculo());
        return dto;
    }
}
