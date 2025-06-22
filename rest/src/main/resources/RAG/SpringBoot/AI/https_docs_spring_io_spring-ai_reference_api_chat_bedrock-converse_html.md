source URL: https://docs.spring.io/spring-ai/reference/api/chat/bedrock-converse.html 
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

### Bedrock Converse API

  * Prerequisites
  * Auto-configuration
  * Chat Properties
  * Runtime Options
  * Tool Calling
  * Multimodal
  * Images
  * Video
  * Documents
  * Sample Controller

  * Spring AI
  * Reference
  * Models
  * Chat Models
  * Amazon Bedrock Converse

# Bedrock Converse API

### Bedrock Converse API

  * Prerequisites
  * Auto-configuration
  * Chat Properties
  * Runtime Options
  * Tool Calling
  * Multimodal
  * Images
  * Video
  * Documents
  * Sample Controller

Amazon Bedrock Converse API provides a unified interface for conversational AI
models with enhanced capabilities including function/tool calling, multimodal
inputs, and streaming responses.

The Bedrock Converse API has the following high-level features:

  * Tool/Function Calling: Support for function definitions and tool use during conversations
  * Multimodal Input: Ability to process both text and image inputs in conversations
  * Streaming Support: Real-time streaming of model responses
  * System Messages: Support for system-level instructions and context setting

|  The Bedrock Converse API provides a unified interface across multiple model
providers while handling AWS-specific authentication and infrastructure
concerns. Currently, the Converse API Supported Models include: `Amazon Titan`,
`Amazon Nova`, `AI21 Labs`, `Anthropic Claude`, `Cohere Command`, `Meta Llama`,
`Mistral AI`.  
---|---  
|  Following the Bedrock recommendations, Spring AI is transitioning to using
Amazon Bedrock’s Converse API for all chat conversation implementations in
Spring AI. While the existing InvokeModel API supports conversation
applications, we strongly recommend adopting the Converse API for all Chat
conversation models. The Converse API does not support embedding operations, so
these will remain in the current API and the embedding model functionality in
the existing `InvokeModel API` will be maintained  
---|---  
##  Prerequisites

Refer to Getting started with Amazon Bedrock for setting up API access

  * Obtain AWS credentials: If you don’t have an AWS account and AWS CLI configured yet, this video guide can help you configure it: AWS CLI & SDK Setup in Less Than 4 Minutes!. You should be able to obtain your access and security keys.
  * Enable the Models to use: Go to Amazon Bedrock and from the Model Access menu on the left, configure access to the models you are going to use.

##  Auto-configuration

|  There has been a significant change in the Spring AI auto-configuration,
starter modules' artifact names. Please refer to the upgrade notes for more
information.  
---|---  
Add the `spring-ai-starter-model-bedrock-converse` dependency to your project’s
Maven `pom.xml` or Gradle `build.gradle` build files:

  * Maven
  * Gradle

```

<dependency>

    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-starter-model-bedrock-converse</artifactId>
</dependency>

Copied!

```

```

dependencies {

    implementation 'org.springframework.ai:spring-ai-starter-model-bedrock-converse'
}

Copied!

```

|  Refer to the Dependency Management section to add the Spring AI BOM to your
build file.  
---|---  
###  Chat Properties

The prefix `spring.ai.bedrock.aws` is the property prefix to configure the
connection to AWS Bedrock.

Property | Description | Default  
---|---|---  
spring.ai.bedrock.aws.region | AWS region to use. | us-east-1  
spring.ai.bedrock.aws.timeout | AWS timeout to use. | 5m  
spring.ai.bedrock.aws.access-key | AWS access key. | -  
spring.ai.bedrock.aws.secret-key | AWS secret key. | -  
spring.ai.bedrock.aws.session-token | AWS session token for temporary credentials. | -  
|  Enabling and disabling of the chat auto-configurations are now configured via
top level properties with the prefix `spring.ai.model.chat`. To enable,
spring.ai.model.chat=bedrock-converse (It is enabled by default) To disable,
spring.ai.model.chat=none (or any value which doesn’t match bedrock-converse)
This change is done to allow configuration of multiple models.  
---|---  
The prefix `spring.ai.bedrock.converse.chat` is the property prefix that
configures the chat model implementation for the Converse API.

Property | Description | Default  
---|---|---  
spring.ai.bedrock.converse.chat.enabled (Removed and no longer valid) | Enable Bedrock Converse chat model. | true  
spring.ai.model.chat | Enable Bedrock Converse chat model. | bedrock-converse  
spring.ai.bedrock.converse.chat.options.model | The model ID to use. You can use the Supported models and model features | None. Select your modelId from the AWS Bedrock console.  
spring.ai.bedrock.converse.chat.options.temperature | Controls the randomness of the output. Values can range over [0.0,1.0] | 0.8  
spring.ai.bedrock.converse.chat.options.top-p | The maximum cumulative probability of tokens to consider when sampling. | AWS Bedrock default  
spring.ai.bedrock.converse.chat.options.top-k | Number of token choices for generating the next token. | AWS Bedrock default  
spring.ai.bedrock.converse.chat.options.max-tokens | Maximum number of tokens in the generated response. | 500  
##  Runtime Options

Use the portable `ChatOptions` or `ToolCallingChatOptions` portable builders to
create model configurations, such as temperature, maxToken, topP, etc.

On start-up, the default options can be configured with the
`BedrockConverseProxyChatModel(api, options)` constructor or the
`spring.ai.bedrock.converse.chat.options.*` properties.

At run-time you can override the default options by adding new, request
specific, options to the `Prompt` call:

```

var options = ToolCallingChatOptions.builder()

        .model("anthropic.claude-3-5-sonnet-20240620-v1:0")
        .temperature(0.6)
        .maxTokens(300)
        .toolCallbacks(List.of(FunctionToolCallback.builder("getCurrentWeather", new WeatherService())
            .description("Get the weather in location. Return temperature in 36°F or 36°C format. Use multi-turn if needed.")
            .inputType(WeatherService.Request.class)
            .build()))
        .build();

String response = ChatClient.create(this.chatModel)

    .prompt("What is current weather in Amsterdam?")
    .options(options)
    .call()
    .content();
Copied!

```

##  Tool Calling

The Bedrock Converse API supports tool calling capabilities, allowing models to
use tools during conversations. Here’s an example of how to define and use @Tool
based tools:

```

public class WeatherService {

    @Tool(description = "Get the weather in location")
    public String weatherByLocation(@ToolParam(description= "City or state name") String location) {
        ...
    }
}

String response = ChatClient.create(this.chatModel)

        .prompt("What's the weather like in Boston?")
        .tools(new WeatherService())
        .call()
        .content();
Copied!

```

You can use the java.util.function beans as tools as well:

```

@Bean

@Description("Get the weather in location. Return temperature in 36°F or 36°C
format.")

public Function<Request, Response> weatherFunction() {

    return new MockWeatherService();
}

String response = ChatClient.create(this.chatModel)

        .prompt("What's the weather like in Boston?")
        .tools("weatherFunction")
        .inputType(Request.class)
        .call()
        .content();
Copied!

```

Find more in Tools documentation.

##  Multimodal

Multimodality refers to a model’s ability to simultaneously understand and
process information from various sources, including text, images, video, pdf,
doc, html, md and more data formats.

The Bedrock Converse API supports multimodal inputs, including text and image
inputs, and can generate a text response based on the combined input.

You need a model that supports multimodal inputs, such as the Anthropic Claude
or Amazon Nova models.

###  Images

For models that support vision multimodality, such as Amazon Nova, Anthropic
Claude, Llama 3.2, the Bedrock Converse API Amazon allows you to include
multiple images in the payload. Those models can analyze the passed images and
answer questions, classify an image, as well as summarize images based on
provided instructions.

Currently, Bedrock Converse supports the `base64` encoded images of
`image/jpeg`, `image/png`, `image/gif` and `image/webp` mime types.

Spring AI’s `Message` interface supports multimodal AI models by introducing the
`Media` type. It contains data and information about media attachments in
messages, using Spring’s `org.springframework.util.MimeType` and a
`java.lang.Object` for the raw media data.

Below is a simple code example, demonstrating the combination of user text with
an image.

```

String response = ChatClient.create(chatModel)

    .prompt()
    .user(u -> u.text("Explain what do you see on this picture?")
        .media(Media.Format.IMAGE_PNG, new ClassPathResource("/test.png")))
    .call()
    .content();

logger.info(response);

Copied!

```

It takes as an input the `test.png` image:

along with the text message "Explain what do you see on this picture?", and
generates a response something like:

```

The image shows a close-up view of a wire fruit basket containing several pieces
of fruit.

...

```

###  Video

The Amazon Nova models allow you to include a single video in the payload, which
can be provided either in base64 format or through an Amazon S3 URI.

Currently, Bedrock Nova supports the images of `video/x-matros`,
`video/quicktime`, `video/mp4`, `video/video/webm`, `video/x-flv`, `video/mpeg`,
`video/x-ms-wmv` and `image/3gpp` mime types.

Spring AI’s `Message` interface supports multimodal AI models by introducing the
`Media`` type. It contains data and information about media attachments in
messages, using Spring’s `org.springframework.util.MimeType` and a
`java.lang.Object` for the raw media data.

Below is a simple code example, demonstrating the combination of user text with
a video.

```

String response = ChatClient.create(chatModel)

    .prompt()
    .user(u -> u.text("Explain what do you see in this video?")
        .media(Media.Format.VIDEO_MP4, new ClassPathResource("/test.video.mp4")))
    .call()
    .content();

logger.info(response);

Copied!

```

It takes as an input the `test.video.mp4` image:

along with the text message "Explain what do you see in this video?", and
generates a response something like:

```

The video shows a group of baby chickens, also known as chicks, huddled together
on a surface

...

```

###  Documents

For some models, Bedrock allows you to include documents in the payload through
Converse API document support, which can be provided in bytes. The document
support has two different variants as explained below:

  * **Text document types** (txt, csv, html, md, and so on), where the emphasis is on text understanding. These use case include answering based on textual elements of the document.
  * **Media document types** (pdf, docx, xlsx), where the emphasis is on vision-based understanding to answer questions. These use cases include answering questions based on charts, graphs, and so on.

Currently the Anthropic PDF support (beta) and Amazon Bedrock Nova models
support document multimodality.

Below is a simple code example, demonstrating the combination of user text with
a media document.

```

String response = ChatClient.create(chatModel)

    .prompt()
    .user(u -> u.text(
            "You are a very professional document summarization specialist. Please summarize the given document.")
        .media(Media.Format.DOC_PDF, new ClassPathResource("/spring-ai-reference-overview.pdf")))
    .call()
    .content();

logger.info(response);

Copied!

```

It takes as an input the `spring-ai-reference-overview.pdf` document:

along with the text message "You are a very professional document summarization
specialist. Please summarize the given document.", and generates a response
something like:

```

**Introduction:**

- Spring AI is designed to simplify the development of applications with artificial intelligence (AI) capabilities, aiming to avoid unnecessary complexity.
...

```

##  Sample Controller

Create a new Spring Boot project and add the `spring-ai-starter-model-bedrock-
converse` to your dependencies.

Add an `application.properties` file under `src/main/resources`:

```

spring.ai.bedrock.aws.region=eu-central-1

spring.ai.bedrock.aws.timeout=10m

spring.ai.bedrock.aws.access-key=${AWS_ACCESS_KEY_ID}

spring.ai.bedrock.aws.secret-key=${AWS_SECRET_ACCESS_KEY}

# session token is only required for temporary credentials

spring.ai.bedrock.aws.session-token=${AWS_SESSION_TOKEN}

spring.ai.bedrock.converse.chat.options.temperature=0.8

spring.ai.bedrock.converse.chat.options.top-k=15

Copied!

```

Here’s an example controller using the chat model:

```

@RestController

public class ChatController {

    private final ChatClient chatClient;

    @Autowired
    public ChatController(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    @GetMapping("/ai/generate")
    public Map generate(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
        return Map.of("generation", this.chatClient.prompt(message).call().content());
    }

    @GetMapping("/ai/generateStream")
    public Flux<ChatResponse> generateStream(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
        return this.chatClient.prompt(message).stream().content();
    }
}

Copied!

```

Chat Models Comparison Anthropic 3

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

