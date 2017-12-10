package app.com.m20.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import app.com.m20.customview.HoloCircularProgressBar;
import app.com.m20.R;
import app.com.m20.utils.Utils;

/**
 * Created by kimyongyeon on 2017-11-20.
 */

public class DetailStrongActivity extends AppCompatActivity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener  {

    SeekBar mSeekBar;
    private HoloCircularProgressBar mHoloCircularProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_detail_5);
        Utils.fullScreen(this);

        mHoloCircularProgressBar = (HoloCircularProgressBar) findViewById(
                R.id.holoCircularProgressBar);

        mSeekBar = findViewById(R.id.btn_sb);
        mSeekBar.setEnabled(false);
        mSeekBar.setOnSeekBarChangeListener(this);

        findViewById(R.id.btn_back).setOnClickListener(this);
        findViewById(R.id.btn_stop).setOnClickListener(this);
        findViewById(R.id.btn_start).setOnClickListener(this);

        ImageButton imageButton = findViewById(R.id.btn_back);
        imageButton.setEnabled(false);

//        findViewById(R.id.btn_abdomen_1).setOnClickListener(this); // f
//        findViewById(R.id.btn_abdomen_2).setOnClickListener(this); // f
//        findViewById(R.id.btn_arm_1).setOnClickListener(this); // f
//        findViewById(R.id.btn_arm_2).setOnClickListener(this); // f
//        findViewById(R.id.btn_bein_1).setOnClickListener(this); // f
//        findViewById(R.id.btn_bein_2).setOnClickListener(this); // f
//        findViewById(R.id.btn_brust_1).setOnClickListener(this); // f
//        findViewById(R.id.btn_brust_2).setOnClickListener(this); // f
//
//        findViewById(R.id.btn_arsch_1).setOnClickListener(this);
//        findViewById(R.id.btn_arsch_2).setOnClickListener(this);
//        findViewById(R.id.btn_latt_1).setOnClickListener(this);
//        findViewById(R.id.btn_latt_2).setOnClickListener(this);
//        findViewById(R.id.btn_waist_1).setOnClickListener(this);
//        findViewById(R.id.btn_waist_2).setOnClickListener(this);
//        findViewById(R.id.btn_sideflank_1).setOnClickListener(this);
//        findViewById(R.id.btn_sideflank_2).setOnClickListener(this);

//        findViewById(R.id.btn_minus).setOnClickListener(this);
//        findViewById(R.id.btn_plus).setOnClickListener(this);
        findViewById(R.id.btnOk).setOnClickListener(this);

//        findViewById(R.id.btn_minus).setOnTouchListener((v, event) -> {
//            Log.d("M20", "minus++");
//            longClickMinus();
//            return true;
//        });
//        findViewById(R.id.btn_plus).setOnTouchListener((v, event) -> {
//            Log.d("M20", "plus++");
//            longClickPlus();
//            return true;
//        });
    }

    private String pad;

    @Override
    public void onClick(View v) {
        SharedPreferences setting ;
        SharedPreferences.Editor editor;

        switch (v.getId()) {

            case R.id.btnOk:
                ImageButton imageButton = findViewById(R.id.btn_back);
                imageButton.setEnabled(true);
                LinearLayout ll = findViewById(R.id.subMenu);
                ll.setVisibility(View.GONE);
                break;

            case R.id.btn_minus:
                SeekBar seekBar = findViewById(R.id.btn_sb);
                seekBar.setProgress(seekBar.getProgress()-1);
                TextView textView = findViewById(R.id.txtPersent);
                textView.setText(seekBar.getProgress() + "%");

                mHoloCircularProgressBar.setProgress((seekBar.getProgress()-1) * 0.01f);
                break;

            case R.id.btn_plus:
                seekBar = findViewById(R.id.btn_sb);
                seekBar.setProgress(seekBar.getProgress()+1);
                textView = findViewById(R.id.txtPersent);
                textView.setText(seekBar.getProgress() + "%");

                mHoloCircularProgressBar.setProgress((seekBar.getProgress()+1) * 0.01f);
                break;

            case R.id.btn_back:
                startActivity(new Intent(DetailStrongActivity.this, DetailActivity.class));
                finish();
                break;

            case R.id.btn_stop: // 멈춤
                break;

            case R.id.btn_start: // 시작
                break;

            case R.id.btn_abdomen_1: // 복부

                setting = getSharedPreferences("setting", 0);
                editor = setting.edit();
                editor.putString("model_sel", "abdo");
                editor.commit();

                startActivity(new Intent(DetailStrongActivity.this, DetailFrontActivity.class));
                finish();
                break;
            case R.id.btn_abdomen_2:

                setting = getSharedPreferences("setting", 0);
                editor = setting.edit();
                editor.putString("model_sel", "abdo");
                editor.commit();

                startActivity(new Intent(DetailStrongActivity.this, DetailFrontActivity.class));
                finish();
                break;
            case R.id.btn_arm_1: // 상완

                setting = getSharedPreferences("setting", 0);
                editor = setting.edit();
                editor.putString("model_sel", "arm");
                editor.commit();

                startActivity(new Intent(DetailStrongActivity.this, DetailFrontActivity.class));
                finish();
                break;
            case R.id.btn_arm_2:

                setting = getSharedPreferences("setting", 0);
                editor = setting.edit();
                editor.putString("model_sel", "arm");
                editor.commit();

                startActivity(new Intent(DetailStrongActivity.this, DetailFrontActivity.class));
                finish();
                break;

            case R.id.btn_bein_1: // 허벅다리

                setting = getSharedPreferences("setting", 0);
                editor = setting.edit();
                editor.putString("model_sel", "bein");
                editor.commit();

                startActivity(new Intent(DetailStrongActivity.this, DetailFrontActivity.class));
                finish();
                break;
            case R.id.btn_bein_2:

                setting = getSharedPreferences("setting", 0);
                editor = setting.edit();
                editor.putString("model_sel", "bein");
                editor.commit();

                startActivity(new Intent(DetailStrongActivity.this, DetailFrontActivity.class));
                finish();
                break;
            case R.id.btn_brust_1: // 흉부

                setting = getSharedPreferences("setting", 0);
                editor = setting.edit();
                editor.putString("model_sel", "brust");
                editor.commit();

                startActivity(new Intent(DetailStrongActivity.this, DetailFrontActivity.class));
                finish();
                break;
            case R.id.btn_brust_2:

                setting = getSharedPreferences("setting", 0);
                editor = setting.edit();
                editor.putString("model_sel", "brust");
                editor.commit();

                startActivity(new Intent(DetailStrongActivity.this, DetailFrontActivity.class));
                finish();
                break;

            case R.id.btn_arsch_1: // 둔부

                setting = getSharedPreferences("setting", 0);
                editor = setting.edit();
                editor.putString("model_sel", "arsch");
                editor.commit();

                startActivity(new Intent(DetailStrongActivity.this, DetailBackActivity.class));
                finish();
                break;
            case R.id.btn_arsch_2:

                setting = getSharedPreferences("setting", 0);
                editor = setting.edit();
                editor.putString("model_sel", "arsch");
                editor.commit();

                startActivity(new Intent(DetailStrongActivity.this, DetailBackActivity.class));
                finish();
                break;
            case R.id.btn_latt_1: // 어깨

                setting = getSharedPreferences("setting", 0);
                editor = setting.edit();
                editor.putString("model_sel", "latt");
                editor.commit();

                startActivity(new Intent(DetailStrongActivity.this, DetailBackActivity.class));
                finish();
                break;
            case R.id.btn_latt_2:

                setting = getSharedPreferences("setting", 0);
                editor = setting.edit();
                editor.putString("model_sel", "latt");
                editor.commit();

                startActivity(new Intent(DetailStrongActivity.this, DetailBackActivity.class));
                finish();
                break;
            case R.id.btn_waist_1: // 허리

                setting = getSharedPreferences("setting", 0);
                editor = setting.edit();
                editor.putString("model_sel", "waist");
                editor.commit();

                startActivity(new Intent(DetailStrongActivity.this, DetailBackActivity.class));
                finish();
                break;
            case R.id.btn_waist_2:

                setting = getSharedPreferences("setting", 0);
                editor = setting.edit();
                editor.putString("model_sel", "waist");
                editor.commit();

                startActivity(new Intent(DetailStrongActivity.this, DetailBackActivity.class));
                finish();
                break;
            case R.id.btn_sideflank_1: // 옆구리

                setting = getSharedPreferences("setting", 0);
                editor = setting.edit();
                editor.putString("model_sel", "sideflank");
                editor.commit();

                startActivity(new Intent(DetailStrongActivity.this, DetailBackActivity.class));
                finish();
                break;
            case R.id.btn_sideflank_2:

                setting = getSharedPreferences("setting", 0);
                editor = setting.edit();
                editor.putString("model_sel", "sideflank");
                editor.commit();

                startActivity(new Intent(DetailStrongActivity.this, DetailBackActivity.class));
                finish();
                break;
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        TextView textView = findViewById(R.id.txtPersent);
        textView.setText(progress + "%");
        mHoloCircularProgressBar.setProgress(progress * 0.01f);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    public void longClickPlus() {
        SeekBar seekBar = findViewById(R.id.btn_sb);
        seekBar.setProgress(seekBar.getProgress() + 1);
        TextView textView = findViewById(R.id.txtPersent);
        textView.setText(seekBar.getProgress() + "%");
    }
    public void longClickMinus() {
        SeekBar seekBar = findViewById(R.id.btn_sb);
        seekBar.setProgress(seekBar.getProgress() - 1);
        TextView textView = findViewById(R.id.txtPersent);
        textView.setText(seekBar.getProgress() + "%");
    }

}
