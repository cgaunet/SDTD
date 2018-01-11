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
		mapMots.put("verte","olive Verte");
		mapMots.put("noire","olive Noire");
    	mapMots.put("demi-sel","beurre Demi-Sel");
    	mapMots.put("filo","pâte Filo");
    	mapMots.put("bicarbonate","bicarbonate de soude");
    	mapMots.put("soude","bicarbonate de soude");
    	mapMots.put("secs","raisins secs");
    	mapMots.put("paimpol","coco de paimpol");
    	mapMots.put("brisée","pâte brisée");
    	mapMots.put("blanc","fromage blanc");
    	mapMots.put("vanillé","sucre vanillé");
    	mapMots.put("chèvre","fromage de chèvre");
    	mapMots.put("espelette","piment d'espelette");
    	mapMots.put("brisée","pâte brisée");
    	mapMots.put("sec", "jambon sec");
    	mapMots.put("noir", "poivre noir");
    	mapMots.put("saint-jacques", "coquilles saint-jacques");
    	mapMots.put("soja", "sauce soja");
    	mapMots.put("fraîche", "crème fraîche");
    	mapMots.put("mie", "pain de mie");
    	mapMots.put("blanc", "jambon blanc");
    	mapMots.put("sarrasin", "farine de sarrasin");
    	mapMots.put("vert", "haricot verts");
    	mapMots.put("verts", "haricot verts");
    	mapMots.put("charentes", "pinot des Charentes");
    	mapMots.put("rose", "arôme de rose");
    	mapMots.put("roux", "sucre roux");
    	mapMots.put("glace", "sucre glace");
    	mapMots.put("chimique", "levure chimique");
    	mapMots.put("boulanger", "levure de boulanger");
    	mapMots.put("poudre", "sucre en poudre");
    	mapMots.put("liquide", "vanille liquide");
    	mapMots.put("pieds", "pieds de porc");
    	mapMots.put("paris", "jambon de paris");
    	mapMots.put("rouge", "vin rouge");
    	mapMots.put("xeres", "vinaigre de Xérès");
    	mapMots.put("xéres", "vinaigre de Xérès");
    	mapMots.put("xérès", "vinaigre de Xérès");
    	mapMots.put("cajou", "noix de Cajou");
    	mapMots.put("haché", "steak haché");
    	mapMots.put("hachés", "steak haché");

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
