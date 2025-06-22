source URL: https://docs.spring.io/spring-ai/reference/api/image/azure-openai-image.html 
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

### Azure OpenAI Image Generation

  * Prerequisites
  * Deployment Name
  * Add Repositories and BOM
  * Auto-configuration
  * Image Generation Properties
  * Runtime Options

  * Spring AI
  * Reference
  * Models
  * Image Models
  * Azure OpenAI

# Azure OpenAI Image Generation

### Azure OpenAI Image Generation

  * Prerequisites
  * Deployment Name
  * Add Repositories and BOM
  * Auto-configuration
  * Image Generation Properties
  * Runtime Options

Spring AI supports DALL-E, the Image generation model from Azure OpenAI.

##  Prerequisites

Obtain your Azure OpenAI `endpoint` and `api-key` from the Azure OpenAI Service
section on the Azure Portal.

Spring AI defines two configuration properties:

  1. `spring.ai.azure.openai.api-key`: Set this to the value of the `API Key` obtained from Azure.
  2. `spring.ai.azure.openai.endpoint`: Set this to the endpoint URL obtained when provisioning your model in Azure.

You can set these configuration properties in your `application.properties`
file:

```

spring.ai.azure.openai.api-key=<your-azure-openai-api-key>

spring.ai.azure.openai.endpoint=<your-azure-openai-endpoint>

Copied!

```

For enhanced security when handling sensitive information like API keys, you can
use Spring Expression Language (SpEL) to reference custom environment variables:

```

# In application.yml

spring:

  ai:

    azure:
      openai:
        api-key: ${AZURE_OPENAI_API_KEY}
        endpoint: ${AZURE_OPENAI_ENDPOINT}
Copied!

```

```

# In your environment or .env file

export AZURE_OPENAI_API_KEY=<your-azure-openai-api-key>

export AZURE_OPENAI_ENDPOINT=<your-azure-openai-endpoint>

Copied!

```

You can also set these configurations programmatically in your application code:

```

// Retrieve API key and endpoint from secure sources or environment variables

String apiKey = System.getenv("AZURE_OPENAI_API_KEY");

String endpoint = System.getenv("AZURE_OPENAI_ENDPOINT");

Copied!

```

###  Deployment Name

To use run Azure AI applications, create an Azure AI Deployment through the
[Azure AI Portal](oai.azure.com/portal).

In Azure, each client must specify a `Deployment Name` to connect to the Azure
OpenAI service.

It’s essential to understand that the `Deployment Name` is different from the
model you choose to deploy

For instance, a deployment named 'MyImgAiDeployment' could be configured to use
either the `Dalle3` model or the `Dalle2` model.

For now, to keep things simple, you can create a deployment using the following
settings:

Deployment Name: `MyImgAiDeployment` Model Name: `Dalle3`

This Azure configuration will align with the default configurations of the
Spring Boot Azure AI Starter and its Autoconfiguration feature.

If you use a different Deployment Name, update the configuration property
accordingly:

```

spring.ai.azure.openai.image.options.deployment-name=<my deployment name>

Copied!

```

The different deployment structures of Azure OpenAI and OpenAI leads to a
property in the Azure OpenAI client library named `deploymentOrModelName`. This
is because in OpenAI there is no `Deployment Name`, only a `Model Name`.

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
Spring AI provides Spring Boot auto-configuration for the Azure OpenAI Chat
Client. To enable it add the following dependency to your project’s Maven
`pom.xml` file:

```

<dependency>

    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-starter-model-azure-openai</artifactId>
</dependency>

Copied!

```

or to your Gradle `build.gradle` build file.

```

dependencies {

    implementation 'org.springframework.ai:spring-ai-starter-model-azure-openai'
}

Copied!

```

|  Refer to the Dependency Management section to add the Spring AI BOM to your
build file.  
---|---  
###  Image Generation Properties

|  Enabling and disabling of the image auto-configurations are now configured
via top level properties with the prefix `spring.ai.model.image`. To enable,
spring.ai.model.image=azure-openai (It is enabled by default) To disable,
spring.ai.model.image=none (or any value which doesn’t match azure-openai) This
change is done to allow configuration of multiple models.  
---|---  
The prefix `spring.ai.openai.image` is the property prefix that lets you
configure the `ImageModel` implementation for OpenAI.

Property | Description | Default  
---|---|---  
spring.ai.azure.openai.image.enabled (Removed and no longer valid) | Enable OpenAI image model. | true  
spring.ai.model.image | Enable OpenAI image model. | azure-openai  
spring.ai.azure.openai.image.options.n | The number of images to generate. Must be between 1 and 10. For dall-e-3, only n=1 is supported. | -  
spring.ai.azure.openai.image.options.model | The model to use for image generation. | AzureOpenAiImageOptions.DEFAULT_IMAGE_MODEL  
spring.ai.azure.openai.image.options.quality | The quality of the image that will be generated. HD creates images with finer details and greater consistency across the image. This parameter is only supported for dall-e-3. | -  
spring.ai.azure.openai.image.options.response_format | The format in which the generated images are returned. Must be one of URL or b64_json. | -  
`spring.ai.openai.image.options.size` | The size of the generated images. Must be one of 256x256, 512x512, or 1024x1024 for dall-e-2. Must be one of 1024x1024, 1792x1024, or 1024x1792 for dall-e-3 models. | -  
`spring.ai.openai.image.options.size_width` | The width of the generated images. Must be one of 256, 512, or 1024 for dall-e-2. | -  
`spring.ai.openai.image.options.size_height` | The height of the generated images. Must be one of 256, 512, or 1024 for dall-e-2. | -  
`spring.ai.openai.image.options.style` | The style of the generated images. Must be one of vivid or natural. Vivid causes the model to lean towards generating hyper-real and dramatic images. Natural causes the model to produce more natural, less hyper-real looking images. This parameter is only supported for dall-e-3. | -  
`spring.ai.openai.image.options.user` | A unique identifier representing your end-user, which can help Azure OpenAI to monitor and detect abuse. | -  
####  Connection Properties

The prefix `spring.ai.openai` is used as the property prefix that lets you
connect to Azure OpenAI.

Property | Description | Default  
---|---|---  
spring.ai.azure.openai.endpoint | The URL to connect to | my-dalle3.openai.azure.com/  
spring.ai.azure.openai.apiKey | The API Key | -  
##  Runtime Options

The OpenAiImageOptions.java provides model configurations, such as the model to
use, the quality, the size, etc.

On start-up, the default options can be configured with the
`AzureOpenAiImageModel(OpenAiImageApi openAiImageApi)` constructor and the
`withDefaultOptions(OpenAiImageOptions defaultOptions)` method. Alternatively,
use the `spring.ai.azure.openai.image.options.*` properties described
previously.

At runtime you can override the default options by adding new, request specific,
options to the `ImagePrompt` call. For example to override the OpenAI specific
options such as quality and the number of images to create, use the following
code example:

```

ImageResponse response = azureOpenaiImageModel.call(

        new ImagePrompt("A light cream colored mini golden doodle",
        OpenAiImageOptions.builder()
                .quality("hd")
                .N(4)
                .height(1024)
                .width(1024).build())

);

Copied!

```

|  In addition to the model specific AzureOpenAiImageOptions you can use a
portable ImageOptions instance, created with the ImageOptionsBuilder#builder().  
---|---  
Image Models OpenAI

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

