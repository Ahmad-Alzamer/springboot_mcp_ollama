source URL: https://docs.spring.io/spring-ai/reference/api/chatmodel.html 
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

### Chat Model API

  * API Overview
  * ChatModel
  * StreamingChatModel
  * Prompt
  * ChatResponse
  * Generation
  * Available Implementations
  * Chat Model API

  * Spring AI
  * Reference
  * Models
  * Chat Models

# Chat Model API

### Chat Model API

  * API Overview
  * ChatModel
  * StreamingChatModel
  * Prompt
  * ChatResponse
  * Generation
  * Available Implementations
  * Chat Model API

The Chat Model API offers developers the ability to integrate AI-powered chat
completion capabilities into their applications. It leverages pre-trained
language models, such as GPT (Generative Pre-trained Transformer), to generate
human-like responses to user inputs in natural language.

The API typically works by sending a prompt or partial conversation to the AI
model, which then generates a completion or continuation of the conversation
based on its training data and understanding of natural language patterns. The
completed response is then returned to the application, which can present it to
the user or use it for further processing.

The `Spring AI Chat Model API` is designed to be a simple and portable interface
for interacting with various AI Models, allowing developers to switch between
different models with minimal code changes. This design aligns with Spring’s
philosophy of modularity and interchangeability.

Also with the help of companion classes like `Prompt` for input encapsulation
and `ChatResponse` for output handling, the Chat Model API unifies the
communication with AI Models. It manages the complexity of request preparation
and response parsing, offering a direct and simplified API interaction.

You can find more about available implementations in the Available
Implementations section as well as detailed comparison in the Chat Models
Comparison section.

##  API Overview

This section provides a guide to the Spring AI Chat Model API interface and
associated classes.

###  ChatModel

Here is the ChatModel interface definition:

```

public interface ChatModel extends Model<Prompt, ChatResponse> {

	default String call(String message) {...}

    @Override
	ChatResponse call(Prompt prompt);
}

Copied!

```

The `call()` method with a `String` parameter simplifies initial use, avoiding
the complexities of the more sophisticated `Prompt` and `ChatResponse` classes.
In real-world applications, it is more common to use the `call()` method that
takes a `Prompt` instance and returns a `ChatResponse`.

###  StreamingChatModel

Here is the StreamingChatModel interface definition:

```

public interface StreamingChatModel extends StreamingModel<Prompt, ChatResponse>
{

    default Flux<String> stream(String message) {...}

    @Override
	Flux<ChatResponse> stream(Prompt prompt);
}

Copied!

```

The `stream()` method takes a `String` or `Prompt` parameter similar to
`ChatModel` but it streams the responses using the reactive Flux API.

###  Prompt

The Prompt is a `ModelRequest` that encapsulates a list of Message objects and
optional model request options. The following listing shows a truncated version
of the `Prompt` class, excluding constructors and other utility methods:

```

public class Prompt implements ModelRequest<List<Message>> {

    private final List<Message> messages;

    private ChatOptions modelOptions;

	@Override
	public ChatOptions getOptions() {...}

	@Override
	public List<Message> getInstructions() {...}

    // constructors and utility methods omitted
}

Copied!

```

####  Message

The `Message` interface encapsulates a `Prompt` textual content, a collection of
metadata attributes, and a categorization known as `MessageType`.

The interface is defined as follows:

```

public interface Content {

	String getText();

	Map<String, Object> getMetadata();
}

public interface Message extends Content {

	MessageType getMessageType();
}

Copied!

```

The multimodal message types implement also the `MediaContent` interface
providing a list of `Media` content objects.

```

public interface MediaContent extends Content {

	Collection<Media> getMedia();

}

Copied!

```

The `Message` interface has various implementations that correspond to the
categories of messages that an AI model can process:

The chat completion endpoint, distinguish between message categories based on
conversational roles, effectively mapped by the `MessageType`.

For instance, OpenAI recognizes message categories for distinct conversational
roles such as `system`, `user`, `function`, or `assistant`.

While the term `MessageType` might imply a specific message format, in this
context it effectively designates the role a message plays in the dialogue.

For AI models that do not use specific roles, the `UserMessage` implementation
acts as a standard category, typically representing user-generated inquiries or
instructions. To understand the practical application and the relationship
between `Prompt` and `Message`, especially in the context of these roles or
message categories, see the detailed explanations in the Prompts section.

####  Chat Options

Represents the options that can be passed to the AI model. The `ChatOptions`
class is a subclass of `ModelOptions` and is used to define few portable options
that can be passed to the AI model. The `ChatOptions` class is defined as
follows:

```

public interface ChatOptions extends ModelOptions {

	String getModel();
	Float getFrequencyPenalty();
	Integer getMaxTokens();
	Float getPresencePenalty();
	List<String> getStopSequences();
	Float getTemperature();
	Integer getTopK();
	Float getTopP();
	ChatOptions copy();

}

Copied!

```

Additionally, every model specific ChatModel/StreamingChatModel implementation
can have its own options that can be passed to the AI model. For example, the
OpenAI Chat Completion model has its own options like `logitBias`, `seed`, and
`user`.

This is a powerful feature that allows developers to use model-specific options
when starting the application and then override them at runtime using the
`Prompt` request.

Spring AI provides a sophisticated system for configuring and using Chat Models.
It allows for default configurations to be set at start-up, while also providing
the flexibility to override these settings on a per-request basis. This approach
enables developers to easily work with different AI models and adjust parameters
as needed, all within a consistent interface provided by the Spring AI
framework.

Following flow diagram illustrates how Spring AI handles the configuration and
execution of Chat Models, combining start-up and runtime options:

  1. Start-up Configuration - The ChatModel/StreamingChatModel is initialized with "Start-Up" Chat Options. These options are set during the ChatModel initialization and are meant to provide default configurations.
  2. Runtime Configuration - For each request, the Prompt can contain a Runtime Chat Options: These can override the start-up options.
  3. Option Merging Process - The "Merge Options" step combines the start-up and runtime options. If runtime options are provided, they take precedence over the start-up options.
  4. Input Processing - The "Convert Input" step transforms the input instructions into native, model-specific formats.
  5. Output Processing - The "Convert Output" step transforms the model’s response into a standardized `ChatResponse` format.

The separation of start-up and runtime options allows for both global
configurations and request-specific adjustments.

###  ChatResponse

The structure of the `ChatResponse` class is as follows:

```

public class ChatResponse implements ModelResponse<Generation> {

    private final ChatResponseMetadata chatResponseMetadata;
	private final List<Generation> generations;

	@Override
	public ChatResponseMetadata getMetadata() {...}

    @Override
	public List<Generation> getResults() {...}

    // other methods omitted
}

Copied!

```

The ChatResponse class holds the AI Model’s output, with each `Generation`
instance containing one of potentially multiple outputs resulting from a single
prompt.

The `ChatResponse` class also carries a `ChatResponseMetadata` metadata about
the AI Model’s response.

###  Generation

Finally, the Generation class extends from the `ModelResult` to represent the
model output (assistant message) and related metadata:

```

public class Generation implements ModelResult<AssistantMessage> {

	private final AssistantMessage assistantMessage;
	private ChatGenerationMetadata chatGenerationMetadata;

	@Override
	public AssistantMessage getOutput() {...}

	@Override
	public ChatGenerationMetadata getMetadata() {...}

    // other methods omitted
}

Copied!

```

##  Available Implementations

This diagram illustrates the unified interfaces, `ChatModel` and
`StreamingChatModel`, are used for interacting with various AI chat models from
different providers, allowing easy integration and switching between different
AI services while maintaining a consistent API for the client application.

  * OpenAI Chat Completion (streaming, multi-modality & function-calling support)
  * Microsoft Azure Open AI Chat Completion (streaming & function-calling support)
  * Ollama Chat Completion (streaming, multi-modality & function-calling support)
  * Hugging Face Chat Completion (no streaming support)
  * Google Vertex AI Gemini Chat Completion (streaming, multi-modality & function-calling support)
  * Amazon Bedrock
  * Mistral AI Chat Completion (streaming & function-calling support)
  * Anthropic Chat Completion (streaming & function-calling support)

|  Find a detailed comparison of the available Chat Models in the Chat Models
Comparison section.  
---|---  
##  Chat Model API

The Spring AI Chat Model API is built on top of the Spring AI `Generic Model
API` providing Chat specific abstractions and implementations. This allows an
easy integration and switching between different AI services while maintaining a
consistent API for the client application. The following class diagram
illustrates the main classes and interfaces of the Spring AI Chat Model API.

Models Chat Models Comparison

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

