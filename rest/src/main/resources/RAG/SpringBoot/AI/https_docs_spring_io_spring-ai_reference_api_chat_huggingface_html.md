source URL: https://docs.spring.io/spring-ai/reference/api/chat/huggingface.html 
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

### Hugging Face Chat

  * Prerequisites
  * Add Repositories and BOM
  * Auto-configuration
  * Chat Properties
  * Sample Controller (Auto-configuration)
  * Manual Configuration

  * Spring AI
  * Reference
  * Models
  * Chat Models
  * Hugging Face

# Hugging Face Chat

### Hugging Face Chat

  * Prerequisites
  * Add Repositories and BOM
  * Auto-configuration
  * Chat Properties
  * Sample Controller (Auto-configuration)
  * Manual Configuration

Hugging Face Text Generation Inference (TGI) is a specialized deployment
solution for serving Large Language Models (LLMs) in the cloud, making them
accessible via an API. TGI provides optimized performance for text generation
tasks through features like continuous batching, token streaming, and efficient
memory management.

|  Text Generation Inference requires models to be compatible with its
architecture-specific optimizations. While many popular LLMs are supported, not
all models on Hugging Face Hub can be deployed using TGI. If you need to deploy
other types of models, consider using standard Hugging Face Inference Endpoints
instead.  
---|---  
|  For a complete and up-to-date list of supported models and architectures, see
the Text Generation Inference supported models documentation.  
---|---  
##  Prerequisites

You will need to create an Inference Endpoint on Hugging Face and create an API
token to access the endpoint. Further details can be found here.

The Spring AI project defines two configuration properties:

  1. `spring.ai.huggingface.chat.api-key`: Set this to the value of the API token obtained from Hugging Face.
  2. `spring.ai.huggingface.chat.url`: Set this to the inference endpoint URL obtained when provisioning your model in Hugging Face.

You can find your inference endpoint URL on the Inference Endpoint’s UI here.

You can set these configuration properties in your `application.properties`
file:

```

spring.ai.huggingface.chat.api-key=<your-huggingface-api-key>

spring.ai.huggingface.chat.url=<your-inference-endpoint-url>

Copied!

```

For enhanced security when handling sensitive information like API keys, you can
use Spring Expression Language (SpEL) to reference custom environment variables:

```

# In application.yml

spring:

  ai:

    huggingface:
      chat:
        api-key: ${HUGGINGFACE_API_KEY}
        url: ${HUGGINGFACE_ENDPOINT_URL}
Copied!

```

```

# In your environment or .env file

export HUGGINGFACE_API_KEY=<your-huggingface-api-key>

export HUGGINGFACE_ENDPOINT_URL=<your-inference-endpoint-url>

Copied!

```

You can also set these configurations programmatically in your application code:

```

// Retrieve API key and endpoint URL from secure sources or environment
variables

String apiKey = System.getenv("HUGGINGFACE_API_KEY");

String endpointUrl = System.getenv("HUGGINGFACE_ENDPOINT_URL");

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
Spring AI provides Spring Boot auto-configuration for the Hugging Face Chat
Client. To enable it add the following dependency to your project’s Maven
`pom.xml` file:

```

<dependency>

    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-starter-model-huggingface</artifactId>
</dependency>

Copied!

```

or to your Gradle `build.gradle` build file.

```

dependencies {

    implementation 'org.springframework.ai:spring-ai-starter-model-huggingface'
}

Copied!

```

|  Refer to the Dependency Management section to add the Spring AI BOM to your
build file.  
---|---  
###  Chat Properties

|  Enabling and disabling of the chat auto-configurations are now configured via
top level properties with the prefix `spring.ai.model.chat`. To enable,
spring.ai.model.chat=huggingface (It is enabled by default) To disable,
spring.ai.model.chat=none (or any value which doesn’t match huggingface) This
change is done to allow configuration of multiple models.  
---|---  
The prefix `spring.ai.huggingface` is the property prefix that lets you
configure the chat model implementation for Hugging Face.

Property | Description | Default  
---|---|---  
spring.ai.huggingface.chat.api-key | API Key to authenticate with the Inference Endpoint. | -  
spring.ai.huggingface.chat.url | URL of the Inference Endpoint to connect to | -  
spring.ai.huggingface.chat.enabled (Removed and no longer valid) | Enable Hugging Face chat model. | true  
spring.ai.model.chat (Removed and no longer valid) | Enable Hugging Face chat model. | huggingface  
##  Sample Controller (Auto-configuration)

Create a new Spring Boot project and add the `spring-ai-starter-model-
huggingface` to your pom (or gradle) dependencies.

Add an `application.properties` file, under the `src/main/resources` directory,
to enable and configure the Hugging Face chat model:

```

spring.ai.huggingface.chat.api-key=YOUR_API_KEY

spring.ai.huggingface.chat.url=YOUR_INFERENCE_ENDPOINT_URL

Copied!

```

|  replace the `api-key` and `url` with your Hugging Face values.  
---|---  
This will create a `HuggingfaceChatModel` implementation that you can inject
into your class. Here is an example of a simple `@Controller` class that uses
the chat model for text generations.

```

@RestController

public class ChatController {

    private final HuggingfaceChatModel chatModel;

    @Autowired
    public ChatController(HuggingfaceChatModel chatModel) {
        this.chatModel = chatModel;
    }

    @GetMapping("/ai/generate")
    public Map generate(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
        return Map.of("generation", this.chatModel.call(message));
    }
}

Copied!

```

##  Manual Configuration

The HuggingfaceChatModel implements the `ChatModel` interface and uses the [low-
level-api] to connect to the Hugging Face inference endpoints.

Add the `spring-ai-huggingface` dependency to your project’s Maven `pom.xml`
file:

```

<dependency>

    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-huggingface</artifactId>
</dependency>

Copied!

```

or to your Gradle `build.gradle` build file.

```

dependencies {

    implementation 'org.springframework.ai:spring-ai-huggingface'
}

Copied!

```

|  Refer to the Dependency Management section to add the Spring AI BOM to your
build file.  
---|---  
Next, create a `HuggingfaceChatModel` and use it for text generations:

```

HuggingfaceChatModel chatModel = new HuggingfaceChatModel(apiKey, url);

ChatResponse response = this.chatModel.call(

    new Prompt("Generate the names of 5 famous pirates."));

System.out.println(response.getGeneration().getResult().getOutput().getContent());

Copied!

```

Groq Mistral AI

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

