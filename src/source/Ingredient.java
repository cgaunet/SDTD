package source;

/**
 * Class which define an ingredient in a Recipe.
 */
public class Ingredient {
    /**
     * Name of the ingredient.
     */
    private String name;
    /**
     * Quantity of the ingredient.
     */
    private float quantity;
    /**
     * Unit of the ingredient.
     */
    private String unit;

    /**
     * Constructor of the ingredient.
     * @param name Name of the ingredient.
     * @param quantity Quantity of the ingredient.
     * @param unit Unit of the ingredient.
     */
    public Ingredient(String name, float quantity, String unit){
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
    }

    /**
     * Simple constructor of Ingredient.
     * @param name Name of the ingredient.
     */
    public Ingredient(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public float getQuantity() {
        return quantity;
    }

    public String getUnit() {
        return unit;
    }

    @Override
    public String toString() {
        return this.quantity+this.unit+" "+this.name;
    }
}
