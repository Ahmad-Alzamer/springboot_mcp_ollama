source URL: https://docs.spring.io/spring-ai/reference/api/vectordbs/weaviate.html 
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

### Weaviate

  * Prerequisites
  * Dependencies
  * Configuration
  * Option 1: Using Spring Expression Language (SpEL)
  * Option 2: Accessing Environment Variables Programmatically
  * Auto-configuration
  * Manual Configuration
  * Metadata filtering
  * Run Weaviate in Docker
  * WeaviateVectorStore properties
  * Accessing the Native Client

  * Spring AI
  * Reference
  * Vector Databases
  * Weaviate

# Weaviate

### Weaviate

  * Prerequisites
  * Dependencies
  * Configuration
  * Option 1: Using Spring Expression Language (SpEL)
  * Option 2: Accessing Environment Variables Programmatically
  * Auto-configuration
  * Manual Configuration
  * Metadata filtering
  * Run Weaviate in Docker
  * WeaviateVectorStore properties
  * Accessing the Native Client

This section walks you through setting up the Weaviate VectorStore to store
document embeddings and perform similarity searches.

Weaviate is an open-source vector database that allows you to store data objects
and vector embeddings from your favorite ML-models and scale seamlessly into
billions of data objects. It provides tools to store document embeddings,
content, and metadata and to search through those embeddings, including metadata
filtering.

##  Prerequisites

  * A running Weaviate instance. The following options are available:
    * Weaviate Cloud Service (requires account creation and API key)
    * Docker container
  * If required, an API key for the EmbeddingModel to generate the embeddings stored by the `WeaviateVectorStore`.

##  Dependencies

|  There has been a significant change in the Spring AI auto-configuration,
starter modules' artifact names. Please refer to the upgrade notes for more
information.  
---|---  
Add the Weaviate Vector Store dependency to your project:

```

<dependency>

    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-weaviate-store</artifactId>
</dependency>

Copied!

```

or to your Gradle `build.gradle` build file.

```

dependencies {

    implementation 'org.springframework.ai:spring-ai-weaviate-store'
}

Copied!

```

|  Refer to the Dependency Management section to add the Spring AI BOM to your
build file.  
---|---  
##  Configuration

To connect to Weaviate and use the `WeaviateVectorStore`, you need to provide
access details for your instance. Configuration can be provided via Spring
Boot’s _application.properties_ :

```

spring.ai.vectorstore.weaviate.host=<host_of_your_weaviate_instance>

spring.ai.vectorstore.weaviate.scheme=<http_or_https>

spring.ai.vectorstore.weaviate.api-key=<your_api_key>

# API key if needed, e.g. OpenAI

spring.ai.openai.api-key=<api-key>

Copied!

```

If you prefer to use environment variables for sensitive information like API
keys, you have multiple options:

###  Option 1: Using Spring Expression Language (SpEL)

You can use custom environment variable names and reference them in your
application configuration:

```

# In application.yml

spring:

  ai:

    vectorstore:
      weaviate:
        host: ${WEAVIATE_HOST}
        scheme: ${WEAVIATE_SCHEME}
        api-key: ${WEAVIATE_API_KEY}
    openai:
      api-key: ${OPENAI_API_KEY}
Copied!

```

```

# In your environment or .env file

export WEAVIATE_HOST=<host_of_your_weaviate_instance>

export WEAVIATE_SCHEME=<http_or_https>

export WEAVIATE_API_KEY=<your_api_key>

export OPENAI_API_KEY=<api-key>

Copied!

```

###  Option 2: Accessing Environment Variables Programmatically

Alternatively, you can access environment variables in your Java code:

```

String weaviateApiKey = System.getenv("WEAVIATE_API_KEY");

String openAiApiKey = System.getenv("OPENAI_API_KEY");

Copied!

```

|  If you choose to create a shell script to manage your environment variables,
be sure to run it prior to starting your application by "sourcing" the file,
i.e. `source <your_script_name>.sh`.  
---|---  
##  Auto-configuration

Spring AI provides Spring Boot auto-configuration for the Weaviate Vector Store.
To enable it, add the following dependency to your project’s Maven `pom.xml`
file:

```

<dependency>

    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-starter-vector-store-weaviate</artifactId>
</dependency>

Copied!

```

or to your Gradle `build.gradle` build file.

```

dependencies {

    implementation 'org.springframework.ai:spring-ai-starter-vector-store-weaviate'
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
Additionally, you will need a configured `EmbeddingModel` bean. Refer to the
EmbeddingModel section for more information.

Here is an example of the required bean:

```

@Bean

public EmbeddingModel embeddingModel() {

    // Retrieve API key from a secure source or environment variable
    String apiKey = System.getenv("OPENAI_API_KEY");

    // Can be any other EmbeddingModel implementation
    return new OpenAiEmbeddingModel(OpenAiApi.builder().apiKey(apiKey).build());
}

Copied!

```

Now you can auto-wire the `WeaviateVectorStore` as a vector store in your
application.

##  Manual Configuration

Instead of using Spring Boot auto-configuration, you can manually configure the
`WeaviateVectorStore` using the builder pattern:

```

@Bean

public WeaviateClient weaviateClient() {

    return new WeaviateClient(new Config("http", "localhost:8080"));
}

@Bean

public VectorStore vectorStore(WeaviateClient weaviateClient, EmbeddingModel
embeddingModel) {

    return WeaviateVectorStore.builder(weaviateClient, embeddingModel)
        .objectClass("CustomClass")                    // Optional: defaults to "SpringAiWeaviate"
        .consistencyLevel(ConsistentLevel.QUORUM)      // Optional: defaults to ConsistentLevel.ONE
        .filterMetadataFields(List.of(                 // Optional: fields that can be used in filters
            MetadataField.text("country"),
            MetadataField.number("year")))
        .build();
}

Copied!

```

##  Metadata filtering

You can leverage the generic, portable metadata filters with Weaviate store as
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

|  Those (portable) filter expressions get automatically converted into the
proprietary Weaviate where filters.  
---|---  
For example, this portable filter expression:

```

country in ['UK', 'NL'] && year >= 2020

Copied!

```

is converted into the proprietary Weaviate GraphQL filter format:

```

operator: And

operands:

    [{
        operator: Or
        operands:
            [{
                path: ["meta_country"]
                operator: Equal
                valueText: "UK"
            },
            {
                path: ["meta_country"]
                operator: Equal
                valueText: "NL"
            }]
    },
    {
        path: ["meta_year"]
        operator: GreaterThanEqual
        valueNumber: 2020
    }]
Copied!

```

##  Run Weaviate in Docker

To quickly get started with a local Weaviate instance, you can run it in Docker:

```

docker run -it --rm --name weaviate \

    -e AUTHENTICATION_ANONYMOUS_ACCESS_ENABLED=true \
    -e PERSISTENCE_DATA_PATH=/var/lib/weaviate \
    -e QUERY_DEFAULTS_LIMIT=25 \
    -e DEFAULT_VECTORIZER_MODULE=none \
    -e CLUSTER_HOSTNAME=node1 \
    -p 8080:8080 \
    semitechnologies/weaviate:1.22.4
Copied!

```

This starts a Weaviate instance accessible at localhost:8080.

##  WeaviateVectorStore properties

You can use the following properties in your Spring Boot configuration to
customize the Weaviate vector store.

Property | Description | Default value  
---|---|---  
`spring.ai.vectorstore.weaviate.host` | The host of the Weaviate server | localhost:8080  
`spring.ai.vectorstore.weaviate.scheme` | Connection schema | http  
`spring.ai.vectorstore.weaviate.api-key` | The API key for authentication |   
`spring.ai.vectorstore.weaviate.object-class` | The class name for storing documents | SpringAiWeaviate  
`spring.ai.vectorstore.weaviate.consistency-level` | Desired tradeoff between consistency and speed | ConsistentLevel.ONE  
`spring.ai.vectorstore.weaviate.filter-field` | Configures metadata fields that can be used in filters. Format: spring.ai.vectorstore.weaviate.filter-field.<field-name>=<field-type> |   
##  Accessing the Native Client

The Weaviate Vector Store implementation provides access to the underlying
native Weaviate client (`WeaviateClient`) through the `getNativeClient()`
method:

```

WeaviateVectorStore vectorStore = context.getBean(WeaviateVectorStore.class);

Optional<WeaviateClient> nativeClient = vectorStore.getNativeClient();

if (nativeClient.isPresent()) {

    WeaviateClient client = nativeClient.get();
    // Use the native client for Weaviate-specific operations
}

Copied!

```

The native client gives you access to Weaviate-specific features and operations
that might not be exposed through the `VectorStore` interface.

Typesense Observability

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

