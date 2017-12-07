### Marmiton Simple API
Crawl des recettes
Envois sur un serveur Kafka des listes d'ingredients
Projet Maven

### Installation
git clone <http:...SDTD>

---Kafka---
sh scriptKafkaInstall.sh
sh scriptKafkaExec.sh

----API----
ouvrir eclipse
File>Import>Maven Project > Existing Files > choisir le dossier [pom.xml devrait etre reconnu]
executer SimpleRecipeSearchExample.java

### Resultats
Sur la console du consumer Kafka, qui reçoit les flux de données, on observe l'ensemble des requêtes


