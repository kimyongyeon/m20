package app.com.m20.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import app.com.m20.R;
import app.com.m20.utils.Utils;

/**
 * Created by kimyongyeon on 2017-11-10.
 */

public class MenuActivity extends AppCompatActivity implements View.OnClickListener{

    LinearLayout linearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_menu_);
        Utils.fullScreen(this);

        findViewById(R.id.ivClose).setOnClickListener(this);
        findViewById(R.id.btnMenu1).setOnClickListener(this);
        findViewById(R.id.btnMenu2).setOnClickListener(this);
        findViewById(R.id.btnMenu3).setOnClickListener(this);
        findViewById(R.id.btnMenu4).setOnClickListener(this);
        findViewById(R.id.btnMenu5).setOnClickListener(this);
        findViewById(R.id.btnMenu11).setOnClickListener(this);
        findViewById(R.id.btnMenu12).setOnClickListener(this);
        findViewById(R.id.btnMenu13).setOnClickListener(this);
        findViewById(R.id.btnMenu14).setOnClickListener(this);
        findViewById(R.id.btnMenu15).setOnClickListener(this);

        linearLayout = (LinearLayout)findViewById(R.id.subMenu);

    }

    private void transAnimation(boolean bool){

        AnimationSet aniInSet = new AnimationSet(true);
        AnimationSet aniOutSet = new AnimationSet(true);
        aniInSet.setInterpolator(new AccelerateInterpolator());
        Animation transInAni = new TranslateAnimation(0,0,500.0f,0);
        Animation transOutAni = new TranslateAnimation(0,0,0,500.0f);
        transInAni.setDuration(200);
        transOutAni.setDuration(200);
        aniInSet.addAnimation(transInAni);
        aniOutSet.addAnimation(transOutAni);

        if (bool) {
            linearLayout.setAnimation(aniInSet);
            linearLayout.setVisibility(View.VISIBLE);
        } else {
            linearLayout.setAnimation(aniOutSet);
            linearLayout.setVisibility(View.GONE);
        }

    }

    @Override
    public void onClick(View v) {
        Button button = null;
        Resources res = null;
        String text = null;
        SharedPreferences setting ;
        SharedPreferences.Editor editor;
        switch (v.getId()) {
            case R.id.ivClose:
                transAnimation(false);
                break;

            case R.id.btnMenu1:
                TextView textView = findViewById(R.id.txtTitle);
                Resources resources = getResources();
                String str = resources.getString(R.string.program_menu_buldup_title);
                textView.setText(str);
                button = findViewById(R.id.btnMenu11);
                res = getResources();
                text = res.getString(R.string.program_buldup_menu1);
                button.setText(text);

                button = findViewById(R.id.btnMenu12);
                res = getResources();
                text = res.getString(R.string.program_buldup_menu2);
                button.setText(text);

                button = findViewById(R.id.btnMenu13);
                res = getResources();
                text = res.getString(R.string.program_buldup_menu3);
                button.setText(text);

                button = findViewById(R.id.btnMenu14);
                res = getResources();
                text = res.getString(R.string.program_buldup_menu4);
                button.setText(text);

                button = findViewById(R.id.btnMenu15);
                res = getResources();
                text = res.getString(R.string.program_buldup_menu5);
                button.setText(text);

                transAnimation(true);
                break;

            case R.id.btnMenu2:
                textView = findViewById(R.id.txtTitle);
                resources = getResources();
                str = resources.getString(R.string.program_menu_shaping_title);
                textView.setText(str);

                button = findViewById(R.id.btnMenu11);
                res = getResources();
                text = res.getString(R.string.program_shaping_menu1);
                button.setText(text);

                button = findViewById(R.id.btnMenu12);
                res = getResources();
                text = res.getString(R.string.program_shaping_menu2);
                button.setText(text);

                button = findViewById(R.id.btnMenu13);
                res = getResources();
                text = res.getString(R.string.program_shaping_menu3);
                button.setText(text);

                button = findViewById(R.id.btnMenu14);
                res = getResources();
                text = res.getString(R.string.program_shaping_menu4);
                button.setText(text);

                button = findViewById(R.id.btnMenu15);
                res = getResources();
                text = res.getString(R.string.program_shaping_menu5);
                button.setText(text);

                transAnimation(true);
                break;

            case R.id.btnMenu3:
                textView = findViewById(R.id.txtTitle);
                resources = getResources();
                str = resources.getString(R.string.program_menu_healthcare_title);
                textView.setText(str);

                button = findViewById(R.id.btnMenu11);
                res = getResources();
                text = res.getString(R.string.program_healthcare_menu1);
                button.setText(text);

                button = findViewById(R.id.btnMenu12);
                res = getResources();
                text = res.getString(R.string.program_healthcare_menu2);
                button.setText(text);

                button = findViewById(R.id.btnMenu13);
                res = getResources();
                text = res.getString(R.string.program_healthcare_menu3);
                button.setText(text);

                button = findViewById(R.id.btnMenu14);
                res = getResources();
                text = res.getString(R.string.program_healthcare_menu4);
                button.setText(text);

                button = findViewById(R.id.btnMenu15);
                res = getResources();
                text = res.getString(R.string.program_healthcare_menu5);
                button.setText(text);

                transAnimation(true);
                break;

            case R.id.btnMenu4:
                textView = findViewById(R.id.txtTitle);
                resources = getResources();
                str = resources.getString(R.string.program_menu_massage_title);
                textView.setText(str);

                button = findViewById(R.id.btnMenu11);
                res = getResources();
                text = res.getString(R.string.program_massage_menu1);
                button.setText(text);

                button = findViewById(R.id.btnMenu12);
                res = getResources();
                text = res.getString(R.string.program_massage_menu2);
                button.setText(text);

                button = findViewById(R.id.btnMenu13);
                res = getResources();
                text = res.getString(R.string.program_massage_menu3);
                button.setText(text);

                button = findViewById(R.id.btnMenu14);
                res = getResources();
                text = res.getString(R.string.program_massage_menu4);
                button.setText(text);

                button = findViewById(R.id.btnMenu15);
                res = getResources();
                text = res.getString(R.string.program_massage_menu5);
                button.setText(text);

                transAnimation(true);
                break;

            case R.id.btnMenu5: // my program
                textView = findViewById(R.id.txtTitle);
                resources = getResources();
                str = resources.getString(R.string.program_menu_my_title);
                textView.setText(str);

                button = findViewById(R.id.btnMenu11);
                res = getResources();
                text = res.getString(R.string.program_massage_menu1);
                button.setText("");

                button = findViewById(R.id.btnMenu12);
                res = getResources();
                text = res.getString(R.string.program_massage_menu2);
                button.setText("");

                button = findViewById(R.id.btnMenu13);
                res = getResources();
                text = res.getString(R.string.program_massage_menu3);
                button.setText("");

                button = findViewById(R.id.btnMenu14);
                res = getResources();
                text = res.getString(R.string.program_massage_menu4);
                button.setText("");

                button = findViewById(R.id.btnMenu15);
                res = getResources();
                text = res.getString(R.string.program_massage_menu5);
                button.setText("");

                transAnimation(true);
                break;

            case R.id.btnMenu11:

                findViewById(R.id.subMenu).setVisibility(View.GONE);
                Intent intent = new Intent(MenuActivity.this, DetailActivity.class);
                Button button1 = findViewById(R.id.btnMenu11);


                setting = getSharedPreferences("setting", 0);
                editor = setting.edit();
                editor.clear();
                editor.putString("main_title", button1.getText().toString());
                editor.putString("id", strToId(button1.getText().toString()));
                editor.commit();
                intent.putExtra("title", button1.getText());

                startActivity(intent);
                finish();
                break;
            case R.id.btnMenu12:
                findViewById(R.id.subMenu).setVisibility(View.GONE);
                intent = new Intent(MenuActivity.this, DetailActivity.class);
                button1 = findViewById(R.id.btnMenu12);

                setting = getSharedPreferences("setting", 0);
                editor = setting.edit();
                editor.clear();
                editor.putString("main_title", button1.getText().toString());
                editor.putString("id", strToId(button1.getText().toString()));
                editor.commit();

                intent.putExtra("title", button1.getText());
                startActivity(intent);
                finish();
                break;
            case R.id.btnMenu13:
                findViewById(R.id.subMenu).setVisibility(View.GONE);
                intent = new Intent(MenuActivity.this, DetailActivity.class);
                button1 = findViewById(R.id.btnMenu13);

                setting = getSharedPreferences("setting", 0);
                editor = setting.edit();
                editor.clear();
                editor.putString("main_title", button1.getText().toString());
                editor.putString("id", strToId(button1.getText().toString()));
                editor.commit();

                intent.putExtra("title", button1.getText());
                startActivity(intent);
                finish();
                break;
            case R.id.btnMenu14:
                findViewById(R.id.subMenu).setVisibility(View.GONE);
                intent = new Intent(MenuActivity.this, DetailActivity.class);
                button1 = findViewById(R.id.btnMenu14);

                setting = getSharedPreferences("setting", 0);
                editor = setting.edit();
                editor.clear();
                editor.putString("main_title", button1.getText().toString());
                editor.putString("id", strToId(button1.getText().toString()));
                editor.commit();

                intent.putExtra("title", button1.getText());
                startActivity(intent);
                finish();
                break;
            case R.id.btnMenu15:
                findViewById(R.id.subMenu).setVisibility(View.GONE);
                intent = new Intent(MenuActivity.this, DetailActivity.class);
                button1 = findViewById(R.id.btnMenu15);

                setting = getSharedPreferences("setting", 0);
                editor = setting.edit();
                editor.clear();
                editor.putString("main_title", button1.getText().toString());
                editor.putString("id", strToId(button1.getText().toString()));
                editor.commit();

                intent.putExtra("title", button1.getText());
                startActivity(intent);
                finish();
                break;
        }
    }

    private String strToId(String str) {
        Resources res = getResources();

        String text1 = res.getString(R.string.program_buldup_menu1);
        String text2 = res.getString(R.string.program_buldup_menu2);
        String text3 = res.getString(R.string.program_buldup_menu3);
        String text4 = res.getString(R.string.program_buldup_menu4);
        String text5 = res.getString(R.string.program_buldup_menu5);

        String text6 = res.getString(R.string.program_shaping_menu1);
        String text7 = res.getString(R.string.program_shaping_menu2);
        String text8 = res.getString(R.string.program_shaping_menu3);
        String text9 = res.getString(R.string.program_shaping_menu4);
        String text10 = res.getString(R.string.program_shaping_menu5);

        String text11 = res.getString(R.string.program_healthcare_menu1);
        String text12 = res.getString(R.string.program_healthcare_menu2);
        String text13 = res.getString(R.string.program_healthcare_menu3);
        String text14 = res.getString(R.string.program_healthcare_menu4);
        String text15 = res.getString(R.string.program_healthcare_menu5);

        String text16 = res.getString(R.string.program_massage_menu1);
        String text17 = res.getString(R.string.program_massage_menu2);
        String text18 = res.getString(R.string.program_massage_menu3);
        String text19 = res.getString(R.string.program_massage_menu4);
        String text20 = res.getString(R.string.program_massage_menu5);

        if (str.equals(text1)) {
            return "01";
        }
        else if (str.equals(text2)) {
            return "02";
        }
        else if (str.equals(text3)) {
            return "03";
        }
        else if (str.equals(text4)) {
            return "04";
        }
        else if (str.equals(text5)) {
            return "05";
        }
        else if (str.equals(text6)) {
            return "06";
        }
        else if (str.equals(text7)) {
            return "07";
        }
        else if (str.equals(text8)) {
            return "08";
        }
        else if (str.equals(text9)) {
            return "09";
        }
        else if (str.equals(text10)) {
            return "10";
        }
        else if (str.equals(text11)) {
            return "11";
        }
        else if (str.equals(text12)) {
            return "12";
        }
        else if (str.equals(text13)) {
            return "13";
        }
        else if (str.equals(text14)) {
            return "14";
        }
        else if (str.equals(text15)) {
            return "15";
        }
        else if (str.equals(text16)) {
            return "16";
        }
        else if (str.equals(text17)) {
            return "17";
        }
        else if (str.equals(text18)) {
            return "18";
        }
        else if (str.equals(text19)) {
            return "19";
        }
        else if (str.equals(text20)) {
            return "20";
        } else {
            return "01";
        }
    }

}