package org.dubbel7.dapo.server;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

public class Server {

    private final HazelcastInstance instance;

    public Server() {
        Config cfg = new Config();
        instance = Hazelcast.newHazelcastInstance(cfg);
    }



    public static void main(String[] a) {

        Server server = new Server();
    }
}
