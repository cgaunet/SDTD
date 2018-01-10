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
		mapMots.put("terre", "Pomme de terre");
		mapMots.put("olive", "Huile d'olive");
		mapMots.put("râpé", "Fromage râpé");
		mapMots.put("coco", "Noix de coco");
		mapMots.put("blanc", "Fromage blanc");
		mapMots.put("fumés", "Lardons fumés");
		mapMots.put("muscade", "Noix de muscade");
		mapMots.put("frais", "Fromage frais");
		mapMots.put("feuilletée", "Pâte feuilletée");
		mapMots.put("épaisse", "Crème épaisse");
		mapMots.put("verte","Olive Verte");
		mapMots.put("noire","Olive Noire");
    	mapMots.put("demi-sel","Beurre Demi-Sel");
    	mapMots.put("filo","Pâte Filo");
    	mapMots.put("bicarbonate","Bicarbonate de soude");
    	mapMots.put("soude","Bicarbonate de soude");
    	mapMots.put("secs","Raisins secs");
    	mapMots.put("paimpol","Coco de paimpol");
    	mapMots.put("brisée","Pâte brisée");
    	mapMots.put("blanc","Fromage blanc");
    	mapMots.put("vanillé","Sucre vanillé");
    	mapMots.put("chèvre","Fromage de chèvre");
    	mapMots.put("espelette","Piment d'espelette");
    	mapMots.put("brisée","Pâte brisée");
    	mapMots.put("sec", "Jambon sec");
    	mapMots.put("noir", "Poivre noir");
    	mapMots.put("saint-jacques", "Coquilles saint-jacques");
    	mapMots.put("soja", "Sauce soja");
    	mapMots.put("fraîche", "Crème fraîche");
    	mapMots.put("mie", "Pain de mie");
    	mapMots.put("blanc", "Jambon blanc");
    	mapMots.put("sarrasin", "Farine de sarrasin");
    	mapMots.put("vert", "Haricot verts");
    	mapMots.put("verts", "Haricot verts");
    	mapMots.put("charentes", "Pinot des Charentes");
    	mapMots.put("rose", "Arôme de rose");
    	mapMots.put("roux", "Sucre roux");
    	mapMots.put("glace", "Sucre glace");
    	mapMots.put("chimique", "Levure chimique");
    	mapMots.put("boulanger", "Levure de boulanger");
    	mapMots.put("poudre", "Sucre en poudre");
    	mapMots.put("liquide", "Vanille liquide");
    	mapMots.put("pieds", "Pieds de porc");
    	mapMots.put("paris", "Jambon de paris");
    	mapMots.put("rouge", "Vin rouge");
    	mapMots.put("xeres", "Vinaigre de Xérès");
    	mapMots.put("xéres", "Vinaigre de Xérès");
    	mapMots.put("xérès", "Vinaigre de Xérès");
    	mapMots.put("cajou", "Noix de Cajou");
    	mapMots.put("haché", "Steak haché");
    	mapMots.put("hachés", "Steak haché");

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
