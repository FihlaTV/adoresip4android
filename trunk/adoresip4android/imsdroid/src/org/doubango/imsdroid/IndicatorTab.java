package org.doubango.imsdroid;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.util.StateSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;



public class IndicatorTab extends LinearLayout {

	
	private static final String THIS_FILE = "IcTAB";
	private Context context;
	private ImageView icon;
	private TextView label;
	private String labelResource;
	private int iconResource;
	private int unselectedIconResource;

	public IndicatorTab(Context aContext, AttributeSet attrs) {
		super(aContext, attrs);
		context = aContext;
		LayoutInflater inflater = LayoutInflater.from(context);
		inflater.inflate(R.layout.tabnames, this, true);
		setFocusable(true);


		icon = (ImageView) findViewById(R.id.photo);
		label = (TextView) findViewById(R.id.name);
		
	}
	
	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		setResources(labelResource, iconResource, unselectedIconResource);
		
	}
	
	@Override
	protected void drawableStateChanged() {
		if(icon != null) {
			int[] state = getDrawableState();
			if(StateSet.stateSetMatches(View.SELECTED_STATE_SET, state)) {
				icon.setImageResource(iconResource);
			}else {
				icon.setImageResource(unselectedIconResource);
			}
		}
		super.drawableStateChanged();
	}
	
	
	
	public void setResources(String aLabelResource, int aIconResource, int aUnselectedIconResource) {
		labelResource = aLabelResource;
		iconResource = aIconResource;
		unselectedIconResource = aUnselectedIconResource;
		if(label != null && icon != null) {
			label.setText(labelResource);
			icon.setImageResource(iconResource);
		}else {
			Log.e(THIS_FILE, "not found !!!");
		}
		drawableStateChanged();
	}
	
	
}
