package com.alzamer.ahmad.springboot_rest_mcp.Config.ai;

import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.template.st.StTemplateRenderer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PromptConfig {
    @Bean
    public PromptTemplate ragPromptTemplate() {
        var stTemplateRenderer = StTemplateRenderer.builder()
                .startDelimiterToken('{') // the default but is shown here to that it can be customized
                .endDelimiterToken('}') // the default but is shown here to that it can be customized
                .build();

        return  PromptTemplate.builder()
                .renderer(stTemplateRenderer)
                .template("""
            {query}
            Context information is below between ---------------------.
            ---------------------
			{question_answer_context}
			---------------------

			Given the context information and no prior knowledge, answer the query.

			Follow these rules:

			1. If the answer is not in the context, just say that you don't know.
			2. Avoid statements like "Based on the context..." or "The provided information...".
            """)
                .build();

    }
}
