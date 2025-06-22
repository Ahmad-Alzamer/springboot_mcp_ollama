source URL: https://docs.spring.io/spring-ai/reference/upgrade-notes.html 
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

### Upgrade Notes

  * Upgrading to 1.0.0-SNAPSHOT
  * Overview
  * Add Snapshot Repositories
  * Update Dependency Management
  * Artifact ID, Package, and Module Changes
  * Upgrading to 1.0.0-RC1
  * Breaking Changes
  * Behavior Changes
  * General Cleanup
  * Upgrading to 1.0.0-M8
  * Breaking Changes
  * Removed Implementations and APIs
  * Behavior Changes
  * General Cleanup
  * Upgrading to 1.0.0-M7
  * Overview of Changes
  * Artifact ID, Package, and Module Changes
  * MCP Java SDK Upgrade to 0.9.0
  * Enabling/Disabling Model Auto-Configuration
  * Automating upgrading using AI
  * Common Changes Across Versions
  * Artifact ID Changes
  * Package Name Changes
  * Module Structure
  * Dependency Structure
  * ToolContext Changes
  * Upgrading to 1.0.0-M6
  * Changes to Usage Interface and DefaultUsage Implementation
  * Changes to usage of FunctionCallingOptions for tool calling
  * Removal of deprecated Amazon Bedrock chat models
  * Changes to use Spring Boot 3.4.2 for dependency management
  * Vector Store API changes
  * Upgrading to 1.0.0.M5
  * Upgrading to 1.0.0.RC3
  * Upgrading to 1.0.0.M2
  * Upgrading to 1.0.0.M1
  * ChatClient changes
  * Artifact name changes
  * Upgrading to 0.8.1
  * Upgrading to 0.8.0
  * January 24, 2024 Update
  * January 13, 2024 Update
  * December 27, 2023 Update
  * December 20, 2023 Update
  * December 19, 2023 Update
  * December 1, 2023
  * 0.7.1-SNAPSHOT Dependencies

  * Spring AI
  * Upgrade Notes

# Upgrade Notes

### Upgrade Notes

  * Upgrading to 1.0.0-SNAPSHOT
  * Overview
  * Add Snapshot Repositories
  * Update Dependency Management
  * Artifact ID, Package, and Module Changes
  * Upgrading to 1.0.0-RC1
  * Breaking Changes
  * Behavior Changes
  * General Cleanup
  * Upgrading to 1.0.0-M8
  * Breaking Changes
  * Removed Implementations and APIs
  * Behavior Changes
  * General Cleanup
  * Upgrading to 1.0.0-M7
  * Overview of Changes
  * Artifact ID, Package, and Module Changes
  * MCP Java SDK Upgrade to 0.9.0
  * Enabling/Disabling Model Auto-Configuration
  * Automating upgrading using AI
  * Common Changes Across Versions
  * Artifact ID Changes
  * Package Name Changes
  * Module Structure
  * Dependency Structure
  * ToolContext Changes
  * Upgrading to 1.0.0-M6
  * Changes to Usage Interface and DefaultUsage Implementation
  * Changes to usage of FunctionCallingOptions for tool calling
  * Removal of deprecated Amazon Bedrock chat models
  * Changes to use Spring Boot 3.4.2 for dependency management
  * Vector Store API changes
  * Upgrading to 1.0.0.M5
  * Upgrading to 1.0.0.RC3
  * Upgrading to 1.0.0.M2
  * Upgrading to 1.0.0.M1
  * ChatClient changes
  * Artifact name changes
  * Upgrading to 0.8.1
  * Upgrading to 0.8.0
  * January 24, 2024 Update
  * January 13, 2024 Update
  * December 27, 2023 Update
  * December 20, 2023 Update
  * December 19, 2023 Update
  * December 1, 2023
  * 0.7.1-SNAPSHOT Dependencies

##  Upgrading to 1.0.0-SNAPSHOT

###  Overview

The 1.0.0-SNAPSHOT version includes significant changes to artifact IDs, package
names, and module structure. This section provides guidance specific to using
the SNAPSHOT version.

###  Add Snapshot Repositories

To use the 1.0.0-SNAPSHOT version, you need to add the snapshot repositories to
your build file. For detailed instructions, refer to the Snapshots - Add
Snapshot Repositories section in the Getting Started guide.

###  Update Dependency Management

Update your Spring AI BOM version to `1.0.0-SNAPSHOT` in your build
configuration. For detailed instructions on configuring dependency management,
refer to the Dependency Management section in the Getting Started guide.

###  Artifact ID, Package, and Module Changes

The 1.0.0-SNAPSHOT includes changes to artifact IDs, package names, and module
structure.

For details, refer to: - Common Artifact ID Changes - Common Package Changes -
Common Module Structure

##  Upgrading to 1.0.0-RC1

You can automate the upgrade process to 1.0.0-RC1 using an OpenRewrite recipe.
This recipe helps apply many of the necessary code changes for this version.
Find the recipe and usage instructions at Arconia Spring AI Migrations.

###  Breaking Changes

####  Chat Client and Advisors

The main changes that impact end user code are:

  * In `VectorStoreChatMemoryAdvisor`:
    * The constant `CHAT_MEMORY_RETRIEVE_SIZE_KEY` has been renamed to `TOP_K`.
    * The constant `DEFAULT_CHAT_MEMORY_RESPONSE_SIZE` (value: 100) has been renamed to `DEFAULT_TOP_K` with a new default value of 20.
  * The constant `CHAT_MEMORY_CONVERSATION_ID_KEY` has been renamed to `CONVERSATION_ID` and moved from `AbstractChatMemoryAdvisor` to the `ChatMemory` interface. Update your imports to use `org.springframework.ai.chat.memory.ChatMemory.CONVERSATION_ID`.

#####  Self-contained Templates in Advisors

The built-in advisors that perform prompt augmentation have been updated to use
self-contained templates. The goal is for each advisor to be able to perform
templating operations without affecting nor being affected by templating and
prompt decisions in other advisors.

**If you were providing custom templates for the following advisors, you’ll need
to update them to ensure all expected placeholders are included.**

  * The `QuestionAnswerAdvisor` expects a template with the following placeholders (see more details):
    * a `query` placeholder to receive the user question.
    * a `question_answer_context` placeholder to receive the retrieved context.
  * The `PromptChatMemoryAdvisor` expects a template with the following placeholders (see more details):
    * an `instructions` placeholder to receive the original system message.
    * a `memory` placeholder to receive the retrieved conversation memory.
  * The `VectorStoreChatMemoryAdvisor` expects a template with the following placeholders (see more details):
    * an `instructions` placeholder to receive the original system message.
    * a `long_term_memory` placeholder to receive the retrieved conversation memory.

####  Observability

  * Refactored content observation to use logging instead of tracing (ca843e8)
    * Replaced content observation filters with logging handlers
    * Renamed configuration properties to better reflect their purpose:
      * `include-prompt` → `log-prompt`
      * `include-completion` → `log-completion`
      * `include-query-response` → `log-query-response`
    * Added `TracingAwareLoggingObservationHandler` for trace-aware logging
    * Replaced `micrometer-tracing-bridge-otel` with `micrometer-tracing`
    * Removed event-based tracing in favor of direct logging
    * Removed direct dependency on the OTel SDK
    * Renamed `includePrompt` to `logPrompt` in observation properties (in `ChatClientBuilderProperties`, `ChatObservationProperties`, and `ImageObservationProperties`)

####  Chat Memory Repository Module and Autoconfiguration Renaming

We’ve standardized the naming pattern for chat memory components by adding the
repository suffix throughout the codebase. This change affects Cassandra, JDBC,
and Neo4j implementations, impacting artifact IDs, Java package names, and class
names for clarity.

####  Artifact IDs

All memory-related artifacts now follow a consistent pattern:

  * `spring-ai-model-chat-memory-`**→`spring-ai-model-chat-memory-repository-`**
  * `spring-ai-autoconfigure-model-chat-memory-`**→`spring-ai-autoconfigure-model-chat-memory-repository-`**
  * `spring-ai-starter-model-chat-memory-`**→`spring-ai-starter-model-chat-memory-repository-`**

####  Java Packages

  * Package paths now include `.repository.` segment
  * Example: `org.springframework.ai.chat.memory.jdbc` → `org.springframework.ai.chat.memory.repository.jdbc`

####  Configuration Classes

  * Main autoconfiguration classes now use the `Repository` suffix
  * Example: `JdbcChatMemoryAutoConfiguration` → `JdbcChatMemoryRepositoryAutoConfiguration`

####  Properties

  * Configuration properties renamed from `spring.ai.chat.memory.<storage>…​` to `spring.ai.chat.memory.repository.<storage>…​`

**Migration Required:** - Update your Maven/Gradle dependencies to use the new
artifact IDs. - Update any imports, class references, or configuration that used
the old package or class names.

####  Message Aggregator Refactoring

#####  Changes

  * `MessageAggregator` class has been moved from `org.springframework.ai.chat.model` package in the `spring-ai-client-chat` module to the `spring-ai-model` module (same package name)
  * The `aggregateChatClientResponse` method has been removed from `MessageAggregator` and moved to a new class `ChatClientMessageAggregator` in the `org.springframework.ai.chat.client` package

#####  Migration Guide

If you were directly using the `aggregateChatClientResponse` method from
`MessageAggregator`, you need to use the new `ChatClientMessageAggregator` class
instead:

```

// Before

new MessageAggregator().aggregateChatClientResponse(chatClientResponses,
aggregationHandler);

// After

new
ChatClientMessageAggregator().aggregateChatClientResponse(chatClientResponses,
aggregationHandler);

Copied!

```

Don’t forget to add the appropriate import:

```

import org.springframework.ai.chat.client.ChatClientMessageAggregator;

Copied!

```

####  Watson

The Watson AI model was removed as it was based on the older text generation
that is considered outdated as there is a new chat generation model available.
Hopefully Watson will reappear in a future version of Spring AI

####  MoonShot and QianFan

Moonshot and Qianfan have been removed since they are not accessible from
outside China. These have been moved to the Spring AI Community repository.

####  Removed Vector Store

  * Removed HanaDB vector store autoconfiguration (f3b4624)

####  Memory Management

  * Removed CassandraChatMemory implementation (11e3c8f)
  * Simplified chat memory advisor hierarchy and removed deprecated API (848a3fd)
  * Removed deprecations in JdbcChatMemory (356a68f)
  * Refactored chat memory repository artifacts for clarity (2d517ee)
  * Refactored chat memory repository autoconfigurations and Spring Boot starters for clarity (f6dba1b)

####  Message and Template APIs

  * Removed deprecated UserMessage constructors (06edee4)
  * Removed deprecated PromptTemplate constructors (722c77e)
  * Removed deprecated methods from Media (228ef10)
  * Refactored StTemplateRenderer: renamed supportStFunctions to validateStFunctions (0e15197)
  * Removed left over TemplateRender interface after moving it (52675d8)

####  Additional Client API Changes

  * Removed deprecations in ChatClient and Advisors (4fe74d8)
  * Removed deprecations from OllamaApi and AnthropicApi (46be898)

####  Package Structure Changes

  * Removed inter-package dependency cycles in spring-ai-model (ebfa5b9)
  * Moved MessageAggregator to spring-ai-model module (54e5c07)

####  Dependencies

  * Removed unused json-path dependency in spring-ai-openai (9de13d1)

###  Behavior Changes

####  Azure OpenAI

  * Added Entra ID identity management for Azure OpenAI with clean autoconfiguration (3dc86d3)

###  General Cleanup

  * Removed all code deprecations (76bee8c) and (b6ce7f3)

##  Upgrading to 1.0.0-M8

You can automate the upgrade process to 1.0.0-M8 using an OpenRewrite recipe.
This recipe helps apply many of the necessary code changes for this version.
Find the recipe and usage instructions at Arconia Spring AI Migrations.

###  Breaking Changes

When upgrading from Spring AI 1.0 M7 to 1.0 M8, users who previously registered
tool callbacks are encountering breaking changes that cause tool calling
functionality to silently fail. This is specifically impacting code that used
the deprecated `tools()` method.

####  Example

Here’s an example of code that worked in M7 but no longer functions as expected
in M8:

```

// This worked in M7 but silently fails in M8

ChatClient chatClient = new OpenAiChatClient(api)

    .tools(List.of(
        new Tool("get_current_weather", "Get the current weather in a given location",
            new ToolSpecification.ToolParameter("location", "The city and state, e.g. San Francisco, CA", true))
    ))
    .toolCallbacks(List.of(
        new ToolCallback("get_current_weather", (toolName, params) -> {
            // Weather retrieval logic
            return Map.of("temperature", 72, "unit", "fahrenheit", "description", "Sunny");
        })
    ));
Copied!

```

####  Solution

The solution is to use the `toolSpecifications()` method instead of the
deprecated `tools()` method:

```

// This works in M8

ChatClient chatClient = new OpenAiChatClient(api)

    .toolSpecifications(List.of(
        new Tool("get_current_weather", "Get the current weather in a given location",
            new ToolSpecification.ToolParameter("location", "The city and state, e.g. San Francisco, CA", true))
    ))
    .toolCallbacks(List.of(
        new ToolCallback("get_current_weather", (toolName, params) -> {
            // Weather retrieval logic
            return Map.of("temperature", 72, "unit", "fahrenheit", "description", "Sunny");
        })
    ));
Copied!

```

###  Removed Implementations and APIs

####  Memory Management

  * Removed CassandraChatMemory implementation (11e3c8f)
  * Simplified chat memory advisor hierarchy and removed deprecated API (848a3fd)
  * Removed deprecations in JdbcChatMemory (356a68f)
  * Refactored chat memory repository artifacts for clarity (2d517ee)
  * Refactored chat memory repository autoconfigurations and Spring Boot starters for clarity (f6dba1b)

####  Client APIs

  * Removed deprecations in ChatClient and Advisors (4fe74d8)
  * Breaking changes to chatclient tool calling (5b7849d)
  * Removed deprecations from OllamaApi and AnthropicApi (46be898)

####  Message and Template APIs

  * Removed deprecated UserMessage constructors (06edee4)
  * Removed deprecated PromptTemplate constructors (722c77e)
  * Removed deprecated methods from Media (228ef10)
  * Refactored StTemplateRenderer: renamed supportStFunctions to validateStFunctions (0e15197)
  * Removed left over TemplateRender interface after moving it (52675d8)

####  Model Implementations

  * Removed Watson text generation model (9e71b16)
  * Removed Qianfan code (bfcaad7)
  * Removed HanaDB vector store autoconfiguration (f3b4624)
  * Removed deepseek options from OpenAiApi (59b36d1)

####  Package Structure Changes

  * Removed inter-package dependency cycles in spring-ai-model (ebfa5b9)
  * Moved MessageAggregator to spring-ai-model module (54e5c07)

####  Dependencies

  * Removed unused json-path dependency in spring-ai-openai (9de13d1)

###  Behavior Changes

####  Observability

  * Refactored content observation to use logging instead of tracing (ca843e8)
    * Replaced content observation filters with logging handlers
    * Renamed configuration properties to better reflect their purpose:
      * `include-prompt` → `log-prompt`
      * `include-completion` → `log-completion`
      * `include-query-response` → `log-query-response`
    * Added `TracingAwareLoggingObservationHandler` for trace-aware logging
    * Replaced `micrometer-tracing-bridge-otel` with `micrometer-tracing`
    * Removed event-based tracing in favor of direct logging
    * Removed direct dependency on the OTel SDK
    * Renamed `includePrompt` to `logPrompt` in observation properties (in `ChatClientBuilderProperties`, `ChatObservationProperties`, and `ImageObservationProperties`)

####  Azure OpenAI

  * Added Entra ID identity management for Azure OpenAI with clean autoconfiguration (3dc86d3)

###  General Cleanup

  * Removed all deprecations from 1.0.0-M8 (76bee8c)
  * General deprecation cleanup (b6ce7f3)

##  Upgrading to 1.0.0-M7

###  Overview of Changes

Spring AI 1.0.0-M7 is the last milestone release before the RC1 and GA releases.
It introduces several important changes to artifact IDs, package names, and
module structure that will be maintained in the final release.

###  Artifact ID, Package, and Module Changes

The 1.0.0-M7 includes the same structural changes as 1.0.0-SNAPSHOT.

For details, refer to: - Common Artifact ID Changes - Common Package Changes -
Common Module Structure

###  MCP Java SDK Upgrade to 0.9.0

Spring AI 1.0.0-M7 now uses MCP Java SDK version 0.9.0, which includes
significant changes from previous versions. If you’re using MCP in your
applications, you’ll need to update your code to accommodate these changes.

Key changes include:

####  Interface Renaming

  * `ClientMcpTransport` → `McpClientTransport`
  * `ServerMcpTransport` → `McpServerTransport`
  * `DefaultMcpSession` → `McpClientSession` or `McpServerSession`
  * All `*Registration` classes → `*Specification` classes

####  Server Creation Changes

  * Use `McpServerTransportProvider` instead of `ServerMcpTransport`

```

// Before

ServerMcpTransport transport = new WebFluxSseServerTransport(objectMapper,
"/mcp/message");

var server = McpServer.sync(transport)

    .serverInfo("my-server", "1.0.0")
    .build();

// After

McpServerTransportProvider transportProvider = new
WebFluxSseServerTransportProvider(objectMapper, "/mcp/message");

var server = McpServer.sync(transportProvider)

    .serverInfo("my-server", "1.0.0")
    .build();
Copied!

```

####  Handler Signature Changes

All handlers now receive an `exchange` parameter as their first argument:

```

// Before

.tool(calculatorTool, args -> new CallToolResult("Result: " + calculate(args)))

// After

.tool(calculatorTool, (exchange, args) -> new CallToolResult("Result: " +
calculate(args)))

Copied!

```

####  Client Interaction via Exchange

Methods previously available on the server are now accessed through the exchange
object:

```

// Before

ClientCapabilities capabilities = server.getClientCapabilities();

CreateMessageResult result = server.createMessage(new
CreateMessageRequest(...));

// After

ClientCapabilities capabilities = exchange.getClientCapabilities();

CreateMessageResult result = exchange.createMessage(new
CreateMessageRequest(...));

Copied!

```

####  Roots Change Handlers

```

// Before

.rootsChangeConsumers(List.of(

    roots -> System.out.println("Roots changed: " + roots)
))

// After

.rootsChangeHandlers(List.of(

    (exchange, roots) -> System.out.println("Roots changed: " + roots)
))

Copied!

```

For a complete guide to migrating MCP code, refer to the MCP Migration Guide.

###  Enabling/Disabling Model Auto-Configuration

The previous configuration properties for enabling/disabling model auto-
configuration have been removed:

  * `spring.ai.<provider>.chat.enabled`
  * `spring.ai.<provider>.embedding.enabled`
  * `spring.ai.<provider>.image.enabled`
  * `spring.ai.<provider>.moderation.enabled`

By default, if a model provider (e.g., OpenAI, Ollama) is found on the
classpath, its corresponding auto-configuration for relevant model types (chat,
embedding, etc.) is enabled. If multiple providers for the same model type are
present (e.g., both `spring-ai-openai-spring-boot-starter` and `spring-ai-
ollama-spring-boot-starter`), you can use the following properties to select
**which** provider’s auto-configuration should be active, effectively disabling
the others for that specific model type.

To disable auto-configuration for a specific model type entirely, even if only
one provider is present, set the corresponding property to a value that does not
match any provider on the classpath (e.g., `none` or `disabled`).

You can refer to the `SpringAIModels` enumeration for a list of well-known
provider values.

  * `spring.ai.model.audio.speech=<model-provider|none>`
  * `spring.ai.model.audio.transcription=<model-provider|none>`
  * `spring.ai.model.chat=<model-provider|none>`
  * `spring.ai.model.embedding=<model-provider|none>`
  * `spring.ai.model.embedding.multimodal=<model-provider|none>`
  * `spring.ai.model.embedding.text=<model-provider|none>`
  * `spring.ai.model.image=<model-provider|none>`
  * `spring.ai.model.moderation=<model-provider|none>`

###  Automating upgrading using AI

You can automate the upgrade process to 1.0.0-M7 using the Claude Code CLI tool
with a provided prompt:

  1. Download the Claude Code CLI tool
  2. Copy the prompt from the update-to-m7.txt file
  3. Paste the prompt into the Claude Code CLI
  4. The AI will analyze your project and make the necessary changes

|  The automated upgrade prompt currently handles artifact ID changes, package
relocations, and module structure changes, but does not yet include automatic
changes for upgrading to MCP 0.9.0. If you’re using MCP, you’ll need to manually
update your code following the guidance in the MCP Java SDK Upgrade section.  
---|---  
##  Common Changes Across Versions

###  Artifact ID Changes

The naming pattern for Spring AI starter artifacts has changed. You’ll need to
update your dependencies according to the following patterns:

  * Model starters: `spring-ai-{model}-spring-boot-starter` → `spring-ai-starter-model-{model}`
  * Vector Store starters: `spring-ai-{store}-store-spring-boot-starter` → `spring-ai-starter-vector-store-{store}`
  * MCP starters: `spring-ai-mcp-{type}-spring-boot-starter` → `spring-ai-starter-mcp-{type}`

####  Examples

  * Maven
  * Gradle

```

<!-- BEFORE -->

<dependency>

    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-openai-spring-boot-starter</artifactId>
</dependency>

<!-- AFTER -->

<dependency>

    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-starter-model-openai</artifactId>
</dependency>

Copied!

```

```

// BEFORE

implementation 'org.springframework.ai:spring-ai-openai-spring-boot-starter'

implementation 'org.springframework.ai:spring-ai-redis-store-spring-boot-
starter'

// AFTER

implementation 'org.springframework.ai:spring-ai-starter-model-openai'

implementation 'org.springframework.ai:spring-ai-starter-vector-store-redis'

Copied!

```

####  Changes to Spring AI Autoconfiguration Artifacts

The Spring AI autoconfiguration has changed from a single monolithic artifact to
individual autoconfiguration artifacts per model, vector store, and other
components. This change was made to minimize the impact of different versions of
dependent libraries conflicting, such as Google Protocol Buffers, Google RPC,
and others. By separating autoconfiguration into component-specific artifacts,
you can avoid pulling in unnecessary dependencies and reduce the risk of version
conflicts in your application.

The original monolithic artifact is no longer available:

```

<!-- NO LONGER AVAILABLE -->

<dependency>

    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-spring-boot-autoconfigure</artifactId>
    <version>${project.version}</version>
</dependency>

Copied!

```

Instead, each component now has its own autoconfiguration artifact following
these patterns:

  * Model autoconfiguration: `spring-ai-autoconfigure-model-{model}`
  * Vector Store autoconfiguration: `spring-ai-autoconfigure-vector-store-{store}`
  * MCP autoconfiguration: `spring-ai-autoconfigure-mcp-{type}`

####  Examples of New Autoconfiguration Artifacts

  * Models
  * Vector Stores
  * MCP

```

<dependency>

    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-autoconfigure-model-openai</artifactId>
</dependency>

<dependency>

    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-autoconfigure-model-anthropic</artifactId>
</dependency>

<dependency>

    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-autoconfigure-model-vertex-ai</artifactId>
</dependency>

Copied!

```

```

<dependency>

    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-autoconfigure-vector-store-redis</artifactId>
</dependency>

<dependency>

    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-autoconfigure-vector-store-pgvector</artifactId>
</dependency>

<dependency>

    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-autoconfigure-vector-store-chroma</artifactId>
</dependency>

Copied!

```

```

<dependency>

    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-autoconfigure-mcp-client</artifactId>
</dependency>

<dependency>

    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-autoconfigure-mcp-server</artifactId>
</dependency>

Copied!

```

|  In most cases, you won’t need to explicitly add these autoconfiguration
dependencies. They are included transitively when using the corresponding
starter dependencies.  
---|---  
###  Package Name Changes

Your IDE should assist with refactoring to the new package locations.

  * `KeywordMetadataEnricher` and `SummaryMetadataEnricher` have moved from `org.springframework.ai.transformer` to `org.springframework.ai.chat.transformer`.
  * `Content`, `MediaContent`, and `Media` have moved from `org.springframework.ai.model` to `org.springframework.ai.content`.

###  Module Structure

The project has undergone significant changes to its module and artifact
structure. Previously, `spring-ai-core` contained all central interfaces, but
this has now been split into specialized domain modules to reduce unnecessary
dependencies in your applications.

####  spring-ai-commons

Base module with no dependencies on other Spring AI modules. Contains: - Core
domain models (`Document`, `TextSplitter`) - JSON utilities and resource
handling - Structured logging and observability support

####  spring-ai-model

Provides AI capability abstractions: - Interfaces like `ChatModel`,
`EmbeddingModel`, and `ImageModel` - Message types and prompt templates -
Function-calling framework (`ToolDefinition`, `ToolCallback`) - Content
filtering and observation support

####  spring-ai-vector-store

Unified vector database abstraction: - `VectorStore` interface for similarity
search - Advanced filtering with SQL-like expressions - `SimpleVectorStore` for
in-memory usage - Batching support for embeddings

####  spring-ai-client-chat

High-level conversational AI APIs: - `ChatClient` interface - Conversation
persistence via `ChatMemory` - Response conversion with `OutputConverter` -
Advisor-based interception - Synchronous and reactive streaming support

####  spring-ai-advisors-vector-store

Bridges chat with vector stores for RAG: - `QuestionAnswerAdvisor`: injects
context into prompts - `VectorStoreChatMemoryAdvisor`: stores/retrieves
conversation history

####  spring-ai-model-chat-memory-cassandra

Apache Cassandra persistence for `ChatMemory`: - `CassandraChatMemory`
implementation - Type-safe CQL with Cassandra’s QueryBuilder ==== spring-ai-
model-chat-memory-neo4j

Neo4j graph database persistence for chat conversations.

####  spring-ai-rag

Comprehensive framework for Retrieval Augmented Generation: - Modular
architecture for RAG pipelines - `RetrievalAugmentationAdvisor` as main entry
point - Functional programming principles with composable components

###  Dependency Structure

The dependency hierarchy can be summarized as:

  * `spring-ai-commons` (foundation)
  * `spring-ai-model` (depends on commons)
  * `spring-ai-vector-store` and `spring-ai-client-chat` (both depend on model)
  * `spring-ai-advisors-vector-store` and `spring-ai-rag` (depend on both client-chat and vector-store)
  * `spring-ai-model-chat-memory-*` modules (depend on client-chat)

###  ToolContext Changes

The `ToolContext` class has been enhanced to support both explicit and implicit
tool resolution. Tools can now be:

  1. **Explicitly Included** : Tools that are explicitly requested in the prompt and included in the call to the model.
  2. **Implicitly Available** : Tools that are made available for runtime dynamic resolution, but never included in any call to the model unless explicitly requested.

Starting with 1.0.0-M7, tools are only included in the call to the model if they
are explicitly requested in the prompt or explicitly included in the call.

Additionally, the `ToolContext` class has now been marked as final and cannot be
extended anymore. It was never supposed to be subclassed. You can add all the
contextual data you need when instantiating a `ToolContext`, in the form of a
`Map<String, Object>`. For more information, check the
[documentation](docs.spring.io/spring-
ai/reference/api/tools.html#_tool_context).

##  Upgrading to 1.0.0-M6

###  Changes to Usage Interface and DefaultUsage Implementation

The `Usage` interface and its default implementation `DefaultUsage` have
undergone the following changes:

  1. Method Rename:
     * `getGenerationTokens()` is now `getCompletionTokens()`
  2. Type Changes:
     * All token count fields in `DefaultUsage` changed from `Long` to `Integer`:
       * `promptTokens`
       * `completionTokens` (formerly `generationTokens`)
       * `totalTokens`

####  Required Actions

  * Replace all calls to `getGenerationTokens()` with `getCompletionTokens()`
  * Update `DefaultUsage` constructor calls:

```

// Old (M5)

new DefaultUsage(Long promptTokens, Long generationTokens, Long totalTokens)

// New (M6)

new DefaultUsage(Integer promptTokens, Integer completionTokens, Integer
totalTokens)

```

|  For more information on handling Usage, refer here  
---|---  
####  JSON Ser/Deser changes

While M6 maintains backward compatibility for JSON deserialization of the
`generationTokens` field, this field will be removed in M7. Any persisted JSON
documents using the old field name should be updated to use `completionTokens`.

Example of the new JSON format:

```

{

  "promptTokens": 100,

  "completionTokens": 50,

  "totalTokens": 150

}

Copied!

```

###  Changes to usage of FunctionCallingOptions for tool calling

Each `ChatModel` instance, at construction time, accepts an optional
`ChatOptions` or `FunctionCallingOptions` instance that can be used to configure
default tools used for calling the model.

Before 1.0.0-M6:

  * any tool passed via the `functions()` method of the default `FunctionCallingOptions` instance was included in each call to the model from that `ChatModel` instance, possibly overwritten by runtime options.
  * any tool passed via the `functionCallbacks()` method of the default `FunctionCallingOptions` instance was only made available for runtime dynamic resolution (see Tool Resolution), but never included in any call to the model unless explicitly requested.

Starting 1.0.0-M6:

  * any tool passed via the `functions()` method or the `functionCallbacks()` of the default `FunctionCallingOptions` instance is now handled in the same way: it is included in each call to the model from that `ChatModel` instance, possibly overwritten by runtime options. With that, there is consistency in the way tools are included in calls to the model and prevents any confusion due to a difference in behavior between `functionCallbacks()` and all the other options.

If you want to make a tool available for runtime dynamic resolution and include
it in a chat request to the model only when explicitly requested, you can use
one of the strategies described in Tool Resolution.

|  1.0.0-M6 introduced new APIs for handling tool calling. Backward
compatibility is maintained for the old APIs across all scenarios, except the
one described above. The old APIs are still available, but they are deprecated
and will be removed in 1.0.0-M7.  
---|---  
###  Removal of deprecated Amazon Bedrock chat models

Starting 1.0.0-M6, Spring AI transitioned to using Amazon Bedrock’s Converse API
for all Chat conversation implementations in Spring AI. All the Amazon Bedrock
Chat models are removed except the Embedding models for Cohere and Titan.

|  Refer to Bedrock Converse documentation for using the chat models.  
---|---  
###  Changes to use Spring Boot 3.4.2 for dependency management

Spring AI updates to use Spring Boot 3.4.2 for the dependency management. You
can refer here for the dependencies managed by Spring Boot 3.4.2

####  Required Actions

  * If you are upgrading to Spring Boot 3.4.2, please make sure to refer to this documentation for the changes required to configure the REST Client. Notably, if you don’t have an HTTP client library on the classpath, this will likely result in the use of `JdkClientHttpRequestFactory` where `SimpleClientHttpRequestFactory` would have been used previously. To switch to use `SimpleClientHttpRequestFactory`, you need to set `spring.http.client.factory=simple`.
  * If you are using a different version of Spring Boot (say Spring Boot 3.3.x) and need a specific version of a dependency, you can override it in your build configuration.

###  Vector Store API changes

In version 1.0.0-M6, the `delete` method in the `VectorStore` interface has been
modified to be a void operation instead of returning an `Optional<Boolean>`. If
your code previously checked the return value of the delete operation, you’ll
need to remove this check. The operation now throws an exception if the deletion
fails, providing more direct error handling.

####  Before 1.0.0-M6:

```

Optional<Boolean> result = vectorStore.delete(ids);

if (result.isPresent() && result.get()) {

    // handle successful deletion
}

Copied!

```

####  In 1.0.0-M6 and later:

```

vectorStore.delete(ids);

// deletion successful if no exception is thrown

Copied!

```

##  Upgrading to 1.0.0.M5

  * Vector Builders have been refactored for consistency.
  * Current VectorStore implementation constructors have been deprecated, use the builder pattern.
  * VectorStore implementation packages have been moved into unique package names, avoiding conflicts across artifact. For example `org.springframework.ai.vectorstore` to `org.springframework.ai.pgvector.vectorstore`.

##  Upgrading to 1.0.0.RC3

  * The type of the portable chat options (`frequencyPenalty`, `presencePenalty`, `temperature`, `topP`) has been changed from `Float` to `Double`.

##  Upgrading to 1.0.0.M2

  * The configuration prefix for the Chroma Vector Store has been changes from `spring.ai.vectorstore.chroma.store` to `spring.ai.vectorstore.chroma` in order to align with the naming conventions of other vector stores.
  * The default value of the `initialize-schema` property on vector stores capable of initializing a schema is now set to `false`. This implies that the applications now need to explicitly opt-in for schema initialization on supported vector stores, if the schema is expected to be created at application startup. Not all vector stores support this property. See the corresponding vector store documentation for more details. The following are the vector stores that currently don’t support the `initialize-schema` property.
    1. Hana
    2. Pinecone
    3. Weaviate
  * In Bedrock Jurassic 2, the chat options `countPenalty`, `frequencyPenalty`, and `presencePenalty` have been renamed to `countPenaltyOptions`, `frequencyPenaltyOptions`, and `presencePenaltyOptions`. Furthermore, the type of the chat option `stopSequences` have been changed from `String[]` to `List<String>`.
  * In Azure OpenAI, the type of the chat options `frequencyPenalty` and `presencePenalty` has been changed from `Double` to `Float`, consistently with all the other implementations.

##  Upgrading to 1.0.0.M1

On our march to release 1.0.0 M1 we have made several breaking changes.
Apologies, it is for the best!

###  ChatClient changes

A major change was made that took the 'old' `ChatClient` and moved the
functionality into `ChatModel`. The 'new' `ChatClient` now takes an instance of
`ChatModel`. This was done to support a fluent API for creating and executing
prompts in a style similar to other client classes in the Spring ecosystem, such
as `RestClient`, `WebClient`, and `JdbcClient`. Refer to the
[JavaDoc](docs.spring.io/spring-ai/docs/api) for more information on the Fluent
API, proper reference documentation is coming shortly.

We renamed the 'old' `ModelClient` to `Model` and renamed implementing classes,
for example `ImageClient` was renamed to `ImageModel`. The `Model`
implementation represents the portability layer that converts between the Spring
AI API and the underlying AI Model API.

A new package `model` that contains interfaces and base classes to support
creating AI Model Clients for any input/output data type combination. At the
moment, the chat and image model packages implement this. We will be updating
the embedding package to this new model soon.

A new "portable options" design pattern. We wanted to provide as much
portability in the `ModelCall` as possible across different chat based AI
Models. There is a common set of generation options and then those that are
specific to a model provider. A sort of "duck typing" approach is used.
`ModelOptions` in the model package is a marker interface indicating
implementations of this class will provide the options for a model. See
`ImageOptions`, a subinterface that defines portable options across all
text→image `ImageModel` implementations. Then `StabilityAiImageOptions` and
`OpenAiImageOptions` provide the options specific to each model provider. All
options classes are created via a fluent API builder, all can be passed into the
portable `ImageModel` API. These option data types are used in
autoconfiguration/configuration properties for the `ImageModel` implementations.

###  Artifact name changes

Renamed POM artifact names: - spring-ai-qdrant → spring-ai-qdrant-store -
spring-ai-cassandra → spring-ai-cassandra-store - spring-ai-pinecone → spring-
ai-pinecone-store - spring-ai-redis → spring-ai-redis-store - spring-ai-qdrant →
spring-ai-qdrant-store - spring-ai-gemfire → spring-ai-gemfire-store - spring-
ai-azure-vector-store-spring-boot-starter → spring-ai-azure-store-spring-boot-
starter - spring-ai-redis-spring-boot-starter → spring-ai-starter-vector-store-
redis

##  Upgrading to 0.8.1

Former `spring-ai-vertex-ai` has been renamed to `spring-ai-vertex-ai-palm2` and
`spring-ai-vertex-ai-spring-boot-starter` has been renamed to `spring-ai-vertex-
ai-palm2-spring-boot-starter`.

So, you need to change the dependency from

```

<dependency>

    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-vertex-ai</artifactId>
</dependency>

Copied!

```

To

```

<dependency>

    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-vertex-ai-palm2</artifactId>
</dependency>

Copied!

```

and the related Boot starter for the Palm2 model has changed from

```

<dependency>

    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-vertex-ai-spring-boot-starter</artifactId>
</dependency>

Copied!

```

to

```

<dependency>

    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-vertex-ai-palm2-spring-boot-starter</artifactId>
</dependency>

Copied!

```

  * Renamed Classes (01.03.2024)
    * VertexAiApi → VertexAiPalm2Api
    * VertexAiClientChat → VertexAiPalm2ChatClient
    * VertexAiEmbeddingClient → VertexAiPalm2EmbeddingClient
    * VertexAiChatOptions → VertexAiPalm2ChatOptions

##  Upgrading to 0.8.0

###  January 24, 2024 Update

  * Moving the `prompt` and `messages` and `metadata` packages to subpackages of `org.springframework.ai.chat`
  * New functionality is **text to image** clients. Classes are `OpenAiImageModel` and `StabilityAiImageModel`. See the integration tests for usage, docs are coming soon.
  * A new package `model` that contains interfaces and base classes to support creating AI Model Clients for any input/output data type combination. At the moment, the chat and image model packages implement this. We will be updating the embedding package to this new model soon.
  * A new "portable options" design pattern. We wanted to provide as much portability in the `ModelCall` as possible across different chat based AI Models. There is a common set of generation options and then those that are specific to a model provider. A sort of "duck typing" approach is used. `ModelOptions` in the model package is a marker interface indicating implementations of this class will provide the options for a model. See `ImageOptions`, a subinterface that defines portable options across all text→image `ImageModel` implementations. Then `StabilityAiImageOptions` and `OpenAiImageOptions` provide the options specific to each model provider. All options classes are created via a fluent API builder, all can be passed into the portable `ImageModel` API. These option data types are used in autoconfiguration/configuration properties for the `ImageModel` implementations.

###  January 13, 2024 Update

The following OpenAi Autoconfiguration chat properties have changed

  * from `spring.ai.openai.model` to `spring.ai.openai.chat.options.model`.
  * from `spring.ai.openai.temperature` to `spring.ai.openai.chat.options.temperature`.

Find updated documentation about the OpenAi properties: docs.spring.io/spring-
ai/reference/api/chat/openai-chat.html

###  December 27, 2023 Update

Merge SimplePersistentVectorStore and InMemoryVectorStore into SimpleVectorStore
* Replace InMemoryVectorStore with SimpleVectorStore

###  December 20, 2023 Update

Refactor the Ollama client and related classes and package names

  * Replace the org.springframework.ai.ollama.client.OllamaClient by org.springframework.ai.ollama.OllamaModelCall.
  * The OllamaChatClient method signatures have changed.
  * Rename the org.springframework.ai.autoconfigure.ollama.OllamaProperties into org.springframework.ai.model.ollama.autoconfigure.OllamaChatProperties and change the suffix to: `spring.ai.ollama.chat`. Some of the properties have changed as well.

###  December 19, 2023 Update

Renaming of AiClient and related classes and package names

  * Rename AiClient to ChatClient
  * Rename AiResponse to ChatResponse
  * Rename AiStreamClient to StreamingChatClient
  * Rename package org.sf.ai.client to org.sf.ai.chat

Rename artifact ID of

  * `transformers-embedding` to `spring-ai-transformers`

Moved Maven modules from top-level directory and `embedding-clients`
subdirectory to all be under a single `models` directory.

###  December 1, 2023

We are transitioning the project’s Group ID:

  * **FROM** : `org.springframework.experimental.ai`
  * **TO** : `org.springframework.ai`

Artifacts will still be hosted in the snapshot repository as shown below.

The main branch will move to the version `0.8.0-SNAPSHOT`. It will be unstable
for a week or two. Please use the 0.7.1-SNAPSHOT if you don’t want to be on the
bleeding edge.

You can access `0.7.1-SNAPSHOT` artifacts as before and still access
0.7.1-SNAPSHOT Documentation.

###  0.7.1-SNAPSHOT Dependencies

  * Azure OpenAI
```

<dependency>

    <groupId>org.springframework.experimental.ai</groupId>
    <artifactId>spring-ai-azure-openai-spring-boot-starter</artifactId>
    <version>0.7.1-SNAPSHOT</version>
</dependency>

Copied!

```

  * OpenAI
```

<dependency>

    <groupId>org.springframework.experimental.ai</groupId>
    <artifactId>spring-ai-openai-spring-boot-starter</artifactId>
    <version>0.7.1-SNAPSHOT</version>
</dependency>

Copied!

```

Deploying to the Cloud Migrating FunctionCallback to ToolCallback API

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

