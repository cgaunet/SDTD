package source;

public class Relation {
	
	private String ingredient1,ingredient2;
	private float coef;
	private int count;
	
	public Relation(String ingredient1, String ingredient2,int count) {
		super();
		this.ingredient1 = ingredient1;
		this.ingredient2 = ingredient2;
		this.count = count;
	}

	protected String getIngredient1() {
		return ingredient1;
	}

	protected void setIngredient1(String ingredient1) {
		this.ingredient1 = ingredient1;
	}

	protected String getIngredient2() {
		return ingredient2;
	}

	protected void setIngredient2(String ingredient2) {
		this.ingredient2 = ingredient2;
	}

	protected float getCoef() {
		return coef;
	}

	protected void setCoef(float coef) {
		this.coef = coef;
	}
	
	protected void incCount(){
		this.count++;
	}

	@Override
	public String toString() {
		return this.ingredient1 + " " + this.ingredient2 + " " + this.count;
	}
	
	
	
}
