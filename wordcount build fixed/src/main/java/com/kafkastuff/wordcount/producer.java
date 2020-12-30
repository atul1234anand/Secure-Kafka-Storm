package com.kafkastuff.wordcount;
import java.util.Properties;
import java.util.Collections;
import java.util.Set;
import org.apache.kafka.clients.producer.Producer;
import com.kafkastuff.wordcount.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.admin.ListTopicsResult;

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

	public static void main(String[] args) {
		//String topicName = "my-first-topic";

		String topicName = "TestTopic";
		Properties props_1 = new Properties();
		props_1.put("bootstrap.servers", "localhost:9092"); 
		createTestTopic(topicName,1,1,props_1);

		Properties props = new Properties();

		props.put("bootstrap.servers", "localhost:9092");    
		props.put("acks", "all");
		props.put("retries", 0);
		props.put("batch.size", 16384);   
		props.put("linger.ms", 1);   
		props.put("buffer.memory", 33554432);
		props.put("key.serializer","org.apache.kafka.common.serialization.StringSerializer");
		props.put("value.serializer","org.apache.kafka.common.serialization.StringSerializer");
		
		Producer<String,String> producer = new KafkaProducer<String,String>(props);
		
		for(int i = 0;i<100;i++) {
	         producer.send(new ProducerRecord<String, String>(topicName, "I am borat" + Integer.toString(i), "I am borat" + Integer.toString(i)));
		}
		System.out.println("MESSAGE SENT");
		producer.close();
	}	
}

