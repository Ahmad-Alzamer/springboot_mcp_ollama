source URL: https://docs.spring.io/spring-ai/reference/api/vectordbs/opensearch.html 
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

### OpenSearch

  * Prerequisites
  * Auto-configuration
  * Configuration Properties
  * Manual Configuration
  * Metadata Filtering
  * Accessing the Native Client

  * Spring AI
  * Reference
  * Vector Databases
  * OpenSearch

# OpenSearch

### OpenSearch

  * Prerequisites
  * Auto-configuration
  * Configuration Properties
  * Manual Configuration
  * Metadata Filtering
  * Accessing the Native Client

This section walks you through setting up `OpenSearchVectorStore` to store
document embeddings and perform similarity searches.

OpenSearch is an open-source search and analytics engine originally forked from
Elasticsearch, distributed under the Apache License 2.0. It enhances AI
application development by simplifying the integration and management of AI-
generated assets. OpenSearch supports vector, lexical, and hybrid search
capabilities, leveraging advanced vector database functionalities to facilitate
low-latency queries and similarity searches as detailed on the vector database
page.

The OpenSearch k-NN functionality allows users to query vector embeddings from
large datasets. An embedding is a numerical representation of a data object,
such as text, image, audio, or document. Embeddings can be stored in the index
and queried using various similarity functions.

##  Prerequisites

  * A running OpenSearch instance. The following options are available:
    * Self-Managed OpenSearch
    * Amazon OpenSearch Service
  * If required, an API key for the EmbeddingModel to generate the embeddings stored by the `OpenSearchVectorStore`.

##  Auto-configuration

|  There has been a significant change in the Spring AI auto-configuration,
starter modules' artifact names. Please refer to the upgrade notes for more
information.  
---|---  
Spring AI provides Spring Boot auto-configuration for the OpenSearch Vector
Store. To enable it, add the following dependency to your project’s Maven
`pom.xml` file:

```

<dependency>

    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-starter-vector-store-opensearch</artifactId>
</dependency>

Copied!

```

or to your Gradle `build.gradle` build file:

```

dependencies {

    implementation 'org.springframework.ai:spring-ai-starter-vector-store-opensearch'
}

Copied!

```

|  Refer to the Dependency Management section to add the Spring AI BOM to your
build file.  
---|---  
For Amazon OpenSearch Service, use these dependencies instead:

```

<dependency>

    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-starter-vector-store-opensearch</artifactId>
</dependency>

Copied!

```

or for Gradle:

```

dependencies {

    implementation 'org.springframework.ai:spring-ai-starter-vector-store-opensearch'
}

Copied!

```

Please have a look at the list of configuration parameters for the vector store
to learn about the default values and configuration options.

Additionally, you will need a configured `EmbeddingModel` bean. Refer to the
EmbeddingModel section for more information.

Now you can auto-wire the `OpenSearchVectorStore` as a vector store in your
application:

```

@Autowired VectorStore vectorStore;

// ...

List<Document> documents = List.of(

    new Document("Spring AI rocks!! Spring AI rocks!! Spring AI rocks!!", Map.of("meta1", "meta1")),
    new Document("The World is Big and Salvation Lurks Around the Corner"),
    new Document("You walk forward facing the past and you turn back toward the future.", Map.of("meta2", "meta2")));

// Add the documents to OpenSearch

vectorStore.add(documents);

// Retrieve documents similar to a query

List<Document> results =
vectorStore.similaritySearch(SearchRequest.builder().query("Spring").topK(5).build());

Copied!

```

###  Configuration Properties

To connect to OpenSearch and use the `OpenSearchVectorStore`, you need to
provide access details for your instance. A simple configuration can be provided
via Spring Boot’s `application.yml`:

```

spring:

  ai:

    vectorstore:
      opensearch:
        uris: <opensearch instance URIs>
        username: <opensearch username>
        password: <opensearch password>
        index-name: spring-ai-document-index
        initialize-schema: true
        similarity-function: cosinesimil
        read-timeout: <time to wait for response>
        connect-timeout: <time to wait until connection established>
        path-prefix: <custom path prefix>
        ssl-bundle: <name of SSL bundle>
        aws:  # Only for Amazon OpenSearch Service
          host: <aws opensearch host>
          service-name: <aws service name>
          access-key: <aws access key>
          secret-key: <aws secret key>
          region: <aws region>
Copied!

```

Properties starting with `spring.ai.vectorstore.opensearch.*` are used to
configure the `OpenSearchVectorStore`:

Property | Description | Default Value  
---|---|---  
`spring.ai.vectorstore.opensearch.uris` | URIs of the OpenSearch cluster endpoints | -  
`spring.ai.vectorstore.opensearch.username` | Username for accessing the OpenSearch cluster | -  
`spring.ai.vectorstore.opensearch.password` | Password for the specified username | -  
`spring.ai.vectorstore.opensearch.index-name` | Name of the index to store vectors | `spring-ai-document-index`  
`spring.ai.vectorstore.opensearch.initialize-schema` | Whether to initialize the required schema | `false`  
`spring.ai.vectorstore.opensearch.similarity-function` | The similarity function to use | `cosinesimil`  
`spring.ai.vectorstore.opensearch.read-timeout` | Time to wait for response from the opposite endpoint. 0 - infinity. | -  
`spring.ai.vectorstore.opensearch.connect-timeout` | Time to wait until connection established. 0 - infinity. | -  
`spring.ai.vectorstore.opensearch.path-prefix` | Path prefix for OpenSearch API endpoints. Useful when OpenSearch is behind a reverse proxy with a non-root path. | -  
`spring.ai.vectorstore.opensearch.ssl-bundle` | Name of the SSL Bundle to use in case of SSL connection | -  
`spring.ai.vectorstore.opensearch.aws.host` | Hostname of the OpenSearch instance | -  
`spring.ai.vectorstore.opensearch.aws.service-name` | AWS service name | -  
`spring.ai.vectorstore.opensearch.aws.access-key` | AWS access key | -  
`spring.ai.vectorstore.opensearch.aws.secret-key` | AWS secret key | -  
`spring.ai.vectorstore.opensearch.aws.region` | AWS region | -  
|  You can control whether the AWS-specific OpenSearch auto-configuration is
enabled using the `spring.ai.vectorstore.opensearch.aws.enabled` property.

  * If this property is set to `false`, the non-AWS OpenSearch configuration is activated, even if AWS SDK classes are present on the classpath. This allows you to use self-managed or third-party OpenSearch clusters in environments where AWS SDKs are present for other services.
  * If AWS SDK classes are not present, the non-AWS configuration is always used.
  * If AWS SDK classes are present and the property is not set or set to `true`, the AWS-specific configuration is used by default.

This fallback logic ensures that users have explicit control over the type of
OpenSearch integration, preventing accidental activation of AWS-specific logic
when not desired.  
---|---  
|  The `path-prefix` property allows you to specify a custom path prefix when
OpenSearch is running behind a reverse proxy that uses a non-root path. For
example, if your OpenSearch instance is accessible at `example.com/opensearch/`
instead of `example.com/`, you would set `path-prefix: /opensearch`.  
---|---  
The following similarity functions are available:

  * `cosinesimil` - Default, suitable for most use cases. Measures cosine similarity between vectors.
  * `l1` - Manhattan distance between vectors.
  * `l2` - Euclidean distance between vectors.
  * `linf` - Chebyshev distance between vectors.

##  Manual Configuration

Instead of using the Spring Boot auto-configuration, you can manually configure
the OpenSearch vector store. For this you need to add the `spring-ai-opensearch-
store` to your project:

```

<dependency>

    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-opensearch-store</artifactId>
</dependency>

Copied!

```

or to your Gradle `build.gradle` build file:

```

dependencies {

    implementation 'org.springframework.ai:spring-ai-opensearch-store'
}

Copied!

```

|  Refer to the Dependency Management section to add the Spring AI BOM to your
build file.  
---|---  
Create an OpenSearch client bean:

```

@Bean

public OpenSearchClient openSearchClient() {

    RestClient restClient = RestClient.builder(
        HttpHost.create("http://localhost:9200"))
        .build();

    return new OpenSearchClient(new RestClientTransport(
        restClient, new JacksonJsonpMapper()));
}

Copied!

```

Then create the `OpenSearchVectorStore` bean using the builder pattern:

```

@Bean

public VectorStore vectorStore(OpenSearchClient openSearchClient, EmbeddingModel
embeddingModel) {

    return OpenSearchVectorStore.builder(openSearchClient, embeddingModel)
        .index("custom-index")                // Optional: defaults to "spring-ai-document-index"
        .similarityFunction("l2")             // Optional: defaults to "cosinesimil"
        .initializeSchema(true)               // Optional: defaults to false
        .batchingStrategy(new TokenCountBatchingStrategy()) // Optional: defaults to TokenCountBatchingStrategy
        .build();
}

// This can be any EmbeddingModel implementation

@Bean

public EmbeddingModel embeddingModel() {

    return new OpenAiEmbeddingModel(new OpenAiApi(System.getenv("OPENAI_API_KEY")));
}

Copied!

```

##  Metadata Filtering

You can leverage the generic, portable metadata filters with OpenSearch as well.

For example, you can use either the text expression language:

```

vectorStore.similaritySearch(

    SearchRequest.builder()
        .query("The World")
        .topK(TOP_K)
        .similarityThreshold(SIMILARITY_THRESHOLD)
        .filterExpression("author in ['john', 'jill'] && 'article_type' == 'blog'").build());
Copied!

```

or programmatically using the `Filter.Expression` DSL:

```

FilterExpressionBuilder b = new FilterExpressionBuilder();

vectorStore.similaritySearch(SearchRequest.builder()

    .query("The World")
    .topK(TOP_K)
    .similarityThreshold(SIMILARITY_THRESHOLD)
    .filterExpression(b.and(
        b.in("author", "john", "jill"),
        b.eq("article_type", "blog")).build()).build());
Copied!

```

|  Those (portable) filter expressions get automatically converted into the
proprietary OpenSearch Query string query.  
---|---  
For example, this portable filter expression:

```

author in ['john', 'jill'] && 'article_type' == 'blog'

Copied!

```

is converted into the proprietary OpenSearch filter format:

```

(metadata.author:john OR jill) AND metadata.article_type:blog

Copied!

```

##  Accessing the Native Client

The OpenSearch Vector Store implementation provides access to the underlying
native OpenSearch client (`OpenSearchClient`) through the `getNativeClient()`
method:

```

OpenSearchVectorStore vectorStore =
context.getBean(OpenSearchVectorStore.class);

Optional<OpenSearchClient> nativeClient = vectorStore.getNativeClient();

if (nativeClient.isPresent()) {

    OpenSearchClient client = nativeClient.get();
    // Use the native client for OpenSearch-specific operations
}

Copied!

```

The native client gives you access to OpenSearch-specific features and
operations that might not be exposed through the `VectorStore` interface.

Neo4j Oracle

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

