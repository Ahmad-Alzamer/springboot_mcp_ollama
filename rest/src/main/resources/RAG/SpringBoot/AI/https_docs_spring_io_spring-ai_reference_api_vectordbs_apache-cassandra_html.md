source URL: https://docs.spring.io/spring-ai/reference/api/vectordbs/apache-cassandra.html 
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

### Apache Cassandra Vector Store

  * What is Apache Cassandra?
  * What is JVector?
  * Prerequisites
  * Dependencies
  * Configuration Properties
  * Usage
  * Basic Usage
  * Advanced Configuration
  * Connection Configuration
  * Metadata Filtering
  * Advanced Example: Vector Store on top of Wikipedia Dataset
  * Loading the Complete Wikipedia Dataset
  * Accessing the Native Client

  * Spring AI
  * Reference
  * Vector Databases
  * Apache Cassandra Vector Store

# Apache Cassandra Vector Store

### Apache Cassandra Vector Store

  * What is Apache Cassandra?
  * What is JVector?
  * Prerequisites
  * Dependencies
  * Configuration Properties
  * Usage
  * Basic Usage
  * Advanced Configuration
  * Connection Configuration
  * Metadata Filtering
  * Advanced Example: Vector Store on top of Wikipedia Dataset
  * Loading the Complete Wikipedia Dataset
  * Accessing the Native Client

This section walks you through setting up `CassandraVectorStore` to store
document embeddings and perform similarity searches.

##  What is Apache Cassandra?

Apache Cassandra® is a true open source distributed database renowned for linear
scalability, proven fault-tolerance and low latency, making it the perfect
platform for mission-critical transactional data.

Its Vector Similarity Search (VSS) is based on the JVector library that ensures
best-in-class performance and relevancy.

A vector search in Apache Cassandra is done as simply as:

```

SELECT content FROM table ORDER BY content_vector ANN OF query_embedding;

Copied!

```

More docs on this can be read here.

This Spring AI Vector Store is designed to work for both brand-new RAG
applications and be able to be retrofitted on top of existing data and tables.

The store can also be used for non-RAG use-cases in an existing database, e.g.
semantic searches, geo-proximity searches, etc.

The store will automatically create, or enhance, the schema as needed according
to its configuration. If you don’t want the schema modifications, configure the
store with `initializeSchema`.

When using spring-boot-autoconfigure `initializeSchema` defaults to `false`, per
Spring Boot standards, and you must opt-in to schema creation/modifications by
setting `…​initialize-schema=true` in the `application.properties` file.

##  What is JVector?

JVector is a pure Java embedded vector search engine.

It stands out from other HNSW Vector Similarity Search implementations by being:

  * Algorithmic-fast. JVector uses state of the art graph algorithms inspired by DiskANN and related research that offer high recall and low latency.
  * Implementation-fast. JVector uses the Panama SIMD API to accelerate index build and queries.
  * Memory efficient. JVector compresses vectors using product quantization so they can stay in memory during searches.
  * Disk-aware. JVector’s disk layout is designed to do the minimum necessary iops at query time.
  * Concurrent. Index builds scale linearly to at least 32 threads. Double the threads, half the build time.
  * Incremental. Query your index as you build it. No delay between adding a vector and being able to find it in search results.
  * Easy to embed. API designed for easy embedding, by people using it in production.

##  Prerequisites

  1. A `EmbeddingModel` instance to compute the document embeddings. This is usually configured as a Spring Bean. Several options are available:
     * `Transformers Embedding` - computes the embedding in your local environment. The default is via ONNX and the all-MiniLM-L6-v2 Sentence Transformers. This just works.
     * If you want to use OpenAI’s Embeddings - uses the OpenAI embedding endpoint. You need to create an account at OpenAI Signup and generate the api-key token at API Keys.
     * There are many more choices, see `Embeddings API` docs.
  2. An Apache Cassandra instance, from version 5.0-beta1
    1. DIY Quick Start
    2. For a managed offering Astra DB offers a healthy free tier offering.

##  Dependencies

|  There has been a significant change in the Spring AI auto-configuration,
starter modules' artifact names. Please refer to the upgrade notes for more
information.  
---|---  
|  For dependency management, we recommend using the Spring AI BOM as explained
in the Dependency Management section.  
---|---  
Add these dependencies to your project:

  * For just the Cassandra Vector Store:

```

<dependency>

    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-cassandra-store</artifactId>
</dependency>

Copied!

```

  * Or, for everything you need in a RAG application (using the default ONNX Embedding Model):

```

<dependency>

    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-starter-vector-store-cassandra</artifactId>
</dependency>

Copied!

```

##  Configuration Properties

You can use the following properties in your Spring Boot configuration to
customize the Apache Cassandra vector store.

Property | Default Value  
---|---  
`spring.ai.vectorstore.cassandra.keyspace` | springframework  
`spring.ai.vectorstore.cassandra.table` | ai_vector_store  
`spring.ai.vectorstore.cassandra.initialize-schema` | false  
`spring.ai.vectorstore.cassandra.index-name` |   
`spring.ai.vectorstore.cassandra.content-column-name` | content  
`spring.ai.vectorstore.cassandra.embedding-column-name` | embedding  
`spring.ai.vectorstore.cassandra.fixed-thread-pool-executor-size` | 16  
##  Usage

###  Basic Usage

Create a CassandraVectorStore instance as a Spring Bean:

```

@Bean

public VectorStore vectorStore(CqlSession session, EmbeddingModel
embeddingModel) {

    return CassandraVectorStore.builder(embeddingModel)
        .session(session)
        .keyspace("my_keyspace")
        .table("my_vectors")
        .build();
}

Copied!

```

Once you have the vector store instance, you can add documents and perform
searches:

```

// Add documents

vectorStore.add(List.of(

    new Document("1", "content1", Map.of("key1", "value1")),
    new Document("2", "content2", Map.of("key2", "value2"))
));

// Search with filters

List<Document> results = vectorStore.similaritySearch(

    SearchRequest.query("search text")
        .withTopK(5)
        .withSimilarityThreshold(0.7f)
        .withFilterExpression("metadata.key1 == 'value1'")
);

Copied!

```

###  Advanced Configuration

For more complex use cases, you can configure additional settings in your Spring
Bean:

```

@Bean

public VectorStore vectorStore(CqlSession session, EmbeddingModel
embeddingModel) {

    return CassandraVectorStore.builder(embeddingModel)
        .session(session)
        .keyspace("my_keyspace")
        .table("my_vectors")
        // Configure primary keys
        .partitionKeys(List.of(
            new SchemaColumn("id", DataTypes.TEXT),
            new SchemaColumn("category", DataTypes.TEXT)
        ))
        .clusteringKeys(List.of(
            new SchemaColumn("timestamp", DataTypes.TIMESTAMP)
        ))
        // Add metadata columns with optional indexing
        .addMetadataColumns(
            new SchemaColumn("category", DataTypes.TEXT, SchemaColumnTags.INDEXED),
            new SchemaColumn("score", DataTypes.DOUBLE)
        )
        // Customize column names
        .contentColumnName("text")
        .embeddingColumnName("vector")
        // Performance tuning
        .fixedThreadPoolExecutorSize(32)
        // Schema management
        .initializeSchema(true)
        // Custom batching strategy
        .batchingStrategy(new TokenCountBatchingStrategy())
        .build();
}

Copied!

```

###  Connection Configuration

There are two ways to configure the connection to Cassandra:

  * Using an injected CqlSession (recommended):

```

@Bean

public VectorStore vectorStore(CqlSession session, EmbeddingModel
embeddingModel) {

    return CassandraVectorStore.builder(embeddingModel)
        .session(session)
        .keyspace("my_keyspace")
        .table("my_vectors")
        .build();
}

Copied!

```

  * Using connection details directly in the builder:

```

@Bean

public VectorStore vectorStore(EmbeddingModel embeddingModel) {

    return CassandraVectorStore.builder(embeddingModel)
        .contactPoint(new InetSocketAddress("localhost", 9042))
        .localDatacenter("datacenter1")
        .keyspace("my_keyspace")
        .build();
}

Copied!

```

###  Metadata Filtering

You can leverage the generic, portable metadata filters with the
CassandraVectorStore. For metadata columns to be searchable they must be either
primary keys or SAI indexed. To make non-primary-key columns indexed, configure
the metadata column with the `SchemaColumnTags.INDEXED`.

For example, you can use either the text expression language:

```

vectorStore.similaritySearch(

    SearchRequest.builder().query("The World")
        .topK(5)
        .filterExpression("country in ['UK', 'NL'] && year >= 2020").build());
Copied!

```

or programmatically using the expression DSL:

```

Filter.Expression f = new FilterExpressionBuilder()

    .and(
        f.in("country", "UK", "NL"),
        f.gte("year", 2020)
    ).build();

vectorStore.similaritySearch(

    SearchRequest.builder().query("The World")
        .topK(5)
        .filterExpression(f).build());
Copied!

```

The portable filter expressions get automatically converted into CQL queries.

##  Advanced Example: Vector Store on top of Wikipedia Dataset

The following example demonstrates how to use the store on an existing schema.
Here we use the schema from the github.com/datastax-labs/colbert-wikipedia-data
project which comes with the full wikipedia dataset ready vectorized for you.

First, create the schema in the Cassandra database:

```

wget https://s.apache.org/colbert-wikipedia-schema-cql -O colbert-wikipedia-
schema.cql

cqlsh -f colbert-wikipedia-schema.cql

Copied!

```

Then configure the store using the builder pattern:

```

@Bean

public VectorStore vectorStore(CqlSession session, EmbeddingModel
embeddingModel) {

    List<SchemaColumn> partitionColumns = List.of(
        new SchemaColumn("wiki", DataTypes.TEXT),
        new SchemaColumn("language", DataTypes.TEXT),
        new SchemaColumn("title", DataTypes.TEXT)
    );

    List<SchemaColumn> clusteringColumns = List.of(
        new SchemaColumn("chunk_no", DataTypes.INT),
        new SchemaColumn("bert_embedding_no", DataTypes.INT)
    );

    List<SchemaColumn> extraColumns = List.of(
        new SchemaColumn("revision", DataTypes.INT),
        new SchemaColumn("id", DataTypes.INT)
    );

    return CassandraVectorStore.builder()
        .session(session)
        .embeddingModel(embeddingModel)
        .keyspace("wikidata")
        .table("articles")
        .partitionKeys(partitionColumns)
        .clusteringKeys(clusteringColumns)
        .contentColumnName("body")
        .embeddingColumnName("all_minilm_l6_v2_embedding")
        .indexName("all_minilm_l6_v2_ann")
        .initializeSchema(false)
        .addMetadataColumns(extraColumns)
        .primaryKeyTranslator((List<Object> primaryKeys) -> {
            if (primaryKeys.isEmpty()) {
                return "test§¶0";
            }
            return String.format("%s§¶%s", primaryKeys.get(2), primaryKeys.get(3));
        })
        .documentIdTranslator((id) -> {
            String[] parts = id.split("§¶");
            String title = parts[0];
            int chunk_no = parts.length > 1 ? Integer.parseInt(parts[1]) : 0;
            return List.of("simplewiki", "en", title, chunk_no, 0);
        })
        .build();
}

@Bean

public EmbeddingModel embeddingModel() {

    // default is ONNX all-MiniLM-L6-v2 which is what we want
    return new TransformersEmbeddingModel();
}

Copied!

```

###  Loading the Complete Wikipedia Dataset

To load the full wikipedia dataset:

  1. Download `simplewiki-sstable.tar` from s.apache.org/simplewiki-sstable-tar (this will take a while, the file is tens of GBs)
  2. Load the data:

```

tar -xf simplewiki-sstable.tar -C ${CASSANDRA_DATA}/data/wikidata/articles-*/

nodetool import wikidata articles ${CASSANDRA_DATA}/data/wikidata/articles-*/

```

|

  * If you have existing data in this table, check the tarball’s files don’t clobber existing sstables when doing the `tar`.
  * An alternative to `nodetool import` is to just restart Cassandra.
  * If there are any failures in the indexes they will be rebuilt automatically.

  
---|---  
##  Accessing the Native Client

The Cassandra Vector Store implementation provides access to the underlying
native Cassandra client (`CqlSession`) through the `getNativeClient()` method:

```

CassandraVectorStore vectorStore = context.getBean(CassandraVectorStore.class);

Optional<CqlSession> nativeClient = vectorStore.getNativeClient();

if (nativeClient.isPresent()) {

    CqlSession session = nativeClient.get();
    // Use the native client for Cassandra-specific operations
}

Copied!

```

The native client gives you access to Cassandra-specific features and operations
that might not be exposed through the `VectorStore` interface.

Azure Cosmos DB Chroma

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

