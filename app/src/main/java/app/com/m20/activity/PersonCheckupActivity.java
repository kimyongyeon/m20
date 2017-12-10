package app.com.m20.activity;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import app.com.m20.R;
import app.com.m20.utils.Utils;

/**
 * Created by kimyongyeon on 2017-11-10.
 */

public class PersonCheckupActivity extends AppCompatActivity {

    Context context;

    class JavaScriptInterface {

        @JavascriptInterface
        public String getFileContents(String assetName){
            return readAssetsContent(context, assetName);
        }

        //Read resources from "assets" folder in string
        public String readAssetsContent(Context context, String name) {
            BufferedReader in = null;
            try {
                StringBuilder buf = new StringBuilder();
                InputStream is = context.getAssets().open(name);
                in = new BufferedReader(new InputStreamReader(is));

                String str;
                boolean isFirst = true;
                while ( (str = in.readLine()) != null ) {
                    if (isFirst)
                        isFirst = false;
                    else
                        buf.append('\n');
                    buf.append(str);
                }
                return buf.toString();
            } catch (IOException e) {
                Log.e("error", "Error opening asset " + name);
            } finally {
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        Log.e("error", "Error closing asset " + name);
                    }
                }
            }

            return null;
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_person_checkup);
        Utils.fullScreen(this);
        context = this;

        MediaPlayer mediaPlayer = new MediaPlayer();
        mediaPlayer = MediaPlayer.create(this, R.raw.check);
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setLooping(false);
        mediaPlayer.start();

        WebView WebView01 = findViewById(R.id.webview);
        WebView01.addJavascriptInterface(new JavaScriptInterface(), "HybridApp");
        WebSettings webSettings = WebView01.getSettings();

        // alert debuging
        WebView01.setWebChromeClient(new WebChromeClient(){
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                return super.onJsAlert(view, url, message, result);
            }
        });


        WebView01.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView view, String url) {
                // 3초후 자동 이동 소스 참조
                Handler handler = new Handler() {
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                        startActivity(new Intent(PersonCheckupActivity.this, PersonTabActivity.class));
                        finish();
                    }
                };
                handler.sendEmptyMessageDelayed(0, 3500);

            }
        });
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        WebView01.loadUrl("file:///android_asset/prog.html");

//        TextView tv = findViewById(R.id.txtComment1);
//        tv.setOnClickListener((v)->{
//
//            Intent intent = new Intent(PersonCheckupActivity.this, PersonTabActivity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            startActivity(intent);
//            overridePendingTransition(R.anim.fade, R.anim.hold);
//            finish();
//
//        });
//
//        Animation startAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.alpha);
//        tv.startAnimation(startAnimation);

        // 3초후 자동 이동 소스 참조
//        Handler handler = new Handler() {
//            public void handleMessage(Message msg) {
//                super.handleMessage(msg);
//                startActivity(new Intent(PersonCheckupActivity.this, PersonTabActivity.class));
//                finish();
//            }
//        };
//        handler.sendEmptyMessageDelayed(0, 3000);


    }


}
