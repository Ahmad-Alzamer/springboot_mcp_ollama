source URL: https://docs.spring.io/spring-ai/reference/api/vectordbs/gemfire.html 
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

### GemFire Vector Store

  * Prerequisites
  * Auto-configuration
  * Configuration properties
  * Manual Configuration
  * Usage

  * Spring AI
  * Reference
  * Vector Databases
  * GemFire

# GemFire Vector Store

### GemFire Vector Store

  * Prerequisites
  * Auto-configuration
  * Configuration properties
  * Manual Configuration
  * Usage

This section walks you through setting up the `GemFireVectorStore` to store
document embeddings and perform similarity searches.

GemFire is a distributed, in-memory, key-value store performing read and write
operations at blazingly fast speeds. It offers highly available parallel message
queues, continuous availability, and an event-driven architecture you can scale
dynamically without downtime. As your data size requirements increase to support
high-performance, real-time apps, GemFire can easily scale linearly.

GemFire VectorDB extends GemFire’s capabilities, serving as a versatile vector
database that efficiently stores, retrieves, and performs vector similarity
searches.

##  Prerequisites

  1. A GemFire cluster with the GemFire VectorDB extension enabled
     * Install GemFire VectorDB extension
  2. An `EmbeddingModel` bean to compute the document embeddings. Refer to the EmbeddingModel section for more information. An option that runs locally on your machine is ONNX and the all-MiniLM-L6-v2 Sentence Transformers.

##  Auto-configuration

|  There has been a significant change in the Spring AI auto-configuration,
starter modules' artifact names. Please refer to the upgrade notes for more
information.  
---|---  
Add the GemFire VectorStore Spring Boot starter to you project’s Maven build
file `pom.xml`:

```

<dependency>

    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-starter-vector-store-gemfire</artifactId>
</dependency>

Copied!

```

or to your Gradle `build.gradle` file

```

dependencies {

    implementation 'org.springframework.ai:spring-ai-starter-vector-store-gemfire'
}

Copied!

```

###  Configuration properties

You can use the following properties in your Spring Boot configuration to
further configure the `GemFireVectorStore`.

Property | Default value  
---|---  
`spring.ai.vectorstore.gemfire.host` | localhost  
`spring.ai.vectorstore.gemfire.port` | 8080  
`spring.ai.vectorstore.gemfire.initialize-schema` | `false`  
`spring.ai.vectorstore.gemfire.index-name` | spring-ai-gemfire-store  
`spring.ai.vectorstore.gemfire.beam-width` | 100  
`spring.ai.vectorstore.gemfire.max-connections` | 16  
`spring.ai.vectorstore.gemfire.vector-similarity-function` | COSINE  
`spring.ai.vectorstore.gemfire.fields` | []  
`spring.ai.vectorstore.gemfire.buckets` | 0  
##  Manual Configuration

To use just the `GemFireVectorStore`, without Spring Boot’s Auto-configuration
add the following dependency to your project’s Maven `pom.xml`:

```

<dependency>

    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-gemfire-store</artifactId>
</dependency>

Copied!

```

For Gradle users, add the following to your `build.gradle` file under the
dependencies block to use just the `GemFireVectorStore`:

```

dependencies {

    implementation 'org.springframework.ai:spring-ai-gemfire-store'
}

```

##  Usage

Here is a sample that creates an instance of the `GemfireVectorStore` instead of
using AutoConfiguration

```

@Bean

public GemFireVectorStore vectorStore(EmbeddingModel embeddingModel) {

    return GemFireVectorStore.builder(embeddingModel)
        .host("localhost")
        .port(7071)
        .indexName("my-vector-index")
        .initializeSchema(true)
        .build();
}

Copied!

```

|  The GemFire VectorStore does not yet support metadata filters.  
---|---  
|  The default configuration connects to a GemFire cluster at `localhost:8080`  
---|---  
  * In your application, create a few documents:

```

List<Document> documents = List.of(

   new Document("Spring AI rocks!! Spring AI rocks!! Spring AI rocks!! Spring AI
rocks!! Spring AI rocks!!", Map.of("country", "UK", "year", 2020)),

   new Document("The World is Big and Salvation Lurks Around the Corner",
Map.of()),

   new Document("You walk forward facing the past and you turn back toward the
future.", Map.of("country", "NL", "year", 2023)));

Copied!

```

  * Add the documents to the vector store:

```

vectorStore.add(documents);

Copied!

```

  * And to retrieve documents using similarity search:

```

List<Document> results = vectorStore.similaritySearch(

   SearchRequest.builder().query("Spring").topK(5).build());

Copied!

```

You should retrieve the document containing the text "Spring AI rocks!!".

You can also limit the number of results using a similarity threshold:

```

List<Document> results = vectorStore.similaritySearch(

   SearchRequest.builder().query("Spring").topK(5)

      .similarityThreshold(0.5d).build());
Copied!

```

Elasticsearch MariaDB Vector Store

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

