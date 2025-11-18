package org.example.config;

import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.qdrant.QdrantEmbeddingStore;
import io.qdrant.client.QdrantClient;
import io.qdrant.client.QdrantGrpcClient;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class QdrantConfig {
    @Bean
    public QdrantClient qdrantClient() {
        QdrantGrpcClient qdrantGrpcClient = QdrantGrpcClient.newBuilder("127.0.0.1", 6334, false).build();
        return new QdrantClient(qdrantGrpcClient);
    }

    @Bean
    public EmbeddingStore<TextSegment> embeddingStore() {
        return QdrantEmbeddingStore.builder()
                .host("127.0.0.1")
                .port(6334)
                .collectionName("test-store")
                .build();
    }
}
