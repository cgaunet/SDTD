### Marmiton Simple API
Crawl des recettes au hasard
Envoit les recettes sur un serveur Kafka des listes d'ingredients

### Installation
git clone <http:...SDTD>

---Kafka---
sh scriptKafkaInstall.sh

### Lancement Producer & Consumer Kafka
sh StartProducerConsumerLocal.sh

### Lancement séparé - si script global ne fonctionne pas :
1er terminal : ----API----
mvn exec:java -Dexec.mainClass="source.Main"

2eme terminal : ---Kafka---
sh scriptKafkaExec.sh

### Arret Kafka
sh StopProducerConsumerLocal.sh

### Resultats
Le consumer Kafka reçoit en continue les recettes jusqu'à l'arrêt du programme de crawling.


