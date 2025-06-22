source URL: https://docs.spring.io/spring-ai/reference/api/vectordbs/azure.html 
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

### Azure AI Service

  * Prerequisites
  * Configuration
  * Dependencies
  * 1. Select an Embeddings interface implementation. You can choose between:
  * 2. Azure (AI Search) Vector Store
  * Configuration Properties
  * Sample Code
  * Metadata filtering
  * Accessing the Native Client

  * Spring AI
  * Reference
  * Vector Databases
  * Azure AI Service

# Azure AI Service

### Azure AI Service

  * Prerequisites
  * Configuration
  * Dependencies
  * 1. Select an Embeddings interface implementation. You can choose between:
  * 2. Azure (AI Search) Vector Store
  * Configuration Properties
  * Sample Code
  * Metadata filtering
  * Accessing the Native Client

This section will walk you through setting up the `AzureVectorStore` to store
document embeddings and perform similarity searches using the Azure AI Search
Service.

Azure AI Search is a versatile cloud-hosted cloud information retrieval system
that is part of Microsoft’s larger AI platform. Among other features, it allows
users to query information using vector-based storage and retrieval.

##  Prerequisites

  1. Azure Subscription: You will need an Azure subscription to use any Azure service.
  2. Azure AI Search Service: Create an AI Search service. Once the service is created, obtain the admin apiKey from the `Keys` section under `Settings` and retrieve the endpoint from the `Url` field under the `Overview` section.
  3. (Optional) Azure OpenAI Service: Create an Azure OpenAI service. **NOTE:** You may have to fill out a separate form to gain access to Azure Open AI services. Once the service is created, obtain the endpoint and apiKey from the `Keys and Endpoint` section under `Resource Management`.

##  Configuration

On startup, the `AzureVectorStore` can attempt to create a new index within your
AI Search service instance if you’ve opted in by setting the relevant
`initialize-schema` `boolean` property to `true` in the constructor or, if using
Spring Boot, setting `…​initialize-schema=true` in your `application.properties`
file.

|  this is a breaking change! In earlier versions of Spring AI, this schema
initialization happened by default.  
---|---  
Alternatively, you can create the index manually.

To set up an AzureVectorStore, you will need the settings retrieved from the
prerequisites above along with your index name:

  * Azure AI Search Endpoint
  * Azure AI Search Key
  * (optional) Azure OpenAI API Endpoint
  * (optional) Azure OpenAI API Key

You can provide these values as OS environment variables.

```

export AZURE_AI_SEARCH_API_KEY=<My AI Search API Key>

export AZURE_AI_SEARCH_ENDPOINT=<My AI Search Index>

export OPENAI_API_KEY=<My Azure AI API Key> (Optional)

Copied!

```

|  You can replace Azure Open AI implementation with any valid OpenAI
implementation that supports the Embeddings interface. For example, you could
use Spring AI’s Open AI or `TransformersEmbedding` implementations for
embeddings instead of the Azure implementation.  
---|---  
##  Dependencies

|  There has been a significant change in the Spring AI auto-configuration,
starter modules' artifact names. Please refer to the upgrade notes for more
information.  
---|---  
Add these dependencies to your project:

###  1. Select an Embeddings interface implementation. You can choose between:

  * OpenAI Embedding
  * Azure AI Embedding
  * Local Sentence Transformers Embedding

```

<dependency>

   <groupId>org.springframework.ai</groupId>

   <artifactId>spring-ai-starter-model-openai</artifactId>

</dependency>

Copied!

```

```

<dependency>

 <groupId>org.springframework.ai</groupId>

 <artifactId>spring-ai-starter-model-azure-openai</artifactId>

</dependency>

Copied!

```

```

<dependency>

 <groupId>org.springframework.ai</groupId>

 <artifactId>spring-ai-starter-model-transformers</artifactId>

</dependency>

Copied!

```

###  2. Azure (AI Search) Vector Store

```

<dependency>

    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-azure-store</artifactId>
</dependency>

Copied!

```

|  Refer to the Dependency Management section to add the Spring AI BOM to your
build file.  
---|---  
##  Configuration Properties

You can use the following properties in your Spring Boot configuration to
customize the Azure vector store.

Property | Default value  
---|---  
`spring.ai.vectorstore.azure.url` |   
`spring.ai.vectorstore.azure.api-key` |   
`spring.ai.vectorstore.azure.useKeylessAuth` | false  
`spring.ai.vectorstore.azure.initialize-schema` | false  
`spring.ai.vectorstore.azure.index-name` | spring_ai_azure_vector_store  
`spring.ai.vectorstore.azure.default-top-k` | 4  
`spring.ai.vectorstore.azure.default-similarity-threshold` | 0.0  
`spring.ai.vectorstore.azure.embedding-property` | embedding  
`spring.ai.vectorstore.azure.index-name` | spring-ai-document-index  
##  Sample Code

To configure an Azure `SearchIndexClient` in your application, you can use the
following code:

```

@Bean

public SearchIndexClient searchIndexClient() {

  return new
SearchIndexClientBuilder().endpoint(System.getenv("AZURE_AI_SEARCH_ENDPOINT"))

    .credential(new AzureKeyCredential(System.getenv("AZURE_AI_SEARCH_API_KEY")))
    .buildClient();
}

Copied!

```

To create a vector store, you can use the following code by injecting the
`SearchIndexClient` bean created in the above sample along with an
`EmbeddingModel` provided by the Spring AI library that implements the desired
Embeddings interface.

```

@Bean

public VectorStore vectorStore(SearchIndexClient searchIndexClient,
EmbeddingModel embeddingModel) {

  return AzureVectorStore.builder(searchIndexClient, embeddingModel)

    .initializeSchema(true)
    // Define the metadata fields to be used
    // in the similarity search filters.
    .filterMetadataFields(List.of(MetadataField.text("country"), MetadataField.int64("year"),
            MetadataField.date("activationDate")))
    .defaultTopK(5)
    .defaultSimilarityThreshold(0.7)
    .indexName("spring-ai-document-index")
    .build();
}

Copied!

```

|  You must list explicitly all metadata field names and types for any metadata
key used in the filter expression. The list above registers filterable metadata
fields: `country` of type `TEXT`, `year` of type `INT64`, and `active` of type
`BOOLEAN`. If the filterable metadata fields are expanded with new entries, you
have to (re)upload/update the documents with this metadata.  
---|---  
In your main code, create some documents:

```

List<Document> documents = List.of(

	new Document("Spring AI rocks!! Spring AI rocks!! Spring AI rocks!! Spring AI rocks!! Spring AI rocks!!", Map.of("country", "BG", "year", 2020)),
	new Document("The World is Big and Salvation Lurks Around the Corner"),
	new Document("You walk forward facing the past and you turn back toward the future.", Map.of("country", "NL", "year", 2023)));
Copied!

```

Add the documents to your vector store:

```

vectorStore.add(documents);

Copied!

```

And finally, retrieve documents similar to a query:

```

List<Document> results = vectorStore.similaritySearch(

    SearchRequest.builder()
      .query("Spring")
      .topK(5).build());
Copied!

```

If all goes well, you should retrieve the document containing the text "Spring
AI rocks!!".

###  Metadata filtering

You can leverage the generic, portable metadata filters with AzureVectorStore as
well.

For example, you can use either the text expression language:

```

vectorStore.similaritySearch(

   SearchRequest.builder()

      .query("The World")
      .topK(TOP_K)
      .similarityThreshold(SIMILARITY_THRESHOLD)
      .filterExpression("country in ['UK', 'NL'] && year >= 2020").build());
Copied!

```

or programmatically using the expression DSL:

```

FilterExpressionBuilder b = new FilterExpressionBuilder();

vectorStore.similaritySearch(

    SearchRequest.builder()
      .query("The World")
      .topK(TOP_K)
      .similarityThreshold(SIMILARITY_THRESHOLD)
      .filterExpression(b.and(
         b.in("country", "UK", "NL"),
         b.gte("year", 2020)).build()).build());
Copied!

```

The portable filter expressions get automatically converted into the proprietary
Azure Search OData filters. For example, the following portable filter
expression:

```

country in ['UK', 'NL'] && year >= 2020

Copied!

```

is converted into the following Azure OData filter expression:

```

$filter search.in(meta_country, 'UK,NL', ',') and meta_year ge 2020

Copied!

```

##  Accessing the Native Client

The Azure Vector Store implementation provides access to the underlying native
Azure Search client (`SearchClient`) through the `getNativeClient()` method:

```

AzureVectorStore vectorStore = context.getBean(AzureVectorStore.class);

Optional<SearchClient> nativeClient = vectorStore.getNativeClient();

if (nativeClient.isPresent()) {

    SearchClient client = nativeClient.get();
    // Use the native client for Azure Search-specific operations
}

Copied!

```

The native client gives you access to Azure Search-specific features and
operations that might not be exposed through the `VectorStore` interface.

Vector Databases Azure Cosmos DB

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

