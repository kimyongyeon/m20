package app.com.m20.activity;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.Color;
import android.hardware.usb.UsbManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import app.com.m20.Application;
import app.com.m20.utils.AndroidToLinux;
import app.com.m20.R;
import app.com.m20.driver.serial.UsbReceiver;
import app.com.m20.utils.Utils;
import app.com.m20.driver.serial.FTDriver;

public class MainActivity extends AppCompatActivity {

    RelativeLayout relativeLayout;
    TextView tv1;
    TextView tv2;

    private FTDriver mSerial;
    private UsbReceiver mUsbReceiver;
    private int mBaudrate;
    private static final String ACTION_USB_PERMISSION = "kr.co.andante.mobiledgs.USB_PERMISSION";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        Utils.fullScreen(this);
        RelativeLayout relativeLayout = findViewById(R.id.mainIdRl);


        //////////////////////////////////
        // Serial
        //////////////////////////////////

//        mUsbReceiver.writeDataToSerial(AndroidToLinux.deviceReg("123456"));
        //////////////////////////////////
        // Serial
        //////////////////////////////////

        tv1 = findViewById(R.id.mainTitle1);
        tv2 = findViewById(R.id.mainTitle2);

        tv1.setText("");
        tv2.setText("");

        Resources resources = getResources();
        String str = resources.getString(R.string.state1);
        SpannableStringBuilder builder = new SpannableStringBuilder(str);
        builder.setSpan(new ForegroundColorSpan(Color.parseColor("#fff200")), 9, 16, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv1.append(builder);

        resources = getResources();
        str = resources.getString(R.string.state1);
        builder = new SpannableStringBuilder(str);
        builder.setSpan(new ForegroundColorSpan(Color.parseColor("#fff200")), 9, 16, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv2.append(builder);

        Display dis = ((WindowManager) getSystemService(WINDOW_SERVICE)).getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int densityDPI = displayMetrics.densityDpi;
        int heightPixels = displayMetrics.heightPixels;
        int widthPixels = displayMetrics.widthPixels;
        int wh = dis.getWidth() * dis.getHeight() / widthPixels * heightPixels;
        TextView textView = new TextView(this);
        textView.setText("dpi : " + densityDPI + ", height : " + heightPixels + ", width : " + widthPixels + ", 해상도 : " + wh);

        relativeLayout.addView(textView);
        relativeLayout.setOnClickListener((v) -> {
            startActivity(new Intent(MainActivity.this, WelcomeActivity.class));
            finish();
        });

    }

    public void onSetText(String buf) {
        String temp = buf + "\n";
        tv1.setText(temp);
    }

//    @Override
//    public void onDestroy() {
//        mUsbReceiver.closeUsbSerial();
//        unregisterReceiver(mUsbReceiver);
//        super.onDestroy();
//    }
}
