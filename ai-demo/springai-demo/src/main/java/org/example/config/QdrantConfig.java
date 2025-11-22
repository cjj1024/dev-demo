package org.example.config;


import io.qdrant.client.QdrantClient;
import io.qdrant.client.QdrantGrpcClient;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.embedding.TokenCountBatchingStrategy;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.qdrant.QdrantVectorStore;
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
    public VectorStore vectorStore(QdrantClient qdrantClient, EmbeddingModel embeddingModel) {
        return QdrantVectorStore.builder(qdrantClient, embeddingModel)
                .collectionName("test-store2")
                .initializeSchema(true)
                .batchingStrategy(new TokenCountBatchingStrategy())

                .build();
    }
}
