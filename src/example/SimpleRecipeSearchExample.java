package example;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import source.Crawler;
/**
 * A simple example of how to use the source.Recipe class to search a recipe.
 */
public class SimpleRecipeSearchExample {

    public static void main(String[] args) {
    	try{
    		PrintWriter writer = new PrintWriter("testMarmiton.json", "UTF-8");
    		Crawler crawl = new Crawler();
//    		ArrayList<Relation> listRel = crawl.weightRelation(crawl.getRelations()); 
//    		for(Relation rel : listRel){
//    			writer.println(rel.toString());
//    		}
    		writer.print("{");
    		//aller sur kafka
    		for(int j=0; j<20;j++) {
        		ArrayList<String> listIngs = crawl.getRandomRecipe();

        		for (int i=0; i<listIngs.size()-1;i++){
        			writer.print("ingredient" + i + "\":\"" + listIngs.get(i) +"\",\"");
        		}    
        		writer.print("ingredient" + (listIngs.size()-1) + "\":\"" + listIngs.get(listIngs.size()-1));
        		writer.println("}");
    		}

    		
    		writer.flush();
    		writer.close();
    	}catch(IOException e){
    		
    	}
    }
}
