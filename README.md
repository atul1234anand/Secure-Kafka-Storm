# Secure-Kafka-Storm

## Create a Topic

```
cd /usr/local/kafka
bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic <TOPIC_NAME>
```

## Publish to a topic
```
bin/kafka-console-producer.sh --broker-list localhost:9092 --topic testTopic
```

## mvn Execution
```
mvn compile
mvn -e -X exec:java -Dexec.mainClass=com.kafkastuff.wordcount.App
```
