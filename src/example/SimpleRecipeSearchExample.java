package example;

import java.util.ArrayList;
import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import source.Crawler;

/**
 * A simple example of how to use the source.Recipe class to search a recipe.
 */
public class SimpleRecipeSearchExample {

	public static void main(String[] args) {
		//PrintWriter writer = new PrintWriter("testMarmiton.json", "UTF-8");
		Crawler crawl = new Crawler();

		Properties properties = new Properties();

		properties.put("bootstrap.servers", "localhost:9092"); //defines the broker location
		properties.put("acks", "all");
		properties.put("retries", 0);
		properties.put("batch.size", 16384);
		properties.put("linger.ms", 1);
		properties.put("buffer.memory", 33554432);
		properties.put("key.serializer", "org.apache.kafka.common.serialization.IntegerSerializer");
		properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		Producer<Integer, String> producer = null;
		try {
			producer = new KafkaProducer<>(properties);
			//test de timeout
			
			for (int j = 0; j < 10; j++) {
				ArrayList<String> listIngs = crawl.getRandomRecipe();
				String lesIngredients = "";
				for (Integer i = 0; i < listIngs.size() - 1; i++) {

					lesIngredients += listIngs;
					lesIngredients += ", ";				
				}
				lesIngredients += listIngs.get(listIngs.size() - 1);
				producer.send(new ProducerRecord<Integer, String>("TOPICINGREDIENTS", j,lesIngredients));
			}
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			producer.close();
		}



	}
}
