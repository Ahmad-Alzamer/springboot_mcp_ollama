source URL: https://docs.spring.io/spring-ai/reference/api/embeddings/vertexai-embeddings-multimodal.html 
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

### Google VertexAI Multimodal Embeddings

  * Prerequisites
  * Add Repositories and BOM
  * Auto-configuration
  * Embedding Properties
  * Manual Configuration

  * Spring AI
  * Reference
  * Models
  * Embedding Models
  * VertexAI
  * Multimodal Embedding

# Google VertexAI Multimodal Embeddings

### Google VertexAI Multimodal Embeddings

  * Prerequisites
  * Add Repositories and BOM
  * Auto-configuration
  * Embedding Properties
  * Manual Configuration

|  EXPERIMENTAL. Used for experimental purposes only. Not compatible yet with
the `VectorStores`.  
---|---  
Vertex AI supports two types of embeddings models, text and multimodal. This
document describes how to create a multimodal embedding using the Vertex AI
Multimodal embeddings API.

The multimodal embeddings model generates 1408-dimension vectors based on the
input you provide, which can include a combination of image, text, and video
data. The embedding vectors can then be used for subsequent tasks like image
classification or video content moderation.

The image embedding vector and text embedding vector are in the same semantic
space with the same dimensionality. Consequently, these vectors can be used
interchangeably for use cases like searching image by text, or searching video
by image.

|  The VertexAI Multimodal API imposes the following limits.  
---|---  
|  For text-only embedding use cases, we recommend using the Vertex AI text-
embeddings model instead.  
---|---  
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
To enable, spring.ai.model.embedding.multimodal=vertexai (It is enabled by
default) To disable, spring.ai.model.embedding.multimodal=none (or any value
which doesn’t match vertexai) This change is done to allow configuration of
multiple models.  
---|---  
The prefix `spring.ai.vertex.ai.embedding.multimodal` is the property prefix
that lets you configure the embedding model implementation for VertexAI
Multimodal Embedding.

Property | Description | Default  
---|---|---  
spring.ai.vertex.ai.embedding.multimodal.enabled (Removed and no longer valid) | Enable Vertex AI Embedding API model. | true  
spring.ai.model.embedding.multimodal=vertexai | Enable Vertex AI Embedding API model. | vertexai  
spring.ai.vertex.ai.embedding.multimodal.options.model | You can get multimodal embeddings by using the following model: | multimodalembedding@001  
spring.ai.vertex.ai.embedding.multimodal.options.dimensions | Specify lower-dimension embeddings. By default, an embedding request returns a 1408 float vector for a data type. You can also specify lower-dimension embeddings (128, 256, or 512 float vectors) for text and image data. | 1408  
spring.ai.vertex.ai.embedding.multimodal.options.video-start-offset-sec | The start offset of the video segment in seconds. If not specified, it’s calculated with max(0, endOffsetSec - 120). | -  
spring.ai.vertex.ai.embedding.multimodal.options.video-end-offset-sec | The end offset of the video segment in seconds. If not specified, it’s calculated with min(video length, startOffSec + 120). If both startOffSec and endOffSec are specified, endOffsetSec is adjusted to min(startOffsetSec+120, endOffsetSec). | -  
spring.ai.vertex.ai.embedding.multimodal.options.video-interval-sec | The interval of the video the embedding will be generated. The minimum value for interval_sec is 4. If the interval is less than 4, an InvalidArgumentError is returned. There are no limitations on the maximum value of the interval. However, if the interval is larger than min(video length, 120s), it impacts the quality of the generated embeddings. Default value: 16. | -  
##  Manual Configuration

The VertexAiMultimodalEmbeddingModel implements the `DocumentEmbeddingModel`.

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
Next, create a `VertexAiMultimodalEmbeddingModel` and use it for embeddings
generations:

```

VertexAiEmbeddingConnectionDetails connectionDetails =

    VertexAiEmbeddingConnectionDetails.builder()
        .projectId(System.getenv(<VERTEX_AI_GEMINI_PROJECT_ID>))
        .location(System.getenv(<VERTEX_AI_GEMINI_LOCATION>))
        .build();

VertexAiMultimodalEmbeddingOptions options =
VertexAiMultimodalEmbeddingOptions.builder()

    .model(VertexAiMultimodalEmbeddingOptions.DEFAULT_MODEL_NAME)
    .build();

var embeddingModel = new
VertexAiMultimodalEmbeddingModel(this.connectionDetails, this.options);

Media imageMedial = new Media(MimeTypeUtils.IMAGE_PNG, new
ClassPathResource("/test.image.png"));

Media videoMedial = new Media(new MimeType("video", "mp4"), new
ClassPathResource("/test.video.mp4"));

var document = new Document("Explain what do you see on this video?",
List.of(this.imageMedial, this.videoMedial), Map.of());

EmbeddingResponse embeddingResponse = this.embeddingModel

	.embedForResponse(List.of("Hello World", "World is big and salvation is near"));

DocumentEmbeddingRequest embeddingRequest = new
DocumentEmbeddingRequest(List.of(this.document),

        EmbeddingOptions.EMPTY);

EmbeddingResponse embeddingResponse =
multiModelEmbeddingModel.call(this.embeddingRequest);

assertThat(embeddingResponse.getResults()).hasSize(3);

Copied!

```

Text Embedding ZhiPu AI

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

