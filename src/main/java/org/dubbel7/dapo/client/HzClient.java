package org.dubbel7.dapo.client;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;

import java.util.*;

public class HzClient implements Client {

    private final HazelcastInstance client;
    public HzClient() {

        ClientConfig clientConfig = new ClientConfig();
        clientConfig.addAddress("127.0.0.1:5701");
        client = HazelcastClient.newHazelcastClient(clientConfig);
    }

    public void save(Entity e) {
        client.getMap(e.getType()).put(e.getKey(), e.getValue());
    }

    public List<Entity> getAll(String type) {
        List<Entity> res = new ArrayList<Entity>();
        IMap<String, String> map = client.getMap(type);
        Set<Map.Entry<String, String>> entries = map.entrySet();
        for(Map.Entry<String, String> e : entries) {
            res.add(new Entity(type, e.getKey(), e.getValue()));
        }
        return res;
    }

    public void registerListener(String type, Listener<Entity> l) {

    }

    public static void main(String[] a) {

        HzClient hzClient = new HzClient();

        Scanner scanner = new Scanner(System.in);
        while (true) {
            String next = scanner.nextLine();
            if(next.startsWith("add")) {
                String[] split = next.split(" ");
                String type = split[1];
                String key = split[2];
                String val = split[3];
                System.out.println("add for type " + type);
                hzClient.save(new Entity(type, key, val));

            } else if(next.startsWith("all")) {
                String[] split = next.split(" ");
                String type = split[1];
                System.out.println("get all for type " + type);
                List<Entity> all = hzClient.getAll(type);
                for(Entity e : all) {
                    System.out.println(e);
                }
            } else if(next.startsWith("exit")) {
                System.out.println("Stop client");
                System.exit(-1);
            }
        }
    }
}
