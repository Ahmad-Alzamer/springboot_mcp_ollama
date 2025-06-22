source URL: https://docs.spring.io/spring-ai/reference/getting-started.html 
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

### Getting Started

  * Spring Initializr
  * Artifact Repositories
  * Milestones - Use Maven Central
  * Snapshots - Add Snapshot Repositories
  * Dependency Management
  * Add dependencies for specific components
  * Spring AI samples

  * Spring AI
  * Getting Started

# Getting Started

### Getting Started

  * Spring Initializr
  * Artifact Repositories
  * Milestones - Use Maven Central
  * Snapshots - Add Snapshot Repositories
  * Dependency Management
  * Add dependencies for specific components
  * Spring AI samples

This section offers jumping off points for how to get started using Spring AI.

You should follow the steps in each of the following sections according to your
needs.

|  Spring AI supports Spring Boot 3.4.x. When Spring Boot 3.5.x is released, we
will support that as well.  
---|---  
##  Spring Initializr

Head on over to start.spring.io and select the AI Models and Vector Stores that
you want to use in your new applications.

##  Artifact Repositories

###  Milestones - Use Maven Central

As of 1.0.0-M6, releases are available in Maven Central. No changes to your
build file are required.

###  Snapshots - Add Snapshot Repositories

To use the Snapshot (and pre 1.0.0-M6 milestone) versions, you need to add the
following snapshot repositories in your build file.

Add the following repository definitions to your Maven or Gradle build file:

  * Maven
  * Gradle

```

<repositories>

  <repository>

    <id>spring-snapshots</id>
    <name>Spring Snapshots</name>
    <url>https://repo.spring.io/snapshot</url>
    <releases>
      <enabled>false</enabled>
    </releases>
  </repository>

  <repository>

    <name>Central Portal Snapshots</name>
    <id>central-portal-snapshots</id>
    <url>https://central.sonatype.com/repository/maven-snapshots/</url>
    <releases>
      <enabled>false</enabled>
    </releases>
    <snapshots>
      <enabled>true</enabled>
    </snapshots>
  </repository>

</repositories>

Copied!

```

```

repositories {

  mavenCentral()

  maven { url 'https://repo.spring.io/milestone' }

  maven { url 'https://repo.spring.io/snapshot' }

  maven {

    name = 'Central Portal Snapshots'
    url = 'https://central.sonatype.com/repository/maven-snapshots/'
  }

}

Copied!

```

**NOTE:** When using Maven with Spring AI snapshots, pay attention to your Maven
mirror configuration. If you have configured a mirror in your `settings.xml`
like this:

```

<mirror>

    <id>my-mirror</id>
    <mirrorOf>*</mirrorOf>
    <url>https://my-company-repository.com/maven</url>
</mirror>

Copied!

```

The wildcard `*` will redirect all repository requests to your mirror,
preventing access to Spring snapshot repositories. To fix this, modify the
`mirrorOf` configuration to exclude Spring repositories:

```

<mirror>

    <id>my-mirror</id>
    <mirrorOf>*,!spring-snapshots,!central-portal-snapshots</mirrorOf>
    <url>https://my-company-repository.com/maven</url>
</mirror>

Copied!

```

This configuration allows Maven to access Spring snapshot repositories directly
while still using your mirror for other dependencies.

##  Dependency Management

The Spring AI Bill of Materials (BOM) declares the recommended versions of all
the dependencies used by a given release of Spring AI. This is a BOM-only
version and it just contains dependency management and no plugin declarations or
direct references to Spring or Spring Boot. You can use the Spring Boot parent
POM, or use the BOM from Spring Boot (`spring-boot-dependencies`) to manage
Spring Boot versions.

Add the BOM to your project:

  * Maven
  * Gradle

```

<dependencyManagement>

    <dependencies>
        <dependency>
            <groupId>org.springframework.ai</groupId>
            <artifactId>spring-ai-bom</artifactId>
            <version>1.0.0-SNAPSHOT</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>

Copied!

```

```

dependencies {

  implementation platform("org.springframework.ai:spring-ai-bom:1.0.0-SNAPSHOT")

  // Replace the following with the starter dependencies of specific modules you
wish to use

  implementation 'org.springframework.ai:spring-ai-openai'

}

Copied!

```

Gradle users can also use the Spring AI BOM by leveraging Gradle (5.0+) native
support for declaring dependency constraints using a Maven BOM. This is
implemented by adding a 'platform' dependency handler method to the dependencies
section of your Gradle build script.

##  Add dependencies for specific components

Each of the following sections in the documentation shows which dependencies you
need to add to your project build system.

  * Chat Models
  * Embeddings Models
  * Image Generation Models
  * Transcription Models
  * Text-To-Speech (TTS) Models
  * Vector Databases

##  Spring AI samples

Please refer to this page for more resources and samples related to Spring AI.

AI Concepts Chat Client API

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

