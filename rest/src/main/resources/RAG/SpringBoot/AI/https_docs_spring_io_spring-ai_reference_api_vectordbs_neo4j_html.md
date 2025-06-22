source URL: https://docs.spring.io/spring-ai/reference/api/vectordbs/neo4j.html 
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

### Neo4j

  * Prerequisites
  * Auto-configuration
  * Configuration Properties
  * Manual Configuration
  * Metadata Filtering
  * Accessing the Native Client

  * Spring AI
  * Reference
  * Vector Databases
  * Neo4j

# Neo4j

### Neo4j

  * Prerequisites
  * Auto-configuration
  * Configuration Properties
  * Manual Configuration
  * Metadata Filtering
  * Accessing the Native Client

This section walks you through setting up `Neo4jVectorStore` to store document
embeddings and perform similarity searches.

Neo4j is an open-source NoSQL graph database. It is a fully transactional
database (ACID) that stores data structured as graphs consisting of nodes,
connected by relationships. Inspired by the structure of the real world, it
allows for high query performance on complex data while remaining intuitive and
simple for the developer.

The Neo4j’s Vector Search allows users to query vector embeddings from large
datasets. An embedding is a numerical representation of a data object, such as
text, image, audio, or document. Embeddings can be stored on _Node_ properties
and can be queried with the `db.index.vector.queryNodes()` function. Those
indexes are powered by Lucene using a Hierarchical Navigable Small World Graph
(HNSW) to perform a k approximate nearest neighbors (k-ANN) query over the
vector fields.

##  Prerequisites

  * A running Neo4j (5.15+) instance. The following options are available:
    * Docker image
    * Neo4j Desktop
    * Neo4j Aura
    * Neo4j Server instance
  * If required, an API key for the EmbeddingModel to generate the embeddings stored by the `Neo4jVectorStore`.

##  Auto-configuration

|  There has been a significant change in the Spring AI auto-configuration,
starter modules' artifact names. Please refer to the upgrade notes for more
information.  
---|---  
Spring AI provides Spring Boot auto-configuration for the Neo4j Vector Store. To
enable it, add the following dependency to your project’s Maven `pom.xml` file:

```

<dependency>

    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-starter-vector-store-neo4j</artifactId>
</dependency>

Copied!

```

or to your Gradle `build.gradle` build file.

```

dependencies {

    implementation 'org.springframework.ai:spring-ai-starter-vector-store-neo4j'
}

Copied!

```

|  Refer to the Dependency Management section to add the Spring AI BOM to your
build file.  
---|---  
Please have a look at the list of Configuration Properties for the vector store
to learn about the default values and configuration options.

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
Additionally, you will need a configured `EmbeddingModel` bean. Refer to the
EmbeddingModel section for more information.

Now you can auto-wire the `Neo4jVectorStore` as a vector store in your
application.

```

@Autowired VectorStore vectorStore;

// ...

List<Document> documents = List.of(

    new Document("Spring AI rocks!! Spring AI rocks!! Spring AI rocks!! Spring AI rocks!! Spring AI rocks!!", Map.of("meta1", "meta1")),
    new Document("The World is Big and Salvation Lurks Around the Corner"),
    new Document("You walk forward facing the past and you turn back toward the future.", Map.of("meta2", "meta2")));

// Add the documents to Neo4j

vectorStore.add(documents);

// Retrieve documents similar to a query

List<Document> results =
vectorStore.similaritySearch(SearchRequest.builder().query("Spring").topK(5).build());

Copied!

```

###  Configuration Properties

To connect to Neo4j and use the `Neo4jVectorStore`, you need to provide access
details for your instance. A simple configuration can be provided via Spring
Boot’s `application.yml`:

```

spring:

  neo4j:

    uri: <neo4j instance URI>
    authentication:
      username: <neo4j username>
      password: <neo4j password>
  ai:

    vectorstore:
      neo4j:
        initialize-schema: true
        database-name: neo4j
        index-name: custom-index
        embedding-dimension: 1536
        distance-type: cosine
Copied!

```

The Spring Boot properties starting with `spring.neo4j.*` are used to configure
the Neo4j client:

Property | Description | Default Value  
---|---|---  
`spring.neo4j.uri` | URI for connecting to the Neo4j instance | `neo4j://localhost:7687`  
`spring.neo4j.authentication.username` | Username for authentication with Neo4j | `neo4j`  
`spring.neo4j.authentication.password` | Password for authentication with Neo4j | -  
Properties starting with `spring.ai.vectorstore.neo4j.*` are used to configure
the `Neo4jVectorStore`:

Property | Description | Default Value  
---|---|---  
`spring.ai.vectorstore.neo4j.initialize-schema` | Whether to initialize the required schema | `false`  
`spring.ai.vectorstore.neo4j.database-name` | The name of the Neo4j database to use | `neo4j`  
`spring.ai.vectorstore.neo4j.index-name` | The name of the index to store the vectors | `spring-ai-document-index`  
`spring.ai.vectorstore.neo4j.embedding-dimension` | The number of dimensions in the vector | `1536`  
`spring.ai.vectorstore.neo4j.distance-type` | The distance function to use | `cosine`  
`spring.ai.vectorstore.neo4j.label` | The label used for document nodes | `Document`  
`spring.ai.vectorstore.neo4j.embedding-property` | The property name used to store embeddings | `embedding`  
The following distance functions are available:

  * `cosine` - Default, suitable for most use cases. Measures cosine similarity between vectors.
  * `euclidean` - Euclidean distance between vectors. Lower values indicate higher similarity.

##  Manual Configuration

Instead of using the Spring Boot auto-configuration, you can manually configure
the Neo4j vector store. For this you need to add the `spring-ai-neo4j-store` to
your project:

```

<dependency>

    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-neo4j-store</artifactId>
</dependency>

Copied!

```

or to your Gradle `build.gradle` build file.

```

dependencies {

    implementation 'org.springframework.ai:spring-ai-neo4j-store'
}

Copied!

```

|  Refer to the Dependency Management section to add the Spring AI BOM to your
build file.  
---|---  
Create a Neo4j `Driver` bean. Read the Neo4j Documentation for more in-depth
information about the configuration of a custom driver.

```

@Bean

public Driver driver() {

    return GraphDatabase.driver("neo4j://<host>:<bolt-port>",
            AuthTokens.basic("<username>", "<password>"));
}

Copied!

```

Then create the `Neo4jVectorStore` bean using the builder pattern:

```

@Bean

public VectorStore vectorStore(Driver driver, EmbeddingModel embeddingModel) {

    return Neo4jVectorStore.builder(driver, embeddingModel)
        .databaseName("neo4j")                // Optional: defaults to "neo4j"
        .distanceType(Neo4jDistanceType.COSINE) // Optional: defaults to COSINE
        .embeddingDimension(1536)                      // Optional: defaults to 1536
        .label("Document")                     // Optional: defaults to "Document"
        .embeddingProperty("embedding")        // Optional: defaults to "embedding"
        .indexName("custom-index")             // Optional: defaults to "spring-ai-document-index"
        .initializeSchema(true)                // Optional: defaults to false
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

You can leverage the generic, portable metadata filters with Neo4j store as
well.

For example, you can use either the text expression language:

```

vectorStore.similaritySearch(

    SearchRequest.builder()
        .query("The World")
        .topK(TOP_K)
        .similarityThreshold(SIMILARITY_THRESHOLD)
        .filterExpression("author in ['john', 'jill'] && 'article_type' == 'blog'").build());
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

|  Those (portable) filter expressions get automatically converted into the
proprietary Neo4j `WHERE` filter expressions.  
---|---  
For example, this portable filter expression:

```

author in ['john', 'jill'] && 'article_type' == 'blog'

Copied!

```

is converted into the proprietary Neo4j filter format:

```

node.`metadata.author` IN ["john","jill"] AND node.`metadata.'article_type'` =
"blog"

Copied!

```

##  Accessing the Native Client

The Neo4j Vector Store implementation provides access to the underlying native
Neo4j client (`Driver`) through the `getNativeClient()` method:

```

Neo4jVectorStore vectorStore = context.getBean(Neo4jVectorStore.class);

Optional<Driver> nativeClient = vectorStore.getNativeClient();

if (nativeClient.isPresent()) {

    Driver driver = nativeClient.get();
    // Use the native client for Neo4j-specific operations
}

Copied!

```

The native client gives you access to Neo4j-specific features and operations
that might not be exposed through the `VectorStore` interface.

MongoDB Atlas OpenSearch

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

