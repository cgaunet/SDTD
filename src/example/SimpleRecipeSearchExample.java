package example;

import source.Recipe;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * A simple example of how to use the source.Recipe class to search a recipe.
 */
public class SimpleRecipeSearchExample {

    public static void main(String[] args) {
    	try{
    		PrintWriter writer = new PrintWriter("testMarmiton", "UTF-8");
    		for (ArrayList<String> listIngredients : Recipe.listRecipes()){
    			for (String nameIngredient : listIngredients){
    				writer.print(nameIngredient + ",");
    			}
    			writer.println();
    		}
    		writer.close();
    	}catch(IOException e){
    		
    	}

    }
}
