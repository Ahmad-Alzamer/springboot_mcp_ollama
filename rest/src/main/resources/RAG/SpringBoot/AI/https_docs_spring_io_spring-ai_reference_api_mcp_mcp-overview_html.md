source URL: https://docs.spring.io/spring-ai/reference/api/mcp/mcp-overview.html 
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

### Model Context Protocol (MCP)

  * MCP Java SDK Architecture
  * Spring AI MCP Integration
  * Client Starters
  * Server Starters
  * Additional Resources

  * Spring AI
  * Reference
  * Model Context Protocol (MCP)

# Model Context Protocol (MCP)

### Model Context Protocol (MCP)

  * MCP Java SDK Architecture
  * Spring AI MCP Integration
  * Client Starters
  * Server Starters
  * Additional Resources

The Model Context Protocol (MCP) is a standardized protocol that enables AI
models to interact with external tools and resources in a structured way. It
supports multiple transport mechanisms to provide flexibility across different
environments.

The MCP Java SDK provides a Java implementation of the Model Context Protocol,
enabling standardized interaction with AI models and tools through both
synchronous and asynchronous communication patterns.

`**Spring AI MCP**`extends the MCP Java SDK with Spring Boot integration,
providing bothclient and server starters. Bootstrap your AI applications with
MCP support using Spring Initializer.

|  Breaking Changes in MCP Java SDK 0.8.0 ⚠️ MCP Java SDK version 0.8.0
introduces several breaking changes including a new session-based architecture.
If you’re upgrading from Java SDK 0.7.0, please refer to the Migration Guide for
detailed instructions.  
---|---  
##  MCP Java SDK Architecture

|  This section provides an overview for the MCP Java SDK architecture. For the
Spring AI MCP integration, refer to the Spring AI MCP Boot Starters
documentation.  
---|---  
The Java MCP implementation follows a three-layer architecture:

|  
---|---  
|

  * **Client/Server Layer** : The McpClient handles client-side operations while the McpServer manages server-side protocol operations. Both utilize McpSession for communication management.
  * **Session Layer (McpSession)** : Manages communication patterns and state through the DefaultMcpSession implementation.
  * **Transport Layer (McpTransport)** : Handles JSON-RPC message serialization and deserialization with support for multiple transport implementations.

  
MCP Client |   
---|---  
The MCP Client is a key component in the Model Context Protocol (MCP)
architecture, responsible for establishing and managing connections with MCP
servers. It implements the client-side of the protocol, handling:

  * Protocol version negotiation to ensure compatibility with servers
  * Capability negotiation to determine available features
  * Message transport and JSON-RPC communication
  * Tool discovery and execution
  * Resource access and management
  * Prompt system interactions
  * Optional features:
    * Roots management
    * Sampling support
  * Synchronous and asynchronous operations
  * Transport options:
    * Stdio-based transport for process-based communication
    * Java HttpClient-based SSE client transport
    * WebFlux SSE client transport for reactive HTTP streaming

|  
MCP Server |   
---|---  
The MCP Server is a foundational component in the Model Context Protocol (MCP)
architecture that provides tools, resources, and capabilities to clients. It
implements the server-side of the protocol, responsible for:

  * Server-side protocol operations implementation
    * Tool exposure and discovery
    * Resource management with URI-based access
    * Prompt template provision and handling
    * Capability negotiation with clients
    * Structured logging and notifications
  * Concurrent client connection management
  * Synchronous and Asynchronous API support
  * Transport implementations:
    * Stdio-based transport for process-based communication
    * Servlet-based SSE server transport
    * WebFlux SSE server transport for reactive HTTP streaming
    * WebMVC SSE server transport for servlet-based HTTP streaming

|  
For detailed implementation guidance, using the low-level MCP Client/Server
APIs, refer to the MCP Java SDK documentation. For simplified setup using Spring
Boot, use the MCP Boot Starters described below.

##  Spring AI MCP Integration

Spring AI provides MCP integration through the following Spring Boot starters:

###  Client Starters

  * `spring-ai-starter-mcp-client` - Core starter providing STDIO and HTTP-based SSE support
  * `spring-ai-starter-mcp-client-webflux` - WebFlux-based SSE transport implementation

###  Server Starters

  * `spring-ai-starter-mcp-server` - Core server with STDIO transport support
  * `spring-ai-starter-mcp-server-webmvc` - Spring MVC-based SSE transport implementation
  * `spring-ai-starter-mcp-server-webflux` - WebFlux-based SSE transport implementation

##  Additional Resources

  * MCP Client Boot Starters Documentation
  * MCP Server Boot Starters Documentation
  * MCP Utilities Documentation
  * Model Context Protocol Specification

Tool Calling MCP Client Boot Starters

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

