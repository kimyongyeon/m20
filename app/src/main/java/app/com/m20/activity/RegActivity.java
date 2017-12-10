package app.com.m20.activity;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.Color;
import android.hardware.usb.UsbManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import app.com.m20.Application;
import app.com.m20.db.DbManagement;
import app.com.m20.model.User;
import app.com.m20.utils.AndroidToLinux;
import app.com.m20.R;
import app.com.m20.driver.serial.UsbReceiver;
import app.com.m20.utils.Utils;
import app.com.m20.driver.serial.FTDriver;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

/**
 * Created by kimyongyeon on 2017-11-10.
 */

public class RegActivity extends AppCompatActivity implements View.OnClickListener {
    EditText et;
    String mInputData = "";
    TextView tv;

    private TextView setColorInPartitial(String pre_string, String string, String color, TextView textView) {
        SpannableStringBuilder builder = new SpannableStringBuilder(pre_string + string);
        builder.setSpan(new ForegroundColorSpan(Color.parseColor(color)), 0, pre_string.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.append(builder);
        return textView;
    }

    public void onSetText(String buf) {
        String temp = buf + "\n";
        tv.setText(temp);
    }

    DbManagement dbManagement;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_reg);
        Utils.fullScreen(this);

        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("m20.realm")
                .deleteRealmIfMigrationNeeded()
                .schemaVersion(42)
                .build();
        dbManagement = new DbManagement(config);

        et = findViewById(R.id.etReg);
        tv = findViewById(R.id.txtComment);

        findViewById(R.id.btnOk).setOnClickListener(this);
        findViewById(R.id.btn_del).setOnClickListener(this);
        findViewById(R.id.btn_1).setOnClickListener(this);
        findViewById(R.id.btn_2).setOnClickListener(this);
        findViewById(R.id.btn_3).setOnClickListener(this);
        findViewById(R.id.btn_4).setOnClickListener(this);
        findViewById(R.id.btn_5).setOnClickListener(this);
        findViewById(R.id.btn_6).setOnClickListener(this);
        findViewById(R.id.btn_7).setOnClickListener(this);
        findViewById(R.id.btn_8).setOnClickListener(this);
        findViewById(R.id.btn_9).setOnClickListener(this);
        findViewById(R.id.btn_0).setOnClickListener(this);

        tv.setText("");
        Resources resources = getResources();
        String str = resources.getString(R.string.reg_comment);
        SpannableStringBuilder builder = new SpannableStringBuilder(str);
        builder.setSpan(new ForegroundColorSpan(Color.parseColor("#fff200")), 7, 14, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv.append(builder);

        MediaPlayer mediaPlayer = new MediaPlayer();
        mediaPlayer = MediaPlayer.create(this, R.raw.reg);
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setLooping(false);
        mediaPlayer.start();

        String hexs = Utils.stringToHex0x("3");
        hexs += Utils.stringToHex0x("0");
        hexs += Utils.stringToHex0x(".");
        hexs += Utils.stringToHex0x("1");
//        tv.setText(stringToHex0xs("30.1"));
    }

    public void activityMove(String str) {
        if (str.equals("0")) {
            Intent intent = new Intent(RegActivity.this, PersonCheckupActivity.class);
            startActivity(intent);
            finish();
        } else {
            Intent intent = new Intent(RegActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnOk:

                if (et.getText().toString().equals("")) {
                    Toast.makeText(this, "예약번호를 입력하세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                User user = new User();
                user.setrNumber(Integer.parseInt(et.getText().toString()));
                dbManagement.dbUserInfoSave(user);

                User user2 = dbManagement.dbNoFilterQuery();
                System.out.println(user2.getrNumber());

                if (et.getText().toString().equals("123456")) {
                    //이곳에 버튼 클릭시 일어날 일을 적습니다.
                    Intent intent = new Intent(RegActivity.this, SettingActivity.class);
                    startActivity(intent);
                    finish();

                } else {

                    //////////////////////////////////
                    // Serial
                    //////////////////////////////////
                    Application.serialConnect("reg", this);
                    new Application().getUsbReceiver().writeDataToSerial(AndroidToLinux.requestNumber(et.getText().toString()));
                    //////////////////////////////////
                    // Serial
                    //////////////////////////////////
                }

                break;
            case R.id.btn_0:
                mInputData += "0";
                et.setText(mInputData);
                break;
            case R.id.btn_1:
                mInputData += "1";
                et.setText(mInputData);
                break;
            case R.id.btn_2:
                mInputData += "2";
                et.setText(mInputData);
                break;
            case R.id.btn_3:
                mInputData += "3";
                et.setText(mInputData);
                break;
            case R.id.btn_4:
                mInputData += "4";
                et.setText(mInputData);
                break;
            case R.id.btn_5:
                mInputData += "5";
                et.setText(mInputData);
                break;
            case R.id.btn_6:
                mInputData += "6";
                et.setText(mInputData);
                break;
            case R.id.btn_7:
                mInputData += "7";
                et.setText(mInputData);
                break;
            case R.id.btn_8:
                mInputData += "8";
                et.setText(mInputData);
                break;
            case R.id.btn_9:
                mInputData += "9";
                et.setText(mInputData);
                break;
            case R.id.btn_del:
                if (mInputData.length() == 0) {
                    et.setText("");
                    break;
                }
                mInputData = mInputData.substring(0, mInputData.length() - 1);
                et.setText(mInputData);
                break;
        }

    }

//    @Override
//    public void onDestroy() {
//        if (mUsbReceiver != null) {
//            mUsbReceiver.closeUsbSerial();
//            unregisterReceiver(mUsbReceiver);
//        }
//        super.onDestroy();
//    }

}
