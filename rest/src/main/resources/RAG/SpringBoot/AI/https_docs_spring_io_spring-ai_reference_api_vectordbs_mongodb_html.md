source URL: https://docs.spring.io/spring-ai/reference/api/vectordbs/mongodb.html 
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

### MongoDB Atlas

  * What is MongoDB Atlas?
  * Prerequisites
  * Auto-configuration
  * Configuration Properties
  * Manual Configuration
  * Metadata Filtering
  * Tutorials and Code Examples
  * Accessing the Native Client

  * Spring AI
  * Reference
  * Vector Databases
  * MongoDB Atlas

# MongoDB Atlas

### MongoDB Atlas

  * What is MongoDB Atlas?
  * Prerequisites
  * Auto-configuration
  * Configuration Properties
  * Manual Configuration
  * Metadata Filtering
  * Tutorials and Code Examples
  * Accessing the Native Client

This section walks you through setting up MongoDB Atlas as a vector store to use
with Spring AI.

##  What is MongoDB Atlas?

MongoDB Atlas is the fully-managed cloud database from MongoDB available in AWS,
Azure, and GCP. Atlas supports native Vector Search and full text search on your
MongoDB document data.

MongoDB Atlas Vector Search allows you to store your embeddings in MongoDB
documents, create vector search indexes, and perform KNN searches with an
approximate nearest neighbor algorithm (Hierarchical Navigable Small Worlds).
You can use the `$vectorSearch` aggregation operator in a MongoDB aggregation
stage to perform a search on your vector embeddings.

##  Prerequisites

  * An Atlas cluster running MongoDB version 6.0.11, 7.0.2, or later. To get started with MongoDB Atlas, you can follow the instructions here. Ensure that your IP address is included in your Atlas project’s access list.
  * A running MongoDB Atlas instance with Vector Search enabled
  * Collection with vector search index configured
  * Collection schema with id (string), content (string), metadata (document), and embedding (vector) fields
  * Proper access permissions for index and collection operations

##  Auto-configuration

|  There has been a significant change in the Spring AI auto-configuration,
starter modules' artifact names. Please refer to the upgrade notes for more
information.  
---|---  
Spring AI provides Spring Boot auto-configuration for the MongoDB Atlas Vector
Store. To enable it, add the following dependency to your project’s Maven
`pom.xml` file:

```

<dependency>

    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-starter-vector-store-mongodb-atlas</artifactId>
</dependency>

Copied!

```

or to your Gradle `build.gradle` build file:

```

dependencies {

    implementation 'org.springframework.ai:spring-ai-starter-vector-store-mongodb-atlas'
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
you must opt-in by setting `spring.ai.vectorstore.mongodb.initialize-
schema=true` in the `application.properties` file. Alternatively you can opt-out
the initialization and create the index manually using the MongoDB Atlas UI,
Atlas Administration API, or Atlas CLI, which can be useful if the index needs
advanced mapping or additional configuration.

|  this is a breaking change! In earlier versions of Spring AI, this schema
initialization happened by default.  
---|---  
Please have a look at the list of configuration parameters for the vector store
to learn about the default values and configuration options.

Additionally, you will need a configured `EmbeddingModel` bean. Refer to the
EmbeddingModel section for more information.

Now you can auto-wire the `MongoDBAtlasVectorStore` as a vector store in your
application:

```

@Autowired VectorStore vectorStore;

// ...

List<Document> documents = List.of(

    new Document("Spring AI rocks!! Spring AI rocks!! Spring AI rocks!! Spring AI rocks!! Spring AI rocks!!", Map.of("meta1", "meta1")),
    new Document("The World is Big and Salvation Lurks Around the Corner"),
    new Document("You walk forward facing the past and you turn back toward the future.", Map.of("meta2", "meta2")));

// Add the documents to MongoDB Atlas

vectorStore.add(documents);

// Retrieve documents similar to a query

List<Document> results =
vectorStore.similaritySearch(SearchRequest.builder().query("Spring").topK(5).build());

Copied!

```

###  Configuration Properties

To connect to MongoDB Atlas and use the `MongoDBAtlasVectorStore`, you need to
provide access details for your instance. A simple configuration can be provided
via Spring Boot’s `application.yml`:

```

spring:

  data:

    mongodb:
      uri: <mongodb atlas connection string>
      database: <database name>
  ai:

    vectorstore:
      mongodb:
        initialize-schema: true
        collection-name: custom_vector_store
        index-name: custom_vector_index
        path-name: custom_embedding
        metadata-fields-to-filter: author,year
Copied!

```

Properties starting with `spring.ai.vectorstore.mongodb.*` are used to configure
the `MongoDBAtlasVectorStore`:

Property | Description | Default Value  
---|---|---  
`spring.ai.vectorstore.mongodb.initialize-schema` | Whether to initialize the required schema | `false`  
`spring.ai.vectorstore.mongodb.collection-name` | The name of the collection to store the vectors | `vector_store`  
`spring.ai.vectorstore.mongodb.index-name` | The name of the vector search index | `vector_index`  
`spring.ai.vectorstore.mongodb.path-name` | The path where vectors are stored | `embedding`  
`spring.ai.vectorstore.mongodb.metadata-fields-to-filter` | Comma-separated list of metadata fields that can be used for filtering | empty list  
##  Manual Configuration

Instead of using the Spring Boot auto-configuration, you can manually configure
the MongoDB Atlas vector store. For this you need to add the `spring-ai-mongodb-
atlas-store` to your project:

```

<dependency>

    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-mongodb-atlas-store</artifactId>
</dependency>

Copied!

```

or to your Gradle `build.gradle` build file:

```

dependencies {

    implementation 'org.springframework.ai:spring-ai-mongodb-atlas-store'
}

Copied!

```

Create a `MongoTemplate` bean:

```

@Bean

public MongoTemplate mongoTemplate() {

    return new MongoTemplate(MongoClients.create("<mongodb atlas connection string>"), "<database name>");
}

Copied!

```

Then create the `MongoDBAtlasVectorStore` bean using the builder pattern:

```

@Bean

public VectorStore vectorStore(MongoTemplate mongoTemplate, EmbeddingModel
embeddingModel) {

    return MongoDBAtlasVectorStore.builder(mongoTemplate, embeddingModel)
        .collectionName("custom_vector_store")           // Optional: defaults to "vector_store"
        .vectorIndexName("custom_vector_index")          // Optional: defaults to "vector_index"
        .pathName("custom_embedding")                    // Optional: defaults to "embedding"
        .numCandidates(500)                             // Optional: defaults to 200
        .metadataFieldsToFilter(List.of("author", "year")) // Optional: defaults to empty list
        .initializeSchema(true)                         // Optional: defaults to false
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

You can leverage the generic, portable metadata filters with MongoDB Atlas as
well.

For example, you can use either the text expression language:

```

vectorStore.similaritySearch(SearchRequest.builder()

        .query("The World")
        .topK(5)
        .similarityThreshold(0.7)
        .filterExpression("author in ['john', 'jill'] && article_type == 'blog'").build());
Copied!

```

or programmatically using the `Filter.Expression` DSL:

```

FilterExpressionBuilder b = new FilterExpressionBuilder();

vectorStore.similaritySearch(SearchRequest.builder()

        .query("The World")
        .topK(5)
        .similarityThreshold(0.7)
        .filterExpression(b.and(
                b.in("author", "john", "jill"),
                b.eq("article_type", "blog")).build()).build());
Copied!

```

|  Those (portable) filter expressions get automatically converted into the
proprietary MongoDB Atlas filter expressions.  
---|---  
For example, this portable filter expression:

```

author in ['john', 'jill'] && article_type == 'blog'

Copied!

```

is converted into the proprietary MongoDB Atlas filter format:

```

{

  "$and": [

    {
      "$or": [
        { "metadata.author": "john" },
        { "metadata.author": "jill" }
      ]
    },
    {
      "metadata.article_type": "blog"
    }
  ]

}

Copied!

```

##  Tutorials and Code Examples

To get started with Spring AI and MongoDB:

  * See the Getting Started guide for Spring AI Integration.
  * For a comprehensive code example demonstrating Retrieval Augmented Generation (RAG) with Spring AI and MongoDB, refer to this detailed tutorial.

##  Accessing the Native Client

The MongoDB Atlas Vector Store implementation provides access to the underlying
native MongoDB client (`MongoClient`) through the `getNativeClient()` method:

```

MongoDBAtlasVectorStore vectorStore =
context.getBean(MongoDBAtlasVectorStore.class);

Optional<MongoClient> nativeClient = vectorStore.getNativeClient();

if (nativeClient.isPresent()) {

    MongoClient client = nativeClient.get();
    // Use the native client for MongoDB-specific operations
}

Copied!

```

The native client gives you access to MongoDB-specific features and operations
that might not be exposed through the `VectorStore` interface.

Milvus Neo4j

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

