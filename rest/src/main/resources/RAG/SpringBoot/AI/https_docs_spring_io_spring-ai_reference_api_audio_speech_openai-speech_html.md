source URL: https://docs.spring.io/spring-ai/reference/api/audio/speech/openai-speech.html 
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

### OpenAI Text-to-Speech (TTS)

  * Introduction
  * Prerequisites
  * Auto-configuration
  * Speech Properties
  * Connection Properties
  * Configuration Properties
  * Runtime Options
  * Manual Configuration
  * Streaming Real-time Audio
  * Example Code

  * Spring AI
  * Reference
  * Models
  * Audio Models
  * Text-To-Speech (TTS) API
  * OpenAI

# OpenAI Text-to-Speech (TTS)

### OpenAI Text-to-Speech (TTS)

  * Introduction
  * Prerequisites
  * Auto-configuration
  * Speech Properties
  * Connection Properties
  * Configuration Properties
  * Runtime Options
  * Manual Configuration
  * Streaming Real-time Audio
  * Example Code

##  Introduction

The Audio API provides a speech endpoint based on OpenAI’s TTS (text-to-speech)
model, enabling users to:

  * Narrate a written blog post.
  * Produce spoken audio in multiple languages.
  * Give real-time audio output using streaming.

##  Prerequisites

  1. Create an OpenAI account and obtain an API key. You can sign up at the OpenAI signup page and generate an API key on the API Keys page.
  2. Add the `spring-ai-openai` dependency to your project’s build file. For more information, refer to the Dependency Management section.

##  Auto-configuration

|  There has been a significant change in the Spring AI auto-configuration,
starter modules' artifact names. Please refer to the upgrade notes for more
information.  
---|---  
Spring AI provides Spring Boot auto-configuration for the OpenAI Text-to-Speech
Client. To enable it add the following dependency to your project’s Maven
`pom.xml` file:

```

<dependency>

    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-starter-model-openai</artifactId>
</dependency>

Copied!

```

or to your Gradle `build.gradle` build file:

```

dependencies {

    implementation 'org.springframework.ai:spring-ai-starter-model-openai'
}

Copied!

```

|  Refer to the Dependency Management section to add the Spring AI BOM to your
build file.  
---|---  
##  Speech Properties

###  Connection Properties

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
###  Configuration Properties

|  Enabling and disabling of the audio speech auto-configurations are now
configured via top level properties with the prefix
`spring.ai.model.audio.speech`. To enable, spring.ai.model.audio.speech=openai
(It is enabled by default) To disable, spring.ai.model.audio.speech=none (or any
value which doesn’t match openai) This change is done to allow configuration of
multiple models.  
---|---  
The prefix `spring.ai.openai.audio.speech` is used as the property prefix that
lets you configure the OpenAI Text-to-Speech client.

Property | Description | Default  
---|---|---  
spring.ai.model.audio.speech | Enable Audio Speech Model | openai  
spring.ai.openai.audio.speech.base-url | The URL to connect to | api.openai.com  
spring.ai.openai.audio.speech.api-key | The API Key | -  
spring.ai.openai.audio.speech.organization-id | Optionally you can specify which organization used for an API request. | -  
spring.ai.openai.audio.speech.project-id | Optionally, you can specify which project is used for an API request. | -  
spring.ai.openai.audio.speech.options.model | ID of the model to use for generating the audio. For OpenAI’s TTS API, use one of the available models: tts-1 or tts-1-hd. | tts-1  
spring.ai.openai.audio.speech.options.voice | The voice to use for synthesis. For OpenAI’s TTS API, One of the available voices for the chosen model: alloy, echo, fable, onyx, nova, and shimmer. | alloy  
spring.ai.openai.audio.speech.options.response-format | The format of the audio output. Supported formats are mp3, opus, aac, flac, wav, and pcm. | mp3  
spring.ai.openai.audio.speech.options.speed | The speed of the voice synthesis. The acceptable range is from 0.25 (slowest) to 4.0 (fastest). | 1.0  
|  You can override the common `spring.ai.openai.base-url`,
`spring.ai.openai.api-key`, `spring.ai.openai.organization-id` and
`spring.ai.openai.project-id` properties. The
`spring.ai.openai.audio.speech.base-url`, `spring.ai.openai.audio.speech.api-
key`, `spring.ai.openai.audio.speech.organization-id` and
`spring.ai.openai.audio.speech.project-id` properties if set take precedence
over the common properties. This is useful if you want to use different OpenAI
accounts for different models and different model endpoints.  
---|---  
|  All properties prefixed with `spring.ai.openai.image.options` can be
overridden at runtime.  
---|---  
##  Runtime Options

The `OpenAiAudioSpeechOptions` class provides the options to use when making a
text-to-speech request. On start-up, the options specified by
`spring.ai.openai.audio.speech` are used but you can override these at runtime.

For example:

```

OpenAiAudioSpeechOptions speechOptions = OpenAiAudioSpeechOptions.builder()

    .model("tts-1")
    .voice(OpenAiAudioApi.SpeechRequest.Voice.ALLOY)
    .responseFormat(OpenAiAudioApi.SpeechRequest.AudioResponseFormat.MP3)
    .speed(1.0f)
    .build();

SpeechPrompt speechPrompt = new SpeechPrompt("Hello, this is a text-to-speech
example.", speechOptions);

SpeechResponse response = openAiAudioSpeechModel.call(speechPrompt);

Copied!

```

##  Manual Configuration

Add the `spring-ai-openai` dependency to your project’s Maven `pom.xml` file:

```

<dependency>

    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-openai</artifactId>
</dependency>

Copied!

```

or to your Gradle `build.gradle` build file:

```

dependencies {

    implementation 'org.springframework.ai:spring-ai-openai'
}

Copied!

```

|  Refer to the Dependency Management section to add the Spring AI BOM to your
build file.  
---|---  
Next, create an `OpenAiAudioSpeechModel`:

```

var openAiAudioApi = new OpenAiAudioApi()

    .apiKey(System.getenv("OPENAI_API_KEY"))
    .build();

var openAiAudioSpeechModel = new OpenAiAudioSpeechModel(openAiAudioApi);

var speechOptions = OpenAiAudioSpeechOptions.builder()

    .responseFormat(OpenAiAudioApi.SpeechRequest.AudioResponseFormat.MP3)
    .speed(1.0f)
    .model(OpenAiAudioApi.TtsModel.TTS_1.value)
    .build();

var speechPrompt = new SpeechPrompt("Hello, this is a text-to-speech example.",
speechOptions);

SpeechResponse response = openAiAudioSpeechModel.call(speechPrompt);

// Accessing metadata (rate limit info)

OpenAiAudioSpeechResponseMetadata metadata = response.getMetadata();

byte[] responseAsBytes = response.getResult().getOutput();

Copied!

```

##  Streaming Real-time Audio

The Speech API provides support for real-time audio streaming using chunk
transfer encoding. This means that the audio is able to be played before the
full file has been generated and made accessible.

```

var openAiAudioApi = new OpenAiAudioApi()

    .apiKey(System.getenv("OPENAI_API_KEY"))
    .build();

var openAiAudioSpeechModel = new OpenAiAudioSpeechModel(openAiAudioApi);

OpenAiAudioSpeechOptions speechOptions = OpenAiAudioSpeechOptions.builder()

    .voice(OpenAiAudioApi.SpeechRequest.Voice.ALLOY)
    .speed(1.0f)
    .responseFormat(OpenAiAudioApi.SpeechRequest.AudioResponseFormat.MP3)
    .model(OpenAiAudioApi.TtsModel.TTS_1.value)
    .build();

SpeechPrompt speechPrompt = new SpeechPrompt("Today is a wonderful day to build
something people love!", speechOptions);

Flux<SpeechResponse> responseStream =
openAiAudioSpeechModel.stream(speechPrompt);

Copied!

```

##  Example Code

  * The OpenAiSpeechModelIT.java test provides some general examples of how to use the library.

Text-To-Speech (TTS) API OpenAI

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

