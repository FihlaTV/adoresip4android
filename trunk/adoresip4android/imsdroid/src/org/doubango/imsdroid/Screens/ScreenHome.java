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

import org.doubango.imsdroid.CustomDialog;
import org.doubango.imsdroid.Main;
import org.doubango.imsdroid.R;
import org.doubango.ngn.events.NgnEventArgs;
import org.doubango.ngn.events.NgnRegistrationEventArgs;
import org.doubango.ngn.services.INgnConfigurationService;
import org.doubango.ngn.services.INgnSipService;
import org.doubango.ngn.sip.NgnSipSession.ConnectionState;
import org.doubango.ngn.utils.NgnConfigurationEntry;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

//vijay3
public class ScreenHome extends BaseScreen {
	private static String TAG = ScreenHome.class.getCanonicalName();
	
	private static final int MENU_EXIT = 0;
	private static final int MENU_SETTINGS = 1;
		
	private GridView mGridView;
	private final INgnSipService mSipService;
	private BroadcastReceiver mSipBroadCastRecv;
	
	//Add by vijay
	public static EditText SipText;
    public static EditText UserText;
    public static EditText PassText;
    
    private SharedPreferences prefs;
    
    
    
	//vijay
    public static final String myprefs = "mySharedPreferences";
    private final INgnConfigurationService mConfigurationService;
    public boolean mComputeConfiguration;

    
    //vijay
    private Button loginButton;
    private Button clearButton;
    private CheckBox saveBox; 
    
    
	
	public ScreenHome() {
		super(SCREEN_TYPE.HOME_T, TAG);
		
		mSipService = getEngine().getSipService();
		mConfigurationService = getEngine().getConfigurationService();//vijay
	}
	public static void vijay() {
		// TODO Auto-generated method stub
		
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.screen_home);
		
		//vijay
		
			
		/**
		 * ScreenHomeAdapter
		 */
		class ScreenHomeAdapter extends BaseAdapter{
			static final int ALWAYS_VISIBLE_ITEMS_COUNT = 1;
			final ScreenHomeItem[] sItems =  new ScreenHomeItem[]{
				
				// always visible
			
	    		new ScreenHomeItem(R.drawable.sign_in_48, "Sign In", null),
	    		new ScreenHomeItem(R.drawable.black_qcif, "login", ScreenAbout.class),
	    		new ScreenHomeItem(R.drawable.black_qcif, "Exit/Quit", null),
	    		new ScreenHomeItem(R.drawable.black_qcif, "Options", ScreenSettings.class),
	    		new ScreenHomeItem(R.drawable.black_qcif, "About", ScreenAbout.class),
	    		// visible only if connected
	    		new ScreenHomeItem(R.drawable.black_qcif, "About", ScreenAbout.class),
	    		new ScreenHomeItem(R.drawable.black_qcif, "About", ScreenAbout.class),
	    		new ScreenHomeItem(R.drawable.black_qcif, "Address Book", ScreenTabContacts.class),
	    		new ScreenHomeItem(R.drawable.black_qcif, "History", ScreenTabHistory.class),
	    		new ScreenHomeItem(R.drawable.black_qcif, "Messages", ScreenTabMessages.class),
	    		new ScreenHomeItem(R.drawable.black_qcif, "login", ScreenAbout.class),
			};
			
			private final LayoutInflater mInflater;
			private final ScreenHome mBaseScreen;
			
			ScreenHomeAdapter(ScreenHome baseScreen){
				mInflater = LayoutInflater.from(baseScreen);
				mBaseScreen = baseScreen;
			}
			
			
			@Override
			public int getCount() {
				return mBaseScreen.mSipService.isRegistered() ? sItems.length : ALWAYS_VISIBLE_ITEMS_COUNT;
			}

			@Override
			public Object getItem(int position) {
				if(position==2);//{ScreenHome.vijay();}
				return sItems[position];
			}

			@Override
			public long getItemId(int position) {
				return position;
			}

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				View view = convertView;
				final ScreenHomeItem item = (ScreenHomeItem)getItem(position);
								
				if(item == null){
					return null;
				}

				if (view == null) {
					view = mInflater.inflate(R.layout.screen_home_item, null);
				}
				
				if(position == ScreenHomeItem.ITEM_SIGNIN_SIGNOUT_POS){
					
			 				
					//mScreenService.show(ScreenAbout.class);
					if(mBaseScreen.mSipService.getRegistrationState() == ConnectionState.CONNECTING || mBaseScreen.mSipService.getRegistrationState() == ConnectionState.TERMINATING){
						//mScreenService.show(ScreenAbout.class);
						
						//refresh();
						//((TextView) view.findViewById(R.id.screen_home_item_text)).setText("Cancel");
						//((ImageView) view .findViewById(R.id.screen_home_item_icon)).setImageResource(R.drawable.sign_inprogress_48);
					}
					else{
						if(mBaseScreen.mSipService.isRegistered()){
							//refresh();
							//mScreenService.show(ScreenAbout.class);
							//((TextView) view.findViewById(R.id.screen_home_item_text)).setText("Sign Out");
							//((ImageView) view .findViewById(R.id.screen_home_item_icon)).setImageResource(R.drawable.sign_out_48);
						}
						else{
							//((TextView) view.findViewById(R.id.screen_home_item_text)).setText("Sign In");
						//	((ImageView) view .findViewById(R.id.screen_home_item_icon)).setImageResource(R.drawable.sign_in_48);
						}
					}
				}
				else{				
					((TextView) view.findViewById(R.id.screen_home_item_text)).setText(item.mText);
					((ImageView) view .findViewById(R.id.screen_home_item_icon)).setImageResource(item.mIconResId);
				}
				
					 return view;
			}
				
		
		}
		//vijay.
		SipText=(EditText)this.findViewById(R.id.SipText);
	    UserText=(EditText)this.findViewById(R.id.UserText);
	    PassText=(EditText)this.findViewById(R.id.passText);
	    
	    this.loginButton=(Button)this.findViewById(R.id.login_button);
	    this.clearButton=(Button)this.findViewById(R.id.clear_button);
	    this.saveBox=(CheckBox)this.findViewById(R.id.save_bt); 
	    
	    loadpreference();//vijay.
	    
	    clearButton.setOnClickListener(new OnClickListener() {
	       @Override
		
	    public void onClick(View v) {
	    	   SipText.setText("");
	           UserText.setText("");
	           PassText.setText("");
	           SipText.requestFocus();
	    			
	    	   }
	        
	        }); 
	    
	        
	    
		   
        
		mGridView = (GridView) findViewById(R.id.screen_home_gridview);
		
		mGridView.clearFocus();
		mGridView.setFocusableInTouchMode(false);
		
		mGridView.setAdapter(new ScreenHomeAdapter(this));
		mGridView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				final ScreenHomeItem item = (ScreenHomeItem)parent.getItemAtPosition(position);
				if (item != null) {
					if(position == ScreenHomeItem.ITEM_SIGNIN_SIGNOUT_POS){
						if(validate()){
						if (saveBox.isChecked()) {
		 					saveconfgvalues();
		 		            Savepreference();
		 	               }
						//vijay
						/*if(mSipService.isRegistered()){
							
							mScreenService.show(ScreenAbout.class);
						
						}*/
						saveconfgvalues();
						if(mSipService.getRegistrationState() == ConnectionState.CONNECTING || mSipService.getRegistrationState() == ConnectionState.TERMINATING)
						{
							mSipService.stopStack();
							
						}
						else if(mSipService.isRegistered()){
							mSipService.unRegister();
							mSipService.register(ScreenHome.this);
							//mScreenService.show(ScreenAbout.class);
						
						}
						
						else if(mSipService.isRegistered()==false)
						{
							mSipService.register(ScreenHome.this);
							mScreenService.show(ScreenAbout.class);
							
						}
						
						
						//ytf7
						/*if(mSipService.isRegistered())
						{
							mScreenService.show(ScreenAbout.class);
							
						}
						else
						{
							mSipService.
						}*/
										
					}
					else if(position == ScreenHomeItem.ITEM_EXIT_POS){
						CustomDialog.show(
								ScreenHome.this,
								R.drawable.exit_48,
								null,
								"Are you sure you want to exit?",
								"Yes",
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog, int which) {
										((Main)(getEngine().getMainActivity())).exit();
									}
								}, "No",
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog, int which) {
										dialog.cancel();
									}
								});
					}
					else{					
						mScreenService.show(ScreenHome.class);
					}
				}}
			}

			//vijay
			public boolean validate() {
				
		        int a, b, c;
				String sip = SipText.getText().toString().trim();
				String user = UserText.getText().toString().trim();
				String pass = PassText.getText().toString().trim();
				
				a = sip.length();
				b = user.length();
				c = pass.length();
				
				if (a == 0) {
					new AlertDialog.Builder(ScreenHome.this).setTitle("Communicator Alert")
				                   .setMessage("SIP IP Address Field Can not be blank")
								   .setNeutralButton("OK",
						new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog,int which){
						// TODO Auto-generated method stub
						//vijay.
												}
											}).show();
							
					SipText.requestFocus();
					mScreenService.show(ScreenAbout.class);
					return false;

					}

				if (b == 0) {
					new AlertDialog.Builder(ScreenHome.this).setTitle("Communicator Alert")
             						.setMessage("User Name field Can not be blank")
									.setNeutralButton("OK",
									
						new DialogInterface.OnClickListener() {

				public void onClick(DialogInterface dialog,int which) {
					// TODO Auto-generated method stub

												}
					}).show();
					
					UserText.requestFocus();
					return false;
					}

				if (c == 0) {
					new AlertDialog.Builder(ScreenHome.this).setTitle("Communicator Alert")
					    			.setMessage("Password Field Can not be blank")
									.setNeutralButton("OK",
						new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog,int which) {
					// TODO Auto-generated method stub

												}
					}).show();
					
					PassText.requestFocus();
					return false;
					}
				mScreenService.show(ScreenAbout.class);
					// TODO Auto-generated method stub
					return true;
					
	        
		
		
		}

			private void Savepreference() {
				// TODO Auto-generated method stub
				int mode = Context.MODE_PRIVATE;
				SharedPreferences mySharedPreferences = getSharedPreferences(myprefs,
								mode);
				SharedPreferences.Editor editor = mySharedPreferences.edit();
						//SharedPreferences mySharedPreferences1 = getSharedPreferences(myprefs1,
						//		mode);
						//SharedPreferences.Editor editor1 = mySharedPreferences1.edit();
						//SharedPreferences mySharedPreferences2 = getSharedPreferences(myprefs2,
						//		mode);
						//SharedPreferences.Editor editor2 = mySharedPreferences2.edit();

				String sip = SipText.getText().toString().trim();
				String user = UserText.getText().toString().trim();
				String pass = PassText.getText().toString().trim();

				editor.putString("sipip", sip);
				editor.putString("userid", user);
				editor.putString("password", pass);
				//editor1.putString("userid", user);
				//editor2.putString("password", pass);
				editor.commit();
				//editor1.commit();//sharad.
				//editor2.commit();
				System.out.println("now saving the user name"+sip);	
			}

			private void saveconfgvalues() {
				// TODO Auto-generated method stub
				String  stringsip = SipText.getText().toString().trim();
				String stringuser = UserText.getText().toString().trim();
				String stringpass = PassText.getText().toString().trim();
				
				String username1 = ScreenHome.UserText.getText().toString().trim();
				String server1 = ScreenHome.SipText.getText().toString().trim();
				String password1 = ScreenHome.PassText.getText().toString().trim();
				
				String atTheRate="@";
				String simpleSip="sip:";
				String appendRate=atTheRate.concat(server1).trim();
				String appendId=username1.concat(appendRate).trim();
				String appendSip=simpleSip.concat(appendId).trim();
				String appendRelam=simpleSip.concat(stringsip);
						    
				//vijay. configurataion values		    
				mConfigurationService.putString(NgnConfigurationEntry.IDENTITY_DISPLAY_NAME, 
									  stringuser);//mEtDisplayName.getText().toString().trim());
				
				mConfigurationService.putString(NgnConfigurationEntry.IDENTITY_IMPU, 
				                      appendSip);//mEtIMPU.getText().toString().trim());
				
				mConfigurationService.putString(NgnConfigurationEntry.IDENTITY_IMPI, 
									  stringuser);//mEtIMPI.getText().toString().trim());
				
				mConfigurationService.putString(NgnConfigurationEntry.IDENTITY_PASSWORD, 
									  stringpass);//mEtPassword.getText().toString().trim());
				
				mConfigurationService.putString(NgnConfigurationEntry.NETWORK_REALM, 
									  stringsip);//mEtRealm.getText().toString().trim());
				
				mConfigurationService.putBoolean(NgnConfigurationEntry.NETWORK_USE_EARLY_IMS, 
								      true);//mCbEarlyIMS.isChecked());
				
				mConfigurationService.putString(NgnConfigurationEntry.NETWORK_PCSCF_HOST, 
									  stringsip);//mEtProxyHost.getText().toString().trim());
							
							// Compute
						if(!mConfigurationService.commit()){
							Log.e(TAG, "Failed to Commit() configuration");
							
							
							//super.mComputeConfiguration = false;
						   }
						//super.saveconfgvalues();
						
				
			}
		});
		
		mSipBroadCastRecv = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				final String action = intent.getAction();
				
				// Registration Event
				if(NgnRegistrationEventArgs.ACTION_REGISTRATION_EVENT.equals(action)){
					NgnRegistrationEventArgs args = intent.getParcelableExtra(NgnEventArgs.EXTRA_EMBEDDED);
					if(args == null){
						Log.e(TAG, "Invalid event args");
						return;
					}
					switch(args.getEventType()){
					
					    case UNREGISTRATION_OK:
					    case UNREGISTRATION_INPROGRESS:
					    	mScreenService.show(ScreenHome.class);
					    	break;
					    case REGISTRATION_OK:
						case REGISTRATION_NOK:
						
						case REGISTRATION_INPROGRESS:
						
						case UNREGISTRATION_NOK:
						default:
							mScreenService.show(ScreenAbout.class);//((ScreenHomeAdapter)mGridView.getAdapter()).refresh();
							break;
					}
				}
			}
		};
		
		final IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(NgnRegistrationEventArgs.ACTION_REGISTRATION_EVENT);
	    registerReceiver(mSipBroadCastRecv, intentFilter);
	}

	private void loadpreference() {
		// TODO Auto-generated method stub
		int mode = Context.MODE_PRIVATE;
		SharedPreferences mySharedPreferences = getSharedPreferences(myprefs,
				mode);
				
		String sipid1 = mySharedPreferences.getString(
				"sipip", "");
		
		String userid1 = mySharedPreferences.getString(
				"userid", "");
		
		String passid1 = mySharedPreferences.getString(
				"password", "");

		
		
		SipText.setText(sipid1);
		UserText.setText(userid1);//sharad.
		PassText.setText(passid1);
		System.out.println("now loading the sipip"+sipid1);
		
	}

	@Override
	protected void onDestroy() {
       if(mSipBroadCastRecv != null){
    	   unregisterReceiver(mSipBroadCastRecv);
    	   mSipBroadCastRecv = null;
       }
        
       super.onDestroy();
	}
	
	
	@Override
	public boolean hasMenu() {
		return false;	      //return true;
	}
	
	
	@Override
	public boolean createOptionsMenu(Menu menu) {
		menu.add(0, ScreenHome.MENU_SETTINGS, 0, "Settings");
		/*MenuItem itemExit =*/ menu.add(0, ScreenHome.MENU_EXIT, 0, "Exit");
		
		return true;
	}
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()){
			/*case ScreenHome.MENU_EXIT:
				((Main)getEngine().getMainActivity()).exit();
				break;*/
			case ScreenHome.MENU_SETTINGS:
				mScreenService.show(ScreenQoS.class);
				break;
			
				
		}
		return true;
	}
	
	
	/**
	 * ScreenHomeItem
	 */
	static class ScreenHomeItem {
		static final int ITEM_SIGNIN_SIGNOUT_POS = 0;
		static final int ITEM_EXIT_POS = 6;
		//static final int ITEM_login_POS = 2;
		final int mIconResId;
		final String mText;
		final Class<? extends Activity> mClass;

		private ScreenHomeItem(int iconResId, String text, Class<? extends Activity> _class) {
			mIconResId = iconResId;
			mText = text;
			mClass = _class;
		}
	}
	
	
	/**
	 * ScreenHomeAdapter
	 */
	static class ScreenHomeAdapter extends BaseAdapter{
		static final int ALWAYS_VISIBLE_ITEMS_COUNT = 1;
		static final ScreenHomeItem[] sItems =  new ScreenHomeItem[]{
			
			// always visible
		
    		new ScreenHomeItem(R.drawable.vijay123, "Sign In", null),
    		//new ScreenHomeItem(R.drawable.black_qcif, "login", ScreenHome.class),
    		//new ScreenHomeItem(R.drawable.black_qcif, "Exit/Quit", null),
    	//	new ScreenHomeItem(R.drawable.black_qcif, "Options", ScreenHome.class),
    	//	new ScreenHomeItem(R.drawable.black_qcif, "About", ScreenHome.class),
    		// visible only if connected
    	//	new ScreenHomeItem(R.drawable.black_qcif, "About", ScreenHome.class),
    	//	new ScreenHomeItem(R.drawable.black_qcif, "About", ScreenHome.class),
    	//	new ScreenHomeItem(R.drawable.black_qcif, "Address Book", ScreenHome.class),
    	//	new ScreenHomeItem(R.drawable.black_qcif, "History", ScreenHome.class),
    	//	new ScreenHomeItem(R.drawable.black_qcif, "Messages", ScreenHome.class),
    	//	new ScreenHomeItem(R.drawable.black_qcif, "login", ScreenHome.class),
		};
		
		private final LayoutInflater mInflater;
		private final ScreenHome mBaseScreen;
		
		ScreenHomeAdapter(ScreenHome baseScreen){
			mInflater = LayoutInflater.from(baseScreen);
			mBaseScreen = baseScreen;
		}
		
			
		@Override
		public int getCount() {
			return mBaseScreen.mSipService.isRegistered() ? sItems.length : ALWAYS_VISIBLE_ITEMS_COUNT;
		}

		@Override
		public Object getItem(int position) {
			if(position==1);//{ScreenHome.vijay();}
			
			return sItems[position];
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = convertView;
			final ScreenHomeItem item = (ScreenHomeItem)getItem(position);
			
			/*if(position == ScreenHomeItem.ITEM_login_POS){
				Log.e(TAG, " I m here????????????????????????????");
				
			}*/
			
			if(item == null){
				return null;
			}

			if (view == null) {
				view = mInflater.inflate(R.layout.screen_home_item, null);
			}
			
			if(position == ScreenHomeItem.ITEM_SIGNIN_SIGNOUT_POS){
				
				if(mBaseScreen.mSipService.getRegistrationState() == ConnectionState.CONNECTING || mBaseScreen.mSipService.getRegistrationState() == ConnectionState.TERMINATING){
					
					
					((TextView) view.findViewById(R.id.screen_home_item_text)).setText("Cancel");
					((ImageView) view .findViewById(R.id.screen_home_item_icon)).setImageResource(R.drawable.sign_inprogress_48);
				}
				else{
					if(mBaseScreen.mSipService.isRegistered()){
						((TextView) view.findViewById(R.id.screen_home_item_text)).setText("Sign Out");
						((ImageView) view .findViewById(R.id.screen_home_item_icon)).setImageResource(R.drawable.sign_out_48);
					}
					else{mBaseScreen.mScreenService.show(ScreenAbout.class);
						((TextView) view.findViewById(R.id.screen_home_item_text)).setText("Sign In");
						((ImageView) view .findViewById(R.id.screen_home_item_icon)).setImageResource(R.drawable.sign_in_48);
					}
				}
			}
			else{				
				((TextView) view.findViewById(R.id.screen_home_item_text)).setText(item.mText);
				((ImageView) view .findViewById(R.id.screen_home_item_icon)).setImageResource(item.mIconResId);
			}
			
			return view;
		}
		
	}
	
	public boolean back(){
		//finish();
		Log.e("vijay","Main class");
		return true;
	}

	
}
