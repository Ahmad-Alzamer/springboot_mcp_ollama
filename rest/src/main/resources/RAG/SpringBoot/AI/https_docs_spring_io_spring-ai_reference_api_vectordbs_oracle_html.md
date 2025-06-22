source URL: https://docs.spring.io/spring-ai/reference/api/vectordbs/oracle.html 
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

### Oracle Database 23ai - AI Vector Search

  * Auto-Configuration
  * Configuration properties
  * Metadata filtering
  * Manual Configuration
  * Run Oracle Database 23ai locally
  * Accessing the Native Client

  * Spring AI
  * Reference
  * Vector Databases
  * Oracle

# Oracle Database 23ai - AI Vector Search

### Oracle Database 23ai - AI Vector Search

  * Auto-Configuration
  * Configuration properties
  * Metadata filtering
  * Manual Configuration
  * Run Oracle Database 23ai locally
  * Accessing the Native Client

The AI Vector Search capabilities of the Oracle Database 23ai (23.4+) are
available as a Spring AI `VectorStore` to help you to store document embeddings
and perform similarity searches. Of course, all other features are also
available.

|  The Run Oracle Database 23ai locally appendix shows how to start a database
with a lightweight Docker container.  
---|---  
##  Auto-Configuration

|  There has been a significant change in the Spring AI auto-configuration,
starter modules' artifact names. Please refer to the upgrade notes for more
information.  
---|---  
Start by adding the Oracle Vector Store boot starter dependency to your project:

```

<dependency>

	<groupId>org.springframework.ai</groupId>
	<artifactId>spring-ai-starter-vector-store-oracle</artifactId>
</dependency>

Copied!

```

or to your Gradle `build.gradle` build file.

```

dependencies {

    implementation 'org.springframework.ai:spring-ai-starter-vector-store-oracle'
}

Copied!

```

If you need this vector store to initialize the schema for you then you’ll need
to pass true for the `initializeSchema` boolean parameter in the appropriate
constructor or by setting `…​initialize-schema=true` in the
`application.properties` file.

|  this is a breaking change! In earlier versions of Spring AI, this schema
initialization happened by default.  
---|---  
The Vector Store, also requires an `EmbeddingModel` instance to calculate
embeddings for the documents. You can pick one of the available EmbeddingModel
Implementations.

For example to use the OpenAI EmbeddingModel add the following dependency to
your project:

```

<dependency>

	<groupId>org.springframework.ai</groupId>
	<artifactId>spring-ai-starter-model-openai</artifactId>
</dependency>

Copied!

```

or to your Gradle `build.gradle` build file.

```

dependencies {

    implementation 'org.springframework.ai:spring-ai-starter-model-openai'
}

Copied!

```

|  Refer to the Dependency Management section to add the Spring AI BOM to your
build file. Refer to the Artifact Repositories section to add Maven Central
and/or Snapshot Repositories to your build file.  
---|---  
To connect to and configure the `OracleVectorStore`, you need to provide access
details for your database. A simple configuration can either be provided via
Spring Boot’s `application.yml`

```

spring:

  datasource:

    url: jdbc:oracle:thin:@//localhost:1521/freepdb1
    username: mlops
    password: mlops
  ai:

	vectorstore:
	  oracle:
		index-type: IVF
		distance-type: COSINE
		dimensions: 1536
```

|  Check the list of configuration parameters to learn about the default values
and configuration options.  
---|---  
Now you can Auto-wire the `OracleVectorStore` in your application and use it:

```

@Autowired VectorStore vectorStore;

// ...

List<Document> documents = List.of(

    new Document("Spring AI rocks!! Spring AI rocks!! Spring AI rocks!! Spring AI rocks!! Spring AI rocks!!", Map.of("meta1", "meta1")),
    new Document("The World is Big and Salvation Lurks Around the Corner"),
    new Document("You walk forward facing the past and you turn back toward the future.", Map.of("meta2", "meta2")));

// Add the documents to Oracle Vector Store

vectorStore.add(documents);

// Retrieve documents similar to a query

List<Document> results =
this.vectorStore.similaritySearch(SearchRequest.builder().query("Spring").topK(5).build());

Copied!

```

###  Configuration properties

You can use the following properties in your Spring Boot configuration to
customize the `OracleVectorStore`.

Property | Description | Default value  
---|---|---  
`spring.ai.vectorstore.oracle.index-type` | Nearest neighbor search index type. Options are `NONE` - exact nearest neighbor search, `IVF` - Inverted Flat File index. It has faster build times and uses less memory than HNSW, but has lower query performance (in terms of speed-recall tradeoff). `HNSW` - creates a multilayer graph. It has slower build times and uses more memory than IVF, but has better query performance (in terms of speed-recall tradeoff). | NONE  
`spring.ai.vectorstore.oracle.distance-type` |  Search distance type among `COSINE` (default), `DOT`, `EUCLIDEAN`, `EUCLIDEAN_SQUARED`, and `MANHATTAN`. NOTE: If vectors are normalized, you can use `DOT` or `COSINE` for best performance. | COSINE  
`spring.ai.vectorstore.oracle.forced-normalization` |  Allows enabling vector normalization (if true) before insertion and for similarity search. CAUTION: Setting this to true is a requirement to allow for search request similarity threshold. NOTE: If vectors are normalized, you can use `DOT` or `COSINE` for best performance. | false  
`spring.ai.vectorstore.oracle.dimensions` | Embeddings dimension. If not specified explicitly the OracleVectorStore will allow the maximum: 65535. Dimensions are set to the embedding column on table creation. If you change the dimensions your would have to re-create the table as well. | 65535  
`spring.ai.vectorstore.oracle.remove-existing-vector-store-table` | Drops the existing table on start up. | false  
`spring.ai.vectorstore.oracle.initialize-schema` | Whether to initialize the required schema. | false  
`spring.ai.vectorstore.oracle.search-accuracy` | Denote the requested accuracy target in the presence of index. Disabled by default. You need to provide an integer in the range [1,100] to override the default index accuracy (95). Using lower accuracy provides approximate similarity search trading off speed versus accuracy. | -1 (`DEFAULT_SEARCH_ACCURACY`)  
##  Metadata filtering

You can leverage the generic, portable metadata filters with the
`OracleVectorStore`.

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
        b.in("author","john", "jill"),
        b.eq("article_type", "blog")).build()).build());
Copied!

```

|  These filter expressions are converted into the equivalent
`OracleVectorStore` filters.  
---|---  
##  Manual Configuration

Instead of using the Spring Boot auto-configuration, you can manually configure
the `OracleVectorStore`. For this you need to add the Oracle JDBC driver and
`JdbcTemplate` auto-configuration dependencies to your project:

```

<dependency>

	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-jdbc</artifactId>
</dependency>

<dependency>

	<groupId>com.oracle.database.jdbc</groupId>
	<artifactId>ojdbc11</artifactId>
	<scope>runtime</scope>
</dependency>

<dependency>

	<groupId>org.springframework.ai</groupId>
	<artifactId>spring-ai-oracle-store</artifactId>
</dependency>

Copied!

```

|  Refer to the Dependency Management section to add the Spring AI BOM to your
build file.  
---|---  
To configure the `OracleVectorStore` in your application, you can use the
following setup:

```

@Bean

public VectorStore vectorStore(JdbcTemplate jdbcTemplate, EmbeddingModel
embeddingModel) {

    return OracleVectorStore.builder(jdbcTemplate, embeddingModel)
        .tableName("my_vectors")
        .indexType(OracleVectorStoreIndexType.IVF)
        .distanceType(OracleVectorStoreDistanceType.COSINE)
        .dimensions(1536)
        .searchAccuracy(95)
        .initializeSchema(true)
        .build();
}

Copied!

```

##  Run Oracle Database 23ai locally

```

docker run --rm --name oracle23ai -p 1521:1521 -e APP_USER=mlops -e
APP_USER_PASSWORD=mlops -e ORACLE_PASSWORD=mlops gvenzl/oracle-free:23-slim

```

You can then connect to the database using:

```

sql mlops/mlops@localhost/freepdb1

```

##  Accessing the Native Client

The Oracle Vector Store implementation provides access to the underlying native
Oracle client (`OracleConnection`) through the `getNativeClient()` method:

```

OracleVectorStore vectorStore = context.getBean(OracleVectorStore.class);

Optional<OracleConnection> nativeClient = vectorStore.getNativeClient();

if (nativeClient.isPresent()) {

    OracleConnection connection = nativeClient.get();
    // Use the native client for Oracle-specific operations
}

Copied!

```

The native client gives you access to Oracle-specific features and operations
that might not be exposed through the `VectorStore` interface.

OpenSearch PGvector

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

