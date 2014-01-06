package cat.can.read.warbots.dialog.actionChooser;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import cat.can.read.warbots.GameboardActivity;
import cat.can.read.warbots.R;
import cat.can.read.warbots.constants.Constants;

public class DialogActionChooser {

	private static final Logger LOGGER = LoggerFactory.getLogger(DialogActionChooser.class);
	
	// -----------------------------------------------------------------------------------------------------------
	
	private Context context;
	
	private Dialog userActions;
	
	private List<ActionsEnum> actionChoosen = new ArrayList<ActionsEnum>();
	
	// -----------------------------------------------------------------------------------------------------------
	
	public DialogActionChooser(final Context context) {
		
		LOGGER.debug("making the user action chooser dialog");
		
		this.context = context;
		
		userActions = new Dialog(context, R.style.lightbox_dialog);
		userActions.setContentView(R.layout.dialog_user_actions);
		
		RelativeLayout gridUserMovments = (RelativeLayout)userActions.findViewById(R.id.relative_user_actions);
		
		addButtonUp(gridUserMovments);
		addButtonDown(gridUserMovments);
		addButtonLeft(gridUserMovments);
		addButtonRight(gridUserMovments);

		userActions.show();
	}

	// -----------------------------------------------------------------------------------------------------------
	
	private void addButtonUp(final ViewGroup layout) {

		ImageView button = new ImageView(context);
		button.setImageResource(R.drawable.pos_up);
		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				addPreview(ActionsEnum.ACTION_MOVE_UP);
			}
		});
		layout.addView(button, setParams(250, 400));
	}
	
	private void addButtonDown(final ViewGroup layout) {

		ImageView button = new ImageView(context);
		button.setImageResource(R.drawable.pos_down);
		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				addPreview(ActionsEnum.ACTION_MOVE_DOWN);
			}
		});
		layout.addView(button, setParams(250, 600));
	}
	
	private void addButtonLeft(final ViewGroup layout) {

		ImageView button = new ImageView(context);
		button.setImageResource(R.drawable.pos_left);
		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				addPreview(ActionsEnum.ACTION_TURN_LEFT);
			}
		});
		layout.addView(button, setParams(200, 500));
	}
	
	private void addButtonRight(final ViewGroup layout) {

		ImageView button = new ImageView(context);
		button.setImageResource(R.drawable.pos_right);
		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				addPreview(ActionsEnum.ACTION_TURN_RIGHT);
			}
		});
		layout.addView(button, setParams(300, 500));
	}
	
	private RelativeLayout.LayoutParams setParams(final int leftMargin, final int topMargin) {
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		params.topMargin = topMargin;
		params.leftMargin = leftMargin;
		return params;
	}
	
	private void addPreview(final ActionsEnum action) {
		
		ImageView preview = new ImageView(context);
		actionChoosen.add(action);
		switch (action) {
			case ACTION_MOVE_UP :
				preview.setImageResource(R.drawable.pos_up);
				break;
			case ACTION_MOVE_DOWN :
				preview.setImageResource(R.drawable.pos_down);
				break;	
			case ACTION_TURN_LEFT :
				preview.setImageResource(R.drawable.pos_left);
				break;
			case ACTION_TURN_RIGHT :
				preview.setImageResource(R.drawable.pos_right);
				break;
		}

		LinearLayout actionsPreview = (LinearLayout) userActions.findViewById(R.id.user_actions);
		actionsPreview.addView(preview);
		
		blockSelection(actionsPreview.getChildCount());
		
	}
	
	private void blockSelection(final int actionChoosen) {
		
		if (actionChoosen == Constants.MAX_ACTION_PER_TURN) {
			playAction();
	    	userActions.dismiss();
		}
	}
	
	private void playAction() {
		
		GameboardActivity gameBoard = (GameboardActivity) context;
		gameBoard.playAction(actionChoosen);
	}
}
