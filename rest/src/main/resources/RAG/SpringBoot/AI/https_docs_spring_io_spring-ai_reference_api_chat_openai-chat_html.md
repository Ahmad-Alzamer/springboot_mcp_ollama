source URL: https://docs.spring.io/spring-ai/reference/api/chat/openai-chat.html 
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

### OpenAI Chat

  * Prerequisites
  * Add Repositories and BOM
  * Auto-configuration
  * Chat Properties
  * Runtime Options
  * Function Calling
  * Multimodal
  * Vision
  * Audio
  * Output Audio
  * Structured Outputs
  * Configuration
  * Sample Controller
  * Manual Configuration
  * Low-level OpenAiApi Client
  * Low-level API Examples
  * API Key Management
  * Default Configuration
  * Custom API Key Configuration

  * Spring AI
  * Reference
  * Models
  * Chat Models
  * OpenAI

# OpenAI Chat

### OpenAI Chat

  * Prerequisites
  * Add Repositories and BOM
  * Auto-configuration
  * Chat Properties
  * Runtime Options
  * Function Calling
  * Multimodal
  * Vision
  * Audio
  * Output Audio
  * Structured Outputs
  * Configuration
  * Sample Controller
  * Manual Configuration
  * Low-level OpenAiApi Client
  * Low-level API Examples
  * API Key Management
  * Default Configuration
  * Custom API Key Configuration

Spring AI supports the various AI language models from OpenAI, the company
behind ChatGPT, which has been instrumental in sparking interest in AI-driven
text generation thanks to its creation of industry-leading text generation
models and embeddings.

##  Prerequisites

You will need to create an API with OpenAI to access ChatGPT models.

Create an account at OpenAI signup page and generate the token on the API Keys
page.

The Spring AI project defines a configuration property named
`spring.ai.openai.api-key` that you should set to the value of the `API Key`
obtained from openai.com.

You can set this configuration property in your `application.properties` file:

```

spring.ai.openai.api-key=<your-openai-api-key>

Copied!

```

For enhanced security when handling sensitive information like API keys, you can
use Spring Expression Language (SpEL) to reference a custom environment
variable:

```

# In application.yml

spring:

  ai:

    openai:
      api-key: ${OPENAI_API_KEY}
Copied!

```

```

# In your environment or .env file

export OPENAI_API_KEY=<your-openai-api-key>

Copied!

```

You can also set this configuration programmatically in your application code:

```

// Retrieve API key from a secure source or environment variable

String apiKey = System.getenv("OPENAI_API_KEY");

Copied!

```

###  Add Repositories and BOM

Spring AI artifacts are published in Maven Central and Spring Snapshot
repositories. Refer to the Artifact Repositories section to add these
repositories to your build system.

To help with dependency management, Spring AI provides a BOM (bill of materials)
to ensure that a consistent version of Spring AI is used throughout the entire
project. Refer to the Dependency Management section to add the Spring AI BOM to
your build system.

##  Auto-configuration

|  There has been a significant change in the Spring AI auto-configuration,
starter modules' artifact names. Please refer to the upgrade notes for more
information.  
---|---  
Spring AI provides Spring Boot auto-configuration for the OpenAI Chat Client. To
enable it add the following dependency to your project’s Maven `pom.xml` or
Gradle `build.gradle` build files:

  * Maven
  * Gradle

```

<dependency>

    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-starter-model-openai</artifactId>
</dependency>

Copied!

```

```

dependencies {

    implementation 'org.springframework.ai:spring-ai-starter-model-openai'
}

Copied!

```

|  Refer to the Dependency Management section to add the Spring AI BOM to your
build file.  
---|---  
###  Chat Properties

####  Retry Properties

The prefix `spring.ai.retry` is used as the property prefix that lets you
configure the retry mechanism for the OpenAI chat model.

Property | Description | Default  
---|---|---  
spring.ai.retry.max-attempts | Maximum number of retry attempts. | 10  
spring.ai.retry.backoff.initial-interval | Initial sleep duration for the exponential backoff policy. | 2 sec.  
spring.ai.retry.backoff.multiplier | Backoff interval multiplier. | 5  
spring.ai.retry.backoff.max-interval | Maximum backoff duration. | 3 min.  
spring.ai.retry.on-client-errors | If false, throw a NonTransientAiException, and do not attempt retry for `4xx` client error codes | false  
spring.ai.retry.exclude-on-http-codes | List of HTTP status codes that should not trigger a retry (e.g. to throw NonTransientAiException). | empty  
spring.ai.retry.on-http-codes | List of HTTP status codes that should trigger a retry (e.g. to throw TransientAiException). | empty  
####  Connection Properties

The prefix `spring.ai.openai` is used as the property prefix that lets you
connect to OpenAI.

Property | Description | Default  
---|---|---  
spring.ai.openai.base-url | The URL to connect to | api.openai.com  
spring.ai.openai.api-key | The API Key | -  
spring.ai.openai.organization-id | Optionally, you can specify which organization to use for an API request. | -  
spring.ai.openai.project-id | Optionally, you can specify which project to use for an API request. | -  
|  For users that belong to multiple organizations (or are accessing their
projects through their legacy user API key), you can optionally specify which
organization and project is used for an API request. Usage from these API
requests will count as usage for the specified organization and project.  
---|---  
####  Configuration Properties

|  Enabling and disabling of the chat auto-configurations are now configured via
top level properties with the prefix `spring.ai.model.chat`. To enable,
spring.ai.model.chat=openai (It is enabled by default) To disable,
spring.ai.model.chat=none (or any value which doesn’t match openai) This change
is done to allow configuration of multiple models.  
---|---  
The prefix `spring.ai.openai.chat` is the property prefix that lets you
configure the chat model implementation for OpenAI.

Property | Description | Default  
---|---|---  
spring.ai.openai.chat.enabled (Removed and no longer valid) | Enable OpenAI chat model. | true  
spring.ai.model.chat | Enable OpenAI chat model. | openai  
spring.ai.openai.chat.base-url | Optional override for the `spring.ai.openai.base-url` property to provide a chat-specific URL. | -  
spring.ai.openai.chat.completions-path | The path to append to the base URL. | `/v1/chat/completions`  
spring.ai.openai.chat.api-key | Optional override for the `spring.ai.openai.api-key` to provide a chat-specific API Key. | -  
spring.ai.openai.chat.organization-id | Optionally, you can specify which organization to use for an API request. | -  
spring.ai.openai.chat.project-id | Optionally, you can specify which project to use for an API request. | -  
spring.ai.openai.chat.options.model | Name of the OpenAI chat model to use. You can select between models such as: `gpt-4o`, `gpt-4o-mini`, `gpt-4-turbo`, `gpt-3.5-turbo`, and more. See the models page for more information. | `gpt-4o-mini`  
spring.ai.openai.chat.options.temperature | The sampling temperature to use that controls the apparent creativity of generated completions. Higher values will make output more random while lower values will make results more focused and deterministic. It is not recommended to modify `temperature` and `top_p` for the same completions request as the interaction of these two settings is difficult to predict. | 0.8  
spring.ai.openai.chat.options.frequencyPenalty | Number between -2.0 and 2.0. Positive values penalize new tokens based on their existing frequency in the text so far, decreasing the model’s likelihood to repeat the same line verbatim. | 0.0f  
spring.ai.openai.chat.options.logitBias | Modify the likelihood of specified tokens appearing in the completion. | -  
spring.ai.openai.chat.options.maxTokens | (Deprecated in favour of `maxCompletionTokens`) The maximum number of tokens to generate in the chat completion. The total length of input tokens and generated tokens is limited by the model’s context length. | -  
spring.ai.openai.chat.options.maxCompletionTokens | An upper bound for the number of tokens that can be generated for a completion, including visible output tokens and reasoning tokens. | -  
spring.ai.openai.chat.options.n | How many chat completion choices to generate for each input message. Note that you will be charged based on the number of generated tokens across all of the choices. Keep `n` as 1 to minimize costs. | 1  
spring.ai.openai.chat.options.store | Whether to store the output of this chat completion request for use in our model | false  
spring.ai.openai.chat.options.metadata | Developer-defined tags and values used for filtering completions in the chat completion dashboard | empty map  
spring.ai.openai.chat.options.output-modalities | Output types that you would like the model to generate for this request. Most models are capable of generating text, which is the default. The `gpt-4o-audio-preview` model can also be used to generate audio. To request that this model generate both text and audio responses, you can use: `text`, `audio`. Not supported for streaming. | -  
spring.ai.openai.chat.options.output-audio | Audio parameters for the audio generation. Required when audio output is requested with `output-modalities`: `audio`. Requires the `gpt-4o-audio-preview` model and is is not supported for streaming completions. | -  
spring.ai.openai.chat.options.presencePenalty | Number between -2.0 and 2.0. Positive values penalize new tokens based on whether they appear in the text so far, increasing the model’s likelihood to talk about new topics. | -  
spring.ai.openai.chat.options.responseFormat.type | Compatible with `GPT-4o`, `GPT-4o mini`, `GPT-4 Turbo` and all `GPT-3.5 Turbo` models newer than `gpt-3.5-turbo-1106`. The `JSON_OBJECT` type enables JSON mode, which guarantees the message the model generates is valid JSON. The `JSON_SCHEMA` type enables Structured Outputs which guarantees the model will match your supplied JSON schema. The JSON_SCHEMA type requires setting the `responseFormat.schema` property as well. | -  
spring.ai.openai.chat.options.responseFormat.name | Response format schema name. Applicable only for `responseFormat.type=JSON_SCHEMA` | custom_schema  
spring.ai.openai.chat.options.responseFormat.schema | Response format JSON schema. Applicable only for `responseFormat.type=JSON_SCHEMA` | -  
spring.ai.openai.chat.options.responseFormat.strict | Response format JSON schema adherence strictness. Applicable only for `responseFormat.type=JSON_SCHEMA` | -  
spring.ai.openai.chat.options.seed | This feature is in Beta. If specified, our system will make a best effort to sample deterministically, such that repeated requests with the same seed and parameters should return the same result. | -  
spring.ai.openai.chat.options.stop | Up to 4 sequences where the API will stop generating further tokens. | -  
spring.ai.openai.chat.options.topP | An alternative to sampling with temperature, called nucleus sampling, where the model considers the results of the tokens with `top_p` probability mass. So 0.1 means only the tokens comprising the top 10% probability mass are considered. We generally recommend altering this or `temperature` but not both. | -  
spring.ai.openai.chat.options.tools | A list of tools the model may call. Currently, only functions are supported as a tool. Use this to provide a list of functions the model may generate JSON inputs for. | -  
spring.ai.openai.chat.options.toolChoice | Controls which (if any) function is called by the model. `none` means the model will not call a function and instead generates a message. `auto` means the model can pick between generating a message or calling a function. Specifying a particular function via `{"type: "function", "function": {"name": "my_function"}}` forces the model to call that function. `none` is the default when no functions are present. `auto` is the default if functions are present. | -  
spring.ai.openai.chat.options.user | A unique identifier representing your end-user, which can help OpenAI to monitor and detect abuse. | -  
spring.ai.openai.chat.options.functions | List of functions, identified by their names, to enable for function calling in a single prompt requests. Functions with those names must exist in the `functionCallbacks` registry. | -  
spring.ai.openai.chat.options.stream-usage | (For streaming only) Set to add an additional chunk with token usage statistics for the entire request. The `choices` field for this chunk is an empty array and all other chunks will also include a usage field, but with a null value. | false  
spring.ai.openai.chat.options.parallel-tool-calls | Whether to enable parallel function calling during tool use. | true  
spring.ai.openai.chat.options.http-headers | Optional HTTP headers to be added to the chat completion request. To override the `api-key` you need to use an `Authorization` header key, and you have to prefix the key value with the `Bearer` prefix. | -  
spring.ai.openai.chat.options.proxy-tool-calls | If true, the Spring AI will not handle the function calls internally, but will proxy them to the client. Then is the client’s responsibility to handle the function calls, dispatch them to the appropriate function, and return the results. If false (the default), the Spring AI will handle the function calls internally. Applicable only for chat models with function calling support | false  
|  You can override the common `spring.ai.openai.base-url` and
`spring.ai.openai.api-key` for the `ChatModel` and `EmbeddingModel`
implementations. The `spring.ai.openai.chat.base-url` and
`spring.ai.openai.chat.api-key` properties, if set, take precedence over the
common properties. This is useful if you want to use different OpenAI accounts
for different models and different model endpoints.  
---|---  
|  All properties prefixed with `spring.ai.openai.chat.options` can be
overridden at runtime by adding request-specific Runtime Options to the `Prompt`
call.  
---|---  
##  Runtime Options

The OpenAiChatOptions.java class provides model configurations such as the model
to use, the temperature, the frequency penalty, etc.

On start-up, the default options can be configured with the
`OpenAiChatModel(api, options)` constructor or the
`spring.ai.openai.chat.options.*` properties.

At run-time, you can override the default options by adding new, request-
specific options to the `Prompt` call. For example, to override the default
model and temperature for a specific request:

```

ChatResponse response = chatModel.call(

    new Prompt(
        "Generate the names of 5 famous pirates.",
        OpenAiChatOptions.builder()
            .model("gpt-4o")
            .temperature(0.4)
        .build()
    ));
Copied!

```

|  In addition to the model specific OpenAiChatOptions you can use a portable
ChatOptions instance, created with ChatOptionsBuilder#builder().  
---|---  
##  Function Calling

You can register custom Java functions with the `OpenAiChatModel` and have the
OpenAI model intelligently choose to output a JSON object containing arguments
to call one or many of the registered functions. This is a powerful technique to
connect the LLM capabilities with external tools and APIs. Read more about Tool
Calling.

##  Multimodal

Multimodality refers to a model’s ability to simultaneously understand and
process information from various sources, including text, images, audio, and
other data formats. OpenAI supports text, vision, and audio input modalities.

###  Vision

OpenAI models that offer vision multimodal support include `gpt-4`, `gpt-4o`,
and `gpt-4o-mini`. Refer to the Vision guide for more information.

The OpenAI User Message API can incorporate a list of base64-encoded images or
image urls with the message. Spring AI’s Message interface facilitates
multimodal AI models by introducing the Media type. This type encompasses data
and details regarding media attachments in messages, utilizing Spring’s
`org.springframework.util.MimeType` and a `org.springframework.core.io.Resource`
for the raw media data.

Below is a code example excerpted from OpenAiChatModelIT.java, illustrating the
fusion of user text with an image using the `gpt-4o` model.

```

var imageResource = new ClassPathResource("/multimodal.test.png");

var userMessage = new UserMessage("Explain what do you see on this picture?",

        new Media(MimeTypeUtils.IMAGE_PNG, this.imageResource));

ChatResponse response = chatModel.call(new Prompt(this.userMessage,

        OpenAiChatOptions.builder().model(OpenAiApi.ChatModel.GPT_4_O.getValue()).build()));
Copied!

```

|  GPT_4_VISION_PREVIEW will continue to be available only to existing users of
this model starting June 17, 2024. If you are not an existing user, please use
the GPT_4_O or GPT_4_TURBO models. More details here  
---|---  
or the image URL equivalent using the `gpt-4o` model:

```

var userMessage = new UserMessage("Explain what do you see on this picture?",

        new Media(MimeTypeUtils.IMAGE_PNG,
                URI.create("https://docs.spring.io/spring-ai/reference/_images/multimodal.test.png")));

ChatResponse response = chatModel.call(new Prompt(this.userMessage,

        OpenAiChatOptions.builder().model(OpenAiApi.ChatModel.GPT_4_O.getValue()).build()));
Copied!

```

|  You can pass multiple images as well.  
---|---  
The example shows a model taking as an input the `multimodal.test.png` image:

along with the text message "Explain what do you see on this picture?", and
generating a response like this:

```

This is an image of a fruit bowl with a simple design. The bowl is made of metal
with curved wire edges that

create an open structure, allowing the fruit to be visible from all angles.
Inside the bowl, there are two

yellow bananas resting on top of what appears to be a red apple. The bananas are
slightly overripe, as

indicated by the brown spots on their peels. The bowl has a metal ring at the
top, likely to serve as a handle

for carrying. The bowl is placed on a flat surface with a neutral-colored
background that provides a clear

view of the fruit inside.

```

###  Audio

OpenAI models that offer input audio multimodal support include `gpt-4o-audio-
preview`. Refer to the Audio guide for more information.

The OpenAI User Message API can incorporate a list of base64-encoded audio files
with the message. Spring AI’s Message interface facilitates multimodal AI models
by introducing the Media type. This type encompasses data and details regarding
media attachments in messages, utilizing Spring’s
`org.springframework.util.MimeType` and a `org.springframework.core.io.Resource`
for the raw media data. Currently, OpenAI support only the following media
types: `audio/mp3` and `audio/wav`.

Below is a code example excerpted from OpenAiChatModelIT.java, illustrating the
fusion of user text with an audio file using the `gpt-4o-audio-preview` model.

```

var audioResource = new ClassPathResource("speech1.mp3");

var userMessage = new UserMessage("What is this recording about?",

        List.of(new Media(MimeTypeUtils.parseMimeType("audio/mp3"), audioResource)));

ChatResponse response = chatModel.call(new Prompt(List.of(userMessage),

        OpenAiChatOptions.builder().model(OpenAiApi.ChatModel.GPT_4_O_AUDIO_PREVIEW).build()));
Copied!

```

|  You can pass multiple audio files as well.  
---|---  
###  Output Audio

OpenAI models that offer input audio multimodal support include `gpt-4o-audio-
preview`. Refer to the Audio guide for more information.

The OpenAI Assystant Message API can contain a list of base64-encoded audio
files with the message. Spring AI’s Message interface facilitates multimodal AI
models by introducing the Media type. This type encompasses data and details
regarding media attachments in messages, utilizing Spring’s
`org.springframework.util.MimeType` and a `org.springframework.core.io.Resource`
for the raw media data. Currently, OpenAI support only the following audio
types: `audio/mp3` and `audio/wav`.

Below is a code example, illustrating the response of user text along with an
audio byte array, using the `gpt-4o-audio-preview` model:

```

var userMessage = new UserMessage("Tell me joke about Spring Framework");

ChatResponse response = chatModel.call(new Prompt(List.of(userMessage),

        OpenAiChatOptions.builder()
            .model(OpenAiApi.ChatModel.GPT_4_O_AUDIO_PREVIEW)
            .outputModalities(List.of("text", "audio"))
            .outputAudio(new AudioParameters(Voice.ALLOY, AudioResponseFormat.WAV))
            .build()));

String text = response.getResult().getOutput().getContent(); // audio transcript

byte[] waveAudio =
response.getResult().getOutput().getMedia().get(0).getDataAsByteArray(); //
audio data

Copied!

```

You have to specify an `audio` modality in the `OpenAiChatOptions` to generate
audio output. The `AudioParameters` class provides the voice and audio format
for the audio output.

##  Structured Outputs

OpenAI provides custom Structured Outputs APIs that ensure your model generates
responses conforming strictly to your provided `JSON Schema`. In addition to the
existing Spring AI model-agnostic Structured Output Converter, these APIs offer
enhanced control and precision.

|  Currently, OpenAI supports a subset of the JSON Schema language format.  
---|---  
###  Configuration

Spring AI allows you to configure your response format either programmatically
using the `OpenAiChatOptions` builder or through application properties.

####  Using the Chat Options Builder

You can set the response format programmatically with the `OpenAiChatOptions`
builder as shown below:

```

String jsonSchema = """

        {
            "type": "object",
            "properties": {
                "steps": {
                    "type": "array",
                    "items": {
                        "type": "object",
                        "properties": {
                            "explanation": { "type": "string" },
                            "output": { "type": "string" }
                        },
                        "required": ["explanation", "output"],
                        "additionalProperties": false
                    }
                },
                "final_answer": { "type": "string" }
            },
            "required": ["steps", "final_answer"],
            "additionalProperties": false
        }
        """;

Prompt prompt = new Prompt("how can I solve 8x + 7 = -23",

        OpenAiChatOptions.builder()
            .model(ChatModel.GPT_4_O_MINI)
            .responseFormat(new ResponseFormat(ResponseFormat.Type.JSON_SCHEMA, this.jsonSchema))
            .build());

ChatResponse response = this.openAiChatModel.call(this.prompt);

Copied!

```

|  Adhere to the OpenAI subset of the JSON Schema language format.  
---|---  
####  Integrating with BeanOutputConverter Utilities

You can leverage existing BeanOutputConverter utilities to automatically
generate the JSON Schema from your domain objects and later convert the
structured response into domain-specific instances:

  * Java
  * Kotlin

```

record MathReasoning(

    @JsonProperty(required = true, value = "steps") Steps steps,
    @JsonProperty(required = true, value = "final_answer") String finalAnswer) {

    record Steps(
        @JsonProperty(required = true, value = "items") Items[] items) {

        record Items(
            @JsonProperty(required = true, value = "explanation") String explanation,
            @JsonProperty(required = true, value = "output") String output) {
        }
    }
}

var outputConverter = new BeanOutputConverter<>(MathReasoning.class);

var jsonSchema = this.outputConverter.getJsonSchema();

Prompt prompt = new Prompt("how can I solve 8x + 7 = -23",

        OpenAiChatOptions.builder()
            .model(ChatModel.GPT_4_O_MINI)
            .responseFormat(new ResponseFormat(ResponseFormat.Type.JSON_SCHEMA, this.jsonSchema))
            .build());

ChatResponse response = this.openAiChatModel.call(this.prompt);

String content = this.response.getResult().getOutput().getContent();

MathReasoning mathReasoning = this.outputConverter.convert(this.content);

Copied!

```

```

data class MathReasoning(

	val steps: Steps,
	@get:JsonProperty(value = "final_answer") val finalAnswer: String) {

	data class Steps(val items: Array<Items>) {

		data class Items(
			val explanation: String,
			val output: String)
	}
}

val outputConverter = BeanOutputConverter(MathReasoning::class.java)

val jsonSchema = outputConverter.jsonSchema;

val prompt = Prompt("how can I solve 8x + 7 = -23",

	OpenAiChatOptions.builder()
		.model(ChatModel.GPT_4_O_MINI)
		.responseFormat(ResponseFormat(ResponseFormat.Type.JSON_SCHEMA, jsonSchema))
		.build())

val response = openAiChatModel.call(prompt)

val content = response.getResult().getOutput().getContent()

val mathReasoning = outputConverter.convert(content)

Copied!

```

|  Although this is optional for JSON Schema, OpenAI mandates required fields
for the structured response to function correctly. Kotlin reflection is used to
infer which property are required or not based on the nullability of types and
default values of parameters, so for most use case `@get:JsonProperty(required =
true)` is not needed. `@get:JsonProperty(value = "custom_name")` can be useful
to customize the property name. Make sure to generate the annotation on the
related getters with this `@get:` syntax, see related documentation.  
---|---  
####  Configuring via Application Properties

Alternatively, when using the OpenAI auto-configuration, you can configure the
desired response format through the following application properties:

```

spring.ai.openai.api-key=YOUR_API_KEY

spring.ai.openai.chat.options.model=gpt-4o-mini

spring.ai.openai.chat.options.response-format.type=JSON_SCHEMA

spring.ai.openai.chat.options.response-format.name=MySchemaName

spring.ai.openai.chat.options.response-
format.schema={"type":"object","properties":{"steps":{"type":"array","items":{"type":"object","properties":{"explanation":{"type":"string"},"output":{"type":"string"}},"required":["explanation","output"],"additionalProperties":false}},"final_answer":{"type":"string"}},"required":["steps","final_answer"],"additionalProperties":false}

spring.ai.openai.chat.options.response-format.strict=true

Copied!

```

##  Sample Controller

Create a new Spring Boot project and add the `spring-ai-starter-model-openai` to
your pom (or gradle) dependencies.

Add an `application.properties` file under the `src/main/resources` directory to
enable and configure the OpenAi chat model:

```

spring.ai.openai.api-key=YOUR_API_KEY

spring.ai.openai.chat.options.model=gpt-4o

spring.ai.openai.chat.options.temperature=0.7

Copied!

```

|  Replace the `api-key` with your OpenAI credentials.  
---|---  
This will create an `OpenAiChatModel` implementation that you can inject into
your classes. Here is an example of a simple `@RestController` class that uses
the chat model for text generations.

```

@RestController

public class ChatController {

    private final OpenAiChatModel chatModel;

    @Autowired
    public ChatController(OpenAiChatModel chatModel) {
        this.chatModel = chatModel;
    }

    @GetMapping("/ai/generate")
    public Map<String,String> generate(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
        return Map.of("generation", this.chatModel.call(message));
    }

    @GetMapping("/ai/generateStream")
	public Flux<ChatResponse> generateStream(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
        Prompt prompt = new Prompt(new UserMessage(message));
        return this.chatModel.stream(prompt);
    }
}

Copied!

```

##  Manual Configuration

The OpenAiChatModel implements the `ChatModel` and `StreamingChatModel` and uses
the Low-level OpenAiApi Client to connect to the OpenAI service.

Add the `spring-ai-openai` dependency to your project’s Maven `pom.xml` file:

```

<dependency>

    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-openai</artifactId>
</dependency>

Copied!

```

or to your Gradle `build.gradle` build file.

```

dependencies {

    implementation 'org.springframework.ai:spring-ai-openai'
}

Copied!

```

|  Refer to the Dependency Management section to add the Spring AI BOM to your
build file.  
---|---  
Next, create an `OpenAiChatModel` and use it for text generations:

```

var openAiApi = OpenAiApi.builder()

            .apiKey(System.getenv("OPENAI_API_KEY"))
            .build();
var openAiChatOptions = OpenAiChatOptions.builder()

            .model("gpt-3.5-turbo")
            .temperature(0.4)
            .maxTokens(200)
            .build();
var chatModel = new OpenAiChatModel(this.openAiApi, this.openAiChatOptions);

ChatResponse response = this.chatModel.call(

    new Prompt("Generate the names of 5 famous pirates."));

// Or with streaming responses

Flux<ChatResponse> response = this.chatModel.stream(

    new Prompt("Generate the names of 5 famous pirates."));
Copied!

```

The `OpenAiChatOptions` provides the configuration information for the chat
requests. The `OpenAiApi.Builder` and `OpenAiChatOptions.Builder` are fluent
options-builders for API client and chat config respectively.

##  Low-level OpenAiApi Client

The OpenAiApi provides is lightweight Java client for OpenAI Chat API OpenAI
Chat API.

Following class diagram illustrates the `OpenAiApi` chat interfaces and building
blocks:

Here is a simple snippet showing how to use the API programmatically:

```

OpenAiApi openAiApi = OpenAiApi.builder()

            .apiKey(System.getenv("OPENAI_API_KEY"))
            .build();

ChatCompletionMessage chatCompletionMessage =

    new ChatCompletionMessage("Hello world", Role.USER);

// Sync request

ResponseEntity<ChatCompletion> response = this.openAiApi.chatCompletionEntity(

    new ChatCompletionRequest(List.of(this.chatCompletionMessage), "gpt-3.5-turbo", 0.8, false));

// Streaming request

Flux<ChatCompletionChunk> streamResponse = this.openAiApi.chatCompletionStream(

        new ChatCompletionRequest(List.of(this.chatCompletionMessage), "gpt-3.5-turbo", 0.8, true));
Copied!

```

Follow the OpenAiApi.java's JavaDoc for further information.

###  Low-level API Examples

  * The OpenAiApiIT.java tests provide some general examples of how to use the lightweight library.
  * The OpenAiApiToolFunctionCallIT.java tests show how to use the low-level API to call tool functions. Based on the OpenAI Function Calling tutorial.

##  API Key Management

Spring AI provides flexible API key management through the `ApiKey` interface
and its implementations. The default implementation, `SimpleApiKey`, is suitable
for most use cases, but you can also create custom implementations for more
complex scenarios.

###  Default Configuration

By default, Spring Boot auto-configuration will create an API key bean using the
`spring.ai.openai.api-key` property:

```

spring.ai.openai.api-key=your-api-key-here

Copied!

```

###  Custom API Key Configuration

You can create a custom instance of `OpenAiApi` with your own `ApiKey`
implementation using the builder pattern:

```

ApiKey customApiKey = new ApiKey() {

    @Override
    public String getValue() {
        // Custom logic to retrieve API key
        return "your-api-key-here";
    }
};

OpenAiApi openAiApi = OpenAiApi.builder()

    .apiKey(customApiKey)
    .build();

// Create a chat client with the custom OpenAiApi instance

OpenAiChatClient chatClient = new OpenAiChatClient(openAiApi);

Copied!

```

This is useful when you need to:

  * Retrieve the API key from a secure key store
  * Rotate API keys dynamically
  * Implement custom API key selection logic

Cohere QianFan

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

