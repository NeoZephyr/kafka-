package com.pain.kafka.basic;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.utils.Utils;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class HashPartitioner implements Partitioner {

    private final AtomicInteger counter = new AtomicInteger(0);

    @Override
    public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
        List<PartitionInfo> partitionInfos = cluster.partitionsForTopic(topic);
        int partitionNum = partitionInfos.size();

        System.out.println(String.format("topic: %s, partitionNum: %d", topic, partitionNum));

        if (null == keyBytes) {
            return counter.incrementAndGet() % partitionNum;
        } else {
            return Utils.toPositive(Utils.murmur2(keyBytes)) % partitionNum;
        }
    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> configs) {

    }
}
