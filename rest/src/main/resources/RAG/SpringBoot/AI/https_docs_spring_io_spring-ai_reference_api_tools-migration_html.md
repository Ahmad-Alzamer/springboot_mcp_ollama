source URL: https://docs.spring.io/spring-ai/reference/api/tools-migration.html 
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

### Migrating from FunctionCallback to ToolCallback API

  * Overview of Changes
  * Key Changes
  * Migration Examples
  * 1. Basic Function Callback
  * 2. ChatClient Usage
  * 3. Method-Based Function Callbacks
  * 4. Options Configuration
  * 5. Default Functions in ChatClient Builder
  * 6. Spring Bean Configuration
  * Breaking Changes
  * Deprecated Methods
  * Declarative Specification with @Tool
  * Additional Notes
  * Timeline

  * Spring AI
  * Upgrade Notes
  * Migrating FunctionCallback to ToolCallback API

# Migrating from FunctionCallback to ToolCallback API

### Migrating from FunctionCallback to ToolCallback API

  * Overview of Changes
  * Key Changes
  * Migration Examples
  * 1. Basic Function Callback
  * 2. ChatClient Usage
  * 3. Method-Based Function Callbacks
  * 4. Options Configuration
  * 5. Default Functions in ChatClient Builder
  * 6. Spring Bean Configuration
  * Breaking Changes
  * Deprecated Methods
  * Declarative Specification with @Tool
  * Additional Notes
  * Timeline

This guide helps you migrate from the deprecated `FunctionCallback` API to the
new `ToolCallback` API in Spring AI. For more information about the new APIs,
check out the Tools Calling documentation.

##  Overview of Changes

These changes are part of a broader effort to improve and extend the tool
calling capabilities in Spring AI. Among the other things, the new API moves
from "functions" to "tools" terminology to better align with industry
conventions. This involves several API changes while maintaining backward
compatibility through deprecated methods.

##  Key Changes

  1. `FunctionCallback` → `ToolCallback`
  2. `FunctionCallback.builder().function()` → `FunctionToolCallback.builder()`
  3. `FunctionCallback.builder().method()` → `MethodToolCallback.builder()`
  4. `FunctionCallingOptions` → `ToolCallingChatOptions`
  5. `ChatClient.builder().defaultFunctions()` → `ChatClient.builder().defaultTools()`
  6. `ChatClient.functions()` → `ChatClient.tools()`
  7. `FunctionCallingOptions.builder().functions()` → `ToolCallingChatOptions.builder().toolNames()`
  8. `FunctionCallingOptions.builder().functionCallbacks()` → `ToolCallingChatOptions.builder().toolCallbacks()`

##  Migration Examples

###  1. Basic Function Callback

Before:

```

FunctionCallback.builder()

    .function("getCurrentWeather", new MockWeatherService())
    .description("Get the weather in location")
    .inputType(MockWeatherService.Request.class)
    .build()
Copied!

```

After:

```

FunctionToolCallback.builder("getCurrentWeather", new MockWeatherService())

    .description("Get the weather in location")
    .inputType(MockWeatherService.Request.class)
    .build()
Copied!

```

###  2. ChatClient Usage

Before:

```

String response = ChatClient.create(chatModel)

    .prompt()
    .user("What's the weather like in San Francisco?")
    .functions(FunctionCallback.builder()
        .function("getCurrentWeather", new MockWeatherService())
        .description("Get the weather in location")
        .inputType(MockWeatherService.Request.class)
        .build())
    .call()
    .content();
Copied!

```

After:

```

String response = ChatClient.create(chatModel)

    .prompt()
    .user("What's the weather like in San Francisco?")
    .tools(FunctionToolCallback.builder("getCurrentWeather", new MockWeatherService())
        .description("Get the weather in location")
        .inputType(MockWeatherService.Request.class)
        .build())
    .call()
    .content();
Copied!

```

###  3. Method-Based Function Callbacks

Before:

```

FunctionCallback.builder()

    .method("getWeatherInLocation", String.class, Unit.class)
    .description("Get the weather in location")
    .targetClass(TestFunctionClass.class)
    .build()
Copied!

```

After:

```

var toolMethod = ReflectionUtils.findMethod(TestFunctionClass.class,
"getWeatherInLocation");

MethodToolCallback.builder()

    .toolDefinition(ToolDefinition.builder(toolMethod)
        .description("Get the weather in location")
        .build())
    .toolMethod(toolMethod)
    .build()
Copied!

```

Or with the declarative approach:

```

class WeatherTools {

    @Tool(description = "Get the weather in location")
    public void getWeatherInLocation(String location, Unit unit) {
        // ...
    }

}

Copied!

```

And you can use the same `ChatClient#tools()` API to register method-based tool
callbackes:

```

String response = ChatClient.create(chatModel)

    .prompt()
    .user("What's the weather like in San Francisco?")
    .tools(MethodToolCallback.builder()
        .toolDefinition(ToolDefinition.builder(toolMethod)
            .description("Get the weather in location")
            .build())
        .toolMethod(toolMethod)
        .build())
    .call()
    .content();
Copied!

```

Or with the declarative approach:

```

String response = ChatClient.create(chatModel)

    .prompt()
    .user("What's the weather like in San Francisco?")
    .tools(new WeatherTools())
    .call()
    .content();
Copied!

```

###  4. Options Configuration

Before:

```

FunctionCallingOptions.builder()

    .model(modelName)
    .function("weatherFunction")
    .build()
Copied!

```

After:

```

ToolCallingChatOptions.builder()

    .model(modelName)
    .toolNames("weatherFunction")
    .build()
Copied!

```

###  5. Default Functions in ChatClient Builder

Before:

```

ChatClient.builder(chatModel)

    .defaultFunctions(FunctionCallback.builder()
        .function("getCurrentWeather", new MockWeatherService())
        .description("Get the weather in location")
        .inputType(MockWeatherService.Request.class)
        .build())
    .build()
Copied!

```

After:

```

ChatClient.builder(chatModel)

    .defaultTools(FunctionToolCallback.builder("getCurrentWeather", new MockWeatherService())
        .description("Get the weather in location")
        .inputType(MockWeatherService.Request.class)
        .build())
    .build()
Copied!

```

###  6. Spring Bean Configuration

Before:

```

@Bean

public FunctionCallback weatherFunctionInfo() {

    return FunctionCallback.builder()
        .function("WeatherInfo", new MockWeatherService())
        .description("Get the current weather")
        .inputType(MockWeatherService.Request.class)
        .build();
}

Copied!

```

After:

```

@Bean

public ToolCallback weatherFunctionInfo() {

    return FunctionToolCallback.builder("WeatherInfo", new MockWeatherService())
        .description("Get the current weather")
        .inputType(MockWeatherService.Request.class)
        .build();
}

Copied!

```

##  Breaking Changes

  1. The `method()` configuration in function callbacks has been replaced with a more explicit method tool configuration using `ToolDefinition` and `MethodToolCallback`.
  2. When using method-based callbacks, you now need to explicitly find the method using `ReflectionUtils` and provide it to the builder. Alternatively, you can use the declarative approach with the `@Tool` annotation.
  3. For non-static methods, you must now provide both the method and the target object:

```

MethodToolCallback.builder()

    .toolDefinition(ToolDefinition.builder(toolMethod)
        .description("Description")
        .build())
    .toolMethod(toolMethod)
    .toolObject(targetObject)
    .build()
```

##  Deprecated Methods

The following methods are deprecated and will be removed in a future release:

  * `ChatClient.Builder.defaultFunctions(String…​)`
  * `ChatClient.Builder.defaultFunctions(FunctionCallback…​)`
  * `ChatClient.RequestSpec.functions()`

Use their `tools` counterparts instead.

##  Declarative Specification with @Tool

Now you can use the method-level annotation (`@Tool`) to register tools with
Spring AI:

```

class Home {

    @Tool(description = "Turn light On or Off in a room.")
    void turnLight(String roomName, boolean on) {
        // ...
        logger.info("Turn light in room: {} to: {}", roomName, on);
    }
}

String response = ChatClient.create(this.chatModel).prompt()

        .user("Turn the light in the living room On.")
        .tools(new Home())
        .call()
        .content();
Copied!

```

##  Additional Notes

  1. The new API provides better separation between tool definition and implementation.
  2. Tool definitions can be reused across different implementations.
  3. The builder pattern has been simplified for common use cases.
  4. Better support for method-based tools with improved error handling.

##  Timeline

The deprecated methods will be maintained for backward compatibility in the
current milestone version but will be removed in the next milestone release.
It’s recommended to migrate to the new API as soon as possible.

Upgrade Notes

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

