source URL: https://docs.spring.io/spring-ai/reference/api/chat/dmr-chat.html 
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

### Docker Model Runner Chat

  * Prerequisite
  * Auto-configuration
  * Chat Properties
  * Runtime Options
  * Function Calling
  * Tool Example
  * Sample Controller

  * Spring AI
  * Reference
  * Models
  * Chat Models
  * Docker Model Runner

# Docker Model Runner Chat

### Docker Model Runner Chat

  * Prerequisite
  * Auto-configuration
  * Chat Properties
  * Runtime Options
  * Function Calling
  * Tool Example
  * Sample Controller

Docker Model Runner is an AI Inference Engine offering a wide range of models
from various providers.

Spring AI integrates with the Docker Model Runner by reusing the existing OpenAI
backed `ChatClient`. To do this, set the base URL to `localhost:12434/engines`
and select one of the provided LLM models.

Check the DockerModelRunnerWithOpenAiChatModelIT.java tests for examples of how
to use the Docker Model Runner with Spring AI.

##  Prerequisite

  * Download Docker Desktop for Mac 4.40.0.

Choose one of the following options to enable the Model Runner:

Option 1:

  * Enable Model Runner `docker desktop enable model-runner --tcp 12434`.
  * Set the base-url to `localhost:12434/engines`

Option 2:

  * Enable Model Runner `docker desktop enable model-runner`.
  * Use Testcontainers and set the base-url as follows:

```

@Container

private static final SocatContainer socat = new SocatContainer().withTarget(80,
"model-runner.docker.internal");

@Bean

public OpenAiApi chatCompletionApi() {

	var baseUrl = "http://%s:%d/engines".formatted(socat.getHost(), socat.getMappedPort(80));
	return OpenAiApi.builder().baseUrl(baseUrl).apiKey("test").build();
}

Copied!

```

You can learn more about the Docker Model Runner by reading the Run LLMs Locally
with Docker blog post.

##  Auto-configuration

|  The artifact IDs for Spring AI starter modules have been renamed since
version 1.0.0.M7. Dependency names should now follow updated naming patterns for
models, vector stores, and MCP starters. Please refer to the upgrade notes for
more information.  
---|---  
Spring AI provides Spring Boot auto-configuration for the OpenAI Chat Client. To
enable it, add the following dependency to your project’s Maven `pom.xml` file:

```

<dependency>

    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-starter-model-openai</artifactId>
</dependency>

Copied!

```

or add the following to your Gradle `build.gradle` build file.

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
spring.ai.openai.base-url | The URL to connect to. Must be set to `hub.docker.com/u/ai` | -  
spring.ai.openai.api-key | Any string | -  
####  Configuration Properties

|  Enabling and disabling chat auto-configurations is now done via top level
properties with the prefix `spring.ai.model.chat`. To enable,
`spring.ai.model.chat=openai` (It is enabled by default) To disable,
`spring.ai.model.chat=none` (or any value which doesn’t match openai) This
change allows for the configuration of multiple models in your application.  
---|---  
The prefix `spring.ai.openai.chat` is the property prefix that lets you
configure the chat model implementation for OpenAI.

Property | Description | Default  
---|---|---  
spring.ai.openai.chat.enabled (Removed and no longer valid) | Enable OpenAI chat model. | true  
spring.ai.model.chat | Enable OpenAI chat model. | openai  
spring.ai.openai.chat.base-url | Optional overrides the `spring.ai.openai.base-url` to provide a chat specific url. Must be set to `localhost:12434/engines` | -  
spring.ai.openai.chat.api-key | Optional overrides the spring.ai.openai.api-key to provide chat specific api-key | -  
spring.ai.openai.chat.options.model | The LLM model to use | -  
spring.ai.openai.chat.options.temperature | The sampling temperature that controls the apparent creativity of generated completions. Higher values will make output more random while lower values will make results more focused and deterministic. It is not recommended to modify temperature and top_p for the same completions request as the interaction of these two settings is difficult to predict. | 0.8  
spring.ai.openai.chat.options.frequencyPenalty | Number between -2.0 and 2.0. Positive values penalize new tokens based on their existing frequency in the text so far, decreasing the model’s likelihood to repeat the same line verbatim. | 0.0f  
spring.ai.openai.chat.options.maxTokens | The maximum number of tokens to generate in the chat completion. The total length of input tokens and generated tokens is limited by the model’s context length. | -  
spring.ai.openai.chat.options.n | How many chat completion choices to generate for each input message. Note that you will be charged based on the number of generated tokens across all of the choices. Keep n as 1 to minimize costs. | 1  
spring.ai.openai.chat.options.presencePenalty | Number between -2.0 and 2.0. Positive values penalize new tokens based on whether they appear in the text so far, increasing the model’s likelihood to talk about new topics. | -  
spring.ai.openai.chat.options.responseFormat | An object specifying the format that the model must output. Setting to `{ "type": "json_object" }` enables JSON mode, which guarantees the message the model generates is valid JSON. | -  
spring.ai.openai.chat.options.seed | This feature is in Beta. If specified, our system will make a best effort to sample deterministically, such that repeated requests with the same seed and parameters should return the same result. | -  
spring.ai.openai.chat.options.stop | Up to 4 sequences where the API will stop generating further tokens. | -  
spring.ai.openai.chat.options.topP | An alternative to sampling with temperature, called nucleus sampling, where the model considers the results of the tokens with top_p probability mass. So 0.1 means only the tokens comprising the top 10% probability mass are considered. We generally recommend altering this or temperature but not both. | -  
spring.ai.openai.chat.options.tools | A list of tools the model may call. Currently, only functions are supported as a tool. Use this to provide a list of functions the model may generate JSON inputs for. | -  
spring.ai.openai.chat.options.toolChoice | Controls which (if any) function is called by the model. none means the model will not call a function and instead generates a message. auto means the model can pick between generating a message or calling a function. Specifying a particular function via {"type: "function", "function": {"name": "my_function"}} forces the model to call that function. none is the default when no functions are present. auto is the default if functions are present. | -  
spring.ai.openai.chat.options.user | A unique identifier representing your end-user, which can help OpenAI to monitor and detect abuse. | -  
spring.ai.openai.chat.options.functions | List of functions, identified by their names, to enable for function calling in a single prompt requests. Functions with those names must exist in the functionCallbacks registry. | -  
spring.ai.openai.chat.options.stream-usage | (For streaming only) Set to add an additional chunk with token usage statistics for the entire request. The `choices` field for this chunk is an empty array and all other chunks will also include a usage field, but with a null value. | false  
spring.ai.openai.chat.options.proxy-tool-calls | If true, the Spring AI will not handle the function calls internally, but will proxy them to the client. Then is the client’s responsibility to handle the function calls, dispatch them to the appropriate function, and return the results. If false (the default), the Spring AI will handle the function calls internally. Applicable only for chat models with function calling support | false  
|  All properties prefixed with `spring.ai.openai.chat.options` can be
overridden at runtime by adding a request specific Runtime Options to the
`Prompt` call.  
---|---  
##  Runtime Options

The OpenAiChatOptions.java provides model configurations, such as the model to
use, the temperature, the frequency penalty, etc.

On start-up, the default options can be configured with the
`OpenAiChatModel(api, options)` constructor or the
`spring.ai.openai.chat.options.*` properties.

At run-time you can override the default options by adding new, request
specific, options to the `Prompt` call. For example, to override the default
model and temperature for a specific request:

```

ChatResponse response = chatModel.call(

    new Prompt(
        "Generate the names of 5 famous pirates.",
        OpenAiChatOptions.builder()
            .model("ai/gemma3:4B-F16")
        .build()
    ));
Copied!

```

|  In addition to the model specific OpenAiChatOptions you can use a portable
ChatOptions instance, created with the ChatOptions#builder().  
---|---  
##  Function Calling

Docker Model Runner supports Tool/Function calling when selecting a model that
supports it.

You can register custom Java functions with your ChatModel and have the provided
model intelligently choose to output a JSON object containing arguments to call
one or many of the registered functions. This is a powerful technique for
connecting the LLM capabilities with external tools and APIs.

###  Tool Example

Here’s a simple example of how to use Docker Model Runner function calling with
Spring AI:

```

spring.ai.openai.api-key=test

spring.ai.openai.base-url=http://localhost:12434/engines

spring.ai.openai.chat.options.model=ai/gemma3:4B-F16

Copied!

```

```

@SpringBootApplication

public class DockerModelRunnerLlmApplication {

    public static void main(String[] args) {
        SpringApplication.run(DockerModelRunnerLlmApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(ChatClient.Builder chatClientBuilder) {
        return args -> {
            var chatClient = chatClientBuilder.build();

            var response = chatClient.prompt()
                .user("What is the weather in Amsterdam and Paris?")
                .functions("weatherFunction") // reference by bean name.
                .call()
                .content();

            System.out.println(response);
        };
    }

    @Bean
    @Description("Get the weather in location")
    public Function<WeatherRequest, WeatherResponse> weatherFunction() {
        return new MockWeatherService();
    }

    public static class MockWeatherService implements Function<WeatherRequest, WeatherResponse> {

        public record WeatherRequest(String location, String unit) {}
        public record WeatherResponse(double temp, String unit) {}

        @Override
        public WeatherResponse apply(WeatherRequest request) {
            double temperature = request.location().contains("Amsterdam") ? 20 : 25;
            return new WeatherResponse(temperature, request.unit);
        }
    }
}

Copied!

```

In this example, when the model needs weather information, it will automatically
call the `weatherFunction` bean, which can then fetch real-time weather data.
The expected response is: "The weather in Amsterdam is currently 20 degrees
Celsius, and the weather in Paris is currently 25 degrees Celsius."

Read more about OpenAI Function Calling.

##  Sample Controller

Create a new Spring Boot project and add the `spring-ai-starter-model-openai` to
your pom (or gradle) dependencies.

Add a `application.properties` file, under the `src/main/resources` directory,
to enable and configure the OpenAi chat model:

```

spring.ai.openai.api-key=test

spring.ai.openai.base-url=http://localhost:12434/engines

spring.ai.openai.chat.options.model=ai/gemma3:4B-F16

# Docker Model Runner doesn't support embeddings, so we need to disable them.

spring.ai.openai.embedding.enabled=false

Copied!

```

Here is an example of a simple `@Controller` class that uses the chat model for
text generation.

```

@RestController

public class ChatController {

    private final OpenAiChatModel chatModel;

    @Autowired
    public ChatController(OpenAiChatModel chatModel) {
        this.chatModel = chatModel;
    }

    @GetMapping("/ai/generate")
    public Map generate(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
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

DeepSeek Google VertexAI

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

