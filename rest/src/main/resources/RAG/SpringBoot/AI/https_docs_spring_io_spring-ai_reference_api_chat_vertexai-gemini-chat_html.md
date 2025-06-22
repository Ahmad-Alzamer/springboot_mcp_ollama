source URL: https://docs.spring.io/spring-ai/reference/api/chat/vertexai-gemini-chat.html 
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

### VertexAI Gemini Chat

  * Prerequisites
  * Auto-configuration
  * Chat Properties
  * Runtime options
  * Tool Calling
  * Multimodal
  * Image, Audio, Video
  * PDF
  * Sample Controller
  * Manual Configuration
  * Low-level Java Client

  * Spring AI
  * Reference
  * Models
  * Chat Models
  * Google VertexAI
  * VertexAI Gemini

# VertexAI Gemini Chat

### VertexAI Gemini Chat

  * Prerequisites
  * Auto-configuration
  * Chat Properties
  * Runtime options
  * Tool Calling
  * Multimodal
  * Image, Audio, Video
  * PDF
  * Sample Controller
  * Manual Configuration
  * Low-level Java Client

The Vertex AI Gemini API allows developers to build generative AI applications
using the Gemini model. The Vertex AI Gemini API supports multimodal prompts as
input and output text or code. A multimodal model is a model that is capable of
processing information from multiple modalities, including images, videos, and
text. For example, you can send the model a photo of a plate of cookies and ask
it to give you a recipe for those cookies.

Gemini is a family of generative AI models developed by Google DeepMind that is
designed for multimodal use cases. The Gemini API gives you access to the Gemini
2.0 Flash and Gemini 2.0 Flash-Lite. For specifications of the Vertex AI Gemini
API models, see Model information.

Gemini API Reference

##  Prerequisites

  * Install the gcloud CLI, appropriate for you OS.
  * Authenticate by running the following command. Replace `PROJECT_ID` with your Google Cloud project ID and `ACCOUNT` with your Google Cloud username.

```

gcloud config set project <PROJECT_ID> &&

gcloud auth application-default login <ACCOUNT>

Copied!

```

##  Auto-configuration

|  There has been a significant change in the Spring AI auto-configuration,
starter modules' artifact names. Please refer to the upgrade notes for more
information.  
---|---  
Spring AI provides Spring Boot auto-configuration for the VertexAI Gemini Chat
Client. To enable it add the following dependency to your project’s Maven
`pom.xml` or Gradle `build.gradle` build files:

  * Maven
  * Gradle

```

<dependency>

    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-starter-model-vertex-ai-gemini</artifactId>
</dependency>

Copied!

```

```

dependencies {

    implementation 'org.springframework.ai:spring-ai-starter-model-vertex-ai-gemini'
}

Copied!

```

|  Refer to the Dependency Management section to add the Spring AI BOM to your
build file.  
---|---  
###  Chat Properties

|  Enabling and disabling of the chat auto-configurations are now configured via
top level properties with the prefix `spring.ai.model.chat`. To enable,
spring.ai.model.chat=vertexai (It is enabled by default) To disable,
spring.ai.model.chat=none (or any value which doesn’t match vertexai) This
change is done to allow configuration of multiple models.  
---|---  
The prefix `spring.ai.vertex.ai.gemini` is used as the property prefix that lets
you connect to VertexAI.

Property | Description | Default  
---|---|---  
spring.ai.model.chat | Enable Chat Model client | vertexai  
spring.ai.vertex.ai.gemini.project-id | Google Cloud Platform project ID | -  
spring.ai.vertex.ai.gemini.location | Region | -  
spring.ai.vertex.ai.gemini.credentials-uri | URI to Vertex AI Gemini credentials. When provided it is used to create an a `GoogleCredentials` instance to authenticate the `VertexAI`. | -  
spring.ai.vertex.ai.gemini.api-endpoint | Vertex AI Gemini API endpoint. | -  
spring.ai.vertex.ai.gemini.scopes |  | -  
spring.ai.vertex.ai.gemini.transport | API transport. GRPC or REST. | GRPC  
The prefix `spring.ai.vertex.ai.gemini.chat` is the property prefix that lets
you configure the chat model implementation for VertexAI Gemini Chat.

Property | Description | Default  
---|---|---  
spring.ai.vertex.ai.gemini.chat.options.model | Supported Vertex AI Gemini Chat model to use include the `gemini-2.0-flash`, `gemini-2.0-flash-lite` and the new `gemini-2.5-pro-preview-03-25`, `gemini-2.5-flash-preview-04-17` models. | gemini-2.0-flash  
spring.ai.vertex.ai.gemini.chat.options.response-mime-type | Output response mimetype of the generated candidate text. | `text/plain`: (default) Text output or `application/json`: JSON response.  
spring.ai.vertex.ai.gemini.chat.options.google-search-retrieval | Use Google search Grounding feature | `true` or `false`, default `false`.  
spring.ai.vertex.ai.gemini.chat.options.temperature | Controls the randomness of the output. Values can range over [0.0,1.0], inclusive. A value closer to 1.0 will produce responses that are more varied, while a value closer to 0.0 will typically result in less surprising responses from the generative. This value specifies default to be used by the backend while making the call to the generative. | 0.7  
spring.ai.vertex.ai.gemini.chat.options.top-k | The maximum number of tokens to consider when sampling. The generative uses combined Top-k and nucleus sampling. Top-k sampling considers the set of topK most probable tokens. | -  
spring.ai.vertex.ai.gemini.chat.options.top-p | The maximum cumulative probability of tokens to consider when sampling. The generative uses combined Top-k and nucleus sampling. Nucleus sampling considers the smallest set of tokens whose probability sum is at least topP. | -  
spring.ai.vertex.ai.gemini.chat.options.candidate-count | The number of generated response messages to return. This value must be between [1, 8], inclusive. Defaults to 1. | 1  
spring.ai.vertex.ai.gemini.chat.options.max-output-tokens | The maximum number of tokens to generate. | -  
spring.ai.vertex.ai.gemini.chat.options.tool-names | List of tools, identified by their names, to enable for function calling in a single prompt request. Tools with those names must exist in the ToolCallback registry. | -  
(**deprecated** by `tool-names`) spring.ai.vertex.ai.gemini.chat.options.functions | List of functions, identified by their names, to enable for function calling in a single prompt request. Functions with those names must exist in the functionCallbacks registry. | -  
spring.ai.vertex.ai.gemini.chat.options.internal-tool-execution-enabled | If true, the tool execution should be performed, otherwise the response from the model is returned back to the user. Default is null, but if it’s null, `ToolCallingChatOptions.DEFAULT_TOOL_EXECUTION_ENABLED` which is true will take into account | -  
(**deprecated** by `internal-tool-execution-enabled`) spring.ai.vertex.ai.gemini.chat.options.proxy-tool-calls | If true, the Spring AI will not handle the function calls internally, but will proxy them to the client. Then is the client’s responsibility to handle the function calls, dispatch them to the appropriate function, and return the results. If false (the default), the Spring AI will handle the function calls internally. Applicable only for chat models with function calling support | false  
spring.ai.vertex.ai.gemini.chat.options.safety-settings | List of safety settings to control safety filters, as defined by Vertex AI Safety Filters. Each safety setting can have a method, threshold, and category. | -  
|  All properties prefixed with `spring.ai.vertex.ai.gemini.chat.options` can be
overridden at runtime by adding a request specific Runtime options to the
`Prompt` call.  
---|---  
##  Runtime options

The VertexAiGeminiChatOptions.java provides model configurations, such as the
temperature, the topK, etc.

On start-up, the default options can be configured with the
`VertexAiGeminiChatModel(api, options)` constructor or the
`spring.ai.vertex.ai.chat.options.*` properties.

At runtime, you can override the default options by adding new, request
specific, options to the `Prompt` call. For example, to override the default
temperature for a specific request:

```

ChatResponse response = chatModel.call(

    new Prompt(
        "Generate the names of 5 famous pirates.",
        VertexAiGeminiChatOptions.builder()
            .temperature(0.4)
        .build()
    ));
Copied!

```

|  In addition to the model specific `VertexAiGeminiChatOptions` you can use a
portable ChatOptions instance, created with the ChatOptionsBuilder#builder().  
---|---  
##  Tool Calling

The Vertex AI Gemini model supports tool calling (in Google Gemini context, it’s
called `function calling`) capabilities, allowing models to use tools during
conversations. Here’s an example of how to define and use `@Tool`-based tools:

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
process information from various (input) sources, including `text`, `pdf`,
`images`, `audio`, and other data formats.

###  Image, Audio, Video

Google’s Gemini AI models support this capability by comprehending and
integrating text, code, audio, images, and video. For more details, refer to the
blog post Introducing Gemini.

Spring AI’s `Message` interface supports multimodal AI models by introducing the
Media type. This type contains data and information about media attachments in
messages, using Spring’s `org.springframework.util.MimeType` and a
`java.lang.Object` for the raw media data.

Below is a simple code example extracted from
VertexAiGeminiChatModelIT#multiModalityTest(), demonstrating the combination of
user text with an image.

```

byte[] data = new ClassPathResource("/vertex-test.png").getContentAsByteArray();

var userMessage = new UserMessage("Explain what do you see on this picture?",

        List.of(new Media(MimeTypeUtils.IMAGE_PNG, this.data)));

ChatResponse response = chatModel.call(new Prompt(List.of(this.userMessage)));

Copied!

```

###  PDF

Latest Vertex Gemini provides support for PDF input types.. Use the
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

Create a new Spring Boot project and add the `spring-ai-starter-model-vertex-ai-
gemini` to your pom (or gradle) dependencies.

Add a `application.properties` file, under the `src/main/resources` directory,
to enable and configure the VertexAi chat model:

```

spring.ai.vertex.ai.gemini.project-id=PROJECT_ID

spring.ai.vertex.ai.gemini.location=LOCATION

spring.ai.vertex.ai.gemini.chat.options.model=gemini-2.0-flash

spring.ai.vertex.ai.gemini.chat.options.temperature=0.5

Copied!

```

|  Replace the `project-id` with your Google Cloud Project ID and `location` is
Google Cloud Region like `us-central1`, `europe-west1`, etc…​  
---|---  
|  Each model has its own set of supported regions, you can find the list of
supported regions in the model page. For example, model=`gemini-2.5-flash` is
currently available in `us-central1` region only, you must set location=`us-
central1`, following the model page Gemini 2.5 Flash - Supported Regions.  
---|---  
This will create a `VertexAiGeminiChatModel` implementation that you can inject
into your class. Here is an example of a simple `@Controller` class that uses
the chat model for text generations.

```

@RestController

public class ChatController {

    private final VertexAiGeminiChatModel chatModel;

    @Autowired
    public ChatController(VertexAiGeminiChatModel chatModel) {
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

The VertexAiGeminiChatModel implements the `ChatModel` and uses the `VertexAI`
to connect to the Vertex AI Gemini service.

Add the `spring-ai-vertex-ai-gemini` dependency to your project’s Maven
`pom.xml` file:

```

<dependency>

    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-vertex-ai-gemini</artifactId>
</dependency>

Copied!

```

or to your Gradle `build.gradle` build file.

```

dependencies {

    implementation 'org.springframework.ai:spring-ai-vertex-ai-gemini'
}

Copied!

```

|  Refer to the Dependency Management section to add the Spring AI BOM to your
build file.  
---|---  
Next, create a `VertexAiGeminiChatModel` and use it for text generations:

```

VertexAI vertexApi =  new VertexAI(projectId, location);

var chatModel = new VertexAiGeminiChatModel(this.vertexApi,

    VertexAiGeminiChatOptions.builder()
        .model(ChatModel.GEMINI_2_0_FLASH)
        .temperature(0.4)
    .build());

ChatResponse response = this.chatModel.call(

    new Prompt("Generate the names of 5 famous pirates."));
Copied!

```

The `VertexAiGeminiChatOptions` provides the configuration information for the
chat requests. The `VertexAiGeminiChatOptions.Builder` is fluent options
builder.

##  Low-level Java Client

Following class diagram illustrates the Vertex AI Gemini native Java API:

Google VertexAI Groq

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

