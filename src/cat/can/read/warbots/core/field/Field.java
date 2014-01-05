package cat.can.read.warbots.core.field;

public abstract class Field {

	protected String name;
	
	protected boolean navigable;
	
	protected int damage;

	public Field(String name, boolean navigable, int damage) {
		super();
		this.name = name;
		this.navigable = navigable;
		this.damage = damage;
	}

	
}
