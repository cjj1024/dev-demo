package org.example.controller;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.parser.apache.tika.ApacheTikaDocumentParser;
import dev.langchain4j.data.document.splitter.DocumentBySentenceSplitter;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import org.example.service.ChatAssistant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

@RestController
public class LangChain4jRagController {
    @Autowired
    EmbeddingStore<TextSegment> embeddingStore;
    @Autowired
    EmbeddingModel embeddingModel;
    @Autowired
    ChatAssistant chatAssistant;

    @PostMapping("/langchain/rag/add")
    public void addText(@RequestParam(name = "path") String path) throws FileNotFoundException {
        FileInputStream fileInputStream = new FileInputStream(path);
        Document document = new ApacheTikaDocumentParser().parse(fileInputStream);
        EmbeddingStoreIngestor embeddingStoreIngestor = EmbeddingStoreIngestor.builder()
                .embeddingStore(embeddingStore)
                .embeddingModel(embeddingModel)
                .documentSplitter(new DocumentBySentenceSplitter(1024, 128))
                .build();
        embeddingStoreIngestor.ingest(document);
    }

    @GetMapping("/langchain/rag/chat")
    public String chat(@RequestParam(name = "id") Long id,
                     @RequestParam(name = "question", defaultValue = "你是谁") String question) {
        return chatAssistant.chat(id, question);
    }
}
