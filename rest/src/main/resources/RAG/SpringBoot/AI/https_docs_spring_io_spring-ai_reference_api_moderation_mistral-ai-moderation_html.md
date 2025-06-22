source URL: https://docs.spring.io/spring-ai/reference/api/moderation/mistral-ai-moderation.html 
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

### Moderation

  * Introduction
  * Prerequisites
  * Auto-configuration
  * Moderation Properties
  * Connection Properties
  * Configuration Properties
  * Runtime Options
  * Manual Configuration
  * Example Code

  * Spring AI
  * Reference
  * Models
  * Moderation Models
  * Mistral AI

# Moderation

### Moderation

  * Introduction
  * Prerequisites
  * Auto-configuration
  * Moderation Properties
  * Connection Properties
  * Configuration Properties
  * Runtime Options
  * Manual Configuration
  * Example Code

##  Introduction

Spring AI supports the new moderation service introduced by Mistral AI and
powered by the Mistral Moderation model. It enables the detection of harmful
text content along several policy dimensions. Follow this link for more
information on the Mistral AI moderation model.

##  Prerequisites

  1. Create an Mistral AI account and obtain an API key. You can sign up at Mistral AI registration page and generate an API key on the API Keys page.
  2. Add the `spring-ai-mistral-ai` dependency to your project’s build file. For more information, refer to the Dependency Management section.

##  Auto-configuration

|  There has been a significant change in the Spring AI auto-configuration,
starter modules' artifact names. Please refer to the upgrade notes for more
information.  
---|---  
Spring AI provides Spring Boot auto-configuration for the Mistral AI Moderation
Model. To enable it add the following dependency to your project’s Maven
`pom.xml` file:

```

<dependency>

    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-starter-model-mistral-ai</artifactId>
</dependency>

Copied!

```

or to your Gradle `build.gradle` build file:

```

dependencies {

    implementation 'org.springframework.ai:spring-ai-starter-model-mistral-ai'
}

Copied!

```

|  Refer to the Dependency Management section to add the Spring AI BOM to your
build file.  
---|---  
##  Moderation Properties

###  Connection Properties

The prefix spring.ai.mistralai is used as the property prefix that lets you
connect to Mistral AI.

Property | Description | Default  
---|---|---  
spring.ai.mistralai.base-url | The URL to connect to | api.mistral.ai  
spring.ai.mistralai.api-key | The API Key | -  
###  Configuration Properties

|  Enabling and disabling of the moderation auto-configurations are now
configured via top level properties with the prefix
`spring.ai.model.moderation`. To enable, spring.ai.model.moderation=mistral (It
is enabled by default) To disable, spring.ai.model.moderation=none (or any value
which doesn’t match mistral) This change is done to allow configuration of
multiple models.  
---|---  
The prefix spring.ai.mistralai.moderation is used as the property prefix for
configuring the Mistral AI moderation model.

Property | Description | Default  
---|---|---  
spring.ai.model.moderation | Enable Moderation model | mistral  
spring.ai.mistralai.moderation.base-url | The URL to connect to | api.mistral.ai  
spring.ai.mistralai.moderation.api-key | The API Key | -  
spring.ai.mistralai.moderation.options.model | ID of the model to use for moderation. | mistral-moderation-latest  
|  You can override the common `spring.ai.mistralai.base-url`,
`spring.ai.mistralai.api-key`, properties. The
`spring.ai.mistralai.moderation.base-url`, `spring.ai.mistralai.moderation.api-
key`, properties, if set, take precedence over the common properties. This is
useful if you want to use different Mistral AI accounts for different models and
different model endpoints.  
---|---  
|  All properties prefixed with `spring.ai.mistralai.moderation.options` can be
overridden at runtime.  
---|---  
##  Runtime Options

The MistralAiModerationOptions class provides the options to use when making a
moderation request. On start-up, the options specified by
spring.ai.mistralai.moderation are used, but you can override these at runtime.

For example:

```

MistralAiModerationOptions moderationOptions =
MistralAiModerationOptions.builder()

    .model("mistral-moderation-latest")
    .build();

ModerationPrompt moderationPrompt = new ModerationPrompt("Text to be moderated",
this.moderationOptions);

ModerationResponse response =
mistralAiModerationModel.call(this.moderationPrompt);

// Access the moderation results

Moderation moderation = moderationResponse.getResult().getOutput();

// Print general information

System.out.println("Moderation ID: " + moderation.getId());

System.out.println("Model used: " + moderation.getModel());

// Access the moderation results (there's usually only one, but it's a list)

for (ModerationResult result : moderation.getResults()) {

    System.out.println("\nModeration Result:");
    System.out.println("Flagged: " + result.isFlagged());

    // Access categories
    Categories categories = this.result.getCategories();
    System.out.println("\nCategories:");
    System.out.println("Law: " + categories.isLaw());
    System.out.println("Financial: " + categories.isFinancial());
    System.out.println("PII: " + categories.isPii());
    System.out.println("Sexual: " + categories.isSexual());
    System.out.println("Hate: " + categories.isHate());
    System.out.println("Harassment: " + categories.isHarassment());
    System.out.println("Self-Harm: " + categories.isSelfHarm());
    System.out.println("Sexual/Minors: " + categories.isSexualMinors());
    System.out.println("Hate/Threatening: " + categories.isHateThreatening());
    System.out.println("Violence/Graphic: " + categories.isViolenceGraphic());
    System.out.println("Self-Harm/Intent: " + categories.isSelfHarmIntent());
    System.out.println("Self-Harm/Instructions: " + categories.isSelfHarmInstructions());
    System.out.println("Harassment/Threatening: " + categories.isHarassmentThreatening());
    System.out.println("Violence: " + categories.isViolence());

    // Access category scores
    CategoryScores scores = this.result.getCategoryScores();
    System.out.println("\nCategory Scores:");
    System.out.println("Law: " + scores.getLaw());
    System.out.println("Financial: " + scores.getFinancial());
    System.out.println("PII: " + scores.getPii());
    System.out.println("Sexual: " + scores.getSexual());
    System.out.println("Hate: " + scores.getHate());
    System.out.println("Harassment: " + scores.getHarassment());
    System.out.println("Self-Harm: " + scores.getSelfHarm());
    System.out.println("Sexual/Minors: " + scores.getSexualMinors());
    System.out.println("Hate/Threatening: " + scores.getHateThreatening());
    System.out.println("Violence/Graphic: " + scores.getViolenceGraphic());
    System.out.println("Self-Harm/Intent: " + scores.getSelfHarmIntent());
    System.out.println("Self-Harm/Instructions: " + scores.getSelfHarmInstructions());
    System.out.println("Harassment/Threatening: " + scores.getHarassmentThreatening());
    System.out.println("Violence: " + scores.getViolence());
}

Copied!

```

##  Manual Configuration

Add the `spring-ai-mistral-ai` dependency to your project’s Maven `pom.xml`
file:

```

<dependency>

    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-mistral-ai</artifactId>
</dependency>

Copied!

```

or to your Gradle `build.gradle` build file:

```

dependencies {

    implementation 'org.springframework.ai:spring-ai-mistral-ai'
}

Copied!

```

|  Refer to the Dependency Management section to add the Spring AI BOM to your
build file.  
---|---  
Next, create an MistralAiModerationModel:

```

MistralAiModerationApi mistralAiModerationApi = new
MistralAiModerationApi(System.getenv("MISTRAL_AI_API_KEY"));

MistralAiModerationModel mistralAiModerationModel = new
MistralAiModerationModel(this.mistralAiModerationApi);

MistralAiModerationOptions moderationOptions =
MistralAiModerationOptions.builder()

    .model("mistral-moderation-latest")
    .build();

ModerationPrompt moderationPrompt = new ModerationPrompt("Text to be moderated",
this.moderationOptions);

ModerationResponse response =
this.mistralAiModerationModel.call(this.moderationPrompt);

Copied!

```

##  Example Code

The `MistralAiModerationModelIT` test provides some general examples of how to
use the library. You can refer to this test for more detailed usage examples.

OpenAI Chat Memory

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

