source URL: https://docs.spring.io/spring-ai/reference/api/embeddings/onnx.html 
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

### Transformers (ONNX) Embeddings

  * Prerequisites
  * Auto-configuration
  * Embedding Properties
  * Errors and special cases
  * Manual Configuration

  * Spring AI
  * Reference
  * Models
  * Embedding Models
  * (ONNX) Transformers

# Transformers (ONNX) Embeddings

### Transformers (ONNX) Embeddings

  * Prerequisites
  * Auto-configuration
  * Embedding Properties
  * Errors and special cases
  * Manual Configuration

The `TransformersEmbeddingModel` is an `EmbeddingModel` implementation that
locally computes sentence embeddings using a selected sentence transformer.

You can use any HuggingFace Embedding model.

It uses pre-trained transformer models, serialized into the Open Neural Network
Exchange (ONNX) format.

The Deep Java Library and the Microsoft ONNX Java Runtime libraries are applied
to run the ONNX models and compute the embeddings in Java.

##  Prerequisites

To run things in Java, we need to **serialize the Tokenizer and the Transformer
Model** into `ONNX` format.

Serialize with optimum-cli - One, quick, way to achieve this, is to use the
optimum-cli command line tool. The following snippet prepares a python virtual
environment, installs the required packages and serializes (e.g. exports) the
specified model using `optimum-cli` :

```

python3 -m venv venv

source ./venv/bin/activate

(venv) pip install --upgrade pip

(venv) pip install optimum onnx onnxruntime sentence-transformers

(venv) optimum-cli export onnx --model sentence-transformers/all-MiniLM-L6-v2
onnx-output-folder

Copied!

```

The snippet exports the sentence-transformers/all-MiniLM-L6-v2 transformer into
the `onnx-output-folder` folder. The latter includes the `tokenizer.json` and
`model.onnx` files used by the embedding model.

In place of the all-MiniLM-L6-v2 you can pick any huggingface transformer
identifier or provide direct file path.

##  Auto-configuration

|  There has been a significant change in the Spring AI auto-configuration,
starter modules' artifact names. Please refer to the upgrade notes for more
information.  
---|---  
Spring AI provides Spring Boot auto-configuration for the ONNX Transformer
Embedding Model. To enable it add the following dependency to your project’s
Maven `pom.xml` file:

```

<dependency>

    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-starter-model-transformers</artifactId>
</dependency>

Copied!

```

or to your Gradle `build.gradle` build file.

```

dependencies {

    implementation 'org.springframework.ai:spring-ai-starter-model-transformers'
}

Copied!

```

|  Refer to the Dependency Management section to add the Spring AI BOM to your
build file. Refer to the Artifact Repositories section to add these repositories
to your build system.  
---|---  
To configure it, use the `spring.ai.embedding.transformer.*` properties.

For example, add this to your _application.properties_ file to configure the
client with the intfloat/e5-small-v2 text embedding model:

```

spring.ai.embedding.transformer.onnx.modelUri=https://huggingface.co/intfloat/e5-small-v2/resolve/main/model.onnx

spring.ai.embedding.transformer.tokenizer.uri=https://huggingface.co/intfloat/e5-small-v2/raw/main/tokenizer.json

```

The complete list of supported properties are:

###  Embedding Properties

|  Enabling and disabling of the embedding auto-configurations are now
configured via top level properties with the prefix `spring.ai.model.embedding`.
To enable, spring.ai.model.embedding=transformers (It is enabled by default) To
disable, spring.ai.model.embedding=none (or any value which doesn’t match
transformers) This change is done to allow configuration of multiple models.  
---|---  
Property | Description | Default  
---|---|---  
spring.ai.embedding.transformer.enabled (Removed and no longer valid) | Enable the Transformer Embedding model. | true  
spring.ai.model.embedding | Enable the Transformer Embedding model. | transformers  
spring.ai.embedding.transformer.tokenizer.uri | URI of a pre-trained HuggingFaceTokenizer created by the ONNX engine (e.g. tokenizer.json). | onnx/all-MiniLM-L6-v2/tokenizer.json  
spring.ai.embedding.transformer.tokenizer.options | HuggingFaceTokenizer options such as ‘addSpecialTokens’, ‘modelMaxLength’, ‘truncation’, ‘padding’, ‘maxLength’, ‘stride’, ‘padToMultipleOf’. Leave empty to fallback to the defaults. | empty  
spring.ai.embedding.transformer.cache.enabled | Enable remote Resource caching. | true  
spring.ai.embedding.transformer.cache.directory | Directory path to cache remote resources, such as the ONNX models | ${java.io.tmpdir}/spring-ai-onnx-model  
spring.ai.embedding.transformer.onnx.modelUri | Existing, pre-trained ONNX model. | onnx/all-MiniLM-L6-v2/model.onnx  
spring.ai.embedding.transformer.onnx.modelOutputName | The ONNX model’s output node name, which we’ll use for embedding calculation. | last_hidden_state  
spring.ai.embedding.transformer.onnx.gpuDeviceId | The GPU device ID to execute on. Only applicable if >= 0. Ignored otherwise.(Requires additional onnxruntime_gpu dependency) | -1  
spring.ai.embedding.transformer.metadataMode | Specifies what parts of the Documents content and metadata will be used for computing the embeddings. | NONE  
###  Errors and special cases

|  If you see an error like `Caused by: ai.onnxruntime.OrtException: Supplied
array is ragged,..`, you need to also enable the tokenizer padding in
`application.properties` as follows: ```

spring.ai.embedding.transformer.tokenizer.options.padding=true

```

  
---|---  
|  If you get an error like `The generative output names don’t contain expected:
last_hidden_state. Consider one of the available model outputs:
token_embeddings, …​.`, you need to set the model output name to a correct value
per your models. Consider the names listed in the error message. For example:
```

spring.ai.embedding.transformer.onnx.modelOutputName=token_embeddings

```

  
---|---  
|  If you get an error like `ai.onnxruntime.OrtException: Error code - ORT_FAIL
- message: Deserialize tensor onnx::MatMul_10319 failed.GetFileLength for
./model.onnx_data failed:Invalid fd was supplied: -1`, that means that you model
is larger than 2GB and is serialized in two files: `model.onnx` and
`model.onnx_data`. The `model.onnx_data` is called External Data and is expected
to be under the same directory of the `model.onnx`. Currently the only
workaround is to copy the large `model.onnx_data` in the folder you run your
Boot application.  
---|---  
|  If you get an error like `ai.onnxruntime.OrtException: Error code -
ORT_EP_FAIL - message: Failed to find CUDA shared provider`, that means that you
are using the GPU parameters `spring.ai.embedding.transformer.onnx.gpuDeviceId`
, but the onnxruntime_gpu dependency are missing. ```

<dependency>

    <groupId>com.microsoft.onnxruntime</groupId>
    <artifactId>onnxruntime_gpu</artifactId>
</dependency>

```

Please select the appropriate onnxruntime_gpu version based on the CUDA
version(ONNX Java Runtime).  
---|---  
##  Manual Configuration

If you are not using Spring Boot, you can manually configure the Onnx
Transformers Embedding Model. For this add the `spring-ai-transformers`
dependency to your project’s Maven `pom.xml` file:

```

<dependency>

  <groupId>org.springframework.ai</groupId>

  <artifactId>spring-ai-transformers</artifactId>

</dependency>

Copied!

```

|  Refer to the Dependency Management section to add the Spring AI BOM to your
build file.  
---|---  
then create a new `TransformersEmbeddingModel` instance and use the
`setTokenizerResource(tokenizerJsonUri)` and `setModelResource(modelOnnxUri)`
methods to set the URIs of the exported `tokenizer.json` and `model.onnx` files.
(`classpath:`, `file:` or `https:` URI schemas are supported).

If the model is not explicitly set, `TransformersEmbeddingModel` defaults to
sentence-transformers/all-MiniLM-L6-v2:

Dimensions | 384  
---|---  
Avg. performance | 58.80  
Speed | 14200 sentences/sec  
Size | 80MB  
The following snippet illustrates how to use the `TransformersEmbeddingModel`
manually:

```

TransformersEmbeddingModel embeddingModel = new TransformersEmbeddingModel();

// (optional) defaults to classpath:/onnx/all-MiniLM-L6-v2/tokenizer.json

embeddingModel.setTokenizerResource("classpath:/onnx/all-
MiniLM-L6-v2/tokenizer.json");

// (optional) defaults to classpath:/onnx/all-MiniLM-L6-v2/model.onnx

embeddingModel.setModelResource("classpath:/onnx/all-MiniLM-L6-v2/model.onnx");

// (optional) defaults to ${java.io.tmpdir}/spring-ai-onnx-model

// Only the http/https resources are cached by default.

embeddingModel.setResourceCacheDirectory("/tmp/onnx-zoo");

// (optional) Set the tokenizer padding if you see an errors like:

// "ai.onnxruntime.OrtException: Supplied array is ragged, ..."

embeddingModel.setTokenizerOptions(Map.of("padding", "true"));

embeddingModel.afterPropertiesSet();

List<List<Double>> embeddings = this.embeddingModel.embed(List.of("Hello world",
"World is big"));

Copied!

```

|  If you create an instance of `TransformersEmbeddingModel` manually, you must
call the `afterPropertiesSet()` method after setting the properties and before
using the client.  
---|---  
The first `embed()` call downloads the large ONNX model and caches it on the
local file system. Therefore, the first call might take longer than usual. Use
the `#setResourceCacheDirectory(<path>)` method to set the local folder where
the ONNX models as stored. The default cache folder is
`${java.io.tmpdir}/spring-ai-onnx-model`.

It is more convenient (and preferred) to create the TransformersEmbeddingModel
as a `Bean`. Then you don’t have to call the `afterPropertiesSet()` manually.

```

@Bean

public EmbeddingModel embeddingModel() {

   return new TransformersEmbeddingModel();

}

Copied!

```

Ollama OpenAI

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

