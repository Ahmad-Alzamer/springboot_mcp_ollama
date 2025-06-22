source URL: https://docs.spring.io/spring-ai/reference/api/chat/oci-genai/cohere-chat.html 
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

### OCI GenAI Cohere Chat

  * Prerequisites
  * Add Repositories and BOM
  * Auto-configuration
  * Chat Properties
  * Runtime Options
  * Sample Controller
  * Manual Configuration

  * Spring AI
  * Reference
  * Models
  * Chat Models
  * OCI Generative AI
  * Cohere

# OCI GenAI Cohere Chat

### OCI GenAI Cohere Chat

  * Prerequisites
  * Add Repositories and BOM
  * Auto-configuration
  * Chat Properties
  * Runtime Options
  * Sample Controller
  * Manual Configuration

OCI GenAI Service offers generative AI chat with on-demand models, or dedicated
AI clusters.

The OCI Chat Models Page and OCI Generative AI Playground provide detailed
information about using and hosting chat models on OCI.

##  Prerequisites

You will need an active Oracle Cloud Infrastructure (OCI) account to use the OCI
GenAI Cohere Chat client. The client offers four different ways to connect,
including simple authentication with a user and private key, workload identity,
instance principal, or OCI configuration file authentication.

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
Spring AI provides Spring Boot auto-configuration for the OCI GenAI Cohere Chat
Client. To enable it add the following dependency to your project’s Maven
`pom.xml` file:

```

<dependency>

    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-starter-model-oci-genai</artifactId>
</dependency>

Copied!

```

or to your Gradle `build.gradle` build file.

```

dependencies {

    implementation 'org.springframework.ai:spring-ai-starter-model-oci-genai'
}

Copied!

```

|  Refer to the Dependency Management section to add the Spring AI BOM to your
build file.  
---|---  
###  Chat Properties

####  Connection Properties

The prefix `spring.ai.oci.genai` is the property prefix to configure the
connection to OCI GenAI.

Property | Description | Default  
---|---|---  
spring.ai.oci.genai.authenticationType | The type of authentication to use when authenticating to OCI. May be `file`, `instance-principal`, `workload-identity`, or `simple`. | file  
spring.ai.oci.genai.region | OCI service region. | us-chicago-1  
spring.ai.oci.genai.tenantId | OCI tenant OCID, used when authenticating with `simple` auth. | -  
spring.ai.oci.genai.userId | OCI user OCID, used when authenticating with `simple` auth. | -  
spring.ai.oci.genai.fingerprint | Private key fingerprint, used when authenticating with `simple` auth. | -  
spring.ai.oci.genai.privateKey | Private key content, used when authenticating with `simple` auth. | -  
spring.ai.oci.genai.passPhrase | Optional private key passphrase, used when authenticating with `simple` auth and a passphrase protected private key. | -  
spring.ai.oci.genai.file | Path to OCI config file. Used when authenticating with `file` auth. | <user’s home directory>/.oci/config  
spring.ai.oci.genai.profile | OCI profile name. Used when authenticating with `file` auth. | DEFAULT  
spring.ai.oci.genai.endpoint | Optional OCI GenAI endpoint. | -  
####  Configuration Properties

|  Enabling and disabling of the chat auto-configurations are now configured via
top level properties with the prefix `spring.ai.model.chat`. To enable,
spring.ai.model.chat=oci-genai (It is enabled by default) To disable,
spring.ai.model.chat=none (or any value which doesn’t match oci-genai) This
change is done to allow configuration of multiple models.  
---|---  
The prefix `spring.ai.oci.genai.chat.cohere` is the property prefix that
configures the `ChatModel` implementation for OCI GenAI Cohere Chat.

Property | Description | Default  
---|---|---  
spring.ai.model.chat | Enable OCI GenAI Cohere chat model. | oci-genai  
spring.ai.oci.genai.chat.cohere.enabled (no longer valid) | Enable OCI GenAI Cohere chat model. | true  
spring.ai.oci.genai.chat.cohere.options.model | Model OCID or endpoint | -  
spring.ai.oci.genai.chat.cohere.options.compartment | Model compartment OCID. | -  
spring.ai.oci.genai.chat.cohere.options.servingMode | The model serving mode to be used. May be `on-demand`, or `dedicated`. | on-demand  
spring.ai.oci.genai.chat.cohere.options.preambleOverride | Override the chat model’s prompt preamble | -  
spring.ai.oci.genai.chat.cohere.options.temperature | Inference temperature | -  
spring.ai.oci.genai.chat.cohere.options.topP | Top P parameter | -  
spring.ai.oci.genai.chat.cohere.options.topK | Top K parameter | -  
spring.ai.oci.genai.chat.cohere.options.frequencyPenalty | Higher values will reduce repeated tokens and outputs will be more random. | -  
spring.ai.oci.genai.chat.cohere.options.presencePenalty | Higher values encourage generating outputs with tokens that haven’t been used. | -  
spring.ai.oci.genai.chat.cohere.options.stop | List of textual sequences that will end completions generation. | -  
spring.ai.oci.genai.chat.cohere.options.documents | List of documents used in chat context. | -  
|  All properties prefixed with `spring.ai.oci.genai.chat.cohere.options` can be
overridden at runtime by adding a request specific Runtime Options to the
`Prompt` call.  
---|---  
##  Runtime Options

The OCICohereChatOptions.java provides model configurations, such as the model
to use, the temperature, the frequency penalty, etc.

On start-up, the default options can be configured with the
`OCICohereChatModel(api, options)` constructor or the
`spring.ai.oci.genai.chat.cohere.options.*` properties.

At run-time you can override the default options by adding new, request
specific, options to the `Prompt` call. For example to override the default
model and temperature for a specific request:

```

ChatResponse response = chatModel.call(

    new Prompt(
        "Generate the names of 5 famous pirates.",
        OCICohereChatOptions.builder()
            .model("my-model-ocid")
            .compartment("my-compartment-ocid")
            .temperature(0.5)
        .build()
    ));
Copied!

```

##  Sample Controller

Create a new Spring Boot project and add the `spring-ai-starter-model-oci-genai`
to your pom (or gradle) dependencies.

Add a `application.properties` file, under the `src/main/resources` directory,
to enable and configure the OCI GenAI Cohere chat model:

```

spring.ai.oci.genai.authenticationType=file

spring.ai.oci.genai.file=/path/to/oci/config/file

spring.ai.oci.genai.cohere.chat.options.compartment=my-compartment-ocid

spring.ai.oci.genai.cohere.chat.options.servingMode=on-demand

spring.ai.oci.genai.cohere.chat.options.model=my-chat-model-ocid

Copied!

```

|  replace the `file`, `compartment`, and `model` with your values from your OCI
account.  
---|---  
This will create a `OCICohereChatModel` implementation that you can inject into
your class. Here is an example of a simple `@Controller` class that uses the
chat model for text generations.

```

@RestController

public class ChatController {

    private final OCICohereChatModel chatModel;

    @Autowired
    public ChatController(OCICohereChatModel chatModel) {
        this.chatModel = chatModel;
    }

    @GetMapping("/ai/generate")
    public Map generate(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
        return Map.of("generation", chatModel.call(message));
    }

    @GetMapping("/ai/generateStream")
	public Flux<ChatResponse> generateStream(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
        var prompt = new Prompt(new UserMessage(message));
        return chatModel.stream(prompt);
    }
}

Copied!

```

##  Manual Configuration

The OCICohereChatModel implements the `ChatModel` and uses the OCI Java SDK to
connect to the OCI GenAI service.

Add the `spring-ai-oci-genai` dependency to your project’s Maven `pom.xml` file:

```

<dependency>

    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-oci-genai</artifactId>
</dependency>

Copied!

```

or to your Gradle `build.gradle` build file.

```

dependencies {

    implementation 'org.springframework.ai:spring-ai-oci-genai'
}

Copied!

```

|  Refer to the Dependency Management section to add the Spring AI BOM to your
build file.  
---|---  
Next, create a `OCICohereChatModel` and use it for text generations:

```

var CONFIG_FILE = Paths.get(System.getProperty("user.home"), ".oci",
"config").toString();

var COMPARTMENT_ID = System.getenv("OCI_COMPARTMENT_ID");

var MODEL_ID = System.getenv("OCI_CHAT_MODEL_ID");

ConfigFileAuthenticationDetailsProvider authProvider = new
ConfigFileAuthenticationDetailsProvider(

        CONFIG_FILE,
        "DEFAULT"
);

var genAi = GenerativeAiInferenceClient.builder()

        .region(Region.valueOf("us-chicago-1"))
        .build(authProvider);

var chatModel = new OCICohereChatModel(genAi, OCICohereChatOptions.builder()

        .model(MODEL_ID)
        .compartment(COMPARTMENT_ID)
        .servingMode("on-demand")
        .build());

ChatResponse response = chatModel.call(

        new Prompt("Generate the names of 5 famous pirates."));
Copied!

```

The `OCICohereChatOptions` provides the configuration information for the chat
requests. The `OCICohereChatOptions.Builder` is fluent options builder.

Perplexity AI OpenAI

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

