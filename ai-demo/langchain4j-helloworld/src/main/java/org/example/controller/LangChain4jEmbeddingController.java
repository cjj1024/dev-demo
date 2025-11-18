package org.example.controller;

import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.output.Response;
import dev.langchain4j.store.embedding.EmbeddingSearchRequest;
import dev.langchain4j.store.embedding.EmbeddingSearchResult;
import dev.langchain4j.store.embedding.EmbeddingStore;
import io.qdrant.client.QdrantClient;
import io.qdrant.client.grpc.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LangChain4jEmbeddingController {
    @Autowired
    private EmbeddingModel embeddingModel;
    @Autowired
    private EmbeddingStore<TextSegment> embeddingStore;
    @Autowired
    private QdrantClient qdrantClient;

    @PostMapping("/langchain/embedding/create")
    public void create() {
        Collections.VectorParams vectorParams = Collections.VectorParams.newBuilder()
                .setDistance(Collections.Distance.Cosine)
                .setSize(768)
                .build();
        qdrantClient.createCollectionAsync("test-store", vectorParams);
    }

    @PostMapping("/langchain/embedding/hello")
    public String hello(@RequestParam(name = "question", defaultValue = "你是谁") String question) {
        TextSegment textSegment = TextSegment.from(question);
        System.out.println(textSegment);
        Response<Embedding> embed = embeddingModel.embed(textSegment);
        embeddingStore.add(embed.content(), textSegment);

        return embed.content().toString();
    }

    @GetMapping("/langchain/embedding/query")
    public String query(@RequestParam(name = "question") String question) {
        Embedding queryEmbedding = embeddingModel.embed(question).content();
        EmbeddingSearchRequest embeddingSearchRequest = EmbeddingSearchRequest.builder()
                .queryEmbedding(queryEmbedding)
                .maxResults(1)
                .build();
        EmbeddingSearchResult<TextSegment> result = embeddingStore.search(embeddingSearchRequest);
        return result.matches().getFirst().embedded().toString();
    }
}
