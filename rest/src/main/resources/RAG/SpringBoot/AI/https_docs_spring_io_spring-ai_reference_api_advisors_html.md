source URL: https://docs.spring.io/spring-ai/reference/api/advisors.html 
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

### Advisors API

  * Core Components
  * Advisor Order
  * API Overview
  * Implementing an Advisor
  * Examples
  * Streaming vs Non-Streaming
  * Best Practices
  * Backward Compatibility
  * Breaking API Changes
  * Advisor Interfaces
  * Context Map Handling

  * Spring AI
  * Reference
  * Chat Client API
  * Advisors

# Advisors API

### Advisors API

  * Core Components
  * Advisor Order
  * API Overview
  * Implementing an Advisor
  * Examples
  * Streaming vs Non-Streaming
  * Best Practices
  * Backward Compatibility
  * Breaking API Changes
  * Advisor Interfaces
  * Context Map Handling

The Spring AI Advisors API provides a flexible and powerful way to intercept,
modify, and enhance AI-driven interactions in your Spring applications. By
leveraging the Advisors API, developers can create more sophisticated, reusable,
and maintainable AI components.

The key benefits include encapsulating recurring Generative AI patterns,
transforming data sent to and from Large Language Models (LLMs), and providing
portability across various models and use cases.

You can configure existing advisors using the ChatClient API as shown in the
following example:

```

var chatClient = ChatClient.builder(chatModel)

    .defaultAdvisors(
        MessageChatMemoryAdvisor.builder(chatMemory).build(), // chat-memory advisor
        QuestionAnswerAdvisor.builder((vectorStore).builder() // RAG advisor
    )
    .build();

var conversationId = "678";

String response = this.chatClient.prompt()

    // Set advisor parameters at runtime
    .advisors(advisor -> advisor.param(ChatMemory.CONVERSATION_ID, conversationId))
    .user(userText)
    .call()
	.content();
Copied!

```

It is recommend to register the advisors at build time using builder’s
`defaultAdvisors()` method.

Advisors also participate in the Observability stack, so you can view metrics
and traces related to their execution.

  * Learn about Question Answer Advisor
  * Learn about Chat Memory Advisor

##  Core Components

The API consists of `CallAroundAdvisor` and `CallAroundAdvisorChain` for non-
streaming scenarios, and `StreamAroundAdvisor` and `StreamAroundAdvisorChain`
for streaming scenarios. It also includes `AdvisedRequest` to represent the
unsealed Prompt request, `AdvisedResponse` for the Chat Completion response.
Both hold an `advise-context` to share state across the advisor chain.

The `nextAroundCall()` and the `nextAroundStream()` are the key advisor methods,
typically performing actions such as examining the unsealed Prompt data,
customizing and augmenting the Prompt data, invoking the next entity in the
advisor chain, optionally blocking the request, examining the chat completion
response, and throwing exceptions to indicate processing errors.

In addition the `getOrder()` method determines advisor order in the chain, while
`getName()` provides a unique advisor name.

The Advisor Chain, created by the Spring AI framework, allows sequential
invocation of multiple advisors ordered by their `getOrder()` values. The lower
values are executed first. The last advisor, added automatically, sends the
request to the LLM.

Following flow diagram illustrates the interaction between the advisor chain and
the Chat Model:

  1. The Spring AI framework creates an `AdvisedRequest` from user’s `Prompt` along with an empty `AdvisorContext` object.
  2. Each advisor in the chain processes the request, potentially modifying it. Alternatively, it can choose to block the request by not making the call to invoke the next entity. In the latter case, the advisor is responsible for filling out the response.
  3. The final advisor, provided by the framework, sends the request to the `Chat Model`.
  4. The Chat Model’s response is then passed back through the advisor chain and converted into `AdvisedResponse`. Later includes the shared `AdvisorContext` instance.
  5. Each advisor can process or modify the response.
  6. The final `AdvisedResponse` is returned to the client by extracting the `ChatCompletion`.

###  Advisor Order

The execution order of advisors in the chain is determined by the `getOrder()`
method. Key points to understand:

  * Advisors with lower order values are executed first.
  * The advisor chain operates as a stack:
    * The first advisor in the chain is the first to process the request.
    * It is also the last to process the response.
  * To control execution order:
    * Set the order close to `Ordered.HIGHEST_PRECEDENCE` to ensure an advisor is executed first in the chain (first for request processing, last for response processing).
    * Set the order close to `Ordered.LOWEST_PRECEDENCE` to ensure an advisor is executed last in the chain (last for request processing, first for response processing).
  * Higher values are interpreted as lower priority.
  * If multiple advisors have the same order value, their execution order is not guaranteed.

|  The seeming contradiction between order and execution sequence is due to the
stack-like nature of the advisor chain:

  * An advisor with the highest precedence (lowest order value) is added to the top of the stack.
  * It will be the first to process the request as the stack unwinds.
  * It will be the last to process the response as the stack rewinds.

  
---|---  
As a reminder, here are the semantics of the Spring `Ordered` interface:

```

public interface Ordered {

    /**
     * Constant for the highest precedence value.
     * @see java.lang.Integer#MIN_VALUE
     */
    int HIGHEST_PRECEDENCE = Integer.MIN_VALUE;

    /**
     * Constant for the lowest precedence value.
     * @see java.lang.Integer#MAX_VALUE
     */
    int LOWEST_PRECEDENCE = Integer.MAX_VALUE;

    /**
     * Get the order value of this object.
     * <p>Higher values are interpreted as lower priority. As a consequence,
     * the object with the lowest value has the highest priority (somewhat
     * analogous to Servlet {@code load-on-startup} values).
     * <p>Same order values will result in arbitrary sort positions for the
     * affected objects.
     * @return the order value
     * @see #HIGHEST_PRECEDENCE
     * @see #LOWEST_PRECEDENCE
     */
    int getOrder();
}

Copied!

```

|  For use cases that need to be first in the chain on both the input and output
sides:

  1. Use separate advisors for each side.
  2. Configure them with different order values.
  3. Use the advisor context to share state between them.

  
---|---  
##  API Overview

The main Advisor interfaces are located in the package
`org.springframework.ai.chat.client.advisor.api`. Here are the key interfaces
you’ll encounter when creating your own advisor:

```

public interface Advisor extends Ordered {

	String getName();

}

Copied!

```

The two sub-interfaces for synchronous and reactive Advisors are

```

public interface CallAroundAdvisor extends Advisor {

	/**
	 * Around advice that wraps the ChatModel#call(Prompt) method.
	 * @param advisedRequest the advised request
	 * @param chain the advisor chain
	 * @return the response
	 */
	AdvisedResponse aroundCall(AdvisedRequest advisedRequest, CallAroundAdvisorChain chain);

}

Copied!

```

and

```

public interface StreamAroundAdvisor extends Advisor {

	/**
	 * Around advice that wraps the invocation of the advised request.
	 * @param advisedRequest the advised request
	 * @param chain the chain of advisors to execute
	 * @return the result of the advised request
	 */
	Flux<AdvisedResponse> aroundStream(AdvisedRequest advisedRequest, StreamAroundAdvisorChain chain);

}

Copied!

```

To continue the chain of Advice, use `CallAroundAdvisorChain` and
`StreamAroundAdvisorChain` in your Advice implementation:

The interfaces are

```

public interface CallAroundAdvisorChain {

	AdvisedResponse nextAroundCall(AdvisedRequest advisedRequest);

}

Copied!

```

and

```

public interface StreamAroundAdvisorChain {

	Flux<AdvisedResponse> nextAroundStream(AdvisedRequest advisedRequest);

}

Copied!

```

##  Implementing an Advisor

To create an advisor, implement either `CallAroundAdvisor` or
`StreamAroundAdvisor` (or both). The key method to implement is
`nextAroundCall()` for non-streaming or `nextAroundStream()` for streaming
advisors.

###  Examples

We will provide few hands-on examples to illustrate how to implement advisors
for observing and augmenting use-cases.

####  Logging Advisor

We can implement a simple logging advisor that logs the `AdvisedRequest` before
and the `AdvisedResponse` after the call to the next advisor in the chain. Note
that the advisor only observes the request and response and does not modify
them. This implementation support both non-streaming and streaming scenarios.

```

public class SimpleLoggerAdvisor implements CallAroundAdvisor,
StreamAroundAdvisor {

	private static final Logger logger = LoggerFactory.getLogger(SimpleLoggerAdvisor.class);

	@Override
	public String getName() { **(1)**
		return this.getClass().getSimpleName();
	}

	@Override
	public int getOrder() { **(2)**
		return 0;
	}

	@Override
	public AdvisedResponse aroundCall(AdvisedRequest advisedRequest, CallAroundAdvisorChain chain) {

		logger.debug("BEFORE: {}", advisedRequest);

		AdvisedResponse advisedResponse = chain.nextAroundCall(advisedRequest);

		logger.debug("AFTER: {}", advisedResponse);

		return advisedResponse;
	}

	@Override
	public Flux<AdvisedResponse> aroundStream(AdvisedRequest advisedRequest, StreamAroundAdvisorChain chain) {

		logger.debug("BEFORE: {}", advisedRequest);

		Flux<AdvisedResponse> advisedResponses = chain.nextAroundStream(advisedRequest);

        return new MessageAggregator().aggregateAdvisedResponse(advisedResponses,
                    advisedResponse -> logger.debug("AFTER: {}", advisedResponse)); **(3)**
	}
}

Copied!

```

**1** | Provides a unique name for the advisor.  
---|---  
**2** | You can control the order of execution by setting the order value. Lower values execute first.  
**3** | The `MessageAggregator` is a utility class that aggregates the Flux responses into a single AdvisedResponse. This can be useful for logging or other processing that observe the entire response rather than individual items in the stream. Note that you can not alter the response in the `MessageAggregator` as it is a read-only operation.  
####  Re-Reading (Re2) Advisor

The "Re-Reading Improves Reasoning in Large Language Models" article introduces
a technique called Re-Reading (Re2) that improves the reasoning capabilities of
Large Language Models. The Re2 technique requires augmenting the input prompt
like this:

```

{Input_Query}

Read the question again: {Input_Query}

```

Implementing an advisor that applies the Re2 technique to the user’s input query
can be done like this:

```

public class ReReadingAdvisor implements CallAroundAdvisor, StreamAroundAdvisor
{

	private AdvisedRequest before(AdvisedRequest advisedRequest) { **(1)**

		Map<String, Object> advisedUserParams = new HashMap<>(advisedRequest.userParams());
		advisedUserParams.put("re2_input_query", advisedRequest.userText());

		return AdvisedRequest.from(advisedRequest)
			.userText("""
			    {re2_input_query}
			    Read the question again: {re2_input_query}
			    """)
			.userParams(advisedUserParams)
			.build();
	}

	@Override
	public AdvisedResponse aroundCall(AdvisedRequest advisedRequest, CallAroundAdvisorChain chain) { **(2)**
		return chain.nextAroundCall(this.before(advisedRequest));
	}

	@Override
	public Flux<AdvisedResponse> aroundStream(AdvisedRequest advisedRequest, StreamAroundAdvisorChain chain) { **(3)**
		return chain.nextAroundStream(this.before(advisedRequest));
	}

	@Override
	public int getOrder() { **(4)**
		return 0;
	}

    @Override
    public String getName() { **(5)**
		return this.getClass().getSimpleName();
	}
}

Copied!

```

**1** | The `before` method augments the user’s input query applying the Re-Reading technique.  
---|---  
**2** | The `aroundCall` method intercepts the non-streaming request and applies the Re-Reading technique.  
**3** | The `aroundStream` method intercepts the streaming request and applies the Re-Reading technique.  
**4** | You can control the order of execution by setting the order value. Lower values execute first.  
**5** | Provides a unique name for the advisor.  
####  Spring AI Built-in Advisors

Spring AI framework provides several built-in advisors to enhance your AI
interactions. Here’s an overview of the available advisors:

#####  Chat Memory Advisors

These advisors manage conversation history in a chat memory store:

  * `MessageChatMemoryAdvisor`
Retrieves memory and adds it as a collection of messages to the prompt. This
approach maintains the structure of the conversation history. Note, not all AI
Models support this approach.

  * `PromptChatMemoryAdvisor`
Retrieves memory and incorporates it into the prompt’s system text.

  * `VectorStoreChatMemoryAdvisor`
Retrieves memory from a VectorStore and adds it into the prompt’s system text.
This advisor is useful for efficiently searching and retrieving relevant
information from large datasets.

#####  Question Answering Advisor

  * `QuestionAnswerAdvisor`
This advisor uses a vector store to provide question-answering capabilities,
implementing the RAG (Retrieval-Augmented Generation) pattern.

#####  Content Safety Advisor

  * `SafeGuardAdvisor`
A simple advisor designed to prevent the model from generating harmful or
inappropriate content.

###  Streaming vs Non-Streaming

  * Non-streaming advisors work with complete requests and responses.
  * Streaming advisors handle requests and responses as continuous streams, using reactive programming concepts (e.g., Flux for responses).

```

@Override

public Flux<AdvisedResponse> aroundStream(AdvisedRequest advisedRequest,
StreamAroundAdvisorChain chain) {

    return  Mono.just(advisedRequest)
            .publishOn(Schedulers.boundedElastic())
            .map(request -> {
                // This can be executed by blocking and non-blocking Threads.
                // Advisor before next section
            })
            .flatMapMany(request -> chain.nextAroundStream(request))
            .map(response -> {
                // Advisor after next section
            });
}

Copied!

```

###  Best Practices

  1. Keep advisors focused on specific tasks for better modularity.
  2. Use the `adviseContext` to share state between advisors when necessary.
  3. Implement both streaming and non-streaming versions of your advisor for maximum flexibility.
  4. Carefully consider the order of advisors in your chain to ensure proper data flow.

##  Backward Compatibility

|  The `AdvisedRequest` class is moved to a new package.  
---|---  
##  Breaking API Changes

The Spring AI Advisor Chain underwent significant changes from version 1.0 M2 to
1.0 M3. Here are the key modifications:

###  Advisor Interfaces

  * In 1.0 M2, there were separate `RequestAdvisor` and `ResponseAdvisor` interfaces.
    * `RequestAdvisor` was invoked before the `ChatModel.call` and `ChatModel.stream` methods.
    * `ResponseAdvisor` was called after these methods.
  * In 1.0 M3, these interfaces have been replaced with:
    * `CallAroundAdvisor`
    * `StreamAroundAdvisor`
  * The `StreamResponseMode`, previously part of `ResponseAdvisor`, has been removed.

###  Context Map Handling

  * In 1.0 M2:
    * The context map was a separate method argument.
    * The map was mutable and passed along the chain.
  * In 1.0 M3:
    * The context map is now part of the `AdvisedRequest` and `AdvisedResponse` records.
    * The map is immutable.
    * To update the context, use the `updateContext` method, which creates a new unmodifiable map with the updated contents.

Example of updating the context in 1.0 M3:

```

@Override

public AdvisedResponse aroundCall(AdvisedRequest advisedRequest,
CallAroundAdvisorChain chain) {

    this.advisedRequest = advisedRequest.updateContext(context -> {
        context.put("aroundCallBefore" + getName(), "AROUND_CALL_BEFORE " + getName());  // Add multiple key-value pairs
        context.put("lastBefore", getName());  // Add a single key-value pair
        return context;
    });

    // Method implementation continues...
}

Copied!

```

Chat Client API Prompts

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

