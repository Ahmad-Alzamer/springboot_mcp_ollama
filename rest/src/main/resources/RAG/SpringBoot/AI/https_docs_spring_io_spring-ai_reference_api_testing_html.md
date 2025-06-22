source URL: https://docs.spring.io/spring-ai/reference/api/testing.html 
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

### Evaluation Testing

  * Relevancy Evaluator
  * Usage in Integration Tests
  * Custom Template
  * FactCheckingEvaluator
  * Usage
  * Example

  * Spring AI
  * Reference
  * Model Evaluation

# Evaluation Testing

### Evaluation Testing

  * Relevancy Evaluator
  * Usage in Integration Tests
  * Custom Template
  * FactCheckingEvaluator
  * Usage
  * Example

Testing AI applications requires evaluating the generated content to ensure the
AI model has not produced a hallucinated response.

One method to evaluate the response is to use the AI model itself for
evaluation. Select the best AI model for the evaluation, which may not be the
same model used to generate the response.

The Spring AI interface for evaluating responses is `Evaluator`, defined as:

```

@FunctionalInterface

public interface Evaluator {

    EvaluationResponse evaluate(EvaluationRequest evaluationRequest);
}

Copied!

```

The input to the evaluation is the `EvaluationRequest` defined as

```

public class EvaluationRequest {

	private final String userText;

	private final List<Content> dataList;

	private final String responseContent;

	public EvaluationRequest(String userText, List<Content> dataList, String responseContent) {
		this.userText = userText;
		this.dataList = dataList;
		this.responseContent = responseContent;
	}

  ...

}

Copied!

```

  * `userText`: The raw input from the user as a `String`
  * `dataList`: Contextual data, such as from Retrieval Augmented Generation, appended to the raw input.
  * `responseContent`: The AI model’s response content as a `String`

##  Relevancy Evaluator

The `RelevancyEvaluator` is an implementation of the `Evaluator` interface,
designed to assess the relevance of AI-generated responses against provided
context. This evaluator helps assess the quality of a RAG flow by determining if
the AI model’s response is relevant to the user’s input with respect to the
retrieved context.

The evaluation is based on the user input, the AI model’s response, and the
context information. It uses a prompt template to ask the AI model if the
response is relevant to the user input and context.

This is the default prompt template used by the `RelevancyEvaluator`:

```

Your task is to evaluate if the response for the query

is in line with the context information provided.

You have two options to answer. Either YES or NO.

Answer YES, if the response for the query

is in line with context information otherwise NO.

Query:

{query}

Response:

{response}

Context:

{context}

Answer:

Copied!

```

|  You can customize the prompt template by providing your own `PromptTemplate`
object via the `.promptTemplate()` builder method. See Custom Template for
details.  
---|---  
##  Usage in Integration Tests

Here is an example of usage of the `RelevancyEvaluator` in an integration test,
validating the result of a RAG flow using the `RetrievalAugmentationAdvisor`:

```

@Test

void evaluateRelevancy() {

    String question = "Where does the adventure of Anacletus and Birba take place?";

    RetrievalAugmentationAdvisor ragAdvisor = RetrievalAugmentationAdvisor.builder()
        .documentRetriever(VectorStoreDocumentRetriever.builder()
            .vectorStore(pgVectorStore)
            .build())
        .build();

    ChatResponse chatResponse = ChatClient.builder(chatModel).build()
        .prompt(question)
        .advisors(ragAdvisor)
        .call()
        .chatResponse();

    EvaluationRequest evaluationRequest = new EvaluationRequest(
        // The original user question
        question,
        // The retrieved context from the RAG flow
        chatResponse.getMetadata().get(RetrievalAugmentationAdvisor.DOCUMENT_CONTEXT),
        // The AI model's response
        chatResponse.getResult().getOutput().getText()
    );

    RelevancyEvaluator evaluator = new RelevancyEvaluator(ChatClient.builder(chatModel));

    EvaluationResponse evaluationResponse = evaluator.evaluate(evaluationRequest);

    assertThat(evaluationResponse.isPass()).isTrue();
}

Copied!

```

You can find several integration tests in the Spring AI project that use the
`RelevancyEvaluator` to test the functionality of the `QuestionAnswerAdvisor`
(see tests) and `RetrievalAugmentationAdvisor` (see tests).

###  Custom Template

The `RelevancyEvaluator` uses a default template to prompt the AI model for
evaluation. You can customize this behavior by providing your own
`PromptTemplate` object via the `.promptTemplate()` builder method.

The custom `PromptTemplate` can use any `TemplateRenderer` implementation (by
default, it uses `StPromptTemplate` based on the StringTemplate engine). The
important requirement is that the template must contain the following
placeholders:

  * a `query` placeholder to receive the user question.
  * a `response` placeholder to receive the AI model’s response.
  * a `context` placeholder to receive the context information.

##  FactCheckingEvaluator

The FactCheckingEvaluator is another implementation of the Evaluator interface,
designed to assess the factual accuracy of AI-generated responses against
provided context. This evaluator helps detect and reduce hallucinations in AI
outputs by verifying if a given statement (claim) is logically supported by the
provided context (document).

The 'claim' and 'document' are presented to the AI model for evaluation. Smaller
and more efficient AI models dedicated to this purpose are available, such as
Bespoke’s Minicheck, which helps reduce the cost of performing these checks
compared to flagship models like GPT-4. Minicheck is also available for use
through Ollama.

###  Usage

The FactCheckingEvaluator constructor takes a ChatClient.Builder as a parameter:

```

public FactCheckingEvaluator(ChatClient.Builder chatClientBuilder) {

  this.chatClientBuilder = chatClientBuilder;

}

Copied!

```

The evaluator uses the following prompt template for fact-checking:

```

Document: {document}

Claim: {claim}

Copied!

```

Where `{document}` is the context information, and `{claim}` is the AI model’s
response to be evaluated.

###  Example

Here’s an example of how to use the FactCheckingEvaluator with an Ollama-based
ChatModel, specifically the Bespoke-Minicheck model:

```

@Test

void testFactChecking() {

  // Set up the Ollama API

  OllamaApi ollamaApi = new OllamaApi("http://localhost:11434");

  ChatModel chatModel = new OllamaChatModel(ollamaApi,

				OllamaOptions.builder().model(BESPOKE_MINICHECK).numPredict(2).temperature(0.0d).build())

  // Create the FactCheckingEvaluator

  var factCheckingEvaluator = new
FactCheckingEvaluator(ChatClient.builder(chatModel));

  // Example context and claim

  String context = "The Earth is the third planet from the Sun and the only
astronomical object known to harbor life.";

  String claim = "The Earth is the fourth planet from the Sun.";

  // Create an EvaluationRequest

  EvaluationRequest evaluationRequest = new EvaluationRequest(context,
Collections.emptyList(), claim);

  // Perform the evaluation

  EvaluationResponse evaluationResponse =
factCheckingEvaluator.evaluate(evaluationRequest);

  assertFalse(evaluationResponse.isPass(), "The claim should not be supported by
the context");

}

Copied!

```

ETL Pipeline Vector Databases

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

