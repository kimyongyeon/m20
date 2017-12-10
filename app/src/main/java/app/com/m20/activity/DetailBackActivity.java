package app.com.m20.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.Window;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import app.com.m20.Application;
import app.com.m20.customview.HoloCircularProgressBar;
import app.com.m20.R;
import app.com.m20.utils.AndroidToLinux;
import app.com.m20.utils.Utils;

/**
 * Created by kimyongyeon on 2017-11-20.
 */

public class DetailBackActivity extends AppCompatActivity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener{

    SeekBar mSeekBar;
    private HoloCircularProgressBar mHoloCircularProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_detail_3);
        Utils.fullScreen(this);

        mHoloCircularProgressBar = (HoloCircularProgressBar) findViewById(R.id.holoCircularProgressBar);

        TextView fronBackText = findViewById(R.id.btn_model_back);
        fronBackText.setText("");
        Resources resources = getResources();
        String str = resources.getString(R.string.front_back_btn_text);
        SpannableStringBuilder builder = new SpannableStringBuilder(str);
        builder.setSpan(new ForegroundColorSpan(Color.parseColor("#fff200")), 3, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        fronBackText.append(builder);


        findViewById(R.id.btn_start).setOnClickListener(this);
        findViewById(R.id.btn_back).setOnClickListener(this);
        findViewById(R.id.btn_arsch_1).setOnClickListener(this);
        findViewById(R.id.btn_arsch_2).setOnClickListener(this);
        findViewById(R.id.btn_latt_1).setOnClickListener(this);
        findViewById(R.id.btn_latt_2).setOnClickListener(this);
        findViewById(R.id.btn_waist_1).setOnClickListener(this);
        findViewById(R.id.btn_waist_2).setOnClickListener(this);
        findViewById(R.id.btn_sideflank_1).setOnClickListener(this);
        findViewById(R.id.btn_sideflank_2).setOnClickListener(this);

        findViewById(R.id.btn_minus).setOnClickListener(this);
        findViewById(R.id.btn_plus).setOnClickListener(this);
        findViewById(R.id.btn_model_back).setOnClickListener(this); // 백버튼
        findViewById(R.id.btn_strong_test).setOnClickListener(this); // 강도테스트


        mSeekBar = findViewById(R.id.btn_sb);
        mSeekBar.setOnSeekBarChangeListener(this);

        findViewById(R.id.btn_minus).setOnTouchListener((v, event) -> {
            longClickMinus();
            return true;
        });
        findViewById(R.id.btn_plus).setOnTouchListener((v, event) -> {
            longClickPlus();
            return true;
        });

        TextView textView = findViewById(R.id.txtTitle);
        SharedPreferences setting ;
        setting = getSharedPreferences("setting", 0);
        textView.setText(setting.getString("main_title", ""));

        if (setting.getString("model_sel", "").equals("arsch")) {
            TextView textView1 = findViewById(R.id.btn_arsch_1);
            textView1.setBackgroundResource(R.drawable.pad_arsch_left);
            TextView textView2 = findViewById(R.id.btn_arsch_2);
            textView2.setBackgroundResource(R.drawable.pad_arsch_right);
            channel="8";
            TextView textView3 = findViewById(R.id.txt_name);
            textView3.setText(getResources().getString(R.string.channel8));
        }
        if (setting.getString("model_sel", "").equals("latt")) {
            TextView textView1 = findViewById(R.id.btn_latt_1);
            textView1.setBackgroundResource(R.drawable.pad_latt_left);
            TextView textView2 = findViewById(R.id.btn_latt_2);
            textView2.setBackgroundResource(R.drawable.pad_latt_right);
            channel="5";
            TextView textView3 = findViewById(R.id.txt_name);
            textView3.setText(getResources().getString(R.string.channel5));
        }
        if (setting.getString("model_sel", "").equals("waist")) {
            TextView textView1 = findViewById(R.id.btn_waist_1);
            textView1.setBackgroundResource(R.drawable.pad_waist_left);
            TextView textView2 = findViewById(R.id.btn_waist_2);
            textView2.setBackgroundResource(R.drawable.pad_waist_right);
            channel="6";
            TextView textView3 = findViewById(R.id.txt_name);
            textView3.setText(getResources().getString(R.string.channel6));
        }
        if (setting.getString("model_sel", "").equals("sideflank")) {
            TextView textView1 = findViewById(R.id.btn_sideflank_1);
            textView1.setBackgroundResource(R.drawable.pad_sideflank_left);
            TextView textView2 = findViewById(R.id.btn_sideflank_2);
            textView2.setBackgroundResource(R.drawable.pad_sideflank_right);
            channel="4";
            TextView textView3 = findViewById(R.id.txt_name);
            textView3.setText(getResources().getString(R.string.channel4));
        }

    }

    public void buttonInit() {
        TextView textView1 = findViewById(R.id.btn_arsch_1);
        TextView textView2 = findViewById(R.id.btn_arsch_2);
        TextView textView3 = findViewById(R.id.btn_latt_1);
        TextView textView4 = findViewById(R.id.btn_latt_2);
        TextView textView5 = findViewById(R.id.btn_waist_1);
        TextView textView6 = findViewById(R.id.btn_waist_2);
        TextView textView7 = findViewById(R.id.btn_sideflank_1);
        TextView textView8 = findViewById(R.id.btn_sideflank_2);

        textView1.setBackgroundResource(R.drawable.pad_arsch_left_off);
        textView2.setBackgroundResource(R.drawable.pad_arsch_right_off);
        textView3.setBackgroundResource(R.drawable.pad_latt_left_off);
        textView4.setBackgroundResource(R.drawable.pad_latt_right_off);
        textView5.setBackgroundResource(R.drawable.pad_waist_left_off);
        textView6.setBackgroundResource(R.drawable.pad_waist_right_off);
        textView7.setBackgroundResource(R.drawable.pad_sideflank_left_off);
        textView8.setBackgroundResource(R.drawable.pad_sideflank_right_off);
    }

    private String channel;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start:
                startActivity(new Intent(DetailBackActivity.this, DetailActivity.class));
                finish();
                break;
            case R.id.btn_model_back:
                startActivity(new Intent(DetailBackActivity.this, DetailFrontActivity.class));
                finish();
                break;
            case R.id.btn_strong_test:
                Toast.makeText(this, "강도테스트 합니다.", Toast.LENGTH_SHORT).show();
                //////////////////////////////////
                // Serial
                //////////////////////////////////
                Application.serialConnect("", this);
                TextView textView = findViewById(R.id.txtPersent);
                String totalPer[] = textView.getText().toString().split("%");
                String per = Utils.sportValPadding(Integer.parseInt(totalPer[0]));
                // 전체 강도
                new Application().getUsbReceiver().writeDataToSerial(AndroidToLinux.programTotalStrongEdit(per+";"));
                // 부분 강도
                new Application().getUsbReceiver().writeDataToSerial(AndroidToLinux.programPartStrongEdit("0000;" + channel + ";" + per + ";"));
                //////////////////////////////////
                // Serial
                //////////////////////////////////
                break;
            case R.id.btn_minus:
                SeekBar seekBar = findViewById(R.id.btn_sb);
                seekBar.setProgress(seekBar.getProgress()-1);
                textView = findViewById(R.id.txtPersent);
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
                startActivity(new Intent(DetailBackActivity.this, DetailActivity.class));
                finish();
                break;
            case R.id.btn_arsch_1: // 둔부
                channel = "8";
                TextView textView3 = findViewById(R.id.txt_name);
                textView3.setText(getResources().getString(R.string.channel8));
                buttonInit();

                TextView textView1 = findViewById(R.id.btn_arsch_1);
                textView1.setBackgroundResource(R.drawable.pad_arsch_left);
                TextView textView2 = findViewById(R.id.btn_arsch_2);
                textView2.setBackgroundResource(R.drawable.pad_arsch_right);

                break;
            case R.id.btn_arsch_2:
                channel = "8";
                textView3 = findViewById(R.id.txt_name);
                textView3.setText(getResources().getString(R.string.channel8));
                buttonInit();
                textView1 = findViewById(R.id.btn_arsch_1);
                textView1.setBackgroundResource(R.drawable.pad_arsch_left);
                textView2 = findViewById(R.id.btn_arsch_2);
                textView2.setBackgroundResource(R.drawable.pad_arsch_right);
                break;
            case R.id.btn_latt_1: // 어깨
                channel = "5";
                textView3 = findViewById(R.id.txt_name);
                textView3.setText(getResources().getString(R.string.channel5));
                buttonInit();
                textView1 = findViewById(R.id.btn_latt_1);
                textView1.setBackgroundResource(R.drawable.pad_latt_left);
                textView2 = findViewById(R.id.btn_latt_2);
                textView2.setBackgroundResource(R.drawable.pad_latt_right);
                break;
            case R.id.btn_latt_2:
                channel = "5";
                textView3 = findViewById(R.id.txt_name);
                textView3.setText(getResources().getString(R.string.channel5));
                buttonInit();
                textView1 = findViewById(R.id.btn_latt_1);
                textView1.setBackgroundResource(R.drawable.pad_latt_left);
                textView2 = findViewById(R.id.btn_latt_2);
                textView2.setBackgroundResource(R.drawable.pad_latt_right);
                break;
            case R.id.btn_waist_1: // 허리
                channel = "6";
                textView3 = findViewById(R.id.txt_name);
                textView3.setText(getResources().getString(R.string.channel6));
                buttonInit();
                textView1 = findViewById(R.id.btn_waist_1);
                textView1.setBackgroundResource(R.drawable.pad_waist_left);
                textView2 = findViewById(R.id.btn_waist_2);
                textView2.setBackgroundResource(R.drawable.pad_waist_right);
                break;
            case R.id.btn_waist_2:
                channel = "6";
                textView3 = findViewById(R.id.txt_name);
                textView3.setText(getResources().getString(R.string.channel6));
                buttonInit();
                textView1 = findViewById(R.id.btn_waist_1);
                textView1.setBackgroundResource(R.drawable.pad_waist_left);
                textView2 = findViewById(R.id.btn_waist_2);
                textView2.setBackgroundResource(R.drawable.pad_waist_right);
                break;
            case R.id.btn_sideflank_1: // 옆구리
                channel = "4";
                textView3 = findViewById(R.id.txt_name);
                textView3.setText(getResources().getString(R.string.channel4));
                buttonInit();
                textView1 = findViewById(R.id.btn_sideflank_1);
                textView1.setBackgroundResource(R.drawable.pad_sideflank_left);
                textView2 = findViewById(R.id.btn_sideflank_2);
                textView2.setBackgroundResource(R.drawable.pad_sideflank_right);
                break;
            case R.id.btn_sideflank_2:
                channel = "4";
                textView3 = findViewById(R.id.txt_name);
                textView3.setText(getResources().getString(R.string.channel4));
                buttonInit();
                textView1 = findViewById(R.id.btn_sideflank_1);
                textView1.setBackgroundResource(R.drawable.pad_sideflank_left);
                textView2 = findViewById(R.id.btn_sideflank_2);
                textView2.setBackgroundResource(R.drawable.pad_sideflank_right);
                break;
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        TextView textView = findViewById(R.id.txtPersent);
        textView.setText(progress + "%");
        mHoloCircularProgressBar.setProgress(progress * 0.01f);
        setText(progress+"");
    }

    private void setText(String per) {

        if (channel.equals("8")) { // 둔부
            TextView textView1 = findViewById(R.id.btn_arsch_1);
            TextView textView2 = findViewById(R.id.btn_arsch_2);
            textView1.setText(per);
            textView2.setText(per);
        }
        else if (channel.equals("5")){ // 어깨
            TextView textView1 = findViewById(R.id.btn_latt_1);
            TextView textView2 = findViewById(R.id.btn_latt_2);
            textView1.setText(per);
            textView2.setText(per);
        }
        else if (channel.equals("6")){ // 허리
            TextView textView1 = findViewById(R.id.btn_waist_1);
            TextView textView2 = findViewById(R.id.btn_waist_2);
            textView1.setText(per);
            textView2.setText(per);
        }
        else if (channel.equals("4")){ // 옆구리
            TextView textView1 = findViewById(R.id.btn_sideflank_1);
            TextView textView2 = findViewById(R.id.btn_sideflank_2);
            textView1.setText(per);
            textView2.setText(per);
        }


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
        setText(seekBar.getProgress()+"");
    }
    public void longClickMinus() {
        SeekBar seekBar = findViewById(R.id.btn_sb);
        seekBar.setProgress(seekBar.getProgress() - 1);
        TextView textView = findViewById(R.id.txtPersent);
        textView.setText(seekBar.getProgress() + "%");
        setText(seekBar.getProgress()+"");

    }

}
