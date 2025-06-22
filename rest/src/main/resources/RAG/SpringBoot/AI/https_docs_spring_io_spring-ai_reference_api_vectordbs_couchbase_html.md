source URL: https://docs.spring.io/spring-ai/reference/api/vectordbs/couchbase.html 
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

### Couchbase

  * Prerequisites
  * Auto-configuration
  * Configuration Properties
  * Metadata Filtering
  * Manual Configuration
  * Limitations

  * Spring AI
  * Reference
  * Vector Databases
  * Couchbase

# Couchbase

### Couchbase

  * Prerequisites
  * Auto-configuration
  * Configuration Properties
  * Metadata Filtering
  * Manual Configuration
  * Limitations

This section will walk you through setting up the `CouchbaseSearchVectorStore`
to store document embeddings and perform similarity searches using Couchbase.

Couchbase is a distributed, JSON document database, with all the desired
capabilities of a relational DBMS. Among other features, it allows users to
query information using vector-based storage and retrieval.

##  Prerequisites

A running Couchbase instance. The following options are available: Couchbase *
Docker * Capella - Couchbase as a Service * Install Couchbase locally *
Couchbase Kubernetes Operator

##  Auto-configuration

|  There has been a significant change in the Spring AI auto-configuration,
starter modules' artifact names. Please refer to the upgrade notes for more
information.  
---|---  
Spring AI provides Spring Boot auto-configuration for the Couchbase Vector
Store. To enable it, add the following dependency to your project’s Maven
`pom.xml` file:

```

<dependency>

    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-starter-vector-store-couchbase</artifactId>
</dependency>

Copied!

```

or to your Gradle `build.gradle` build file.

```

dependencies {

    implementation 'org.springframework.ai:spring-ai-couchbase-store-spring-boot-starter'
}

Copied!

```

|  Couchbase Vector search is only available in starting version 7.6 and Java
SDK version 3.6.0"  
---|---  
|  Refer to the Dependency Management section to add the Spring AI BOM to your
build file.  
---|---  
|  Refer to the Artifact Repositories section to add Milestone and/or Snapshot
Repositories to your build file.  
---|---  
The vector store implementation can initialize the configured bucket, scope,
collection and search index for you, with default options, but you must opt-in
by specifying the `initializeSchema` boolean in the appropriate constructor.

|  This is a breaking change! In earlier versions of Spring AI, this schema
initialization happened by default.  
---|---  
Please have a look at the list of configuration parameters for the vector store
to learn about the default values and configuration options.

Additionally, you will need a configured `EmbeddingModel` bean. Refer to the
EmbeddingModel section for more information.

Now you can auto-wire the `CouchbaseSearchVectorStore` as a vector store in your
application.

```

@Autowired VectorStore vectorStore;

// ...

List <Document> documents = List.of(

    new Document("Spring AI rocks!! Spring AI rocks!! Spring AI rocks!! Spring AI rocks!! Spring AI rocks!!", Map.of("meta1", "meta1")),
    new Document("The World is Big and Salvation Lurks Around the Corner"),
    new Document("You walk forward facing the past and you turn back toward the future.", Map.of("meta2", "meta2")));

// Add the documents to Qdrant

vectorStore.add(documents);

// Retrieve documents similar to a query

List<Document> results =
vectorStore.similaritySearch(SearchRequest.query("Spring").withTopK(5));

Copied!

```

###  Configuration Properties

To connect to Couchbase and use the `CouchbaseSearchVectorStore`, you need to
provide access details for your instance. Configuration can be provided via
Spring Boot’s `application.properties`:

```

spring.ai.openai.api-key=<key>

spring.couchbase.connection-string=<conn_string>

spring.couchbase.username=<username>

spring.couchbase.password=<password>

Copied!

```

If you prefer to use environment variables for sensitive information like
passwords or API keys, you have multiple options:

####  Option 1: Using Spring Expression Language (SpEL)

You can use custom environment variable names and reference them in your
application configuration using SpEL:

```

# In application.yml

spring:

  ai:

    openai:
      api-key: ${OPENAI_API_KEY}
  couchbase:

    connection-string: ${COUCHBASE_CONN_STRING}
    username: ${COUCHBASE_USER}
    password: ${COUCHBASE_PASSWORD}
Copied!

```

```

# In your environment or .env file

export OPENAI_API_KEY=<api-key>

export COUCHBASE_CONN_STRING=<couchbase connection string like
couchbase://localhost>

export COUCHBASE_USER=<couchbase username>

export COUCHBASE_PASSWORD=<couchbase password>

Copied!

```

####  Option 2: Accessing Environment Variables Programmatically

Alternatively, you can access environment variables in your Java code:

```

String apiKey = System.getenv("OPENAI_API_KEY");

Copied!

```

This approach gives you flexibility in naming your environment variables while
keeping sensitive information out of your application configuration files.

|  If you choose to create a shell script for ease in future work, be sure to
run it prior to starting your application by "sourcing" the file, i.e. `source
<your_script_name>.sh`.  
---|---  
Spring Boot’s auto-configuration feature for the Couchbase Cluster will create a
bean instance that will be used by the `CouchbaseSearchVectorStore`.

The Spring Boot properties starting with `spring.couchbase.*` are used to
configure the Couchbase cluster instance:

Property | Description | Default Value  
---|---|---  
`spring.couchbase.connection-string` | A couchbase connection string | `couchbase://localhost`  
`spring.couchbase.password` | Password for authentication with Couchbase. | -  
`spring.couchbase.username` | Username for authentication with Couchbase. | -  
`spring.couchbase.env.io.minEndpoints` | Minimum number of sockets per node. | 1  
`spring.couchbase.env.io.maxEndpoints` | Maximum number of sockets per node. | 12  
`spring.couchbase.env.io.idleHttpConnectionTimeout` | Length of time an HTTP connection may remain idle before it is closed and removed from the pool. | 1s  
`spring.couchbase.env.ssl.enabled` | Whether to enable SSL support. Enabled automatically if a "bundle" is provided unless specified otherwise. | -  
`spring.couchbase.env.ssl.bundle` | SSL bundle name. | -  
`spring.couchbase.env.timeouts.connect` | Bucket connect timeout. | 10s  
`spring.couchbase.env.timeouts.disconnect` | Bucket disconnect timeout. | 10s  
`spring.couchbase.env.timeouts.key-value` | Timeout for operations on a specific key-value. | 2500ms  
`spring.couchbase.env.timeouts.key-value` | Timeout for operations on a specific key-value with a durability level. | 10s  
`spring.couchbase.env.timeouts.key-value-durable` | Timeout for operations on a specific key-value with a durability level. | 10s  
`spring.couchbase.env.timeouts.query` | SQL++ query operations timeout. | 75s  
`spring.couchbase.env.timeouts.view` | Regular and geospatial view operations timeout. | 75s  
`spring.couchbase.env.timeouts.search` | Timeout for the search service. | 75s  
`spring.couchbase.env.timeouts.analytics` | Timeout for the analytics service. | 75s  
`spring.couchbase.env.timeouts.management` | Timeout for the management operations. | 75s  
Properties starting with the `spring.ai.vectorstore.couchbase.*` prefix are used
to configure `CouchbaseSearchVectorStore`.

Property | Description | Default Value  
---|---|---  
`spring.ai.vectorstore.couchbase.index-name` | The name of the index to store the vectors. | spring-ai-document-index  
`spring.ai.vectorstore.couchbase.bucket-name` | The name of the Couchbase Bucket, parent of the scope. | default  
`spring.ai.vectorstore.couchbase.scope-name` | The name of the Couchbase scope, parent of the collection. Search queries will be executed in the scope context. | _default_  
`spring.ai.vectorstore.couchbase.collection-name` | The name of the Couchbase collection to store the Documents. | _default_  
`spring.ai.vectorstore.couchbase.dimensions` | The number of dimensions in the vector. | 1536  
`spring.ai.vectorstore.couchbase.similarity` | The similarity function to use. | `dot_product`  
`spring.ai.vectorstore.couchbase.optimization` | The similarity function to use. | `recall`  
`spring.ai.vectorstore.couchbase.initialize-schema` | whether to initialize the required schema | `false`  
The following similarity functions are available:

  * l2_norm
  * dot_product

The following index optimizations are available:

  * recall
  * latency

More details about each in the Couchbase Documentation on vector searches.

##  Metadata Filtering

You can leverage the generic, portable metadata filters with the Couchbase
store.

For example, you can use either the text expression language:

```

vectorStore.similaritySearch(

    SearchRequest.defaults()
    .query("The World")
    .topK(TOP_K)
    .filterExpression("author in ['john', 'jill'] && article_type == 'blog'"));
Copied!

```

or programmatically using the `Filter.Expression` DSL:

```

FilterExpressionBuilder b = new FilterExpressionBuilder();

vectorStore.similaritySearch(SearchRequest.defaults()

    .query("The World")
    .topK(TOP_K)
    .filterExpression(b.and(
        b.in("author","john", "jill"),
        b.eq("article_type", "blog")).build()));
Copied!

```

|  These filter expressions are converted into the equivalent Couchbase SQL++
filters.  
---|---  
##  Manual Configuration

Instead of using the Spring Boot auto-configuration, you can manually configure
the Couchbase vector store. For this you need to add the `spring-ai-couchbase-
store` to your project:

```

<dependency>

    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-couchbase-store</artifactId>
</dependency>

Copied!

```

or to your Gradle `build.gradle` build file.

```

dependencies {

    implementation 'org.springframework.ai:spring-ai-couchbase-store'
}

Copied!

```

Create a Couchbase `Cluster` bean. Read the Couchbase Documentation for more in-
depth information about the configuration of a custom Cluster instance.

```

@Bean

public Cluster cluster() {

    return Cluster.connect("couchbase://localhost", "username", "password");
}

Copied!

```

and then create the `CouchbaseSearchVectorStore` bean using the builder pattern:

```

@Bean

public VectorStore couchbaseSearchVectorStore(Cluster cluster,

                                              EmbeddingModel embeddingModel,
                                              Boolean initializeSchema) {
    return CouchbaseSearchVectorStore
            .builder(cluster, embeddingModel)
            .bucketName("test")
            .scopeName("test")
            .collectionName("test")
            .initializeSchema(initializeSchema)
            .build();
}

// This can be any EmbeddingModel implementation.

@Bean

public EmbeddingModel embeddingModel() {

    return new OpenAiEmbeddingModel(OpenAiApi.builder().apiKey(this.openaiKey).build());
}

Copied!

```

##  Limitations

|  It is mandatory to have the following Couchbase services activated: Data,
Query, Index, Search. While Data and Search could be enough, Query and Index are
necessary to support the complete metadata filtering mechanism.  
---|---  
Chroma Elasticsearch

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

