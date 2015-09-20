package com.hfut.service;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import com.reason.UcsReason;
import com.yzx.api.CallType;
import com.yzx.api.UCSCall;
import com.yzx.api.UCSMessage;
import com.yzx.api.UCSService;
import com.yzx.listenerInterface.CallStateListener;
import com.yzx.listenerInterface.ConnectionListener;
import com.yzx.listenerInterface.MessageListener;
import com.yzx.tcp.packet.UcsMessage;
import com.yzx.tcp.packet.UcsStatus;
import com.yzx.tools.CustomLog;
import com.yzxdemo.action.UIDfineAction;

import com.yzxdemo.tools.DataTools;
import com.yzxdemo.tools.DfineAction;
import com.yzxdemo.tools.DialConfig;
import com.yzxdemo.tools.LoginConfig;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.os.StrictMode;
import android.util.Log;


/**
 * ��̨����/���ӿ�����
 *
 */
public class ConnectionService extends Service implements ConnectionListener,CallStateListener,MessageListener {

	
	public static HashMap<String ,UcsStatus> mapstatus = new HashMap<String ,UcsStatus>();
	
	
	private String cliend_id;
	private String cliend_pwd;
	private String sid;
	private String sid_pwd;
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@SuppressLint("NewApi") @Override
	public void onCreate() {
		super.onCreate();
		mapstatus.clear();
		CustomLog.i("ConnectionService onCreate ... ");
		
		if (android.os.Build.VERSION.SDK_INT >= 14) {
			StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
					.detectDiskReads().detectDiskWrites().detectNetwork()
					.penaltyLog().build());
			StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
					.detectLeakedSqlLiteObjects().penaltyLog().penaltyDeath()
					.build());
		}
		//������Ӽ�����
		UCSService.addConnectionListener(ConnectionService.this);
		//��ӵ绰������
		UCSCall.addCallStateListener(ConnectionService.this);
		//����δ����ʱ��ƵԤ��
		UCSCall.setCameraPreViewStatu(ConnectionService.this,true);
		//�����Ϣ������
		UCSMessage.addMessageListener(ConnectionService.this);
		//��ʼ��SDK
		UCSService.initAction(this);
		UCSService.init(this,true);
		//��ʼ��action����
		UIDfineAction.initAction(ConnectionService.this.getPackageName());
		//����Զ�����Ϣ����  ����λ���븽��
		UCSMessage.addMessageType(UIDfineAction.LOCATION);
		UCSMessage.addMessageType(UIDfineAction.FILE);
       
		IntentFilter ift = new IntentFilter();
        ift.addAction(UIDfineAction.ACTION_LOGIN);
        ift.addAction(UIDfineAction.ACTION_DIAL);
        ift.addAction(UIDfineAction.ACTION_START_TIME);
        ift.addAction(UCSService.ACTION_INIT_SUCCESS);
        registerReceiver(br, ift);
        Log.e("����", "111");
	}
	
	private BroadcastReceiver br = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			if(intent.getAction().equals(UIDfineAction.ACTION_LOGIN)){
				//���µ�¼ʱ�������ͨ����Ҷ�
				UCSCall.hangUp("");
				//����Client�˺ŵ�½
				CustomLog.i(DfineAction.TAG_TCP, "��ʼ�������˺ŵ���");
				if(intent.hasExtra("cliend_id") && intent.hasExtra("cliend_pwd")){
					Log.e("����", "222");
					cliend_id = intent.getStringExtra("cliend_id");
					cliend_pwd = intent.getStringExtra("cliend_pwd");
					sid = intent.getStringExtra("sid");
					sid_pwd = intent.getStringExtra("sid_pwd");
					CustomLog.i(DfineAction.TAG_TCP, "CLIENT_ID:"+cliend_id+"   CLIENT_PWD:"+cliend_pwd);
					if(cliend_id != null && cliend_id.length() > 0
							&& cliend_pwd != null && cliend_pwd.length() > 0){
						if(DataTools.istest){
							//��˾����ר��
							Log.e("����", cliend_id+"����"+cliend_pwd);
							connectionService("http://113.31.89.144","8887",sid,sid_pwd,cliend_id,cliend_pwd);
						}else {
							//������ʹ��
							Log.e("����", cliend_id+"����"+cliend_pwd);
							connectionService(null,null,sid,sid_pwd,cliend_id,cliend_pwd);
						}
					}
				}else if(intent.hasExtra("sid_pwd")){
					Log.e("����", "333");
					cliend_id = null;
					cliend_pwd = null;
					sid = null;
					sid_pwd = null;
					String token = intent.getStringExtra("sid_pwd");
					CustomLog.e(DfineAction.TAG_TCP, "TOKEN:"+token);
					//==================token====================
					connectionService(null,null,token);
					Log.e("����", "333");
				}
			}else if(intent.getAction().equals(UIDfineAction.ACTION_DIAL)){
				int type = intent.getIntExtra("type", 1);
				String uid = intent.getStringExtra(UIDfineAction.CALL_UID);
				String phone = intent.getStringExtra(UIDfineAction.CALL_PHONE);
				//FROM_NUM_KEY�������Ժţ���TO_NUM_KEY�������Ժţ���������Ϊ�ز���ʱ����ʾ�ã�����Ϊ��
				String fromSerNum= "";
				String toSerNum = "";
				if(intent.hasExtra(UIDfineAction.FROM_NUM_KEY)){
					fromSerNum = intent.getStringExtra(UIDfineAction.FROM_NUM_KEY);
				}
				if(intent.hasExtra(UIDfineAction.TO_NUM_KEY)){
					toSerNum = intent.getStringExtra(UIDfineAction.TO_NUM_KEY);
				}
				
				//type:
				//		0��ֱ��
				//		1�����
				//		2:�ز�
				//		3:��Ƶ��Ե�
				//		4:����
				//      5:���ܲ���
				Log.e("����", String.valueOf(type)+phone);
				switch(type){
				
				case 0:
					Log.e("��绰", "3");
					UCSCall.dial(ConnectionService.this,CallType.DIRECT, phone);
					Log.e("��绰", "4");
					break;
				case 1:
					//�����н������أ����Դ���͸���ֶ�Ҳ���Բ����롣
					//UCSCall.dial(ConnectionService.this,CallType.VOIP, uid);
					UCSCall.dial(ConnectionService.this,CallType.VOIP, uid);
					break;
				case 2:
					UCSCall.callBack(ConnectionService.this, phone, fromSerNum, toSerNum);
					break;
				case 3:
					UCSCall.dial(ConnectionService.this,CallType.VIDEO, uid);
					break;
				case 5:
					UCSCall.dial(ConnectionService.this, CallType.CALL_AUTO, phone);
					break;
				}
			}else if(intent.getAction().equals(UIDfineAction.ACTION_LOGOUT)){
				//UCSService.uninit(ConnectionService.this);
			}else if(intent.getAction().equals(UCSService.ACTION_INIT_SUCCESS)){
				CustomLog.i(DfineAction.TAG_TCP, "�����ɹ�  ... ");
				UCSService.openSdkLog(true);//���sdk��־��sd���� yunzhixun/log/YZX_SDK_����.txt
			}else if(intent.getAction().equals(UIDfineAction.ACTION_START_TIME)){
				startCallTimer();
			}
		}
	};
	
	
	

	
	@Override
	public void onDestroy() {
		CustomLog.i(DfineAction.TAG_TCP,"onDestroy ... ");
		unregisterReceiver(br);
		//�Ͽ�������
		UCSService.uninit();
		android.os.Process.killProcess(android.os.Process.myPid());
		super.onDestroy();
	}

	
	//����ʧ�ܻ���߻ص�
	@Override
	public void onConnectionFailed(UcsReason reason) {
		CustomLog.i(DfineAction.TAG_TCP,"CONNECTION_FAILED:"+reason.getReason());
		if(reason.getMsg().length() > 0){
			CustomLog.i(DfineAction.TAG_TCP,"CONNECTION_FAILED:"+reason.getMsg());
		}
		sendBroadcast(new Intent(UIDfineAction.ACTION_TCP_LOGIN_CLIENT_RESPONSE).putExtra(UIDfineAction.RESULT_KEY, 1).putExtra(UIDfineAction.REASON_KEY, reason.getReason()));
		if(reason.getReason() == 300505 || reason.getReason() == 300207){
			sendBroadcast(new Intent(UIDfineAction.ACTION_LOGOUT).putExtra(UIDfineAction.REASON_KEY, reason.getReason()));
			Intent intent = new Intent();
//			intent.setClass(ConnectionService.this,TerminalLoginDialogActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK).putExtra("reason", reason.getReason());
			startActivity(intent);
		}
	}

	//���ӳɹ��ص�
	@Override
	public void onConnectionSuccessful() {
//		IMMessageActivity.msgList.clear();
		sendBroadcast(new Intent(UIDfineAction.ACTION_TCP_LOGIN_CLIENT_RESPONSE).putExtra(UIDfineAction.RESULT_KEY, 0));
		CustomLog.e("CONNECTION_SUCCESS ... ");
		if(cliend_id != null && cliend_id.length() > 0){
			LoginConfig.saveCurrentClientId(ConnectionService.this, cliend_id);
		}
	}
	
	public void connectionService(final String host,final String port,final String sid ,final String sidPwd ,final String cliend_id,final String cliend_pwd){
		new Thread(new Runnable() {
			@Override
			public void run() {
				if(host != null && host.length() > 0){
					UCSService.connect(host,port,sid,sidPwd,cliend_id,cliend_pwd);
				}else{
					UCSService.connect(sid,sidPwd,cliend_id,cliend_pwd);
					
				}
			}
		}).start();
	}
	
	public void connectionService(final String host,final String port,final String token){
		new Thread(new Runnable() {
			@Override
			public void run() {
				if(host != null && host.length() > 0){
					UCSService.connect(host,port,token);
				}else{
					UCSService.connect(token);
				}
			}
		}).start();
	}

	
	
	
	private int second = 0;
	private int minute = 0;
	private int hour = 0;
	private Timer timer = null;
	/**
	 * ͨ����ʱ
	 * @author: xiaozhenhua
	 * @data:2014-6-24 ����10:19:56
	 */
	public void startCallTimer(){
		stopCallTimer();
		if(timer == null){
			timer = new Timer();
		}
		second = 0; //��
		minute = 0; //��
		hour = 0;   //ʱ
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				StringBuffer timer = new StringBuffer();
				second++;
				if (second >= 60) {
					minute++;
					second = 0;
				}
				if (minute >= 60) {
					hour++;
					minute = 0;
				}
				if (hour != 0) {
					if (hour < 10) {
						timer.append(0);
					}
					timer.append(hour);
					timer.append(":");
				}
				if (minute < 10) {
					timer.append(0);
				}
				timer.append(minute);
				timer.append(":");
				if (second < 10) {
					timer.append(0);
				}
				timer.append(second);
				CustomLog.i(DfineAction.TAG_TCP,"timer:"+timer.toString());
				sendBroadcast(new Intent(UIDfineAction.ACTION_CALL_TIME).putExtra("callduration", hour * 3600 + minute * 60 + second).putExtra("timer", timer.toString()));
			}
		}, 0, 1000);
	}
	
	public void stopCallTimer(){
		if (timer != null){
			CustomLog.i(DfineAction.TAG_TCP,"cancel() timer");
			timer.cancel();
			timer=null;
		}
	}
	
	
	//�Է���������ص�
	@Override
	public void onAlerting(String arg0) {
		CustomLog.i(DfineAction.TAG_TCP,"onAlerting CURRENT_ID:"+arg0);
		sendBroadcast(new Intent(UIDfineAction.ACTION_DIAL_STATE).putExtra("state", UCSCall.CALL_VOIP_RINGING_180));
	}

	//�Է���ͨ�ص�
	@Override
	public void onAnswer(String arg0) {
		CustomLog.i(DfineAction.TAG_TCP,"onAnswer CURRENT_ID:"+arg0);
		sendBroadcast(new Intent(UIDfineAction.ACTION_ANSWER));
		startCallTimer();
	}

	//����ʧ�ܻص������ӡ��������reason.getReason()��������ѯ�����뺬��
	@Override
	public void onDialFailed(String arg0, UcsReason reason) {
		DialConfig.saveCallType(ConnectionService.this, "");
		CustomLog.i("onDialFailed CURRENT_ID:"+arg0+"          SERVICE:"+reason.getReason()+"   MSG:"+reason.getMsg());
		voipSwitch(reason);
	}

	private void voipSwitch(UcsReason reason){
		stopCallTimer();
		switch(reason.getReason()){
		case 300210:
			sendBroadcast(new Intent(UIDfineAction.ACTION_DIAL_STATE).putExtra("state", UCSCall.CALL_VOIP_ERROR));
			break;
		case 300211:
			sendBroadcast(new Intent(UIDfineAction.ACTION_DIAL_STATE).putExtra("state", UCSCall.CALL_VOIP_NOT_ENOUGH_BALANCE));
			break;
		case 300212:
			sendBroadcast(new Intent(UIDfineAction.ACTION_DIAL_STATE).putExtra("state", UCSCall.CALL_VOIP_BUSY));
			break;
		case 300213:
			sendBroadcast(new Intent(UIDfineAction.ACTION_DIAL_STATE).putExtra("state", UCSCall.CALL_VOIP_REFUSAL));
			break;
		case 300214:
		case 300215:
			sendBroadcast(new Intent(UIDfineAction.ACTION_DIAL_STATE).putExtra("state", UCSCall.CALL_VOIP_NUMBER_ERROR));
			break;
		case 300216:
			sendBroadcast(new Intent(UIDfineAction.ACTION_DIAL_STATE).putExtra("state", UCSCall.CALL_VOIP_ACCOUNT_FROZEN));
			break;
		case 300217:
			sendBroadcast(new Intent(UIDfineAction.ACTION_DIAL_STATE).putExtra("state", UCSCall.CALL_VOIP_REJECT_ACCOUNT_FROZEN));
			break;
		case 300218:
			sendBroadcast(new Intent(UIDfineAction.ACTION_DIAL_STATE).putExtra("state", UCSCall.CALL_VOIP_ACCOUNT_EXPIRED));
			break;
		case 300219:
			sendBroadcast(new Intent(UIDfineAction.ACTION_DIAL_STATE).putExtra("state", UCSCall.CALL_VOIP_CALLYOURSELF));
			break;
		case 300220:
		case 300224:
			sendBroadcast(new Intent(UIDfineAction.ACTION_DIAL_STATE).putExtra("state", UCSCall.CALL_VOIP_NETWORK_TIMEOUT));
			break;
		case 300221:
			sendBroadcast(new Intent(UIDfineAction.ACTION_DIAL_STATE).putExtra("state", UCSCall.CALL_VOIP_NOT_ANSWER));
			break;
		case 300222:
			sendBroadcast(new Intent(UIDfineAction.ACTION_DIAL_STATE).putExtra("state", UCSCall.CALL_VOIP_TRYING_183));
			break;
		case 300223:
			sendBroadcast(new Intent(UIDfineAction.ACTION_DIAL_STATE).putExtra("state", UCSCall.CALL_VOIP_SESSION_EXPIRATION));
			break;
		case 300225:
			sendBroadcast(new Intent(UIDfineAction.ACTION_DIAL_STATE).putExtra("state", UCSCall.HUNGUP_MYSELF));
			break;
		case 300226:
			sendBroadcast(new Intent(UIDfineAction.ACTION_DIAL_STATE).putExtra("state", UCSCall.HUNGUP_OTHER));
			break;
		case 300267:
			sendBroadcast(new Intent(UIDfineAction.ACTION_DIAL_STATE).putExtra("state", UCSCall.HUNGUP_WHILE_2G));
			break;
		case 300248:
			sendBroadcast(new Intent(UIDfineAction.ACTION_DIAL_STATE).putExtra("state", UCSCall.HUNGUP_MYSELF_REFUSAL));
			break;
		case 300249:
			sendBroadcast(new Intent(UIDfineAction.ACTION_DIAL_STATE).putExtra("state", UCSCall.CALL_VIDEO_DOES_NOT_SUPPORT));
			break;
		default:
			if(reason.getReason() >= 10000 && reason.getReason() <= 20000){//͸��������
				CustomLog.i(DfineAction.TAG_TCP,"KC_REASON:"+reason.getReason());
			}else if(reason.getReason() >= 300233 && reason.getReason() <= 300243){//�ز�
				sendBroadcast(new Intent(UIDfineAction.ACTION_DIAL_STATE).putExtra("state", reason.getReason()));
			}
			break;
		}
	}
	
	
	@Override
	public void onHangUp(String arg0, UcsReason reason) {
		DialConfig.saveCallType(ConnectionService.this, "");
		UCSCall.stopCallRinging();
		sendBroadcast(new Intent(UIDfineAction.ACTION_DIAL_HANGUP).putExtra("state", reason.getReason()));
		CustomLog.i("onHangUp CURRENT_ID:"+arg0+"SERVICE:"+reason.getReason());
		voipSwitch(reason);
	}

	/**
	 * ��������Ϣ
	 * nickName ��ʾ�����ǳ�
	 * userdata ��ʱû�õ�
	 */
	@Override
	public void onIncomingCall(String callId, String callType, String callerNumber ,String nickName, String userdata) {
		CustomLog.v("�յ��µ����� callType="+callType);
		CustomLog.v("�յ��µ�������Ϣ��"+userdata);
		Intent intent = new Intent();
		if(callType.equals("0")){
//			intent.setClass(ConnectionService.this,AudioConverseActivity.class);
		}else if(callType.equals("2")){
			//����
		}else{
			//��Ƶ�绰
//			intent.setClass(ConnectionService.this,VideoConverseActivity.class);
		}
		intent.putExtra("phoneNumber", callerNumber).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.putExtra("inCall", true);
		intent.putExtra("nickName", nickName);
		startActivity(intent);
	}

	/**
	 * ��Ƶͨ�������ص�  reason: 1�������ã�0������һ�㣬2��������
	 */
	@Override
	public void onNetWorkState(int reason) {
		sendBroadcast(new Intent(UIDfineAction.ACTION_NETWORK_STATE).putExtra("state", reason));
	}
	
	/**
	 * ��������Ϣ
	 */
	@Override
	public void onReceiveUcsMessage(UcsReason reason ,UcsMessage message) {
		/*if(reason.getReason() == 0){
			ArrayList<UcsMessage> arrayList = IMMessageActivity.msgList.get(message.getFormuid());
			if(arrayList == null){
				arrayList = new ArrayList<UcsMessage>();
				IMMessageActivity.msgList.put(message.getFormuid(), arrayList);
			}
			if(!arrayList.contains(message)){
				arrayList.add(message);
			}
			CustomLog.v("����Ϣ:"+message.toJSON());
			sendBroadcast(new Intent(UIDfineAction.ACTION));
		}else{
			CustomLog.v("�����ļ�ʧ��:"+reason.getReason());
		}*/
	}

	/**
	 * ��Ϣ�Ƿ��ͳɹ�
	 */
	@Override
	public void onSendUcsMessage(UcsReason reason, UcsMessage message) {
/*		if(message != null){
			ArrayList<UcsMessage> arrayList = IMMessageActivity.msgList.get(message.getTouid());
			if(arrayList == null){
				arrayList = new ArrayList<UcsMessage>();
				IMMessageActivity.msgList.put(message.getTouid(), arrayList);
			}
			if(!arrayList.contains(message)){
				arrayList.add(message);
			}
			CustomLog.v("����Ϣ:"+message.toJSON());
			sendBroadcast(new Intent(UIDfineAction.ACTION));
		}else{
			if(reason != null){
				CustomLog.v("��Ϣ����:"+reason.getReason());
				sendBroadcast(new Intent(UIDfineAction.ACTION_MSG).putExtra(UIDfineAction.REASON_KEY, reason.getReason()));
			}
		}*/
	}

	//���͸������Ȼص�
	@Override
	public void onSendFileProgress(int progress) {
		CustomLog.d(DfineAction.TAG_TCP,"�����ļ�����:"+progress);
		sendBroadcast(new Intent(UIDfineAction.ACTION_SEND_FILE_PROGRESS).putExtra(UIDfineAction.RESULT_KEY, progress));
	}

	//�ز�����ɹ��ص�
	@Override
	public void onCallBackSuccess() {
		CustomLog.d("�ز�����ɹ� ... ");
		sendBroadcast(new Intent(UIDfineAction.ACTION_CALL_BACK));
	}

	//�ļ�������ɻص�
	@Override
	public void onDownloadAttachedProgress(String msgId, String filePaht,int sizeProgrss, int currentProgress) {
/*		CustomLog.d(DfineAction.TAG_TCP,"�����ļ�����:"+sizeProgrss+"    "+currentProgress);
		if(currentProgress >= sizeProgrss){
			CustomLog.d(DfineAction.TAG_TCP,"�����ļ����:"+filePaht);
			for(String key:IMMessageActivity.msgList.keySet()){
				ArrayList<UcsMessage> arrayList = IMMessageActivity.msgList.get(key);
				for(UcsMessage msg :arrayList){
					if(msg.getMsgId().equals(msgId) && msg.getType() != UCSMessage.SEND){
						msg.setMsg(filePaht);
						break;
					}
				}
			}
			sendBroadcast(new Intent(UIDfineAction.ACTION));
		}*/
	}

	//����û�״̬�ص�
	@Override
	public void onUserState(ArrayList list) {
		for(int i = 0 ; i < list.size() ; i ++){
			UcsStatus status = (UcsStatus)list.get(i);
			mapstatus.put(status.getUid(), status);
		}
		sendBroadcast(new Intent(UIDfineAction.ACTION_STATUS));
	}

	@Override
	public void onDTMF(int dtmfCode) {
		CustomLog.d(DfineAction.TAG_TCP,"DTMF:"+dtmfCode);
	}

	@Override
	public void onCameraCapture(String filePaht) {
		CustomLog.d(DfineAction.TAG_TCP,"CAMERACAPTURE:"+filePaht);
	}
}
