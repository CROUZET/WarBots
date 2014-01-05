package cat.can.read.warbots;

import cat.can.read.warbots.R.drawable;
import android.os.Bundle;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.GridLayout;
import android.widget.GridLayout.Spec;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class GameboardActivity extends Activity {

	private int gameboardSize;
	
	private GridLayout gridLayout;
	
	private Dialog userActions;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		loadParameters();

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gameboard);
		
		// Init Gameboard
		initGameBoard();
		initRobotPosition();
		
		// Start Actions
		showUserActions();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.gameboard, menu);
		return true;
	}

	private void loadParameters() {
		
		Intent intent = getIntent();
		gameboardSize = Integer.parseInt(intent.getStringExtra(MainActivity.PARAM_GAMEBOARD_SIZE));
	}
	
	
	
	private void initGameBoard() {

		String mapOne = "121321231";
		
		int cpt = 0;
		for (char codeField : mapOne.toCharArray()) {
			
			int rowIndex=cpt/gameboardSize; //row index to which i add the button
			int columnIndex=cpt%gameboardSize; //cols index to which i add the button
			
			Spec rowspecs = GridLayout.spec(rowIndex, 1); 
			Spec colspecs = GridLayout.spec(columnIndex, 1);
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
		
		int posX = (gameboardSize/2);
		int posY = gameboardSize-1;
		
		ImageView image = new ImageView(this);
		image.setImageResource(R.drawable.robot);
		
		Spec rowspecs = GridLayout.spec(posY, 1); 
		Spec colspecs = GridLayout.spec(posX, 1);
		GridLayout.LayoutParams gridLayoutParam = new GridLayout.LayoutParams(rowspecs, colspecs);
		
		gridLayout.addView(image, gridLayoutParam);
	}

	 
	public void showUserActions() {
		userActions = new Dialog(this, R.style.lightbox_dialog);
		userActions.setContentView(R.layout.dialog_user_actions);
		
		// Bouton Up
		GridLayout gridUserMovments = (GridLayout)userActions.findViewById(R.id.grid_users_movments);
		ImageView btnUp = new ImageView(this);
		btnUp.setImageResource(R.drawable.pos_up);
		btnUp.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				addPreview(0);
			}
		});

		Spec rowspecs = GridLayout.spec(0, 1); 
		Spec colspecs = GridLayout.spec(0, 1);
		GridLayout.LayoutParams gridLayoutParam = new GridLayout.LayoutParams(rowspecs, colspecs);
		gridUserMovments.addView(btnUp, gridLayoutParam);
		
		userActions.show();
	}

	private void addPreview(int numAction) {
		
		ImageView preview = new ImageView(this);
		if (numAction == 0) {
			preview.setImageResource(R.drawable.pos_up);
		}
		
		LinearLayout actionsPreview = (LinearLayout) userActions.findViewById(R.id.user_actions);
		actionsPreview.addView(preview);
		
	}
	

	
	 
	
	public void hideUserActions() {
	    if (userActions != null)
	    	userActions.dismiss();
	 
	    userActions = null;
	}

		
		

	
	
	
	
	
}
