package app.com.m20.activity;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.UUID;

import app.com.m20.Application;
import app.com.m20.R;
import app.com.m20.driver.serial.FTDriver;
import app.com.m20.driver.serial.UsbReceiver;
import app.com.m20.utils.AndroidToLinux;
import app.com.m20.utils.Utils;
import io.realm.Realm;
import io.realm.RealmSchema;

public class IntroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_intro);
        Utils.fullScreen(this);

        Realm.init(this);

        ImageView imageView = findViewById(R.id.introTitle);
        imageView.setOnClickListener((v) -> {
            startActivity(new Intent(IntroActivity.this, RegActivity.class));
            finish();
        });
    }

    private void serialConnect() {

    }

    // 장치별 유니크 아이디
    @SuppressLint("MissingPermission")
    public String getUniqueID(Context context) {
        final TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        final String tmDevice, tmSerial, androidId;
        tmDevice = "" + tm.getDeviceId();
        tmSerial = "" + tm.getSimSerialNumber();
        androidId = "" + android.provider.Settings.Secure.getString(context.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);

        UUID deviceUuid = new UUID(androidId.hashCode(), ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
        String deviceId = deviceUuid.toString();
        return deviceId;
    }


}
