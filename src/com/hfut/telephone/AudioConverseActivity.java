package com.hfut.telephone;

import com.yzx.api.UCSCall;
import com.yzx.api.UCSService;
import com.yzx.tools.CustomLog;

import com.yzxdemo.action.UIDfineAction;
import com.yzxdemo.tools.DfineAction;
import com.yzxdemo.tools.DialConfig;
import com.yzxdemo.tools.LoginConfig;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AudioConverseActivity extends ConverseActivity{

	public String calledUid ;
	public String calledPhone ;
	public boolean inCall = false;
	private TextView converse_client;
	private TextView converse_information;
	private TextView dial_close;
	private EditText dial_number;
	private AudioManager mAudioManager;
	private ImageButton converse_call_mute;
	private ImageButton converse_call_dial;
	private ImageButton converse_call_speaker;
	private ImageButton converse_call_hangup;
	private ImageButton converse_call_answer;
	private ImageButton converse_call_endcall;
	private ImageButton dial_endcall;
	private LinearLayout key_layout;
	private LinearLayout converse_main;
	private boolean open_headset=false;
	private int max = 0 ;
	private int current= 0;
	private int calltype = 2;
	private boolean speakerPhoneState = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_audio_converse);
		initviews();
		
		mAudioManager = ((AudioManager) getSystemService(Context.AUDIO_SERVICE));
		max = mAudioManager.getStreamMaxVolume( AudioManager.STREAM_VOICE_CALL );
		current = mAudioManager.getStreamVolume( AudioManager.STREAM_VOICE_CALL );
		
		IntentFilter ift = new IntentFilter();
		ift.addAction(UIDfineAction.ACTION_DIAL_STATE);
		ift.addAction(UIDfineAction.ACTION_CALL_BACK);
		ift.addAction(UIDfineAction.ACTION_ANSWER);
		ift.addAction(UIDfineAction.ACTION_CALL_TIME);
		ift.addAction(UIDfineAction.ACTION_DIAL_HANGUP);
		ift.addAction("android.intent.action.HEADSET_PLUG");
		registerReceiver(br, ift);
		if(getIntent().hasExtra("call_type")){
			calltype = getIntent().getIntExtra("call_type", 2);
		}
		
		//�ж��Ƿ���������Ϣ��Ĭ����ȥ�磬��������Ϣ����ConnectionService�е�onIncomingCall�ص��з��͹㲥������ͨ�����棬inCallΪtrue��
		if(getIntent().hasExtra("inCall")){
			inCall = getIntent().getBooleanExtra("inCall", false);
		}
		
		//���Ҫ����ĺ��룬���ܲ�������ͨ����phoneNumber����ClientID��ֱ���ͻز�����ClientID�󶨵��ֻ�����
		if(getIntent().hasExtra("call_client")){
			calledUid = getIntent().getStringExtra("call_client");
			Log.e("ţ��", calledUid);
		}
		if(getIntent().hasExtra("call_phone")){
			calledPhone = getIntent().getStringExtra("call_phone");
		}
		DialConfig.saveCallType(this, "1");
		if(inCall){
			//����
			if("".equals(getIntent().getStringExtra("nickName"))){
				converse_client.setText(getIntent().getStringExtra("phoneNumber"));
			}else {
				converse_client.setText(getIntent().getStringExtra("nickName"));
			}
			converse_call_answer.setVisibility(View.VISIBLE);
			converse_call_hangup.setVisibility(View.VISIBLE);
			converse_call_endcall.setVisibility(View.GONE);
			UCSCall.setSpeakerphone(true);
			UCSCall.startRinging(true);
		
		}else{
			//ȥ��
			converse_call_answer.setVisibility(View.GONE);
			converse_call_hangup.setVisibility(View.VISIBLE);
			converse_call_endcall.setVisibility(View.GONE);
			//���в���
			dial();
			if(calltype == 1){
				converse_information.setText("��ѵ绰������");
			}else if(calltype == 0){
				converse_information.setText("ֱ���绰������");
			}else if(calltype == 5){
				converse_information.setText("���ܵ绰������");
			}else{
				converse_information.setText("�ز�������");
			}
		}
	}

	private void initviews() {
		converse_client = (TextView) findViewById(R.id.converse_client);
		converse_information = (TextView) findViewById(R.id.converse_information);
		if(getIntent().getStringExtra("call_client") != null){
			converse_client.setText(getIntent().getStringExtra("call_client"));
		}else {
			converse_client.setText(getIntent().getStringExtra("call_phone"));
		}
		converse_call_mute = (ImageButton) findViewById(R.id.converse_call_mute);
		converse_main = (LinearLayout) findViewById(R.id.converse_main);
		key_layout = (LinearLayout) findViewById(R.id.key_layout);
		dial_endcall = (ImageButton) findViewById(R.id.dial_endcall);
		dial_number = (EditText) findViewById(R.id.text_dtmf_number);
		
		//����
		converse_call_mute.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(UCSCall.isMicMute()){
					converse_call_mute.setBackgroundResource(R.drawable.converse_mute);
				}else{
					converse_call_mute.setBackgroundResource(R.drawable.converse_mute_down);
				}
				UCSCall.setMicMute(!UCSCall.isMicMute());
			}
		});
		
		//������
		converse_call_speaker = (ImageButton)findViewById(R.id.converse_call_speaker);
		converse_call_speaker.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(UCSCall.isSpeakerphoneOn()){
					converse_call_speaker.setBackgroundResource(R.drawable.converse_speaker);
				}else{
					converse_call_speaker.setBackgroundResource(R.drawable.converse_speaker_down);
				}
				UCSCall.setSpeakerphone(!UCSCall.isSpeakerphoneOn());
			}
		});
		
		//��ͨ
		converse_call_answer = (ImageButton)findViewById(R.id.audio_call_answer);
		converse_call_answer.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				CustomLog.v(DfineAction.TAG_TCP,"��ͨ�绰");
				UCSCall.stopRinging();
				UCSCall.answer("");
				UCSCall.setSpeakerphone(false);
			}
		});
				
		//�Ҷ�
		converse_call_hangup = (ImageButton)findViewById(R.id.audio_call_hangup);
		converse_call_hangup.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				DialConfig.saveCallType(AudioConverseActivity.this, "");
				UCSCall.stopRinging();
				UCSCall.hangUp("");
				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						AudioConverseActivity.this.finish();
					}
				}, 1500);
			}
		});
		
		//����ͨ��
		converse_call_endcall  = (ImageButton)findViewById(R.id.audio_call_endcall);
		converse_call_endcall.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				CustomLog.v(DfineAction.TAG_TCP,"�����绰");
				UCSCall.stopRinging();
				UCSCall.setSpeakerphone(false);
				UCSCall.hangUp("");
				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						AudioConverseActivity.this.finish();
					}
				}, 1500);
			}
		});
		
		//����ͨ�������̽����еİ�ť��
		dial_endcall  = (ImageButton)findViewById(R.id.dial_endcall);
		dial_endcall.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				CustomLog.v(DfineAction.TAG_TCP, "�����绰");
				UCSCall.stopRinging();
				UCSCall.setSpeakerphone(false);
				UCSCall.hangUp("");
				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						AudioConverseActivity.this.finish();
					}
				}, 1500);
			}
		});
		//�򿪼���
		converse_call_dial  = (ImageButton)findViewById(R.id.converse_call_dial);
		converse_call_dial.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				CustomLog.v(DfineAction.TAG_TCP,"�򿪼���");
				key_layout.setVisibility(View.VISIBLE);
				converse_main.setVisibility(View.GONE);
			}
		});
		
		//�رռ���
		dial_close = (TextView) findViewById(R.id.dial_close);
		dial_close.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				CustomLog.v(DfineAction.TAG_TCP,"�رռ���");
				key_layout.setVisibility(View.GONE);
				converse_main.setVisibility(View.VISIBLE);
			}
		});
		findViewById(R.id.digit0).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				UCSCall.sendDTMF(AudioConverseActivity.this, KeyEvent.KEYCODE_0,dial_number);
			}
		});
		findViewById(R.id.digit1).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				UCSCall.sendDTMF(AudioConverseActivity.this, KeyEvent.KEYCODE_1,dial_number);
			}
		});
		findViewById(R.id.digit2).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				UCSCall.sendDTMF(AudioConverseActivity.this, KeyEvent.KEYCODE_2,dial_number);
			}
		});
		findViewById(R.id.digit3).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				UCSCall.sendDTMF(AudioConverseActivity.this, KeyEvent.KEYCODE_3,dial_number);
			}
		});
		findViewById(R.id.digit4).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				UCSCall.sendDTMF(AudioConverseActivity.this, KeyEvent.KEYCODE_4,dial_number);
			}
		});
		findViewById(R.id.digit5).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				UCSCall.sendDTMF(AudioConverseActivity.this, KeyEvent.KEYCODE_5,dial_number);
			}
		});
		findViewById(R.id.digit6).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				UCSCall.sendDTMF(AudioConverseActivity.this, KeyEvent.KEYCODE_6,dial_number);
			}
		});
		findViewById(R.id.digit7).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				UCSCall.sendDTMF(AudioConverseActivity.this, KeyEvent.KEYCODE_7,dial_number);
			}
		});
		findViewById(R.id.digit8).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				UCSCall.sendDTMF(AudioConverseActivity.this, KeyEvent.KEYCODE_8,dial_number);
			}
		});
		findViewById(R.id.digit9).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				UCSCall.sendDTMF(AudioConverseActivity.this, KeyEvent.KEYCODE_9,dial_number);
			}
		});
		findViewById(R.id.digit_star).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				UCSCall.sendDTMF(AudioConverseActivity.this, KeyEvent.KEYCODE_STAR,dial_number);
			}
		});
		findViewById(R.id.digit_husa).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				UCSCall.sendDTMF(AudioConverseActivity.this, KeyEvent.KEYCODE_POUND,dial_number);
			}
		});
	}
	
	//���ͨ����ֱ�����ز������ܲ���  �����ܲ�������ͨ����phoneNumber����ClientID��ֱ���ͻز�����ClientID�󶨵��ֻ�����
	public void dial(){
		UCSCall.setSpeakerphone(false);
		//UCSCall.startCallRinging("dialling_tone.pcm");
		Intent intent = new Intent(UIDfineAction.ACTION_DIAL);
		//�ز���Ҫ�õ�FROM_NUM_KEY�������Ժţ���TO_NUM_KEY�������Ժţ���������Ϊ�ز�����ʾ�ã�����Ϊ��
		if(getIntent().hasExtra(UIDfineAction.FROM_NUM_KEY)){
			intent.putExtra(UIDfineAction.FROM_NUM_KEY, getIntent().getStringExtra(UIDfineAction.FROM_NUM_KEY));
		}
		if(getIntent().hasExtra(UIDfineAction.TO_NUM_KEY)){
			intent.putExtra(UIDfineAction.TO_NUM_KEY, getIntent().getStringExtra(UIDfineAction.TO_NUM_KEY));
		}
		
		//type:
		//		0��ֱ��
		//		1�����
		//		2:�ز�
		//		3:��Ƶ��Ե�
		//		4:����
		//      5:���ܲ���
		switch (calltype) {
		//��Ƶ��Ե㡢���ͨ��
		case 0:
			//ֱ������绰����
			Log.e("��绰", "1");
			sendBroadcast(intent.putExtra(UIDfineAction.CALL_PHONE, calledPhone).putExtra("type", calltype));
			Log.e("��绰", "2");
			break;
		case 1:
			//��Ѵ���clientid
			sendBroadcast(intent.putExtra(UIDfineAction.CALL_UID, calledUid).putExtra("type", calltype));
			break;
		case 2:
			//�ز�����绰����
			sendBroadcast(intent.putExtra(UIDfineAction.CALL_PHONE, calledPhone).putExtra("type", calltype));
			break;
		case 3:
			//��Ƶ��Ե� ����clientid
			sendBroadcast(intent.putExtra(UIDfineAction.CALL_UID, calledPhone).putExtra("type", calltype));
			break;
		case 5:
			//���ܲ��� clientid�͵绰����ͬʱ���룬��ѡ��clientid������Ѳ�������Է���������ѡ���ֻ��������ֱ��
			sendBroadcast(intent.putExtra(UIDfineAction.CALL_UID, calledUid).putExtra(UIDfineAction.CALL_PHONE, calledPhone).putExtra("type", calltype));
			break;
		}
	}
	
	
	private BroadcastReceiver br = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			if(intent.getAction().equals(UIDfineAction.ACTION_DIAL_STATE)){
				int state = intent.getIntExtra("state", 0);
				CustomLog.v(DfineAction.TAG_TCP, "AUDIO_CALL_STATE:"+state);
				if(UIDfineAction.dialState.keySet().contains(state)){
					CustomLog.v(state+UIDfineAction.dialState.get(state));
					//���ͨ��״̬��Ϣ
					converse_information.setText(UIDfineAction.dialState.get(state));
				}else{
					if(state >= 300233 && state <= 300243){
						switch(state){
						//case 300010:session����
						case 300211:
							converse_information.setText("����");
							break;
						case 300219:
							converse_information.setText("���ܲ����Լ��󶨺���");
							break;
						case 300233:
							converse_information.setText("�ز�����û�а��ֻ�����");
							break;
						case 300234:
							converse_information.setText("�ز����ֻ������쳣");
							break;
						case 300235:
							converse_information.setText("�ز���Ȩ����");
							break;
						case 300236:
							converse_information.setText("�ز�IO����");
							break;
						case 300237:
							converse_information.setText("�ز�����ɹ�������JSON����");
							break;
						case 300238:
							converse_information.setText("�ز�����ʱ");
							break;
						case 300239:
							converse_information.setText("�ز���������æ");
							break;
						case 300240:
							converse_information.setText("�ز��������ڲ�����");
							break;
						case 300241:
							converse_information.setText("�ز����к������");
							break;
						case 300242:
							converse_information.setText("��ֵ��ſ��Բ�����ʵ绰");
							break;
						case 300243:
							converse_information.setText("�ز�δ֪����");
							break;
						default:
							converse_information.setText("�ز�δ֪����");
							break;
						}    
					}
				}
				if((calltype == 0 && state == UCSCall.CALL_VOIP_RINGING_180)
						|| (calltype == 5 && state == UCSCall.CALL_VOIP_TRYING_183)){
					stopRing180();
					UCSCall.stopRinging();
				}
				if(state == UCSCall.HUNGUP_REFUSAL
						|| state == UCSCall.HUNGUP_MYSELF
						|| state == UCSCall.HUNGUP_OTHER
						|| state == UCSCall.HUNGUP_MYSELF_REFUSAL){
					CustomLog.v("�յ��Ҷ���Ϣ");
					UCSCall.stopRinging();
					new Handler().postDelayed(new Runnable() {
						@Override
						public void run() {
							AudioConverseActivity.this.finish();
						}
					}, 1500);
				}else{
					if((state >= 300210 && state <= 300249)&&
							(state != 300221 && state != 300222 && state !=300247)
							|| state == UCSCall.HUNGUP_NOT_ANSWER){
						new Handler().postDelayed(new Runnable() {
							@Override
							public void run() {
								AudioConverseActivity.this.finish();
							}
						}, 3000);
					}
				}
			}else if(intent.getAction().equals(UIDfineAction.ACTION_CALL_BACK)){
				//�ز��ɹ���رջ�������1.5����Ƴ�Activity���ȴ��ز��绰
				converse_information.setText("�ز��ɹ�");
				CustomLog.e("�ز��ɹ���رջ�������1.5����Ƴ�Activity���ȴ��ز��绰");
				stopRing180();
				DialConfig.saveCallType(AudioConverseActivity.this,"");
				UCSCall.stopRinging();
				
				DialConfig.saveCallType(AudioConverseActivity.this,"");
				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						AudioConverseActivity.this.finish();
					}
				}, 1500);
			}else if(intent.getAction().equals(UIDfineAction.ACTION_ANSWER)){
				//��ͨ��֪ͨ��������ʱ��
				//sendBroadcast(new Intent(UIDfineAction.ACTION_START_TIME));
				//��ͨ��رջ�����
				converse_call_answer.setVisibility(View.GONE);
				converse_call_hangup.setVisibility(View.GONE);
				converse_call_endcall.setVisibility(View.VISIBLE);
				stopRing180();
				//����������
				UCSCall.setSpeakerphone(false);
				converse_call_speaker.setBackgroundResource(R.drawable.converse_speaker);
				open_headset = true;
			}else if(intent.getAction().equals(UIDfineAction.ACTION_DIAL_HANGUP)){
				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						AudioConverseActivity.this.finish();
					}
				}, 1500);
			}else if(intent.getAction().equals(UIDfineAction.ACTION_CALL_TIME)){
				String timer = intent.getStringExtra("timer");
				if(converse_information != null){
					converse_information.setText(timer);
				}
			}else if(intent.getAction().equals("android.intent.action.HEADSET_PLUG")){
				//���ָ����ֻ����ͨ�绰ǰ�յ�����㲥�������������ˣ�����open_headset��Ϊ�жϱ����ͨ���ٽ��ն�����ι㲥����Ч
				if(intent.getIntExtra("state", 0) == 1 && open_headset){
					CustomLog.e(DfineAction.TAG_TCP,"Speaker false");
					converse_call_speaker.setBackgroundResource(R.drawable.converse_speaker);
					speakerPhoneState = UCSCall.isSpeakerphoneOn();
					UCSCall.setSpeakerphone(false);
                }else if(intent.getIntExtra("state", 0) == 0 && open_headset){//headset disconnected
                	//CustomLog.e("headset unplug");
                	if(speakerPhoneState){
                		//CustomLog.e("Speaker true");
                		converse_call_speaker.setBackgroundResource(R.drawable.converse_speaker_down);
                		UCSCall.setSpeakerphone(true);
                	}
                } 
			}
		}
	};
	
	@Override
	protected void onResume() {
		super.onResume();
		if(!UCSService.isConnected()){
			CustomLog.v("���� ����...");
			UCSCall.stopRinging();
			finish();
		}
	}
	
	public void stopRing180(){
		UCSCall.stopCallRinging();
	}
	
	@Override
	public void finish() {
		LoginConfig.saveCurrentCall(this,0);
		super.finish();
	}
	
	@Override
	protected void onDestroy() {
		unregisterReceiver(br);
		DialConfig.saveCallType(this, "");
		stopRing180();
		super.onDestroy();
	}
}
