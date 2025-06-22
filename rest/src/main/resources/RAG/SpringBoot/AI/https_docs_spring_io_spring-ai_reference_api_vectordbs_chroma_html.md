source URL: https://docs.spring.io/spring-ai/reference/api/vectordbs/chroma.html 
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

### Chroma

  * Prerequisites
  * Auto-configuration
  * Configuration properties
  * Metadata filtering
  * Manual Configuration
  * Sample Code
  * Run Chroma Locally

  * Spring AI
  * Reference
  * Vector Databases
  * Chroma

# Chroma

### Chroma

  * Prerequisites
  * Auto-configuration
  * Configuration properties
  * Metadata filtering
  * Manual Configuration
  * Sample Code
  * Run Chroma Locally

This section will walk you through setting up the Chroma VectorStore to store
document embeddings and perform similarity searches.

Chroma is the open-source embedding database. It gives you the tools to store
document embeddings, content, and metadata and to search through those
embeddings, including metadata filtering.

##  Prerequisites

  1. Access to ChromeDB. The setup local ChromaDB appendix shows how to set up a DB locally with a Docker container.
  2. `EmbeddingModel` instance to compute the document embeddings. Several options are available:
     * If required, an API key for the EmbeddingModel to generate the embeddings stored by the `ChromaVectorStore`.

On startup, the `ChromaVectorStore` creates the required collection if one is
not provisioned already.

##  Auto-configuration

|  There has been a significant change in the Spring AI auto-configuration,
starter modules' artifact names. Please refer to the upgrade notes for more
information.  
---|---  
Spring AI provides Spring Boot auto-configuration for the Chroma Vector Store.
To enable it, add the following dependency to your project’s Maven `pom.xml`
file:

```

<dependency>

    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-starter-vector-store-chroma</artifactId>
</dependency>

Copied!

```

or to your Gradle `build.gradle` build file.

```

dependencies {

    implementation 'org.springframework.ai:spring-ai-starter-vector-store-chroma'
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
Additionally, you will need a configured `EmbeddingModel` bean. Refer to the
EmbeddingModel section for more information.

Here is an example of the needed bean:

```

@Bean

public EmbeddingModel embeddingModel() {

    // Can be any other EmbeddingModel implementation.
    return new OpenAiEmbeddingModel(OpenAiApi.builder().apiKey(System.getenv("OPENAI_API_KEY")).build());
}

Copied!

```

To connect to Chroma you need to provide access details for your instance. A
simple configuration can either be provided via Spring Boot’s
_application.properties_ ,

```

# Chroma Vector Store connection properties

spring.ai.vectorstore.chroma.client.host=<your Chroma instance host>

spring.ai.vectorstore.chroma.client.port=<your Chroma instance port>

spring.ai.vectorstore.chroma.client.key-token=<your access token (if configure)>

spring.ai.vectorstore.chroma.client.username=<your username (if configure)>

spring.ai.vectorstore.chroma.client.password=<your password (if configure)>

# Chroma Vector Store collection properties

spring.ai.vectorstore.chroma.initialize-schema=<true or false>

spring.ai.vectorstore.chroma.collection-name=<your collection name>

# Chroma Vector Store configuration properties

# OpenAI API key if the OpenAI auto-configuration is used.

spring.ai.openai.api.key=<OpenAI Api-key>

Copied!

```

Please have a look at the list of configuration parameters for the vector store
to learn about the default values and configuration options.

Now you can auto-wire the Chroma Vector Store in your application and use it

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
customize the vector store.

Property | Description | Default value  
---|---|---  
`spring.ai.vectorstore.chroma.client.host` | Server connection host | http://localhost  
`spring.ai.vectorstore.chroma.client.port` | Server connection port | `8000`  
`spring.ai.vectorstore.chroma.client.key-token` | Access token (if configured) | -  
`spring.ai.vectorstore.chroma.client.username` | Access username (if configured) | -  
`spring.ai.vectorstore.chroma.client.password` | Access password (if configured) | -  
`spring.ai.vectorstore.chroma.collection-name` | Collection name | `SpringAiCollection`  
`spring.ai.vectorstore.chroma.initialize-schema` | Whether to initialize the required schema | `false`  
|  For ChromaDB secured with Static API Token Authentication use the
`ChromaApi#withKeyToken(<Your Token Credentials>)` method to set your
credentials. Check the `ChromaWhereIT` for an example. For ChromaDB secured with
Basic Authentication use the `ChromaApi#withBasicAuth(<your user>, <your
password>)` method to set your credentials. Check the `BasicAuthChromaWhereIT`
for an example.  
---|---  
##  Metadata filtering

You can leverage the generic, portable metadata filters with ChromaVector store
as well.

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
                            b.in("john", "jill"),
                            b.eq("article_type", "blog")).build()).build());
Copied!

```

|  Those (portable) filter expressions get automatically converted into the
proprietary Chroma `where` filter expressions.  
---|---  
For example, this portable filter expression:

```

author in ['john', 'jill'] && article_type == 'blog'

Copied!

```

is converted into the proprietary Chroma format

```

{"$and":[

	{"author": {"$in": ["john", "jill"]}},
	{"article_type":{"$eq":"blog"}}]
}

Copied!

```

##  Manual Configuration

If you prefer to configure the Chroma Vector Store manually, you can do so by
creating a `ChromaVectorStore` bean in your Spring Boot application.

Add these dependencies to your project: * Chroma VectorStore.

```

<dependency>

  <groupId>org.springframework.ai</groupId>

  <artifactId>spring-ai-chroma-store</artifactId>

</dependency>

Copied!

```

  * OpenAI: Required for calculating embeddings. You can use any other embedding model implementation.

```

<dependency>

 <groupId>org.springframework.ai</groupId>

 <artifactId>spring-ai-starter-model-openai</artifactId>

</dependency>

Copied!

```

|  Refer to the Dependency Management section to add the Spring AI BOM to your
build file.  
---|---  
###  Sample Code

Create a `RestClient.Builder` instance with proper ChromaDB authorization
configurations and Use it to create a `ChromaApi` instance:

```

@Bean

public RestClient.Builder builder() {

    return RestClient.builder().requestFactory(new SimpleClientHttpRequestFactory());
}

@Bean

public ChromaApi chromaApi(RestClient.Builder restClientBuilder) {

   String chromaUrl = "http://localhost:8000";

   ChromaApi chromaApi = new ChromaApi(chromaUrl, restClientBuilder);

   return chromaApi;

}

Copied!

```

Integrate with OpenAI’s embeddings by adding the Spring Boot OpenAI starter to
your project. This provides you with an implementation of the Embeddings client:

```

@Bean

public VectorStore chromaVectorStore(EmbeddingModel embeddingModel, ChromaApi
chromaApi) {

 return ChromaVectorStore.builder(chromaApi, embeddingModel)

    .collectionName("TestCollection")
    .initializeSchema(true)
    .build();
}

Copied!

```

In your main code, create some documents:

```

List<Document> documents = List.of(

 new Document("Spring AI rocks!! Spring AI rocks!! Spring AI rocks!! Spring AI
rocks!! Spring AI rocks!!", Map.of("meta1", "meta1")),

 new Document("The World is Big and Salvation Lurks Around the Corner"),

 new Document("You walk forward facing the past and you turn back toward the
future.", Map.of("meta2", "meta2")));

Copied!

```

Add the documents to your vector store:

```

vectorStore.add(documents);

Copied!

```

And finally, retrieve documents similar to a query:

```

List<Document> results = vectorStore.similaritySearch("Spring");

Copied!

```

If all goes well, you should retrieve the document containing the text "Spring
AI rocks!!".

###  Run Chroma Locally

```

docker run -it --rm --name chroma -p 8000:8000 ghcr.io/chroma-core/chroma:1.0.0

Copied!

```

Starts a chroma store at localhost:8000/api/v1

Apache Cassandra Vector Store Couchbase

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

