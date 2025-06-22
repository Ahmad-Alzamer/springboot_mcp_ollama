source URL: https://docs.spring.io/spring-ai/reference/api/vectordbs/pinecone.html 
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

### Pinecone

  * Prerequisites
  * Auto-configuration
  * Configuration properties
  * Metadata filtering
  * Manual Configuration
  * Sample Code
  * Accessing the Native Client

  * Spring AI
  * Reference
  * Vector Databases
  * Pinecone

# Pinecone

### Pinecone

  * Prerequisites
  * Auto-configuration
  * Configuration properties
  * Metadata filtering
  * Manual Configuration
  * Sample Code
  * Accessing the Native Client

This section walks you through setting up the Pinecone `VectorStore` to store
document embeddings and perform similarity searches.

Pinecone is a popular cloud-based vector database, which allows you to store and
search vectors efficiently.

##  Prerequisites

  1. Pinecone Account: Before you start, sign up for a Pinecone account.
  2. Pinecone Project: Once registered, generate an API key and create and index. You’ll need these details for configuration.
  3. `EmbeddingModel` instance to compute the document embeddings. Several options are available:
     * If required, an API key for the EmbeddingModel to generate the embeddings stored by the `PineconeVectorStore`.

To set up `PineconeVectorStore`, gather the following details from your Pinecone
account:

  * Pinecone API Key
  * Pinecone Index Name
  * Pinecone Namespace

|  This information is available to you in the Pinecone UI portal. The namespace
support is not available in the Pinecone free tier.  
---|---  
##  Auto-configuration

|  There has been a significant change in the Spring AI auto-configuration,
starter modules' artifact names. Please refer to the upgrade notes for more
information.  
---|---  
Spring AI provides Spring Boot auto-configuration for the Pinecone Vector Store.
To enable it, add the following dependency to your project’s Maven `pom.xml`
file:

```

<dependency>

    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-starter-vector-store-pinecone</artifactId>
</dependency>

Copied!

```

or to your Gradle `build.gradle` build file.

```

dependencies {

    implementation 'org.springframework.ai:spring-ai-starter-vector-store-pinecone'
}

Copied!

```

|  Refer to the Dependency Management section to add the Spring AI BOM to your
build file.  
---|---  
|  Refer to the Artifact Repositories section to add Maven Central and/or
Snapshot Repositories to your build file.  
---|---  
Additionally, you will need a configured `EmbeddingModel` bean. Refer to the
EmbeddingModel section for more information.

Here is an example of the needed bean:

```

@Bean

public EmbeddingModel embeddingModel() {

    // Can be any other EmbeddingModel implementation.
    return new OpenAiEmbeddingModel(new OpenAiApi(System.getenv("OPENAI_API_KEY")));
}

Copied!

```

To connect to Pinecone you need to provide access details for your instance. A
simple configuration can either be provided via Spring Boot’s
_application.properties_ ,

```

spring.ai.vectorstore.pinecone.apiKey=<your api key>

spring.ai.vectorstore.pinecone.index-name=<your index name>

# API key if needed, e.g. OpenAI

spring.ai.openai.api.key=<api-key>

Copied!

```

Please have a look at the list of configuration parameters for the vector store
to learn about the default values and configuration options.

Now you can Auto-wire the Pinecone Vector Store in your application and use it

```

@Autowired VectorStore vectorStore;

// ...

List <Document> documents = List.of(

    new Document("Spring AI rocks!! Spring AI rocks!! Spring AI rocks!! Spring AI rocks!! Spring AI rocks!!", Map.of("meta1", "meta1")),
    new Document("The World is Big and Salvation Lurks Around the Corner"),
    new Document("You walk forward facing the past and you turn back toward the future.", Map.of("meta2", "meta2")));

// Add the documents

vectorStore.add(documents);

// Retrieve documents similar to a query

List<Document> results =
this.vectorStore.similaritySearch(SearchRequest.builder().query("Spring").topK(5).build());

Copied!

```

###  Configuration properties

You can use the following properties in your Spring Boot configuration to
customize the Pinecone vector store.

Property | Description | Default value  
---|---|---  
`spring.ai.vectorstore.pinecone.api-key` | Pinecone API Key | -  
`spring.ai.vectorstore.pinecone.index-name` | Pinecone index name | -  
`spring.ai.vectorstore.pinecone.namespace` | Pinecone namespace | -  
`spring.ai.vectorstore.pinecone.content-field-name` | Pinecone metadata field name used to store the original text content. | `document_content`  
`spring.ai.vectorstore.pinecone.distance-metadata-field-name` | Pinecone metadata field name used to store the computed distance. | `distance`  
`spring.ai.vectorstore.pinecone.server-side-timeout` |  | 20 sec.  
##  Metadata filtering

You can leverage the generic, portable metadata filters with the Pinecone store.

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

|  These filter expressions are converted into the equivalent Pinecone filters.  
---|---  
##  Manual Configuration

If you prefer to configure `PineconeVectorStore` manually, you can do so by
using the `PineconeVectorStore#Builder`.

Add these dependencies to your project:

  * OpenAI: Required for calculating embeddings.

```

<dependency>

	<groupId>org.springframework.ai</groupId>
	<artifactId>spring-ai-starter-model-openai</artifactId>
</dependency>

Copied!

```

  * Pinecone

```

<dependency>

    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-pinecone-store</artifactId>
</dependency>

Copied!

```

|  Refer to the Dependency Management section to add the Spring AI BOM to your
build file.  
---|---  
###  Sample Code

To configure Pinecone in your application, you can use the following setup:

```

@Bean

public VectorStore pineconeVectorStore(EmbeddingModel embeddingModel) {

    return PineconeVectorStore.builder(embeddingModel)
            .apiKey(PINECONE_API_KEY)
            .indexName(PINECONE_INDEX_NAME)
            .namespace(PINECONE_NAMESPACE) // the free tier doesn't support namespaces.
            .contentFieldName(CUSTOM_CONTENT_FIELD_NAME) // optional field to store the original content. Defaults to `document_content`
            .build();
}

Copied!

```

In your main code, create some documents:

```

List<Document> documents = List.of(

	new Document("Spring AI rocks!! Spring AI rocks!! Spring AI rocks!! Spring AI rocks!! Spring AI rocks!!", Map.of("meta1", "meta1")),
	new Document("The World is Big and Salvation Lurks Around the Corner"),
	new Document("You walk forward facing the past and you turn back toward the future.", Map.of("meta2", "meta2")));
Copied!

```

Add the documents to Pinecone:

```

vectorStore.add(documents);

Copied!

```

And finally, retrieve documents similar to a query:

```

List<Document> results =
vectorStore.similaritySearch(SearchRequest.query("Spring").topK(5).build());

Copied!

```

If all goes well, you should retrieve the document containing the text "Spring
AI rocks!!".

##  Accessing the Native Client

The Pinecone Vector Store implementation provides access to the underlying
native Pinecone client (`PineconeConnection`) through the `getNativeClient()`
method:

```

PineconeVectorStore vectorStore = context.getBean(PineconeVectorStore.class);

Optional<PineconeConnection> nativeClient = vectorStore.getNativeClient();

if (nativeClient.isPresent()) {

    PineconeConnection client = nativeClient.get();
    // Use the native client for Pinecone-specific operations
}

Copied!

```

The native client gives you access to Pinecone-specific features and operations
that might not be exposed through the `VectorStore` interface.

PGvector Qdrant

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

