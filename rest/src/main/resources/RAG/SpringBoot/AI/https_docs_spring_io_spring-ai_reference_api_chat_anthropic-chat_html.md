source URL: https://docs.spring.io/spring-ai/reference/api/chat/anthropic-chat.html 
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

### Anthropic 3 Chat

  * Prerequisites
  * Add Repositories and BOM
  * Auto-configuration
  * Chat Properties
  * Runtime Options
  * Tool/Function Calling
  * Multimodal
  * Images
  * PDF
  * Sample Controller
  * Manual Configuration
  * Low-level AnthropicApi Client
  * Low-level API Examples

  * Spring AI
  * Reference
  * Models
  * Chat Models
  * Anthropic 3

# Anthropic 3 Chat

### Anthropic 3 Chat

  * Prerequisites
  * Add Repositories and BOM
  * Auto-configuration
  * Chat Properties
  * Runtime Options
  * Tool/Function Calling
  * Multimodal
  * Images
  * PDF
  * Sample Controller
  * Manual Configuration
  * Low-level AnthropicApi Client
  * Low-level API Examples

Anthropic Claude is a family of foundational AI models that can be used in a
variety of applications. For developers and businesses, you can leverage the API
access and build directly on top of Anthropic’s AI infrastructure.

Spring AI supports the Anthropic Messaging API for sync and streaming text
generations.

|  Anthropic’s Claude models are also available through Amazon Bedrock Converse.
Spring AI provides dedicated Amazon Bedrock Converse Anthropic client
implementations as well.  
---|---  
##  Prerequisites

You will need to create an API key on the Anthropic portal.

Create an account at Anthropic API dashboard and generate the API key on the Get
API Keys page.

The Spring AI project defines a configuration property named
`spring.ai.anthropic.api-key` that you should set to the value of the `API Key`
obtained from anthropic.com.

You can set this configuration property in your `application.properties` file:

```

spring.ai.anthropic.api-key=<your-anthropic-api-key>

Copied!

```

For enhanced security when handling sensitive information like API keys, you can
use Spring Expression Language (SpEL) to reference a custom environment
variable:

```

# In application.yml

spring:

  ai:

    anthropic:
      api-key: ${ANTHROPIC_API_KEY}
Copied!

```

```

# In your environment or .env file

export ANTHROPIC_API_KEY=<your-anthropic-api-key>

Copied!

```

You can also set this configuration programmatically in your application code:

```

// Retrieve API key from a secure source or environment variable

String apiKey = System.getenv("ANTHROPIC_API_KEY");

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
Spring AI provides Spring Boot auto-configuration for the Anthropic Chat Client.
To enable it add the following dependency to your project’s Maven `pom.xml` or
Gradle `build.gradle` file:

  * Maven
  * Gradle

```

<dependency>

    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-starter-model-anthropic</artifactId>
</dependency>

Copied!

```

```

dependencies {

    implementation 'org.springframework.ai:spring-ai-starter-model-anthropic'
}

Copied!

```

|  Refer to the Dependency Management section to add the Spring AI BOM to your
build file.  
---|---  
###  Chat Properties

####  Retry Properties

The prefix `spring.ai.retry` is used as the property prefix that lets you
configure the retry mechanism for the Anthropic chat model.

Property | Description | Default  
---|---|---  
spring.ai.retry.max-attempts | Maximum number of retry attempts. | 10  
spring.ai.retry.backoff.initial-interval | Initial sleep duration for the exponential backoff policy. | 2 sec.  
spring.ai.retry.backoff.multiplier | Backoff interval multiplier. | 5  
spring.ai.retry.backoff.max-interval | Maximum backoff duration. | 3 min.  
spring.ai.retry.on-client-errors | If false, throw a NonTransientAiException, and do not attempt retry for `4xx` client error codes | false  
spring.ai.retry.exclude-on-http-codes | List of HTTP status codes that should NOT trigger a retry (e.g. to throw NonTransientAiException). | empty  
spring.ai.retry.on-http-codes | List of HTTP status codes that should trigger a retry (e.g. to throw TransientAiException). | empty  
|  currently the retry policies are not applicable for the streaming API.  
---|---  
####  Connection Properties

The prefix `spring.ai.anthropic` is used as the property prefix that lets you
connect to Anthropic.

Property | Description | Default  
---|---|---  
spring.ai.anthropic.base-url | The URL to connect to | api.anthropic.com  
spring.ai.anthropic.completions-path | The path to append to the base URL. | `/v1/chat/completions`  
spring.ai.anthropic.version | Anthropic API version | 2023-06-01  
spring.ai.anthropic.api-key | The API Key | -  
spring.ai.anthropic.beta-version | Enables new/experimental features. If set to `max-tokens-3-5-sonnet-2024-07-15` the output tokens limit is increased from `4096` to `8192` tokens (for claude-3-5-sonnet only). | `tools-2024-04-04`  
####  Configuration Properties

|  Enabling and disabling of the chat auto-configurations are now configured via
top level properties with the prefix `spring.ai.model.chat`. To enable,
spring.ai.model.chat=anthropic (It is enabled by default) To disable,
spring.ai.model.chat=none (or any value which doesn’t match anthropic) This
change is done to allow configuration of multiple models.  
---|---  
The prefix `spring.ai.anthropic.chat` is the property prefix that lets you
configure the chat model implementation for Anthropic.

Property | Description | Default  
---|---|---  
spring.ai.anthropic.chat.enabled (Removed and no longer valid) | Enable Anthropic chat model. | true  
spring.ai.model.chat | Enable Anthropic chat model. | anthropic  
spring.ai.anthropic.chat.options.model | This is the Anthropic Chat model to use. Supports: `claude-3-7-sonnet-latest`, `claude-3-5-sonnet-latest`, `claude-3-opus-20240229`, `claude-3-sonnet-20240229`, `claude-3-haiku-20240307` | `claude-3-7-sonnet-latest`  
spring.ai.anthropic.chat.options.temperature | The sampling temperature to use that controls the apparent creativity of generated completions. Higher values will make output more random while lower values will make results more focused and deterministic. It is not recommended to modify temperature and top_p for the same completions request as the interaction of these two settings is difficult to predict. | 0.8  
spring.ai.anthropic.chat.options.max-tokens | The maximum number of tokens to generate in the chat completion. The total length of input tokens and generated tokens is limited by the model’s context length. | 500  
spring.ai.anthropic.chat.options.stop-sequence | Custom text sequences that will cause the model to stop generating. Our models will normally stop when they have naturally completed their turn, which will result in a response stop_reason of "end_turn". If you want the model to stop generating when it encounters custom strings of text, you can use the stop_sequences parameter. If the model encounters one of the custom sequences, the response stop_reason value will be "stop_sequence" and the response stop_sequence value will contain the matched stop sequence. | -  
spring.ai.anthropic.chat.options.top-p | Use nucleus sampling. In nucleus sampling, we compute the cumulative distribution over all the options for each subsequent token in decreasing probability order and cut it off once it reaches a particular probability specified by top_p. You should either alter temperature or top_p, but not both. Recommended for advanced use cases only. You usually only need to use temperature. | -  
spring.ai.anthropic.chat.options.top-k | Only sample from the top K options for each subsequent token. Used to remove "long tail" low probability responses. Learn more technical details here. Recommended for advanced use cases only. You usually only need to use temperature. | -  
spring.ai.anthropic.chat.options.toolNames | List of tools, identified by their names, to enable for tool calling in a single prompt requests. Tools with those names must exist in the toolCallbacks registry. | -  
spring.ai.anthropic.chat.options.toolCallbacks | Tool Callbacks to register with the ChatModel. | -  
spring.ai.anthropic.chat.options.internal-tool-execution-enabled | If false, the Spring AI will not handle the tool calls internally, but will proxy them to the client. Then it is the client’s responsibility to handle the tool calls, dispatch them to the appropriate function, and return the results. If true (the default), the Spring AI will handle the function calls internally. Applicable only for chat models with function calling support | true  
(**deprecated** - replaced by `toolNames`) spring.ai.anthropic.chat.options.functions | List of functions, identified by their names, to enable for function calling in a single prompt requests. Functions with those names must exist in the functionCallbacks registry. | -  
(**deprecated** - replaced by `toolCallbacks`) spring.ai.anthropic.chat.options.functionCallbacks | Tool Function Callbacks to register with the ChatModel. | -  
(**deprecated** - replaced by a negated `internal-tool-execution-enabled`) spring.ai.anthropic.chat.options.proxy-tool-calls | If true, the Spring AI will not handle the function calls internally, but will proxy them to the client. Then is the client’s responsibility to handle the function calls, dispatch them to the appropriate function, and return the results. If false (the default), the Spring AI will handle the function calls internally. Applicable only for chat models with function calling support | false  
spring.ai.anthropic.chat.options.http-headers | Optional HTTP headers to be added to the chat completion request. | -  
|  All properties prefixed with `spring.ai.anthropic.chat.options` can be
overridden at runtime by adding a request specific Runtime Options to the
`Prompt` call.  
---|---  
##  Runtime Options

The AnthropicChatOptions.java provides model configurations, such as the model
to use, the temperature, the max token count, etc.

On start-up, the default options can be configured with the
`AnthropicChatModel(api, options)` constructor or the
`spring.ai.anthropic.chat.options.*` properties.

At run-time you can override the default options by adding new, request
specific, options to the `Prompt` call. For example to override the default
model and temperature for a specific request:

```

ChatResponse response = chatModel.call(

    new Prompt(
        "Generate the names of 5 famous pirates.",
        AnthropicChatOptions.builder()
            .model("claude-3-7-sonnet-latest")
            .temperature(0.4)
        .build()
    ));
Copied!

```

|  In addition to the model specific AnthropicChatOptions you can use a portable
ChatOptions instance, created with the ChatOptionsBuilder#builder().  
---|---  
##  Tool/Function Calling

You can register custom Java Tools with the `AnthropicChatModel` and have the
Anthropic Claude model intelligently choose to output a JSON object containing
arguments to call one or many of the registered functions. This is a powerful
technique to connect the LLM capabilities with external tools and APIs. Read
more about Tool Calling.

##  Multimodal

Multimodality refers to a model’s ability to simultaneously understand and
process information from various sources, including text, pdf, images, data
formats.

###  Images

Currently, Anthropic Claude 3 supports the `base64` source type for `images`,
and the `image/jpeg`, `image/png`, `image/gif`, and `image/webp` media types.
Check the Vision guide for more information. Anthropic Claude 3.5 Sonnet also
supports the `pdf` source type for `application/pdf` files.

Spring AI’s `Message` interface supports multimodal AI models by introducing the
Media type. This type contains data and information about media attachments in
messages, using Spring’s `org.springframework.util.MimeType` and a
`java.lang.Object` for the raw media data.

Below is a simple code example extracted from AnthropicChatModelIT.java,
demonstrating the combination of user text with an image.

```

var imageData = new ClassPathResource("/multimodal.test.png");

var userMessage = new UserMessage("Explain what do you see on this picture?",

        List.of(new Media(MimeTypeUtils.IMAGE_PNG, this.imageData)));

ChatResponse response = chatModel.call(new Prompt(List.of(this.userMessage)));

logger.info(response.getResult().getOutput().getContent());

Copied!

```

It takes as an input the `multimodal.test.png` image:

along with the text message "Explain what do you see on this picture?", and
generates a response something like:

```

The image shows a close-up view of a wire fruit basket containing several pieces
of fruit.

...

```

###  PDF

Starting with Sonnet 3.5 PDF support (beta) is provided. Use the
`application/pdf` media type to attach a PDF file to the message:

```

var pdfData = new ClassPathResource("/spring-ai-reference-overview.pdf");

var userMessage = new UserMessage(

        "You are a very professional document summarization specialist. Please summarize the given document.",
        List.of(new Media(new MimeType("application", "pdf"), pdfData)));

var response = this.chatModel.call(new Prompt(List.of(userMessage)));

Copied!

```

##  Sample Controller

Create a new Spring Boot project and add the `spring-ai-starter-model-anthropic`
to your pom (or gradle) dependencies.

Add a `application.properties` file, under the `src/main/resources` directory,
to enable and configure the Anthropic chat model:

```

spring.ai.anthropic.api-key=YOUR_API_KEY

spring.ai.anthropic.chat.options.model=claude-3-5-sonnet-latest

spring.ai.anthropic.chat.options.temperature=0.7

spring.ai.anthropic.chat.options.max-tokens=450

Copied!

```

|  replace the `api-key` with your Anthropic credentials.  
---|---  
This will create a `AnthropicChatModel` implementation that you can inject into
your class. Here is an example of a simple `@Controller` class that uses the
chat model for text generations.

```

@RestController

public class ChatController {

    private final AnthropicChatModel chatModel;

    @Autowired
    public ChatController(AnthropicChatModel chatModel) {
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

##  Manual Configuration

The AnthropicChatModel implements the `ChatModel` and `StreamingChatModel` and
uses the Low-level AnthropicApi Client to connect to the Anthropic service.

Add the `spring-ai-anthropic` dependency to your project’s Maven `pom.xml` file:

```

<dependency>

    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-anthropic</artifactId>
</dependency>

Copied!

```

or to your Gradle `build.gradle` build file.

```

dependencies {

    implementation 'org.springframework.ai:spring-ai-anthropic'
}

Copied!

```

|  Refer to the Dependency Management section to add the Spring AI BOM to your
build file.  
---|---  
Next, create a `AnthropicChatModel` and use it for text generations:

```

var anthropicApi = new AnthropicApi(System.getenv("ANTHROPIC_API_KEY"));

var chatModel = new AnthropicChatModel(this.anthropicApi,

        AnthropicChatOptions.builder()
            .model("claude-3-opus-20240229")
            .temperature(0.4)
            .maxTokens(200)
        .build());

ChatResponse response = this.chatModel.call(

    new Prompt("Generate the names of 5 famous pirates."));

// Or with streaming responses

Flux<ChatResponse> response = this.chatModel.stream(

    new Prompt("Generate the names of 5 famous pirates."));
Copied!

```

The `AnthropicChatOptions` provides the configuration information for the chat
requests. The `AnthropicChatOptions.Builder` is fluent options builder.

##  Low-level AnthropicApi Client

The AnthropicApi provides is lightweight Java client for Anthropic Message API.

Following class diagram illustrates the `AnthropicApi` chat interfaces and
building blocks:

Here is a simple snippet how to use the api programmatically:

```

AnthropicApi anthropicApi =

    new AnthropicApi(System.getenv("ANTHROPIC_API_KEY"));

AnthropicMessage chatCompletionMessage = new AnthropicMessage(

        List.of(new ContentBlock("Tell me a Joke?")), Role.USER);

// Sync request

ResponseEntity<ChatCompletionResponse> response = this.anthropicApi

    .chatCompletionEntity(new ChatCompletionRequest(AnthropicApi.ChatModel.CLAUDE_3_OPUS.getValue(),
            List.of(this.chatCompletionMessage), null, 100, 0.8, false));

// Streaming request

Flux<StreamResponse> response = this.anthropicApi

    .chatCompletionStream(new ChatCompletionRequest(AnthropicApi.ChatModel.CLAUDE_3_OPUS.getValue(),
            List.of(this.chatCompletionMessage), null, 100, 0.8, true));
Copied!

```

Follow the AnthropicApi.java's JavaDoc for further information.

###  Low-level API Examples

  * The AnthropicApiIT.java test provides some general examples how to use the lightweight library.

Amazon Bedrock Converse Azure OpenAI

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

