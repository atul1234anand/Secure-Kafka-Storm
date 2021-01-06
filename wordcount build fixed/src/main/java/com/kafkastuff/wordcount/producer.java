package com.kafkastuff.wordcount;
import java.util.Properties;
import java.util.Collections;
import java.util.Set;
import org.apache.kafka.clients.producer.Producer;
//import com.kafkastuff.wordcount.KafkaProducer;
//import com.kafkastuff.wordcount.Producer_kafka;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.admin.ListTopicsResult;
import org.apache.kafka.common.securekafkastuff.Topics;


public class producer {
	
	private static final Logger logger = LogManager.getLogger(producer.class);
	
	
	
	public static void createTestTopic(String topic, int numberOfPartitions, int replicationFactor, Properties properties) {
		//LOG.info("Creating topic {}", topic);
		System.out.println("Topic is: "+topic);
		try (AdminClient adminClient = AdminClient.create(new Properties())) {
			ListTopicsResult listTopics = adminClient.listTopics();
			Set<String> names = listTopics.names().get();
			boolean contains = names.contains(topic);
			if(!contains){
				NewTopic topicObj = new NewTopic(topic, numberOfPartitions, (short) replicationFactor);
				adminClient.createTopics(Collections.singleton(topicObj)).all().get();
			}
		} catch (Exception e) {
			e.printStackTrace();
			//fail("Create test topic : " + topic + " failed, " + e.getMessage());
		}
	}
	
	
	public void sendInfo(KafkaProducer<String,String> Producer,String topicName){
		for(int i = 0;i<100;i++) {
			String[] updatedKV = Producer.updateRules("KEY" + Integer.toString(i), "VALUE" + Integer.toString(i));
			Producer.send(new ProducerRecord<String, String>(topicName,updatedKV[0],updatedKV[1]));
		}	
	}

	public static void main(String[] args) {
		//String topicName = "my-first-topic";
		
		System.out.println("Started Producer");
		String topicName = "StockMarketTopic";
		Properties props_1 = new Properties();
		props_1.put("bootstrap.servers", "localhost:9092"); 
		//createTestTopic(topicName,1,1,props_1);
		Topics t = new Topics();
		t.updateTopics(props_1);
		t.createTopic(topicName,1,1,props_1);

		Properties props = new Properties();

		props.put("bootstrap.servers", "localhost:9092");    
		props.put("acks", "all");
		props.put("retries", 0);
		props.put("batch.size", 16384);   
		props.put("linger.ms", 1);   
		props.put("buffer.memory", 33554432);
		props.put("key.serializer","org.apache.kafka.common.serialization.StringSerializer");
		props.put("value.serializer","org.apache.kafka.common.serialization.StringSerializer");
		props.put("rules","GROUP1:READ");
		
		KafkaProducer<String,String> producer = new KafkaProducer<String,String>(props);
		producer p = new producer();
		System.out.println("Before Send");
		p.sendInfo(producer,topicName);
		System.out.println("MESSAGE SENT");
		t.deleteTopic("testTopic",props_1);
		producer.close();
	}	
}

