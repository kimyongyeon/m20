package app.com.m20.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import app.com.m20.R;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        Resources res = getResources();
        String text = String.format(res.getString(R.string.hello_small_size), "관리자");
        CharSequence styledText = Html.fromHtml(text);

        TextView tv = findViewById(R.id.mainTitle1);
        tv.setText(text);

        tv.setOnClickListener((v) -> {
            startActivity(new Intent(WelcomeActivity.this, PersonCheckupActivity.class));
            finish();
        });
    }
}
