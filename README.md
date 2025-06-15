# Créditos Constituídos API

API de gerenciamento de créditos constituídos para controle de ISSQN (Imposto Sobre Serviços de Qualquer Natureza).

## Sobre o Projeto

Este projeto é uma API RESTful desenvolvida com Spring Boot para gerenciar créditos constituídos relacionados a notas fiscais de serviço eletrônicas (NFS-e). A aplicação permite consultar créditos por número de NFS-e ou número de crédito, registrando eventos de consulta em um tópico Kafka para análise posterior.

## Tecnologias Utilizadas

- **Java 17**
- **Spring Boot 3.5.0**
- **Spring Data JPA**
- **PostgreSQL** (banco de dados principal)
- **H2 Database** (para testes)
- **Apache Kafka** (para mensageria)
- **Docker & Docker Compose** (para containerização)
- **Maven** (gerenciamento de dependências)

## Estrutura do Projeto

```
creditos-constituidos/
├── src/
│   ├── main/
│   │   ├── java/com/lima/software/creditos_constituidos/
│   │   │   ├── config/           # Configurações (Kafka, etc.)
│   │   │   ├── controller/       # Controladores REST
│   │   │   ├── dto/              # Objetos de Transferência de Dados
│   │   │   ├── exception/        # Tratamento de exceções
│   │   │   ├── model/            # Entidades JPA
│   │   │   ├── repository/       # Repositórios Spring Data
│   │   │   ├── service/          # Camada de serviço
│   │   │   └── CreditosConstituidosApplication.java
│   │   └── resources/
│   │       ├── db/               # Scripts SQL
│   │       ├── application.yml   # Configurações da aplicação
│   │       └── application.properties
│   └── test/                     # Testes automatizados
├── .mvn/                         # Configurações do Maven Wrapper
├── docker-compose.yml            # Configuração do Docker Compose
├── Dockerfile                    # Configuração do Docker
├── mvnw                          # Maven Wrapper (Unix)
├── mvnw.cmd                      # Maven Wrapper (Windows)
└── pom.xml                       # Configuração do Maven
```

## APIs Disponíveis

### Consulta de Créditos por Número de NFS-e

```
GET /api/creditos/{numeroNfse}
```

Retorna todos os créditos associados a uma NFS-e específica.

**Exemplo de resposta:**
```json
[
  {
    "numeroCredito": "CR001",
    "numeroNfse": "NFS001",
    "dataConstituicao": "2024-01-15",
    "valorIssqn": 1500.75,
    "tipoCredito": "ISSQN",
    "simplesNacional": true,
    "aliquota": 5.00,
    "valorFaturado": 30000.00,
    "valorDeducao": 5000.00,
    "baseCalculo": 25000.00
  },
  {
    "numeroCredito": "CR004",
    "numeroNfse": "NFS001",
    "dataConstituicao": "2024-02-10",
    "valorIssqn": 1800.00,
    "tipoCredito": "ISSQN",
    "simplesNacional": true,
    "aliquota": 5.00,
    "valorFaturado": 36000.00,
    "valorDeducao": 0.00,
    "baseCalculo": 36000.00
  }
]
```

### Consulta de Crédito por Número de Crédito

```
GET /api/creditos/credito/{numeroCredito}
```

Retorna um crédito específico pelo seu número.

**Exemplo de resposta:**
```json
{
  "numeroCredito": "CR001",
  "numeroNfse": "NFS001",
  "dataConstituicao": "2024-01-15",
  "valorIssqn": 1500.75,
  "tipoCredito": "ISSQN",
  "simplesNacional": true,
  "aliquota": 5.00,
  "valorFaturado": 30000.00,
  "valorDeducao": 5000.00,
  "baseCalculo": 25000.00
}
```

## Configuração e Execução

### Pré-requisitos

- Docker e Docker Compose
- Java 17 (para desenvolvimento local)
- Maven (opcional, pode usar o Maven Wrapper incluído)

### Executando com Docker Compose

1. Clone o repositório:
   ```bash
   git clone https://github.com/seu-usuario/creditos-constituidos.git
   cd creditos-constituidos
   ```

2. Inicie os contêineres com Docker Compose:
   ```bash
   docker-compose up -d
   ```

3. Verifique se todos os serviços estão em execução:
   ```bash
   docker-compose ps
   ```

4. A API estará disponível em:
   ```
   http://localhost:8080/api/creditos
   ```

5. O Kafka UI estará disponível em:
   ```
   http://localhost:8090
   ```

### Executando Localmente (Desenvolvimento)

1. Inicie apenas os serviços de infraestrutura:
   ```bash
   docker-compose up -d postgres kafka zookeeper kafka-ui
   ```

2. Execute a aplicação com Maven:
   ```bash
   ./mvnw spring-boot:run
   ```

### Executando Testes

```bash
./mvnw test
```

## Monitoramento de Eventos Kafka

A aplicação registra eventos de consulta no tópico Kafka `creditos-consultas`. Você pode monitorar esses eventos através do Kafka UI disponível em `http://localhost:8090`.

## Parando a Aplicação

```bash
docker-compose down
```

Para remover volumes e redes:
```bash
docker-compose down -v
```

## Desenvolvimento

### Adicionando Novas Funcionalidades

1. Crie uma nova branch:
   ```bash
   git checkout -b feature/nova-funcionalidade
   ```

2. Implemente a funcionalidade e testes

3. Execute os testes:
   ```bash
   ./mvnw test
   ```

4. Envie as alterações:
   ```bash
   git push origin feature/nova-funcionalidade
   ```

## Licença

Este projeto está licenciado sob a licença MIT - consulte o arquivo LICENSE para obter detalhes.