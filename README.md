### Marmiton Simple API
Crawl des recettes au hasard
Envoit les recettes sur un serveur Kafka des listes d'ingredients

### Installation
git clone <http:...SDTD>

---Kafka---
sh scriptKafkaInstall.sh
sh scriptKafkaExec.sh

----API----
mvn exec:java -Dexec.mainClass="source.Main"

### Lancement Producer & Consumer Kafka


### Resultats
Le consumer Kafka reçoit en continue les recettes jusqu'à l'arrêt du programme de crawling.


