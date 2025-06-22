source URL: https://docs.spring.io/spring-ai/reference/api/embeddings/openai-embeddings.html 
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

### OpenAI Embeddings

  * Prerequisites
  * Add Repositories and BOM
  * Auto-configuration
  * Embedding Properties
  * Runtime Options
  * Sample Controller
  * Manual Configuration

  * Spring AI
  * Reference
  * Models
  * Embedding Models
  * OpenAI

# OpenAI Embeddings

### OpenAI Embeddings

  * Prerequisites
  * Add Repositories and BOM
  * Auto-configuration
  * Embedding Properties
  * Runtime Options
  * Sample Controller
  * Manual Configuration

Spring AI supports the OpenAI’s text embeddings models. OpenAI’s text embeddings
measure the relatedness of text strings. An embedding is a vector (list) of
floating point numbers. The distance between two vectors measures their
relatedness. Small distances suggest high relatedness and large distances
suggest low relatedness.

##  Prerequisites

You will need to create an API with OpenAI to access OpenAI embeddings models.

Create an account at OpenAI signup page and generate the token on the API Keys
page.

The Spring AI project defines a configuration property named
`spring.ai.openai.api-key` that you should set to the value of the `API Key`
obtained from openai.com.

You can set this configuration property in your `application.properties` file:

```

spring.ai.openai.api-key=<your-openai-api-key>

Copied!

```

For enhanced security when handling sensitive information like API keys, you can
use Spring Expression Language (SpEL) to reference an environment variable:

```

# In application.yml

spring:

  ai:

    openai:
      api-key: ${OPENAI_API_KEY}
Copied!

```

```

# In your environment or .env file

export OPENAI_API_KEY=<your-openai-api-key>

Copied!

```

You can also set this configuration programmatically in your application code:

```

// Retrieve API key from a secure source or environment variable

String apiKey = System.getenv("OPENAI_API_KEY");

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
Spring AI provides Spring Boot auto-configuration for the OpenAI Embedding
Model. To enable it add the following dependency to your project’s Maven
`pom.xml` file:

```

<dependency>

    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-starter-model-openai</artifactId>
</dependency>

Copied!

```

or to your Gradle `build.gradle` build file.

```

dependencies {

    implementation 'org.springframework.ai:spring-ai-starter-model-openai'
}

Copied!

```

|  Refer to the Dependency Management section to add the Spring AI BOM to your
build file.  
---|---  
###  Embedding Properties

####  Retry Properties

The prefix `spring.ai.retry` is used as the property prefix that lets you
configure the retry mechanism for the OpenAI Embedding model.

Property | Description | Default  
---|---|---  
spring.ai.retry.max-attempts | Maximum number of retry attempts. | 10  
spring.ai.retry.backoff.initial-interval | Initial sleep duration for the exponential backoff policy. | 2 sec.  
spring.ai.retry.backoff.multiplier | Backoff interval multiplier. | 5  
spring.ai.retry.backoff.max-interval | Maximum backoff duration. | 3 min.  
spring.ai.retry.on-client-errors | If false, throw a NonTransientAiException, and do not attempt retry for `4xx` client error codes | false  
spring.ai.retry.exclude-on-http-codes | List of HTTP status codes that should not trigger a retry (e.g. to throw NonTransientAiException). | empty  
spring.ai.retry.on-http-codes | List of HTTP status codes that should trigger a retry (e.g. to throw TransientAiException). | empty  
####  Connection Properties

The prefix `spring.ai.openai` is used as the property prefix that lets you
connect to OpenAI.

Property | Description | Default  
---|---|---  
spring.ai.openai.base-url | The URL to connect to | https://api.openai.com  
spring.ai.openai.api-key | The API Key | -  
spring.ai.openai.organization-id | Optionally you can specify which organization used for an API request. | -  
spring.ai.openai.project-id | Optionally, you can specify which project is used for an API request. | -  
|  For users that belong to multiple organizations (or are accessing their
projects through their legacy user API key), optionally, you can specify which
organization and project is used for an API request. Usage from these API
requests will count as usage for the specified organization and project.  
---|---  
####  Configuration Properties

|  Enabling and disabling of the embedding auto-configurations are now
configured via top level properties with the prefix `spring.ai.model.embedding`.
To enable, spring.ai.model.embedding=openai (It is enabled by default) To
disable, spring.ai.model.embedding=none (or any value which doesn’t match
openai) This change is done to allow configuration of multiple models.  
---|---  
The prefix `spring.ai.openai.embedding` is property prefix that configures the
`EmbeddingModel` implementation for OpenAI.

Property | Description | Default  
---|---|---  
spring.ai.openai.embedding.enabled (Required and no longer valid) | Enable OpenAI embedding model. | true  
spring.ai.model.embedding | Enable OpenAI embedding model. | openai  
spring.ai.openai.embedding.base-url | Optional overrides the spring.ai.openai.base-url to provide embedding specific url | -  
spring.ai.openai.embedding.embeddings-path | The path to append to the base-url | `/v1/embeddings`  
spring.ai.openai.embedding.api-key | Optional overrides the spring.ai.openai.api-key to provide embedding specific api-key | -  
spring.ai.openai.embedding.organization-id | Optionally you can specify which organization used for an API request. | -  
spring.ai.openai.embedding.project-id | Optionally, you can specify which project is used for an API request. | -  
spring.ai.openai.embedding.metadata-mode | Document content extraction mode. | EMBED  
spring.ai.openai.embedding.options.model | The model to use | text-embedding-ada-002 (other options: text-embedding-3-large, text-embedding-3-small)  
spring.ai.openai.embedding.options.encodingFormat | The format to return the embeddings in. Can be either float or base64. | -  
spring.ai.openai.embedding.options.user | A unique identifier representing your end-user, which can help OpenAI to monitor and detect abuse. | -  
spring.ai.openai.embedding.options.dimensions | The number of dimensions the resulting output embeddings should have. Only supported in `text-embedding-3` and later models. | -  
|  You can override the common `spring.ai.openai.base-url` and
`spring.ai.openai.api-key` for the `ChatModel` and `EmbeddingModel`
implementations. The `spring.ai.openai.embedding.base-url` and
`spring.ai.openai.embedding.api-key` properties if set take precedence over the
common properties. Similarly, the `spring.ai.openai.chat.base-url` and
`spring.ai.openai.chat.api-key` properties if set take precedence over the
common properties. This is useful if you want to use different OpenAI accounts
for different models and different model endpoints.  
---|---  
|  All properties prefixed with `spring.ai.openai.embedding.options` can be
overridden at runtime by adding a request specific Runtime Options to the
`EmbeddingRequest` call.  
---|---  
##  Runtime Options

The OpenAiEmbeddingOptions.java provides the OpenAI configurations, such as the
model to use and etc.

The default options can be configured using the
`spring.ai.openai.embedding.options` properties as well.

At start-time use the `OpenAiEmbeddingModel` constructor to set the default
options used for all embedding requests. At run-time you can override the
default options, using a `OpenAiEmbeddingOptions` instance as part of your
`EmbeddingRequest`.

For example to override the default model name for a specific request:

```

EmbeddingResponse embeddingResponse = embeddingModel.call(

    new EmbeddingRequest(List.of("Hello World", "World is big and salvation is near"),
        OpenAiEmbeddingOptions.builder()
            .model("Different-Embedding-Model-Deployment-Name")
        .build()));
Copied!

```

##  Sample Controller

This will create a `EmbeddingModel` implementation that you can inject into your
class. Here is an example of a simple `@Controller` class that uses the
`EmbeddingModel` implementation.

```

spring.ai.openai.api-key=YOUR_API_KEY

spring.ai.openai.embedding.options.model=text-embedding-ada-002

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

##  Manual Configuration

If you are not using Spring Boot, you can manually configure the OpenAI
Embedding Model. For this add the `spring-ai-openai` dependency to your
project’s Maven `pom.xml` file:

```

<dependency>

    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-openai</artifactId>
</dependency>

Copied!

```

or to your Gradle `build.gradle` build file.

```

dependencies {

    implementation 'org.springframework.ai:spring-ai-openai'
}

Copied!

```

|  Refer to the Dependency Management section to add the Spring AI BOM to your
build file.  
---|---  
|  The `spring-ai-openai` dependency provides access also to the
`OpenAiChatModel`. For more information about the `OpenAiChatModel` refer to the
OpenAI Chat Client section.  
---|---  
Next, create an `OpenAiEmbeddingModel` instance and use it to compute the
similarity between two input texts:

```

var openAiApi = OpenAiApi.builder()

                .apiKey(System.getenv("OPENAI_API_KEY"))
                .build();

var embeddingModel = new OpenAiEmbeddingModel(

		this.openAiApi,
        MetadataMode.EMBED,
        OpenAiEmbeddingOptions.builder()
                .model("text-embedding-ada-002")
                .user("user-6")
                .build(),
        RetryUtils.DEFAULT_RETRY_TEMPLATE);

EmbeddingResponse embeddingResponse = this.embeddingModel

        .embedForResponse(List.of("Hello World", "World is big and salvation is near"));
Copied!

```

The `OpenAiEmbeddingOptions` provides the configuration information for the
embedding requests. The api and options class offers a `builder()` for easy
options creation.

(ONNX) Transformers PostgresML

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

