package source;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

/**
 * The class source.Recipe describe a recipe.
 */
public class Recipe {
    /**
     * Name of the source.Recipe
     */
    private String name;
    /**
     * URL link of the source.Recipe extracted from <a href="http://marmiton.org">http://marmiton.org</a>
     */
    private String urlLink;
    /**
     * HTML page of the source.Recipe
     */
    private Document page;
    /**
     * HTML code that contains informations about the source.Recipe
     */
    private Element pageContent;

    /**
     * Preparation time of the source.Recipe
     */
    private String prepTime;
    /**
     * Cooking time of the source.Recipe
     */
    private String cookingTime;
    /**
     * Details about the source.Recipe : instructions about how to make this source.Recipe
     */
    private String details;
    /**
     * Ingredients needed to cook the source.Recipe
     */
    private ArrayList<Ingredient> ingredients;
    /**
     * Number of person for whom the recipe is made.
     */
    private int people;


    /**
     * Simple constructor of source.Recipe.
     *
     * @param name    Name of the source.Recipe.
     * @param urlLink Url link of the source.Recipe.
     */
    public Recipe(String name, String urlLink) {

        this.name = name;
        this.urlLink = urlLink;


    }

    /**
     * Method that send a query to marmiton.org and load the informations about the source.Recipe.
     *
     * @throws IOException
     */
    public void loadInformations() throws IOException {
        page = Jsoup.connect(this.getUrlLink()).get();
        pageContent = page.getElementsByClass("m_content_recette_main").first();

        //System.out.println(pageContent);

        prepTime = (pageContent.getElementsByClass("preptime").first().getElementsByClass("value-title").first().attr("title")).substring(2);
        cookingTime = (pageContent.getElementsByClass("cooktime").first().getElementsByClass("value-title").first().attr("title")).substring(2);
        details = pageContent.getElementsByClass("m_content_recette_todo").first().text();
        String ingredientsString = pageContent.getElementsByClass("m_content_recette_ingredients").first().text();

        ingredients = extractIngredients(ingredientsString);

        people = getPeople(ingredientsString);

    }


    public String getName() {
        return name;
    }

    public String getUrlLink() {
        return urlLink;
    }

    public String getPrepTime() {
        return prepTime;
    }

    public String getCookingTime() {
        return cookingTime;
    }



    /**
     * Static method which search Recipes by name and returns an ArrayList of these Recipes.
     *
     * @param keyword Title of the source.Recipe to search.
     * @return ArrayList of Recipes. Informations about these Recipes are not available due to performance reasons, you have to call {@link #loadInformations()} loadInformations} on the source.Recipe you want to have the informations.
     * @throws IOException
     */
    public static ArrayList<Recipe> search(String keyword) throws IOException {
        keyword.replaceAll(" ", "-");

        ArrayList<Recipe> resultRecipes = new ArrayList<>();

        Document document = Jsoup.connect("http://www.marmiton.org/recettes/recherche.aspx?aqt=" + keyword).get();

        Elements resultsElements = document.getElementsByClass("recipe-card");

        for (Element e : resultsElements) {
            Elements currentRecipeElement = e.getElementsByAttribute("recipe-card__title");
            String title = currentRecipeElement.attr("recipe-card__title");
            String urlLink = "http://www.marmiton.org" + currentRecipeElement.attr("href");
            //System.out.println(titre+"\n"+lien+"\n\n");

            resultRecipes.add(new Recipe(title, urlLink));
        }

        return resultRecipes;
    }



    private int getPeople(String ingredientsString){

        String completeString[] = ingredientsString.split(":");

        if (completeString[0].contains("personne")){

            String peopleString = completeString[0].split("pour")[1].substring(1,2);
            int people = Integer.parseInt(peopleString);
            return people;
        }

        return 0;

    }


    private ArrayList<Ingredient> extractIngredients(String ingredientsString){

        ArrayList<Ingredient> result = new ArrayList<>();

        String completeString[] = ingredientsString.split(":");
        completeString = completeString[1].split("-");

        for (String s :
                completeString) {
            if (s.equals(" ")) {
                //escape the first empty element
            }else {

                String ingredientName = "";
                String unit = "";
                float quantity = 0.0f;

                if (s.matches("[^0-9]*[0-9]+[^0-9]*")) {
                    String numberOnly = s.replaceAll("[^0-9]", "");
                    quantity = Float.parseFloat(numberOnly);
                }
                if (s.contains(" de ")) {
                    unit = s.split(" de ")[0];
                    unit = unit.replaceAll("\\d", "");
                    ingredientName = s.split(" de ")[1];

                } else {
                    ingredientName = s.replaceAll("\\d", "");
                }

                result.add(new Ingredient(ingredientName, quantity, unit));
            }
        }

        return result;
    }

    public static ArrayList<ArrayList<String>> listRecipes()  throws IOException{
    	ArrayList<ArrayList<String>> listRecipes = new ArrayList<>();
    	for (int i=0; i<20;i++){
    		listRecipes.add(getRandomRecipe());
    	}
    	return listRecipes;
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

        	listIngredients.add(ingredient.text());
        }
        return listIngredients;

    }


    @Override
    public String toString() {
        return "Recipe{" +
                "name='" + name + '\'' +
                ", urlLink='" + urlLink + '\'' +
                ", prepTime='" + prepTime + '\'' +
                ", cookingTime='" + cookingTime + '\'' +
                ", details='" + details + '\'' +
                ", ingredients=" + ingredients +
                ", people=" + people +
                '}';
    }
}
