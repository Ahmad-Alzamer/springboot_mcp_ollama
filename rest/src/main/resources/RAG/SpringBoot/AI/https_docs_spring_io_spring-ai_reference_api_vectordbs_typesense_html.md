source URL: https://docs.spring.io/spring-ai/reference/api/vectordbs/typesense.html 
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

### Typesense

  * Prerequisites
  * Auto-configuration
  * Configuration Properties
  * Manual Configuration
  * Metadata Filtering
  * Accessing the Native Client

  * Spring AI
  * Reference
  * Vector Databases
  * Typesense

# Typesense

### Typesense

  * Prerequisites
  * Auto-configuration
  * Configuration Properties
  * Manual Configuration
  * Metadata Filtering
  * Accessing the Native Client

This section walks you through setting up `TypesenseVectorStore` to store
document embeddings and perform similarity searches.

Typesense is an open source typo tolerant search engine that is optimized for
instant sub-50ms searches while providing an intuitive developer experience. It
provides vector search capabilities that allow you to store and query high-
dimensional vectors alongside your regular search data.

##  Prerequisites

  * A running Typesense instance. The following options are available:
    * Typesense Cloud (recommended)
    * Docker image _typesense/typesense:latest_
  * If required, an API key for the EmbeddingModel to generate the embeddings stored by the `TypesenseVectorStore`.

##  Auto-configuration

|  There has been a significant change in the Spring AI auto-configuration,
starter modules' artifact names. Please refer to the upgrade notes for more
information.  
---|---  
Spring AI provides Spring Boot auto-configuration for the Typesense Vector
Store. To enable it add the following dependency to your project’s Maven
`pom.xml` file:

```

<dependency>

    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-starter-vector-store-typesense</artifactId>
</dependency>

Copied!

```

or to your Gradle `build.gradle` build file.

```

dependencies {

    implementation 'org.springframework.ai:spring-ai-starter-vector-store-typesense'
}

Copied!

```

|  Refer to the Dependency Management section to add the Spring AI BOM to your
build file.  
---|---  
Please have a look at the list of configuration parameters for the vector store
to learn about the default values and configuration options.

|  Refer to the Artifact Repositories section to add Maven Central and/or
Snapshot Repositories to your build file.  
---|---  
The vector store implementation can initialize the requisite schema for you but
you must opt-in by setting `…​initialize-schema=true` in the
`application.properties` file.

Additionally you will need a configured `EmbeddingModel` bean. Refer to the
EmbeddingModel section for more information.

Now you can auto-wire the `TypesenseVectorStore` as a vector store in your
application:

```

@Autowired VectorStore vectorStore;

// ...

List<Document> documents = List.of(

    new Document("Spring AI rocks!! Spring AI rocks!! Spring AI rocks!! Spring AI rocks!! Spring AI rocks!!", Map.of("meta1", "meta1")),
    new Document("The World is Big and Salvation Lurks Around the Corner"),
    new Document("You walk forward facing the past and you turn back toward the future.", Map.of("meta2", "meta2")));

// Add the documents to Typesense

vectorStore.add(documents);

// Retrieve documents similar to a query

List<Document> results =
vectorStore.similaritySearch(SearchRequest.builder().query("Spring").topK(5).build());

Copied!

```

###  Configuration Properties

To connect to Typesense and use the `TypesenseVectorStore` you need to provide
access details for your instance. A simple configuration can be provided via
Spring Boot’s `application.yml`:

```

spring:

  ai:

    vectorstore:
      typesense:
        initialize-schema: true
        collection-name: vector_store
        embedding-dimension: 1536
        client:
          protocol: http
          host: localhost
          port: 8108
          api-key: xyz
Copied!

```

Properties starting with `spring.ai.vectorstore.typesense.*` are used to
configure the `TypesenseVectorStore`:

Property | Description | Default Value  
---|---|---  
`spring.ai.vectorstore.typesense.initialize-schema` | Whether to initialize the required schema | `false`  
`spring.ai.vectorstore.typesense.collection-name` | The name of the collection to store vectors | `vector_store`  
`spring.ai.vectorstore.typesense.embedding-dimension` | The number of dimensions in the vector | `1536`  
`spring.ai.vectorstore.typesense.client.protocol` | HTTP Protocol | `http`  
`spring.ai.vectorstore.typesense.client.host` | Hostname | `localhost`  
`spring.ai.vectorstore.typesense.client.port` | Port | `8108`  
`spring.ai.vectorstore.typesense.client.api-key` | API Key | `xyz`  
##  Manual Configuration

Instead of using the Spring Boot auto-configuration you can manually configure
the Typesense vector store. For this you need to add the `spring-ai-typesense-
store` to your project:

```

<dependency>

    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-typesense-store</artifactId>
</dependency>

Copied!

```

or to your Gradle `build.gradle` build file.

```

dependencies {

    implementation 'org.springframework.ai:spring-ai-typesense-store'
}

Copied!

```

|  Refer to the Dependency Management section to add the Spring AI BOM to your
build file.  
---|---  
Create a Typesense `Client` bean:

```

@Bean

public Client typesenseClient() {

    List<Node> nodes = new ArrayList<>();
    nodes.add(new Node("http", "localhost", "8108"));
    Configuration configuration = new Configuration(nodes, Duration.ofSeconds(5), "xyz");
    return new Client(configuration);
}

Copied!

```

Then create the `TypesenseVectorStore` bean using the builder pattern:

```

@Bean

public VectorStore vectorStore(Client client, EmbeddingModel embeddingModel) {

    return TypesenseVectorStore.builder(client, embeddingModel)
        .collectionName("custom_vectors")     // Optional: defaults to "vector_store"
        .embeddingDimension(1536)            // Optional: defaults to 1536
        .initializeSchema(true)              // Optional: defaults to false
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

##  Metadata Filtering

You can leverage the generic portable metadata filters with Typesense store as
well.

For example you can use either the text expression language:

```

vectorStore.similaritySearch(

    SearchRequest.builder()
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

|  Those (portable) filter expressions get automatically converted into
Typesense Search Filters.  
---|---  
For example this portable filter expression:

```

country in ['UK', 'NL'] && year >= 2020

Copied!

```

is converted into the proprietary Typesense filter format:

```

country: ['UK', 'NL'] && year: >=2020

Copied!

```

|  If you are not retrieving the documents in the expected order or the search
results are not as expected, check the embedding model you are using. Embedding
models can have a significant impact on the search results (i.e. make sure if
your data is in Spanish to use a Spanish or multilingual embedding model).  
---|---  
##  Accessing the Native Client

The Typesense Vector Store implementation provides access to the underlying
native Typesense client (`Client`) through the `getNativeClient()` method:

```

TypesenseVectorStore vectorStore = context.getBean(TypesenseVectorStore.class);

Optional<Client> nativeClient = vectorStore.getNativeClient();

if (nativeClient.isPresent()) {

    Client client = nativeClient.get();
    // Use the native client for Typesense-specific operations
}

Copied!

```

The native client gives you access to Typesense-specific features and operations
that might not be exposed through the `VectorStore` interface.

SAP Hana Weaviate

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

