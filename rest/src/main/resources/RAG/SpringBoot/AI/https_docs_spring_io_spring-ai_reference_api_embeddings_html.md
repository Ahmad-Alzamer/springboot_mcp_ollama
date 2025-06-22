source URL: https://docs.spring.io/spring-ai/reference/api/embeddings.html 
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

### Embeddings Model API

  * API Overview
  * EmbeddingModel
  * Available Implementations

  * Spring AI
  * Reference
  * Models
  * Embedding Models

# Embeddings Model API

### Embeddings Model API

  * API Overview
  * EmbeddingModel
  * Available Implementations

Embeddings are numerical representations of text, images, or videos that capture
relationships between inputs.

Embeddings work by converting text, image, and video into arrays of floating
point numbers, called vectors. These vectors are designed to capture the meaning
of the text, images, and videos. The length of the embedding array is called the
vector’s dimensionality.

By calculating the numerical distance between the vector representations of two
pieces of text, an application can determine the similarity between the objects
used to generate the embedding vectors.

The `EmbeddingModel` interface is designed for straightforward integration with
embedding models in AI and machine learning. Its primary function is to convert
text into numerical vectors, commonly referred to as embeddings. These
embeddings are crucial for various tasks such as semantic analysis and text
classification.

The design of the EmbeddingModel interface centers around two primary goals:

  * **Portability** : This interface ensures easy adaptability across various embedding models. It allows developers to switch between different embedding techniques or models with minimal code changes. This design aligns with Spring’s philosophy of modularity and interchangeability.
  * **Simplicity** : EmbeddingModel simplifies the process of converting text to embeddings. By providing straightforward methods like `embed(String text)` and `embed(Document document)`, it takes the complexity out of dealing with raw text data and embedding algorithms. This design choice makes it easier for developers, especially those new to AI, to utilize embeddings in their applications without delving deep into the underlying mechanics.

##  API Overview

The Embedding Model API is built on top of the generic Spring AI Model API,
which is a part of the Spring AI library. As such, the EmbeddingModel interface
extends the `Model` interface, which provides a standard set of methods for
interacting with AI models. The `EmbeddingRequest` and `EmbeddingResponse`
classes extend from the `ModelRequest` and `ModelResponse` are used to
encapsulate the input and output of the embedding models, respectively.

The Embedding API in turn is used by higher-level components to implement
Embedding Models for specific embedding models, such as OpenAI, Titan, Azure
OpenAI, Ollie, and others.

Following diagram illustrates the Embedding API and its relationship with the
Spring AI Model API and the Embedding Models:

###  EmbeddingModel

This section provides a guide to the `EmbeddingModel` interface and associated
classes.

```

public interface EmbeddingModel extends Model<EmbeddingRequest,
EmbeddingResponse> {

	@Override
	EmbeddingResponse call(EmbeddingRequest request);

	/**
	 * Embeds the given document's content into a vector.
	 * @param document the document to embed.
	 * @return the embedded vector.
	 */
	float[] embed(Document document);

	/**
	 * Embeds the given text into a vector.
	 * @param text the text to embed.
	 * @return the embedded vector.
	 */
	default float[] embed(String text) {
		Assert.notNull(text, "Text must not be null");
		return this.embed(List.of(text)).iterator().next();
	}

	/**
	 * Embeds a batch of texts into vectors.
	 * @param texts list of texts to embed.
	 * @return list of list of embedded vectors.
	 */
	default List<float[]> embed(List<String> texts) {
		Assert.notNull(texts, "Texts must not be null");
		return this.call(new EmbeddingRequest(texts, EmbeddingOptions.EMPTY))
			.getResults()
			.stream()
			.map(Embedding::getOutput)
			.toList();
	}

	/**
	 * Embeds a batch of texts into vectors and returns the {@link EmbeddingResponse}.
	 * @param texts list of texts to embed.
	 * @return the embedding response.
	 */
	default EmbeddingResponse embedForResponse(List<String> texts) {
		Assert.notNull(texts, "Texts must not be null");
		return this.call(new EmbeddingRequest(texts, EmbeddingOptions.EMPTY));
	}

	/**
	 * @return the number of dimensions of the embedded vectors. It is generative
	 * specific.
	 */
	default int dimensions() {
		return embed("Test String").size();
	}

}

Copied!

```

The embed methods offer various options for converting text into embeddings,
accommodating single strings, structured `Document` objects, or batches of text.

Multiple shortcut methods are provided for embedding text, including the
`embed(String text)` method, which takes a single string and returns the
corresponding embedding vector. All shortcuts are implemented around the `call`
method, which is the primary method for invoking the embedding model.

Typically the embedding returns a lists of floats, representing the embeddings
in a numerical vector format.

The `embedForResponse` method provides a more comprehensive output, potentially
including additional information about the embeddings.

The dimensions method is a handy tool for developers to quickly ascertain the
size of the embedding vectors, which is important for understanding the
embedding space and for subsequent processing steps.

####  EmbeddingRequest

The `EmbeddingRequest` is a `ModelRequest` that takes a list of text objects and
optional embedding request options. The following listing shows a truncated
version of the EmbeddingRequest class, excluding constructors and other utility
methods:

```

public class EmbeddingRequest implements ModelRequest<List<String>> {

	private final List<String> inputs;
	private final EmbeddingOptions options;
	// other methods omitted
}

Copied!

```

####  EmbeddingResponse

The structure of the `EmbeddingResponse` class is as follows:

```

public class EmbeddingResponse implements ModelResponse<Embedding> {

	private List<Embedding> embeddings;
	private EmbeddingResponseMetadata metadata = new EmbeddingResponseMetadata();
	// other methods omitted
}

Copied!

```

The `EmbeddingResponse` class holds the AI Model’s output, with each `Embedding`
instance containing the result vector data from a single text input.

The `EmbeddingResponse` class also carries a `EmbeddingResponseMetadata`
metadata about the AI Model’s response.

####  Embedding

The `Embedding` represents a single embedding vector.

```

public class Embedding implements ModelResult<float[]> {

	private float[] embedding;
	private Integer index;
	private EmbeddingResultMetadata metadata;
	// other methods omitted
}

Copied!

```

##  Available Implementations

Internally the various `EmbeddingModel` implementations use different low-level
libraries and APIs to perform the embedding tasks. The following are some of the
available implementations of the `EmbeddingModel` implementations:

  * Spring AI OpenAI Embeddings
  * Spring AI Azure OpenAI Embeddings
  * Spring AI Ollama Embeddings
  * Spring AI Transformers (ONNX) Embeddings
  * Spring AI PostgresML Embeddings
  * Spring AI Bedrock Cohere Embeddings
  * Spring AI Bedrock Titan Embeddings
  * Spring AI VertexAI Embeddings
  * Spring AI Mistral AI Embeddings
  * Spring AI Oracle Cloud Infrastructure GenAI Embeddings

ZhiPu AI Amazon Bedrock

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

