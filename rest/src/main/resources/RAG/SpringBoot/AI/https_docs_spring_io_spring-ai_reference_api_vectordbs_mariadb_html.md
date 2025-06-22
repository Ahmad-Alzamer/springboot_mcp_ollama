source URL: https://docs.spring.io/spring-ai/reference/api/vectordbs/mariadb.html 
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

### MariaDB Vector Store

  * Prerequisites
  * Auto-Configuration
  * Configuration Properties
  * Manual Configuration
  * Metadata Filtering
  * Accessing the Native Client

  * Spring AI
  * Reference
  * Vector Databases
  * MariaDB Vector Store

# MariaDB Vector Store

### MariaDB Vector Store

  * Prerequisites
  * Auto-Configuration
  * Configuration Properties
  * Manual Configuration
  * Metadata Filtering
  * Accessing the Native Client

This section walks you through setting up `MariaDBVectorStore` to store document
embeddings and perform similarity searches.

MariaDB Vector is part of MariaDB 11.7 and enables storing and searching over
machine learning-generated embeddings. It provides efficient vector similarity
search capabilities using vector indexes, supporting both cosine similarity and
Euclidean distance metrics.

##  Prerequisites

  * A running MariaDB (11.7+) instance. The following options are available:
    * Docker image
    * MariaDB Server
    * MariaDB SkySQL
  * If required, an API key for the EmbeddingModel to generate the embeddings stored by the `MariaDBVectorStore`.

##  Auto-Configuration

|  There has been a significant change in the Spring AI auto-configuration,
starter modules' artifact names. Please refer to the upgrade notes for more
information.  
---|---  
Spring AI provides Spring Boot auto-configuration for the MariaDB Vector Store.
To enable it, add the following dependency to your project’s Maven `pom.xml`
file:

```

<dependency>

    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-starter-vector-store-mariadb</artifactId>
</dependency>

Copied!

```

or to your Gradle `build.gradle` build file.

```

dependencies {

    implementation 'org.springframework.ai:spring-ai-starter-vector-store-mariadb'
}

Copied!

```

|  Refer to the Dependency Management section to add the Spring AI BOM to your
build file.  
---|---  
The vector store implementation can initialize the required schema for you, but
you must opt-in by specifying the `initializeSchema` boolean in the appropriate
constructor or by setting `…​initialize-schema=true` in the
`application.properties` file.

|  This is a breaking change! In earlier versions of Spring AI, this schema
initialization happened by default.  
---|---  
Additionally, you will need a configured `EmbeddingModel` bean. Refer to the
EmbeddingModel section for more information.

For example, to use the OpenAI EmbeddingModel, add the following dependency:

```

<dependency>

    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-starter-model-openai</artifactId>
</dependency>

Copied!

```

|  Refer to the Artifact Repositories section to add Maven Central and/or
Snapshot Repositories to your build file.  
---|---  
Now you can auto-wire the `MariaDBVectorStore` in your application:

```

@Autowired VectorStore vectorStore;

// ...

List<Document> documents = List.of(

    new Document("Spring AI rocks!! Spring AI rocks!! Spring AI rocks!! Spring AI rocks!! Spring AI rocks!!", Map.of("meta1", "meta1")),
    new Document("The World is Big and Salvation Lurks Around the Corner"),
    new Document("You walk forward facing the past and you turn back toward the future.", Map.of("meta2", "meta2")));

// Add the documents to MariaDB

vectorStore.add(documents);

// Retrieve documents similar to a query

List<Document> results =
vectorStore.similaritySearch(SearchRequest.builder().query("Spring").topK(5).build());

Copied!

```

###  Configuration Properties

To connect to MariaDB and use the `MariaDBVectorStore`, you need to provide
access details for your instance. A simple configuration can be provided via
Spring Boot’s `application.yml`:

```

spring:

  datasource:

    url: jdbc:mariadb://localhost/db
    username: myUser
    password: myPassword
  ai:

    vectorstore:
      mariadb:
        initialize-schema: true
        distance-type: COSINE
        dimensions: 1536
Copied!

```

|  If you run MariaDB Vector as a Spring Boot dev service via Docker Compose or
Testcontainers, you don’t need to configure URL, username and password since
they are autoconfigured by Spring Boot.  
---|---  
Properties starting with `spring.ai.vectorstore.mariadb.*` are used to configure
the `MariaDBVectorStore`:

Property | Description | Default Value  
---|---|---  
`spring.ai.vectorstore.mariadb.initialize-schema` | Whether to initialize the required schema | `false`  
`spring.ai.vectorstore.mariadb.distance-type` | Search distance type. Use `COSINE` (default) or `EUCLIDEAN`. If vectors are normalized to length 1, you can use `EUCLIDEAN` for best performance. | `COSINE`  
`spring.ai.vectorstore.mariadb.dimensions` | Embeddings dimension. If not specified explicitly, will retrieve dimensions from the provided `EmbeddingModel`. | `1536`  
`spring.ai.vectorstore.mariadb.remove-existing-vector-store-table` | Deletes the existing vector store table on startup. | `false`  
`spring.ai.vectorstore.mariadb.schema-name` | Vector store schema name | `null`  
`spring.ai.vectorstore.mariadb.table-name` | Vector store table name | `vector_store`  
`spring.ai.vectorstore.mariadb.schema-validation` | Enables schema and table name validation to ensure they are valid and existing objects. | `false`  
|  If you configure a custom schema and/or table name, consider enabling schema
validation by setting `spring.ai.vectorstore.mariadb.schema-validation=true`.
This ensures the correctness of the names and reduces the risk of SQL injection
attacks.  
---|---  
##  Manual Configuration

Instead of using the Spring Boot auto-configuration, you can manually configure
the MariaDB vector store. For this you need to add the following dependencies to
your project:

```

<dependency>

    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-jdbc</artifactId>
</dependency>

<dependency>

    <groupId>org.mariadb.jdbc</groupId>
    <artifactId>mariadb-java-client</artifactId>
    <scope>runtime</scope>
</dependency>

<dependency>

    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-mariadb-store</artifactId>
</dependency>

Copied!

```

|  Refer to the Dependency Management section to add the Spring AI BOM to your
build file.  
---|---  
Then create the `MariaDBVectorStore` bean using the builder pattern:

```

@Bean

public VectorStore vectorStore(JdbcTemplate jdbcTemplate, EmbeddingModel
embeddingModel) {

    return MariaDBVectorStore.builder(jdbcTemplate, embeddingModel)
        .dimensions(1536)                      // Optional: defaults to 1536
        .distanceType(MariaDBDistanceType.COSINE) // Optional: defaults to COSINE
        .schemaName("mydb")                    // Optional: defaults to null
        .vectorTableName("custom_vectors")     // Optional: defaults to "vector_store"
        .contentFieldName("text")             // Optional: defaults to "content"
        .embeddingFieldName("embedding")      // Optional: defaults to "embedding"
        .idFieldName("doc_id")                // Optional: defaults to "id"
        .metadataFieldName("meta")           // Optional: defaults to "metadata"
        .initializeSchema(true)               // Optional: defaults to false
        .schemaValidation(true)              // Optional: defaults to false
        .removeExistingVectorStoreTable(false) // Optional: defaults to false
        .maxDocumentBatchSize(10000)         // Optional: defaults to 10000
        .build();
}

// This can be any EmbeddingModel implementation

@Bean

public EmbeddingModel embeddingModel() {

    return new OpenAiEmbeddingModel(new OpenAiApi(System.getenv("OPENAI_API_KEY")));
}

Copied!

```

##  Metadata Filtering

You can leverage the generic, portable metadata filters with MariaDB Vector
store.

For example, you can use either the text expression language:

```

vectorStore.similaritySearch(

    SearchRequest.builder()
        .query("The World")
        .topK(TOP_K)
        .similarityThreshold(SIMILARITY_THRESHOLD)
        .filterExpression("author in ['john', 'jill'] && article_type == 'blog'").build());
Copied!

```

or programmatically using the `Filter.Expression` DSL:

```

FilterExpressionBuilder b = new FilterExpressionBuilder();

vectorStore.similaritySearch(SearchRequest.builder()

    .query("The World")
    .topK(TOP_K)
    .similarityThreshold(SIMILARITY_THRESHOLD)
    .filterExpression(b.and(
        b.in("author", "john", "jill"),
        b.eq("article_type", "blog")).build()).build());
Copied!

```

|  These filter expressions are automatically converted into the equivalent
MariaDB JSON path expressions.  
---|---  
##  Accessing the Native Client

The MariaDB Vector Store implementation provides access to the underlying native
JDBC client (`JdbcTemplate`) through the `getNativeClient()` method:

```

MariaDBVectorStore vectorStore = context.getBean(MariaDBVectorStore.class);

Optional<JdbcTemplate> nativeClient = vectorStore.getNativeClient();

if (nativeClient.isPresent()) {

    JdbcTemplate jdbc = nativeClient.get();
    // Use the native client for MariaDB-specific operations
}

Copied!

```

The native client gives you access to MariaDB-specific features and operations
that might not be exposed through the `VectorStore` interface.

GemFire Milvus

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

