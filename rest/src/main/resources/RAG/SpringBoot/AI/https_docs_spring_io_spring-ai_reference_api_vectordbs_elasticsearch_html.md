source URL: https://docs.spring.io/spring-ai/reference/api/vectordbs/elasticsearch.html 
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

### Elasticsearch

  * Prerequisites
  * Auto-configuration
  * Configuration Properties
  * Metadata Filtering
  * Manual Configuration
  * Accessing the Native Client

  * Spring AI
  * Reference
  * Vector Databases
  * Elasticsearch

# Elasticsearch

### Elasticsearch

  * Prerequisites
  * Auto-configuration
  * Configuration Properties
  * Metadata Filtering
  * Manual Configuration
  * Accessing the Native Client

This section walks you through setting up the Elasticsearch `VectorStore` to
store document embeddings and perform similarity searches.

Elasticsearch is an open source search and analytics engine based on the Apache
Lucene library.

##  Prerequisites

A running Elasticsearch instance. The following options are available:

  * Docker
  * Self-Managed Elasticsearch
  * Elastic Cloud

##  Auto-configuration

|  There has been a significant change in the Spring AI auto-configuration,
starter modules' artifact names. Please refer to the upgrade notes for more
information.  
---|---  
Spring AI provides Spring Boot auto-configuration for the Elasticsearch Vector
Store. To enable it, add the following dependency to your project’s Maven
`pom.xml` or Gradle `build.gradle` build files:

  * Maven
  * Gradle

```

<dependency>

    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-starter-vector-store-elasticsearch</artifactId>
</dependency>

Copied!

```

```

dependencies {

    implementation 'org.springframework.ai:spring-ai-starter-vector-store-elasticsearch'
}

Copied!

```

|  For spring-boot versions pre 3.3.0 it’s necessary to explicitly add the
elasticsearch-java dependency with version > 8.13.3, otherwise the older version
used will be incompatible with the queries performed:

  * Maven
  * Gradle

```

<dependency>

    <groupId>co.elastic.clients</groupId>
    <artifactId>elasticsearch-java</artifactId>
    <version>8.13.3</version>
</dependency>Copied!

```

```

dependencies {

    implementation 'co.elastic.clients:elasticsearch-java:8.13.3'
}Copied!

```

  
---|---  
|  Refer to the Dependency Management section to add the Spring AI BOM to your
build file.  
---|---  
|  Refer to the Artifact Repositories section to add Maven Central and/or
Snapshot Repositories to your build file.  
---|---  
The vector store implementation can initialize the requisite schema for you, but
you must opt-in by specifying the `initializeSchema` boolean in the appropriate
constructor or by setting `…​initialize-schema=true` in the
`application.properties` file. Alternatively you can opt-out the initialization
and create the index manually using the Elasticsearch client, which can be
useful if the index needs advanced mapping or additional configuration.

|  this is a breaking change! In earlier versions of Spring AI, this schema
initialization happened by default.  
---|---  
Please have a look at the list of configuration parameters for the vector store
to learn about the default values and configuration options. These properties
can be also set by configuring the `ElasticsearchVectorStoreOptions` bean.

Additionally, you will need a configured `EmbeddingModel` bean. Refer to the
EmbeddingModel section for more information.

Now you can auto-wire the `ElasticsearchVectorStore` as a vector store in your
application.

```

@Autowired VectorStore vectorStore;

// ...

List <Document> documents = List.of(

    new Document("Spring AI rocks!! Spring AI rocks!! Spring AI rocks!! Spring AI rocks!! Spring AI rocks!!", Map.of("meta1", "meta1")),
    new Document("The World is Big and Salvation Lurks Around the Corner"),
    new Document("You walk forward facing the past and you turn back toward the future.", Map.of("meta2", "meta2")));

// Add the documents to Elasticsearch

vectorStore.add(documents);

// Retrieve documents similar to a query

List<Document> results =
this.vectorStore.similaritySearch(SearchRequest.builder().query("Spring").topK(5).build());

Copied!

```

###  Configuration Properties

To connect to Elasticsearch and use the `ElasticsearchVectorStore`, you need to
provide access details for your instance. A simple configuration can either be
provided via Spring Boot’s `application.yml`,

```

spring:

  elasticsearch:

    uris: <elasticsearch instance URIs>
    username: <elasticsearch username>
    password: <elasticsearch password>
  ai:

    vectorstore:
      elasticsearch:
        initialize-schema: true
        index-name: custom-index
        dimensions: 1536
        similarity: cosine
Copied!

```

The Spring Boot properties starting with `spring.elasticsearch.*` are used to
configure the Elasticsearch client:

Property | Description | Default Value  
---|---|---  
`spring.elasticsearch.connection-timeout` | Connection timeout used when communicating with Elasticsearch. | `1s`  
`spring.elasticsearch.password` | Password for authentication with Elasticsearch. | -  
`spring.elasticsearch.username` | Username for authentication with Elasticsearch. | -  
`spring.elasticsearch.uris` | Comma-separated list of the Elasticsearch instances to use. | `localhost:9200`  
`spring.elasticsearch.path-prefix` | Prefix added to the path of every request sent to Elasticsearch. | -  
`spring.elasticsearch.restclient.sniffer.delay-after-failure` | Delay of a sniff execution scheduled after a failure. | `1m`  
`spring.elasticsearch.restclient.sniffer.interval` | Interval between consecutive ordinary sniff executions. | `5m`  
`spring.elasticsearch.restclient.ssl.bundle` | SSL bundle name. | -  
`spring.elasticsearch.socket-keep-alive` | Whether to enable socket keep alive between client and Elasticsearch. | `false`  
`spring.elasticsearch.socket-timeout` | Socket timeout used when communicating with Elasticsearch. | `30s`  
Properties starting with `spring.ai.vectorstore.elasticsearch.*` are used to
configure the `ElasticsearchVectorStore`:

Property | Description | Default Value  
---|---|---  
`spring.ai.vectorstore.elasticsearch.initialize-schema` | Whether to initialize the required schema | `false`  
`spring.ai.vectorstore.elasticsearch.index-name` | The name of the index to store the vectors | `spring-ai-document-index`  
`spring.ai.vectorstore.elasticsearch.dimensions` | The number of dimensions in the vector | `1536`  
`spring.ai.vectorstore.elasticsearch.similarity` | The similarity function to use | `cosine`  
The following similarity functions are available:

  * `cosine` - Default, suitable for most use cases. Measures cosine similarity between vectors.
  * `l2_norm` - Euclidean distance between vectors. Lower values indicate higher similarity.
  * `dot_product` - Best performance for normalized vectors (e.g., OpenAI embeddings).

More details about each in the Elasticsearch Documentation on dense vectors.

##  Metadata Filtering

You can leverage the generic, portable metadata filters with Elasticsearch as
well.

For example, you can use either the text expression language:

```

vectorStore.similaritySearch(SearchRequest.builder()

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
proprietary Elasticsearch Query string query.  
---|---  
For example, this portable filter expression:

```

author in ['john', 'jill'] && 'article_type' == 'blog'

Copied!

```

is converted into the proprietary Elasticsearch filter format:

```

(metadata.author:john OR jill) AND metadata.article_type:blog

Copied!

```

##  Manual Configuration

Instead of using the Spring Boot auto-configuration, you can manually configure
the Elasticsearch vector store. For this you need to add the `spring-ai-
elasticsearch-store` to your project:

```

<dependency>

    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-elasticsearch-store</artifactId>
</dependency>

Copied!

```

or to your Gradle `build.gradle` build file.

```

dependencies {

    implementation 'org.springframework.ai:spring-ai-elasticsearch-store'
}

Copied!

```

Create an Elasticsearch `RestClient` bean. Read the Elasticsearch Documentation
for more in-depth information about the configuration of a custom RestClient.

```

@Bean

public RestClient restClient() {

    return RestClient.builder(new HttpHost("<host>", 9200, "http"))
        .setDefaultHeaders(new Header[]{
            new BasicHeader("Authorization", "Basic <encoded username and password>")
        })
        .build();
}

Copied!

```

Then create the `ElasticsearchVectorStore` bean using the builder pattern:

```

@Bean

public VectorStore vectorStore(RestClient restClient, EmbeddingModel
embeddingModel) {

    ElasticsearchVectorStoreOptions options = new ElasticsearchVectorStoreOptions();
    options.setIndexName("custom-index");    // Optional: defaults to "spring-ai-document-index"
    options.setSimilarity(COSINE);           // Optional: defaults to COSINE
    options.setDimensions(1536);             // Optional: defaults to model dimensions or 1536

    return ElasticsearchVectorStore.builder(restClient, embeddingModel)
        .options(options)                     // Optional: use custom options
        .initializeSchema(true)               // Optional: defaults to false
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

##  Accessing the Native Client

The Elasticsearch Vector Store implementation provides access to the underlying
native Elasticsearch client (`ElasticsearchClient`) through the
`getNativeClient()` method:

```

ElasticsearchVectorStore vectorStore =
context.getBean(ElasticsearchVectorStore.class);

Optional<ElasticsearchClient> nativeClient = vectorStore.getNativeClient();

if (nativeClient.isPresent()) {

    ElasticsearchClient client = nativeClient.get();
    // Use the native client for Elasticsearch-specific operations
}

Copied!

```

The native client gives you access to Elasticsearch-specific features and
operations that might not be exposed through the `VectorStore` interface.

Couchbase GemFire

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

