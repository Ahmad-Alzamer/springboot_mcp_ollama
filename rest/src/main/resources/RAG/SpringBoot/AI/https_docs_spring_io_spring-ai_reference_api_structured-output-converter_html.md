source URL: https://docs.spring.io/spring-ai/reference/api/structured-output-converter.html 
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

### Structured Output Converter

  * Structured Output API
  * Available Converters
  * Using Converters
  * Bean Output Converter
  * Property Ordering in Generated Schema
  * Map Output Converter
  * List Output Converter
  * Supported AI Models
  * Built-in JSON mode

  * Spring AI
  * Reference
  * Structured Output

# Structured Output Converter

### Structured Output Converter

  * Structured Output API
  * Available Converters
  * Using Converters
  * Bean Output Converter
  * Property Ordering in Generated Schema
  * Map Output Converter
  * List Output Converter
  * Supported AI Models
  * Built-in JSON mode

|  As of 02.05.2024 the old `OutputParser`, `BeanOutputParser`,
`ListOutputParser` and `MapOutputParser` classes are deprecated in favor of the
new `StructuredOutputConverter`, `BeanOutputConverter`, `ListOutputConverter`
and `MapOutputConverter` implementations. the latter are drop-in replacements
for the former ones and provide the same functionality. The reason for the
change was primarily naming, as there isn’t any parsing being done, but also
have aligned with the Spring `org.springframework.core.convert.converter`
package bringing in some improved functionality.  
---|---  
The ability of LLMs to produce structured outputs is important for downstream
applications that rely on reliably parsing output values. Developers want to
quickly turn results from an AI model into data types, such as JSON, XML or Java
classes, that can be passed to other application functions and methods.

The Spring AI `Structured Output Converters` help to convert the LLM output into
a structured format. As shown in the following diagram, this approach operates
around the LLM text completion endpoint:

Generating structured outputs from Large Language Models (LLMs) using generic
completion APIs requires careful handling of inputs and outputs. The structured
output converter plays a crucial role before and after the LLM call, ensuring
the desired output structure is achieved.

Before the LLM call, the converter appends format instructions to the prompt,
providing explicit guidance to the models on generating the desired output
structure. These instructions act as a blueprint, shaping the model’s response
to conform to the specified format.

After the LLM call, the converter takes the model’s output text and transforms
it into instances of the structured type. This conversion process involves
parsing the raw text output and mapping it to the corresponding structured data
representation, such as JSON, XML, or domain-specific data structures.

|  The `StructuredOutputConverter` is a best effort to convert the model output
into a structured output. The AI Model is not guaranteed to return the
structured output as requested. The model may not understand the prompt or be
unable to generate the structured output as requested. Consider implementing a
validation mechanism to ensure the model output is as expected.  
---|---  
|  The `StructuredOutputConverter` is not used for LLM Tool Calling, as this
feature inherently provides structured outputs by default.  
---|---  
##  Structured Output API

The `StructuredOutputConverter` interface allows you to obtain structured
output, such as mapping the output to a Java class or an array of values from
the text-based AI Model output. The interface definition is:

```

public interface StructuredOutputConverter<T> extends Converter<String, T>,
FormatProvider {

}

Copied!

```

It combines the Spring Converter<String, T> interface and the `FormatProvider`
interface

```

public interface FormatProvider {

	String getFormat();
}

Copied!

```

The following diagram shows the data flow when using the structured output API.

The `FormatProvider` supplies specific formatting guidelines to the AI Model,
enabling it to produce text outputs that can be converted into the designated
target type `T` using the `Converter`. Here is an example of such formatting
instructions:

```

  Your response should be in JSON format.

  The data structure for the JSON should match this Java class:
java.util.HashMap

  Do not include any explanations, only provide a RFC8259 compliant JSON
response following this format without deviation.

```

The format instructions are most often appended to the end of the user input
using the PromptTemplate like this:

```

    StructuredOutputConverter outputConverter = ...
    String userInputTemplate = """
        ... user text input ....
        {format}
        """; // user input with a "format" placeholder.
    Prompt prompt = new Prompt(
       new PromptTemplate(
			   this.userInputTemplate,
          Map.of(..., "format", outputConverter.getFormat()) // replace the "format" placeholder with the converter's format.
       ).createMessage());
Copied!

```

The Converter<String, T> is responsible to transform output text from the model
into instances of the specified type `T`.

###  Available Converters

Currently, Spring AI provides `AbstractConversionServiceOutputConverter`,
`AbstractMessageOutputConverter`, `BeanOutputConverter`, `MapOutputConverter`
and `ListOutputConverter` implementations:

  * `AbstractConversionServiceOutputConverter<T>` - Offers a pre-configured GenericConversionService for transforming LLM output into the desired format. No default `FormatProvider` implementation is provided.
  * `AbstractMessageOutputConverter<T>` - Supplies a pre-configured MessageConverter for converting LLM output into the desired format. No default `FormatProvider` implementation is provided.
  * `BeanOutputConverter<T>` - Configured with a designated Java class (e.g., Bean) or a ParameterizedTypeReference, this converter employs a `FormatProvider` implementation that directs the AI Model to produce a JSON response compliant with a `DRAFT_2020_12`, `JSON Schema` derived from the specified Java class. Subsequently, it utilizes an `ObjectMapper` to deserialize the JSON output into a Java object instance of the target class.
  * `MapOutputConverter` - Extends the functionality of `AbstractMessageOutputConverter` with a `FormatProvider` implementation that guides the AI Model to generate an RFC8259 compliant JSON response. Additionally, it incorporates a converter implementation that utilizes the provided `MessageConverter` to translate the JSON payload into a `java.util.Map<String, Object>` instance.
  * `ListOutputConverter` - Extends the `AbstractConversionServiceOutputConverter` and includes a `FormatProvider` implementation tailored for comma-delimited list output. The converter implementation employs the provided `ConversionService` to transform the model text output into a `java.util.List`.

##  Using Converters

The following sections provide guides how to use the available converters to
generate structured outputs.

###  Bean Output Converter

The following example shows how to use `BeanOutputConverter` to generate the
filmography for an actor.

The target record representing actor’s filmography:

```

record ActorsFilms(String actor, List<String> movies) {

}

Copied!

```

Here is how to apply the BeanOutputConverter using the high-level, fluent
`ChatClient` API:

```

ActorsFilms actorsFilms = ChatClient.create(chatModel).prompt()

        .user(u -> u.text("Generate the filmography of 5 movies for {actor}.")
                    .param("actor", "Tom Hanks"))
        .call()
        .entity(ActorsFilms.class);
Copied!

```

or using the low-level `ChatModel` API directly:

```

BeanOutputConverter<ActorsFilms> beanOutputConverter =

    new BeanOutputConverter<>(ActorsFilms.class);

String format = this.beanOutputConverter.getFormat();

String actor = "Tom Hanks";

String template = """

        Generate the filmography of 5 movies for {actor}.
        {format}
        """;

Generation generation = chatModel.call(

    new PromptTemplate(this.template, Map.of("actor", this.actor, "format", this.format)).create()).getResult();

ActorsFilms actorsFilms =
this.beanOutputConverter.convert(this.generation.getOutput().getText());

Copied!

```

###  Property Ordering in Generated Schema

The `BeanOutputConverter` supports custom property ordering in the generated
JSON schema through the `@JsonPropertyOrder` annotation. This annotation allows
you to specify the exact sequence in which properties should appear in the
schema, regardless of their declaration order in the class or record.

For example, to ensure specific ordering of properties in the `ActorsFilms`
record:

```

@JsonPropertyOrder({"actor", "movies"})

record ActorsFilms(String actor, List<String> movies) {}

Copied!

```

This annotation works with both records and regular Java classes.

####  Generic Bean Types

Use the `ParameterizedTypeReference` constructor to specify a more complex
target class structure. For example, to represent a list of actors and their
filmographies:

```

List<ActorsFilms> actorsFilms = ChatClient.create(chatModel).prompt()

        .user("Generate the filmography of 5 movies for Tom Hanks and Bill Murray.")
        .call()
        .entity(new ParameterizedTypeReference<List<ActorsFilms>>() {});
Copied!

```

or using the low-level `ChatModel` API directly:

```

BeanOutputConverter<List<ActorsFilms>> outputConverter = new
BeanOutputConverter<>(

        new ParameterizedTypeReference<List<ActorsFilms>>() { });

String format = this.outputConverter.getFormat();

String template = """

        Generate the filmography of 5 movies for Tom Hanks and Bill Murray.
        {format}
        """;

Prompt prompt = new PromptTemplate(this.template, Map.of("format",
this.format)).create();

Generation generation = chatModel.call(this.prompt).getResult();

List<ActorsFilms> actorsFilms =
this.outputConverter.convert(this.generation.getOutput().getText());

Copied!

```

###  Map Output Converter

The following snippet shows how to use `MapOutputConverter` to convert the model
output to a list of numbers in a map.

```

Map<String, Object> result = ChatClient.create(chatModel).prompt()

        .user(u -> u.text("Provide me a List of {subject}")
                    .param("subject", "an array of numbers from 1 to 9 under they key name 'numbers'"))
        .call()
        .entity(new ParameterizedTypeReference<Map<String, Object>>() {});
Copied!

```

or using the low-level `ChatModel` API directly:

```

MapOutputConverter mapOutputConverter = new MapOutputConverter();

String format = this.mapOutputConverter.getFormat();

String template = """

        Provide me a List of {subject}
        {format}
        """;

Prompt prompt = new PromptTemplate(this.template,

        Map.of("subject", "an array of numbers from 1 to 9 under they key name 'numbers'", "format", this.format)).create();

Generation generation = chatModel.call(this.prompt).getResult();

Map<String, Object> result =
this.mapOutputConverter.convert(this.generation.getOutput().getText());

Copied!

```

###  List Output Converter

The following snippet shows how to use `ListOutputConverter` to convert the
model output into a list of ice cream flavors.

```

List<String> flavors = ChatClient.create(chatModel).prompt()

                .user(u -> u.text("List five {subject}")
                            .param("subject", "ice cream flavors"))
                .call()
                .entity(new ListOutputConverter(new DefaultConversionService()));
Copied!

```

or using the low-level `ChatModel API` directly:

```

ListOutputConverter listOutputConverter = new ListOutputConverter(new
DefaultConversionService());

String format = this.listOutputConverter.getFormat();

String template = """

        List five {subject}
        {format}
        """;

Prompt prompt = new PromptTemplate(this.template,

        Map.of("subject", "ice cream flavors", "format", this.format)).create();

Generation generation = this.chatModel.call(this.prompt).getResult();

List<String> list =
this.listOutputConverter.convert(this.generation.getOutput().getText());

Copied!

```

##  Supported AI Models

The following AI Models have been tested to support List, Map and Bean
structured outputs.

Model | Integration Tests / Samples  
---|---  
OpenAI | OpenAiChatModelIT  
Anthropic Claude 3 | AnthropicChatModelIT.java  
Azure OpenAI | AzureOpenAiChatModelIT.java  
Mistral AI | MistralAiChatModelIT.java  
Ollama | OllamaChatModelIT.java  
Vertex AI Gemini | VertexAiGeminiChatModelIT.java  
##  Built-in JSON mode

Some AI Models provide dedicated configuration options to generate structured
(usually JSON) output.

  * OpenAI Structured Outputs can ensure your model generates responses conforming strictly to your provided JSON Schema. You can choose between the `JSON_OBJECT` that guarantees the message the model generates is valid JSON or `JSON_SCHEMA` with a supplied schema that guarantees the model will generate a response that matches your supplied schema (`spring.ai.openai.chat.options.responseFormat` option).
  * Azure OpenAI - provides a `spring.ai.azure.openai.chat.options.responseFormat` options specifying the format that the model must output. Setting to `{ "type": "json_object" }` enables JSON mode, which guarantees the message the model generates is valid JSON.
  * Ollama - provides a `spring.ai.ollama.chat.options.format` option to specify the format to return a response in. Currently, the only accepted value is `json`.
  * Mistral AI - provides a `spring.ai.mistralai.chat.options.responseFormat` option to specify the format to return a response in. Setting it to `{ "type": "json_object" }` enables JSON mode, which guarantees the message the model generates is valid JSON.

Prompts Multimodality

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

