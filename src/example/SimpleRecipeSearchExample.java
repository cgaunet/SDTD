package example;

import source.Crawler;
import source.Relation;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * A simple example of how to use the source.Recipe class to search a recipe.
 */
public class SimpleRecipeSearchExample {

    public static void main(String[] args) {
    	try{
    		PrintWriter writer = new PrintWriter("testMarmiton", "UTF-8");
    		for(Relation rel : Crawler.getRelations()){
    			writer.println(rel.toString());
    		}
    		writer.close();
    	}catch(IOException e){
    		
    	}

    }
}
