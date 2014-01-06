package cat.can.read.warbots.dialog.actionChooser;

public enum OrientationEnum {

	NORTH (0),
	EAST (1),
	SOUTH (2),
	WEST (3);
	
	private int val;
	
	private OrientationEnum(int v) {
		val = v;
	}
	
	public int getVal() {
		return val;
	}
	
	public OrientationEnum getOrientation(int val) {
		
		if (val == NORTH.getVal()) {
			return NORTH;
		} else if (val == EAST.getVal()) {
			return EAST;
		} else if (val == SOUTH.getVal()) {
			return SOUTH;
		} else if (val == WEST.getVal()) {
			return WEST;
		}
		
		return null;
	}
	
	
	public OrientationEnum getOrientationAfterTurn(final ActionsEnum turnAction) {
		
		if (turnAction == ActionsEnum.ACTION_TURN_LEFT) {
			int newPos = (val+3)%4;
			return getOrientation(newPos);
		}
		
		if (turnAction == ActionsEnum.ACTION_TURN_RIGHT) {
			int newPos = (val+1)%4;
			return getOrientation(newPos);
		}
		
		return null;
	}
	
}
