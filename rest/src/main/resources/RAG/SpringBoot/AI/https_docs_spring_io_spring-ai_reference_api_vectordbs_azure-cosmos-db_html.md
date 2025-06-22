source URL: https://docs.spring.io/spring-ai/reference/api/vectordbs/azure-cosmos-db.html 
________________________________
Why Spring

Overview Microservices Reactive Event Driven Cloud Web Applications Serverless
Batch

Learn

Overview Quickstart Guides Blog

Projects

Overview Spring Boot Spring Framework Spring Cloud Spring Cloud Data Flow Spring
Data Spring Integration Spring Batch Spring Security View all projects

* DEVELOPMENT TOOLS
Spring Tools 4 Spring Initializr

Academy

Courses Get Certified

Solutions

Overview Spring Runtime Spring Consulting Spring Academy For Teams Security
Advisories

Community

Overview Events Team

light

Spring AI 1.0.0

Search CTRL + k

  *     * Overview
      * AI Concepts
    * Getting Started
    * Reference
      * Chat Client API
        * Advisors
      * Prompts
      * Structured Output
      * Multimodality
      * Models
        * Chat Models
          * Chat Models Comparison
          * Amazon Bedrock Converse
          * Anthropic 3
          * Azure OpenAI
          * DeepSeek
          * Docker Model Runner
          * Google VertexAI
            * VertexAI Gemini
          * Groq
          * Hugging Face
          * Mistral AI
          * MiniMax
          * Moonshot AI
          * NVIDIA
          * Ollama
          * Perplexity AI
          * OCI Generative AI
            * Cohere
          * OpenAI
          * QianFan
          * ZhiPu AI
        * Embedding Models
          * Amazon Bedrock
            * Cohere
            * Titan
          * Azure OpenAI
          * Mistral AI
          * MiniMax
          * OCI GenAI
          * Ollama
          * (ONNX) Transformers
          * OpenAI
          * PostgresML
          * QianFan
          * VertexAI
            * Text Embedding
            * Multimodal Embedding
          * ZhiPu AI
        * Image Models
          * Azure OpenAI
          * OpenAI
          * Stability
          * ZhiPuAI
          * QianFan
        * Audio Models
          * Transcription API
            * Azure OpenAI
            * OpenAI
          * Text-To-Speech (TTS) API
            * OpenAI
        * Moderation Models
          * OpenAI
          * Mistral AI
      * Chat Memory
      * Tool Calling
      * Model Context Protocol (MCP)
        * MCP Client Boot Starters
        * MCP Server Boot Starters
        * MCP Utilities
      * Retrieval Augmented Generation (RAG)
        * ETL Pipeline
      * Model Evaluation
      * Vector Databases
        * Azure AI Service
        * Azure Cosmos DB
        * Apache Cassandra Vector Store
        * Chroma
        * Couchbase
        * Elasticsearch
        * GemFire
        * MariaDB Vector Store
        * Milvus
        * MongoDB Atlas
        * Neo4j
        * OpenSearch
        * Oracle
        * PGvector
        * Pinecone
        * Qdrant
        * Redis
        * SAP Hana
        * Typesense
        * Weaviate
      * Observability
      * Development-time Services
      * Testing
        * Testcontainers
    * Guides
      * Awesome Spring AI
      * Prompt Engineering Patterns
      * Building Effective Agents
      * Deploying to the Cloud
  *     * Upgrade Notes
      * Migrating FunctionCallback to ToolCallback API

Search CTRL + k

### Azure Cosmos DB

  * What is Azure Cosmos DB?
  * What is DiskANN?
  * Setting up Azure Cosmos DB Vector Store with Auto Configuration
  * Auto Configuration
  * Configuration Properties
  * Complex Searches with Filters
  * Setting up Azure Cosmos DB Vector Store without Auto Configuration
  * Manual Dependency Setup
  * Accessing the Native Client

  * Spring AI
  * Reference
  * Vector Databases
  * Azure Cosmos DB

# Azure Cosmos DB

### Azure Cosmos DB

  * What is Azure Cosmos DB?
  * What is DiskANN?
  * Setting up Azure Cosmos DB Vector Store with Auto Configuration
  * Auto Configuration
  * Configuration Properties
  * Complex Searches with Filters
  * Setting up Azure Cosmos DB Vector Store without Auto Configuration
  * Manual Dependency Setup
  * Accessing the Native Client

This section walks you through setting up `CosmosDBVectorStore` to store
document embeddings and perform similarity searches.

##  What is Azure Cosmos DB?

Azure Cosmos DB is Microsoft’s globally distributed cloud-native database
service designed for mission-critical applications. It offers high availability,
low latency, and the ability to scale horizontally to meet modern application
demands. It was built from the ground up with global distribution, fine-grained
multi-tenancy, and horizontal scalability at its core. It is a foundational
service in Azure, used by most of Microsoft’s mission critical applications at
global scale, including Teams, Skype, Xbox Live, Office 365, Bing, Azure Active
Directory, Azure Portal, Microsoft Store, and many others. It is also used by
thousands of external customers including OpenAI for ChatGPT and other mission-
critical AI applications that require elastic scale, turnkey global
distribution, and low latency and high availability across the planet.

##  What is DiskANN?

DiskANN (Disk-based Approximate Nearest Neighbor Search) is an innovative
technology used in Azure Cosmos DB to enhance the performance of vector
searches. It enables efficient and scalable similarity searches across high-
dimensional data by indexing embeddings stored in Cosmos DB.

DiskANN provides the following benefits:

  * **Efficiency** : By utilizing disk-based structures, DiskANN significantly reduces the time required to find nearest neighbors compared to traditional methods.
  * **Scalability** : It can handle large datasets that exceed memory capacity, making it suitable for various applications, including machine learning and AI-driven solutions.
  * **Low Latency** : DiskANN minimizes latency during search operations, ensuring that applications can retrieve results quickly even with substantial data volumes.

In the context of Spring AI for Azure Cosmos DB, vector searches will create and
leverage DiskANN indexes to ensure optimal performance for similarity queries.

##  Setting up Azure Cosmos DB Vector Store with Auto Configuration

The following code demonstrates how to set up the `CosmosDBVectorStore` with
auto-configuration:

```

package com.example.demo;

import io.micrometer.observation.ObservationRegistry;

import org.slf4j.Logger;

import org.slf4j.LoggerFactory;

import org.springframework.ai.document.Document;

import org.springframework.ai.vectorstore.SearchRequest;

import org.springframework.ai.vectorstore.VectorStore;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import org.springframework.boot.CommandLineRunner;

import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Lazy;

import java.util.List;

import java.util.Map;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootApplication

@EnableAutoConfiguration

public class DemoApplication implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DemoApplication.class);

    @Lazy
    @Autowired
    private VectorStore vectorStore;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Document document1 = new Document(UUID.randomUUID().toString(), "Sample content1", Map.of("key1", "value1"));
        Document document2 = new Document(UUID.randomUUID().toString(), "Sample content2", Map.of("key2", "value2"));
		this.vectorStore.add(List.of(document1, document2));
        List<Document> results = this.vectorStore.similaritySearch(SearchRequest.builder().query("Sample content").topK(1).build());

        log.info("Search results: {}", results);

        // Remove the documents from the vector store
		this.vectorStore.delete(List.of(document1.getId(), document2.getId()));
    }

    @Bean
    public ObservationRegistry observationRegistry() {
        return ObservationRegistry.create();
    }
}

Copied!

```

##  Auto Configuration

|  There has been a significant change in the Spring AI auto-configuration,
starter modules' artifact names. Please refer to the upgrade notes for more
information.  
---|---  
Add the following dependency to your Maven project:

```

<dependency>

    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-starter-vector-store-azure-cosmos-db</artifactId>
</dependency>

Copied!

```

##  Configuration Properties

The following configuration properties are available for the Cosmos DB vector
store:

Property | Description  
---|---  
spring.ai.vectorstore.cosmosdb.databaseName | The name of the Cosmos DB database to use.  
spring.ai.vectorstore.cosmosdb.containerName | The name of the Cosmos DB container to use.  
spring.ai.vectorstore.cosmosdb.partitionKeyPath | The path for the partition key.  
spring.ai.vectorstore.cosmosdb.metadataFields | Comma-separated list of metadata fields.  
spring.ai.vectorstore.cosmosdb.vectorStoreThroughput | The throughput for the vector store.  
spring.ai.vectorstore.cosmosdb.vectorDimensions | The number of dimensions for the vectors.  
spring.ai.vectorstore.cosmosdb.endpoint | The endpoint for the Cosmos DB.  
spring.ai.vectorstore.cosmosdb.key | The key for the Cosmos DB (if key is not present, [DefaultAzureCredential](learn.microsoft.com/azure/developer/java/sdk/authentication/credential-chains#defaultazurecredential-overview) will be used).  
##  Complex Searches with Filters

You can perform more complex searches using filters in the Cosmos DB vector
store. Below is a sample demonstrating how to use filters in your search
queries.

```

Map<String, Object> metadata1 = new HashMap<>();

metadata1.put("country", "UK");

metadata1.put("year", 2021);

metadata1.put("city", "London");

Map<String, Object> metadata2 = new HashMap<>();

metadata2.put("country", "NL");

metadata2.put("year", 2022);

metadata2.put("city", "Amsterdam");

Document document1 = new Document("1", "A document about the UK",
this.metadata1);

Document document2 = new Document("2", "A document about the Netherlands",
this.metadata2);

vectorStore.add(List.of(document1, document2));

FilterExpressionBuilder builder = new FilterExpressionBuilder();

List<Document> results =
vectorStore.similaritySearch(SearchRequest.builder().query("The World")

    .topK(10)
    .filterExpression((this.builder.in("country", "UK", "NL")).build()).build());
Copied!

```

##  Setting up Azure Cosmos DB Vector Store without Auto Configuration

The following code demonstrates how to set up the `CosmosDBVectorStore` without
relying on auto-configuration.
[DefaultAzureCredential](learn.microsoft.com/azure/developer/java/sdk/authentication/credential-
chains#defaultazurecredential-overview) is recommended for authentication to
Azure Cosmos DB.

```

@Bean

public VectorStore vectorStore(ObservationRegistry observationRegistry) {

    // Create the Cosmos DB client
    CosmosAsyncClient cosmosClient = new CosmosClientBuilder()
            .endpoint(System.getenv("COSMOSDB_AI_ENDPOINT"))
            .credential(new DefaultAzureCredentialBuilder().build())
            .userAgentSuffix("SpringAI-CDBNoSQL-VectorStore")
            .gatewayMode()
            .buildAsyncClient();

    // Create and configure the vector store
    return CosmosDBVectorStore.builder(cosmosClient, embeddingModel)
            .databaseName("test-database")
            .containerName("test-container")
            // Configure metadata fields for filtering
            .metadataFields(List.of("country", "year", "city"))
            // Set the partition key path (optional)
            .partitionKeyPath("/id")
            // Configure performance settings
            .vectorStoreThroughput(1000)
            .vectorDimensions(1536)  // Match your embedding model's dimensions
            // Add custom batching strategy (optional)
            .batchingStrategy(new TokenCountBatchingStrategy())
            // Add observation registry for metrics
            .observationRegistry(observationRegistry)
            .build();
}

@Bean

public EmbeddingModel embeddingModel() {

    return new TransformersEmbeddingModel();
}

Copied!

```

This configuration shows all the available builder options:

  * `databaseName`: The name of your Cosmos DB database
  * `containerName`: The name of your container within the database
  * `partitionKeyPath`: The path for the partition key (e.g., "/id")
  * `metadataFields`: List of metadata fields that will be used for filtering
  * `vectorStoreThroughput`: The throughput (RU/s) for the vector store container
  * `vectorDimensions`: The number of dimensions for your vectors (should match your embedding model)
  * `batchingStrategy`: Strategy for batching document operations (optional)

##  Manual Dependency Setup

Add the following dependency in your Maven project:

```

<dependency>

    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-azure-cosmos-db-store</artifactId>
</dependency>

Copied!

```

##  Accessing the Native Client

The Azure Cosmos DB Vector Store implementation provides access to the
underlying native Azure Cosmos DB client (`CosmosClient`) through the
`getNativeClient()` method:

```

CosmosDBVectorStore vectorStore = context.getBean(CosmosDBVectorStore.class);

Optional<CosmosClient> nativeClient = vectorStore.getNativeClient();

if (nativeClient.isPresent()) {

    CosmosClient client = nativeClient.get();
    // Use the native client for Azure Cosmos DB-specific operations
}

Copied!

```

The native client gives you access to Azure Cosmos DB-specific features and
operations that might not be exposed through the `VectorStore` interface.

Azure AI Service Apache Cassandra Vector Store

  * Spring AI
    * 1.0.0 
    * 1.1.0-SNAPSHOT 

  * Related Spring Documentation 
    * Spring Boot 
    * Spring Framework 
    * Spring Cloud 
      * Spring Cloud Build 
      * Spring Cloud Bus 
      * Spring Cloud Circuit Breaker 
      * Spring Cloud Commons 
      * Spring Cloud Config 
      * Spring Cloud Consul 
      * Spring Cloud Contract 
      * Spring Cloud Function 
      * Spring Cloud Gateway 
      * Spring Cloud Kubernetes 
      * Spring Cloud Netflix 
      * Spring Cloud OpenFeign 
      * Spring Cloud Stream 
      * Spring Cloud Task 
      * Spring Cloud Vault 
      * Spring Cloud Zookeeper 
    * Spring Data 
      * Spring Data Cassandra 
      * Spring Data Commons 
      * Spring Data Couchbase 
      * Spring Data Elasticsearch 
      * Spring Data JPA 
      * Spring Data KeyValue 
      * Spring Data LDAP 
      * Spring Data MongoDB 
      * Spring Data Neo4j 
      * Spring Data Redis 
      * Spring Data JDBC & R2DBC 
      * Spring Data REST 
    * Spring Integration 
    * Spring Batch 
    * Spring Security 
      * Spring Authorization Server 
      * Spring LDAP 
      * Spring Security Kerberos 
      * Spring Session 
      * Spring Vault 
    * Spring AI 
    * Spring AMQP 
    * Spring CLI 
    * Spring GraphQL 
    * Spring for Apache Kafka 
    * Spring Modulith 
    * Spring for Apache Pulsar 
    * Spring Shell 
All Docs...

Copyright © 2005 -  
Terms of Use • Privacy • Trademark Guidelines • Thank you • Your California
Privacy Rights • Cookie Settings

Apache®, Apache Tomcat®, Apache Kafka®, Apache Cassandra™, and Apache Geode™ are
trademarks or registered trademarks of the Apache Software Foundation in the
United States and/or other countries. Java™, Java™ SE, Java™ EE, and OpenJDK™
are trademarks of Oracle and/or its affiliates. Kubernetes® is a registered
trademark of the Linux Foundation in the United States and other countries.
Linux® is the registered trademark of Linus Torvalds in the United States and
other countries. Windows® and Microsoft® Azure are registered trademarks of
Microsoft Corporation. “AWS” and “Amazon Web Services” are trademarks or
registered trademarks of Amazon.com Inc. or its affiliates. All other trademarks
and copyrights are property of their respective owners and are only mentioned
for informative purposes. Other names may be trademarks of their respective
owners.

Search in all Spring Docs

