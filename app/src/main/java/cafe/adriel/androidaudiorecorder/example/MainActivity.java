package cafe.adriel.androidaudiorecorder.example;

import android.Manifest;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import cafe.adriel.androidaudiorecorder.AndroidAudioRecorder;
import cafe.adriel.androidaudiorecorder.AudioRecorderActivity;
import cafe.adriel.androidaudiorecorder.model.AudioChannel;
import cafe.adriel.androidaudiorecorder.model.AudioSampleRate;
import cafe.adriel.androidaudiorecorder.model.AudioSource;
import android.util.Log;
import android.os.Handler;
import android.content.Context;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_RECORD_AUDIO = 0;
    private static final String AUDIO_FILE_PATH =
            Environment.getExternalStorageDirectory().getPath() + "/recorded_audio.wav";

    public static String picName;
    public static String textName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setBackgroundDrawable(
                    new ColorDrawable(ContextCompat.getColor(this, R.color.colorPrimaryDark)));
        }

        Util.requestPermission(this, Manifest.permission.RECORD_AUDIO);
        Util.requestPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        findViewById(R.id.celebName).setVisibility(View.INVISIBLE);
        findViewById(R.id.celebPic).setVisibility(View.INVISIBLE);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_RECORD_AUDIO) {
            if (resultCode == RESULT_OK) {

                findViewById(R.id.celebName).setVisibility(View.INVISIBLE);
                findViewById(R.id.celebPic).setVisibility(View.INVISIBLE);

                Runnable r = new Runnable() {
                    @Override
                    public void run(){
                        String lol = AudioRecorderActivity.textName;
                        ((TextView)findViewById(R.id.celebName)).setText(lol);

                        ((ImageView)findViewById(R.id.celebPic)).setImageResource(find(lol));

                }};



                Handler h = new Handler();
                h.postDelayed(r, 1000); // <-- the "1000" is the delay time in miliseconds.



                findViewById(R.id.celebName).setVisibility(View.VISIBLE);
                findViewById(R.id.celebPic).setVisibility(View.VISIBLE);


               // Toast.makeText(this, "Audio recorded successfully!", Toast.LENGTH_SHORT).show();
            } else if (resultCode == RESULT_CANCELED) {
              //  Toast.makeText(this, "Audio was not recorded", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void recordAudio(View v) {
        AndroidAudioRecorder.with(this)
                // Required
                .setFilePath(AUDIO_FILE_PATH)
                .setColor(ContextCompat.getColor(this, R.color.recorder_bg))
                .setRequestCode(REQUEST_RECORD_AUDIO)

                // Optional
                .setSource(AudioSource.MIC)
                .setChannel(AudioChannel.STEREO)
                .setSampleRate(AudioSampleRate.HZ_48000)
                .setAutoStart(false)
                .setKeepDisplayOn(true)

                // Start recording
                .record();
    }

    public int find(String str)
    {
        if(str.equals("Beyonce"))
        {
            return R.drawable.beyonce;
        }
        if(str.equals("Varun"))
        {
            return R.drawable.varun;
        }
        if(str.equals("Kush"))
        {
            return R.drawable.kush;
        }
        if(str.equals("Ethan"))
        {
            return R.drawable.ethan;
        }
        if(str.equals("Jordan"))
        {
            return R.drawable.jordan;
        }
        if(str.equals("Eminem"))
        {
            return R.drawable.eminem;
        }
        if(str.equals("Justin Bieber"))
        {
            return R.drawable.justin;
        }
        if(str.equals("Kanye West"))
        {
            return R.drawable.kanye;
        }
        if(str.equals("Taylor Swift"))
        {
            return R.drawable.taylor;
        }
        if(str.equals("Camilla Cabello"))
        {
            return R.drawable.camilla;
        }
        return 0;

    }

}