/* Copyright (C) 2010-2011, Mamadou Diop.
*  Copyright (C) 2011, Doubango Telecom.
*
* Contact: Mamadou Diop <diopmamadou(at)doubango(dot)org>
*	
* This file is part of imsdroid Project (http://code.google.com/p/imsdroid)
*
* imsdroid is free software: you can redistribute it and/or modify it under the terms of 
* the GNU General Public License as published by the Free Software Foundation, either version 3 
* of the License, or (at your option) any later version.
*	
* imsdroid is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
* without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  
* See the GNU General Public License for more details.
*	
* You should have received a copy of the GNU General Public License along 
* with this program; if not, write to the Free Software Foundation, Inc., 
* 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
*/
package org.doubango.imsdroid.Screens;

import java.lang.reflect.Method;

import org.doubango.imsdroid.IMSDroid;
import org.doubango.imsdroid.IndicatorTab;
import org.doubango.imsdroid.R;
import org.doubango.imsdroid.Screens.BaseScreen.SCREEN_TYPE;
import org.doubango.imsdroid.Services.IScreenService;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.TabHost.TabSpec;
import android.app.TabActivity;
								//Add by Vijay for tabhost
public class ScreenAbout extends TabActivity {
	//Add by vijay
	private static final String TAG = ScreenAbout.class.getCanonicalName();
	private static final String THIS_FILE = "Tab HOME";
	
	public static final int ACCOUNTS_MENU = Menu.FIRST + 1;
	public static final int PARAMS_MENU = Menu.FIRST + 2;
	public static final int CLOSE_MENU = Menu.FIRST + 3;

	//vijay
	private Intent dialerIntent;
	private Intent calllogsIntent;
	private Intent phoneIntent;
	
	
	/*public ScreenAbout() {
		super(SCREEN_TYPE.ABOUT_T, TAG);
	}*/

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_about);
        Log.d(THIS_FILE, "On Create TabHome");
        
        //Add by vijay
        CharSequence abc = getIntent().getCharSequenceExtra("org.doubango.imsdroid");
        System.out.println("NOW IN TABHOME"+abc);
        String name = "org.doubango.imsdroid";
        
        dialerIntent = new Intent(this, ScreenTabDialer.class).putExtra(name, abc);
        calllogsIntent = new Intent(this, ScreenTabHistory.class);
        phoneIntent = new Intent(this, ScreenTabContacts.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        
        addTab("tab1", "Dial Pad", R.drawable.ic_tab_selected_dialer, R.drawable.ic_tab_unselected_dialer, dialerIntent);
        addTab("tab2", "Call log", R.drawable.ic_tab_selected_recent, R.drawable.ic_tab_unselected_recent, calllogsIntent);
        addTab("tab3", "Phone Book", R.drawable.ic_tab_selected_recent, R.drawable.ic_tab_unselected_recent, phoneIntent);
        
        Bundle bundle = savedInstanceState;
        
        
    /*    TextView textView = (TextView)this.findViewById(R.id.screen_about_textView_copyright);
        String copyright = this.getString(R.string.copyright);
		textView.setText(String.format(copyright,
				IMSDroid.getVersionName(), this.getString(R.string.doubango_revision)));*/
	}
	
	//Add by vijay refrenced by IndicatorTab class
	private void addTab(String tag, String label, int icon, int ficon, Intent content) {
    	TabHost tabHost = getTabHost();
		
		//TabHost tabHost="1";
    	
		TabSpec tabspecDialer = tabHost.newTabSpec(tag).setContent(content);
		
		boolean fails = true;
	if(true) {
			IndicatorTab icTab = new IndicatorTab(this, null);
		 	icTab.setResources(label, icon, ficon);
		 	try {
				Method method = tabspecDialer.getClass().getDeclaredMethod("setIndicator", View.class);
				method.invoke(tabspecDialer, icTab);
				fails = false;
			    } catch (Exception e) {
				Log.d(THIS_FILE, "We are probably on 1.5 : use standard simple tabs");
			} 
		 	
		}
		if(fails){
			// Fallback to old style icons
		    tabspecDialer.setIndicator(label, getResources().getDrawable(icon));
		}
		
		tabHost.addTab(tabspecDialer);
    }
	
protected void onPause() {
    	
    	Log.d(THIS_FILE, "On Pause TabHOME");
    	super.onPause();
    	
    }
    
    @Override
    protected void onResume() {
    	//nsettings.updateNetwork();
		Log.d(THIS_FILE, "On Resume TabHOME");
    	super.onResume();
    }
    
    @Override
	protected void onDestroy() {
    	
		super.onDestroy();
		Log.d(THIS_FILE, "---DESTROY TAB HOME END---");
	}
    
    //Add by vijay for "setting options"
    private void populateMenu(Menu menu) {
    	menu.add(Menu.NONE, ACCOUNTS_MENU, Menu.NONE, "ReLogin").setIcon(
				R.drawable.ic_menu_accounts);
		menu.add(Menu.NONE, PARAMS_MENU, Menu.NONE, "Settings").setIcon(
				R.drawable.setting);
		menu.add(Menu.NONE, CLOSE_MENU, Menu.NONE, "Exit").setIcon(
				R.drawable.exit);
	}
    
 
	public boolean hasMenu() {
		return true;
	}
    
    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
    	menu.add(Menu.NONE, ACCOUNTS_MENU, Menu.NONE, "ReLogin").setIcon(
				R.drawable.ic_menu_accounts);
		menu.add(Menu.NONE, PARAMS_MENU, Menu.NONE, "Settings").setIcon(
				R.drawable.setting);
		menu.add(Menu.NONE, CLOSE_MENU, Menu.NONE, "Exit").setIcon(
				R.drawable.exit);
		
    	//populateMenu(menu);
		return super.onCreateOptionsMenu(menu);
	}
    
    //@Override
   
	/*public boolean hasBack(){
		return true;
	}
	

	public boolean back(){
	//	mScreenService.show(ScreenQoS.class);
		Intent screenIntent = new Intent(ScreenAbout.this, ScreenHome.class);
		//screenIntent.setFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
screenIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
startActivity(screenIntent);
		finish();
		//mScreenService.show(ScreenHome.class);
		//boolean ret = mScreenService.show(ScreenHome.class);
//		if(ret){
//			mScreenService.destroy(getId());
//		}
		return true;
	}
	*/
}
