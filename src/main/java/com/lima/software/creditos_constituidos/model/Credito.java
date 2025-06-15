package com.lima.software.creditos_constituidos.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "credito")
public class Credito {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    private Long id;
    
    @Column(name = "numero_credito", nullable = false)
    private String numeroCredito;
    
    @Column(name = "numero_nfse", nullable = false)
    private String numeroNfse;
    
    @Column(name = "data_constituicao", nullable = false)
    private LocalDate dataConstituicao;
    
    @Column(name = "valor_issqn", nullable = false)
    private BigDecimal valorIssqn;
    
    @Column(name = "tipo_credito", nullable = false)
    private String tipoCredito;
    
    @Column(name = "simples_nacional", nullable = false)
    private boolean simplesNacional;
    
    @Column(name = "aliquota", nullable = false)
    private BigDecimal aliquota;
    
    @Column(name = "valor_faturado", nullable = false)
    private BigDecimal valorFaturado;
    
    @Column(name = "valor_deducao", nullable = false)
    private BigDecimal valorDeducao;
    
    @Column(name = "base_calculo", nullable = false)
    private BigDecimal baseCalculo;
}
