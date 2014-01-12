package cat.can.read.warbots.core.enums;

import cat.can.read.warbots.R;

public enum FloorEnum {

	NORMAL (R.drawable.field_normal),
	DANGER (R.drawable.field_danger),
	BLOCK  (R.drawable.field_block);
	
	private int image;
	
	private FloorEnum(final int image) {
		this.image = image;
	}
	
	public int getImage() {
		return image;
	}
}
