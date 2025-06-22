source URL: https://docs.spring.io/spring-ai/reference/observability/index.html 
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

### Observability

  * Chat Client
  * Prompt Content
  * Input Data (Deprecated)
  * Chat Client Advisors
  * Chat Model
  * Chat Prompt and Completion Data
  * Tool Calling
  * Tool Call Arguments and Result Data
  * EmbeddingModel
  * Image Model
  * Image Prompt Data
  * Vector Stores
  * Response Data

  * Spring AI
  * Reference
  * Observability

# Observability

### Observability

  * Chat Client
  * Prompt Content
  * Input Data (Deprecated)
  * Chat Client Advisors
  * Chat Model
  * Chat Prompt and Completion Data
  * Tool Calling
  * Tool Call Arguments and Result Data
  * EmbeddingModel
  * Image Model
  * Image Prompt Data
  * Vector Stores
  * Response Data

Spring AI builds upon the observability features in the Spring ecosystem to
provide insights into AI-related operations. Spring AI provides metrics and
tracing capabilities for its core components: `ChatClient` (including
`Advisor`), `ChatModel`, `EmbeddingModel`, `ImageModel`, and `VectorStore`.

|  Low cardinality keys will be added to metrics and traces, while high
cardinality keys will only be added to traces.  
---|---  
|  **1.0.0-RC1 Breaking Changes** Following configuration properties have been
renamed to better reflect their purpose:

  * `spring.ai.chat.client.observations.include-prompt` → `spring.ai.chat.client.observations.log-prompt`
  * `spring.ai.chat.observations.include-prompt` → `spring.ai.chat.observations.log-prompt`
  * `spring.ai.chat.observations.include-completion` → `spring.ai.chat.observations.log-completion`
  * `spring.ai.image.observations.include-prompt` → `spring.ai.image.observations.log-prompt`
  * `spring.ai.vectorstore.observations.include-query-response` → `spring.ai.vectorstore.observations.log-query-response`

  
---|---  
##  Chat Client

The `spring.ai.chat.client` observations are recorded when a ChatClient `call()`
or `stream()` operations are invoked. They measure the time spent performing the
invocation and propagate the related tracing information.

Table 1. Low Cardinality Keys Name | Description  
---|---  
`gen_ai.operation.name` |  Always `framework`.  
`gen_ai.system` |  Always `spring_ai`.  
`spring.ai.chat.client.stream` |  Is the chat model response a stream - `true or false`  
`spring.ai.kind` |  The kind of framework API in Spring AI: `chat_client`.  
Table 2. High Cardinality Keys Name | Description  
---|---  
`gen_ai.prompt` |  The content of the prompt sent via the chat client. Optional.  
`spring.ai.chat.client.advisor.params` (deprecated) |  Map of advisor parameters. The conversation ID is now included in `spring.ai.chat.client.conversation.id`.  
`spring.ai.chat.client.advisors` |  List of configured chat client advisors.  
`spring.ai.chat.client.conversation.id` |  Identifier of the conversation when using the chat memory.  
`spring.ai.chat.client.system.params` (deprecated) |  Chat client system parameters. Optional. Superseded by `gen_ai.prompt`.  
`spring.ai.chat.client.system.text` (deprecated) |  Chat client system text. Optional. Superseded by `gen_ai.prompt`.  
`spring.ai.chat.client.tool.function.names` (deprecated) |  Enabled tool function names. Superseded by `spring.ai.chat.client.tool.names`.  
`spring.ai.chat.client.tool.function.callbacks` (deprecated) |  List of configured chat client function callbacks. Superseded by `spring.ai.chat.client.tool.names`.  
`spring.ai.chat.client.tool.names` |  Names of the tools passed to the chat client.  
`spring.ai.chat.client.user.params` (deprecated) |  Chat client user parameters. Optional. Superseded by `gen_ai.prompt`.  
`spring.ai.chat.client.user.text` (deprecated) |  Chat client user text. Optional. Superseded by `gen_ai.prompt`.  
###  Prompt Content

The `ChatClient` prompt content is typically big and possibly containing
sensitive information. For those reasons, it is not exported by default.

Spring AI supports logging the prompt content to help with debugging and
troubleshooting.

Property | Description | Default  
---|---|---  
`spring.ai.chat.client.observations.log-prompt` | Whether to log the chat client prompt content. | `false`  
|  If you enable logging of the chat client prompt content, there’s a risk of
exposing sensitive or private information. Please, be careful!  
---|---  
###  Input Data (Deprecated)

|  The `spring.ai.chat.client.observations.include-input` property is
deprecated, replaced by `spring.ai.chat.client.observations.log-prompt`. See
Prompt Content.  
---|---  
The `ChatClient` input data is typically big and possibly containing sensitive
information. For those reasons, it is not exported by default.

Spring AI supports logging input data to help with debugging and
troubleshooting.

Property | Description | Default  
---|---|---  
`spring.ai.chat.client.observations.include-input` | Whether to include the input content in the observations. | `false`  
|  If you enable the inclusion of the input content in the observations, there’s
a risk of exposing sensitive or private information. Please, be careful!  
---|---  
###  Chat Client Advisors

The `spring.ai.advisor` observations are recorded when an advisor is executed.
They measure the time spent in the advisor (including the time spend on the
inner advisors) and propagate the related tracing information.

Table 3. Low Cardinality Keys Name | Description  
---|---  
`gen_ai.operation.name` |  Always `framework`.  
`gen_ai.system` |  Always `spring_ai`.  
`spring.ai.advisor.type` (deprecated) |  Where the advisor applies it’s logic in the request processing, one of `BEFORE`, `AFTER`, or `AROUND`. This distinction doesn’t apply anymore since all Advisors are always of the same type.  
`spring.ai.kind` |  The kind of framework API in Spring AI: `advisor`.  
Table 4. High Cardinality Keys Name | Description  
---|---  
`spring.ai.advisor.name` |  Name of the advisor.  
`spring.ai.advisor.order` |  Advisor order in the advisor chain.  
##  Chat Model

|  Observability features are currently supported only for `ChatModel`
implementations from the following AI model providers: Anthropic, Azure OpenAI,
Mistral AI, Ollama, OpenAI, Vertex AI, MiniMax, Moonshot, QianFan, Zhiu AI.
Additional AI model providers will be supported in a future release.  
---|---  
The `gen_ai.client.operation` observations are recorded when calling the
ChatModel `call` or `stream` methods. They measure the time spent on method
completion and propagate the related tracing information.

|  The `gen_ai.client.token.usage` metrics measures number of input and output
tokens used by a single model call.  
---|---  
Table 5. Low Cardinality Keys Name | Description  
---|---  
`gen_ai.operation.name` |  The name of the operation being performed.  
`gen_ai.system` |  The model provider as identified by the client instrumentation.  
`gen_ai.request.model` |  The name of the model a request is being made to.  
`gen_ai.response.model` |  The name of the model that generated the response.  
Table 6. High Cardinality Keys Name | Description  
---|---  
`gen_ai.request.frequency_penalty` |  The frequency penalty setting for the model request.  
`gen_ai.request.max_tokens` |  The maximum number of tokens the model generates for a request.  
`gen_ai.request.presence_penalty` |  The presence penalty setting for the model request.  
`gen_ai.request.stop_sequences` |  List of sequences that the model will use to stop generating further tokens.  
`gen_ai.request.temperature` |  The temperature setting for the model request.  
`gen_ai.request.top_k` |  The top_k sampling setting for the model request.  
`gen_ai.request.top_p` |  The top_p sampling setting for the model request.  
`gen_ai.response.finish_reasons` |  Reasons the model stopped generating tokens, corresponding to each generation received.  
`gen_ai.response.id` |  The unique identifier for the AI response.  
`gen_ai.usage.input_tokens` |  The number of tokens used in the model input (prompt).  
`gen_ai.usage.output_tokens` |  The number of tokens used in the model output (completion).  
`gen_ai.usage.total_tokens` |  The total number of tokens used in the model exchange.  
`gen_ai.prompt` |  The full prompt sent to the model. Optional.  
`gen_ai.completion` |  The full response received from the model. Optional.  
`spring.ai.model.request.tool.names` |  List of tool definitions provided to the model in the request.  
|  For measuring user tokens, the previous table lists the values present in an
observation trace. Use the metric name `gen_ai.client.token.usage` that is
provided by the `ChatModel`.  
---|---  
###  Chat Prompt and Completion Data

The chat prompt and completion data is typically big and possibly containing
sensitive information. For those reasons, it is not exported by default.

Spring AI supports logging chat prompt and completion data, useful for
troubleshooting scenarios. When tracing is available, the logs will include
trace information for better correlation.

Property | Description | Default  
---|---|---  
`spring.ai.chat.observations.log-prompt` | Log the prompt content. `true` or `false` | `false`  
`spring.ai.chat.observations.log-completion` | Log the completion content. `true` or `false` | `false`  
`spring.ai.chat.observations.include-error-logging` | Include error logging in observations. `true` or `false` | `false`  
|  If you enable logging of the chat prompt and completion data, there’s a risk
of exposing sensitive or private information. Please, be careful!  
---|---  
##  Tool Calling

The `spring.ai.tool` observations are recorded when performing tool calling in
the context of a chat model interaction. They measure the time spent on toll
call completion and propagate the related tracing information.

Table 7. Low Cardinality Keys Name | Description  
---|---  
`gen_ai.operation.name` |  The name of the operation being performed. It’s always `framework`.  
`gen_ai.system` |  The provider responsible for the operation. It’s always `spring_ai`.  
`spring.ai.kind` |  The kind of operation performed by Spring AI. It’s always `tool_call`.  
`spring.ai.tool.definition.name` |  The name of the tool.  
Table 8. High Cardinality Keys Name |  Description  
---|---  
`spring.ai.tool.definition.description` |  Description of the tool.  
`spring.ai.tool.definition.schema` |  Schema of the parameters used to call the tool.  
`spring.ai.tool.call.arguments` |  The input arguments to the tool call. (Only when enabled)  
`spring.ai.tool.call.result` |  Schema of the parameters used to call the tool. (Only when enabled)  
###  Tool Call Arguments and Result Data

The input arguments and result from the tool call are not exported by default,
as they can be potentially sensitive.

Spring AI supports exporting tool call arguments and result data as span
attributes.

Property | Description | Default  
---|---|---  
`spring.ai.tools.observations.include-content` | Include the tool call content in observations. `true` or `false` | `false`  
|  If you enable the inclusion of the tool call arguments and result in the
observations, there’s a risk of exposing sensitive or private information.
Please, be careful!  
---|---  
##  EmbeddingModel

|  Observability features are currently supported only for `EmbeddingModel`
implementations from the following AI model providers: Azure OpenAI, Mistral AI,
Ollama, and OpenAI. Additional AI model providers will be supported in a future
release.  
---|---  
The `gen_ai.client.operation` observations are recorded on embedding model
method calls. They measure the time spent on method completion and propagate the
related tracing information.

|  The `gen_ai.client.token.usage` metrics measures number of input and output
tokens used by a single model call.  
---|---  
Table 9. Low Cardinality Keys Name | Description  
---|---  
`gen_ai.operation.name` |  The name of the operation being performed.  
`gen_ai.system` |  The model provider as identified by the client instrumentation.  
`gen_ai.request.model` |  The name of the model a request is being made to.  
`gen_ai.response.model` |  The name of the model that generated the response.  
Table 10. High Cardinality Keys Name | Description  
---|---  
`gen_ai.request.embedding.dimensions` |  The number of dimensions the resulting output embeddings have.  
`gen_ai.usage.input_tokens` |  The number of tokens used in the model input.  
`gen_ai.usage.total_tokens` |  The total number of tokens used in the model exchange.  
|  For measuring user tokens, the previous table lists the values present in an
observation trace. Use the metric name `gen_ai.client.token.usage` that is
provided by the `EmbeddingModel`.  
---|---  
##  Image Model

|  Observability features are currently supported only for `ImageModel`
implementations from the following AI model providers: OpenAI. Additional AI
model providers will be supported in a future release.  
---|---  
The `gen_ai.client.operation` observations are recorded on image model method
calls. They measure the time spent on method completion and propagate the
related tracing information.

|  The `gen_ai.client.token.usage` metrics measures number of input and output
tokens used by a single model call.  
---|---  
Table 11. Low Cardinality Keys Name | Description  
---|---  
`gen_ai.operation.name` |  The name of the operation being performed.  
`gen_ai.system` |  The model provider as identified by the client instrumentation.  
`gen_ai.request.model` |  The name of the model a request is being made to.  
Table 12. High Cardinality Keys Name | Description  
---|---  
`gen_ai.request.image.response_format` | The format in which the generated image is returned.  
`gen_ai.request.image.size` | The size of the image to generate.  
`gen_ai.request.image.style` | The style of the image to generate.  
`gen_ai.response.id` | The unique identifier for the AI response.  
`gen_ai.response.model` | The name of the model that generated the response.  
`gen_ai.usage.input_tokens` | The number of tokens used in the model input (prompt).  
`gen_ai.usage.output_tokens` | The number of tokens used in the model output (generation).  
`gen_ai.usage.total_tokens` | The total number of tokens used in the model exchange.  
`gen_ai.prompt` | The full prompt sent to the model. Optional.  
|  For measuring user tokens, the previous table lists the values present in an
observation trace. Use the metric name `gen_ai.client.token.usage` that is
provided by the `ImageModel`.  
---|---  
###  Image Prompt Data

The image prompt data is typically big and possibly containing sensitive
information. For those reasons, it is not exported by default.

Spring AI supports logging image prompt data, useful for troubleshooting
scenarios. When tracing is available, the logs will include trace information
for better correlation.

Property | Description | Default  
---|---|---  
`spring.ai.image.observations.log-prompt` | Log the image prompt content. `true` or `false` | `false`  
|  If you enable logging of the image prompt data, there’s a risk of exposing
sensitive or private information. Please, be careful!  
---|---  
##  Vector Stores

All vector store implementations in Spring AI are instrumented to provide
metrics and distributed tracing data through Micrometer.

The `db.vector.client.operation` observations are recorded when interacting with
the Vector Store. They measure the time spent on the `query`, `add` and `remove`
operations and propagate the related tracing information.

Table 13. Low Cardinality Keys Name | Description  
---|---  
`db.operation.name` |  The name of the operation or command being executed. One of `add`, `delete`, or `query`.  
`db.system` |  The database management system (DBMS) product as identified by the client instrumentation. One of `pg_vector`, `azure`, `cassandra`, `chroma`, `elasticsearch`, `milvus`, `neo4j`, `opensearch`, `qdrant`, `redis`, `typesense`, `weaviate`, `pinecone`, `oracle`, `mongodb`, `gemfire`, `hana`, `simple`.  
`spring.ai.kind` |  The kind of framework API in Spring AI: `vector_store`.  
Table 14. High Cardinality Keys Name | Description  
---|---  
`db.collection.name` |  The name of a collection (table, container) within the database.  
`db.namespace` |  The name of the database, fully qualified within the server address and port.  
`db.record.id` |  The record identifier if present.  
`db.search.similarity_metric` |  The metric used in similarity search.  
`db.vector.dimension_count` |  The dimension of the vector.  
`db.vector.field_name` |  The name field as of the vector (e.g. a field name).  
`db.vector.query.content` |  The content of the search query being executed.  
`db.vector.query.filter` |  The metadata filters used in the search query.  
`db.vector.query.response.documents` |  Returned documents from a similarity search query. Optional.  
`db.vector.query.similarity_threshold` |  Similarity threshold that accepts all search scores. A threshold value of 0.0 means any similarity is accepted or disable the similarity threshold filtering. A threshold value of 1.0 means an exact match is required.  
`db.vector.query.top_k` |  The top-k most similar vectors returned by a query.  
###  Response Data

The vector search response data is typically big and possibly containing
sensitive information. For those reasons, it is not exported by default.

Spring AI supports logging vector search response data, useful for
troubleshooting scenarios. When tracing is available, the logs will include
trace information for better correlation.

Property | Description | Default  
---|---|---  
`spring.ai.vectorstore.observations.log-query-response` | Log the vector store query response content. `true` or `false` | `false`  
|  If you enable logging of the vector search response data, there’s a risk of
exposing sensitive or private information. Please, be careful!  
---|---  
Weaviate Development-time Services

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

