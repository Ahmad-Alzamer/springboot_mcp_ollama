source URL: https://docs.spring.io/spring-ai/reference/api/chat/deepseek-chat.html 
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

### DeepSeek Chat

  * Prerequisites
  * Add Repositories and BOM
  * Auto-configuration
  * Chat Properties
  * Runtime Options
  * Sample Controller (Auto-configuration)
  * Chat Prefix Completion
  * Reasoning Model (deepseek-reasoner)
  * Reasoning Model Multi-round Conversation
  * Manual Configuration
  * Low-level DeepSeekApi Client

  * Spring AI
  * Reference
  * Models
  * Chat Models
  * DeepSeek

# DeepSeek Chat

### DeepSeek Chat

  * Prerequisites
  * Add Repositories and BOM
  * Auto-configuration
  * Chat Properties
  * Runtime Options
  * Sample Controller (Auto-configuration)
  * Chat Prefix Completion
  * Reasoning Model (deepseek-reasoner)
  * Reasoning Model Multi-round Conversation
  * Manual Configuration
  * Low-level DeepSeekApi Client

Spring AI supports the various AI language models from DeepSeek. You can
interact with DeepSeek language models and create a multilingual conversational
assistant based on DeepSeek models.

##  Prerequisites

You will need to create an API key with DeepSeek to access DeepSeek language
models.

Create an account at DeepSeek registration page and generate a token on the API
Keys page.

The Spring AI project defines a configuration property named
`spring.ai.deepseek.api-key` that you should set to the value of the `API Key`
obtained from the API Keys page.

You can set this configuration property in your `application.properties` file:

```

spring.ai.deepseek.api-key=<your-deepseek-api-key>

Copied!

```

For enhanced security when handling sensitive information like API keys, you can
use Spring Expression Language (SpEL) to reference a custom environment
variable:

```

# In application.yml

spring:

  ai:

    deepseek:
      api-key: ${DEEPSEEK_API_KEY}
Copied!

```

```

# In your environment or .env file

export DEEPSEEK_API_KEY=<your-deepseek-api-key>

Copied!

```

You can also set this configuration programmatically in your application code:

```

// Retrieve API key from a secure source or environment variable

String apiKey = System.getenv("DEEPSEEK_API_KEY");

Copied!

```

###  Add Repositories and BOM

Spring AI artifacts are published in the Spring Milestone and Snapshot
repositories. Refer to the Artifact Repositories section to add these
repositories to your build system.

To help with dependency management, Spring AI provides a BOM (bill of materials)
to ensure that a consistent version of Spring AI is used throughout your entire
project. Refer to the Dependency Management section to add the Spring AI BOM to
your build system.

##  Auto-configuration

Spring AI provides Spring Boot auto-configuration for the DeepSeek Chat Model.
To enable it, add the following dependency to your project’s Maven `pom.xml`
file:

```

<dependency>

    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-starter-model-deepseek</artifactId>
</dependency>

Copied!

```

or to your Gradle `build.gradle` file.

```

dependencies {

    implementation 'org.springframework.ai:spring-ai-starter-model-deepseek'
}

Copied!

```

|  Refer to the Dependency Management section to add the Spring AI BOM to your
build file.  
---|---  
###  Chat Properties

####  Retry Properties

The prefix `spring.ai.retry` is used as the property prefix that lets you
configure the retry mechanism for the DeepSeek Chat model.

Property | Description | Default  
---|---|---  
spring.ai.retry.max-attempts | Maximum number of retry attempts. | 10  
spring.ai.retry.backoff.initial-interval | Initial sleep duration for the exponential backoff policy. | 2 sec.  
spring.ai.retry.backoff.multiplier | Backoff interval multiplier. | 5  
spring.ai.retry.backoff.max-interval | Maximum backoff duration. | 3 min.  
spring.ai.retry.on-client-errors | If false, throws a NonTransientAiException, and does not attempt a retry for `4xx` client error codes | false  
spring.ai.retry.exclude-on-http-codes | List of HTTP status codes that should not trigger a retry (e.g. to throw NonTransientAiException). | empty  
spring.ai.retry.on-http-codes | List of HTTP status codes that should trigger a retry (e.g. to throw TransientAiException). | empty  
####  Connection Properties

The prefix `spring.ai.deepseek` is used as the property prefix that lets you
connect to DeepSeek.

Property | Description | Default  
---|---|---  
spring.ai.deepseek.base-url | The URL to connect to | api.deepseek.com  
spring.ai.deepseek.api-key | The API Key | -  
####  Configuration Properties

The prefix `spring.ai.deepseek.chat` is the property prefix that lets you
configure the chat model implementation for DeepSeek.

Property | Description | Default  
---|---|---  
spring.ai.deepseek.chat.enabled | Enables the DeepSeek chat model. | true  
spring.ai.deepseek.chat.base-url | Optionally overrides the spring.ai.deepseek.base-url to provide a chat-specific URL | api.deepseek.com/  
spring.ai.deepseek.chat.api-key | Optionally overrides the spring.ai.deepseek.api-key to provide a chat-specific API key | -  
spring.ai.deepseek.chat.completions-path | The path to the chat completions endpoint | /chat/completions  
spring.ai.deepseek.chat.beta-prefix-path | The prefix path to the beta feature endpoint | /beta/chat/completions  
spring.ai.deepseek.chat.options.model | ID of the model to use. You can use either deepseek-coder or deepseek-chat. | deepseek-chat  
spring.ai.deepseek.chat.options.frequencyPenalty | Number between -2.0 and 2.0. Positive values penalize new tokens based on their existing frequency in the text so far, decreasing the model’s likelihood to repeat the same line verbatim. | 0.0f  
spring.ai.deepseek.chat.options.maxTokens | The maximum number of tokens to generate in the chat completion. The total length of input tokens and generated tokens is limited by the model’s context length. | -  
spring.ai.deepseek.chat.options.presencePenalty | Number between -2.0 and 2.0. Positive values penalize new tokens based on whether they appear in the text so far, increasing the model’s likelihood to talk about new topics. | 0.0f  
spring.ai.deepseek.chat.options.stop | Up to 4 sequences where the API will stop generating further tokens. | -  
spring.ai.deepseek.chat.options.temperature | Which sampling temperature to use, between 0 and 2. Higher values like 0.8 will make the output more random, while lower values like 0.2 will make it more focused and deterministic. We generally recommend altering this or top_p, but not both. | 1.0F  
spring.ai.deepseek.chat.options.topP | An alternative to sampling with temperature, called nucleus sampling, where the model considers the results of the tokens with top_p probability mass. So 0.1 means only the tokens comprising the top 10% probability mass are considered. We generally recommend altering this or temperature, but not both. | 1.0F  
spring.ai.deepseek.chat.options.logprobs | Whether to return log probabilities of the output tokens or not. If true, returns the log probabilities of each output token returned in the content of the message. | -  
spring.ai.deepseek.chat.options.topLogprobs | An integer between 0 and 20 specifying the number of most likely tokens to return at each token position, each with an associated log probability. logprobs must be set to true if this parameter is used. | -  
|  You can override the common `spring.ai.deepseek.base-url` and
`spring.ai.deepseek.api-key` for the `ChatModel` implementations. The
`spring.ai.deepseek.chat.base-url` and `spring.ai.deepseek.chat.api-key`
properties, if set, take precedence over the common properties. This is useful
if you want to use different DeepSeek accounts for different models and
different model endpoints.  
---|---  
|  All properties prefixed with `spring.ai.deepseek.chat.options` can be
overridden at runtime by adding a request-specific Runtime Options to the
`Prompt` call.  
---|---  
##  Runtime Options

The DeepSeekChatOptions.java provides model configurations, such as the model to
use, the temperature, the frequency penalty, etc.

On startup, the default options can be configured with the
`DeepSeekChatModel(api, options)` constructor or the
`spring.ai.deepseek.chat.options.*` properties.

At runtime, you can override the default options by adding new, request-specific
options to the `Prompt` call. For example, to override the default model and
temperature for a specific request:

```

ChatResponse response = chatModel.call(

    new Prompt(
        "Generate the names of 5 famous pirates. Please provide the JSON response without any code block markers such as ```json```.",
        DeepSeekChatOptions.builder()
            .withModel(DeepSeekApi.ChatModel.DEEPSEEK_CHAT.getValue())
            .withTemperature(0.8f)
        .build()
    ));
Copied!

```

|  In addition to the model-specific DeepSeekChatOptions, you can use a portable
ChatOptions instance, created with the ChatOptionsBuilder#builder().  
---|---  
##  Sample Controller (Auto-configuration)

Create a new Spring Boot project and add the `spring-ai-starter-model-deepseek`
to your pom (or gradle) dependencies.

Add an `application.properties` file under the `src/main/resources` directory to
enable and configure the DeepSeek Chat model:

```

spring.ai.deepseek.api-key=YOUR_API_KEY

spring.ai.deepseek.chat.options.model=deepseek-chat

spring.ai.deepseek.chat.options.temperature=0.8

Copied!

```

|  Replace the `api-key` with your DeepSeek credentials.  
---|---  
This will create a `DeepSeekChatModel` implementation that you can inject into
your class. Here is an example of a simple `@Controller` class that uses the
chat model for text generation.

```

@RestController

public class ChatController {

    private final DeepSeekChatModel chatModel;

    @Autowired
    public ChatController(DeepSeekChatModel chatModel) {
        this.chatModel = chatModel;
    }

    @GetMapping("/ai/generate")
    public Map generate(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
        return Map.of("generation", chatModel.call(message));
    }

    @GetMapping("/ai/generateStream")
	public Flux<ChatResponse> generateStream(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
        var prompt = new Prompt(new UserMessage(message));
        return chatModel.stream(prompt);
    }
}

Copied!

```

##  Chat Prefix Completion

The chat prefix completion follows the Chat Completion API, where users provide
an assistant’s prefix message for the model to complete the rest of the message.

When using prefix completion, the user must ensure that the last message in the
messages list is a DeepSeekAssistantMessage.

Below is a complete Java code example for chat prefix completion. In this
example, we set the prefix message of the assistant to "```python\n" to force
the model to output Python code, and set the stop parameter to [‘`’] to prevent
additional explanations from the model.

```

@RestController

public class CodeGenerateController {

    private final DeepSeekChatModel chatModel;

    @Autowired
    public ChatController(DeepSeekChatModel chatModel) {
        this.chatModel = chatModel;
    }

    @GetMapping("/ai/generatePythonCode")
    public String generate(@RequestParam(value = "message", defaultValue = "Please write quick sort code") String message) {
		UserMessage userMessage = new UserMessage(message);
		Message assistantMessage = DeepSeekAssistantMessage.prefixAssistantMessage("```python\\n");
		Prompt prompt = new Prompt(List.of(userMessage, assistantMessage), ChatOptions.builder().stopSequences(List.of("```")).build());
		ChatResponse response = chatModel.call(prompt);
		return response.getResult().getOutput().getText();
    }
}

Copied!

```

##  Reasoning Model (deepseek-reasoner)

The `deepseek-reasoner` is a reasoning model developed by DeepSeek. Before
delivering the final answer, the model first generates a Chain of Thought (CoT)
to enhance the accuracy of its responses. Our API provides users with access to
the CoT content generated by `deepseek-reasoner`, enabling them to view,
display, and distill it.

You can use the `DeepSeekAssistantMessage` to get the CoT content generated by
`deepseek-reasoner`.

```

public void deepSeekReasonerExample() {

    DeepSeekChatOptions promptOptions = DeepSeekChatOptions.builder()
            .model(DeepSeekApi.ChatModel.DEEPSEEK_REASONER.getValue())
            .build();
    Prompt prompt = new Prompt("9.11 and 9.8, which is greater?", promptOptions);
    ChatResponse response = chatModel.call(prompt);

    // Get the CoT content generated by deepseek-reasoner, only available when using deepseek-reasoner model
    DeepSeekAssistantMessage deepSeekAssistantMessage = (DeepSeekAssistantMessage) response.getResult().getOutput();
    String reasoningContent = deepSeekAssistantMessage.getReasoningContent();
    String text = deepSeekAssistantMessage.getText();
}

Copied!

```

##  Reasoning Model Multi-round Conversation

In each round of the conversation, the model outputs the CoT (reasoning_content)
and the final answer (content). In the next round of the conversation, the CoT
from previous rounds is not concatenated into the context, as illustrated in the
following diagram:

Please note that if the reasoning_content field is included in the sequence of
input messages, the API will return a 400 error. Therefore, you should remove
the reasoning_content field from the API response before making the API request,
as demonstrated in the API example.

```

public String deepSeekReasonerMultiRoundExample() {

    List<Message> messages = new ArrayList<>();
    messages.add(new UserMessage("9.11 and 9.8, which is greater?"));
    DeepSeekChatOptions promptOptions = DeepSeekChatOptions.builder()
            .model(DeepSeekApi.ChatModel.DEEPSEEK_REASONER.getValue())
            .build();

    Prompt prompt = new Prompt(messages, promptOptions);
    ChatResponse response = chatModel.call(prompt);

    DeepSeekAssistantMessage deepSeekAssistantMessage = (DeepSeekAssistantMessage) response.getResult().getOutput();
    String reasoningContent = deepSeekAssistantMessage.getReasoningContent();
    String text = deepSeekAssistantMessage.getText();

    messages.add(new AssistantMessage(Objects.requireNonNull(text)));
    messages.add(new UserMessage("How many Rs are there in the word 'strawberry'?"));
    Prompt prompt2 = new Prompt(messages, promptOptions);
    ChatResponse response2 = chatModel.call(prompt2);

    DeepSeekAssistantMessage deepSeekAssistantMessage2 = (DeepSeekAssistantMessage) response2.getResult().getOutput();
    String reasoningContent2 = deepSeekAssistantMessage2.getReasoningContent();
    return deepSeekAssistantMessage2.getText();
}

Copied!

```

##  Manual Configuration

The DeepSeekChatModel implements the `ChatModel` and `StreamingChatModel` and
uses the Low-level DeepSeekApi Client to connect to the DeepSeek service.

Add the `spring-ai-deepseek` dependency to your project’s Maven `pom.xml` file:

```

<dependency>

    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-deepseek</artifactId>
</dependency>

Copied!

```

or to your Gradle `build.gradle` file.

```

dependencies {

    implementation 'org.springframework.ai:spring-ai-deepseek'
}

Copied!

```

|  Refer to the Dependency Management section to add the Spring AI BOM to your
build file.  
---|---  
Next, create a `DeepSeekChatModel` and use it for text generation:

```

var deepSeekApi = new DeepSeekApi(System.getenv("DEEPSEEK_API_KEY"));

var chatModel = new DeepSeekChatModel(deepSeekApi, DeepSeekChatOptions.builder()

                .withModel(DeepSeekApi.ChatModel.DEEPSEEK_CHAT.getValue())
                .withTemperature(0.4f)
                .withMaxTokens(200)
                .build());

ChatResponse response = chatModel.call(

    new Prompt("Generate the names of 5 famous pirates."));

// Or with streaming responses

Flux<ChatResponse> streamResponse = chatModel.stream(

    new Prompt("Generate the names of 5 famous pirates."));
Copied!

```

The `DeepSeekChatOptions` provides the configuration information for the chat
requests. The `DeepSeekChatOptions.Builder` is a fluent options builder.

###  Low-level DeepSeekApi Client

The DeepSeekApi is a lightweight Java client for DeepSeek API.

Here is a simple snippet showing how to use the API programmatically:

```

DeepSeekApi deepSeekApi =

    new DeepSeekApi(System.getenv("DEEPSEEK_API_KEY"));

ChatCompletionMessage chatCompletionMessage =

    new ChatCompletionMessage("Hello world", Role.USER);

// Sync request

ResponseEntity<ChatCompletion> response = deepSeekApi.chatCompletionEntity(

    new ChatCompletionRequest(List.of(chatCompletionMessage), DeepSeekApi.ChatModel.DEEPSEEK_CHAT.getValue(), 0.7f, false));

// Streaming request

Flux<ChatCompletionChunk> streamResponse = deepSeekApi.chatCompletionStream(

        new ChatCompletionRequest(List.of(chatCompletionMessage), DeepSeekApi.ChatModel.DEEPSEEK_CHAT.getValue(), 0.7f, true));
Copied!

```

Follow the DeepSeekApi.java's JavaDoc for further information.

####  DeepSeekApi Samples

  * The DeepSeekApiIT.java test provides some general examples of how to use the lightweight library.

Azure OpenAI Docker Model Runner

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

