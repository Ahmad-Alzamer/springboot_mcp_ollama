source URL: https://docs.spring.io/spring-ai/reference/api/image/openai-image.html 
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

### OpenAI Image Generation

  * Prerequisites
  * Auto-configuration
  * Image Generation Properties
  * Runtime Options

  * Spring AI
  * Reference
  * Models
  * Image Models
  * OpenAI

# OpenAI Image Generation

### OpenAI Image Generation

  * Prerequisites
  * Auto-configuration
  * Image Generation Properties
  * Runtime Options

Spring AI supports DALL-E, the Image generation model from OpenAI.

##  Prerequisites

You will need to create an API key with OpenAI to access ChatGPT models.

Create an account at OpenAI signup page and generate the token on the API Keys
page.

The Spring AI project defines a configuration property named
`spring.ai.openai.api-key` that you should set to the value of the `API Key`
obtained from openai.com.

You can set this configuration property in your `application.properties` file:

```

spring.ai.openai.api-key=<your-openai-api-key>

Copied!

```

For enhanced security when handling sensitive information like API keys, you can
use Spring Expression Language (SpEL) to reference a custom environment
variable:

```

# In application.yml

spring:

  ai:

    openai:
      api-key: ${OPENAI_API_KEY}
Copied!

```

```

# In your environment or .env file

export OPENAI_API_KEY=<your-openai-api-key>

Copied!

```

You can also set this configuration programmatically in your application code:

```

// Retrieve API key from a secure source or environment variable

String apiKey = System.getenv("OPENAI_API_KEY");

Copied!

```

##  Auto-configuration

|  There has been a significant change in the Spring AI auto-configuration,
starter modules' artifact names. Please refer to the upgrade notes for more
information.  
---|---  
Spring AI provides Spring Boot auto-configuration for the OpenAI Image
Generation Client. To enable it add the following dependency to your project’s
Maven `pom.xml` file:

```

<dependency>

    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-starter-model-openai</artifactId>
</dependency>

Copied!

```

or to your Gradle `build.gradle` build file.

```

dependencies {

    implementation 'org.springframework.ai:spring-ai-starter-model-openai'
}

Copied!

```

|  Refer to the Dependency Management section to add the Spring AI BOM to your
build file.  
---|---  
###  Image Generation Properties

####  Connection Properties

The prefix `spring.ai.openai` is used as the property prefix that lets you
connect to OpenAI.

Property | Description | Default  
---|---|---  
spring.ai.openai.base-url | The URL to connect to | api.openai.com  
spring.ai.openai.api-key | The API Key | -  
spring.ai.openai.organization-id | Optionally you can specify which organization used for an API request. | -  
spring.ai.openai.project-id | Optionally, you can specify which project is used for an API request. | -  
|  For users that belong to multiple organizations (or are accessing their
projects through their legacy user API key), optionally, you can specify which
organization and project is used for an API request. Usage from these API
requests will count as usage for the specified organization and project.  
---|---  
####  Retry Properties

The prefix `spring.ai.retry` is used as the property prefix that lets you
configure the retry mechanism for the OpenAI Image client.

Property | Description | Default  
---|---|---  
spring.ai.retry.max-attempts | Maximum number of retry attempts. | 10  
spring.ai.retry.backoff.initial-interval | Initial sleep duration for the exponential backoff policy. | 2 sec.  
spring.ai.retry.backoff.multiplier | Backoff interval multiplier. | 5  
spring.ai.retry.backoff.max-interval | Maximum backoff duration. | 3 min.  
spring.ai.retry.on-client-errors | If false, throw a NonTransientAiException, and do not attempt retry for `4xx` client error codes | false  
spring.ai.retry.exclude-on-http-codes | List of HTTP status codes that should not trigger a retry (e.g. to throw NonTransientAiException). | empty  
spring.ai.retry.on-http-codes | List of HTTP status codes that should trigger a retry (e.g. to throw TransientAiException). | empty  
####  Configuration Properties

|  Enabling and disabling of the image auto-configurations are now configured
via top level properties with the prefix `spring.ai.model.image`. To enable,
spring.ai.model.image=openai (It is enabled by default) To disable,
spring.ai.model.image=none (or any value which doesn’t match openai) This change
is done to allow configuration of multiple models.  
---|---  
The prefix `spring.ai.openai.image` is the property prefix that lets you
configure the `ImageModel` implementation for OpenAI.

Property | Description | Default  
---|---|---  
spring.ai.openai.image.enabled (Removed and no longer valid) | Enable OpenAI image model. | true  
spring.ai.model.image | Enable OpenAI image model. | openai  
spring.ai.openai.image.base-url | Optional overrides the spring.ai.openai.base-url to provide chat specific url | -  
spring.ai.openai.image.api-key | Optional overrides the spring.ai.openai.api-key to provide chat specific api-key | -  
spring.ai.openai.image.organization-id | Optionally you can specify which organization used for an API request. | -  
spring.ai.openai.image.project-id | Optionally, you can specify which project is used for an API request. | -  
spring.ai.openai.image.options.n | The number of images to generate. Must be between 1 and 10. For dall-e-3, only n=1 is supported. | -  
spring.ai.openai.image.options.model | The model to use for image generation. | OpenAiImageApi.DEFAULT_IMAGE_MODEL  
spring.ai.openai.image.options.quality | The quality of the image that will be generated. HD creates images with finer details and greater consistency across the image. This parameter is only supported for dall-e-3. | -  
spring.ai.openai.image.options.response_format | The format in which the generated images are returned. Must be one of URL or b64_json. | -  
`spring.ai.openai.image.options.size` | The size of the generated images. Must be one of 256x256, 512x512, or 1024x1024 for dall-e-2. Must be one of 1024x1024, 1792x1024, or 1024x1792 for dall-e-3 models. | -  
`spring.ai.openai.image.options.size_width` | The width of the generated images. Must be one of 256, 512, or 1024 for dall-e-2. | -  
`spring.ai.openai.image.options.size_height` | The height of the generated images. Must be one of 256, 512, or 1024 for dall-e-2. | -  
`spring.ai.openai.image.options.style` | The style of the generated images. Must be one of vivid or natural. Vivid causes the model to lean towards generating hyper-real and dramatic images. Natural causes the model to produce more natural, less hyper-real looking images. This parameter is only supported for dall-e-3. | -  
`spring.ai.openai.image.options.user` | A unique identifier representing your end-user, which can help OpenAI to monitor and detect abuse. | -  
|  You can override the common `spring.ai.openai.base-url`,
`spring.ai.openai.api-key`, `spring.ai.openai.organization-id` and
`spring.ai.openai.project-id` properties. The `spring.ai.openai.image.base-url`,
`spring.ai.openai.image.api-key`, `spring.ai.openai.image.organization-id` and
`spring.ai.openai.image.project-id` properties if set take precedence over the
common properties. This is useful if you want to use different OpenAI accounts
for different models and different model endpoints.  
---|---  
|  All properties prefixed with `spring.ai.openai.image.options` can be
overridden at runtime.  
---|---  
##  Runtime Options

The OpenAiImageOptions.java provides model configurations, such as the model to
use, the quality, the size, etc.

On start-up, the default options can be configured with the
`OpenAiImageModel(OpenAiImageApi openAiImageApi)` constructor and the
`withDefaultOptions(OpenAiImageOptions defaultOptions)` method. Alternatively,
use the `spring.ai.openai.image.options.*` properties described previously.

At runtime you can override the default options by adding new, request specific,
options to the `ImagePrompt` call. For example to override the OpenAI specific
options such as quality and the number of images to create, use the following
code example:

```

ImageResponse response = openaiImageModel.call(

        new ImagePrompt("A light cream colored mini golden doodle",
        OpenAiImageOptions.builder()
                .quality("hd")
                .N(4)
                .height(1024)
                .width(1024).build())

);

Copied!

```

|  In addition to the model specific OpenAiImageOptions you can use a portable
ImageOptions instance, created with the ImageOptionsBuilder#builder().  
---|---  
Azure OpenAI Stability

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

