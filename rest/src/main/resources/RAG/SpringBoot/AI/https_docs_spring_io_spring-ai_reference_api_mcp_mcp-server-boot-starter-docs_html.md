source URL: https://docs.spring.io/spring-ai/reference/api/mcp/mcp-server-boot-starter-docs.html 
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

### MCP Server Boot Starter

  * Starters
  * Standard MCP Server
  * WebMVC Server Transport
  * WebFlux Server Transport
  * Configuration Properties
  * Sync/Async Server Types
  * Server Capabilities
  * Transport Options
  * Features and Capabilities
  * Tools
  * Resource Management
  * Prompt Management
  * Completion Management
  * Root Change Consumers
  * Usage Examples
  * Standard STDIO Server Configuration
  * WebMVC Server Configuration
  * WebFlux Server Configuration
  * Creating a Spring Boot Application with MCP Server
  * Example Applications
  * Additional Resources

  * Spring AI
  * Reference
  * Model Context Protocol (MCP)
  * MCP Server Boot Starters

# MCP Server Boot Starter

### MCP Server Boot Starter

  * Starters
  * Standard MCP Server
  * WebMVC Server Transport
  * WebFlux Server Transport
  * Configuration Properties
  * Sync/Async Server Types
  * Server Capabilities
  * Transport Options
  * Features and Capabilities
  * Tools
  * Resource Management
  * Prompt Management
  * Completion Management
  * Root Change Consumers
  * Usage Examples
  * Standard STDIO Server Configuration
  * WebMVC Server Configuration
  * WebFlux Server Configuration
  * Creating a Spring Boot Application with MCP Server
  * Example Applications
  * Additional Resources

The Spring AI MCP (Model Context Protocol) Server Boot Starter provides auto-
configuration for setting up an MCP server in Spring Boot applications. It
enables seamless integration of MCP server capabilities with Spring Boot’s auto-
configuration system.

The MCP Server Boot Starter offers:

  * Automatic configuration of MCP server components
  * Support for both synchronous and asynchronous operation modes
  * Multiple transport layer options
  * Flexible tool, resource, and prompt specification
  * Change notification capabilities

##  Starters

|  There has been a significant change in the Spring AI auto-configuration,
starter modules' artifact names. Please refer to the upgrade notes for more
information.  
---|---  
Choose one of the following starters based on your transport requirements:

###  Standard MCP Server

Full MCP Server features support with `STDIO` server transport.

```

<dependency>

    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-mcp-server-spring-boot-starter</artifactId>
</dependency>

Copied!

```

  * Suitable for command-line and desktop tools
  * No additional web dependencies required

The starter activates the `McpServerAutoConfiguration` auto-configuration
responsible for:

  * Configuring the basic server components
  * Handling tool, resource, and prompt specifications
  * Managing server capabilities and change notifications
  * Providing both sync and async server implementations

###  WebMVC Server Transport

Full MCP Server features support with `SSE` (Server-Sent Events) server
transport based on Spring MVC and an optional `STDIO` transport.

```

<dependency>

    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-starter-mcp-server-webmvc</artifactId>
</dependency>

Copied!

```

The starter activates the `McpWebMvcServerAutoConfiguration` and
`McpServerAutoConfiguration` auto-configurations to provide:

  * HTTP-based transport using Spring MVC (`WebMvcSseServerTransportProvider`)
  * Automatically configured SSE endpoints
  * Optional `STDIO` transport (enabled by setting `spring.ai.mcp.server.stdio=true`)
  * Included `spring-boot-starter-web` and `mcp-spring-webmvc` dependencies

###  WebFlux Server Transport

Full MCP Server features support with `SSE` (Server-Sent Events) server
transport based on Spring WebFlux and an optional `STDIO` transport.

```

<dependency>

    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-starter-mcp-server-webflux</artifactId>
</dependency>

Copied!

```

The starter activates the `McpWebFluxServerAutoConfiguration` and
`McpServerAutoConfiguration` auto-configurations to provide:

  * Reactive transport using Spring WebFlux (`WebFluxSseServerTransportProvider`)
  * Automatically configured reactive SSE endpoints
  * Optional `STDIO` transport (enabled by setting `spring.ai.mcp.server.stdio=true`)
  * Included `spring-boot-starter-webflux` and `mcp-spring-webflux` dependencies

##  Configuration Properties

All properties are prefixed with `spring.ai.mcp.server`:

Property | Description | Default  
---|---|---  
`enabled` | Enable/disable the MCP server | `true`  
`stdio` | Enable/disable stdio transport | `false`  
`name` | Server name for identification | `mcp-server`  
`version` | Server version | `1.0.0`  
`instructions` | Optional instructions to provide guidance to the client on how to interact with this server | `null`  
`type` | Server type (SYNC/ASYNC) | `SYNC`  
`capabilities.resource` | Enable/disable resource capabilities | `true`  
`capabilities.tool` | Enable/disable tool capabilities | `true`  
`capabilities.prompt` | Enable/disable prompt capabilities | `true`  
`capabilities.completion` | Enable/disable completion capabilities | `true`  
`resource-change-notification` | Enable resource change notifications | `true`  
`prompt-change-notification` | Enable prompt change notifications | `true`  
`tool-change-notification` | Enable tool change notifications | `true`  
`tool-response-mime-type` | (optional) response MIME type per tool name. For example `spring.ai.mcp.server.tool-response-mime-type.generateImage=image/png` will associate the `image/png` mime type with the `generateImage()` tool name | `-`  
`sse-message-endpoint` | Custom SSE Message endpoint path for web transport to be used by the client to send messages | `/mcp/message`  
`sse-endpoint` | Custom SSE endpoint path for web transport | `/sse`  
`base-url` | Optional URL prefix. For example `base-url=/api/v1` means that the client should access the sse endpont at `/api/v1` + `sse-endpoint` and the message endpoint is `/api/v1` + `sse-message-endpoint` | -  
`request-timeout` | Duration to wait for server responses before timing out requests. Applies to all requests made through the client, including tool calls, resource access, and prompt operations. | `20` seconds  
##  Sync/Async Server Types

  * **Synchronous Server** - The default server type implemented using `McpSyncServer`. It is designed for straightforward request-response patterns in your applications. To enable this server type, set `spring.ai.mcp.server.type=SYNC` in your configuration. When activated, it automatically handles the configuration of synchronous tool specifications.
  * **Asynchronous Server** - The asynchronous server implementation uses `McpAsyncServer` and is optimized for non-blocking operations. To enable this server type, configure your application with `spring.ai.mcp.server.type=ASYNC`. This server type automatically sets up asynchronous tool specifications with built-in Project Reactor support.

##  Server Capabilities

The MCP Server supports four main capability types that can be individually
enabled or disabled:

  * **Tools** - Enable/disable tool capabilities with `spring.ai.mcp.server.capabilities.tool=true|false`
  * **Resources** - Enable/disable resource capabilities with `spring.ai.mcp.server.capabilities.resource=true|false`
  * **Prompts** - Enable/disable prompt capabilities with `spring.ai.mcp.server.capabilities.prompt=true|false`
  * **Completions** - Enable/disable completion capabilities with `spring.ai.mcp.server.capabilities.completion=true|false`

All capabilities are enabled by default. Disabling a capability will prevent the
server from registering and exposing the corresponding features to clients.

##  Transport Options

The MCP Server supports three transport mechanisms, each with its dedicated
starter:

  * Standard Input/Output (STDIO) - `spring-ai-starter-mcp-server`
  * Spring MVC (Server-Sent Events) - `spring-ai-starter-mcp-server-webmvc`
  * Spring WebFlux (Reactive SSE) - `spring-ai-starter-mcp-server-webflux`

##  Features and Capabilities

The MCP Server Boot Starter allows servers to expose tools, resources, and
prompts to clients. It automatically converts custom capability handlers
registered as Spring beans to sync/async specifications based on server type:

###  Tools

Allows servers to expose tools that can be invoked by language models. The MCP
Server Boot Starter provides:

  * Change notification support
  * Spring AI Tools are automatically converted to sync/async specifications based on server type
  * Automatic tool specification through Spring beans:

```

@Bean

public ToolCallbackProvider myTools(...) {

    List<ToolCallback> tools = ...
    return ToolCallbackProvider.from(tools);
}

Copied!

```

or using the low-level API:

```

@Bean

public List<McpServerFeatures.SyncToolSpecification> myTools(...) {

    List<McpServerFeatures.SyncToolSpecification> tools = ...
    return tools;
}

Copied!

```

The auto-configuration will automatically detect and register all tool callbacks
from: * Individual `ToolCallback` beans * Lists of `ToolCallback` beans *
`ToolCallbackProvider` beans

Tools are de-duplicated by name, with the first occurrence of each tool name
being used.

####  Tool Context Support

The ToolContext is supported, allowing contextual information to be passed to
tool calls. It contains an `McpSyncServerExchange` instance under the `exchange`
key, accessible via `McpToolUtils.getMcpExchange(toolContext)`. See this example
demonstrating `exchange.loggingNotification(…​)` and
`exchange.createMessage(…​)`.

###  Resource Management

Provides a standardized way for servers to expose resources to clients.

  * Static and dynamic resource specifications
  * Optional change notifications
  * Support for resource templates
  * Automatic conversion between sync/async resource specifications
  * Automatic resource specification through Spring beans:

```

@Bean

public List<McpServerFeatures.SyncResourceSpecification> myResources(...) {

    var systemInfoResource = new McpSchema.Resource(...);
    var resourceSpecification = new McpServerFeatures.SyncResourceSpecification(systemInfoResource, (exchange, request) -> {
        try {
            var systemInfo = Map.of(...);
            String jsonContent = new ObjectMapper().writeValueAsString(systemInfo);
            return new McpSchema.ReadResourceResult(
                    List.of(new McpSchema.TextResourceContents(request.uri(), "application/json", jsonContent)));
        }
        catch (Exception e) {
            throw new RuntimeException("Failed to generate system info", e);
        }
    });

    return List.of(resourceSpecification);
}

Copied!

```

###  Prompt Management

Provides a standardized way for servers to expose prompt templates to clients.

  * Change notification support
  * Template versioning
  * Automatic conversion between sync/async prompt specifications
  * Automatic prompt specification through Spring beans:

```

@Bean

public List<McpServerFeatures.SyncPromptSpecification> myPrompts() {

    var prompt = new McpSchema.Prompt("greeting", "A friendly greeting prompt",
        List.of(new McpSchema.PromptArgument("name", "The name to greet", true)));

    var promptSpecification = new McpServerFeatures.SyncPromptSpecification(prompt, (exchange, getPromptRequest) -> {
        String nameArgument = (String) getPromptRequest.arguments().get("name");
        if (nameArgument == null) { nameArgument = "friend"; }
        var userMessage = new PromptMessage(Role.USER, new TextContent("Hello " + nameArgument + "! How can I assist you today?"));
        return new GetPromptResult("A personalized greeting message", List.of(userMessage));
    });

    return List.of(promptSpecification);
}

Copied!

```

###  Completion Management

Provides a standardized way for servers to expose completion capabilities to
clients.

  * Support for both sync and async completion specifications
  * Automatic registration through Spring beans:

```

@Bean

public List<McpServerFeatures.SyncCompletionSpecification> myCompletions() {

    var completion = new McpServerFeatures.SyncCompletionSpecification(
        "code-completion",
        "Provides code completion suggestions",
        (exchange, request) -> {
            // Implementation that returns completion suggestions
            return new McpSchema.CompletionResult(List.of(
                new McpSchema.Completion("suggestion1", "First suggestion"),
                new McpSchema.Completion("suggestion2", "Second suggestion")
            ));
        }
    );

    return List.of(completion);
}

Copied!

```

###  Root Change Consumers

When roots change, clients that support `listChanged` send a Root Change
notification.

  * Support for monitoring root changes
  * Automatic conversion to async consumers for reactive applications
  * Optional registration through Spring beans

```

@Bean

public BiConsumer<McpSyncServerExchange, List<McpSchema.Root>>
rootsChangeHandler() {

    return (exchange, roots) -> {
        logger.info("Registering root resources: {}", roots);
    };
}

Copied!

```

##  Usage Examples

###  Standard STDIO Server Configuration

```

# Using spring-ai-starter-mcp-server

spring:

  ai:

    mcp:
      server:
        name: stdio-mcp-server
        version: 1.0.0
        type: SYNC
Copied!

```

###  WebMVC Server Configuration

```

# Using spring-ai-starter-mcp-server-webmvc

spring:

  ai:

    mcp:
      server:
        name: webmvc-mcp-server
        version: 1.0.0
        type: SYNC
        instructions: "This server provides weather information tools and resources"
        sse-message-endpoint: /mcp/messages
        capabilities:
          tool: true
          resource: true
          prompt: true
          completion: true
Copied!

```

###  WebFlux Server Configuration

```

# Using spring-ai-starter-mcp-server-webflux

spring:

  ai:

    mcp:
      server:
        name: webflux-mcp-server
        version: 1.0.0
        type: ASYNC  # Recommended for reactive applications
        instructions: "This reactive server provides weather information tools and resources"
        sse-message-endpoint: /mcp/messages
        capabilities:
          tool: true
          resource: true
          prompt: true
          completion: true
Copied!

```

###  Creating a Spring Boot Application with MCP Server

```

@Service

public class WeatherService {

    @Tool(description = "Get weather information by city name")
    public String getWeather(String cityName) {
        // Implementation
    }
}

@SpringBootApplication

public class McpServerApplication {

    private static final Logger logger = LoggerFactory.getLogger(McpServerApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(McpServerApplication.class, args);
    }

	@Bean
	public ToolCallbackProvider weatherTools(WeatherService weatherService) {
		return MethodToolCallbackProvider.builder().toolObjects(weatherService).build();
	}
}

Copied!

```

The auto-configuration will automatically register the tool callbacks as MCP
tools. You can have multiple beans producing ToolCallbacks. The auto-
configuration will merge them.

##  Example Applications

  * Weather Server (WebFlux) - Spring AI MCP Server Boot Starter with WebFlux transport.
  * Weather Server (STDIO) - Spring AI MCP Server Boot Starter with STDIO transport.
  * Weather Server Manual Configuration - Spring AI MCP Server Boot Starter that doesn’t use auto-configuration but the Java SDK to configure the server manually.

##  Additional Resources

  * Spring AI Documentation
  * Model Context Protocol Specification
  * Spring Boot Auto-configuration

MCP Client Boot Starters MCP Utilities

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

