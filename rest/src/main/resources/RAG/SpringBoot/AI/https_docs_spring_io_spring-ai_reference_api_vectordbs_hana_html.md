source URL: https://docs.spring.io/spring-ai/reference/api/vectordbs/hana.html 
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

### SAP HANA Cloud

  * Prerequisites
  * Auto-configuration
  * HanaCloudVectorStore Properties
  * Build a Sample RAG application
  * Create an Entity class named CricketWorldCup that extends from HanaVectorEntity:

  * Spring AI
  * Reference
  * Vector Databases
  * SAP Hana

# SAP HANA Cloud

### SAP HANA Cloud

  * Prerequisites
  * Auto-configuration
  * HanaCloudVectorStore Properties
  * Build a Sample RAG application
  * Create an Entity class named CricketWorldCup that extends from HanaVectorEntity:

##  Prerequisites

  * You need a SAP HANA Cloud vector engine account - Refer SAP HANA Cloud vector engine - provision a trial account guide to create a trial account.
  * If required, an API key for the EmbeddingModel to generate the embeddings stored by the vector store.

##  Auto-configuration

Spring AI does not provide a dedicated module for SAP Hana vector store. Users
are expected to provide their own configuration in the applications using the
standard vector store module for SAP Hana vector store in Spring AI - `spring-
ai-hanadb-store`.

|  Refer to the Dependency Management section to add the Spring AI BOM to your
build file.  
---|---  
Please have a look at the list of HanaCloudVectorStore Properties for the vector
store to learn about the default values and configuration options.

|  Refer to the Artifact Repositories section to add Maven Central and/or
Snapshot Repositories to your build file.  
---|---  
Additionally, you will need a configured `EmbeddingModel` bean. Refer to the
EmbeddingModel section for more information.

##  HanaCloudVectorStore Properties

You can use the following properties in your Spring Boot configuration to
customize the SAP Hana vector store. It uses `spring.datasource.`**properties to
configure the Hana datasource and the`spring.ai.vectorstore.hanadb.`**
properties to configure the Hana vector store.

Property | Description | Default value  
---|---|---  
`spring.datasource.driver-class-name` | Driver class name | com.sap.db.jdbc.Driver  
`spring.datasource.url` | Hana Datasource URL | -  
`spring.datasource.username` | Hana datasource username | -  
`spring.datasource.password` | Hana datasource password | -  
`spring.ai.vectorstore.hanadb.top-k` | TODO | -  
`spring.ai.vectorstore.hanadb.table-name` | TODO | -  
`spring.ai.vectorstore.hanadb.initialize-schema` | whether to initialize the required schema | `false`  
##  Build a Sample RAG application

Shows how to setup a project that uses SAP Hana Cloud as the vector DB and
leverage OpenAI to implement RAG pattern

  * Create a table `CRICKET_WORLD_CUP` in SAP Hana DB:

```

CREATE TABLE CRICKET_WORLD_CUP (

    _ID VARCHAR2(255) PRIMARY KEY,
    CONTENT CLOB,
    EMBEDDING REAL_VECTOR(1536)
)

```

  * Add the following dependencies in your `pom.xml`

You may set the property `spring-ai-version` as `<spring-ai-
version>1.0.0-SNAPSHOT</spring-ai-version>`:

```

<dependencyManagement>

    <dependencies>
        <dependency>
            <groupId>org.springframework.ai</groupId>
            <artifactId>spring-ai-bom</artifactId>
            <version>${spring-ai-version}</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>

<dependency>

    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>

<dependency>

    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-pdf-document-reader</artifactId>
</dependency>

<dependency>

    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-starter-model-openai</artifactId>
</dependency>

<dependency>

    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-starter-vector-store-hana</artifactId>
</dependency>

<dependency>

    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <version>1.18.30</version>
    <scope>provided</scope>
</dependency>

Copied!

```

  * Add the following properties in `application.properties` file:

```

spring.ai.openai.api-key=${OPENAI_API_KEY}

spring.ai.openai.embedding.options.model=text-embedding-ada-002

spring.datasource.driver-class-name=com.sap.db.jdbc.Driver

spring.datasource.url=${HANA_DATASOURCE_URL}

spring.datasource.username=${HANA_DATASOURCE_USERNAME}

spring.datasource.password=${HANA_DATASOURCE_PASSWORD}

spring.ai.vectorstore.hanadb.tableName=CRICKET_WORLD_CUP

spring.ai.vectorstore.hanadb.topK=3

```

###  Create an `Entity` class named `CricketWorldCup` that extends from
`HanaVectorEntity`:

```

package com.interviewpedia.spring.ai.hana;

import jakarta.persistence.Column;

import jakarta.persistence.Entity;

import jakarta.persistence.Table;

import lombok.Data;

import lombok.NoArgsConstructor;

import lombok.extern.jackson.Jacksonized;

import org.springframework.ai.vectorstore.hanadb.HanaVectorEntity;

@Entity

@Table(name = "CRICKET_WORLD_CUP")

@Data

@Jacksonized

@NoArgsConstructor

public class CricketWorldCup extends HanaVectorEntity {

    @Column(name = "content")
    private String content;
}

Copied!

```

  * Create a `Repository` named `CricketWorldCupRepository` that implements `HanaVectorRepository` interface:

```

package com.interviewpedia.spring.ai.hana;

import jakarta.persistence.EntityManager;

import jakarta.persistence.PersistenceContext;

import jakarta.transaction.Transactional;

import org.springframework.ai.vectorstore.hanadb.HanaVectorRepository;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public class CricketWorldCupRepository implements
HanaVectorRepository<CricketWorldCup> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void save(String tableName, String id, String embedding, String content) {
        String sql = String.format("""
                INSERT INTO %s (_ID, EMBEDDING, CONTENT)
                VALUES(:_id, TO_REAL_VECTOR(:embedding), :content)
                """, tableName);

		this.entityManager.createNativeQuery(sql)
                .setParameter("_id", id)
                .setParameter("embedding", embedding)
                .setParameter("content", content)
                .executeUpdate();
    }

    @Override
    @Transactional
    public int deleteEmbeddingsById(String tableName, List<String> idList) {
        String sql = String.format("""
                DELETE FROM %s WHERE _ID IN (:ids)
                """, tableName);

        return this.entityManager.createNativeQuery(sql)
                .setParameter("ids", idList)
                .executeUpdate();
    }

    @Override
    @Transactional
    public int deleteAllEmbeddings(String tableName) {
        String sql = String.format("""
                DELETE FROM %s
                """, tableName);

        return this.entityManager.createNativeQuery(sql).executeUpdate();
    }

    @Override
    public List<CricketWorldCup> cosineSimilaritySearch(String tableName, int topK, String queryEmbedding) {
        String sql = String.format("""
                SELECT TOP :topK * FROM %s
                ORDER BY COSINE_SIMILARITY(EMBEDDING, TO_REAL_VECTOR(:queryEmbedding)) DESC
                """, tableName);

        return this.entityManager.createNativeQuery(sql, CricketWorldCup.class)
                .setParameter("topK", topK)
                .setParameter("queryEmbedding", queryEmbedding)
                .getResultList();
    }
}

Copied!

```

  * Now, create a REST Controller class `CricketWorldCupHanaController`, and autowire `ChatModel` and `VectorStore` as dependencies In this controller class, create the following REST endpoints:
    * `/ai/hana-vector-store/cricket-world-cup/purge-embeddings` - to purge all the embeddings from the Vector Store
    * `/ai/hana-vector-store/cricket-world-cup/upload` - to upload the Cricket_World_Cup.pdf so that its data gets stored in SAP Hana Cloud Vector DB as embeddings
    * `/ai/hana-vector-store/cricket-world-cup` - to implement `RAG` using Cosine_Similarity in SAP Hana DB

```

package com.interviewpedia.spring.ai.hana;

import lombok.extern.slf4j.Slf4j;

import org.springframework.ai.chat.model.ChatModel;

import org.springframework.ai.chat.messages.UserMessage;

import org.springframework.ai.chat.prompt.Prompt;

import org.springframework.ai.chat.prompt.SystemPromptTemplate;

import org.springframework.ai.document.Document;

import org.springframework.ai.reader.pdf.PagePdfDocumentReader;

import org.springframework.ai.transformer.splitter.TokenTextSplitter;

import org.springframework.ai.vectorstore.hanadb.HanaCloudVectorStore;

import org.springframework.ai.vectorstore.VectorStore;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.core.io.Resource;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import java.util.List;

import java.util.Map;

import java.util.function.Function;

import java.util.function.Supplier;

import java.util.stream.Collectors;

@RestController

@Slf4j

public class CricketWorldCupHanaController {

    private final VectorStore hanaCloudVectorStore;
    private final ChatModel chatModel;

    @Autowired
    public CricketWorldCupHanaController(ChatModel chatModel, VectorStore hanaCloudVectorStore) {
        this.chatModel = chatModel;
        this.hanaCloudVectorStore = hanaCloudVectorStore;
    }

    @PostMapping("/ai/hana-vector-store/cricket-world-cup/purge-embeddings")
    public ResponseEntity<String> purgeEmbeddings() {
        int deleteCount = ((HanaCloudVectorStore) this.hanaCloudVectorStore).purgeEmbeddings();
        log.info("{} embeddings purged from CRICKET_WORLD_CUP table in Hana DB", deleteCount);
        return ResponseEntity.ok().body(String.format("%d embeddings purged from CRICKET_WORLD_CUP table in Hana DB", deleteCount));
    }

    @PostMapping("/ai/hana-vector-store/cricket-world-cup/upload")
    public ResponseEntity<String> handleFileUpload(@RequestParam("pdf") MultipartFile file) throws IOException {
        Resource pdf = file.getResource();
        Supplier<List<Document>> reader = new PagePdfDocumentReader(pdf);
        Function<List<Document>, List<Document>> splitter = new TokenTextSplitter();
        List<Document> documents = splitter.apply(reader.get());
        log.info("{} documents created from pdf file: {}", documents.size(), pdf.getFilename());
		this.hanaCloudVectorStore.accept(documents);
        return ResponseEntity.ok().body(String.format("%d documents created from pdf file: %s",
                documents.size(), pdf.getFilename()));
    }

    @GetMapping("/ai/hana-vector-store/cricket-world-cup")
    public Map<String, String> hanaVectorStoreSearch(@RequestParam(value = "message") String message) {
        var documents = this.hanaCloudVectorStore.similaritySearch(message);
        var inlined = documents.stream().map(Document::getText).collect(Collectors.joining(System.lineSeparator()));
        var similarDocsMessage = new SystemPromptTemplate("Based on the following: {documents}")
                .createMessage(Map.of("documents", inlined));

        var userMessage = new UserMessage(message);
        Prompt prompt = new Prompt(List.of(similarDocsMessage, userMessage));
        String generation = this.chatModel.call(prompt).getResult().getOutput().getContent();
        log.info("Generation: {}", generation);
        return Map.of("generation", generation);
    }
}

Copied!

```

Since HanaDB vector store support does not provide the autoconfiguration module,
you also need to provide the vector store bean in your application, as shown
below, as an example.

```

@Bean

public VectorStore hanaCloudVectorStore(CricketWorldCupRepository
cricketWorldCupRepository,

        EmbeddingModel embeddingModel) {

    return HanaCloudVectorStore.builder(cricketWorldCupRepository, embeddingModel)
        .tableName("CRICKET_WORLD_CUP")
        .topK(1)
        .build();
}

Copied!

```

  * Use a `contextual` pdf file from wikipedia

Go to wikipedia and download `Cricket World Cup` page as a PDF file.

Upload this PDF file using the file-upload REST endpoint that we created in the
previous step.

Redis Typesense

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

