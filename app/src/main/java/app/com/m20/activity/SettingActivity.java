package app.com.m20.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;

import app.com.m20.R;
import app.com.m20.utils.Utils;

public class SettingActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_setting_);
        Utils.fullScreen(this);

        Button btnOk = findViewById(R.id.btn_ok);
        Button btnCancel = findViewById(R.id.btn_cancel);
        ImageButton btnBack = findViewById(R.id.btn_back);

        btnOk.setOnClickListener((v)-> {
            startActivity(new Intent(SettingActivity.this, RegActivity.class));
            finish();
        });
        btnBack.setOnClickListener((v)-> {
            startActivity(new Intent(SettingActivity.this, RegActivity.class));
            finish();
        });
        btnCancel.setOnClickListener((v)-> {
            startActivity(new Intent(SettingActivity.this, RegActivity.class));
            finish();
        });


    }

}
