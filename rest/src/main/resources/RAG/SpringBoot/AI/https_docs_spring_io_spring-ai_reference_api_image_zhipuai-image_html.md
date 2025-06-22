source URL: https://docs.spring.io/spring-ai/reference/api/image/zhipuai-image.html 
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

### ZhiPuAI Image Generation

  * Prerequisites
  * Add Repositories and BOM
  * Auto-configuration
  * Image Generation Properties
  * Runtime Options

  * Spring AI
  * Reference
  * Models
  * Image Models
  * ZhiPuAI

# ZhiPuAI Image Generation

### ZhiPuAI Image Generation

  * Prerequisites
  * Add Repositories and BOM
  * Auto-configuration
  * Image Generation Properties
  * Runtime Options

Spring AI supports CogView, the Image generation model from ZhiPuAI.

##  Prerequisites

You will need to create an API with ZhiPuAI to access ZhiPu AI language models.

Create an account at ZhiPu AI registration page and generate the token on the
API Keys page.

The Spring AI project defines a configuration property named
`spring.ai.zhipuai.api-key` that you should set to the value of the `API Key`
obtained from the API Keys page.

You can set this configuration property in your `application.properties` file:

```

spring.ai.zhipuai.api-key=<your-zhipuai-api-key>

Copied!

```

For enhanced security when handling sensitive information like API keys, you can
use Spring Expression Language (SpEL) to reference a custom environment
variable:

```

# In application.yml

spring:

  ai:

    zhipuai:
      api-key: ${ZHIPUAI_API_KEY}
Copied!

```

```

# In your environment or .env file

export ZHIPUAI_API_KEY=<your-zhipuai-api-key>

Copied!

```

You can also set this configuration programmatically in your application code:

```

// Retrieve API key from a secure source or environment variable

String apiKey = System.getenv("ZHIPUAI_API_KEY");

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
Spring AI provides Spring Boot auto-configuration for the ZhiPuAI Chat Client.
To enable it add the following dependency to your project’s Maven `pom.xml`
file:

```

<dependency>

    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-starter-model-zhipuai</artifactId>
</dependency>

Copied!

```

or to your Gradle `build.gradle` build file.

```

dependencies {

    implementation 'org.springframework.ai:spring-ai-starter-model-zhipuai'
}

Copied!

```

|  Refer to the Dependency Management section to add the Spring AI BOM to your
build file.  
---|---  
###  Image Generation Properties

|  Enabling and disabling of the image auto-configurations are now configured
via top level properties with the prefix `spring.ai.model.image`. To enable,
spring.ai.model.image=stabilityai (It is enabled by default) To disable,
spring.ai.model.image=none (or any value which doesn’t match stabilityai) This
change is done to allow configuration of multiple models.  
---|---  
The prefix `spring.ai.zhipuai.image` is the property prefix that lets you
configure the `ImageModel` implementation for ZhiPuAI.

Property | Description | Default  
---|---|---  
spring.ai.zhipuai.image.enabled (Removed and no longer valid) | Enable ZhiPuAI image model. | true  
spring.ai.model.image | Enable ZhiPuAI image model. | zhipuai  
spring.ai.zhipuai.image.base-url | Optional overrides the spring.ai.zhipuai.base-url to provide chat specific url | -  
spring.ai.zhipuai.image.api-key | Optional overrides the spring.ai.zhipuai.api-key to provide chat specific api-key | -  
spring.ai.zhipuai.image.options.model | The model to use for image generation. | cogview-3  
spring.ai.zhipuai.image.options.user | A unique identifier representing your end-user, which can help ZhiPuAI to monitor and detect abuse. | -  
####  Connection Properties

The prefix `spring.ai.zhipuai` is used as the property prefix that lets you
connect to ZhiPuAI.

Property | Description | Default  
---|---|---  
spring.ai.zhipuai.base-url | The URL to connect to | open.bigmodel.cn/api/paas  
spring.ai.zhipuai.api-key | The API Key | -  
####  Configuration Properties

####  Retry Properties

The prefix `spring.ai.retry` is used as the property prefix that lets you
configure the retry mechanism for the ZhiPuAI Image client.

Property | Description | Default  
---|---|---  
spring.ai.retry.max-attempts | Maximum number of retry attempts. | 10  
spring.ai.retry.backoff.initial-interval | Initial sleep duration for the exponential backoff policy. | 2 sec.  
spring.ai.retry.backoff.multiplier | Backoff interval multiplier. | 5  
spring.ai.retry.backoff.max-interval | Maximum backoff duration. | 3 min.  
spring.ai.retry.on-client-errors | If false, throw a NonTransientAiException, and do not attempt retry for `4xx` client error codes | false  
spring.ai.retry.exclude-on-http-codes | List of HTTP status codes that should not trigger a retry (e.g. to throw NonTransientAiException). | empty  
spring.ai.retry.on-http-codes | List of HTTP status codes that should trigger a retry (e.g. to throw TransientAiException). | empty  
##  Runtime Options

The ZhiPuAiImageOptions.java provides model configurations, such as the model to
use, the quality, the size, etc.

On start-up, the default options can be configured with the
`ZhiPuAiImageModel(ZhiPuAiImageApi zhiPuAiImageApi)` constructor and the
`withDefaultOptions(ZhiPuAiImageOptions defaultOptions)` method. Alternatively,
use the `spring.ai.zhipuai.image.options.*` properties described previously.

At runtime you can override the default options by adding new, request specific,
options to the `ImagePrompt` call. For example to override the ZhiPuAI specific
options such as quality and the number of images to create, use the following
code example:

```

ImageResponse response = zhiPuAiImageModel.call(

        new ImagePrompt("A light cream colored mini golden doodle",
        ZhiPuAiImageOptions.builder()
                .quality("hd")
                .N(4)
                .height(1024)
                .width(1024).build())

);

Copied!

```

|  In addition to the model specific ZhiPuAiImageOptions you can use a portable
ImageOptions instance, created with the ImageOptionsBuilder#builder().  
---|---  
Stability QianFan

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

