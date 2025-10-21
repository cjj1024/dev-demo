package org.example;

import io.etcd.jetcd.ByteSequence;
import io.etcd.jetcd.Client;
import io.etcd.jetcd.KV;
import io.etcd.jetcd.kv.GetResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;

@Component
public class EtcdConfigLoader {
    @Value("${etcd.endpoints[0]}")
    private String endpoint;

    @Value("${etcd.key-prefix}")
    private String keyPrefix;

    private Client client;

    @PostConstruct
    public void init() {
        client = Client.builder().endpoints(endpoint).build();
        System.out.println("Connected to etcd: " + endpoint);
    }

    public String getConfig(String key) throws Exception {
        KV kvClient = client.getKVClient();
        ByteSequence bsKey = ByteSequence.from((keyPrefix + key).getBytes(StandardCharsets.UTF_8));
        GetResponse response = kvClient.get(bsKey).get();
        if (response.getKvs().isEmpty()) {
            return null;
        }
        return response.getKvs().get(0).getValue().toString(StandardCharsets.UTF_8);
    }
}
