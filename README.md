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
## Running the producer
```
Create a new java project in eclipse.
Create a new java class (name it producer) by right clicking on the java project.
Right click on the newly created class, select build path -> configure build path -> libraries -> add external jars and select all jars in the lib folder of the kafka source code.
Copy the code in producer.java to the newly created class.
Run the class
```

## mvn Execution
```
mvn compile
mvn -e -X exec:java -Dexec.mainClass=com.kafkastuff.wordcount.App
```

## mvn Process PID command
```
ps -o pid,user,cmd -C java | sed -e 's/\([0-9]\+ *[^ ]*\) *[^ ]* *\([^$]*\)/\1 \2/' -e 's/-c[^ ]* [^ ]* \|-[^ ]* //g'
```
