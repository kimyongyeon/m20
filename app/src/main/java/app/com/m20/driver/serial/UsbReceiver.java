package app.com.m20.driver.serial;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.hardware.usb.UsbManager;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import app.com.m20.activity.PersonCheckupActivity;
import app.com.m20.activity.RegActivity;
import app.com.m20.db.DbManagement;
import app.com.m20.driver.serial.FTDriver;
import app.com.m20.driver.serial.FTDriverUtil;
import app.com.m20.activity.MainActivity;
import app.com.m20.model.Body;
import app.com.m20.model.BodyFat;
import app.com.m20.model.User;
import app.com.m20.utils.Utils;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import io.realm.RealmResults;

import static android.support.v4.content.ContextCompat.startActivity;


public class UsbReceiver extends BroadcastReceiver {
	private Boolean SHOW_DEBUG = false;
	private String TAG = "HDJ";

	private Context mContext;
	private Activity mActivity;
	private FTDriver mSerial;
	private Handler mHandler = new Handler();
	private StringBuilder mText;

	private static final String ACTION_USB_PERMISSION = "kr.co.andante.mobiledgs.USB_PERMISSION";

	private boolean mStop = false;
	private boolean mRunningMainLoop = false;

	private static final int TEXT_MAX_SIZE = 8192;

	// Default settings
	private int mTextFontSize       = 12;
	private Typeface mTextTypeface  = Typeface.MONOSPACE;
	private int mDisplayType        = FTDriverUtil.DISP_CHAR;
	private int mBaudrate           = FTDriver.BAUD9600;
	private int mDataBits           = FTDriver.FTDI_SET_DATA_BITS_8;
	private int mParity             = FTDriver.FTDI_SET_DATA_PARITY_NONE;
	private int mStopBits           = FTDriver.FTDI_SET_DATA_STOP_BITS_1;
	private int mFlowControl        = FTDriver.FTDI_SET_FLOW_CTRL_NONE;
	private int mBreak              = FTDriver.FTDI_SET_NOBREAK;

	public UsbReceiver(Activity activity, FTDriver serial)
	{
		mActivity = activity;
		mContext = activity.getBaseContext();
		mSerial = serial;
	}

	public int GetTextFontSize()
	{
		return mTextFontSize;
	}

	public int GetBaudrate()
	{
		return mBaudrate;
	}

	// Load default baud rate
	public int loadDefaultBaudrate() {
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(mContext);
		String res = pref.getString("baudrate_list", Integer.toString(FTDriver.BAUD9600));
		return Integer.valueOf(res);
	}

	public void loadDefaultSettingValues() {
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(mContext);
		String res = pref.getString("display_list", Integer.toString(FTDriverUtil.DISP_CHAR));
		mDisplayType = Integer.valueOf(res);

		res = pref.getString("fontsize_list", Integer.toString(12));
		mTextFontSize = Integer.valueOf(res);

		res = pref.getString("typeface_list", Integer.toString(3));
		switch(Integer.valueOf(res)){
		case 0:
			mTextTypeface = Typeface.DEFAULT;
			break;
		case 1:
			mTextTypeface = Typeface.SANS_SERIF;
			break;
		case 2:
			mTextTypeface = Typeface.SERIF;
			break;
		case 3:
			mTextTypeface = Typeface.MONOSPACE;
			break;
		}

		res = pref.getString("readlinefeedcode_list", Integer.toString(FTDriverUtil.LINEFEED_CODE_CRLF));
		FTDriverUtil.mReadLinefeedCode = Integer.valueOf(res);

		res = pref.getString("writelinefeedcode_list", Integer.toString(FTDriverUtil.LINEFEED_CODE_CRLF));
		FTDriverUtil.mWriteLinefeedCode = Integer.valueOf(res);

		res = pref.getString("email_edittext", "@gmail.com");

		res = pref.getString("databits_list", Integer.toString(FTDriver.FTDI_SET_DATA_BITS_8));
		mDataBits = Integer.valueOf(res);
		mSerial.setSerialPropertyDataBit(mDataBits, FTDriver.CH_A);

		res = pref.getString("parity_list", Integer.toString(FTDriver.FTDI_SET_DATA_PARITY_NONE));
		mParity = Integer.valueOf(res) << 8; // parity_list's number is 0 to 4
		mSerial.setSerialPropertyParity(mParity, FTDriver.CH_A);

		res = pref.getString("stopbits_list", Integer.toString(FTDriver.FTDI_SET_DATA_STOP_BITS_1));
		mStopBits = Integer.valueOf(res) << 11; // stopbits_list's number is 0 to 2
		mSerial.setSerialPropertyStopBits(mStopBits, FTDriver.CH_A);

		res = pref.getString("flowcontrol_list", Integer.toString(FTDriver.FTDI_SET_FLOW_CTRL_NONE));
		mFlowControl = Integer.valueOf(res) << 8;
		mSerial.setFlowControl(FTDriver.CH_A, mFlowControl);

		res = pref.getString("break_list", Integer.toString(FTDriver.FTDI_SET_NOBREAK));
		mBreak = Integer.valueOf(res) << 14;
		mSerial.setSerialPropertyBreak(mBreak, FTDriver.CH_A);

		mSerial.setSerialPropertyToChip(FTDriver.CH_A);
	}

	public void openUsbSerial() {
		if (!mSerial.isConnected()) {
			if (SHOW_DEBUG) {
				Log.d(TAG, "onNewIntent begin");
			}
			mBaudrate = loadDefaultBaudrate();
			if (!mSerial.begin(mBaudrate)) {
				Toast.makeText(mContext, "cannot open", Toast.LENGTH_SHORT).show();
				return;
			} else {
				Toast.makeText(mContext, "connected", Toast.LENGTH_SHORT).show();
			}
		}

		if (!mRunningMainLoop) {
			mainloop();
		}

	}

	public void closeUsbSerial() {
		detachedUi();
		mStop = true;
		mSerial.end();
	}

	public void writeDataToSerial(String strWrite) {
		strWrite = FTDriverUtil.changeLinefeedcode(strWrite);
		if (SHOW_DEBUG) {
			Log.d(TAG, "FTDriver Write(" + strWrite.length() + ") : " + strWrite);
		}
		
		if(mSerial.isConnected())
			mSerial.write(strWrite.getBytes(), strWrite.length());
		else
			Toast.makeText(mContext, "Usb is disconnection", Toast.LENGTH_SHORT).show();
	}

	DbManagement dbManagement;
	public void mainloop() {

		RealmConfiguration config = new RealmConfiguration.Builder()
				.name("m20.realm")
				.schemaVersion(42)
				.build();
		dbManagement = new DbManagement(config);
		mStop = false;
		mRunningMainLoop = true;
		Toast.makeText(mContext, "connected", Toast.LENGTH_SHORT).show();
		if (SHOW_DEBUG) {
			Log.d(TAG, "start mainloop");
		}
		new Thread(mLoop).start();
	}

	private Runnable mLoop = new Runnable() {

		@Override
		public void run() {

			int len;
			byte[] rbuf = new byte[4096];
			int bMsgCnt = 0;
			
			for (;;) {// this is the main loop for transferring

				len = mSerial.read(rbuf);
				rbuf[len] = 0;
				
				if (len > 0) {
					
					if(bMsgCnt == 0)
						mText = new StringBuilder();
					
					if(len < 33)
					{
						OnReadMessage(rbuf, len);
						bMsgCnt += len;
					}
					else if(len == 33)
					{
						OnReadMessage(rbuf, len);
						bMsgCnt = len;
					}
					
					if(bMsgCnt >= 33)
					{
						bMsgCnt = 0;
					
						mHandler.post(new Runnable() {
							public void run() {

								String str = Utils.hexToString(mText.toString());

								String array[] = str.split(";");
								String key = array[0];
								if (key.equals("S10")) {
									// 맥어드레스
									String mac = array[1];
								}
								else if (key.equals("S30")) { // 체지방 진행 여부 판단해서 화면 이동
									String sel = array[1]; // sel
									User user = new User();
									user.setSel(sel);
									dbManagement.dbSelSave(user);
									if (sel.equals("0")) { // 체지방 진행 YES
										((RegActivity)mActivity).activityMove("0");
									} else { // 체지방 진행 NO
										((RegActivity)mActivity).activityMove("1");
									}
								}
								else if (key.equals("S20")) { // 설정값
									String totalBody = array[1]; // 전체값
									String thorax = array[2]; // 흉부
									String stomach = array[3]; // 복부
									String arm = array[4]; // 상완
									String thigh = array[5]; // 허벅다리
									String shoulder = array[6]; // 어깨
									String waist = array[7]; // 허리
									String side = array[8]; // 옆구리
									String posterior = array[9]; // 둔부

									Body body = new Body();
									body.setTotalBodySet(totalBody);
									body.setThorax(thorax);
									body.setStomach(stomach);
									body.setArm(arm);
									body.setThigh(thigh);
									body.setShoulder(shoulder);
									body.setWaist(waist);
									body.setSide(side);
									body.setPosterior(posterior);
									dbManagement.dbDefaultBodySettingSave(body);
								}
								else if (key.equals("S21")) { // 마이프로그램1
									String totalBody = array[1]; // 전체값
									String thorax = array[2]; // 흉부
									String stomach = array[3]; // 복부
									String arm = array[4]; // 상완
									String thigh = array[5]; // 허벅다리
									String shoulder = array[6]; // 어깨
									String waist = array[7]; // 허리
									String side = array[8]; // 옆구리
									String posterior = array[9]; // 둔부

									Body body = new Body();
									body.setTotalBodySet(totalBody);
									body.setThorax(thorax);
									body.setStomach(stomach);
									body.setArm(arm);
									body.setThigh(thigh);
									body.setShoulder(shoulder);
									body.setWaist(waist);
									body.setSide(side);
									body.setPosterior(posterior);

									User user = dbManagement.dbNoFilterQuery();
									RealmList realmList = user.getProgram();
									realmList.add(body);
									dbManagement.dbUserInfoSave(user);
								}
								else if (key.equals("S22")) { // 마이프로그램2
									String totalBody = array[1]; // 전체값
									String thorax = array[2]; // 흉부
									String stomach = array[3]; // 복부
									String arm = array[4]; // 상완
									String thigh = array[5]; // 허벅다리
									String shoulder = array[6]; // 어깨
									String waist = array[7]; // 허리
									String side = array[8]; // 옆구리
									String posterior = array[9]; // 둔부

									Body body = new Body();
									body.setTotalBodySet(totalBody);
									body.setThorax(thorax);
									body.setStomach(stomach);
									body.setArm(arm);
									body.setThigh(thigh);
									body.setShoulder(shoulder);
									body.setWaist(waist);
									body.setSide(side);
									body.setPosterior(posterior);

									User user = dbManagement.dbNoFilterQuery();
									RealmList realmList = user.getProgram();
									realmList.add(body);
									dbManagement.dbUserInfoSave(user);
								}
								else if (key.equals("S23")) { // 마이프로그램3
									String totalBody = array[1]; // 전체값
									String thorax = array[2]; // 흉부
									String stomach = array[3]; // 복부
									String arm = array[4]; // 상완
									String thigh = array[5]; // 허벅다리
									String shoulder = array[6]; // 어깨
									String waist = array[7]; // 허리
									String side = array[8]; // 옆구리
									String posterior = array[9]; // 둔부

									Body body = new Body();
									body.setTotalBodySet(totalBody);
									body.setThorax(thorax);
									body.setStomach(stomach);
									body.setArm(arm);
									body.setThigh(thigh);
									body.setShoulder(shoulder);
									body.setWaist(waist);
									body.setSide(side);
									body.setPosterior(posterior);

									User user = dbManagement.dbNoFilterQuery();
									RealmList realmList = user.getProgram();
									realmList.add(body);
									dbManagement.dbUserInfoSave(user);
								}
								else if (key.equals("S24")) { // 마이프로그램4
									String totalBody = array[1]; // 전체값
									String thorax = array[2]; // 흉부
									String stomach = array[3]; // 복부
									String arm = array[4]; // 상완
									String thigh = array[5]; // 허벅다리
									String shoulder = array[6]; // 어깨
									String waist = array[7]; // 허리
									String side = array[8]; // 옆구리
									String posterior = array[9]; // 둔부

									Body body = new Body();
									body.setTotalBodySet(totalBody);
									body.setThorax(thorax);
									body.setStomach(stomach);
									body.setArm(arm);
									body.setThigh(thigh);
									body.setShoulder(shoulder);
									body.setWaist(waist);
									body.setSide(side);
									body.setPosterior(posterior);

									User user = dbManagement.dbNoFilterQuery();
									RealmList realmList = user.getProgram();
									realmList.add(body);
									dbManagement.dbUserInfoSave(user);
								}
								else if (key.equals("S25")) { // 마이프로그램5
									String totalBody = array[1]; // 전체값
									String thorax = array[2]; // 흉부
									String stomach = array[3]; // 복부
									String arm = array[4]; // 상완
									String thigh = array[5]; // 허벅다리
									String shoulder = array[6]; // 어깨
									String waist = array[7]; // 허리
									String side = array[8]; // 옆구리
									String posterior = array[9]; // 둔부

									Body body = new Body();
									body.setTotalBodySet(totalBody);
									body.setThorax(thorax);
									body.setStomach(stomach);
									body.setArm(arm);
									body.setThigh(thigh);
									body.setShoulder(shoulder);
									body.setWaist(waist);
									body.setSide(side);
									body.setPosterior(posterior);

									User user = dbManagement.dbNoFilterQuery();
									RealmList realmList = user.getProgram();
									realmList.add(body);
									dbManagement.dbUserInfoSave(user);
								}
								else if (key.equals("S31")) { // 이름,키,몸무게
									String name = array[1]; // 이름
									String height = array[2]; // 키
									String weight = array[3]; // 몸무게
									User user = dbManagement.dbNoFilterQuery();
									user.setName(name);
									user.setHeight(height);
									user.setWeight(weight);
									dbManagement.dbUserInfoSave(user);
								}
								else if (key.equals("S32")) { // 체지방 측정 결과
									String bodyFat = array[1]; // 체지방
									String weight = array[2]; // 체중
									BodyFat bodyFat1 = new BodyFat();
									bodyFat1.setBodyFat(bodyFat);
									bodyFat1.setWeight(weight);
									dbManagement.dbBodyFatSave(bodyFat1);
								}
								else if (key.equals("S35")) { // 추천 프로그램
									String programId = array[1]; // 추천프로그램ID
									String totalBody = array[2]; // 전체값
									String thorax = array[3]; // 흉부
									String stomach = array[4]; // 복부
									String arm = array[5]; // 상완
									String thigh = array[6]; // 허벅다리
									String shoulder = array[7]; // 어깨
									String waist = array[8]; // 허리
									String side = array[9]; // 옆구리
									String posterior = array[10]; // 둔부

									Body body = new Body();
									body.setProgramId(programId);
									body.setTotalBodySet(totalBody);
									body.setThorax(thorax);
									body.setStomach(stomach);
									body.setArm(arm);
									body.setThigh(thigh);
									body.setShoulder(shoulder);
									body.setWaist(waist);
									body.setSide(side);
									body.setPosterior(posterior);

									User user = dbManagement.dbNoFilterQuery();
									RealmList realmList = user.getProgram();
									realmList.add(body);
									dbManagement.dbUserInfoSave(user);
								}
								else if (key.equals("S50")) {
									String bodyFat = array[1];
									String weight = array[2];
								} else if (key.equals("S51")) {
									// 부위별
									String part = array[1];
								} else if (key.equals("S99")) {
									// 1: 정상, 2: 사용중, 3: 대기중, 4: 리눅스통신 Error, 5: AVR 통신 Error
									// 6: 슈트 이상, 7: Channel 이상, 8: Connector: 이상, 9~00: Reserved
									String errorCode = array[1];
									if (errorCode.equals("01")) {
										Toast.makeText(mContext, "정상", Toast.LENGTH_SHORT).show();
									}
									else if (errorCode.equals("02")) {
										Toast.makeText(mContext, "사용중", Toast.LENGTH_SHORT).show();
									}
									else if (errorCode.equals("03")) {
										Toast.makeText(mContext, "대기중", Toast.LENGTH_SHORT).show();
									}
									else if (errorCode.equals("04")) {
										Toast.makeText(mContext, "리눅스 통신 Error", Toast.LENGTH_SHORT).show();
									}
									else if (errorCode.equals("05")) {
										Toast.makeText(mContext, "AVR 통신 Error", Toast.LENGTH_SHORT).show();
									}
									else if (errorCode.equals("06")) {
										Toast.makeText(mContext, "슈트 이상", Toast.LENGTH_SHORT).show();
									}
									else if (errorCode.equals("07")) {
										Toast.makeText(mContext, "Channel 이상", Toast.LENGTH_SHORT).show();
									}
									else if (errorCode.equals("08")) {
										Toast.makeText(mContext, "Connect 이상", Toast.LENGTH_SHORT).show();
									} else {
										Toast.makeText(mContext, "Reserverd", Toast.LENGTH_SHORT).show();
									}

								}
							}
						});
					}
				}

				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				if (mStop) {
					mRunningMainLoop = false;
					return;
				}
			}
		}
	};
	
	private void OnReadMessage(byte[] rbuf, int len)
	{
		switch (mDisplayType) {
		case FTDriverUtil.DISP_CHAR:
			FTDriverUtil.setSerialDataToTextView(mText, mDisplayType, rbuf, len, "", "");
			break;
		case FTDriverUtil.DISP_DEC:
			FTDriverUtil.setSerialDataToTextView(mText, mDisplayType, rbuf, len, "013", "010");
			break;
		case FTDriverUtil.DISP_HEX:
			FTDriverUtil.setSerialDataToTextView(mText, mDisplayType, rbuf, len, "0d", "0a");
			break;
		}
		
		if (SHOW_DEBUG) {
			Log.d(TAG, "Read Length : " + len +"/" +mText);
		}
	}

	private void detachedUi() {
		if(mSerial.isConnected())
			Toast.makeText(mContext, "disconnect", Toast.LENGTH_SHORT).show();
	}

	public void onReceive(Context context, Intent intent) {
		String action = intent.getAction();

		if (UsbManager.ACTION_USB_DEVICE_ATTACHED.equals(action)) {
			if (SHOW_DEBUG) {
				Log.d(TAG, "Device attached");
				Toast.makeText(mContext, "Device attached", Toast.LENGTH_SHORT).show();
			}
			if (!mSerial.isConnected()) {
				if (SHOW_DEBUG) {
					Log.d(TAG, "Device attached begin");
					Toast.makeText(mContext, "Device attached begin", Toast.LENGTH_SHORT).show();
				}
				mBaudrate = loadDefaultBaudrate();
				mSerial.begin(mBaudrate);
				loadDefaultSettingValues();
			}
			if (!mRunningMainLoop) {
				if (SHOW_DEBUG) {
					Log.d(TAG, "Device attached mainloop");
					Toast.makeText(mContext, "Device attached mainloop", Toast.LENGTH_SHORT).show();
				}
				mainloop();
			}
		} else if (UsbManager.ACTION_USB_DEVICE_DETACHED.equals(action)) {
			if (SHOW_DEBUG) {
				Log.d(TAG, "Device detached");
				Toast.makeText(mContext, "Device detached", Toast.LENGTH_SHORT).show();
			}
			mStop = true;
			detachedUi();
			mSerial.usbDetached(intent);
			mSerial.end();
		} else if (ACTION_USB_PERMISSION.equals(action)) {
			if (SHOW_DEBUG) {
				Log.d(TAG, "Request permission");
				Toast.makeText(mContext, "Request permission", Toast.LENGTH_SHORT).show();
			}
			synchronized (this) {
				if (!mSerial.isConnected()) {
					if (SHOW_DEBUG) {
						Log.d(TAG, "Request permission begin");
						Toast.makeText(mContext, "Request permission begin", Toast.LENGTH_SHORT).show();
					}
					mBaudrate = loadDefaultBaudrate();
					mSerial.begin(mBaudrate);
					loadDefaultSettingValues();
				}
			}
			if (!mRunningMainLoop) {
				if (SHOW_DEBUG) {
					Log.d(TAG, "Request permission mainloop");
					Toast.makeText(mContext, "Request permission mainloop", Toast.LENGTH_SHORT).show();
				}
				mainloop();
			}
		}
	}
}
