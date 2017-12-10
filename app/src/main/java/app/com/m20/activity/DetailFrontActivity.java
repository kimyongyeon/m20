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
import android.widget.Button;
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

public class DetailFrontActivity extends AppCompatActivity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener, View.OnLongClickListener {

    SeekBar mSeekBar;
    private HoloCircularProgressBar mHoloCircularProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_detail_2);
        Utils.fullScreen(this);

        mHoloCircularProgressBar = (HoloCircularProgressBar) findViewById(
                R.id.holoCircularProgressBar);

        TextView fronBackText = findViewById(R.id.btn_model_back);
        fronBackText.setText("");
        Resources resources = getResources();
        String str = resources.getString(R.string.front_back_btn_text);
        SpannableStringBuilder builder = new SpannableStringBuilder(str);
        builder.setSpan(new ForegroundColorSpan(Color.parseColor("#fff200")), 0, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        fronBackText.append(builder);

        findViewById(R.id.btn_start).setOnClickListener(this);
        findViewById(R.id.btn_back).setOnClickListener(this);
        findViewById(R.id.btn_abdomen_1).setOnClickListener(this); // f
        findViewById(R.id.btn_abdomen_2).setOnClickListener(this); // f
        findViewById(R.id.btn_arm_1).setOnClickListener(this); // f
        findViewById(R.id.btn_arm_2).setOnClickListener(this); // f
        findViewById(R.id.btn_bein_1).setOnClickListener(this); // fㅠ
        findViewById(R.id.btn_bein_2).setOnClickListener(this); // f
        findViewById(R.id.btn_brust_1).setOnClickListener(this); // f
        findViewById(R.id.btn_brust_2).setOnClickListener(this); // f
        findViewById(R.id.btn_model_back).setOnClickListener(this); // 백버튼
        findViewById(R.id.btn_strong_test).setOnClickListener(this); // 강도테스트

        findViewById(R.id.btn_minus).setOnClickListener(this);
        findViewById(R.id.btn_plus).setOnClickListener(this);

        findViewById(R.id.btn_plus).setOnLongClickListener(this);
        findViewById(R.id.btn_minus).setOnLongClickListener(this);

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

        if (setting.getString("model_sel", "").equals("abdo")) {

            TextView textView1 = findViewById(R.id.btn_abdomen_1);
            textView1.setBackgroundResource(R.drawable.pad_abdomen_left);
            TextView textView2 = findViewById(R.id.btn_abdomen_2);
            textView2.setBackgroundResource(R.drawable.pad_abdomen_right);

            channel = "2";

            TextView textView3 = findViewById(R.id.txt_name);
            textView3.setText(getResources().getString(R.string.channel2));

        }

        if (setting.getString("model_sel", "").equals("arm")) {

            TextView textView1 = findViewById(R.id.btn_arm_1);
            textView1.setBackgroundResource(R.drawable.pad_arm_left);
            TextView textView2 = findViewById(R.id.btn_arm_2);
            textView2.setBackgroundResource(R.drawable.pad_arm_right);

            channel = "3";

            TextView textView3 = findViewById(R.id.txt_name);
            textView3.setText(getResources().getString(R.string.channel3));

        }

        if (setting.getString("model_sel", "").equals("bein")) {

            TextView textView1 = findViewById(R.id.btn_bein_1);
            textView1.setBackgroundResource(R.drawable.pad_bein_left);
            TextView textView2 = findViewById(R.id.btn_bein_2);
            textView2.setBackgroundResource(R.drawable.pad_bein_right);

            channel = "7";

            TextView textView3 = findViewById(R.id.txt_name);
            textView3.setText(getResources().getString(R.string.channel7));

        }

        if (setting.getString("model_sel", "").equals("brust")) {

            TextView textView1 = findViewById(R.id.btn_brust_1);
            textView1.setBackgroundResource(R.drawable.pad_brust_left);
            TextView textView2 = findViewById(R.id.btn_brust_2);
            textView2.setBackgroundResource(R.drawable.pad_brust_right);

            channel = "1";

            TextView textView3 = findViewById(R.id.txt_name);
            textView3.setText(getResources().getString(R.string.channel1));

        }
    }

    public void buttonInit() {
        TextView textView1 = findViewById(R.id.btn_abdomen_1);
        TextView textView2 = findViewById(R.id.btn_abdomen_2);
        TextView textView3 = findViewById(R.id.btn_arm_1);
        TextView textView4 = findViewById(R.id.btn_arm_2);
        TextView textView5 = findViewById(R.id.btn_bein_1);
        TextView textView6 = findViewById(R.id.btn_bein_2);
        TextView textView7 = findViewById(R.id.btn_brust_1);
        TextView textView8 = findViewById(R.id.btn_brust_2);

        textView1.setBackgroundResource(R.drawable.pad_abdomen_left_off);
        textView2.setBackgroundResource(R.drawable.pad_abdomen_right_off);
        textView3.setBackgroundResource(R.drawable.pad_arm_left_off);
        textView4.setBackgroundResource(R.drawable.pad_arm_right_off);
        textView5.setBackgroundResource(R.drawable.pad_bein_left_off);
        textView6.setBackgroundResource(R.drawable.pad_bein_right_off);
        textView7.setBackgroundResource(R.drawable.pad_brust_left_off);
        textView8.setBackgroundResource(R.drawable.pad_brust_right_off);
    }

    private String channel;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start:
                startActivity(new Intent(DetailFrontActivity.this, DetailActivity.class));
                finish();
                break;
            case R.id.btn_model_back:
                startActivity(new Intent(DetailFrontActivity.this, DetailBackActivity.class));
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
                startActivity(new Intent(DetailFrontActivity.this, DetailActivity.class));
                finish();
                break;
            case R.id.btn_abdomen_1: // 복부
                channel = "2";
                TextView textView3 = findViewById(R.id.txt_name);
                textView3.setText(getResources().getString(R.string.channel2));

                buttonInit();
                TextView textView1 = findViewById(R.id.btn_abdomen_1);
                textView1.setBackgroundResource(R.drawable.pad_abdomen_left);
                TextView textView2 = findViewById(R.id.btn_abdomen_2);
                textView2.setBackgroundResource(R.drawable.pad_abdomen_right);
                break;
            case R.id.btn_abdomen_2:
                channel = "2";
                textView3 = findViewById(R.id.txt_name);
                textView3.setText(getResources().getString(R.string.channel2));
                buttonInit();
                textView1 = findViewById(R.id.btn_abdomen_1);
                textView1.setBackgroundResource(R.drawable.pad_abdomen_left);
                textView2 = findViewById(R.id.btn_abdomen_2);
                textView2.setBackgroundResource(R.drawable.pad_abdomen_right);
                break;
            case R.id.btn_arm_1: // 상완
                channel = "3";
                textView3 = findViewById(R.id.txt_name);
                textView3.setText(getResources().getString(R.string.channel3));
                buttonInit();
                textView1 = findViewById(R.id.btn_arm_1);
                textView1.setBackgroundResource(R.drawable.pad_arm_left);
                textView2 = findViewById(R.id.btn_arm_2);
                textView2.setBackgroundResource(R.drawable.pad_arm_right);
                break;
            case R.id.btn_arm_2:
                channel = "3";
                textView3 = findViewById(R.id.txt_name);
                textView3.setText(getResources().getString(R.string.channel3));
                buttonInit();
                textView1 = findViewById(R.id.btn_arm_1);
                textView1.setBackgroundResource(R.drawable.pad_arm_left);
                textView2 = findViewById(R.id.btn_arm_2);
                textView2.setBackgroundResource(R.drawable.pad_arm_right);
                break;

            case R.id.btn_bein_1: // 허벅
                channel = "7";
                textView3 = findViewById(R.id.txt_name);
                textView3.setText(getResources().getString(R.string.channel7));
                buttonInit();
                textView1 = findViewById(R.id.btn_bein_1);
                textView1.setBackgroundResource(R.drawable.pad_bein_left);
                textView2 = findViewById(R.id.btn_bein_2);
                textView2.setBackgroundResource(R.drawable.pad_bein_right);
                break;
            case R.id.btn_bein_2:
                channel = "7";
                textView3 = findViewById(R.id.txt_name);
                textView3.setText(getResources().getString(R.string.channel7));
                buttonInit();
                textView1 = findViewById(R.id.btn_bein_1);
                textView1.setBackgroundResource(R.drawable.pad_bein_left);
                textView2 = findViewById(R.id.btn_bein_2);
                textView2.setBackgroundResource(R.drawable.pad_bein_right);
                break;
            case R.id.btn_brust_1: // 흉부
                channel = "1";
                textView3 = findViewById(R.id.txt_name);
                textView3.setText(getResources().getString(R.string.channel1));
                buttonInit();
                textView1 = findViewById(R.id.btn_brust_1);
                textView1.setBackgroundResource(R.drawable.pad_brust_left);
                textView2 = findViewById(R.id.btn_brust_2);
                textView2.setBackgroundResource(R.drawable.pad_brust_right);
                break;
            case R.id.btn_brust_2:
                channel = "1";
                textView3 = findViewById(R.id.txt_name);
                textView3.setText(getResources().getString(R.string.channel1));
                buttonInit();
                textView1 = findViewById(R.id.btn_brust_1);
                textView1.setBackgroundResource(R.drawable.pad_brust_left);
                textView2 = findViewById(R.id.btn_brust_2);
                textView2.setBackgroundResource(R.drawable.pad_brust_right);
                break;
        }
    }

    private void setText(String per) {

        if (channel.equals("2")) { // 복부
            TextView textView1 = findViewById(R.id.btn_abdomen_1);
            TextView textView2 = findViewById(R.id.btn_abdomen_2);
            textView1.setText(per);
            textView2.setText(per);
        }
        else if (channel.equals("3")){ // 상완
            TextView textView1 = findViewById(R.id.btn_arm_1);
            TextView textView2 = findViewById(R.id.btn_arm_2);
            textView1.setText(per);
            textView2.setText(per);
        }
        else if (channel.equals("7")){ // 허벅
            TextView textView1 = findViewById(R.id.btn_bein_1);
            TextView textView2 = findViewById(R.id.btn_bein_2);
            textView1.setText(per);
            textView2.setText(per);
        }
        else if (channel.equals("1")){ // 흉부
            TextView textView1 = findViewById(R.id.btn_brust_1);
            TextView textView2 = findViewById(R.id.btn_brust_2);
            textView1.setText(per);
            textView2.setText(per);
        }


    }


    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        TextView textView = findViewById(R.id.txtPersent);
        textView.setText(progress + "%");
        mHoloCircularProgressBar.setProgress(progress * 0.01f);
        setText(progress+"");
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public boolean onLongClick(View v) {
        switch (v.getId()) {
            case R.id.btn_minus:
                SeekBar seekBar = findViewById(R.id.btn_sb);
                seekBar.setProgress(seekBar.getProgress() - 1);
                TextView textView = findViewById(R.id.txtPersent);
                textView.setText(seekBar.getProgress() + "%");
                break;

            case R.id.btn_plus:
                seekBar = findViewById(R.id.btn_sb);
                seekBar.setProgress(seekBar.getProgress() + 1);
                textView = findViewById(R.id.txtPersent);
                textView.setText(seekBar.getProgress() + "%");
                break;
        }
        return true;
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
