# Intro
SpringBoot application that:
1. connects to Ollama running in Docker
2. configured to "llama3.1:8b" LLM model
3. uses Docker MCP toolkit gateway Client to connect t MCP servers running in Docker desktop
4. uses MCP client config to connect to start and then connect to playwright MCP server
5. two rest endpoints :
   1. v1/chat : standard chat client with no persona set
   2. v1/chat/playwrightExpert : chat client with persona set to playwright guru
   
   both will accept "prompt" and "conversationId" request parameters.
6. InMemory storage to keep latest 20 messages per conversation.



# How to connect to Cassandra running in Docker from CLI using another Docker image:
```bash
docker run -it --network docker_compose_ollama-docker --rm cassandra cqlsh cassandra
```
or simply connect to it using the DB pane within Intellij

# Todo
1. look into using CassandraChatMemoryMessage to keep message in Cassandra DB for 1 day only but you can keep as much as you want.



# Note
added option to use openAI as the AI's LLM model
this is done by introducing two profiles:
1. ollama
2. openai

if you want to use openai, then you need to provide the openAI key in the env var ``openai_api_key``
