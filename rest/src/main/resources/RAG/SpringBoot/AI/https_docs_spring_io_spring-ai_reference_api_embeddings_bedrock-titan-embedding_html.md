source URL: https://docs.spring.io/spring-ai/reference/api/embeddings/bedrock-titan-embedding.html 
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

### Titan Embeddings

  * Prerequisites
  * Add Repositories and BOM
  * Auto-configuration
  * Enable Titan Embedding Support
  * Embedding Properties
  * Runtime Options
  * Sample Controller
  * Manual Configuration
  * Low-level TitanEmbeddingBedrockApi Client

  * Spring AI
  * Reference
  * Models
  * Embedding Models
  * Amazon Bedrock
  * Titan

# Titan Embeddings

### Titan Embeddings

  * Prerequisites
  * Add Repositories and BOM
  * Auto-configuration
  * Enable Titan Embedding Support
  * Embedding Properties
  * Runtime Options
  * Sample Controller
  * Manual Configuration
  * Low-level TitanEmbeddingBedrockApi Client

Provides Bedrock Titan Embedding model. Amazon Titan foundation models (FMs)
provide customers with a breadth of high-performing image, multimodal
embeddings, and text model choices, via a fully managed API. Amazon Titan models
are created by AWS and pretrained on large datasets, making them powerful,
general-purpose models built to support a variety of use cases, while also
supporting the responsible use of AI. Use them as is or privately customize them
with your own data.

|  Bedrock Titan Embedding supports Text and Image embedding.  
---|---  
|  Bedrock Titan Embedding does NOT support batch embedding.  
---|---  
The AWS Bedrock Titan Model Page and Amazon Bedrock User Guide contains detailed
information on how to use the AWS hosted model.

##  Prerequisites

Refer to the Spring AI documentation on Amazon Bedrock for setting up API
access.

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
Add the `spring-ai-starter-model-bedrock` dependency to your project’s Maven
`pom.xml` file:

```

<dependency>

  <groupId>org.springframework.ai</groupId>

  <artifactId>spring-ai-starter-model-bedrock</artifactId>

</dependency>

Copied!

```

or to your Gradle `build.gradle` build file.

```

dependencies {

    implementation 'org.springframework.ai:spring-ai-starter-model-bedrock'
}

Copied!

```

|  Refer to the Dependency Management section to add the Spring AI BOM to your
build file.  
---|---  
###  Enable Titan Embedding Support

By default, the Titan embedding model is disabled. To enable it, set the
`spring.ai.model.embedding` property to `bedrock-titan` in your application
configuration:

```

spring.ai.model.embedding=bedrock-titan

Copied!

```

Alternatively, you can use Spring Expression Language (SpEL) to reference an
environment variable:

```

# In application.yml

spring:

  ai:

    model:
      embedding: ${AI_MODEL_EMBEDDING}
Copied!

```

```

# In your environment or .env file

export AI_MODEL_EMBEDDING=bedrock-titan

Copied!

```

You can also set this property using Java system properties when starting your
application:

```

java -Dspring.ai.model.embedding=bedrock-titan -jar your-application.jar

Copied!

```

###  Embedding Properties

The prefix `spring.ai.bedrock.aws` is the property prefix to configure the
connection to AWS Bedrock.

Property | Description | Default  
---|---|---  
spring.ai.bedrock.aws.region | AWS region to use. | us-east-1  
spring.ai.bedrock.aws.access-key | AWS access key. | -  
spring.ai.bedrock.aws.secret-key | AWS secret key. | -  
|  Enabling and disabling of the embedding auto-configurations are now
configured via top level properties with the prefix `spring.ai.model.embedding`.
To enable, spring.ai.model.embedding=bedrock-titan (It is enabled by default) To
disable, spring.ai.model.embedding=none (or any value which doesn’t match
bedrock-titan) This change is done to allow configuration of multiple models.  
---|---  
The prefix `spring.ai.bedrock.titan.embedding` (defined in
`BedrockTitanEmbeddingProperties`) is the property prefix that configures the
embedding model implementation for Titan.

Property | Description | Default  
---|---|---  
spring.ai.bedrock.titan.embedding.enabled (Removed and no longer valid) | Enable or disable support for Titan embedding | false  
spring.ai.model.embedding | Enable or disable support for Titan embedding | bedrock-titan  
spring.ai.bedrock.titan.embedding.model | The model id to use. See the `TitanEmbeddingModel` for the supported models. | amazon.titan-embed-image-v1  
Supported values are: `amazon.titan-embed-image-v1`, `amazon.titan-embed-
text-v1` and `amazon.titan-embed-text-v2:0`. Model ID values can also be found
in the AWS Bedrock documentation for base model IDs.

##  Runtime Options

The BedrockTitanEmbeddingOptions.java provides model configurations, such as
`input-type`. On start-up, the default options can be configured with the
`BedrockTitanEmbeddingModel(api).withInputType(type)` method or the
`spring.ai.bedrock.titan.embedding.input-type` properties.

At run-time you can override the default options by adding new, request
specific, options to the `EmbeddingRequest` call. For example to override the
default temperature for a specific request:

```

EmbeddingResponse embeddingResponse = embeddingModel.call(

    new EmbeddingRequest(List.of("Hello World", "World is big and salvation is near"),
        BedrockTitanEmbeddingOptions.builder()
        	.withInputType(InputType.TEXT)
        .build()));
Copied!

```

##  Sample Controller

Create a new Spring Boot project and add the `spring-ai-starter-model-bedrock`
to your pom (or gradle) dependencies.

Add a `application.properties` file, under the `src/main/resources` directory,
to enable and configure the Titan Embedding model:

```

spring.ai.bedrock.aws.region=eu-central-1

spring.ai.bedrock.aws.access-key=${AWS_ACCESS_KEY_ID}

spring.ai.bedrock.aws.secret-key=${AWS_SECRET_ACCESS_KEY}

spring.ai.model.embedding=bedrock-titan

Copied!

```

|  replace the `regions`, `access-key` and `secret-key` with your AWS
credentials.  
---|---  
This will create a `EmbeddingController` implementation that you can inject into
your class. Here is an example of a simple `@Controller` class that uses the
chat model for text generations.

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

The BedrockTitanEmbeddingModel implements the `EmbeddingModel` and uses the Low-
level TitanEmbeddingBedrockApi Client to connect to the Bedrock Titan service.

Add the `spring-ai-bedrock` dependency to your project’s Maven `pom.xml` file:

```

<dependency>

    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-bedrock</artifactId>
</dependency>

Copied!

```

or to your Gradle `build.gradle` build file.

```

dependencies {

    implementation 'org.springframework.ai:spring-ai-bedrock'
}

Copied!

```

|  Refer to the Dependency Management section to add the Spring AI BOM to your
build file.  
---|---  
Next, create an BedrockTitanEmbeddingModel and use it for text embeddings:

```

var titanEmbeddingApi = new TitanEmbeddingBedrockApi(

	TitanEmbeddingModel.TITAN_EMBED_IMAGE_V1.id(), Region.US_EAST_1.id());

var embeddingModel = new BedrockTitanEmbeddingModel(this.titanEmbeddingApi);

EmbeddingResponse embeddingResponse = this.embeddingModel

	.embedForResponse(List.of("Hello World")); // NOTE titan does not support batch embedding.
Copied!

```

##  Low-level TitanEmbeddingBedrockApi Client

The TitanEmbeddingBedrockApi provides is lightweight Java client on top of AWS
Bedrock Titan Embedding models.

Following class diagram illustrates the TitanEmbeddingBedrockApi interface and
building blocks:

The TitanEmbeddingBedrockApi supports the `amazon.titan-embed-image-v1` and
`amazon.titan-embed-image-v1` models for single and batch embedding computation.

Here is a simple snippet how to use the api programmatically:

```

TitanEmbeddingBedrockApi titanEmbedApi = new TitanEmbeddingBedrockApi(

		TitanEmbeddingModel.TITAN_EMBED_TEXT_V1.id(), Region.US_EAST_1.id());

TitanEmbeddingRequest request = TitanEmbeddingRequest.builder()

	.withInputText("I like to eat apples.")
	.build();

TitanEmbeddingResponse response = this.titanEmbedApi.embedding(this.request);

Copied!

```

To embed an image you need to convert it into `base64` format:

```

TitanEmbeddingBedrockApi titanEmbedApi = new TitanEmbeddingBedrockApi(

		TitanEmbeddingModel.TITAN_EMBED_IMAGE_V1.id(), Region.US_EAST_1.id());

byte[] image = new DefaultResourceLoader()

	.getResource("classpath:/spring_framework.png")
	.getContentAsByteArray();

TitanEmbeddingRequest request = TitanEmbeddingRequest.builder()

	.withInputImage(Base64.getEncoder().encodeToString(this.image))
	.build();

TitanEmbeddingResponse response = this.titanEmbedApi.embedding(this.request);

Copied!

```

Cohere Azure OpenAI

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

