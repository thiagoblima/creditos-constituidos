package com.lima.software.creditos_constituidos;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(properties = {
    "spring.datasource.url=jdbc:h2:mem:testdb",
    "spring.datasource.username=sa",
    "spring.datasource.password=",
    "spring.datasource.driver-class-name=org.h2.Driver",
    "spring.jpa.hibernate.ddl-auto=create-drop",
    "spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.H2Dialect",
    "spring.kafka.bootstrap-servers=embedded-kafka:9092",
    "spring.kafka.consumer.auto-offset-reset=earliest",
    "spring.kafka.consumer.group-id=test-group",
    "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect"
})
class CreditosConstituidosApplicationTests {

	@Test
	void contextLoads() {
		// Verifica se o contexto da aplicação carrega corretamente
		assertTrue(true, "O contexto da aplicação foi carregado com sucesso");
	}

	@Test
	void applicationStarts() {
		// Testa se a aplicação pode ser iniciada sem erros
		CreditosConstituidosApplication.main(new String[]{"--spring.profiles.active=test"});
		assertTrue(true, "A aplicação foi iniciada com sucesso");
	}
}
