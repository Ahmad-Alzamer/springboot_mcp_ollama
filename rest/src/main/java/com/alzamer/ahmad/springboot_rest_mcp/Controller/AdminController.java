package com.alzamer.ahmad.springboot_rest_mcp.Controller;

import io.micrometer.core.annotation.Timed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.reader.TextReader;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.filter.Filter;
import org.springframework.ai.vectorstore.filter.FilterExpressionBuilder;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Arrays;

@RestController
@RequestMapping("v1/admin")
@Slf4j
public class AdminController {
    private final VectorStore vectorStore;

    public AdminController(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    @GetMapping("load/rag/springboot/ai")
    @Timed(value = "load/rag/springboot/ai",percentiles = {.9,.95,.99})
    public ResponseEntity<Void> loadAiDocsInfoVectorDB(){

        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        try {
            Resource[] resources = resolver.getResources("classpath:RAG/springboot/ai/*");
            log.info("found {} resources",resources.length);
            Arrays.stream(resources)
                    .peek(r -> log.trace("adding document to vector store: [{}]",r.getFilename()))
                    .forEach(r -> {
                        try {
                            vectorStore.add(new TextReader(r).get());
                            log.trace("added document to vector store: [{}]",r.getFilename());
                        } catch (Exception e) {
                            log.error("failed to load document: [{}]",r.getFilename(),e);
                        }
                    });
            log.info("added documents to vector store");
            return ResponseEntity.accepted().build();
        } catch (IOException e) {
            log.error("failed to load resources",e);
            return ResponseEntity.internalServerError().build();
        }

    }


}
