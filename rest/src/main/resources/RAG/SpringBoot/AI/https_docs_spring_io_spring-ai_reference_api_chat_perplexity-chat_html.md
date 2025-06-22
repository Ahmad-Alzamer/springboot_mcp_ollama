source URL: https://docs.spring.io/spring-ai/reference/api/chat/perplexity-chat.html 
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

### Perplexity Chat

  * Prerequisites
  * Add Repositories and BOM
  * Auto-configuration
  * Chat Properties
  * Runtime Options
  * Function Calling
  * Multimodal
  * Sample Controller
  * Supported Models
  * References

  * Spring AI
  * Reference
  * Models
  * Chat Models
  * Perplexity AI

# Perplexity Chat

### Perplexity Chat

  * Prerequisites
  * Add Repositories and BOM
  * Auto-configuration
  * Chat Properties
  * Runtime Options
  * Function Calling
  * Multimodal
  * Sample Controller
  * Supported Models
  * References

Perplexity AI provides a unique AI service that integrates its language models
with real-time search capabilities. It offers a variety of models and supports
streaming responses for conversational AI.

Spring AI integrates with Perplexity AI by reusing the existing OpenAI client.
To get started, you’ll need to obtain a Perplexity API Key, configure the base
URL, and select one of the supported models.

|  The Perplexity API is not fully compatible with the OpenAI API. Perplexity
combines realtime web search results with its language model responses. Unlike
OpenAI, Perplexity does not expose `toolCalls` - `function call` mechanisms.
Additionally, currently Perplexity doesn’t support multimodal messages.  
---|---  
Check the PerplexityWithOpenAiChatModelIT.java tests for examples of using
Perplexity with Spring AI.

##  Prerequisites

  * **Create an API Key** : Visit here to create an API Key. Configure it using the `spring.ai.openai.api-key` property in your Spring AI project.
  * **Set the Perplexity Base URL** : Set the `spring.ai.openai.base-url` property to `api.perplexity.ai`.
  * **Select a Perplexity Model** : Use the `spring.ai.openai.chat.model=<model name>` property to specify the model. Refer to Supported Models for available options.
  * **Set the chat completions path** : Set the `spring.ai.openai.chat.completions-path` to `/chat/completions`. Refer to chat completions api for more details.

You can set these configuration properties in your `application.properties`
file:

```

spring.ai.openai.api-key=<your-perplexity-api-key>

spring.ai.openai.base-url=https://api.perplexity.ai

spring.ai.openai.chat.model=llama-3.1-sonar-small-128k-online

spring.ai.openai.chat.completions-path=/chat/completions

Copied!

```

For enhanced security when handling sensitive information like API keys, you can
use Spring Expression Language (SpEL) to reference custom environment variables:

```

# In application.yml

spring:

  ai:

    openai:
      api-key: ${PERPLEXITY_API_KEY}
      base-url: ${PERPLEXITY_BASE_URL}
      chat:
        model: ${PERPLEXITY_MODEL}
        completions-path: ${PERPLEXITY_COMPLETIONS_PATH}
Copied!

```

```

# In your environment or .env file

export PERPLEXITY_API_KEY=<your-perplexity-api-key>

export PERPLEXITY_BASE_URL=https://api.perplexity.ai

export PERPLEXITY_MODEL=llama-3.1-sonar-small-128k-online

export PERPLEXITY_COMPLETIONS_PATH=/chat/completions

Copied!

```

You can also set these configurations programmatically in your application code:

```

// Retrieve configuration from secure sources or environment variables

String apiKey = System.getenv("PERPLEXITY_API_KEY");

String baseUrl = System.getenv("PERPLEXITY_BASE_URL");

String model = System.getenv("PERPLEXITY_MODEL");

String completionsPath = System.getenv("PERPLEXITY_COMPLETIONS_PATH");

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
Spring AI provides Spring Boot auto-configuration for the OpenAI Chat Client. To
enable it add the following dependency to your project’s Maven `pom.xml` or
Gradle `build.gradle` build files:

  * Maven
  * Gradle

```

<dependency>

    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-starter-model-openai</artifactId>
</dependency>

Copied!

```

```

dependencies {

    implementation 'org.springframework.ai:spring-ai-starter-model-openai'
}

Copied!

```

|  Refer to the Dependency Management section to add the Spring AI BOM to your
build file.  
---|---  
###  Chat Properties

####  Retry Properties

The prefix `spring.ai.retry` is used as the property prefix that lets you
configure the retry mechanism for the OpenAI chat model.

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
spring.ai.openai.base-url | The URL to connect to. Must be set to `api.perplexity.ai` | -  
spring.ai.openai.chat.api-key | Your Perplexity API Key | -  
####  Configuration Properties

|  Enabling and disabling of the chat auto-configurations are now configured via
top level properties with the prefix `spring.ai.model.chat`. To enable,
spring.ai.model.chat=openai (It is enabled by default) To disable,
spring.ai.model.chat=none (or any value which doesn’t match openai) This change
is done to allow configuration of multiple models.  
---|---  
The prefix `spring.ai.openai.chat` is the property prefix that lets you
configure the chat model implementation for OpenAI.

Property | Description | Default  
---|---|---  
spring.ai.model.chat | Enable OpenAI chat model. | openai  
spring.ai.openai.chat.model | One of the supported Perplexity models. Example: `llama-3.1-sonar-small-128k-online`. | -  
spring.ai.openai.chat.base-url | Optional overrides the spring.ai.openai.base-url to provide chat specific url. Must be set to `api.perplexity.ai` | -  
spring.ai.openai.chat.completions-path | Must be set to `/chat/completions` | `/v1/chat/completions`  
spring.ai.openai.chat.options.temperature | The amount of randomness in the response, valued between 0 inclusive and 2 exclusive. Higher values are more random, and lower values are more deterministic. Required range: `0 < x < 2`. | 0.2  
spring.ai.openai.chat.options.frequencyPenalty | A multiplicative penalty greater than 0. Values greater than 1.0 penalize new tokens based on their existing frequency in the text so far, decreasing the model’s likelihood to repeat the same line verbatim. A value of 1.0 means no penalty. Incompatible with presence_penalty. Required range: `x > 0`. | 1  
spring.ai.openai.chat.options.maxTokens | The maximum number of completion tokens returned by the API. The total number of tokens requested in max_tokens plus the number of prompt tokens sent in messages must not exceed the context window token limit of model requested. If left unspecified, then the model will generate tokens until either it reaches its stop token or the end of its context window. | -  
spring.ai.openai.chat.options.presencePenalty | A value between -2.0 and 2.0. Positive values penalize new tokens based on whether they appear in the text so far, increasing the model’s likelihood to talk about new topics. Incompatible with `frequency_penalty`. Required range: `-2 < x < 2` | 0  
spring.ai.openai.chat.options.topP | The nucleus sampling threshold, valued between 0 and 1 inclusive. For each subsequent token, the model considers the results of the tokens with top_p probability mass. We recommend either altering top_k or top_p, but not both. Required range: `0 < x < 1` | 0.9  
spring.ai.openai.chat.options.stream-usage | (For streaming only) Set to add an additional chunk with token usage statistics for the entire request. The `choices` field for this chunk is an empty array and all other chunks will also include a usage field, but with a null value. | false  
|  All properties prefixed with `spring.ai.openai.chat.options` can be
overridden at runtime by adding a request specific Runtime Options to the
`Prompt` call.  
---|---  
##  Runtime Options

The OpenAiChatOptions.java provides model configurations, such as the model to
use, the temperature, the frequency penalty, etc.

On start-up, the default options can be configured with the
`OpenAiChatModel(api, options)` constructor or the
`spring.ai.openai.chat.options.*` properties.

At run-time you can override the default options by adding new, request
specific, options to the `Prompt` call. For example to override the default
model and temperature for a specific request:

```

ChatResponse response = chatModel.call(

    new Prompt(
        "Generate the names of 5 famous pirates.",
        OpenAiChatOptions.builder()
            .model("llama-3.1-sonar-large-128k-online")
            .temperature(0.4)
        .build()
    ));
Copied!

```

|  In addition to the model specific OpenAiChatOptions you can use a portable
ChatOptions instance, created with the ChatOptions#builder().  
---|---  
##  Function Calling

|  Perplexity does not support explicit function calling. Instead, it integrates
search results directly into responses.  
---|---  
##  Multimodal

|  Currently, the Perplexity API doesn’t support media content.  
---|---  
##  Sample Controller

Create a new Spring Boot project and add the `spring-ai-starter-model-openai` to
your pom (or gradle) dependencies.

Add a `application.properties` file, under the `src/main/resources` directory,
to enable and configure the OpenAi chat model:

```

spring.ai.openai.api-key=<PERPLEXITY_API_KEY>

spring.ai.openai.base-url=https://api.perplexity.ai

spring.ai.openai.chat.completions-path=/chat/completions

spring.ai.openai.chat.options.model=llama-3.1-sonar-small-128k-online

spring.ai.openai.chat.options.temperature=0.7

# The Perplexity API doesn't support embeddings, so we need to disable it.

spring.ai.openai.embedding.enabled=false

Copied!

```

|  replace the `api-key` with your Perplexity Api key.  
---|---  
This will create a `OpenAiChatModel` implementation that you can inject into
your class. Here is an example of a simple `@Controller` class that uses the
chat model for text generations.

```

@RestController

public class ChatController {

    private final OpenAiChatModel chatModel;

    @Autowired
    public ChatController(OpenAiChatModel chatModel) {
        this.chatModel = chatModel;
    }

    @GetMapping("/ai/generate")
    public Map generate(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
        return Map.of("generation", this.chatModel.call(message));
    }

    @GetMapping("/ai/generateStream")
	public Flux<ChatResponse> generateStream(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
        Prompt prompt = new Prompt(new UserMessage(message));
        return this.chatModel.stream(prompt);
    }
}

Copied!

```

##  Supported Models

Perplexity supports several models optimized for search-enhanced conversational
AI. Refer to Supported Models for details.

##  References

  * Documentation Home
  * API Reference
  * Getting Started
  * Rate Limits

Ollama Cohere

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

