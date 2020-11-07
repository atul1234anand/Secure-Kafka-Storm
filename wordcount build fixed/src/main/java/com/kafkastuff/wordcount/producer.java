package com.kafkastuff.wordcount;
import java.util.Properties;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class producer {
	
	private static final Logger logger = LogManager.getLogger(producer.class);
	public static void main(String[] args) {
		//String topicName = "my-first-topic";

		String topicName = "prototypeOneTopic";
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

