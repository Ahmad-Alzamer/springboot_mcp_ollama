source URL: https://docs.spring.io/spring-ai/reference/api/imageclient.html 
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

### Image Model API

  * API Overview
  * Image Model
  * ImagePrompt
  * ImageResponse
  * ImageGeneration
  * Available Implementations
  * API Docs
  * Feedback and Contributions

  * Spring AI
  * Reference
  * Models
  * Image Models

# Image Model API

### Image Model API

  * API Overview
  * Image Model
  * ImagePrompt
  * ImageResponse
  * ImageGeneration
  * Available Implementations
  * API Docs
  * Feedback and Contributions

The `Spring Image Model API` is designed to be a simple and portable interface
for interacting with various AI Models specialized in image generation, allowing
developers to switch between different image-related models with minimal code
changes. This design aligns with Spring’s philosophy of modularity and
interchangeability, ensuring developers can quickly adapt their applications to
different AI capabilities related to image processing.

Additionally, with the support of companion classes like `ImagePrompt` for input
encapsulation and `ImageResponse` for output handling, the Image Model API
unifies the communication with AI Models dedicated to image generation. It
manages the complexity of request preparation and response parsing, offering a
direct and simplified API interaction for image-generation functionalities.

The Spring Image Model API is built on top of the Spring AI `Generic Model API`,
providing image-specific abstractions and implementations.

##  API Overview

This section provides a guide to the Spring Image Model API interface and
associated classes.

##  Image Model

Here is the ImageModel interface definition:

```

@FunctionalInterface

public interface ImageModel extends Model<ImagePrompt, ImageResponse> {

	ImageResponse call(ImagePrompt request);

}

Copied!

```

###  ImagePrompt

The ImagePrompt is a `ModelRequest` that encapsulates a list of ImageMessage
objects and optional model request options. The following listing shows a
truncated version of the `ImagePrompt` class, excluding constructors and other
utility methods:

```

public class ImagePrompt implements ModelRequest<List<ImageMessage>> {

    private final List<ImageMessage> messages;

	private ImageOptions imageModelOptions;

    @Override
	public List<ImageMessage> getInstructions() {...}

	@Override
	public ImageOptions getOptions() {...}

    // constructors and utility methods omitted
}

Copied!

```

####  ImageMessage

The `ImageMessage` class encapsulates the text to use and the weight that the
text should have in influencing the generated image. For models that support
weights, they can be positive or negative.

```

public class ImageMessage {

	private String text;

	private Float weight;

    public String getText() {...}

	public Float getWeight() {...}

   // constructors and utility methods omitted

}

Copied!

```

####  ImageOptions

Represents the options that can be passed to the Image generation model. The
`ImageOptions` interface extends the `ModelOptions` interface and is used to
define few portable options that can be passed to the AI model.

The `ImageOptions` interface is defined as follows:

```

public interface ImageOptions extends ModelOptions {

	Integer getN();

	String getModel();

	Integer getWidth();

	Integer getHeight();

	String getResponseFormat(); // openai - url or base64 : stability ai byte[] or base64

}

Copied!

```

Additionally, every model specific ImageModel implementation can have its own
options that can be passed to the AI model. For example, the OpenAI Image
Generation model has its own options like `quality`, `style`, etc.

This is a powerful feature that allows developers to use model specific options
when starting the application and then override them at runtime using the
`ImagePrompt`.

###  ImageResponse

The structure of the `ImageResponse` class is as follows:

```

public class ImageResponse implements ModelResponse<ImageGeneration> {

	private final ImageResponseMetadata imageResponseMetadata;

	private final List<ImageGeneration> imageGenerations;

	@Override
	public ImageGeneration getResult() {
		// get the first result
	}

	@Override
	public List<ImageGeneration> getResults() {...}

	@Override
	public ImageResponseMetadata getMetadata() {...}

    // other methods omitted

}

Copied!

```

The ImageResponse class holds the AI Model’s output, with each `ImageGeneration`
instance containing one of potentially multiple outputs resulting from a single
prompt.

The `ImageResponse` class also carries a `ImageResponseMetadata` object holding
metadata about the AI Model’s response.

###  ImageGeneration

Finally, the ImageGeneration class extends from the `ModelResult` to represent
the output response and related metadata about this result:

```

public class ImageGeneration implements ModelResult<Image> {

	private ImageGenerationMetadata imageGenerationMetadata;

	private Image image;

    @Override
	public Image getOutput() {...}

	@Override
	public ImageGenerationMetadata getMetadata() {...}

    // other methods omitted

}

Copied!

```

##  Available Implementations

`ImageModel` implementations are provided for the following Model providers:

  * OpenAI Image Generation
  * Azure OpenAI Image Generation
  * QianFan Image Generation
  * StabilityAI Image Generation
  * ZhiPuAI Image Generation

##  API Docs

You can find the Javadoc here.

##  Feedback and Contributions

The project’s GitHub discussions is a great place to send feedback.

ZhiPu AI Azure OpenAI

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

