source URL: https://docs.spring.io/spring-ai/reference/api/embeddings/vertexai-embeddings-text.html 
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

### Google VertexAI Text Embeddings

  * Prerequisites
  * Add Repositories and BOM
  * Auto-configuration
  * Embedding Properties
  * Sample Controller
  * Manual Configuration
  * Load credentials from a Google Service Account

  * Spring AI
  * Reference
  * Models
  * Embedding Models
  * VertexAI
  * Text Embedding

# Google VertexAI Text Embeddings

### Google VertexAI Text Embeddings

  * Prerequisites
  * Add Repositories and BOM
  * Auto-configuration
  * Embedding Properties
  * Sample Controller
  * Manual Configuration
  * Load credentials from a Google Service Account

Vertex AI supports two types of embeddings models, text and multimodal. This
document describes how to create a text embedding using the Vertex AI Text
embeddings API.

Vertex AI text embeddings API uses dense vector representations. Unlike sparse
vectors, which tend to directly map words to numbers, dense vectors are designed
to better represent the meaning of a piece of text. The benefit of using dense
vector embeddings in generative AI is that instead of searching for direct word
or syntax matches, you can better search for passages that align to the meaning
of the query, even if the passages don’t use the same language.

##  Prerequisites

  * Install the gcloud CLI, appropriate for you OS.
  * Authenticate by running the following command. Replace `PROJECT_ID` with your Google Cloud project ID and `ACCOUNT` with your Google Cloud username.

```

gcloud config set project <PROJECT_ID> &&

gcloud auth application-default login <ACCOUNT>

Copied!

```

###  Add Repositories and BOM

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
Spring AI provides Spring Boot auto-configuration for the VertexAI Embedding
Model. To enable it add the following dependency to your project’s Maven
`pom.xml` file:

```

<dependency>

    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-starter-model-vertex-ai-embedding</artifactId>
</dependency>

Copied!

```

or to your Gradle `build.gradle` build file.

```

dependencies {

    implementation 'org.springframework.ai:spring-ai-starter-model-vertex-ai-embedding'
}

Copied!

```

|  Refer to the Dependency Management section to add the Spring AI BOM to your
build file.  
---|---  
###  Embedding Properties

The prefix `spring.ai.vertex.ai.embedding` is used as the property prefix that
lets you connect to VertexAI Embedding API.

Property | Description | Default  
---|---|---  
spring.ai.vertex.ai.embedding.project-id | Google Cloud Platform project ID | -  
spring.ai.vertex.ai.embedding.location | Region | -  
spring.ai.vertex.ai.embedding.apiEndpoint | Vertex AI Embedding API endpoint. | -  
|  Enabling and disabling of the embedding auto-configurations are now
configured via top level properties with the prefix `spring.ai.model.embedding`.
To enable, spring.ai.model.embedding.text=vertexai (It is enabled by default) To
disable, spring.ai.model.embedding.text=none (or any value which doesn’t match
vertexai) This change is done to allow configuration of multiple models.  
---|---  
The prefix `spring.ai.vertex.ai.embedding.text` is the property prefix that lets
you configure the embedding model implementation for VertexAI Text Embedding.

Property | Description | Default  
---|---|---  
spring.ai.vertex.ai.embedding.text.enabled (Removed and no longer valid) | Enable Vertex AI Embedding API model. | true  
spring.ai.model.embedding.text | Enable Vertex AI Embedding API model. | vertexai  
spring.ai.vertex.ai.embedding.text.options.model | This is the Vertex Text Embedding model to use | text-embedding-004  
spring.ai.vertex.ai.embedding.text.options.task-type | The intended downstream application to help the model produce better quality embeddings. Available task-types | `RETRIEVAL_DOCUMENT`  
spring.ai.vertex.ai.embedding.text.options.title | Optional title, only valid with task_type=RETRIEVAL_DOCUMENT. | -  
spring.ai.vertex.ai.embedding.text.options.dimensions | The number of dimensions the resulting output embeddings should have. Supported for model version 004 and later. You can use this parameter to reduce the embedding size, for example, for storage optimization. | -  
spring.ai.vertex.ai.embedding.text.options.auto-truncate | When set to true, input text will be truncated. When set to false, an error is returned if the input text is longer than the maximum length supported by the model. | true  
##  Sample Controller

Create a new Spring Boot project and add the `spring-ai-starter-model-vertex-ai-
embedding` to your pom (or gradle) dependencies.

Add a `application.properties` file, under the `src/main/resources` directory,
to enable and configure the VertexAi chat model:

```

spring.ai.vertex.ai.embedding.project-id=<YOUR_PROJECT_ID>

spring.ai.vertex.ai.embedding.location=<YOUR_PROJECT_LOCATION>

spring.ai.vertex.ai.embedding.text.options.model=text-embedding-004

Copied!

```

This will create a `VertexAiTextEmbeddingModel` implementation that you can
inject into your class. Here is an example of a simple `@Controller` class that
uses the embedding model for embeddings generations.

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

##  Manual Configuration

The VertexAiTextEmbeddingModel implements the `EmbeddingModel`.

Add the `spring-ai-vertex-ai-embedding` dependency to your project’s Maven
`pom.xml` file:

```

<dependency>

    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-vertex-ai-embedding</artifactId>
</dependency>

Copied!

```

or to your Gradle `build.gradle` build file.

```

dependencies {

    implementation 'org.springframework.ai:spring-ai-vertex-ai-embedding'
}

Copied!

```

|  Refer to the Dependency Management section to add the Spring AI BOM to your
build file.  
---|---  
Next, create a `VertexAiTextEmbeddingModel` and use it for text generations:

```

VertexAiEmbeddingConnectionDetails connectionDetails =

    VertexAiEmbeddingConnectionDetails.builder()
        .projectId(System.getenv(<VERTEX_AI_GEMINI_PROJECT_ID>))
        .location(System.getenv(<VERTEX_AI_GEMINI_LOCATION>))
        .build();

VertexAiTextEmbeddingOptions options = VertexAiTextEmbeddingOptions.builder()

    .model(VertexAiTextEmbeddingOptions.DEFAULT_MODEL_NAME)
    .build();

var embeddingModel = new VertexAiTextEmbeddingModel(this.connectionDetails,
this.options);

EmbeddingResponse embeddingResponse = this.embeddingModel

	.embedForResponse(List.of("Hello World", "World is big and salvation is near"));
Copied!

```

###  Load credentials from a Google Service Account

To programmatically load the GoogleCredentials from a Service Account json file,
you can use the following:

```

GoogleCredentials credentials =
GoogleCredentials.fromStream(<INPUT_STREAM_TO_CREDENTIALS_JSON>)

        .createScoped("https://www.googleapis.com/auth/cloud-platform");
credentials.refreshIfExpired();

VertexAiEmbeddingConnectionDetails connectionDetails =

    VertexAiEmbeddingConnectionDetails.builder()
        .projectId(System.getenv(<VERTEX_AI_GEMINI_PROJECT_ID>))
        .location(System.getenv(<VERTEX_AI_GEMINI_LOCATION>))
        .apiEndpoint(endpoint)
        .predictionServiceSettings(
            PredictionServiceSettings.newBuilder()
                .setEndpoint(endpoint)
                .setCredentialsProvider(FixedCredentialsProvider.create(credentials))
                .build());
Copied!

```

QianFan Multimodal Embedding

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

