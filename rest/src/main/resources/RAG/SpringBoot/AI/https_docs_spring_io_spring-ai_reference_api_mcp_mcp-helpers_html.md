source URL: https://docs.spring.io/spring-ai/reference/api/mcp/mcp-helpers.html 
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

### MCP Utilities

  * ToolCallback Utility
  * Tool Callback Adapter
  * Tool Callback Providers
  * McpToolUtils
  * ToolCallbacks to ToolSpecifications
  * MCP Clients to ToolCallbacks
  * Native Image Support

  * Spring AI
  * Reference
  * Model Context Protocol (MCP)
  * MCP Utilities

# MCP Utilities

### MCP Utilities

  * ToolCallback Utility
  * Tool Callback Adapter
  * Tool Callback Providers
  * McpToolUtils
  * ToolCallbacks to ToolSpecifications
  * MCP Clients to ToolCallbacks
  * Native Image Support

The MCP utilities provide foundational support for integrating Model Context
Protocol with Spring AI applications. These utilities enable seamless
communication between Spring AI’s tool system and MCP servers, supporting both
synchronous and asynchronous operations. They are typically used for
programmatic MCP Client and Server configuration and interaction. For a more
streamlined configuration, consider using the boot starters.

##  ToolCallback Utility

###  Tool Callback Adapter

Adapts MCP tools to Spring AI’s tool interface with both synchronous and
asynchronous execution support.

  * Sync
  * Async

```

McpSyncClient mcpClient = // obtain MCP client

Tool mcpTool = // obtain MCP tool definition

ToolCallback callback = new SyncMcpToolCallback(mcpClient, mcpTool);

// Use the tool through Spring AI's interfaces

ToolDefinition definition = callback.getToolDefinition();

String result = callback.call("{\"param\": \"value\"}");

Copied!

```

```

McpAsyncClient mcpClient = // obtain MCP client

Tool mcpTool = // obtain MCP tool definition

ToolCallback callback = new AsyncMcpToolCallback(mcpClient, mcpTool);

// Use the tool through Spring AI's interfaces

ToolDefinition definition = callback.getToolDefinition();

String result = callback.call("{\"param\": \"value\"}");

Copied!

```

###  Tool Callback Providers

Discovers and provides MCP tools from MCP clients.

  * Sync
  * Async

```

McpSyncClient mcpClient = // obtain MCP client

ToolCallbackProvider provider = new SyncMcpToolCallbackProvider(mcpClient);

// Get all available tools

ToolCallback[] tools = provider.getToolCallbacks();

Copied!

```

For multiple clients:

```

List<McpSyncClient> clients = // obtain list of clients

List<ToolCallback> callbacks =
SyncMcpToolCallbackProvider.syncToolCallbacks(clients);

Copied!

```

```

McpAsyncClient mcpClient = // obtain MCP client

ToolCallbackProvider provider = new AsyncMcpToolCallbackProvider(mcpClient);

// Get all available tools

ToolCallback[] tools = provider.getToolCallbacks();

Copied!

```

For multiple clients:

```

List<McpAsyncClient> clients = // obtain list of clients

Flux<ToolCallback> callbacks =
AsyncMcpToolCallbackProvider.asyncToolCallbacks(clients);

Copied!

```

##  McpToolUtils

###  ToolCallbacks to ToolSpecifications

Converting Spring AI tool callbacks to MCP tool specifications:

  * Sync
  * Async

```

List<ToolCallback> toolCallbacks = // obtain tool callbacks

List<SyncToolSpecifications> syncToolSpecs =
McpToolUtils.toSyncToolSpecifications(toolCallbacks);

Copied!

```

then you can use the `McpServer.SyncSpecification` to register the tool
specifications:

```

McpServer.SyncSpecification syncSpec = ...

syncSpec.tools(syncToolSpecs);

Copied!

```

```

List<ToolCallback> toolCallbacks = // obtain tool callbacks

List<AsyncToolSpecification> asyncToolSpecifications =
McpToolUtils.toAsyncToolSpecifications(toolCallbacks);

Copied!

```

then you can use the `McpServer.AsyncSpecification` to register the tool
specifications:

```

McpServer.AsyncSpecification asyncSpec = ...

asyncSpec.tools(asyncToolSpecifications);

Copied!

```

###  MCP Clients to ToolCallbacks

Getting tool callbacks from MCP clients

  * Sync
  * Async

```

List<McpSyncClient> syncClients = // obtain sync clients

List<ToolCallback> syncCallbacks =
McpToolUtils.getToolCallbacksFromSyncClients(syncClients);

Copied!

```

```

List<McpAsyncClient> asyncClients = // obtain async clients

List<ToolCallback> asyncCallbacks =
McpToolUtils.getToolCallbacksFromAsyncClients(asyncClients);

Copied!

```

##  Native Image Support

The `McpHints` class provides GraalVM native image hints for MCP schema classes.
This class automatically registers all necessary reflection hints for MCP schema
classes when building native images.

MCP Server Boot Starters Retrieval Augmented Generation (RAG)

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

