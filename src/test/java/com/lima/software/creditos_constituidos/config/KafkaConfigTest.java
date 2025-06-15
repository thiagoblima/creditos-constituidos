package com.lima.software.creditos_constituidos.config;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = KafkaConfig.class)
@TestPropertySource(properties = {
    "spring.kafka.bootstrap-servers=localhost:9092"
})
class KafkaConfigTest {

    @Autowired
    private KafkaConfig kafkaConfig;

    @Test
    void kafkaAdminShouldHaveCorrectBootstrapServers() {
        // Arrange & Act
        KafkaAdmin kafkaAdmin = kafkaConfig.kafkaAdmin();
        
        // Assert
        assertNotNull(kafkaAdmin);
        assertEquals("localhost:9092", 
                kafkaAdmin.getConfigurationProperties().get(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG));
    }

    @Test
    void creditosConsultasTopicShouldBeConfiguredCorrectly() {
        // Arrange & Act
        NewTopic topic = kafkaConfig.creditosConsultasTopic();
        
        // Assert
        assertNotNull(topic);
        assertEquals("creditos-consultas", topic.name());
        assertEquals(1, topic.numPartitions());
        assertEquals((short) 1, topic.replicationFactor());
    }
}
