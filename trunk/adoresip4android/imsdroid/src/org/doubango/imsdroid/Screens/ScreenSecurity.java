package org.doubango.imsdroid.Screens;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Timer;





import android.app.KeyguardManager.KeyguardLock;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.os.PowerManager;
import android.service.wallpaper.WallpaperService.Engine;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.OrientationEventListener;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;
import android.widget.ViewFlipper;

import org.doubango.imsdroid.IMSDroid;
import org.doubango.imsdroid.Main;
import org.doubango.imsdroid.R;
import org.doubango.imsdroid.Screens.ScreenAV;
import org.doubango.imsdroid.Services.IScreenService;
import org.doubango.imsdroid.Utils.DialerUtils;
import org.doubango.ngn.sip.NgnAVSession;
import org.doubango.ngn.utils.NgnStringUtils;
import org.doubango.tinyWRAP.SipSession;

public class ScreenSecurity extends BaseScreen 
{ //  public static HashMap<String, MyAVSession> sessionsvalue;
	private static SimpleDateFormat __timerFormat;
	private long startTime;
	private final Timer timerInCall;
	private final Timer timerSuicide;
	private final Timer timerBlankPacket;
	private ToggleButton  speakerButton;
	private Button padButton,dialpadButton;
	private ToggleButton  holdcall;
	private Button ricvcall;
	private NgnAVSession mAVSession;
	private ViewFlipper fvFlipper;
	private ViewType mCurrentView;
	
	private ImageView ivDialer;
	private LinearLayout llVideoLocal;
	private static LinearLayout llVideoRemote;
	private static LinearLayout calllVideoRemote;
	private ImageView ivState;
	private TextView tvInfo;
	private TextView tvTime;
	private TextView tvRemoteUri;
	private TextView tvNumber;
	private static TextView updateinfo;
	private Button btBack2Call;
    private Button cancelbutton;
    private static Chronometer elapsedTime;
	private ImageButton btDtmf_0;
	private ImageButton btDtmf_1;
	private ImageButton btDtmf_2;
	private ImageButton btDtmf_3;
	private ImageButton btDtmf_4;
	private ImageButton btDtmf_5;
	private ImageButton btDtmf_6;
	private ImageButton btDtmf_7;
	private ImageButton btDtmf_8;
	private ImageButton btDtmf_9;
	private ImageButton btDtmf_Sharp;
	private ImageButton btDtmf_Star;
	
//	private ProxSensor proxSensor;
	private KeyguardLock keyguardLock;
	//private final IScreenService mScreenService;
	//private static   LinearLayout llVideoRemote;
	private static final int SELECT_CONTENT = 1;
	
	private final static int MENU_PICKUP = 0;
	private final static int MENU_HANGUP= 1;
	private final static int MENU_HOLD_RESUME = 2;
	private final static int MENU_SEND_STOP_VIDEO = 3;
	private final static int MENU_SHARE_CONTENT = 4;
	private final static int MENU_SPEAKER = 5;
	private static final String TAG = null;
	 
//	private static MyAVSession mAVSession ;
	private ScreenAV avScreen;
	private static enum ViewType{
		ViewNone,
		ViewTrying,
		ViewInCall,
		ViewProxSensor,
		ViewTermwait
	}

	public static int numbers=1;
	
	public ScreenSecurity() {
		super(SCREEN_TYPE.AV_T, null);
		
		mCurrentView = ViewType.ViewNone;
		this.timerInCall = new Timer();
		this.timerSuicide = new Timer();
		this.timerBlankPacket = new Timer();
		
	//	mScreenService = ((Engine)Engine.getInstance()).getScreenService();
	}	
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_security);
        OrientationEventListener mListener;
        
        // retrieve id
        this.mId = getIntent().getStringExtra("id");
        //this.avSession = MyAVSession.getSession(Long.parseLong(this.id));
       // MyAVSession.getCallEventHandler().setAvScreen(this);
        
        // get controls
        this.fvFlipper = (ViewFlipper) this.findViewById(R.id.screen_av_flipperView);
        this.ivDialer = (ImageView) this.findViewById(R.id.screen_av_imageView_dialer);
        llVideoLocal = (LinearLayout)this.findViewById(R.id.screen_av_linearLayout_video_local);
        llVideoRemote = (LinearLayout)this.findViewById(R.id.screen_av_linearLayout_video_remote);
        calllVideoRemote = (LinearLayout)this.findViewById(R.id.calscreen_av_linearLayout_video_remote);
        this.ivState = (ImageView)this.findViewById(R.id.screen_av_imageView_state);
        this.tvInfo = (TextView)this.findViewById(R.id.screen_av_textView_info);
        this.tvTime = (TextView)this.findViewById(R.id.screen_av_textView_time);
        this.tvRemoteUri = (TextView)this.findViewById(R.id.screen_av_textView_remoteUri);
         this.tvNumber = (TextView)this.findViewById(R.id.numberinfo);
         this.updateinfo = (TextView)this.findViewById(R.id.update_info);
        
        this.btBack2Call = (Button)this.findViewById(R.id.screen_av_button_back2call);
        this.cancelbutton=(Button)this.findViewById(R.id.cancelcall);
        speakerButton = (ToggleButton) findViewById(R.id.speakerButton);
        elapsedTime = (Chronometer) findViewById(R.id.elapsedTime);
        padButton = (Button)this.findViewById(R.id.dialpadButton);              
        holdcall=(ToggleButton)this.findViewById(R.id.holdcall);
        ricvcall=(Button)this.findViewById(R.id.ricvcall);
        dialpadButton=(Button)this.findViewById(R.id.dialpadButton);
         View.OnClickListener mOnKeyboardClickListener = new View.OnClickListener() {
    		@Override
    		public void onClick(View v) {
    			if(mAVSession != null){
    				mAVSession.sendDTMF(NgnStringUtils.parseInt(v.getTag().toString(), -1));
    			}
    		}
    	};
        
      /*  private void loadKeyboard(View view){
    		DialerUtils.setDialerTextButton(view.findViewById(R.id.view_dialer_buttons_0), "0", "+", DialerUtils.TAG_0, mOnKeyboardClickListener);
    		DialerUtils.setDialerTextButton(view.findViewById(R.id.view_dialer_buttons_1), "1", "", DialerUtils.TAG_1, mOnKeyboardClickListener);
    		DialerUtils.setDialerTextButton(view.findViewById(R.id.view_dialer_buttons_2), "2", "ABC", DialerUtils.TAG_2, mOnKeyboardClickListener);
    		DialerUtils.setDialerTextButton(view.findViewById(R.id.view_dialer_buttons_3), "3", "DEF", DialerUtils.TAG_3, mOnKeyboardClickListener);
    		DialerUtils.setDialerTextButton(view.findViewById(R.id.view_dialer_buttons_4), "4", "GHI", DialerUtils.TAG_4, mOnKeyboardClickListener);
    		DialerUtils.setDialerTextButton(view.findViewById(R.id.view_dialer_buttons_5), "5", "JKL", DialerUtils.TAG_5, mOnKeyboardClickListener);
    		DialerUtils.setDialerTextButton(view.findViewById(R.id.view_dialer_buttons_6), "6", "MNO", DialerUtils.TAG_6, mOnKeyboardClickListener);
    		DialerUtils.setDialerTextButton(view.findViewById(R.id.view_dialer_buttons_7), "7", "PQRS", DialerUtils.TAG_7, mOnKeyboardClickListener);
    		DialerUtils.setDialerTextButton(view.findViewById(R.id.view_dialer_buttons_8), "8", "TUV", DialerUtils.TAG_8, mOnKeyboardClickListener);
    		DialerUtils.setDialerTextButton(view.findViewById(R.id.view_dialer_buttons_9), "9", "WXYZ", DialerUtils.TAG_9, mOnKeyboardClickListener);
    		DialerUtils.setDialerTextButton(view.findViewById(R.id.view_dialer_buttons_star), "*", "", DialerUtils.TAG_STAR, mOnKeyboardClickListener);
    		DialerUtils.setDialerTextButton(view.findViewById(R.id.view_dialer_buttons_sharp), "#", "", DialerUtils.TAG_SHARP, mOnKeyboardClickListener);
    	}*/
        
        this.cancelbutton.setOnClickListener(this.cancelbutton_OnClickListener);
        this.speakerButton.setOnClickListener(this.speakerbutton_OnClickListener);
        this.holdcall.setOnClickListener(holdbutton_OnClickListener);
        this.ricvcall.setOnClickListener(ricvbutton_OnClickListener);
        this.dialpadButton.setOnClickListener(dialpadbutton_OnClickListener);
        // Hide video preview
        this.llVideoLocal.setVisibility(View.GONE);
        Bundle extras = getIntent().getExtras();

       // String PhoneNumber = extras.getString("phonenumber");
       
        //tvNumber.setText(PhoneNumber); 
//        this.fvFlipper.setInAnimation(AnimationUtils.loadAnimation(this,
//				R.anim.slidein));
//        this.fvFlipper.setOutAnimation(AnimationUtils.loadAnimation(this,
//				R.anim.slideout));
//        
//        this.dialpadButton.setOnClickListener(new OnClickListener(){
//			@Override
//			public void onClick(View v) {
//				CallSharad.this.fvFlipper.showNext();
//			}
//        });

	} 

  
	
	
//  public void	incomingcal()
//	{  System.out.println("incomingcal()");
//	   Intent screenIntent = new Intent(CallSharad.this, TabHome.class);
//		//screenIntent.setFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
//  	    screenIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//		startActivity(screenIntent);
//		finish();
//	}
	
	
	
//Added by Sharad
	public void updateStates(ViewType state){
	
		System.out.println("value of state in calsharad hello"+state);
		String statevalue=state.toString();
		System.out.println("in update state abcd"+updateinfo);
		//this.newupdate(statevalue);
		//ScreenAV.updateState(state, null);
		if(statevalue.equals("CALL_INPROGRESS"))
		{   System.out.println("Calling..xxxx....");
			//numbers=2;
			//CallSharad.nextto();
		  updateinfo.setText("Calling");
		  //elapsedTime.start();
		}
		else if(statevalue.equals("INCALL"))
		{   
			System.out.println("Connected...xxxxx.."+updateinfo);
		
		this.runOnUiThread(new Runnable(){
			@Override
			public void run() {
				
				//CallSharad.this.updateinfo.setText("Connected.....");
									
				updateinfo.setText("Connected.....");		
				elapsedTime.start();		
	                   }
		
		     //numbers=3;
		     //CallSharad.nextto();
		
		});
		}
		else if(statevalue.equals("CALL_TERMINATED"))
		{    System.out.println("Disconnectedxxxxx");
		  this.runOnUiThread(new Runnable(){
			@Override
			public void run() {
				
				updateinfo.setText("Disconnected.....");
				elapsedTime.stop();
				 System.out.println("discontperform();");
				// discontperform();
//				 Intent returnIntent = new Intent();
//		    	 setResult(RESULT_OK,returnIntent);    	
//		    	 finish();			
	                   }
		});
		
		}
	/*	else if(ViewType.equals(ViewTrying))
		{  
			System.out.println("Calling..xxxx....");
			this.runOnUiThread(new Runnable(){
				@Override
				public void run() {
					//Looper.prepare();
					updateinfo.setText("INCOMING.....");
					//elapsedTime.stop();
					 System.out.println("discontperform();");
					// discontperform();
//					 Intent returnIntent = new Intent();
//			    	 setResult(RESULT_OK,returnIntent);    	
//			    	 finish();			
		                   }
			});
			
		}	*/
		
		else
		{
			System.out.println("out Side of update else");
		}
		System.out.println("out Side of update");
	}
	
	//button for hold
	 private OnClickListener holdbutton_OnClickListener = new OnClickListener(){
	 		
			@Override
	 		public void onClick(View v) {
	 			 ScreenAV screendis=new ScreenAV();
	 	         System.out.println("//call sharad hold\\");
	 	       // ScreenAV.loadTryingView();
	 		}
		};
		
 private OnClickListener dialpadbutton_OnClickListener = new OnClickListener(){
	 		
			@Override
	 		public void onClick(View v) {
	 			 ScreenAV screendis=new ScreenAV();
	 	         System.out.println("//call sharad hold\\");
	 	       // screendis.holdcall();
	 		}
		};
	
		 private OnClickListener ricvbutton_OnClickListener = new OnClickListener(){
		 		
				@Override
		 		public void onClick(View v) {
		 			 ScreenAV screendis=new ScreenAV();
		 	         System.out.println("//call sharad ricv\\");
		 	       // screendis.ricvcall();
		 		}
			};	
		

	//call ending button
	 private OnClickListener cancelbutton_OnClickListener = new OnClickListener(){
 		
		@Override
 		public void onClick(View v) {
 			 ScreenAV screendis=new ScreenAV();
 	         System.out.println("//call sharad disconneted\\");
 	        //  screendis.disconneted();
 			  Intent returnIntent = new Intent();
 	    	  setResult(RESULT_OK,returnIntent);    	
 	    	  finish();
 			
 		}
		
 	};
 	
// 	public void removeoflayout()
// 	
//	{  System.out.println("now thw llVideoRemote"+calllVideoRemote);
//	calllVideoRemote.removeAllViews();
//	if(avSession.getMediaType() == MediaType.AudioVideo || avSession.getMediaType() == MediaType.Video){
//		final View remote_preview = MyAVSession.getVideoConsumer().startPreview();
//		if(remote_preview != null){
//			final ViewParent viewParent = remote_preview.getParent();
//			if(viewParent != null && viewParent instanceof ViewGroup){
//				((ViewGroup)(viewParent)).removeView(remote_preview);
//			}
//			calllVideoRemote.addView(remote_preview);
//		}
//	}
//	}
 	
 	
 	//code for accessing the speaker  sharad
	private OnClickListener speakerbutton_OnClickListener = new OnClickListener(){
	 	@Override
		public void onClick(View v) {
//	 		 AudioManager audioManager;
//	 		audioManager = (AudioManager)IMSDroid.getContext().getSystemService(Context.AUDIO_SERVICE);
//	 		speakerButton.setText(((AudioManager)getSystemService(Context.AUDIO_SERVICE)).isSpeakerphoneOn() ? "Speaker OFF" : "Speaker ON");
	 		if (((AudioManager)getSystemService(Context.AUDIO_SERVICE)).isSpeakerphoneOn()) {
	 			//speakerButton.setTittle("Speaker OFF");
	 			((AudioManager)getSystemService(Context.AUDIO_SERVICE)).setSpeakerphoneOn(false);
	                System.out.println("now on");
	 			} else {
	 				//speakerButton.setTag("Speaker ON");
	 			((AudioManager)getSystemService(Context.AUDIO_SERVICE)).setSpeakerphoneOn(true);
	 			System.out.println("now off");
	 			}
	 	
	 	}
	 	};
 	
 	

/*	private OnClickListener dtmf_OnClickListener = new OnClickListener(){
		@Override
		public void onClick(View v) {
			if(CallSharad.this.avSession == null){
				return;
			}
			
			if(v == CallSharad.this.btDtmf_0){
				if(CallSharad.this.avSession.sendDTMF(0)){
					ServiceManager.getSoundService().playDTMF(0);
				}
			}
			else if(v == CallSharad.this.btDtmf_1){
				if(CallSharad.this.avSession.sendDTMF(1)){
					ServiceManager.getSoundService().playDTMF(1);
				}
			}
			else if(v == CallSharad.this.btDtmf_2){
				if(CallSharad.this.avSession.sendDTMF(2)){
					ServiceManager.getSoundService().playDTMF(2);
				}
			}
			else if(v == CallSharad.this.btDtmf_3){
				if(CallSharad.this.avSession.sendDTMF(3)){
					ServiceManager.getSoundService().playDTMF(3);
				}
			}
			else if(v == CallSharad.this.btDtmf_4){
				if(CallSharad.this.avSession.sendDTMF(4)){
					ServiceManager.getSoundService().playDTMF(4);
				}
			}
			else if(v == CallSharad.this.btDtmf_5){
				if(CallSharad.this.avSession.sendDTMF(5)){
					ServiceManager.getSoundService().playDTMF(5);
				}
			}
			else if(v == CallSharad.this.btDtmf_6){
				if(CallSharad.this.avSession.sendDTMF(6)){
					ServiceManager.getSoundService().playDTMF(6);
				}
			}
			else if(v == CallSharad.this.btDtmf_7){
				if(CallSharad.this.avSession.sendDTMF(7)){
					ServiceManager.getSoundService().playDTMF(7);
				}
			}
			else if(v == CallSharad.this.btDtmf_8){
				if(CallSharad.this.avSession.sendDTMF(8)){
					ServiceManager.getSoundService().playDTMF(8);
				}
			}
			else if(v == CallSharad.this.btDtmf_9){
				if(CallSharad.this.avSession.sendDTMF(9)){
					ServiceManager.getSoundService().playDTMF(9);
				}
			}
			else if(v == CallSharad.this.btDtmf_Star){
				if(CallSharad.this.avSession.sendDTMF(10)){
					ServiceManager.getSoundService().playDTMF(10);
				}
			}
			else if(v == CallSharad.this.btDtmf_Sharp){
				if(CallSharad.this.avSession.sendDTMF(11)){
					ServiceManager.getSoundService().playDTMF(11);
				}
			}
		}
	};*/
	/*private void startStopVideo(boolean start){
		if(this.avSession== null || (this.avSession.getMediaType() != MediaType.AudioVideo && this.avSession.getMediaType() != MediaType.Video)){
			return;
		}
		
		this.avSession.setSendingVideo(start);
		
		this.llVideoLocal.removeAllViews();
		if(start){
			this.timerBlankPacket.cancel();
			final View local_preview = MyAVSession.getVideoProducer().startPreview();
			if(local_preview != null){
				final ViewParent viewParent = local_preview.getParent();
				if(viewParent != null && viewParent instanceof ViewGroup){
					((ViewGroup)(viewParent)).removeView(local_preview);
				}
				this.llVideoLocal.addView(local_preview);
			}
		}
		this.llVideoLocal.setVisibility(start ? View.VISIBLE : View.GONE);
	}*/
	// from here handling of the call will be done sharad
	
//	private void updateState(CallState state){
//		this.updateState(state, null);
//	}
//	

		
	
	

}