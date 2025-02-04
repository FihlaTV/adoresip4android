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

import org.doubango.imsdroid.R;
import org.doubango.ngn.services.INgnConfigurationService;
import org.doubango.ngn.utils.NgnConfigurationEntry;
import org.doubango.tinyWRAP.MediaSessionMgr;
import org.doubango.tinyWRAP.tmedia_bandwidth_level_t;
import org.doubango.tinyWRAP.tmedia_qos_strength_t;
import org.doubango.tinyWRAP.tmedia_qos_stype_t;

import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;

public class ScreenQoS  extends BaseScreen {
	private final static String TAG = ScreenQoS.class.getCanonicalName();
	
	private CheckBox mCbEnableSessionTimers;
	private RelativeLayout mRlSessionTimers;
	private EditText mEtSessionTimeOut;
	private Spinner mSpRefresher;
	private Spinner mSpPrecondStrength;
	private Spinner mSpPrecondType;
	private Spinner mSpPrecondBandwidth;
	
	//Add by vijay
	private LinearLayout layoutgeneral,layoutnetwork,layoutmedia,layoutmiscellaneous; 
	static final String[] COUNTRIES = new String[] {
	    "Gerenal","Network","Media"};
	private static final String THIS_FILE = "NSettings";
	private  boolean wi_fi;
	private  boolean g3_4g;
	private WifiManager wifiManager;
	
	private final static String[] sSpinnerRefresherItems = new String[] {NgnConfigurationEntry.DEFAULT_QOS_REFRESHER, "uas", "uac"};
	private final static ScreenQoSStrength[] sSpinnerPrecondStrengthItems = new ScreenQoSStrength[] {
			new ScreenQoSStrength(tmedia_qos_strength_t.tmedia_qos_strength_none, "None"),
			new ScreenQoSStrength(tmedia_qos_strength_t.tmedia_qos_strength_optional, "Optional"),
			new ScreenQoSStrength(tmedia_qos_strength_t.tmedia_qos_strength_mandatory, "Mandatory"),
		};
	private final static ScreenQoSType[] sSpinnerPrecondTypeItems = new ScreenQoSType[] {
		   new ScreenQoSType(tmedia_qos_stype_t.tmedia_qos_stype_none, "None"),
		   new ScreenQoSType(tmedia_qos_stype_t.tmedia_qos_stype_segmented, "Segmented"),
		   new ScreenQoSType(tmedia_qos_stype_t.tmedia_qos_stype_e2e, "End2End")
		};
	private final static int[] sSpinnerPrecondBandwidthItems = new int[] {
		tmedia_bandwidth_level_t.tmedia_bl_low.swigValue(), 
		tmedia_bandwidth_level_t.tmedia_bl_medium.swigValue(), 
		tmedia_bandwidth_level_t.tmedia_bl_hight.swigValue(), 
		NgnConfigurationEntry.DEFAULT_QOS_PRECOND_BANDWIDTH_LEVEL
		};
	private final static String[] sSpinnerPrecondBandwidthItemsStrings = new String[] {
		"Low", 
		"Medium", 
		"High", 
		"Unrestricted"
		};
	
	private final INgnConfigurationService mConfigurationService;
	
	public ScreenQoS() {
		super(SCREEN_TYPE.QOS_T, TAG);

		// Services
		mConfigurationService = getEngine().getConfigurationService();
	}
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.screen_qos);
		
		//Add by vijay
		 layoutgeneral=(LinearLayout)findViewById(R.id.layoutgeneral);
	      layoutnetwork=(LinearLayout)findViewById(R.id.layoutnetwork);
         layoutmedia=(LinearLayout)findViewById(R.id.layoutmedia); 
         layoutmiscellaneous=(LinearLayout)findViewById(R.id.layoutmiscellaneous);
         this.layoutnetwork.setOnClickListener(this.layoutnetwork_OnClickListener);
        this.layoutgeneral.setOnClickListener(this.layoutgeneral_OnClickListner);
         this.layoutmedia.setOnClickListener(this.layoutmedia_OnClickListener);
         this.layoutmiscellaneous.setOnClickListener(this.layoutmiscellaneous_OnClickListener);
         
		
		// get controls
		mCbEnableSessionTimers = (CheckBox)findViewById(R.id.screen_qos_checkBox_sessiontimers);
		mRlSessionTimers = (RelativeLayout)findViewById(R.id.screen_qos_relativeLayout_sessiontimers);
        mEtSessionTimeOut = (EditText)findViewById(R.id.screen_qos_editText_stimeout);
        mSpRefresher = (Spinner)findViewById(R.id.screen_qos_spinner_refresher);
        mSpPrecondStrength = (Spinner)findViewById(R.id.screen_qos_spinner_precond_strength);
        mSpPrecondType = (Spinner)findViewById(R.id.screen_qos_Spinner_precond_type);
        mSpPrecondBandwidth = (Spinner)findViewById(R.id.screen_qos_spinner_precond_bandwidth);
        
        // spinners
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ScreenQoS.sSpinnerRefresherItems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpRefresher.setAdapter(adapter);
        
        ArrayAdapter<ScreenQoSStrength> adapterStrength = new ArrayAdapter<ScreenQoSStrength>(this, android.R.layout.simple_spinner_item, ScreenQoS.sSpinnerPrecondStrengthItems);
        adapterStrength.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpPrecondStrength.setAdapter(adapterStrength);
        
        ArrayAdapter<ScreenQoSType> adapterType = new ArrayAdapter<ScreenQoSType>(this, android.R.layout.simple_spinner_item, ScreenQoS.sSpinnerPrecondTypeItems);
        adapterType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpPrecondType.setAdapter(adapterType);
        
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ScreenQoS.sSpinnerPrecondBandwidthItemsStrings);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpPrecondBandwidth.setAdapter(adapter);
        
        // load values from configuration file (Do it before adding UI listeners)
        mCbEnableSessionTimers.setChecked(mConfigurationService.getBoolean(NgnConfigurationEntry.QOS_USE_SESSION_TIMERS, NgnConfigurationEntry.DEFAULT_QOS_USE_SESSION_TIMERS));
        mEtSessionTimeOut.setText(mConfigurationService.getString(NgnConfigurationEntry.QOS_SIP_CALLS_TIMEOUT, Integer.toString(NgnConfigurationEntry.DEFAULT_QOS_SIP_CALLS_TIMEOUT)));
		mSpRefresher.setSelection(getSpinnerIndex(
				mConfigurationService.getString(NgnConfigurationEntry.QOS_REFRESHER,
						sSpinnerRefresherItems[0]),
						sSpinnerRefresherItems));
		
		mSpPrecondStrength.setSelection(ScreenQoSStrength.getSpinnerIndex(tmedia_qos_strength_t.valueOf(mConfigurationService.getString(
				NgnConfigurationEntry.QOS_PRECOND_STRENGTH,
				NgnConfigurationEntry.DEFAULT_QOS_PRECOND_STRENGTH))));
		
		
		mSpPrecondType.setSelection(ScreenQoSType.getSpinnerIndex(tmedia_qos_stype_t.valueOf(mConfigurationService.getString(
				NgnConfigurationEntry.QOS_PRECOND_TYPE,
				NgnConfigurationEntry.DEFAULT_QOS_PRECOND_TYPE))));
		
		mSpPrecondBandwidth.setSelection(getSpinnerIndex(
				mConfigurationService.getInt(NgnConfigurationEntry.QOS_PRECOND_BANDWIDTH_LEVEL,
						NgnConfigurationEntry.DEFAULT_QOS_PRECOND_BANDWIDTH_LEVEL),
						sSpinnerPrecondBandwidthItems));
		
		mRlSessionTimers.setVisibility(mCbEnableSessionTimers.isChecked() ? View.VISIBLE : View.INVISIBLE);
		
		// add listeners (for the configuration)
		/* addConfigurationListener(cbEnableSessionTimers); */
		addConfigurationListener(mEtSessionTimeOut);
		addConfigurationListener(mSpRefresher);
		addConfigurationListener(mSpPrecondStrength);
		addConfigurationListener(mSpPrecondType);
		addConfigurationListener(mSpPrecondBandwidth);
		
		// add local listeners
        mCbEnableSessionTimers.setOnCheckedChangeListener(mCbEnableSessionTimers_OnCheckedChangeListener);
	}
	
	protected void onPause() {
		if(super.mComputeConfiguration){
			
			mConfigurationService.putBoolean(NgnConfigurationEntry.QOS_USE_SESSION_TIMERS,
					mCbEnableSessionTimers.isChecked());
			mConfigurationService.putString(NgnConfigurationEntry.QOS_SIP_CALLS_TIMEOUT,
					mEtSessionTimeOut.getText().toString());
			mConfigurationService.putString(NgnConfigurationEntry.QOS_REFRESHER, 
					sSpinnerRefresherItems[mSpRefresher.getSelectedItemPosition()]);
			mConfigurationService.putString(NgnConfigurationEntry.QOS_PRECOND_STRENGTH, 
					sSpinnerPrecondStrengthItems[mSpPrecondStrength.getSelectedItemPosition()].mStrength.toString());
			mConfigurationService.putString(NgnConfigurationEntry.QOS_PRECOND_TYPE, 
					sSpinnerPrecondTypeItems[mSpPrecondType.getSelectedItemPosition()].mType.toString());
			mConfigurationService.putInt(NgnConfigurationEntry.QOS_PRECOND_BANDWIDTH_LEVEL, 
					sSpinnerPrecondBandwidthItems[mSpPrecondBandwidth.getSelectedItemPosition()]);
			
			// Compute
			if(!mConfigurationService.commit()){
				Log.e(TAG, "Failed to commit() configuration");
			}
			else{
				tmedia_bandwidth_level_t newBandwidthLevel = tmedia_bandwidth_level_t.swigToEnum(sSpinnerPrecondBandwidthItems[mSpPrecondBandwidth.getSelectedItemPosition()]);
				MediaSessionMgr.defaultsSetBandwidthLevel(newBandwidthLevel);
			}
			
			super.mComputeConfiguration = false;
		}
		super.onPause();
	}	
	
	
	private OnCheckedChangeListener mCbEnableSessionTimers_OnCheckedChangeListener = new OnCheckedChangeListener(){
		public void onCheckedChanged(CompoundButton arg0, boolean isChecked) {
			mRlSessionTimers.setVisibility(isChecked? View.VISIBLE : View.INVISIBLE);
			mComputeConfiguration = true;
		}
	};
	
	private static class ScreenQoSStrength {
		private final String mDescription;
		private final tmedia_qos_strength_t mStrength;

		private ScreenQoSStrength(tmedia_qos_strength_t strength, String description) {
			mStrength = strength;
			mDescription = description;
		}

		@Override
		public String toString() {
			return mDescription;
		}

		@Override
		public boolean equals(Object o) {
			return mStrength.equals(((ScreenQoSStrength)o).mStrength);
		}
		
		static int getSpinnerIndex(tmedia_qos_strength_t strength){
			for(int i = 0; i< sSpinnerPrecondStrengthItems.length; i++){
				if(strength == sSpinnerPrecondStrengthItems[i].mStrength){
					return i;
				}
			}
			return 0;
		}
	}
	
	private static class ScreenQoSType {
		private final String mDescription;
		private final tmedia_qos_stype_t mType;

		private ScreenQoSType(tmedia_qos_stype_t type, String description) {
			mType = type;
			mDescription = description;
		}

		@Override
		public String toString() {
			return mDescription;
		}

		@Override
		public boolean equals(Object o) {
			return mType.equals(((ScreenQoSType)o).mType);
		}
		
		static int getSpinnerIndex(tmedia_qos_stype_t type){
			for(int i = 0; i< sSpinnerPrecondTypeItems.length; i++){
				if(type == sSpinnerPrecondTypeItems[i].mType){
					return i;
				}
			}
			return 0;
		}
	}
	
	// Add by vijay
	
	private OnClickListener layoutnetwork_OnClickListener = new OnClickListener(){
		@Override
		public void onClick(View v) {
			
			Intent screencall1 = new Intent(ScreenQoS.this, ScreenNetwork.class);
			screencall1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(screencall1);
			//finish();miscellaneous
		}
	};
	
	 private OnClickListener layoutgeneral_OnClickListner = new OnClickListener(){
		@Override
		public void onClick(View v) {
			
			Intent screencall1 = new Intent(ScreenQoS.this, ScreenGeneral.class);
			screencall1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(screencall1);
			//finish();
		}
	};
	
	private OnClickListener layoutmedia_OnClickListener = new OnClickListener(){
		@Override
		public void onClick(View v) {
			
			Intent screencall1 = new Intent(ScreenQoS.this, ScreenCodecs.class);
			screencall1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(screencall1);
			//finish();
		}
	};
	private OnClickListener layoutmiscellaneous_OnClickListener = new OnClickListener(){
		@Override
		public void onClick(View v) {
			
			Intent screencall1 = new Intent(ScreenQoS.this, ScreenPresence.class);
			screencall1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(screencall1);
			//finish();
		}
	};
}
