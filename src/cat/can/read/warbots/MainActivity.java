package cat.can.read.warbots;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {

	public final static String PARAM_GAMEBOARD_SIZE = "cat.can.read.warbots.GAMEBOARD_SIZE";

	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    public void launchGame(View view) {
    	
    	System.out.println("aaa");
    	
    	Intent intent = new Intent(this, GameboardActivity.class);
    	intent.putExtra(PARAM_GAMEBOARD_SIZE, "3");

    	startActivity(intent);

    	
    	
    	
    	
    }
    
}
