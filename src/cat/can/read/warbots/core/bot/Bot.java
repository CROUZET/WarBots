package cat.can.read.warbots.core.bot;

import java.io.Serializable;
import java.util.List;

import cat.can.read.warbots.core.enums.ActionsEnum;

import android.widget.ImageView;

public class Bot implements Serializable {

	// -----------------------------------------------------------------------------------------------------------
	
	/**
	 * Serial UID
	 */
	private static final long serialVersionUID = 5712657966466512090L;

	// -----------------------------------------------------------------------------------------------------------
	
	private long hp;
	
	private long armor;
	
	private long damage;
	
	// -----------------------------------------------------------------------------------------------------------
	
	public Bot(long hp, long armor, long damage) {
		
	}

	// -----------------------------------------------------------------------------------------------------------
	
	public long getHp() {
		return hp;
	}

	public void setHp(long hp) {
		this.hp = hp;
	}

	public long getArmor() {
		return armor;
	}

	public void setArmor(long armor) {
		this.armor = armor;
	}

	public long getDamage() {
		return damage;
	}

	public void setDamage(long damage) {
		this.damage = damage;
	}
}
