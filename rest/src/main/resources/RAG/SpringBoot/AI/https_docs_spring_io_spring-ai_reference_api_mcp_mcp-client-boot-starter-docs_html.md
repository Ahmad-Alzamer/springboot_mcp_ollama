source URL: https://docs.spring.io/spring-ai/reference/api/mcp/mcp-client-boot-starter-docs.html 
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

### MCP Client Boot Starter

  * Starters
  * Standard MCP Client
  * WebFlux Client
  * Configuration Properties
  * Common Properties
  * Stdio Transport Properties
  * SSE Transport Properties
  * Features
  * Sync/Async Client Types
  * Client Customization
  * Transport Support
  * Integration with Spring AI
  * Usage Example
  * Example Applications
  * Additional Resources

  * Spring AI
  * Reference
  * Model Context Protocol (MCP)
  * MCP Client Boot Starters

# MCP Client Boot Starter

### MCP Client Boot Starter

  * Starters
  * Standard MCP Client
  * WebFlux Client
  * Configuration Properties
  * Common Properties
  * Stdio Transport Properties
  * SSE Transport Properties
  * Features
  * Sync/Async Client Types
  * Client Customization
  * Transport Support
  * Integration with Spring AI
  * Usage Example
  * Example Applications
  * Additional Resources

The Spring AI MCP (Model Context Protocol) Client Boot Starter provides auto-
configuration for MCP client functionality in Spring Boot applications. It
supports both synchronous and asynchronous client implementations with various
transport options.

The MCP Client Boot Starter provides:

  * Management of multiple client instances
  * Automatic client initialization (if enabled)
  * Support for multiple named transports
  * Integration with Spring AI’s tool execution framework
  * Proper lifecycle management with automatic cleanup of resources when the application context is closed
  * Customizable client creation through customizers

##  Starters

|  There has been a significant change in the Spring AI auto-configuration,
starter modules' artifact names. Please refer to the upgrade notes for more
information.  
---|---  
###  Standard MCP Client

```

<dependency>

    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-starter-mcp-client</artifactId>
</dependency>

Copied!

```

The standard starter connects simultaneously to one or more MCP servers over
`STDIO` (in-process) and/or `SSE` (remote) transports. The SSE connection uses
the HttpClient-based transport implementation. Each connection to an MCP server
creates a new MCP client instance. You can choose either `SYNC` or `ASYNC` MCP
clients (note: you cannot mix sync and async clients). For production
deployment, we recommend using the WebFlux-based SSE connection with the
`spring-ai-starter-mcp-client-webflux`.

###  WebFlux Client

The WebFlux starter provides similar functionality to the standard starter but
uses a WebFlux-based SSE transport implementation.

```

<dependency>

    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-starter-mcp-client-webflux</artifactId>
</dependency>

Copied!

```

##  Configuration Properties

###  Common Properties

The common properties are prefixed with `spring.ai.mcp.client`:

Property | Description | Default Value  
---|---|---  
`enabled` | Enable/disable the MCP client | `true`  
`name` | Name of the MCP client instance (used for compatibility checks) | `spring-ai-mcp-client`  
`version` | Version of the MCP client instance | `1.0.0`  
`initialized` | Whether to initialize clients on creation | `true`  
`request-timeout` | Timeout duration for MCP client requests | `20s`  
`type` | Client type (SYNC or ASYNC). All clients must be either sync or async; mixing is not supported | `SYNC`  
`root-change-notification` | Enable/disable root change notifications for all clients | `true`  
`toolcallback.enabled` | Enable/disable the MCP tool callback integration with Spring AI’s tool execution framework | `true`  
###  Stdio Transport Properties

Properties for Standard I/O transport are prefixed with
`spring.ai.mcp.client.stdio`:

Property | Description | Default Value  
---|---|---  
`servers-configuration` | Resource containing the MCP servers configuration in JSON format | -  
`connections` | Map of named stdio connection configurations | -  
`connections.[name].command` | The command to execute for the MCP server | -  
`connections.[name].args` | List of command arguments | -  
`connections.[name].env` | Map of environment variables for the server process | -  
Example configuration:

```

spring:

  ai:

    mcp:
      client:
        stdio:
          root-change-notification: true
          connections:
            server1:
              command: /path/to/server
              args:
                - --port=8080
                - --mode=production
              env:
                API_KEY: your-api-key
                DEBUG: "true"
Copied!

```

Alternatively, you can configure stdio connections using an external JSON file
using the Claude Desktop format:

```

spring:

  ai:

    mcp:
      client:
        stdio:
          servers-configuration: classpath:mcp-servers.json
Copied!

```

The Claude Desktop format looks like this:

```

{

  "mcpServers": {

    "filesystem": {
      "command": "npx",
      "args": [
        "-y",
        "@modelcontextprotocol/server-filesystem",
        "/Users/username/Desktop",
        "/Users/username/Downloads"
      ]
    }
  }

}

Copied!

```

Currently, the Claude Desktop format supports only STDIO connection types.

###  SSE Transport Properties

Properties for Server-Sent Events (SSE) transport are prefixed with
`spring.ai.mcp.client.sse`:

Property | Description | Default Value  
---|---|---  
`connections` | Map of named SSE connection configurations | -  
`connections.[name].url` | Base URL endpoint for SSE communication with the MCP server | -  
`connections.[name].sse-endpoint` | the sse endpoint (as url suffix) to use for the connection | `/sse`  
Example configuration:

```

spring:

  ai:

    mcp:
      client:
        sse:
          connections:
            server1:
              url: http://localhost:8080
            server2:
              url: http://otherserver:8081
              sse-endpoint: /custom-sse
Copied!

```

##  Features

###  Sync/Async Client Types

The starter supports two types of clients:

  * Synchronous - default client type, suitable for traditional request-response patterns with blocking operations
  * Asynchronous - suitable for reactive applications with non-blocking operations, configured using `spring.ai.mcp.client.type=ASYNC`

###  Client Customization

The auto-configuration provides extensive client spec customization capabilities
through callback interfaces. These customizers allow you to configure various
aspects of the MCP client behavior, from request timeouts to event handling and
message processing.

####  Customization Types

The following customization options are available:

  * **Request Configuration** - Set custom request timeouts
  * **Custom Sampling Handlers** - standardized way for servers to request LLM sampling (`completions` or `generations`) from LLMs via clients. This flow allows clients to maintain control over model access, selection, and permissions while enabling servers to leverage AI capabilities — with no server API keys necessary.
  * **File system (Roots) Access** - standardized way for clients to expose filesystem `roots` to servers. Roots define the boundaries of where servers can operate within the filesystem, allowing them to understand which directories and files they have access to. Servers can request the list of roots from supporting clients and receive notifications when that list changes.
  * **Event Handlers** - client’s handler to be notified when a certain server event occurs:
    * Tools change notifications - when the list of available server tools changes
    * Resources change notifications - when the list of available server resources changes.
    * Prompts change notifications - when the list of available server prompts changes.
  * **Logging Handlers** - standardized way for servers to send structured log messages to clients. Clients can control logging verbosity by setting minimum log levels

You can implement either `McpSyncClientCustomizer` for synchronous clients or
`McpAsyncClientCustomizer` for asynchronous clients, depending on your
application’s needs.

  * Sync
  * Async

```

@Component

public class CustomMcpSyncClientCustomizer implements McpSyncClientCustomizer {

    @Override
    public void customize(String serverConfigurationName, McpClient.SyncSpec spec) {

        // Customize the request timeout configuration
        spec.requestTimeout(Duration.ofSeconds(30));

        // Sets the root URIs that this client can access.
        spec.roots(roots);

        // Sets a custom sampling handler for processing message creation requests.
        spec.sampling((CreateMessageRequest messageRequest) -> {
            // Handle sampling
            CreateMessageResult result = ...
            return result;
        });

        // Adds a consumer to be notified when the available tools change, such as tools
        // being added or removed.
        spec.toolsChangeConsumer((List<McpSchema.Tool> tools) -> {
            // Handle tools change
        });

        // Adds a consumer to be notified when the available resources change, such as resources
        // being added or removed.
        spec.resourcesChangeConsumer((List<McpSchema.Resource> resources) -> {
            // Handle resources change
        });

        // Adds a consumer to be notified when the available prompts change, such as prompts
        // being added or removed.
        spec.promptsChangeConsumer((List<McpSchema.Prompt> prompts) -> {
            // Handle prompts change
        });

        // Adds a consumer to be notified when logging messages are received from the server.
        spec.loggingConsumer((McpSchema.LoggingMessageNotification log) -> {
            // Handle log messages
        });
    }
}

Copied!

```

```

@Component

public class CustomMcpAsyncClientCustomizer implements McpAsyncClientCustomizer
{

    @Override
    public void customize(String serverConfigurationName, McpClient.AsyncSpec spec) {
        // Customize the async client configuration
        spec.requestTimeout(Duration.ofSeconds(30));
    }
}

Copied!

```

The `serverConfigurationName` parameter is the name of the server configuration
that the customizer is being applied to and the MCP Client is created for.

The MCP client auto-configuration automatically detects and applies any
customizers found in the application context.

###  Transport Support

The auto-configuration supports multiple transport types:

  * Standard I/O (Stdio) (activated by the `spring-ai-starter-mcp-client`)
  * SSE HTTP (activated by the `spring-ai-starter-mcp-client`)
  * SSE WebFlux (activated by the `spring-ai-starter-mcp-client-webflux`)

###  Integration with Spring AI

The starter can configure tool callbacks that integrate with Spring AI’s tool
execution framework, allowing MCP tools to be used as part of AI interactions.
This integration is enabled by default and can be disabled by setting the
`spring.ai.mcp.client.toolcallback.enabled=false` property.

##  Usage Example

Add the appropriate starter dependency to your project and configure the client
in `application.properties` or `application.yml`:

```

spring:

  ai:

    mcp:
      client:
        enabled: true
        name: my-mcp-client
        version: 1.0.0
        request-timeout: 30s
        type: SYNC  # or ASYNC for reactive applications
        sse:
          connections:
            server1:
              url: http://localhost:8080
            server2:
              url: http://otherserver:8081
        stdio:
          root-change-notification: false
          connections:
            server1:
              command: /path/to/server
              args:
                - --port=8080
                - --mode=production
              env:
                API_KEY: your-api-key
                DEBUG: "true"
Copied!

```

The MCP client beans will be automatically configured and available for
injection:

```

@Autowired

private List<McpSyncClient> mcpSyncClients;  // For sync client

// OR

@Autowired

private List<McpAsyncClient> mcpAsyncClients;  // For async client

Copied!

```

When tool callbacks are enabled (the default behavior), the registered MCP Tools
with all MCP clients are provided as a `ToolCallbackProvider` instance:

```

@Autowired

private SyncMcpToolCallbackProvider toolCallbackProvider;

ToolCallback[] toolCallbacks = toolCallbackProvider.getToolCallbacks();

Copied!

```

##  Example Applications

  * Brave Web Search Chatbot - A chatbot that uses the Model Context Protocol to interact with a web search server.
  * Default MCP Client Starter - A simple example of using the default `spring-ai-starter-mcp-client` MCP Client Boot Starter.
  * WebFlux MCP Client Starter - A simple example of using the `spring-ai-starter-mcp-client-webflux` MCP Client Boot Starter.

##  Additional Resources

  * Spring AI Documentation
  * Model Context Protocol Specification
  * Spring Boot Auto-configuration

Model Context Protocol (MCP) MCP Server Boot Starters

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

