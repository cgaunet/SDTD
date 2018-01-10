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
	
	//DEUX HASHMAP : 
	//mapMots gere les cas particuliers de derniers mots : exemple 500g d'huile d'olive , au lieu de recuperer olive je renvois huile d'olive
	//mapCount 
	
	private HashMap<String,Integer> mapCount;
	
	
	public ArrayList<Relation> weightRelation(ArrayList<Relation> listRelations){
		for (Relation rel : listRelations){
			rel.setCoef((float)rel.getCount()/((float)this.mapCount.get(rel.getIngredient1())));
		}
		return  listRelations;
	}
   
	
	static HashMap<String, String> mapMots = new HashMap<>();

	
	//constructeur
	//initialise le mapcount et le mapMots pour les cas particuliers
	public Crawler(){
		this.mapCount = new HashMap<String,Integer>();
		mapMots.put("terre", "pomme de terre");
		mapMots.put("olive", "huile d'olive");
		mapMots.put("râpé", "fromage râpé");
		mapMots.put("coco", "noix de coco");
		mapMots.put("blanc", "fromage blanc");
		mapMots.put("fumés", "lardons fumés");
		mapMots.put("muscade", "noix de muscade");
		mapMots.put("frais", "fromage frais");
		mapMots.put("feuilletée", "pâte feuilletée");
		mapMots.put("épaisse", "crème épaisse");
		mapMots.put("verte","Olive Verte");
		mapMots.put("noire","Olive Noire");
    	mapMots.put("demi-sel","Beurre Demi-Sel");
    	mapMots.put("demi-Sel","Beurre Demi-Sel");
    	mapMots.put("demi sel","Beurre Demi-Sel");
    	mapMots.put("demi Sel","Beurre Demi-Sel");
    	mapMots.put("filo","Pâte Filo");
    	mapMots.put("bicarbonate","Bicarbonate de soude");
    	mapMots.put("soude","Bicarbonate de soude");
    	mapMots.put("verte","Olive Verte");
    	mapMots.put("secs","Raisins secs");
    	mapMots.put("paimpol","Coco de paimpol");
    	mapMots.put("brisée","Pâte brisée");
    	mapMots.put("blanc","Fromage blanc");
    	mapMots.put("vanillé","Sucre vanillé");
    	mapMots.put("chèvre","Fromage de chèvre");
    	mapMots.put("espelette","Piment d'espelette");
    	mapMots.put("brisée","Pâte brisée");
    	mapMots.put("sec", "Raisins Sec");

	}
	
	
	
    public HashMap<String,HashMap<String,Integer>> mapRelationsString()  throws IOException{
    	ArrayList<String> resultRecipes = new ArrayList<>();
    	HashMap<String,HashMap<String,Integer>> mapRelations = new HashMap<>();
    	String name1="";
    	String name2="";

    	for (int k=0; k<10;k++){
    		resultRecipes = getRandomRecipe();
    		for (int i=0;i<resultRecipes.size();i++){
            	for (int j=0;j<resultRecipes.size();j++){
            		if (i!=j){
            			name1 = resultRecipes.get(i).toLowerCase() + resultRecipes.get(i).substring(1);
            			name2 = resultRecipes.get(j).toLowerCase() + resultRecipes.get(j).substring(1);
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
    
    /**
     * Method which return a array list of relation ingredient 1 - ingredient2 - coeff
     * @return  ArrayList<Relation>
     * @throws IOException
     */
    public ArrayList<Relation> getRelations() throws IOException{
    	this.mapCount = new HashMap<String,Integer>();
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

    public ArrayList<String> getRandomRecipe() throws IOException{

        Document document = Jsoup.connect("http://www.marmiton.org/recettes/recette-hasard.aspx").get();

        ArrayList<String> listIngredients = new ArrayList<>();
        Elements ingredients = document.getElementsByClass("ingredient");
        for (Element ingredient : ingredients){

        	String lastWord = ingredient.text().substring(ingredient.text().lastIndexOf(" ")+1).replace("d'", "");
        	if(mapMots.containsKey(lastWord)){
        		lastWord = mapMots.get(lastWord);
        	}
        	listIngredients.add(lastWord);
        }
        return listIngredients;

    }

}
