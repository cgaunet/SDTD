package example;

import source.Crawler;
import source.Relation;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * A simple example of how to use the source.Recipe class to search a recipe.
 */
public class SimpleRecipeSearchExample {

    public static void main(String[] args) {
    	try{
    		PrintWriter writer = new PrintWriter("testMarmiton.json", "UTF-8");
    		Crawler crawl = new Crawler();
    		ArrayList<Relation> listRel = crawl.weightRelation(crawl.getRelations()); 
    		for(Relation rel : listRel){
    			writer.println(rel.toString());
    		}
    		writer.close();
    	}catch(IOException e){
    		
    	}

    }
}
