package com.kafkastuff.wordcount;

import org.apache.storm.Config;
import java.util.UUID;
import org.apache.storm.spout.SchemeAsMultiScheme;
//import org.apache.storm.LocalCluster;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.kafka.spout.KafkaSpoutConfig;
import org.apache.storm.kafka.spout.KafkaSpout;
import org.apache.storm.kafka.bolt.KafkaBolt;
import org.apache.storm.StormSubmitter;
import org.apache.storm.generated.AlreadyAliveException;
import java.lang.InterruptedException;
import java.lang.Exception;
import java.util.Properties;
import org.apache.storm.kafka.bolt.mapper.FieldNameBasedTupleToKafkaMapper;
import org.apache.storm.kafka.bolt.selector.DefaultTopicSelector;
import org.apache.storm.LocalCluster;
import org.apache.storm.generated.InvalidTopologyException;
import org.apache.storm.thrift.TException;


public class App 
{
    public static void main( String[] args ) throws AlreadyAliveException, InterruptedException, org.apache.storm.thrift.TException, java.lang.Exception
    {
        System.out.println( "TOPOLOGY_STARTING" );

	System.out.println("Config given for topology");
	//Kafka Spout configerations
	String zkConnString = "localhost:2181";
	String topic = "words";
	System.out.println("Config given for Spout");
	final TopologyBuilder tp = new TopologyBuilder();
	System.out.println("Empty Topology created");
	KafkaSpoutConfig<String,String> kafkaSpoutConfig = KafkaSpoutConfig.builder("localhost:9092","testTopic").build();
	KafkaSpout<String,String> kafkaSpoutInput = new KafkaSpout<>(kafkaSpoutConfig);
	tp.setSpout("kafka_spout",kafkaSpoutInput, 1);
	System.out.println("Spout set");

	//NIMBUS ERRORS HERE
	LocalCluster localCluster = new LocalCluster();
	localCluster.submitTopology("stupitops",new Config(),tp.createTopology());
	Thread.sleep(30000);
    }
    
    /* KafkaSpoutConfig<ByteBuffer, ByteBuffer> kafkaSpoutConfig = 
    new KafkaSpoutConfig.Builder<ByteBuffer, ByteBuffer>(bootstrapServers, topic)
    .setProp(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, ByteBufferDeserializer.class)
    .setProp(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ByteBufferDeserializer.class)
    .setProp(ConsumerConfig.GROUP_ID_CONFIG, "storm-sql-kafka-" + UUID.randomUUID().toString())
    .setRecordTranslator(new RecordTranslatorSchemeAdapter(scheme))
    .build();*/
}
