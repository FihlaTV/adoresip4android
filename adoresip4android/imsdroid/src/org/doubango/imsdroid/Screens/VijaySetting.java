package org.doubango.imsdroid.Screens;
/*package org.doubango.imsdroid.Screens;


import java.util.ArrayList;

import org.doubango.imsdroid.IMSDroid;
import org.doubango.imsdroid.R;
import org.doubango.ngn.services.INgnConfigurationService;
import org.doubango.ngn.services.INgnSipService;





import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.AdapterView.OnItemClickListener;

public class VijaySetting extends ListActivity   {
	//private ListView gnmlistview;
	//private ArrayList mListItem;
	//private final INgnSipService mSipService;
	//private final INgnConfigurationService mConfigurationService;
   // private  Configuration configuration;
	//private  INetworkService networkService;
	private LinearLayout layoutgeneral,layoutnetwork,layoutmedia,layoutmiscellaneous; 
	static final String[] COUNTRIES = new String[] {
	    "Gerenal","Network","Media"};
	private static final String THIS_FILE = "NSettings";
	private  boolean wi_fi;
	private  boolean g3_4g;
	private WifiManager wifiManager;
	protected void onCreate(Bundle savedInstanceState)
	{    super.onCreate(savedInstanceState);
	      setContentView(R.layout.gnmlist_ietam);
	      layoutgeneral=(LinearLayout)findViewById(R.id.layoutgeneral);
	      layoutnetwork=(LinearLayout)findViewById(R.id.layoutnetwork);
          layoutmedia=(LinearLayout)findViewById(R.id.layoutmedia); 
          layoutmiscellaneous=(LinearLayout)findViewById(R.id.layoutmiscellaneous);
          this.layoutnetwork.setOnClickListener(this.layoutnetwork_OnClickListener);
         this.layoutgeneral.setOnClickListener(this.layoutgeneral_OnClickListner);
          this.layoutmedia.setOnClickListener(this.layoutmedia_OnClickListener);
          this.layoutmiscellaneous.setOnClickListener(this.layoutmiscellaneous_OnClickListener);
          
         // updateNetwork();
     }
	
	@Override
	protected void onPause() {
		updateNetwork();
		super.onPause();
		Log.d(THIS_FILE, "On Pause NSetting");
	}
	
	@Override
	protected void onResume() {
		updateNetwork();
		super.onResume();
		Log.d(THIS_FILE, "On Resume  NSetting");
	}
	
	 @Override
		protected void onDestroy() {
		//updateNetwork();
			super.onDestroy();
			Log.d(THIS_FILE, "---DESTROY NSettings---");
		}
	    
	
	
		
	void updateNetwork()
	{     ConnectivityManager connectivityManager = (ConnectivityManager) IMSDroid.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
	     NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
	     int netType = networkInfo.getType();
		 int netSubType = networkInfo.getSubtype();
		 mConfigurationService = ServiceManager.getConfigurationService();
 	     //this.networkService = ServiceManager.getNetworkService();
 	     configuration=ServiceManager.setEntry();//sharad for the seting the entry 
 	    mSipService = ServiceManager.getSipService();
	   //  NSettings.this.configurationService.start();
	     this.wifiManager = (WifiManager) IMSDroid.getContext().getSystemService(Context.WIFI_SERVICE);
	     this.g3_4g=mConfigurationService.getBoolean(CONFIGURATION_SECTION.NETWORK, CONFIGURATION_ENTRY.THREE_3G, Configuration.DEFAULT_3G);
         this.wi_fi=configurationService.getBoolean(CONFIGURATION_SECTION.NETWORK, CONFIGURATION_ENTRY.WIFI, Configuration.DEFAULT_WIFI);
         System.out.println("wi fi and 3g"+g3_4g+wi_fi+"...."+wifiManager+",,,,"+wifiManager.isWifiEnabled());   	
       
         if(wi_fi==true && (netType == ConnectivityManager.TYPE_WIFI)){
 			if(this.wifiManager.isWifiEnabled())   
                System.out.println("now in the wi fi section");
 			    VijaySetting.mSipService.register();
		    }
		    else if((g3_4g)==true)
		   {     
		    	System.out.println("now in the 3g section");
	    		VijaySetting.mSipService.register();
	    		System.out.println("now in the $$$ section");
	    	}
           else
	    	{
	    		VijaySetting.mSipService.unregister();
	    	}
	}
	private OnClickListener layoutnetwork_OnClickListener = new OnClickListener(){
		@Override
		public void onClick(View v) {
			
			Intent screencall1 = new Intent(VijaySetting.this, ScreenNetwork.class);
			screencall1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(screencall1);
			//finish();miscellaneous
		}
	};
	
	 private OnClickListener layoutgeneral_OnClickListner = new OnClickListener(){
		@Override
		public void onClick(View v) {
			
			Intent screencall1 = new Intent(VijaySetting.this, ScreenGeneral.class);
			screencall1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(screencall1);
			//finish();
		}
	};
	
	private OnClickListener layoutmedia_OnClickListener = new OnClickListener(){
		@Override
		public void onClick(View v) {
			
			Intent screencall1 = new Intent(VijaySetting.this, ScreenCodecs.class);
			screencall1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(screencall1);
			//finish();
		}
	};
	private OnClickListener layoutmiscellaneous_OnClickListener = new OnClickListener(){
		@Override
		public void onClick(View v) {
			
			Intent screencall1 = new Intent(VijaySetting.this, ScreenPresence.class);
			screencall1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(screencall1);
			//finish();
		}
	};
	
	}

	


*/