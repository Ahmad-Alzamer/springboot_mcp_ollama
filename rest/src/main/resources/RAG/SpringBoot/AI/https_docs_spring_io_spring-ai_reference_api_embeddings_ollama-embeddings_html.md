source URL: https://docs.spring.io/spring-ai/reference/api/embeddings/ollama-embeddings.html 
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

### Ollama Embeddings

  * Prerequisites
  * Auto-configuration
  * Base Properties
  * Embedding Properties
  * Runtime Options
  * Auto-pulling Models
  * HuggingFace Models
  * Sample Controller
  * Manual Configuration

  * Spring AI
  * Reference
  * Models
  * Embedding Models
  * Ollama

# Ollama Embeddings

### Ollama Embeddings

  * Prerequisites
  * Auto-configuration
  * Base Properties
  * Embedding Properties
  * Runtime Options
  * Auto-pulling Models
  * HuggingFace Models
  * Sample Controller
  * Manual Configuration

With Ollama you can run various AI Models locally and generate embeddings from
them. An embedding is a vector (list) of floating point numbers. The distance
between two vectors measures their relatedness. Small distances suggest high
relatedness and large distances suggest low relatedness.

The `OllamaEmbeddingModel` implementation leverages the Ollama Embeddings API
endpoint.

##  Prerequisites

You first need access to an Ollama instance. There are a few options, including
the following:

  * Download and install Ollama on your local machine.
  * Configure and run Ollama via Testcontainers.
  * Bind to an Ollama instance via Kubernetes Service Bindings.

You can pull the models you want to use in your application from the Ollama
model library:

```

ollama pull <model-name>

Copied!

```

You can also pull any of the thousands, free, GGUF Hugging Face Models:

```

ollama pull hf.co/<username>/<model-repository>

Copied!

```

Alternatively, you can enable the option to download automatically any needed
model: Auto-pulling Models.

##  Auto-configuration

|  There has been a significant change in the Spring AI auto-configuration,
starter modules' artifact names. Please refer to the upgrade notes for more
information.  
---|---  
Spring AI provides Spring Boot auto-configuration for the Azure Ollama Embedding
Model. To enable it add the following dependency to your Maven `pom.xml` or
Gradle `build.gradle` build files:

  * Maven
  * Gradle

```

<dependency>

   <groupId>org.springframework.ai</groupId>

   <artifactId>spring-ai-starter-model-ollama</artifactId>

</dependency>

Copied!

```

```

dependencies {

    implementation 'org.springframework.ai:spring-ai-starter-model-ollama'
}

Copied!

```

|  Refer to the Dependency Management section to add the Spring AI BOM to your
build file. Spring AI artifacts are published in Maven Central and Spring
Snapshot repositories. Refer to the Repositories section to add these
repositories to your build system.  
---|---  
###  Base Properties

The prefix `spring.ai.ollama` is the property prefix to configure the connection
to Ollama

Property | Description | Default  
---|---|---  
spring.ai.ollama.base-url | Base URL where Ollama API server is running. | `localhost:11434`  
Here are the properties for initializing the Ollama integration and auto-pulling
models.

Property | Description | Default  
---|---|---  
spring.ai.ollama.init.pull-model-strategy | Whether to pull models at startup-time and how. | `never`  
spring.ai.ollama.init.timeout | How long to wait for a model to be pulled. | `5m`  
spring.ai.ollama.init.max-retries | Maximum number of retries for the model pull operation. | `0`  
spring.ai.ollama.init.embedding.include | Include this type of models in the initialization task. | `true`  
spring.ai.ollama.init.embedding.additional-models | Additional models to initialize besides the ones configured via default properties. | `[]`  
###  Embedding Properties

|  Enabling and disabling of the embedding auto-configurations are now
configured via top level properties with the prefix `spring.ai.model.embedding`.
To enable, spring.ai.model.embedding=ollama (It is enabled by default) To
disable, spring.ai.model.embedding=none (or any value which doesn’t match
ollama) This change is done to allow configuration of multiple models.  
---|---  
The prefix `spring.ai.ollama.embedding.options` is the property prefix that
configures the Ollama embedding model. It includes the Ollama request (advanced)
parameters such as the `model`, `keep-alive`, and `truncate` as well as the
Ollama model `options` properties.

Here are the advanced request parameter for the Ollama embedding model:

Property | Description | Default  
---|---|---  
spring.ai.ollama.embedding.enabled (Removed and no longer valid) | Enables the Ollama embedding model auto-configuration. | true  
spring.ai.model.embedding | Enables the Ollama embedding model auto-configuration. | ollama  
spring.ai.ollama.embedding.options.model | The name of the supported model to use. You can use dedicated Embedding Model types | mistral  
spring.ai.ollama.embedding.options.keep_alive | Controls how long the model will stay loaded into memory following the request | 5m  
spring.ai.ollama.embedding.options.truncate | Truncates the end of each input to fit within context length. Returns error if false and context length is exceeded. | true  
The remaining `options` properties are based on the Ollama Valid Parameters and
Values and Ollama Types. The default values are based on: Ollama type defaults.

Property | Description | Default  
---|---|---  
spring.ai.ollama.embedding.options.numa | Whether to use NUMA. | false  
spring.ai.ollama.embedding.options.num-ctx | Sets the size of the context window used to generate the next token. | 2048  
spring.ai.ollama.embedding.options.num-batch | Prompt processing maximum batch size. | 512  
spring.ai.ollama.embedding.options.num-gpu | The number of layers to send to the GPU(s). On macOS it defaults to 1 to enable metal support, 0 to disable. 1 here indicates that NumGPU should be set dynamically | -1  
spring.ai.ollama.embedding.options.main-gpu | When using multiple GPUs this option controls which GPU is used for small tensors for which the overhead of splitting the computation across all GPUs is not worthwhile. The GPU in question will use slightly more VRAM to store a scratch buffer for temporary results. | 0  
spring.ai.ollama.embedding.options.low-vram | - | false  
spring.ai.ollama.embedding.options.f16-kv | - | true  
spring.ai.ollama.embedding.options.logits-all | Return logits for all the tokens, not just the last one. To enable completions to return logprobs, this must be true. | -  
spring.ai.ollama.embedding.options.vocab-only | Load only the vocabulary, not the weights. | -  
spring.ai.ollama.embedding.options.use-mmap | By default, models are mapped into memory, which allows the system to load only the necessary parts of the model as needed. However, if the model is larger than your total amount of RAM or if your system is low on available memory, using mmap might increase the risk of pageouts, negatively impacting performance. Disabling mmap results in slower load times but may reduce pageouts if you’re not using mlock. Note that if the model is larger than the total amount of RAM, turning off mmap would prevent the model from loading at all. | null  
spring.ai.ollama.embedding.options.use-mlock | Lock the model in memory, preventing it from being swapped out when memory-mapped. This can improve performance but trades away some of the advantages of memory-mapping by requiring more RAM to run and potentially slowing down load times as the model loads into RAM. | false  
spring.ai.ollama.embedding.options.num-thread | Sets the number of threads to use during computation. By default, Ollama will detect this for optimal performance. It is recommended to set this value to the number of physical CPU cores your system has (as opposed to the logical number of cores). 0 = let the runtime decide | 0  
spring.ai.ollama.embedding.options.num-keep | - | 4  
spring.ai.ollama.embedding.options.seed | Sets the random number seed to use for generation. Setting this to a specific number will make the model generate the same text for the same prompt. | -1  
spring.ai.ollama.embedding.options.num-predict | Maximum number of tokens to predict when generating text. (-1 = infinite generation, -2 = fill context) | -1  
spring.ai.ollama.embedding.options.top-k | Reduces the probability of generating nonsense. A higher value (e.g., 100) will give more diverse answers, while a lower value (e.g., 10) will be more conservative. | 40  
spring.ai.ollama.embedding.options.top-p | Works together with top-k. A higher value (e.g., 0.95) will lead to more diverse text, while a lower value (e.g., 0.5) will generate more focused and conservative text. | 0.9  
spring.ai.ollama.embedding.options.min-p | Alternative to the top_p, and aims to ensure a balance of quality and variety. The parameter p represents the minimum probability for a token to be considered, relative to the probability of the most likely token. For example, with p=0.05 and the most likely token having a probability of 0.9, logits with a value less than 0.045 are filtered out. | 0.0  
spring.ai.ollama.embedding.options.tfs-z | Tail-free sampling is used to reduce the impact of less probable tokens from the output. A higher value (e.g., 2.0) will reduce the impact more, while a value of 1.0 disables this setting. | 1.0  
spring.ai.ollama.embedding.options.typical-p | - | 1.0  
spring.ai.ollama.embedding.options.repeat-last-n | Sets how far back for the model to look back to prevent repetition. (Default: 64, 0 = disabled, -1 = num_ctx) | 64  
spring.ai.ollama.embedding.options.temperature | The temperature of the model. Increasing the temperature will make the model answer more creatively. | 0.8  
spring.ai.ollama.embedding.options.repeat-penalty | Sets how strongly to penalize repetitions. A higher value (e.g., 1.5) will penalize repetitions more strongly, while a lower value (e.g., 0.9) will be more lenient. | 1.1  
spring.ai.ollama.embedding.options.presence-penalty | - | 0.0  
spring.ai.ollama.embedding.options.frequency-penalty | - | 0.0  
spring.ai.ollama.embedding.options.mirostat | Enable Mirostat sampling for controlling perplexity. (default: 0, 0 = disabled, 1 = Mirostat, 2 = Mirostat 2.0) | 0  
spring.ai.ollama.embedding.options.mirostat-tau | Controls the balance between coherence and diversity of the output. A lower value will result in more focused and coherent text. | 5.0  
spring.ai.ollama.embedding.options.mirostat-eta | Influences how quickly the algorithm responds to feedback from the generated text. A lower learning rate will result in slower adjustments, while a higher learning rate will make the algorithm more responsive. | 0.1  
spring.ai.ollama.embedding.options.penalize-newline | - | true  
spring.ai.ollama.embedding.options.stop | Sets the stop sequences to use. When this pattern is encountered the LLM will stop generating text and return. Multiple stop patterns may be set by specifying multiple separate stop parameters in a modelfile. | -  
spring.ai.ollama.embedding.options.functions | List of functions, identified by their names, to enable for function calling in a single prompt requests. Functions with those names must exist in the functionCallbacks registry. | -  
|  All properties prefixed with `spring.ai.ollama.embedding.options` can be
overridden at runtime by adding a request specific Runtime Options to the
`EmbeddingRequest` call.  
---|---  
##  Runtime Options

The OllamaOptions.java provides the Ollama configurations, such as the model to
use, the low level GPU and CPU tuning, etc.

The default options can be configured using the
`spring.ai.ollama.embedding.options` properties as well.

At start-time use the `OllamaEmbeddingModel(OllamaApi ollamaApi, OllamaOptions
defaultOptions)` to configure the default options used for all embedding
requests. At run-time you can override the default options, using a
`OllamaOptions` instance as part of your `EmbeddingRequest`.

For example to override the default model name for a specific request:

```

EmbeddingResponse embeddingResponse = embeddingModel.call(

    new EmbeddingRequest(List.of("Hello World", "World is big and salvation is near"),
        OllamaOptions.builder()
            .model("Different-Embedding-Model-Deployment-Name"))
            .truncates(false)
            .build());
Copied!

```

##  Auto-pulling Models

Spring AI Ollama can automatically pull models when they are not available in
your Ollama instance. This feature is particularly useful for development and
testing as well as for deploying your applications to new environments.

|  You can also pull, by name, any of the thousands, free, GGUF Hugging Face
Models.  
---|---  
There are three strategies for pulling models:

  * `always` (defined in `PullModelStrategy.ALWAYS`): Always pull the model, even if it’s already available. Useful to ensure you’re using the latest version of the model.
  * `when_missing` (defined in `PullModelStrategy.WHEN_MISSING`): Only pull the model if it’s not already available. This may result in using an older version of the model.
  * `never` (defined in `PullModelStrategy.NEVER`): Never pull the model automatically.

|  Due to potential delays while downloading models, automatic pulling is not
recommended for production environments. Instead, consider assessing and pre-
downloading the necessary models in advance.  
---|---  
All models defined via configuration properties and default options can be
automatically pulled at startup time. You can configure the pull strategy,
timeout, and maximum number of retries using configuration properties:

```

spring:

  ai:

    ollama:
      init:
        pull-model-strategy: always
        timeout: 60s
        max-retries: 1
Copied!

```

|  The application will not complete its initialization until all specified
models are available in Ollama. Depending on the model size and internet
connection speed, this may significantly slow down your application’s startup
time.  
---|---  
You can initialize additional models at startup, which is useful for models used
dynamically at runtime:

```

spring:

  ai:

    ollama:
      init:
        pull-model-strategy: always
        embedding:
          additional-models:
            - mxbai-embed-large
            - nomic-embed-text
Copied!

```

If you want to apply the pulling strategy only to specific types of models, you
can exclude embedding models from the initialization task:

```

spring:

  ai:

    ollama:
      init:
        pull-model-strategy: always
        embedding:
          include: false
Copied!

```

This configuration will apply the pulling strategy to all models except
embedding models.

##  HuggingFace Models

Ollama can access, out of the box, all GGUF Hugging Face Embedding models. You
can pull any of these models by name: `ollama pull hf.co/<username>/<model-
repository>` or configure the auto-pulling strategy: Auto-pulling Models:

```

spring.ai.ollama.embedding.options.model=hf.co/mixedbread-ai/mxbai-embed-
large-v1

spring.ai.ollama.init.pull-model-strategy=always

Copied!

```

  * `spring.ai.ollama.embedding.options.model`: Specifies the Hugging Face GGUF model to use.
  * `spring.ai.ollama.init.pull-model-strategy=always`: (optional) Enables automatic model pulling at startup time. For production, you should pre-download the models to avoid delays: `ollama pull hf.co/mixedbread-ai/mxbai-embed-large-v1`.

##  Sample Controller

This will create a `EmbeddingModel` implementation that you can inject into your
class. Here is an example of a simple `@Controller` class that uses the
`EmbeddingModel` implementation.

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

If you are not using Spring Boot, you can manually configure the
`OllamaEmbeddingModel`. For this add the spring-ai-ollama dependency to your
project’s Maven pom.xml or Gradle `build.gradle` build files:

  * Maven
  * Gradle

```

<dependency>

    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-ollama</artifactId>
</dependency>

Copied!

```

```

dependencies {

    implementation 'org.springframework.ai:spring-ai-ollama'
}

Copied!

```

|  Refer to the Dependency Management section to add the Spring AI BOM to your
build file.  
---|---  
|  The `spring-ai-ollama` dependency provides access also to the
`OllamaChatModel`. For more information about the `OllamaChatModel` refer to the
Ollama Chat Client section.  
---|---  
Next, create an `OllamaEmbeddingModel` instance and use it to compute the
embeddings for two input texts using a dedicated `chroma/all-minilm-l6-v2-f32`
embedding models:

```

var ollamaApi = OllamaApi.builder().build();

var embeddingModel = new OllamaEmbeddingModel(this.ollamaApi,

        OllamaOptions.builder()
			.model(OllamaModel.MISTRAL.id())
            .build());

EmbeddingResponse embeddingResponse = this.embeddingModel.call(

    new EmbeddingRequest(List.of("Hello World", "World is big and salvation is near"),
        OllamaOptions.builder()
            .model("chroma/all-minilm-l6-v2-f32"))
            .truncate(false)
            .build());
Copied!

```

The `OllamaOptions` provides the configuration information for all embedding
requests.

OCI GenAI (ONNX) Transformers

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

