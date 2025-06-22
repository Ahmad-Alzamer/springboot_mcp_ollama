source URL: https://docs.spring.io/spring-ai/reference/api/audio/transcriptions/azure-openai-transcriptions.html 
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

### Azure OpenAI Transcriptions

  * Prerequisites
  * Auto-configuration
  * Transcription Properties
  * Runtime Options
  * Manual Configuration

  * Spring AI
  * Reference
  * Models
  * Audio Models
  * Transcription API
  * Azure OpenAI

# Azure OpenAI Transcriptions

### Azure OpenAI Transcriptions

  * Prerequisites
  * Auto-configuration
  * Transcription Properties
  * Runtime Options
  * Manual Configuration

Spring AI supports Azure Whisper model.

##  Prerequisites

Obtain your Azure OpenAI `endpoint` and `api-key` from the Azure OpenAI Service
section on the Azure Portal. Spring AI defines a configuration property named
`spring.ai.azure.openai.api-key` that you should set to the value of the `API
Key` obtained from Azure. There is also a configuration property named
`spring.ai.azure.openai.endpoint` that you should set to the endpoint URL
obtained when provisioning your model in Azure. Exporting an environment
variable is one way to set that configuration property:

##  Auto-configuration

|  There has been a significant change in the Spring AI auto-configuration,
starter modules' artifact names. Please refer to the upgrade notes for more
information.  
---|---  
Spring AI provides Spring Boot auto-configuration for the Azure OpenAI
Transcription Generation Client. To enable it, add the following dependency to
your project’s Maven `pom.xml` file:

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
###  Transcription Properties

|  Enabling and disabling of the audio transcription auto-configurations are now
configured via top level properties with the prefix
`spring.ai.model.audio.transcription`. To enable,
spring.ai.model.audio.transcription=azure-openai (It is enabled by default) To
disable, spring.ai.model.audio.transcription=none (or any value which doesn’t
match azure-openai) This change is done to allow configuration of multiple
models.  
---|---  
The prefix `spring.ai.openai.audio.transcription` is used as the property prefix
that lets you configure the retry mechanism for the OpenAI image model.

Property | Description | Default  
---|---|---  
spring.ai.azure.openai.audio.transcription.enabled (Removed and no longer valid) | Enable Azure OpenAI transcription model. | true  
spring.ai.model.audio.transcription | Enable Azure OpenAI transcription model. | azure-openai  
spring.ai.azure.openai.audio.transcription.options.model | ID of the model to use. Only whisper is currently available. | whisper  
spring.ai.azure.openai.audio.transcription.options.deployment-name | The deployment name under which the model is deployed. |   
spring.ai.azure.openai.audio.transcription.options.response-format | The format of the transcript output, in one of these options: json, text, srt, verbose_json, or vtt. | json  
spring.ai.azure.openai.audio.transcription.options.prompt | An optional text to guide the model’s style or continue a previous audio segment. The prompt should match the audio language. |   
spring.ai.azure.openai.audio.transcription.options.language | The language of the input audio. Supplying the input language in ISO-639-1 format will improve accuracy and latency. |   
spring.ai.azure.openai.audio.transcription.options.temperature | The sampling temperature, between 0 and 1. Higher values like 0.8 will make the output more random, while lower values like 0.2 will make it more focused and deterministic. If set to 0, the model will use log probability to automatically increase the temperature until certain thresholds are hit. | 0  
spring.ai.azure.openai.audio.transcription.options.timestamp-granularities | The timestamp granularities to populate for this transcription. response_format must be set verbose_json to use timestamp granularities. Either or both of these options are supported: word, or segment. Note: There is no additional latency for segment timestamps, but generating word timestamps incurs additional latency. | segment  
##  Runtime Options

The `AzureOpenAiAudioTranscriptionOptions` class provides the options to use
when making a transcription. On start-up, the options specified by
`spring.ai.azure.openai.audio.transcription` are used, but you can override
these at runtime.

For example:

```

AzureOpenAiAudioTranscriptionOptions.TranscriptResponseFormat responseFormat =
AzureOpenAiAudioTranscriptionOptions.TranscriptResponseFormat.VTT;

AzureOpenAiAudioTranscriptionOptions transcriptionOptions =
AzureOpenAiAudioTranscriptionOptions.builder()

    .language("en")
    .prompt("Ask not this, but ask that")
    .temperature(0f)
    .responseFormat(this.responseFormat)
    .build();
AudioTranscriptionPrompt transcriptionRequest = new
AudioTranscriptionPrompt(audioFile, this.transcriptionOptions);

AudioTranscriptionResponse response =
azureOpenAiTranscriptionModel.call(this.transcriptionRequest);

Copied!

```

##  Manual Configuration

Add the `spring-ai-openai` dependency to your project’s Maven `pom.xml` file:

```

<dependency>

    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-azure-openai</artifactId>
</dependency>

Copied!

```

or to your Gradle `build.gradle` build file.

```

dependencies {

    implementation 'org.springframework.ai:spring-ai-azure-openai'
}

Copied!

```

|  Refer to the Dependency Management section to add the Spring AI BOM to your
build file.  
---|---  
Next, create a `AzureOpenAiAudioTranscriptionModel`

```

var openAIClient = new OpenAIClientBuilder()

    .credential(new AzureKeyCredential(System.getenv("AZURE_OPENAI_API_KEY")))
    .endpoint(System.getenv("AZURE_OPENAI_ENDPOINT"))
    .buildClient();

var azureOpenAiAudioTranscriptionModel = new
AzureOpenAiAudioTranscriptionModel(this.openAIClient, null);

var transcriptionOptions = AzureOpenAiAudioTranscriptionOptions.builder()

    .responseFormat(TranscriptResponseFormat.TEXT)
    .temperature(0f)
    .build();

var audioFile = new
FileSystemResource("/path/to/your/resource/speech/jfk.flac");

AudioTranscriptionPrompt transcriptionRequest = new
AudioTranscriptionPrompt(this.audioFile, this.transcriptionOptions);

AudioTranscriptionResponse response =
this.azureOpenAiAudioTranscriptionModel.call(this.transcriptionRequest);

Copied!

```

Transcription API OpenAI

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

