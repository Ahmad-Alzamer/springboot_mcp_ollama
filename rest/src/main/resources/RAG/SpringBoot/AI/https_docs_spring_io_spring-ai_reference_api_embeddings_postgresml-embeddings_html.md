source URL: https://docs.spring.io/spring-ai/reference/api/embeddings/postgresml-embeddings.html 
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

### PostgresML Embeddings

  * Add Repositories and BOM
  * Auto-configuration
  * Embedding Properties
  * Runtime Options
  * Sample Controller
  * Manual configuration

  * Spring AI
  * Reference
  * Models
  * Embedding Models
  * PostgresML

# PostgresML Embeddings

### PostgresML Embeddings

  * Add Repositories and BOM
  * Auto-configuration
  * Embedding Properties
  * Runtime Options
  * Sample Controller
  * Manual configuration

Spring AI supports the PostgresML text embeddings models.

Embeddings are a numeric representation of text. They are used to represent
words and sentences as vectors, an array of numbers. Embeddings can be used to
find similar pieces of text, by comparing the similarity of the numeric vectors
using a distance measure, or they can be used as input features for other
machine learning models, since most algorithms can’t use text directly.

Many pre-trained LLMs can be used to generate embeddings from text within
PostgresML. You can browse all the models available to find the best solution on
Hugging Face.

##  Add Repositories and BOM

Spring AI artifacts are published in Maven Central and Spring Snapshot
repositories. Refer to the Artifact Repositories section to add these
repositories to your build system.

To help with dependency management, Spring AI provides a BOM (bill of materials)
to ensure that a consistent version of Spring AI is used throughout the entire
project. Refer to the Dependency Management section to add the Spring AI BOM to
your build system.

##  Auto-configuration

|  There has been a significant change in the Spring AI auto-configuration,
starter modules' artifact names. Please refer to the upgrade notes for more
information.  
---|---  
Spring AI provides Spring Boot auto-configuration for the Azure PostgresML
Embedding Model. To enable it add the following dependency to your project’s
Maven `pom.xml` file:

```

<dependency>

    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-starter-model-postgresml-embedding</artifactId>
</dependency>

Copied!

```

or to your Gradle `build.gradle` build file.

```

dependencies {

    implementation 'org.springframework.ai:spring-ai-starter-model-postgresml-embedding'
}

Copied!

```

|  Refer to the Dependency Management section to add the Spring AI BOM to your
build file.  
---|---  
Use the `spring.ai.postgresml.embedding.options.*` properties to configure your
`PostgresMlEmbeddingModel`. links

###  Embedding Properties

|  Enabling and disabling of the embedding auto-configurations are now
configured via top level properties with the prefix `spring.ai.model.embedding`.
To enable, spring.ai.model.embedding=postgresml (It is enabled by default) To
disable, spring.ai.model.embedding=none (or any value which doesn’t match
postgresml) This change is done to allow configuration of multiple models.  
---|---  
The prefix `spring.ai.postgresml.embedding` is property prefix that configures
the `EmbeddingModel` implementation for PostgresML embeddings.

Property | Description | Default  
---|---|---  
spring.ai.postgresml.embedding.enabled (Removed and no longer valid) | Enable PostgresML embedding model. | true  
spring.ai.model.embedding | Enable PostgresML embedding model. | postgresml  
spring.ai.postgresml.embedding.create-extension | Execute the SQL 'CREATE EXTENSION IF NOT EXISTS pgml' to enable the extesnion | false  
spring.ai.postgresml.embedding.options.transformer | The Hugging Face transformer model to use for the embedding. | distilbert-base-uncased  
spring.ai.postgresml.embedding.options.kwargs | Additional transformer specific options. | empty map  
spring.ai.postgresml.embedding.options.vectorType | PostgresML vector type to use for the embedding. Two options are supported: `PG_ARRAY` and `PG_VECTOR`. | PG_ARRAY  
spring.ai.postgresml.embedding.options.metadataMode | Document metadata aggregation mode | EMBED  
|  All properties prefixed with `spring.ai.postgresml.embedding.options` can be
overridden at runtime by adding a request specific Runtime Options to the
`EmbeddingRequest` call.  
---|---  
##  Runtime Options

Use the PostgresMlEmbeddingOptions.java to configure the
`PostgresMlEmbeddingModel` with options, such as the model to use and etc.

On start you can pass a `PostgresMlEmbeddingOptions` to the
`PostgresMlEmbeddingModel` constructor to configure the default options used for
all embedding requests.

At run-time you can override the default options, using a
`PostgresMlEmbeddingOptions` in your `EmbeddingRequest`.

For example to override the default model name for a specific request:

```

EmbeddingResponse embeddingResponse = embeddingModel.call(

    new EmbeddingRequest(List.of("Hello World", "World is big and salvation is near"),
            PostgresMlEmbeddingOptions.builder()
                .transformer("intfloat/e5-small")
                .vectorType(VectorType.PG_ARRAY)
                .kwargs(Map.of("device", "gpu"))
                .build()));
Copied!

```

##  Sample Controller

This will create a `EmbeddingModel` implementation that you can inject into your
class. Here is an example of a simple `@Controller` class that uses the
`EmbeddingModel` implementation.

```

spring.ai.postgresml.embedding.options.transformer=distilbert-base-uncased

spring.ai.postgresml.embedding.options.vectorType=PG_ARRAY

spring.ai.postgresml.embedding.options.metadataMode=EMBED

spring.ai.postgresml.embedding.options.kwargs.device=cpu

Copied!

```

```

@RestController

public class EmbeddingController {

    private final EmbeddingModel embeddingModel;

    @Autowired
    public EmbeddingController(EmbeddingModel embeddingModel) {
        this.embeddingModel = embeddingModel;
    }

    @GetMapping("/ai/embedding")
    public Map embed(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
        EmbeddingResponse embeddingResponse = this.embeddingModel.embedForResponse(List.of(message));
        return Map.of("embedding", embeddingResponse);
    }
}

Copied!

```

##  Manual configuration

Instead of using the Spring Boot auto-configuration, you can create the
`PostgresMlEmbeddingModel` manually. For this add the `spring-ai-postgresml`
dependency to your project’s Maven `pom.xml` file:

```

<dependency>

    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-postgresml</artifactId>
</dependency>

Copied!

```

or to your Gradle `build.gradle` build file.

```

dependencies {

    implementation 'org.springframework.ai:spring-ai-postgresml'
}

Copied!

```

|  Refer to the Dependency Management section to add the Spring AI BOM to your
build file.  
---|---  
Next, create an `PostgresMlEmbeddingModel` instance and use it to compute the
similarity between two input texts:

```

var jdbcTemplate = new JdbcTemplate(dataSource); // your posgresml data source

PostgresMlEmbeddingModel embeddingModel = new
PostgresMlEmbeddingModel(this.jdbcTemplate,

        PostgresMlEmbeddingOptions.builder()
            .transformer("distilbert-base-uncased") // huggingface transformer model name.
            .vectorType(VectorType.PG_VECTOR) //vector type in PostgreSQL.
            .kwargs(Map.of("device", "cpu")) // optional arguments.
            .metadataMode(MetadataMode.EMBED) // Document metadata mode.
            .build());

embeddingModel.afterPropertiesSet(); // initialize the jdbc template and
database.

EmbeddingResponse embeddingResponse = this.embeddingModel

	.embedForResponse(List.of("Hello World", "World is big and salvation is near"));
Copied!

```

|  When created manually, you must call the `afterPropertiesSet()` after setting
the properties and before using the client. It is more convenient (and
preferred) to create the PostgresMlEmbeddingModel as a `@Bean`. Then you don’t
have to call the `afterPropertiesSet()` manually:  
---|---  
```

@Bean

public EmbeddingModel embeddingModel(JdbcTemplate jdbcTemplate) {

    return new PostgresMlEmbeddingModel(jdbcTemplate,
        PostgresMlEmbeddingOptions.builder()
             ....
            .build());
}

Copied!

```

OpenAI QianFan

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

