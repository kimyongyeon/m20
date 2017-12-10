package app.com.m20.activity;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.hardware.usb.UsbManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import app.com.m20.Application;
import app.com.m20.customview.HoloCircularProgressBar;
import app.com.m20.customview.PopupDialog;
import app.com.m20.R;
import app.com.m20.driver.serial.FTDriver;
import app.com.m20.driver.serial.UsbReceiver;
import app.com.m20.utils.AndroidToLinux;
import app.com.m20.utils.Utils;

/**
 * Created by kimyongyeon on 2017-11-20.
 */

public class DetailStartActivity extends AppCompatActivity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {

    TextView mEllapse;
    private PopupDialog popupDialog;
    SeekBar mSeekBar;
    private HoloCircularProgressBar mHoloCircularProgressBar;

    //스톱워치의 상태를 위한 상수
    final static int IDLE = 0;
    final static int RUNNING = 1;
    final static int PAUSE = 2;
    int mStatus = IDLE;//처음 상태는 IDLE
    long mBaseTime;
    long mPauseTime;
    int iEllapse;

    CountDownTimer mCountDown = null;
    static String timerBuffer; // 04:11:15 등의 경과 시간 문자열이 저장될 버퍼 정의


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_detail_4);
        Utils.fullScreen(this);

        TextView textView = findViewById(R.id.txtTitle);
        SharedPreferences setting;
        setting = getSharedPreferences("setting", 0);
        textView.setText(setting.getString("main_title", ""));

        mHoloCircularProgressBar = (HoloCircularProgressBar) findViewById(
                R.id.holoCircularProgressBar);

        MediaPlayer mediaPlayer = new MediaPlayer();
        mediaPlayer = MediaPlayer.create(this, R.raw.start);
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setLooping(false);
        mediaPlayer.start();
        mediaPlayer.setOnCompletionListener((tmp) -> {

            animationStart();

            mCountDown = new CountDownTimer(1000 * 60 * 20, 1000) {
                public void onTick(long millisUntilFinished) {
                    iEllapse = (int) millisUntilFinished / 1000;
                    secToHHMMSS(iEllapse);
                    mEllapse.setText(timerBuffer);

                }
                public void onFinish() {
                }
            }.start();

            ImageButton imageButton = findViewById(R.id.btn_play);
            imageButton.setBackground(this.getResources().getDrawable(R.drawable.play_btn_on) );

        });

        mEllapse = (TextView) findViewById(R.id.txtEllapse);


        mSeekBar = findViewById(R.id.btn_sb);
        mSeekBar.setOnSeekBarChangeListener(this);

        findViewById(R.id.btn_back).setOnClickListener(this);
        findViewById(R.id.btn_play).setOnClickListener(this);
        findViewById(R.id.btn_pause).setOnClickListener(this);
        findViewById(R.id.btn_stop).setOnClickListener(this);

        findViewById(R.id.btn_minus).setOnClickListener(this);
        findViewById(R.id.btn_plus).setOnClickListener(this);

        findViewById(R.id.btn_minus).setOnTouchListener((v, event) -> {
            longClickMinus();
            return true;
        });
        findViewById(R.id.btn_plus).setOnTouchListener((v, event) -> {
            longClickPlus();
            return true;
        });

        ImageButton imageButton = findViewById(R.id.btn_play);
//        imageButton.callOnClick();

    }

    public void animationStart() {

        TextView textView1 = findViewById(R.id.btn_arsch_1);
        Animation startAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.alpha);
        textView1.startAnimation(startAnimation);
        TextView textView2 = findViewById(R.id.btn_arsch_2);
        startAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.alpha);
        textView2.startAnimation(startAnimation);

        textView1 = findViewById(R.id.btn_latt_1);
        startAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.alpha);
        textView1.startAnimation(startAnimation);
        textView2 = findViewById(R.id.btn_latt_2);
        startAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.alpha);
        textView2.startAnimation(startAnimation);

        textView1 = findViewById(R.id.btn_waist_1);
        startAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.alpha);
        textView1.startAnimation(startAnimation);
        textView2 = findViewById(R.id.btn_waist_2);
        startAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.alpha);
        textView2.startAnimation(startAnimation);

        textView1 = findViewById(R.id.btn_sideflank_1);
        startAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.alpha);
        textView1.startAnimation(startAnimation);
        textView2 = findViewById(R.id.btn_sideflank_2);
        startAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.alpha);
        textView2.startAnimation(startAnimation);

        textView1 = findViewById(R.id.btn_abdomen_1);
        startAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.alpha);
        textView1.startAnimation(startAnimation);
        textView2 = findViewById(R.id.btn_abdomen_2);
        startAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.alpha);
        textView2.startAnimation(startAnimation);

        textView1 = findViewById(R.id.btn_arm_1);
        startAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.alpha);
        textView1.startAnimation(startAnimation);
        textView2 = findViewById(R.id.btn_arm_2);
        startAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.alpha);
        textView2.startAnimation(startAnimation);

        textView1 = findViewById(R.id.btn_bein_1);
        startAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.alpha);
        textView1.startAnimation(startAnimation);
        textView2 = findViewById(R.id.btn_bein_2);
        startAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.alpha);
        textView2.startAnimation(startAnimation);

        textView1 = findViewById(R.id.btn_brust_1);
        startAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.alpha);
        textView1.startAnimation(startAnimation);
        textView2 = findViewById(R.id.btn_brust_2);
        startAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.alpha);
        textView2.startAnimation(startAnimation);

    }

    // 정수로 된 시간을 초단위(sec)로 입력 받아, "04:11:15" 등의 형식의 문자열로 시분초를 저장
    public static void secToHHMMSS(int secs) {
        int hour, min, sec;

        sec = secs % 60;
        min = secs / 60 % 60;
        hour = secs / 3600;

        timerBuffer = String.format("%02d:%02d", min, sec);
    }

    public static int secToMillies(String timerBuffer) {
        String[] array = timerBuffer.split(":");
        return (Integer.parseInt(array[0]) * 1 * 60 * 1000) + (Integer.parseInt(array[1]) * 1000);

    }


    //스톱워치는 위해 핸들러를 만든다.
    Handler mTimer = new Handler() {
        //핸들러는 기본적으로 handleMessage에서 처리한다.
        public void handleMessage(android.os.Message msg) {
            //텍스트뷰를 수정해준다.
            mEllapse.setText(getEllapse());
            //메시지를 다시 보낸다.
            mTimer.sendEmptyMessage(0);//0은 메시지를 구분하기 위한 것
        }

        ;
    };

    String getEllapse() {
        long now = SystemClock.elapsedRealtime();
        long ell = now - mBaseTime;//현재 시간과 지난 시간을 빼서 ell값을 구하고
        //아래에서 포맷을 예쁘게 바꾼다음 리턴해준다.
        String sEll = String.format("%02d:%02d", (ell / 1000) % 60, (ell % 1000) / 10);
        return sEll;
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        mTimer.removeMessages(0);//메시지를 지워서 메모리릭 방지
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                startActivity(new Intent(DetailStartActivity.this, DetailActivity.class));
                finish();
                break;
            case R.id.btn_play: // 운동시작

                ImageButton imageButton = findViewById(R.id.btn_pause);
                imageButton.setBackground(this.getResources().getDrawable(R.drawable.pause_btn_off) );

                ImageButton imageButton2 = findViewById(R.id.btn_play);
                imageButton2.setBackground(this.getResources().getDrawable(R.drawable.play_btn_on) );

                ImageButton imageButton3 = findViewById(R.id.btn_stop);
                imageButton3.setBackground(this.getResources().getDrawable(R.drawable.stop_btn_off) );

                //////////////////////////////////
                // Serial
                //////////////////////////////////
                Application.serialConnect("", this);
                // 근육강화 : 1, 순발력강화 : 2, 근력강화 : 3, 근지구력 강화 : 4, 지구력 강화 : 5
                SharedPreferences setting;
                setting = getSharedPreferences("setting", 0);
                TextView textView = findViewById(R.id.txtPersent);
                String totalPer[] = textView.getText().toString().split("%");
                String per = Utils.sportValPadding(Integer.parseInt(totalPer[0]));
                // 운동아이디;운동SEQ;전체강도
                new Application().getUsbReceiver().writeDataToSerial(AndroidToLinux.programStart(setting.getString("id", "")+";02;"+per));
                //////////////////////////////////
                // Serial
                //////////////////////////////////

                switch (mStatus) {

                    case IDLE:
                        mStatus = RUNNING;
                        mCountDown = new CountDownTimer(1000 * 60 * 20, 1000) {
                            public void onTick(long millisUntilFinished) {
                                iEllapse = (int) millisUntilFinished / 1000;
                                secToHHMMSS(iEllapse);
                                mEllapse.setText(timerBuffer);
                            }

                            public void onFinish() {
                            }
                        }.start();
                        break;

                    case RUNNING:
                        mStatus = PAUSE;//상태를 멈춤으로 표시
                        break;

                    //멈춤이면
                    case PAUSE:
                        //////////////////////////////////
                        // Serial
                        //////////////////////////////////
                        Application.serialConnect("", this);
                        new Application().getUsbReceiver().writeDataToSerial(AndroidToLinux.programStop(""));
                        //////////////////////////////////
                        // Serial
                        //////////////////////////////////

//                        //현재값 가져옴
                        mStatus = RUNNING;
                        int ellapseTime = secToMillies(timerBuffer) + 1000;
                        mCountDown = new CountDownTimer(ellapseTime, 1000) {
                            public void onTick(long millisUntilFinished) {
                                iEllapse = (int) millisUntilFinished / 1000;
                                secToHHMMSS(iEllapse);
                                mEllapse.setText(timerBuffer);
                            }

                            public void onFinish() {
                            }
                        }.start();
                        break;
                }
                break;

            case R.id.btn_pause: // 멈춤

                imageButton = findViewById(R.id.btn_pause);
                imageButton.setBackground(this.getResources().getDrawable(R.drawable.pause_btn_on) );

                imageButton2 = findViewById(R.id.btn_play);
                imageButton2.setBackground(this.getResources().getDrawable(R.drawable.play_btn_off) );

                imageButton3 = findViewById(R.id.btn_stop);
                imageButton3.setBackground(this.getResources().getDrawable(R.drawable.stop_btn_off) );




                if(mCountDown != null)
                    mCountDown.cancel();
                mStatus = PAUSE;//상태를 멈춤으로 표시
                break;

            case R.id.btn_stop:

                 imageButton = findViewById(R.id.btn_pause);
                imageButton.setBackground(this.getResources().getDrawable(R.drawable.pause_btn_off) );

                 imageButton2 = findViewById(R.id.btn_play);
                imageButton2.setBackground(this.getResources().getDrawable(R.drawable.play_btn_off) );

                 imageButton3 = findViewById(R.id.btn_stop);
                imageButton3.setBackground(this.getResources().getDrawable(R.drawable.stop_btn_on) );

                if (mCountDown != null) {
                    mCountDown.cancel(); // 정지
                }

                mStatus = IDLE;//상태를 멈춤으로 표시
                popupDialog = new PopupDialog(this,
                        "[다이얼로그 제목]", // 제목
                        "다이얼로그 내용 표시하기", // 내용
                        leftListener, // 왼쪽 버튼 이벤트
                        rightListener); // 오른쪽 버튼 이벤트
                popupDialog.show();
                break;

            case R.id.btn_minus:
                SeekBar seekBar = findViewById(R.id.btn_sb);
                seekBar.setProgress(seekBar.getProgress() - 1);
                textView = findViewById(R.id.txtPersent);
                textView.setText(seekBar.getProgress() + "%");
                mHoloCircularProgressBar.setProgress((seekBar.getProgress() - 1) * 0.01f);
                break;

            case R.id.btn_plus:
                seekBar = findViewById(R.id.btn_sb);
                seekBar.setProgress(seekBar.getProgress() + 1);
                textView = findViewById(R.id.txtPersent);
                textView.setText(seekBar.getProgress() + "%");
                mHoloCircularProgressBar.setProgress((seekBar.getProgress() + 1) * 0.01f);
                break;
        }
    }

    private View.OnClickListener leftListener = new View.OnClickListener() {
        public void onClick(View v) {
            popupDialog.dismiss();
        }
    };

    private View.OnClickListener rightListener = new View.OnClickListener() {
        public void onClick(View v) {
            popupDialog.dismiss();
            startActivity(new Intent(DetailStartActivity.this, EndActivity.class));
            finish();
        }
    };


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
