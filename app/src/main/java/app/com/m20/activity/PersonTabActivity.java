package app.com.m20.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import app.com.m20.R;
import app.com.m20.utils.Utils;

/**
 * Created by kimyongyeon on 2017-11-10.
 */

public class PersonTabActivity extends AppCompatActivity {

    public static WebView WebView01;
    private final Handler handler = new Handler();
    Handler mHandler = null;
    Context context;

    @Override
    public void onBackPressed() {
        if (WebView01.canGoBack()) {
            WebView01.goBack();
        } else {
            super.onBackPressed();
        }
    }

    private class AndroidBridge {
        @JavascriptInterface
        public void setMessage(final String arg) { // must be final
            handler.post(new Runnable() {
                public void run() {
                    if (arg.equals("y")) {
                        startActivity(new Intent(PersonTabActivity.this, MenuActivity.class));
                        finish();
                    } else {
                        Intent intent = new Intent(PersonTabActivity.this, DetailActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    }
                }
            });
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_result);
        Utils.fullScreen(this);
        context = this;

        WebView01 = new WebView(this);
        WebView01.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        WebView01.addJavascriptInterface(new PersonTabActivity.AndroidBridge(), "HybridApp");
        WebView01.setWebViewClient(new WebViewClient() {
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            }
        });
        WebSettings webSettings = WebView01.getSettings();
        webSettings.setJavaScriptEnabled(true);
        WebView01.loadUrl("file:///android_asset/result.html");

        RelativeLayout relativeLayout = findViewById(R.id.tab_result);
        relativeLayout.addView(WebView01);
    }




}