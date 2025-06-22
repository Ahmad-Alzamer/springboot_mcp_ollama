source URL: https://docs.spring.io/spring-ai/reference/api/vectordbs/redis.html 
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

### Redis

  * Prerequisites
  * Auto-configuration
  * Configuration Properties
  * Metadata Filtering
  * Manual Configuration
  * Accessing the Native Client

  * Spring AI
  * Reference
  * Vector Databases
  * Redis

# Redis

### Redis

  * Prerequisites
  * Auto-configuration
  * Configuration Properties
  * Metadata Filtering
  * Manual Configuration
  * Accessing the Native Client

This section walks you through setting up `RedisVectorStore` to store document
embeddings and perform similarity searches.

Redis is an open source (BSD licensed), in-memory data structure store used as a
database, cache, message broker, and streaming engine. Redis provides data
structures such as strings, hashes, lists, sets, sorted sets with range queries,
bitmaps, hyperloglogs, geospatial indexes, and streams.

Redis Search and Query extends the core features of Redis OSS and allows you to
use Redis as a vector database:

  * Store vectors and the associated metadata within hashes or JSON documents
  * Retrieve vectors
  * Perform vector searches

##  Prerequisites

  1. A Redis Stack instance
     * Redis Cloud (recommended)
     * Docker image _redis/redis-stack:latest_
  2. `EmbeddingModel` instance to compute the document embeddings. Several options are available:
     * If required, an API key for the EmbeddingModel to generate the embeddings stored by the `RedisVectorStore`.

##  Auto-configuration

|  There has been a significant change in the Spring AI auto-configuration,
starter modules' artifact names. Please refer to the upgrade notes for more
information.  
---|---  
Spring AI provides Spring Boot auto-configuration for the Redis Vector Store. To
enable it, add the following dependency to your project’s Maven `pom.xml` file:

```

<dependency>

    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-starter-vector-store-redis</artifactId>
</dependency>

Copied!

```

or to your Gradle `build.gradle` build file.

```

dependencies {

    implementation 'org.springframework.ai:spring-ai-starter-vector-store-redis'
}

Copied!

```

|  Refer to the Dependency Management section to add the Spring AI BOM to your
build file.  
---|---  
|  Refer to the Artifact Repositories section to add Maven Central and/or
Snapshot Repositories to your build file.  
---|---  
The vector store implementation can initialize the requisite schema for you, but
you must opt-in by specifying the `initializeSchema` boolean in the appropriate
constructor or by setting `…​initialize-schema=true` in the
`application.properties` file.

|  this is a breaking change! In earlier versions of Spring AI, this schema
initialization happened by default.  
---|---  
Please have a look at the list of configuration parameters for the vector store
to learn about the default values and configuration options.

Additionally, you will need a configured `EmbeddingModel` bean. Refer to the
EmbeddingModel section for more information.

Now you can auto-wire the `RedisVectorStore` as a vector store in your
application.

```

@Autowired VectorStore vectorStore;

// ...

List <Document> documents = List.of(

    new Document("Spring AI rocks!! Spring AI rocks!! Spring AI rocks!! Spring AI rocks!! Spring AI rocks!!", Map.of("meta1", "meta1")),
    new Document("The World is Big and Salvation Lurks Around the Corner"),
    new Document("You walk forward facing the past and you turn back toward the future.", Map.of("meta2", "meta2")));

// Add the documents to Redis

vectorStore.add(documents);

// Retrieve documents similar to a query

List<Document> results =
this.vectorStore.similaritySearch(SearchRequest.builder().query("Spring").topK(5).build());

Copied!

```

###  Configuration Properties

To connect to Redis and use the `RedisVectorStore`, you need to provide access
details for your instance. A simple configuration can be provided via Spring
Boot’s `application.yml`,

```

spring:

  data:

    redis:
      url: <redis instance url>
  ai:

    vectorstore:
      redis:
        initialize-schema: true
        index-name: custom-index
        prefix: custom-prefix
Copied!

```

For redis connection configuration, alternatively, a simple configuration can be
provided via Spring Boot’s _application.properties_.

```

spring.data.redis.host=localhost

spring.data.redis.port=6379

spring.data.redis.username=default

spring.data.redis.password=

Copied!

```

Properties starting with `spring.ai.vectorstore.redis.*` are used to configure
the `RedisVectorStore`:

Property | Description | Default Value  
---|---|---  
`spring.ai.vectorstore.redis.initialize-schema` | Whether to initialize the required schema | `false`  
`spring.ai.vectorstore.redis.index-name` | The name of the index to store the vectors | `spring-ai-index`  
`spring.ai.vectorstore.redis.prefix` | The prefix for Redis keys | `embedding:`  
##  Metadata Filtering

You can leverage the generic, portable metadata filters with Redis as well.

For example, you can use either the text expression language:

```

vectorStore.similaritySearch(SearchRequest.builder()

        .query("The World")
        .topK(TOP_K)
        .similarityThreshold(SIMILARITY_THRESHOLD)
        .filterExpression("country in ['UK', 'NL'] && year >= 2020").build());
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
                b.in("country", "UK", "NL"),
                b.gte("year", 2020)).build()).build());
Copied!

```

|  Those (portable) filter expressions get automatically converted into Redis
search queries.  
---|---  
For example, this portable filter expression:

```

country in ['UK', 'NL'] && year >= 2020

Copied!

```

is converted into the proprietary Redis filter format:

```

@country:{UK | NL} @year:[2020 inf]
Copied!

```

##  Manual Configuration

Instead of using the Spring Boot auto-configuration, you can manually configure
the Redis vector store. For this you need to add the `spring-ai-redis-store` to
your project:

```

<dependency>

    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-redis-store</artifactId>
</dependency>

Copied!

```

or to your Gradle `build.gradle` build file.

```

dependencies {

    implementation 'org.springframework.ai:spring-ai-redis-store'
}

Copied!

```

Create a `JedisPooled` bean:

```

@Bean

public JedisPooled jedisPooled() {

    return new JedisPooled("<host>", 6379);
}

Copied!

```

Then create the `RedisVectorStore` bean using the builder pattern:

```

@Bean

public VectorStore vectorStore(JedisPooled jedisPooled, EmbeddingModel
embeddingModel) {

    return RedisVectorStore.builder(jedisPooled, embeddingModel)
        .indexName("custom-index")                // Optional: defaults to "spring-ai-index"
        .prefix("custom-prefix")                  // Optional: defaults to "embedding:"
        .metadataFields(                         // Optional: define metadata fields for filtering
            MetadataField.tag("country"),
            MetadataField.numeric("year"))
        .initializeSchema(true)                   // Optional: defaults to false
        .batchingStrategy(new TokenCountBatchingStrategy()) // Optional: defaults to TokenCountBatchingStrategy
        .build();
}

// This can be any EmbeddingModel implementation

@Bean

public EmbeddingModel embeddingModel() {

    return new OpenAiEmbeddingModel(new OpenAiApi(System.getenv("OPENAI_API_KEY")));
}

Copied!

```

|  You must list explicitly all metadata field names and types (`TAG`, `TEXT`,
or `NUMERIC`) for any metadata field used in filter expressions. The
`metadataFields` above registers filterable metadata fields: `country` of type
`TAG`, `year` of type `NUMERIC`.  
---|---  
##  Accessing the Native Client

The Redis Vector Store implementation provides access to the underlying native
Redis client (`JedisPooled`) through the `getNativeClient()` method:

```

RedisVectorStore vectorStore = context.getBean(RedisVectorStore.class);

Optional<JedisPooled> nativeClient = vectorStore.getNativeClient();

if (nativeClient.isPresent()) {

    JedisPooled jedis = nativeClient.get();
    // Use the native client for Redis-specific operations
}

Copied!

```

The native client gives you access to Redis-specific features and operations
that might not be exposed through the `VectorStore` interface.

Qdrant SAP Hana

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

