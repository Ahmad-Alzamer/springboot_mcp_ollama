source URL: https://docs.spring.io/spring-ai/reference/api/embeddings/oci-genai-embeddings.html 
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

### Oracle Cloud Infrastructure (OCI) GenAI Embeddings

  * Prerequisites
  * Add Repositories and BOM
  * Auto-configuration
  * Embedding Properties
  * Runtime Options
  * Sample Code
  * Manual Configuration

  * Spring AI
  * Reference
  * Models
  * Embedding Models
  * OCI GenAI

# Oracle Cloud Infrastructure (OCI) GenAI Embeddings

### Oracle Cloud Infrastructure (OCI) GenAI Embeddings

  * Prerequisites
  * Add Repositories and BOM
  * Auto-configuration
  * Embedding Properties
  * Runtime Options
  * Sample Code
  * Manual Configuration

OCI GenAI Service offers text embedding with on-demand models, or dedicated AI
clusters.

The OCI Embedding Models Page and OCI Text Embeddings Page provide detailed
information about using and hosting embedding models on OCI.

##  Prerequisites

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
Spring AI provides Spring Boot auto-configuration for the OCI GenAI Embedding
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
###  Embedding Properties

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
|  Enabling and disabling of the embedding auto-configurations are now
configured via top level properties with the prefix `spring.ai.model.embedding`.
To enable, spring.ai.model.embedding=oci-genai (It is enabled by default) To
disable, spring.ai.model.embedding=none (or any value which doesn’t match oci-
genai) This change is done to allow configuration of multiple models.  
---|---  
The prefix `spring.ai.oci.genai.embedding` is the property prefix that
configures the `EmbeddingModel` implementation for OCI GenAI

Property | Description | Default  
---|---|---  
spring.ai.oci.genai.embedding.enabled (Removed and no longer valid) | Enable OCI GenAI embedding model. | true  
spring.ai.model.embedding | Enable OCI GenAI embedding model. | oci-genai  
spring.ai.oci.genai.embedding.compartment | Model compartment OCID. | -  
spring.ai.oci.genai.embedding.servingMode | The model serving mode to be used. May be `on-demand`, or `dedicated`. | on-demand  
spring.ai.oci.genai.embedding.truncate | How to truncate text if it overruns the embedding context. May be `START`, or `END`. | END  
spring.ai.oci.genai.embedding.model | The model or model endpoint used for embeddings. | -  
|  All properties prefixed with `spring.ai.oci.genai.embedding.options` can be
overridden at runtime by adding a request specific Runtime Options to the
`EmbeddingRequest` call.  
---|---  
##  Runtime Options

The `OCIEmbeddingOptions` provides the configuration information for the
embedding requests. The `OCIEmbeddingOptions` offers a builder to create the
options.

At start time use the `OCIEmbeddingOptions` constructor to set the default
options used for all embedding requests. At run-time you can override the
default options, by passing a `OCIEmbeddingOptions` instance with your to the
`EmbeddingRequest` request.

For example to override the default model name for a specific request:

```

EmbeddingResponse embeddingResponse = embeddingModel.call(

    new EmbeddingRequest(List.of("Hello World", "World is big and salvation is near"),
        OCIEmbeddingOptions.builder()
            .model("my-other-embedding-model")
            .build()
));

Copied!

```

##  Sample Code

This will create a `EmbeddingModel` implementation that you can inject into your
class. Here is an example of a simple `@Controller` class that uses the
`EmbeddingModel` implementation.

```

spring.ai.oci.genai.embedding.model=<your model>

spring.ai.oci.genai.embedding.compartment=<your model compartment>

Copied!

```

```

@RestController

public class EmbeddingController {

    private final EmbeddingModel embeddingModel;

    @Autowired
    public EmbeddingController(EmbeddingModel embeddingModel) {
        this.embeddingModel = embeddingModel;
    }

    @GetMapping("/ai/embedding")
    public Map embed(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
        EmbeddingResponse embeddingResponse = this.embeddingModel.embedForResponse(List.of(message));
        return Map.of("embedding", embeddingResponse);
    }
}

Copied!

```

##  Manual Configuration

If you prefer not to use the Spring Boot auto-configuration, you can manually
configure the `OCIEmbeddingModel` in your application. For this add the `spring-
oci-genai-openai` dependency to your project’s Maven `pom.xml` file:

```

<dependency>

    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-oci-genai-openai</artifactId>
</dependency>

Copied!

```

or to your Gradle `build.gradle` build file.

```

dependencies {

    implementation 'org.springframework.ai:spring-oci-genai-openai'
}

Copied!

```

|  Refer to the Dependency Management section to add the Spring AI BOM to your
build file.  
---|---  
Next, create an `OCIEmbeddingModel` instance and use it to compute the
similarity between two input texts:

```

final String EMBEDDING_MODEL = "cohere.embed-english-light-v2.0";

final String CONFIG_FILE = Paths.get(System.getProperty("user.home"), ".oci",
"config").toString();

final String PROFILE = "DEFAULT";

final String REGION = "us-chicago-1";

final String COMPARTMENT_ID = System.getenv("OCI_COMPARTMENT_ID");

var authProvider = new ConfigFileAuthenticationDetailsProvider(

		this.CONFIG_FILE, this.PROFILE);
var aiClient = GenerativeAiInferenceClient.builder()

    .region(Region.valueOf(this.REGION))
    .build(this.authProvider);
var options = OCIEmbeddingOptions.builder()

    .model(this.EMBEDDING_MODEL)
    .compartment(this.COMPARTMENT_ID)
    .servingMode("on-demand")
    .build();
var embeddingModel = new OCIEmbeddingModel(this.aiClient, this.options);

List<Double> embedding = this.embeddingModel.embed(new Document("How many
provinces are in Canada?"));

Copied!

```

MiniMax Ollama

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

