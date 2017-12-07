#Partie exécution
cd /opt/Kafka/kafka_2.11-1.0.0/
bin/zookeeper-server-start.sh config/zookeeper.properties
bin/kafka-server-start.sh config/server.properties
bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic TOPICINGREDIENTS
bin/kafka-console-consumer.sh --zookeeper localhost:2181 --from-beginning --topic TOPICINGREDIENTS
