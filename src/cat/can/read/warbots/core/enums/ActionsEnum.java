package cat.can.read.warbots.core.enums;

import cat.can.read.warbots.R;

public enum ActionsEnum {

	ACTION_MOVE_UP (R.drawable.pos_up),
	ACTION_MOVE_DOWN (R.drawable.pos_down),
	ACTION_TURN_LEFT (R.drawable.pos_left),
	ACTION_TURN_RIGHT (R.drawable.pos_right);
	
	private int image;
	
	private ActionsEnum(final int image) {
		this.image = image;
	}
	
	public int getImage() {
		return image;
	}
}
