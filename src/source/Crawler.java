package source;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;




import java.io.IOException;
import java.util.*;

/**
 * The class source.Recipe describe a recipe.
 */
public class Crawler {
	private HashMap<String,Integer> mapCount;
	
	public ArrayList<Relation> weightRelation(ArrayList<Relation> listRelations){
		for (Relation rel : listRelations){
			rel.setCoef((float)rel.getCount()/(float)this.mapCount.get(rel.getIngredient1()));
		}
		return  listRelations;
	}
   
    public static HashMap<String,HashMap<String,Integer>> mapRelationsString()  throws IOException{
    	ArrayList<String> resultRecipes = new ArrayList<>();
    	HashMap<String,HashMap<String,Integer>> mapRelations = new HashMap<>();
    	String name1="";
    	String name2="";
    	for (int k=0; k<400;k++){
    		resultRecipes = getRandomRecipe();
    		for (int i=0;i<resultRecipes.size();i++){
            	for (int j=0;j<resultRecipes.size();j++){
            		if (i!=j){
            			name1 = resultRecipes.get(i).substring(0, 1).toUpperCase() + resultRecipes.get(i).substring(1);
            			name2 = resultRecipes.get(j).substring(0, 1).toUpperCase() + resultRecipes.get(j).substring(1);
            			if (mapRelations.containsKey(name1)){
            				if (mapRelations.get(name1).containsKey(name2)){
            					mapRelations.get(name1).put(name2, mapRelations.get(name1).get(name2) +1);
            				}else{
            					mapRelations.get(name1).put(name2, 1);
            				}
            			}else{
            				mapRelations.put(name1, new HashMap<String,Integer>());
            				mapRelations.get(name1).put(name2, 1);
            			}
            		}
            	}
            }
    	}    	
    	return mapRelations;
    }
    
    HashMap<String,String> temp = new HashMap<>();
    public Crawler(){
    	temp.put("Verte","Olive Verte");
    	temp.put("Noire","Olive Noire");
    	temp.put("Demi-sel","Beurre Demi-Sel");
    	temp.put("Demi-Sel","Beurre Demi-Sel");
    	temp.put("Demi sel","Beurre Demi-Sel");
    	temp.put("Demi Sel","Beurre Demi-Sel");
    	temp.put("Filo","Pâte Filo");
    	temp.put("Bicarbonate","Bicarbonate de soude");
    	temp.put("Soude","Bicarbonate de soude");
    	temp.put("Secs","Raisins secs");
    	temp.put("Paimpol","Coco de paimpol");
    	temp.put("Brisée","Pâte brisée");
    	temp.put("Blanc","Fromage blanc");
    	temp.put("Vanillé","Sucre vanillé");
    	temp.put("Chèvre","Fromage de chèvre");
    	temp.put("Espelette","Piment d'espelette");
    	temp.put("Brisée","Pâte brisée");
    	temp.put("Brisée","Pâte brisée");
    	temp.put("Brisée","Pâte brisée");
    	temp.put("Brisée","Pâte brisée");
    	temp.put("Brisée","Pâte brisée");
    }
    
    
    public ArrayList<Relation> getRelations() throws IOException{
    	this.mapCount = new HashMap<>();
    	ArrayList<Relation> listRelations = new ArrayList<>();
    	HashMap<String,HashMap<String,Integer>> mapRelations = mapRelationsString();
    	for (String key : mapRelations.keySet()){
    		mapCount.put(key, 0);
    		HashMap<String,Integer> mapValues = mapRelations.get(key);
    		for(String key2 : mapValues.keySet()){
    			mapCount.put(key, mapCount.get(key) + 1);
    			Relation rel = new Relation(key, key2,mapValues.get(key2));
    			listRelations.add(rel);
    		}
    	}
    	return listRelations;
    }
    /**
     * Method which return a random Recipe.
     * @return  A random Recipe.
     * @throws IOException
     */
    public static ArrayList<String> getRandomRecipe() throws IOException{

        Document document = Jsoup.connect("http://www.marmiton.org/recettes/recette-hasard.aspx").get();
//        String url = document.baseUri();
        ArrayList<String> listIngredients = new ArrayList<>();
        Elements ingredients = document.getElementsByClass("ingredient");
        for (Element ingredient : ingredients){
//        	String nomComplet = ingredient.toString();
        	String lastWord = ingredient.text().substring(ingredient.text().lastIndexOf(" ")+1).replace("d'", "");
        	listIngredients.add(lastWord);
        }
        return listIngredients;

    }

}
