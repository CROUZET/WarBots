package cat.can.read.warbots;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.GridLayout;
import android.widget.GridLayout.Spec;
import android.widget.ImageView;
import cat.can.read.warbots.constants.Constants;
import cat.can.read.warbots.dialog.actionChooser.ActionChooserEnum;
import cat.can.read.warbots.dialog.actionChooser.DialogActionChooser;

public class GameboardActivity extends Activity {

	private static final int ANIMATION_BOT_TRANSLATION = 100;
	
	// -----------------------------------------------------------------------------------------------------------

	private int gameboardSize;
	
	private GridLayout gridLayout;

	private ImageView playersBot;
	
	private int playersBotPosX;
	
	private int playersBotPosY;
	
	private List<ActionChooserEnum> actionsToPerform;
	
	private int numberActionPlayed;
	
	
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
		gameboardSize = Integer.parseInt(intent.getStringExtra(MainActivity.PARAM_GAMEBOARD_SIZE));
	}
	
	private void initGameBoard() {

		String mapOne = "121323231";
		
		int cpt = 0;
		for (char codeField : mapOne.toCharArray()) {
			
			int posX=cpt%gameboardSize; //cols index to which i add the button
			int posY=cpt/gameboardSize; //row index to which i add the button
			
			Spec colspecs = GridLayout.spec(posX);
			Spec rowspecs = GridLayout.spec(posY); 
			GridLayout.LayoutParams gridLayoutParam = new GridLayout.LayoutParams(rowspecs, colspecs);
			
			
			ImageView image = new ImageView(this);
			if (codeField == '1') {
				image.setBackgroundResource(R.drawable.field_normal);
			} else if (codeField == '2') {
				image.setBackgroundResource(R.drawable.field_danger);
			} else {
				image.setBackgroundResource(R.drawable.field_block);
			}
			
			gridLayout = (GridLayout) findViewById(R.id.thegrid);
			gridLayout.addView(image, gridLayoutParam);

			cpt++;
		}
	}
		
	private void initRobotPosition() {

		
		playersBot = new ImageView(this);
		playersBot.setImageResource(R.drawable.robot);
		
		playersBotPosX = gameboardSize/2;
		playersBotPosY = gameboardSize-1;
		
		Spec colspecs = GridLayout.spec(playersBotPosX);
		Spec rowspecs = GridLayout.spec(playersBotPosY); 
		GridLayout.LayoutParams gridLayoutParam = new GridLayout.LayoutParams(rowspecs, colspecs);
		
		gridLayout.addView(playersBot, gridLayoutParam);
	}

	 
	private void showUserActionsChooser() {
		
		new DialogActionChooser(this);
	}
	
	public void playAction(List<ActionChooserEnum> actions) {
			
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
					// do
					break;	
				case ACTION_TURN_RIGHT :
					// do
					break;		
			}
			
			numberActionPlayed++;
		}
	}
	
	private void moveBotUp() {
		TranslateAnimation animation = new TranslateAnimation(0, 0, 0, -ANIMATION_BOT_TRANSLATION);
		animation.setDuration(Constants.BOT_MOVING_SPEED);
		animation.setFillAfter(false);
		animation.setAnimationListener(new BotMoveUpAnimationListener());
		playersBot.startAnimation(animation);
	}
	
	private void moveBotDown() {
		TranslateAnimation animation = new TranslateAnimation(0, 0, 0, ANIMATION_BOT_TRANSLATION);
		animation.setDuration(Constants.BOT_MOVING_SPEED);
		animation.setFillAfter(false);
		animation.setAnimationListener(new BotMoveDownAnimationListener());
		playersBot.startAnimation(animation);
	}
	 
	private class BotMoveUpAnimationListener implements AnimationListener {

	    @Override
	    public void onAnimationEnd(Animation animation) {
	    	playersBot.clearAnimation();
	
			Spec colspecs = GridLayout.spec(playersBotPosX);
			Spec rowspecs = GridLayout.spec(--playersBotPosY); 
	    	GridLayout.LayoutParams lp = new GridLayout.LayoutParams(rowspecs, colspecs);
	        playersBot.setLayoutParams(lp);
	        
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

	    @Override
	    public void onAnimationEnd(Animation animation) {
	    	playersBot.clearAnimation();

			Spec colspecs = GridLayout.spec(playersBotPosX);
			Spec rowspecs = GridLayout.spec(++playersBotPosY); 
	    	GridLayout.LayoutParams lp = new GridLayout.LayoutParams(rowspecs, colspecs);
	        playersBot.setLayoutParams(lp);
	        
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
