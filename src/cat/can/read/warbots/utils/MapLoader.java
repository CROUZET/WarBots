package cat.can.read.warbots.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cat.can.read.warbots.R;
import cat.can.read.warbots.core.enums.FloorEnum;

import android.content.Context;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.GridLayout.Spec;

public class MapLoader {

	private static final Logger LOGGER = LoggerFactory.getLogger(MapLoader.class);
	
	// -----------------------------------------------------------------------------------------------------------
	
	private static final String JSON_ATTR_STRUCT = "struct";
	
	private static final String JSON_ATTR_POS_X = "x";
	
	private static final String JSON_ATTR_POS_Y = "y";
	
	private static final String JSON_ATTR_FLOOR_TYPE = "type";
	
	// -----------------------------------------------------------------------------------------------------------

	private static JSONObject loadMapFlow(final Context context, final int map) throws IOException, JSONException {
		
		InputStream inputStream = context.getResources().openRawResource(map);
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

	    int ctr = inputStream.read();
	    while (ctr != -1) {
	        byteArrayOutputStream.write(ctr);
	        ctr = inputStream.read();
	    }
	    inputStream.close();
	
	    return new JSONObject(byteArrayOutputStream.toString());
	}
	
	public static void construct(final Context context, final int map, final GridLayout gridLayout) {
		
		try {
			JSONObject jObject = MapLoader.loadMapFlow(context, map);
			
		    JSONArray jArray = jObject.getJSONArray(JSON_ATTR_STRUCT);
		    for (int i=0; i<jArray.length(); i++) {
		    	
		    	// Params
		    	int posX = jArray.getJSONObject(i).getInt(JSON_ATTR_POS_X);
		    	int posY = jArray.getJSONObject(i).getInt(JSON_ATTR_POS_Y);
		    	FloorEnum floorType = FloorEnum.valueOf(jArray.getJSONObject(i).getString(JSON_ATTR_FLOOR_TYPE).toUpperCase());
		    
		    	// Position in the gridLayout
				Spec colspecs = GridLayout.spec(posX);
				Spec rowspecs = GridLayout.spec(posY); 
				GridLayout.LayoutParams gridLayoutParam = new GridLayout.LayoutParams(rowspecs, colspecs);
				
				// Set the floor image
				ImageView image = new ImageView(context);
				image.setBackgroundResource(floorType.getImage());
				
				gridLayout.addView(image, gridLayoutParam);
		    }
		    
		} catch (Exception e) {
			LOGGER.error("Map loading failure.", e);
		    e.printStackTrace();
		}
	}
}
