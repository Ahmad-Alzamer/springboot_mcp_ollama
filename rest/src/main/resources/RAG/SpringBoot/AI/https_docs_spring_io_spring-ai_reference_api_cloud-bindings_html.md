source URL: https://docs.spring.io/spring-ai/reference/api/cloud-bindings.html 
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

### Cloud Bindings

  * Available Cloud Bindings

  * Spring AI
  * Guides
  * Deploying to the Cloud

# Cloud Bindings

### Cloud Bindings

  * Available Cloud Bindings

Spring AI provides support for cloud bindings based on the foundations in
spring-cloud-bindings. This allows applications to specify a binding type for a
provider and then express properties using a generic format. The spring-ai cloud
bindings will process these properties and bind them to spring-ai native
properties.

For example, when using `OpenAi`, the binding type is `openai`. Using the
property `spring.ai.cloud.bindings.openai.enabled`, the binding processor can be
enabled or disabled. By default, when specifying a binding type, this property
will be enabled. Configuration for `api-key`, `uri`, `username`, `password`,
etc. can be specified and spring-ai will map them to the corresponding
properties in the supported system.

To enable cloud binding support, include the following dependency in the
application.

```

<dependency>

   <groupId>org.springframework.ai</groupId>

   <artifactId>spring-ai-spring-cloud-bindings</artifactId>

</dependency>

Copied!

```

or to your Gradle `build.gradle` build file.

```

dependencies {

    implementation 'org.springframework.ai:spring-ai-spring-cloud-bindings'
}

Copied!

```

|  Refer to the Dependency Management section to add the Spring AI BOM to your
build file.  
---|---  
##  Available Cloud Bindings

The following are the components for which the cloud binding support is
currently available in the `spring-ai-spring-clou-bindings` module:

Service Type | Binding Type | Source Properties | Target Properties  
---|---|---|---  
`Chroma Vector Store` | `chroma` | `uri`, `username`, `passwor` | `spring.ai.vectorstore.chroma.client.host`, `spring.ai.vectorstore.chroma.client.port`, `spring.ai.vectorstore.chroma.client.username`, `spring.ai.vectorstore.chroma.client.host.password`  
`Mistral AI` | `mistralai` | `api-key`, `uri` | `spring.ai.mistralai.api-key`, `spring.ai.mistralai.base-url`  
`Ollama` | `ollama` | `uri` | `spring.ai.ollama.base-url`  
`OpenAi` | `openai` | `api-key`, `uri` | `spring.ai.openai.api-key`, `spring.ai.openai.base-url`  
`Weaviate` | `weaviate` | `uri`, `api-key` | `spring.ai.vectorstore.weaviate.scheme`, `spring.ai.vectorstore.weaviate.host`, `spring.ai.vectorstore.weaviate.api-key`  
`Tanzu GenAI` | `genai` | `uri`, `api-key`, `model-capabilities` (`chat` and `embedding`), `model-name` | `spring.ai.openai.chat.base-url`, , spring.ai.openai.chat.api-key`, `spring.ai.openai.chat.options.model`, `spring.ai.openai.embedding.base-url`, , spring.ai.openai.embedding.api-key`, `spring.ai.openai.embedding.options.model`  
Building Effective Agents Upgrade Notes

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

