package org.example;

import org.apache.curator.framework.CuratorFramework;
import org.springframework.stereotype.Service;

@Service
public class ZkService {
    private final CuratorFramework client;

    public ZkService(CuratorFramework client) {
        this.client = client;
    }


    public Object createNode(String path, String value) throws Exception {
        if (client.checkExists().forPath(path) == null) {
            return client.create().creatingParentsIfNeeded().forPath(path, value.getBytes());
        } else {
            return client.setData().forPath(path, value.getBytes());
        }
    }


    public String readNode(String path) throws Exception {
        if (client.checkExists().forPath(path) != null) {
            return new String(client.getData().forPath(path));
        }
        return null;
    }


    public Object deleteNode(String path) throws Exception {
        if (client.checkExists().forPath(path) != null) {
            return client.delete().forPath(path);
        }

        return null;
    }
}
