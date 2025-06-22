source URL: https://docs.spring.io/spring-ai/reference/api/chat/mistralai-chat.html 
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

### Mistral AI Chat

  * Prerequisites
  * Add Repositories and BOM
  * Auto-configuration
  * Chat Properties
  * Runtime Options
  * Function Calling
  * Multimodal
  * Vision
  * OpenAI API Compatibility
  * Sample Controller (Auto-configuration)
  * Manual Configuration
  * Low-level MistralAiApi Client

  * Spring AI
  * Reference
  * Models
  * Chat Models
  * Mistral AI

# Mistral AI Chat

### Mistral AI Chat

  * Prerequisites
  * Add Repositories and BOM
  * Auto-configuration
  * Chat Properties
  * Runtime Options
  * Function Calling
  * Multimodal
  * Vision
  * OpenAI API Compatibility
  * Sample Controller (Auto-configuration)
  * Manual Configuration
  * Low-level MistralAiApi Client

Spring AI supports the various AI language models from Mistral AI. You can
interact with Mistral AI language models and create a multilingual
conversational assistant based on Mistral models.

|  Mistral AI offers an OpenAI API-compatible endpoint as well. Check the OpenAI
API compatibility section to learn how to use the Spring AI OpenAI integration
to talk to a Mistral endpoint.  
---|---  
##  Prerequisites

You will need to create an API with Mistral AI to access Mistral AI language
models.

Create an account at Mistral AI registration page and generate the token on the
API Keys page.

The Spring AI project defines a configuration property named
`spring.ai.mistralai.api-key` that you should set to the value of the `API Key`
obtained from console.mistral.ai.

You can set this configuration property in your `application.properties` file:

```

spring.ai.mistralai.api-key=<your-mistralai-api-key>

Copied!

```

For enhanced security when handling sensitive information like API keys, you can
use Spring Expression Language (SpEL) to reference a custom environment
variable:

```

# In application.yml

spring:

  ai:

    mistralai:
      api-key: ${MISTRALAI_API_KEY}
Copied!

```

```

# In your environment or .env file

export MISTRALAI_API_KEY=<your-mistralai-api-key>

Copied!

```

You can also set this configuration programmatically in your application code:

```

// Retrieve API key from a secure source or environment variable

String apiKey = System.getenv("MISTRALAI_API_KEY");

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
Spring AI provides Spring Boot auto-configuration for the Mistral AI Chat
Client. To enable it add the following dependency to your project’s Maven
`pom.xml` file:

```

<dependency>

    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-starter-model-mistral-ai</artifactId>
</dependency>

Copied!

```

or to your Gradle `build.gradle` build file.

```

dependencies {

    implementation 'org.springframework.ai:spring-ai-starter-model-mistral-ai'
}

Copied!

```

|  Refer to the Dependency Management section to add the Spring AI BOM to your
build file.  
---|---  
###  Chat Properties

####  Retry Properties

The prefix `spring.ai.retry` is used as the property prefix that lets you
configure the retry mechanism for the Mistral AI chat model.

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

The prefix `spring.ai.mistralai` is used as the property prefix that lets you
connect to OpenAI.

Property | Description | Default  
---|---|---  
spring.ai.mistralai.base-url | The URL to connect to | api.mistral.ai  
spring.ai.mistralai.api-key | The API Key | -  
####  Configuration Properties

|  Enabling and disabling of the chat auto-configurations are now configured via
top level properties with the prefix `spring.ai.model.chat`. To enable,
spring.ai.model.chat=mistral (It is enabled by default) To disable,
spring.ai.model.chat=none (or any value which doesn’t match mistral) This change
is done to allow configuration of multiple models.  
---|---  
The prefix `spring.ai.mistralai.chat` is the property prefix that lets you
configure the chat model implementation for Mistral AI.

Property | Description | Default  
---|---|---  
spring.ai.mistralai.chat.enabled (Removed and no longer valid) | Enable Mistral AI chat model. | true  
spring.ai.model.chat | Enable Mistral AI chat model. | mistral  
spring.ai.mistralai.chat.base-url | Optional override for the `spring.ai.mistralai.base-url` property to provide chat-specific URL. | -  
spring.ai.mistralai.chat.api-key | Optional override for the `spring.ai.mistralai.api-key` to provide chat-specific API Key. | -  
spring.ai.mistralai.chat.options.model | This is the Mistral AI Chat model to use | `open-mistral-7b`, `open-mixtral-8x7b`, `open-mixtral-8x22b`, `mistral-small-latest`, `mistral-large-latest`  
spring.ai.mistralai.chat.options.temperature | The sampling temperature to use that controls the apparent creativity of generated completions. Higher values will make output more random while lower values will make results more focused and deterministic. It is not recommended to modify `temperature` and `top_p` for the same completions request as the interaction of these two settings is difficult to predict. | 0.8  
spring.ai.mistralai.chat.options.maxTokens | The maximum number of tokens to generate in the chat completion. The total length of input tokens and generated tokens is limited by the model’s context length. | -  
spring.ai.mistralai.chat.options.safePrompt | Indicates whether to inject a security prompt before all conversations. | false  
spring.ai.mistralai.chat.options.randomSeed | This feature is in Beta. If specified, our system will make a best effort to sample deterministically, such that repeated requests with the same seed and parameters should return the same result. | -  
spring.ai.mistralai.chat.options.stop | Stop generation if this token is detected. Or if one of these tokens is detected when providing an array. | -  
spring.ai.mistralai.chat.options.topP | An alternative to sampling with temperature, called nucleus sampling, where the model considers the results of the tokens with top_p probability mass. So 0.1 means only the tokens comprising the top 10% probability mass are considered. We generally recommend altering this or `temperature` but not both. | -  
spring.ai.mistralai.chat.options.responseFormat | An object specifying the format that the model must output. Setting to `{ "type": "json_object" }` enables JSON mode, which guarantees the message the model generates is valid JSON. | -  
spring.ai.mistralai.chat.options.tools | A list of tools the model may call. Currently, only functions are supported as a tool. Use this to provide a list of functions the model may generate JSON inputs for. | -  
spring.ai.mistralai.chat.options.toolChoice | Controls which (if any) function is called by the model. `none` means the model will not call a function and instead generates a message. `auto` means the model can pick between generating a message or calling a function. Specifying a particular function via `{"type: "function", "function": {"name": "my_function"}}` forces the model to call that function. `none` is the default when no functions are present. `auto` is the default if functions are present. | -  
spring.ai.mistralai.chat.options.functions | List of functions, identified by their names, to enable for function calling in a single prompt requests. Functions with those names must exist in the functionCallbacks registry. | -  
spring.ai.mistralai.chat.options.functionCallbacks | Mistral AI Tool Function Callbacks to register with the ChatModel. | -  
spring.ai.mistralai.chat.options.proxy-tool-calls | If true, the Spring AI will not handle the function calls internally, but will proxy them to the client. Then is the client’s responsibility to handle the function calls, dispatch them to the appropriate function, and return the results. If false (the default), the Spring AI will handle the function calls internally. Applicable only for chat models with function calling support | false  
|  You can override the common `spring.ai.mistralai.base-url` and
`spring.ai.mistralai.api-key` for the `ChatModel` and `EmbeddingModel`
implementations. The `spring.ai.mistralai.chat.base-url` and
`spring.ai.mistralai.chat.api-key` properties, if set, take precedence over the
common properties. This is useful if you want to use different Mistral AI
accounts for different models and different model endpoints.  
---|---  
|  All properties prefixed with `spring.ai.mistralai.chat.options` can be
overridden at runtime by adding request-specific Runtime Options to the `Prompt`
call.  
---|---  
##  Runtime Options

The MistralAiChatOptions.java provides model configurations, such as the model
to use, the temperature, the frequency penalty, etc.

On start-up, the default options can be configured with the
`MistralAiChatModel(api, options)` constructor or the
`spring.ai.mistralai.chat.options.*` properties.

At run-time, you can override the default options by adding new, request-
specific options to the `Prompt` call. For example, to override the default
model and temperature for a specific request:

```

ChatResponse response = chatModel.call(

    new Prompt(
        "Generate the names of 5 famous pirates.",
        MistralAiChatOptions.builder()
            .model(MistralAiApi.ChatModel.LARGE.getValue())
            .temperature(0.5)
        .build()
    ));
Copied!

```

|  In addition to the model specific MistralAiChatOptions you can use a portable
ChatOptions instance, created with ChatOptionsBuilder#builder().  
---|---  
##  Function Calling

You can register custom Java functions with the `MistralAiChatModel` and have
the Mistral AI model intelligently choose to output a JSON object containing
arguments to call one or many of the registered functions. This is a powerful
technique to connect the LLM capabilities with external tools and APIs. Read
more about Tool Calling.

##  Multimodal

Multimodality refers to a model’s ability to simultaneously understand and
process information from various sources, including text, images, audio, and
other data formats. Mistral AI supports text and vision modalities.

###  Vision

Mistral AI models that offer vision multimodal support include `pixtral-large-
latest`. Refer to the Vision guide for more information.

The Mistral AI User Message API can incorporate a list of base64-encoded images
or image urls with the message. Spring AI’s Message interface facilitates
multimodal AI models by introducing the Media type. This type encompasses data
and details regarding media attachments in messages, utilizing Spring’s
`org.springframework.util.MimeType` and a `org.springframework.core.io.Resource`
for the raw media data.

Below is a code example excerpted from `MistralAiChatModelIT.java`, illustrating
the fusion of user text with an image.

```

var imageResource = new ClassPathResource("/multimodal.test.png");

var userMessage = new UserMessage("Explain what do you see on this picture?",

        new Media(MimeTypeUtils.IMAGE_PNG, this.imageResource));

ChatResponse response = chatModel.call(new Prompt(this.userMessage,

        ChatOptions.builder().model(MistralAiApi.ChatModel.PIXTRAL_LARGE.getValue()).build()));
Copied!

```

or the image URL equivalent:

```

var userMessage = new UserMessage("Explain what do you see on this picture?",

        new Media(MimeTypeUtils.IMAGE_PNG,
                URI.create("https://docs.spring.io/spring-ai/reference/_images/multimodal.test.png")));

ChatResponse response = chatModel.call(new Prompt(this.userMessage,

        ChatOptions.builder().model(MistralAiApi.ChatModel.PIXTRAL_LARGE.getValue()).build()));
Copied!

```

|  You can pass multiple images as well.  
---|---  
The example shows a model taking as an input the `multimodal.test.png` image:

along with the text message "Explain what do you see on this picture?", and
generating a response like this:

```

This is an image of a fruit bowl with a simple design. The bowl is made of metal
with curved wire edges that

create an open structure, allowing the fruit to be visible from all angles.
Inside the bowl, there are two

yellow bananas resting on top of what appears to be a red apple. The bananas are
slightly overripe, as

indicated by the brown spots on their peels. The bowl has a metal ring at the
top, likely to serve as a handle

for carrying. The bowl is placed on a flat surface with a neutral-colored
background that provides a clear

view of the fruit inside.

```

##  OpenAI API Compatibility

Mistral is OpenAI API-compatible and you can use the Spring AI OpenAI client to
talk to Mistrial. For this, you need to configure the OpenAI base URL to the
Mistral AI platform: `spring.ai.openai.chat.base-url=https://api.mistral.ai`,
and select a Mistral model: `spring.ai.openai.chat.options.model=mistral-small-
latest` and set the Mistral AI API key: `spring.ai.openai.chat.api-key=<YOUR
MISTRAL API KEY`.

Check the MistralWithOpenAiChatModelIT.java tests for examples of using Mistral
over Spring AI OpenAI.

##  Sample Controller (Auto-configuration)

Create a new Spring Boot project and add the `spring-ai-starter-model-mistral-
ai` to your pom (or gradle) dependencies.

Add a `application.properties` file under the `src/main/resources` directory to
enable and configure the Mistral AI chat model:

```

spring.ai.mistralai.api-key=YOUR_API_KEY

spring.ai.mistralai.chat.options.model=mistral-small

spring.ai.mistralai.chat.options.temperature=0.7

Copied!

```

|  Replace the `api-key` with your Mistral AI credentials.  
---|---  
This will create a `MistralAiChatModel` implementation that you can inject into
your classes. Here is an example of a simple `@RestController` class that uses
the chat model for text generations.

```

@RestController

public class ChatController {

    private final MistralAiChatModel chatModel;

    @Autowired
    public ChatController(MistralAiChatModel chatModel) {
        this.chatModel = chatModel;
    }

    @GetMapping("/ai/generate")
    public Map<String,String> generate(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
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

The MistralAiChatModel implements the `ChatModel` and `StreamingChatModel` and
uses the Low-level MistralAiApi Client to connect to the Mistral AI service.

Add the `spring-ai-mistral-ai` dependency to your project’s Maven `pom.xml`
file:

```

<dependency>

    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-mistral-ai</artifactId>
</dependency>

Copied!

```

or to your Gradle `build.gradle` build file.

```

dependencies {

    implementation 'org.springframework.ai:spring-ai-mistral-ai'
}

Copied!

```

|  Refer to the Dependency Management section to add the Spring AI BOM to your
build file.  
---|---  
Next, create a `MistralAiChatModel` and use it for text generations:

```

var mistralAiApi = new MistralAiApi(System.getenv("MISTRAL_AI_API_KEY"));

var chatModel = new MistralAiChatModel(this.mistralAiApi,
MistralAiChatOptions.builder()

                .model(MistralAiApi.ChatModel.LARGE.getValue())
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

The `MistralAiChatOptions` provides the configuration information for the chat
requests. The `MistralAiChatOptions.Builder` is a fluent options-builder.

###  Low-level MistralAiApi Client

The MistralAiApi provides is lightweight Java client for Mistral AI API.

Here is a simple snippet showing how to use the API programmatically:

```

MistralAiApi mistralAiApi = new
MistralAiApi(System.getenv("MISTRAL_AI_API_KEY"));

ChatCompletionMessage chatCompletionMessage =

    new ChatCompletionMessage("Hello world", Role.USER);

// Sync request

ResponseEntity<ChatCompletion> response =
this.mistralAiApi.chatCompletionEntity(

    new ChatCompletionRequest(List.of(this.chatCompletionMessage), MistralAiApi.ChatModel.LARGE.getValue(), 0.8, false));

// Streaming request

Flux<ChatCompletionChunk> streamResponse =
this.mistralAiApi.chatCompletionStream(

        new ChatCompletionRequest(List.of(this.chatCompletionMessage), MistralAiApi.ChatModel.LARGE.getValue(), 0.8, true));
Copied!

```

Follow the MistralAiApi.java's JavaDoc for further information.

####  MistralAiApi Samples

  * The MistralAiApiIT.java tests provide some general examples of how to use the lightweight library.
  * The PaymentStatusFunctionCallingIT.java tests show how to use the low-level API to call tool functions. Based on the Mistral AI Function Calling tutorial.

Hugging Face MiniMax

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

