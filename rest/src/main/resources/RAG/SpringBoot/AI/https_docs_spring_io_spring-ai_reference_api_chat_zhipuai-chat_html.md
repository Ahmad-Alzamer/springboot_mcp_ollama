source URL: https://docs.spring.io/spring-ai/reference/api/chat/zhipuai-chat.html 
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

### ZhiPu AI Chat

  * Prerequisites
  * Add Repositories and BOM
  * Auto-configuration
  * Chat Properties
  * Runtime Options
  * Sample Controller
  * Manual Configuration
  * Low-level ZhiPuAiApi Client

  * Spring AI
  * Reference
  * Models
  * Chat Models
  * ZhiPu AI

# ZhiPu AI Chat

### ZhiPu AI Chat

  * Prerequisites
  * Add Repositories and BOM
  * Auto-configuration
  * Chat Properties
  * Runtime Options
  * Sample Controller
  * Manual Configuration
  * Low-level ZhiPuAiApi Client

Spring AI supports the various AI language models from ZhiPu AI. You can
interact with ZhiPu AI language models and create a multilingual conversational
assistant based on ZhiPuAI models.

##  Prerequisites

You will need to create an API with ZhiPuAI to access ZhiPu AI language models.

Create an account at ZhiPu AI registration page and generate the token on the
API Keys page.

The Spring AI project defines a configuration property named
`spring.ai.zhipuai.api-key` that you should set to the value of the `API Key`
obtained from the API Keys page.

You can set this configuration property in your `application.properties` file:

```

spring.ai.zhipuai.api-key=<your-zhipuai-api-key>

Copied!

```

For enhanced security when handling sensitive information like API keys, you can
use Spring Expression Language (SpEL) to reference a custom environment
variable:

```

# In application.yml

spring:

  ai:

    zhipuai:
      api-key: ${ZHIPUAI_API_KEY}
Copied!

```

```

# In your environment or .env file

export ZHIPUAI_API_KEY=<your-zhipuai-api-key>

Copied!

```

You can also set this configuration programmatically in your application code:

```

// Retrieve API key from a secure source or environment variable

String apiKey = System.getenv("ZHIPUAI_API_KEY");

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
Spring AI provides Spring Boot auto-configuration for the ZhiPuAI Chat Client.
To enable it add the following dependency to your project’s Maven `pom.xml`
file:

```

<dependency>

    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-starter-model-zhipuai</artifactId>
</dependency>

Copied!

```

or to your Gradle `build.gradle` build file.

```

dependencies {

    implementation 'org.springframework.ai:spring-ai-starter-model-zhipuai'
}

Copied!

```

|  Refer to the Dependency Management section to add the Spring AI BOM to your
build file.  
---|---  
###  Chat Properties

####  Retry Properties

The prefix `spring.ai.retry` is used as the property prefix that lets you
configure the retry mechanism for the ZhiPu AI chat model.

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

The prefix `spring.ai.zhiPu` is used as the property prefix that lets you
connect to ZhiPuAI.

Property | Description | Default  
---|---|---  
spring.ai.zhipuai.base-url | The URL to connect to | open.bigmodel.cn/api/paas  
spring.ai.zhipuai.api-key | The API Key | -  
####  Configuration Properties

|  Enabling and disabling of the chat auto-configurations are now configured via
top level properties with the prefix `spring.ai.model.chat`. To enable,
spring.ai.model.chat=zhipuai (It is enabled by default) To disable,
spring.ai.model.chat=none (or any value which doesn’t match zhipuai) This change
is done to allow configuration of multiple models.  
---|---  
The prefix `spring.ai.zhipuai.chat` is the property prefix that lets you
configure the chat model implementation for ZhiPuAI.

Property | Description | Default  
---|---|---  
spring.ai.zhipuai.chat.enabled (Removed and no longer valid) | Enable ZhiPuAI chat model. | true  
spring.ai.model.chat | Enable ZhiPuAI chat model. | zhipuai  
spring.ai.zhipuai.chat.base-url | Optional overrides the spring.ai.zhipuai.base-url to provide chat specific url | open.bigmodel.cn/api/paas  
spring.ai.zhipuai.chat.api-key | Optional overrides the spring.ai.zhipuai.api-key to provide chat specific api-key | -  
spring.ai.zhipuai.chat.options.model | This is the ZhiPuAI Chat model to use | `GLM-3-Turbo` (the `GLM-3-Turbo`, `GLM-4`, `GLM-4-Air`, `GLM-4-AirX`, `GLM-4-Flash`, and `GLM-4V` point to the latest model versions)  
spring.ai.zhipuai.chat.options.maxTokens | The maximum number of tokens to generate in the chat completion. The total length of input tokens and generated tokens is limited by the model’s context length. | -  
spring.ai.zhipuai.chat.options.temperature | What sampling temperature to use, between 0 and 1. Higher values like 0.8 will make the output more random, while lower values like 0.2 will make it more focused and deterministic. We generally recommend altering this or top_p but not both. | 0.7  
spring.ai.zhipuai.chat.options.topP | An alternative to sampling with temperature, called nucleus sampling, where the model considers the results of the tokens with top_p probability mass. So 0.1 means only the tokens comprising the top 10% probability mass are considered. We generally recommend altering this or temperature but not both.. | 1.0  
spring.ai.zhipuai.chat.options.stop | The model will stop generating characters specified by stop, and currently only supports a single stop word in the format of ["stop_word1"] | -  
spring.ai.zhipuai.chat.options.user | A unique identifier representing your end-user, which can help ZhiPuAI to monitor and detect abuse. | -  
spring.ai.zhipuai.chat.options.requestId | The parameter is passed by the client and must ensure uniqueness. It is used to distinguish the unique identifier for each request. If the client does not provide it, the platform will generate it by default. | -  
spring.ai.zhipuai.chat.options.doSample | When do_sample is set to true, the sampling strategy is enabled. If do_sample is false, the sampling strategy parameters temperature and top_p will not take effect. | true  
spring.ai.zhipuai.chat.options.proxy-tool-calls | If true, the Spring AI will not handle the function calls internally, but will proxy them to the client. Then is the client’s responsibility to handle the function calls, dispatch them to the appropriate function, and return the results. If false (the default), the Spring AI will handle the function calls internally. Applicable only for chat models with function calling support | false  
|  You can override the common `spring.ai.zhipuai.base-url` and
`spring.ai.zhipuai.api-key` for the `ChatModel` implementations. The
`spring.ai.zhipuai.chat.base-url` and `spring.ai.zhipuai.chat.api-key`
properties if set take precedence over the common properties. This is useful if
you want to use different ZhiPuAI accounts for different models and different
model endpoints.  
---|---  
|  All properties prefixed with `spring.ai.zhipuai.chat.options` can be
overridden at runtime by adding a request specific Runtime Options to the
`Prompt` call.  
---|---  
##  Runtime Options

The ZhiPuAiChatOptions.java provides model configurations, such as the model to
use, the temperature, the frequency penalty, etc.

On start-up, the default options can be configured with the
`ZhiPuAiChatModel(api, options)` constructor or the
`spring.ai.zhipuai.chat.options.*` properties.

At run-time you can override the default options by adding new, request
specific, options to the `Prompt` call. For example to override the default
model and temperature for a specific request:

```

ChatResponse response = chatModel.call(

    new Prompt(
        "Generate the names of 5 famous pirates.",
        ZhiPuAiChatOptions.builder()
            .model(ZhiPuAiApi.ChatModel.GLM_3_Turbo.getValue())
            .temperature(0.5)
        .build()
    ));
Copied!

```

|  In addition to the model specific ZhiPuAiChatOptions you can use a portable
ChatOptions instance, created with the ChatOptionsBuilder#builder().  
---|---  
##  Sample Controller

Create a new Spring Boot project and add the `spring-ai-starter-model-zhipuai`
to your pom (or gradle) dependencies.

Add a `application.properties` file, under the `src/main/resources` directory,
to enable and configure the ZhiPuAi chat model:

```

spring.ai.zhipuai.api-key=YOUR_API_KEY

spring.ai.zhipuai.chat.options.model=glm-4-air

spring.ai.zhipuai.chat.options.temperature=0.7

Copied!

```

|  replace the `api-key` with your ZhiPuAI credentials.  
---|---  
This will create a `ZhiPuAiChatModel` implementation that you can inject into
your class. Here is an example of a simple `@Controller` class that uses the
chat model for text generations.

```

@RestController

public class ChatController {

    private final ZhiPuAiChatModel chatModel;

    @Autowired
    public ChatController(ZhiPuAiChatModel chatModel) {
        this.chatModel = chatModel;
    }

    @GetMapping("/ai/generate")
    public Map generate(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
        return Map.of("generation", this.chatModel.call(message));
    }

    @GetMapping("/ai/generateStream")
	public Flux<ChatResponse> generateStream(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
        var prompt = new Prompt(new UserMessage(message));
        return this.chatModel.stream(prompt);
    }
}

Copied!

```

##  Manual Configuration

The ZhiPuAiChatModel implements the `ChatModel` and `StreamingChatModel` and
uses the Low-level ZhiPuAiApi Client to connect to the ZhiPuAI service.

Add the `spring-ai-zhipuai` dependency to your project’s Maven `pom.xml` file:

```

<dependency>

    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-zhipuai</artifactId>
</dependency>

Copied!

```

or to your Gradle `build.gradle` build file.

```

dependencies {

    implementation 'org.springframework.ai:spring-ai-zhipuai'
}

Copied!

```

|  Refer to the Dependency Management section to add the Spring AI BOM to your
build file.  
---|---  
Next, create a `ZhiPuAiChatModel` and use it for text generations:

```

var zhiPuAiApi = new ZhiPuAiApi(System.getenv("ZHIPU_AI_API_KEY"));

var chatModel = new ZhiPuAiChatModel(this.zhiPuAiApi,
ZhiPuAiChatOptions.builder()

                .model(ZhiPuAiApi.ChatModel.GLM_3_Turbo.getValue())
                .temperature(0.4)
                .maxTokens(200)
                .build());

ChatResponse response = this.chatModel.call(

    new Prompt("Generate the names of 5 famous pirates."));

// Or with streaming responses

Flux<ChatResponse> streamResponse = this.chatModel.stream(

    new Prompt("Generate the names of 5 famous pirates."));
Copied!

```

The `ZhiPuAiChatOptions` provides the configuration information for the chat
requests. The `ZhiPuAiChatOptions.Builder` is fluent options builder.

###  Low-level ZhiPuAiApi Client

The ZhiPuAiApi provides is lightweight Java client for ZhiPu AI API.

Here is a simple snippet how to use the api programmatically:

```

ZhiPuAiApi zhiPuAiApi =

    new ZhiPuAiApi(System.getenv("ZHIPU_AI_API_KEY"));

ChatCompletionMessage chatCompletionMessage =

    new ChatCompletionMessage("Hello world", Role.USER);

// Sync request

ResponseEntity<ChatCompletion> response = this.zhiPuAiApi.chatCompletionEntity(

    new ChatCompletionRequest(List.of(this.chatCompletionMessage), ZhiPuAiApi.ChatModel.GLM_3_Turbo.getValue(), 0.7, false));

// Streaming request

Flux<ChatCompletionChunk> streamResponse = this.zhiPuAiApi.chatCompletionStream(

        new ChatCompletionRequest(List.of(this.chatCompletionMessage), ZhiPuAiApi.ChatModel.GLM_3_Turbo.getValue(), 0.7, true));
Copied!

```

Follow the ZhiPuAiApi.java's JavaDoc for further information.

####  ZhiPuAiApi Samples

  * The ZhiPuAiApiIT.java test provides some general examples how to use the lightweight library.

QianFan Embedding Models

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

