package app.com.m20.activity;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;

import app.com.m20.R;
import app.com.m20.utils.Utils;

/**
 * Created by kimyongyeon on 2017-11-17.
 */

public class EndActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_end);
        Utils.fullScreen(this);

        MediaPlayer mediaPlayer = new MediaPlayer();
        mediaPlayer = MediaPlayer.create(this, R.raw.end);
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setLooping(false);
        mediaPlayer.start();

        mediaPlayer.setOnCompletionListener((tmp) -> {

            Handler handler = new Handler() {
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    startActivity(new Intent(EndActivity.this, IntroActivity.class));
                    finish();
                }
            };
            handler.sendEmptyMessageDelayed(0, 5000);

        });

        RelativeLayout relativeLayout = findViewById(R.id.rrLayout);
        relativeLayout.setOnClickListener((v)-> {
            startActivity(new Intent(EndActivity.this, IntroActivity.class));
            finish();
        });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                break;

        }
    }


}
