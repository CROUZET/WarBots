package cat.can.read.warbots;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Matrix;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.GridLayout;
import android.widget.GridLayout.Spec;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import cat.can.read.warbots.constants.Constants;
import cat.can.read.warbots.core.enums.ActionsEnum;
import cat.can.read.warbots.core.enums.OrientationEnum;
import cat.can.read.warbots.dialog.actionChooser.DialogActionChooser;
import cat.can.read.warbots.utils.MapLoader;

public class GameboardActivity extends Activity {

	private static final int ANIMATION_BOT_ROTATION_ONE_QUARTER = 90;
	
	// -----------------------------------------------------------------------------------------------------------

	//private int gameboardSize;
	
	private GridLayout gridLayout;

	private ImageView playersBot;
	
	private int playersBotPosX;
	
	private int playersBotPosY;
	
	private List<ActionsEnum> actionsToPerform;
	
	private int numberActionPlayed;
	
	private OrientationEnum botOrientation;
	
	
	// -----------------------------------------------------------------------------------------------------------
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		loadParameters();

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gameboard);
		
		// Init Gameboard
		initGameBoard();
		initRobotPosition();
		
		// Start Actions
		showUserActionsChooser();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.gameboard, menu);
		return true;
	}

	// -----------------------------------------------------------------------------------------------------------
	
	private void loadParameters() {
		
		Intent intent = getIntent();
		//gameboardSize = Integer.parseInt(intent.getStringExtra(MainActivity.PARAM_GAMEBOARD_SIZE));
	}
	
	private void initGameBoard() {

		gridLayout = (GridLayout) findViewById(R.id.thegrid);
		MapLoader.construct(this, R.raw.map_dev, gridLayout);
	}
		
	private void initRobotPosition() {

		playersBot = new ImageView(this);
		playersBot.setImageResource(R.drawable.robot);

		playersBotPosX = gridLayout.getColumnCount()/2;
		playersBotPosY = gridLayout.getRowCount()-1;
		
		Spec colspecs = GridLayout.spec(playersBotPosX);
		Spec rowspecs = GridLayout.spec(playersBotPosY); 
		GridLayout.LayoutParams gridLayoutParam = new GridLayout.LayoutParams(rowspecs, colspecs);
		
		botOrientation = OrientationEnum.NORTH;
		
		gridLayout.addView(playersBot, gridLayoutParam);
	}

	 
	private void showUserActionsChooser() {
		
		new DialogActionChooser(this);
	}
	
	public void playAction(List<ActionsEnum> actions) {
			
		// Play actions until the last one
		if (numberActionPlayed < actions.size()) {
			
			actionsToPerform = actions;
			switch (actionsToPerform.get(numberActionPlayed)) {
			
				case ACTION_MOVE_UP :
					moveBotUp();
					break;
				case ACTION_MOVE_DOWN :
					moveBotDown();
					break;	
				case ACTION_TURN_LEFT :
					turnBotLeft();
					break;	
				case ACTION_TURN_RIGHT :
					turnBotRight();
					break;		
			}
			
			numberActionPlayed++;
		}
	}
	
	private void moveBotUp() {
		
		boolean canMove = true;
		
		int gridHeigth = gridLayout.getRowCount();
		int gridwidth = gridLayout.getColumnCount();
		
		TranslateAnimation animation = null;
		if (botOrientation == OrientationEnum.NORTH) {
			if (playersBotPosY == 0) {
				animation = new TranslateAnimation(0, 0, 0, 0);
				canMove = false;
			} else {
				animation = new TranslateAnimation(0, 0, 0, -gridHeigth);
			}
		} else if (botOrientation == OrientationEnum.EAST) {
			if (playersBotPosX == gridwidth-1) {
				animation = new TranslateAnimation(0, 0, 0, 0);
				canMove = false;
			} else {
				animation = new TranslateAnimation(0, gridHeigth, 0, 0);
			}
		} else if (botOrientation == OrientationEnum.SOUTH) {
			if (playersBotPosY == gridHeigth-1) {
				animation = new TranslateAnimation(0, 0, 0, 0);
				canMove = false;
			} else {
				animation = new TranslateAnimation(0, 0, 0, gridHeigth);
			}
		} else {
			if (playersBotPosX == 0) {
				animation = new TranslateAnimation(0, 0, 0, 0);
				canMove = false;
			} else {
				animation = new TranslateAnimation(0, -gridHeigth, 0, 0);
			}
		}
		
		animation.setDuration(Constants.BOT_MOVING_SPEED);
		animation.setFillAfter(false);
		animation.setAnimationListener(new BotMoveUpAnimationListener(canMove));
		playersBot.startAnimation(animation);
	}
	
	private void moveBotDown() {
		
		boolean canMove = true;
		
		int gridHeigth = gridLayout.getRowCount();
		int gridwidth = gridLayout.getColumnCount();
		
		TranslateAnimation animation;
		if (botOrientation == OrientationEnum.NORTH) {
			if (playersBotPosY == gridHeigth-1) {
				animation = new TranslateAnimation(0, 0, 0, 0);
				canMove = false;
			} else {
				animation = new TranslateAnimation(0, 0, 0, gridHeigth);
			}
		} else if (botOrientation == OrientationEnum.EAST) {
			if (playersBotPosX == 0) {
				animation = new TranslateAnimation(0, 0, 0, 0);
				canMove = false;
			} else {
				animation = new TranslateAnimation(0, -gridHeigth, 0, 0);
			}
		} else if (botOrientation == OrientationEnum.SOUTH) {
			if (playersBotPosY == 0) {
				animation = new TranslateAnimation(0, 0, 0, 0);
				canMove = false;
			} else {
				animation = new TranslateAnimation(0, 0, 0, -gridHeigth);
			}
		} else {
			if (playersBotPosX == gridwidth-1) {
				animation = new TranslateAnimation(0, 0, 0, 0);
				canMove = false;
			} else {
				animation = new TranslateAnimation(0, gridHeigth, 0, 0);
			}
		}
		animation.setDuration(Constants.BOT_MOVING_SPEED);
		animation.setFillAfter(false);
		animation.setAnimationListener(new BotMoveDownAnimationListener(canMove));
		playersBot.startAnimation(animation);
	}
	 
	private void turnBotLeft() {
		
		View v = gridLayout.getChildAt(0);
		int halfSize = (v.getBottom()-v.getTop())/2;
		
		RotateAnimation animation = new RotateAnimation(0, -ANIMATION_BOT_ROTATION_ONE_QUARTER, halfSize, halfSize);
		animation.setDuration(Constants.BOT_MOVING_SPEED);
		animation.setFillAfter(false);
		animation.setAnimationListener(new BotTurnLeftAnimationListener());
		playersBot.startAnimation(animation);
		botOrientation = botOrientation.getOrientationAfterTurn(ActionsEnum.ACTION_TURN_LEFT);
	}
	
	private void turnBotRight() {
		
		View v = gridLayout.getChildAt(0);
		int halfSize = (v.getBottom()-v.getTop())/2;
		
		RotateAnimation animation = new RotateAnimation(0, ANIMATION_BOT_ROTATION_ONE_QUARTER, halfSize, halfSize);
		animation.setDuration(Constants.BOT_MOVING_SPEED);
		animation.setFillAfter(false);
		animation.setAnimationListener(new BotTurnRightAnimationListener());
		playersBot.startAnimation(animation);
		botOrientation = botOrientation.getOrientationAfterTurn(ActionsEnum.ACTION_TURN_RIGHT);
	}
	
	
	private class BotMoveUpAnimationListener implements AnimationListener {

		private boolean canMove;
		
		public BotMoveUpAnimationListener(final boolean canMove) {
			this.canMove = canMove;
		}
		
	    @Override
	    public void onAnimationEnd(Animation animation) {
	    	
	    	playersBot.clearAnimation();
		
		    if (canMove) {
		    	Spec colspecs;
		    	Spec rowspecs;
				if (botOrientation == OrientationEnum.NORTH) {
					colspecs = GridLayout.spec(playersBotPosX);
					rowspecs = GridLayout.spec(--playersBotPosY); 
				} else if (botOrientation == OrientationEnum.EAST) {
					colspecs = GridLayout.spec(++playersBotPosX);
					rowspecs = GridLayout.spec(playersBotPosY); 
				} else if (botOrientation == OrientationEnum.SOUTH) {
					colspecs = GridLayout.spec(playersBotPosX);
					rowspecs = GridLayout.spec(++playersBotPosY);
				} else {
					colspecs = GridLayout.spec(--playersBotPosX);
					rowspecs = GridLayout.spec(playersBotPosY);
				}
	
		    	GridLayout.LayoutParams lp = new GridLayout.LayoutParams(rowspecs, colspecs);
		        playersBot.setLayoutParams(lp);
	    	}
	        
	        playAction(actionsToPerform);
	    }

	    @Override
	    public void onAnimationRepeat(Animation animation) {
	    }

	    @Override
	    public void onAnimationStart(Animation animation) {
	    }
	}

	private class BotMoveDownAnimationListener implements AnimationListener {

		private boolean canMove;
		
		public BotMoveDownAnimationListener(final boolean canMove) {
			this.canMove = canMove;
		}
		
	    @Override
	    public void onAnimationEnd(Animation animation) {
	    	playersBot.clearAnimation();

	    	if (canMove) {
		    	Spec colspecs;
		    	Spec rowspecs;
				if (botOrientation == OrientationEnum.NORTH) {
					colspecs = GridLayout.spec(playersBotPosX);
					rowspecs = GridLayout.spec(++playersBotPosY); 
				} else if (botOrientation == OrientationEnum.EAST) {
					colspecs = GridLayout.spec(--playersBotPosX);
					rowspecs = GridLayout.spec(playersBotPosY); 
				} else if (botOrientation == OrientationEnum.SOUTH) {
					colspecs = GridLayout.spec(playersBotPosX);
					rowspecs = GridLayout.spec(--playersBotPosY);
				} else {
					colspecs = GridLayout.spec(++playersBotPosX);
					rowspecs = GridLayout.spec(playersBotPosY);
				}
				
		    	GridLayout.LayoutParams lp = new GridLayout.LayoutParams(rowspecs, colspecs);
		        playersBot.setLayoutParams(lp);
	    	}
	        
	        playAction(actionsToPerform);
	    }

	    @Override
	    public void onAnimationRepeat(Animation animation) {
	    }

	    @Override
	    public void onAnimationStart(Animation animation) {
	    }
	}
	
	private class BotTurnLeftAnimationListener implements AnimationListener {

	    @Override
	    public void onAnimationEnd(Animation animation) {
	    	playersBot.clearAnimation();
	    	
	    	View v = gridLayout.getChildAt(0);
			int halfSize = (v.getBottom()-v.getTop())/2;
			
	    	Matrix matrix=new Matrix();
	    	playersBot.setScaleType(ScaleType.MATRIX); 
	    	matrix.postRotate((float) ANIMATION_BOT_ROTATION_ONE_QUARTER*botOrientation.getVal(), halfSize, halfSize);
	    	playersBot.setImageMatrix(matrix);
	    	
	        playAction(actionsToPerform);
	    }

	    @Override
	    public void onAnimationRepeat(Animation animation) {
	    }

	    @Override
	    public void onAnimationStart(Animation animation) {
	    }
	}
	
	private class BotTurnRightAnimationListener implements AnimationListener {

	    @Override
	    public void onAnimationEnd(Animation animation) {
	    	playersBot.clearAnimation();
	    	
	    	View v = gridLayout.getChildAt(0);
			int halfSize = (v.getBottom()-v.getTop())/2;
			
	    	Matrix matrix=new Matrix();
	    	playersBot.setScaleType(ScaleType.MATRIX); 
	    	matrix.postRotate((float) ANIMATION_BOT_ROTATION_ONE_QUARTER*botOrientation.getVal(), halfSize, halfSize);
	    	playersBot.setImageMatrix(matrix);
	    	
	        playAction(actionsToPerform);
	    }

	    @Override
	    public void onAnimationRepeat(Animation animation) {
	    }

	    @Override
	    public void onAnimationStart(Animation animation) {
	    }
	}
}
