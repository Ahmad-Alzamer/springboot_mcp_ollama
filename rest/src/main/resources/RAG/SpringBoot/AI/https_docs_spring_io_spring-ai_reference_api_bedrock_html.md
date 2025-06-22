source URL: https://docs.spring.io/spring-ai/reference/api/bedrock.html 
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

### Amazon Bedrock

  * Getting Started
  * Project Dependencies
  * Connect to AWS Bedrock
  * Enable selected Bedrock model

  * Spring AI
  * Reference
  * Models
  * Embedding Models
  * Amazon Bedrock

# Amazon Bedrock

### Amazon Bedrock

  * Getting Started
  * Project Dependencies
  * Connect to AWS Bedrock
  * Enable selected Bedrock model

|  Following the Bedrock recommendations, Spring AI transitioned to using Amazon
Bedrock’s Converse API for all Chat conversation implementations in Spring AI.
The Bedrock Converse API has the following key benefits:

  * Unified Interface: Write your code once and use it with any supported Amazon Bedrock model
  * Model Flexibility: Seamlessly switch between different conversation models without code changes
  * Extended Functionality: Support for model-specific parameters through dedicated structures
  * Tool Support: Native integration with function calling and tool usage capabilities
  * Multimodal Capabilities: Built-in support for vision and other multimodal features
  * Future-Proof: Aligned with Amazon Bedrock’s recommended best practices

The Converse API does not support embedding operations, so these will remain in
the current API and the embedding model functionality in the existing
`InvokeModel API` will be maintained  
---|---  
Amazon Bedrock is a managed service that provides foundation models from various
AI providers, available through a unified API.

Spring AI supports the Embedding AI models available through Amazon Bedrock by
implementing the Spring `EmbeddingModel` interface.

Additionally, Spring AI provides Spring Auto-Configurations and Boot Starters
for all clients, making it easy to bootstrap and configure for the Bedrock
models.

##  Getting Started

There are a few steps to get started

  * Add the Spring Boot starter for Bedrock to your project.
  * Obtain AWS credentials: If you don’t have an AWS account and AWS CLI configured yet, this video guide can help you configure it: AWS CLI & SDK Setup in Less Than 4 Minutes!. You should be able to obtain your access and security keys.
  * Enable the Models to use: Go to Amazon Bedrock and from the Model Access menu on the left, configure access to the models you are going to use.

###  Project Dependencies

Then add the Spring Boot Starter dependency to your project’s Maven `pom.xml`
build file:

```

<dependency>

 <artifactId>spring-ai-starter-model-bedrock</artifactId>

 <groupId>org.springframework.ai</groupId>

</dependency>

Copied!

```

or to your Gradle `build.gradle` build file.

```

dependencies {

    implementation 'org.springframework.ai:spring-ai-starter-model-bedrock'
}

Copied!

```

|  Refer to the Dependency Management section to add the Spring AI BOM to your
build file.  
---|---  
###  Connect to AWS Bedrock

Use the `BedrockAwsConnectionProperties` to configure AWS credentials and
region:

```

spring.ai.bedrock.aws.region=us-east-1

spring.ai.bedrock.aws.access-key=YOUR_ACCESS_KEY

spring.ai.bedrock.aws.secret-key=YOUR_SECRET_KEY

spring.ai.bedrock.aws.timeout=10m

Copied!

```

The `region` property is compulsory.

AWS credentials are resolved in the following order:

  1. Spring-AI Bedrock `spring.ai.bedrock.aws.access-key` and `spring.ai.bedrock.aws.secret-key` properties.
  2. Java System Properties - `aws.accessKeyId` and `aws.secretAccessKey`.
  3. Environment Variables - `AWS_ACCESS_KEY_ID` and `AWS_SECRET_ACCESS_KEY`.
  4. Web Identity Token credentials from system properties or environment variables.
  5. Credential profiles file at the default location (`~/.aws/credentials`) shared by all AWS SDKs and the AWS CLI.
  6. Credentials delivered through the Amazon EC2 container service if the `AWS_CONTAINER_CREDENTIALS_RELATIVE_URI` environment variable is set and the security manager has permission to access the variable.
  7. Instance profile credentials delivered through the Amazon EC2 metadata service or set the `AWS_ACCESS_KEY_ID` and `AWS_SECRET_ACCESS_KEY` environment variables.

AWS region is resolved in the following order:

  1. Spring-AI Bedrock `spring.ai.bedrock.aws.region` property.
  2. Java System Properties - `aws.region`.
  3. Environment Variables - `AWS_REGION`.
  4. Credential profiles file at the default location (`~/.aws/credentials`) shared by all AWS SDKs and the AWS CLI.
  5. Instance profile region delivered through the Amazon EC2 metadata service.

In addition to the standard Spring-AI Bedrock credentials and region properties
configuration, Spring-AI provides support for custom `AwsCredentialsProvider`
and `AwsRegionProvider` beans.

|  For example, using Spring-AI and Spring Cloud for Amazon Web Services at the
same time. Spring-AI is compatible with Spring Cloud for Amazon Web Services
credential configuration.  
---|---  
###  Enable selected Bedrock model

|  By default, all models are disabled. You have to enable the chosen Bedrock
models explicitly using the `spring.ai.bedrock.<model>.embedding.enabled=true`
property.  
---|---  
Here are the supported `<model>`s:

Model  
---  
cohere  
titan (no batch support yet)  
For example, to enable the Bedrock Cohere embedding model, you need to set
`spring.ai.bedrock.cohere.embedding.enabled=true`.

Next, you can use the `spring.ai.bedrock.<model>.embedding.*` properties to
configure each model as provided.

For more information, refer to the documentation below for each supported model.

  * Spring AI Bedrock Cohere Embeddings: `spring.ai.bedrock.cohere.embedding.enabled=true`
  * Spring AI Bedrock Titan Embeddings: `spring.ai.bedrock.titan.embedding.enabled=true`

Embedding Models Cohere

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

