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
import org.apache.kafka.common.serialization.ByteBufferDeserializer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import com.kafkastuff.wordcount.splitter;




public class App 
{
    public static void main( String[] args ) throws AlreadyAliveException, InterruptedException, org.apache.storm.thrift.TException, java.lang.Exception
    {
        System.out.println( "TOPOLOGY_STARTING" );
	System.out.println("Config given for topology");
	String zkConnString = "localhost:2181";
	System.out.println("Config given for Spout");
	final TopologyBuilder tp = new TopologyBuilder();
	System.out.println("Empty Topology created");
	KafkaSpoutConfig<String,String> kafkaSpoutConfig = KafkaSpoutConfig.builder("localhost:9092","prototypeOneTopic")
	.setProp(ConsumerConfig.GROUP_ID_CONFIG, "kafka_spout-" + UUID.randomUUID().toString())
	.build();
	KafkaSpout<String,String> kafkaSpoutInput = new KafkaSpout<>(kafkaSpoutConfig);
	tp.setSpout("kafka_spout",kafkaSpoutInput, 1);
	tp.setBolt("sentence-splitter", new splitter(), 1).shuffleGrouping("kafka_spout");
	System.out.println("Spout set");
	LocalCluster localCluster = new LocalCluster();
	localCluster.submitTopology("stupidtops",new Config(),tp.createTopology());
	Thread.sleep(30000);
    }
}
