source URL: https://docs.spring.io/spring-ai/reference/api/embeddings/azure-openai-embeddings.html 
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

### Azure OpenAI Embeddings

  * Prerequisites
  * Azure API Key & Endpoint
  * OpenAI Key
  * Microsoft Entra ID
  * Add Repositories and BOM
  * Auto-configuration
  * Embedding Properties
  * Runtime Options
  * Sample Code
  * Manual Configuration

  * Spring AI
  * Reference
  * Models
  * Embedding Models
  * Azure OpenAI

# Azure OpenAI Embeddings

### Azure OpenAI Embeddings

  * Prerequisites
  * Azure API Key & Endpoint
  * OpenAI Key
  * Microsoft Entra ID
  * Add Repositories and BOM
  * Auto-configuration
  * Embedding Properties
  * Runtime Options
  * Sample Code
  * Manual Configuration

Azure’s OpenAI extends the OpenAI capabilities, offering safe text generation
and Embeddings computation models for various task:

  * Similarity embeddings are good at capturing semantic similarity between two or more pieces of text.
  * Text search embeddings help measure whether long documents are relevant to a short query.
  * Code search embeddings are useful for embedding code snippets and embedding natural language search queries.

The Azure OpenAI embeddings rely on `cosine similarity` to compute similarity
between documents and a query.

##  Prerequisites

The Azure OpenAI client offers three options to connect: using an Azure API key
or using an OpenAI API Key, or using Microsoft Entra ID.

###  Azure API Key & Endpoint

Obtain your Azure OpenAI `endpoint` and `api-key` from the Azure OpenAI Service
section on the Azure Portal.

Spring AI defines two configuration properties:

  1. `spring.ai.azure.openai.api-key`: Set this to the value of the `API Key` obtained from Azure.
  2. `spring.ai.azure.openai.endpoint`: Set this to the endpoint URL obtained when provisioning your model in Azure.

You can set these configuration properties in your `application.properties` or
`application.yml` file:

```

spring.ai.azure.openai.api-key=<your-azure-api-key>

spring.ai.azure.openai.endpoint=<your-azure-endpoint-url>

Copied!

```

If you prefer to use environment variables for sensitive information like API
keys, you can use Spring Expression Language (SpEL) in your configuration:

```

# In application.yml

spring:

  ai:

    azure:
      openai:
        api-key: ${AZURE_OPENAI_API_KEY}
        endpoint: ${AZURE_OPENAI_ENDPOINT}
Copied!

```

```

# In your environment or .env file

export AZURE_OPENAI_API_KEY=<your-azure-openai-api-key>

export AZURE_OPENAI_ENDPOINT=<your-azure-endpoint-url>

Copied!

```

###  OpenAI Key

To authenticate with the OpenAI service (not Azure), provide an OpenAI API key.
This will automatically set the endpoint to api.openai.com/v1.

When using this approach, set the
`spring.ai.azure.openai.chat.options.deployment-name` property to the name of
the OpenAI model you wish to use.

In your application configuration:

```

spring.ai.azure.openai.openai-api-key=<your-azure-openai-key>

spring.ai.azure.openai.chat.options.deployment-name=<openai-model-name>

Copied!

```

Using environment variables with SpEL:

```

# In application.yml

spring:

  ai:

    azure:
      openai:
        openai-api-key: ${AZURE_OPENAI_API_KEY}
        chat:
          options:
            deployment-name: ${OPENAI_MODEL_NAME}
Copied!

```

```

# In your environment or .env file

export AZURE_OPENAI_API_KEY=<your-openai-key>

export OPENAI_MODEL_NAME=<openai-model-name>

Copied!

```

###  Microsoft Entra ID

For keyless authentication using Microsoft Entra ID (formerly Azure Active
Directory), set _only_ the `spring.ai.azure.openai.endpoint` configuration
property and _not_ the api-key property mentioned above.

Finding only the endpoint property, your application will evaluate several
different options for retrieving credentials and an `OpenAIClient` instance will
be created using the token credentials.

|  It is no longer necessary to create a `TokenCredential` bean; it is
configured for you automatically.  
---|---  
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
Spring AI provides Spring Boot auto-configuration for the Azure OpenAI Embedding
Model. To enable it add the following dependency to your project’s Maven
`pom.xml` file:

```

<dependency>

    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-starter-model-azure-openai</artifactId>
</dependency>

Copied!

```

or to your Gradle `build.gradle` build file.

```

dependencies {

    implementation 'org.springframework.ai:spring-ai-starter-model-azure-openai'
}

Copied!

```

|  Refer to the Dependency Management section to add the Spring AI BOM to your
build file.  
---|---  
###  Embedding Properties

The prefix `spring.ai.azure.openai` is the property prefix to configure the
connection to Azure OpenAI.

Property | Description | Default  
---|---|---  
spring.ai.azure.openai.api-key | The Key from Azure AI OpenAI `Keys and Endpoint` section under `Resource Management` | -  
spring.ai.azure.openai.endpoint | The endpoint from the Azure AI OpenAI `Keys and Endpoint` section under `Resource Management` | -  
spring.ai.azure.openai.openai-api-key | (non Azure) OpenAI API key. Used to authenticate with the OpenAI service, instead of Azure OpenAI. This automatically sets the endpoint to api.openai.com/v1. Use either `api-key` or `openai-api-key` property. With this configuration the `spring.ai.azure.openai.embedding.options.deployment-name` is threated as an OpenAi Model name. | -  
|  Enabling and disabling of the embedding auto-configurations are now
configured via top level properties with the prefix `spring.ai.model.embedding`.
To enable, spring.ai.model.embedding=azure-openai (It is enabled by default) To
disable, spring.ai.model.embedding=none (or any value which doesn’t match azure-
openai) This change is done to allow configuration of multiple models.  
---|---  
The prefix `spring.ai.azure.openai.embedding` is the property prefix that
configures the `EmbeddingModel` implementation for Azure OpenAI

Property | Description | Default  
---|---|---  
spring.ai.azure.openai.embedding.enabled (Removed and no longer valid) | Enable Azure OpenAI embedding model. | true  
spring.ai.model.embedding | Enable Azure OpenAI embedding model. | azure-openai  
spring.ai.azure.openai.embedding.metadata-mode | Document content extraction mode | EMBED  
spring.ai.azure.openai.embedding.options.deployment-name | This is the value of the 'Deployment Name' as presented in the Azure AI Portal | text-embedding-ada-002  
spring.ai.azure.openai.embedding.options.user | An identifier for the caller or end user of the operation. This may be used for tracking or rate-limiting purposes. | -  
|  All properties prefixed with `spring.ai.azure.openai.embedding.options` can
be overridden at runtime by adding a request specific Runtime Options to the
`EmbeddingRequest` call.  
---|---  
##  Runtime Options

The `AzureOpenAiEmbeddingOptions` provides the configuration information for the
embedding requests. The `AzureOpenAiEmbeddingOptions` offers a builder to create
the options.

At start time use the `AzureOpenAiEmbeddingModel` constructor to set the default
options used for all embedding requests. At run-time you can override the
default options, by passing a `AzureOpenAiEmbeddingOptions` instance with your
to the `EmbeddingRequest` request.

For example to override the default model name for a specific request:

```

EmbeddingResponse embeddingResponse = embeddingModel.call(

    new EmbeddingRequest(List.of("Hello World", "World is big and salvation is near"),
        AzureOpenAiEmbeddingOptions.builder()
        .model("Different-Embedding-Model-Deployment-Name")
        .build()));
Copied!

```

##  Sample Code

This will create a `EmbeddingModel` implementation that you can inject into your
class. Here is an example of a simple `@Controller` class that uses the
`EmbeddingModel` implementation.

```

spring.ai.azure.openai.api-key=YOUR_API_KEY

spring.ai.azure.openai.endpoint=YOUR_ENDPOINT

spring.ai.azure.openai.embedding.options.model=text-embedding-ada-002

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

If you prefer not to use the Spring Boot auto-configuration, you can manually
configure the `AzureOpenAiEmbeddingModel` in your application. For this add the
`spring-ai-azure-openai` dependency to your project’s Maven `pom.xml` file:

```

<dependency>

    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-azure-openai</artifactId>
</dependency>

Copied!

```

or to your Gradle `build.gradle` build file.

```

dependencies {

    implementation 'org.springframework.ai:spring-ai-azure-openai'
}

Copied!

```

|  Refer to the Dependency Management section to add the Spring AI BOM to your
build file.  
---|---  
|  The `spring-ai-azure-openai` dependency also provide the access to the
`AzureOpenAiEmbeddingModel`. For more information about the
`AzureOpenAiChatModel` refer to the Azure OpenAI Embeddings section.  
---|---  
Next, create an `AzureOpenAiEmbeddingModel` instance and use it to compute the
similarity between two input texts:

```

var openAIClient = OpenAIClientBuilder()

        .credential(new AzureKeyCredential(System.getenv("AZURE_OPENAI_API_KEY")))
		.endpoint(System.getenv("AZURE_OPENAI_ENDPOINT"))
		.buildClient();

var embeddingModel = new AzureOpenAiEmbeddingModel(this.openAIClient)

    .withDefaultOptions(AzureOpenAiEmbeddingOptions.builder()
        .model("text-embedding-ada-002")
        .user("user-6")
        .build());

EmbeddingResponse embeddingResponse = this.embeddingModel

	.embedForResponse(List.of("Hello World", "World is big and salvation is near"));
Copied!

```

|  the `text-embedding-ada-002` is actually the `Deployment Name` as presented
in the Azure AI Portal.  
---|---  
Titan Mistral AI

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

