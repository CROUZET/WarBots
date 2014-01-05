package cat.can.read.warbots;

import java.util.List;

import cat.can.read.warbots.dialog.actionChooser.ActionChooserEnum;
import cat.can.read.warbots.dialog.actionChooser.DialogActionChooser;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.GridLayout;
import android.widget.GridLayout.Spec;
import android.widget.ImageView;

public class GameboardActivity extends Activity {

	private int gameboardSize;
	
	private GridLayout gridLayout;

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

	 
	private void showUserActionsChooser() {
		
		new DialogActionChooser(this);
	}
	
	public void playAction(List<ActionChooserEnum> actions) {
		
			for (ActionChooserEnum action : actions) {
			
				
				
				System.out.println(action.name());
			}
		
		
	}
	
	

	
	 
	


		
		

	
	
	
	
	
}
