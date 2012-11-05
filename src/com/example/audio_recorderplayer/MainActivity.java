package com.example.audio_recorderplayer;

import java.io.File;
import java.io.IOException;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ToggleButton;

public class MainActivity extends Activity implements OnClickListener{

	MediaRecorder rec = null;
	MediaPlayer bgm = null;
	ToggleButton tbtn_rec = null;
	ToggleButton tbtn_play = null;
	String sd;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        tbtn_rec = (ToggleButton)findViewById(R.id.toggleButton1);
        tbtn_rec.setOnClickListener(this);
        
        tbtn_play = (ToggleButton)findViewById(R.id.toggleButton2);
        tbtn_play.setOnClickListener(this);
        
        File file = new File(Environment.getExternalStorageDirectory()
                + "/audio.mp3");
        
        Uri uri = Uri.fromFile(file);
        bgm = MediaPlayer.create(this, uri);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.toggleButton1:
			if(tbtn_rec.isChecked()){			
				try {
					rec = new MediaRecorder();
					rec.setAudioSource(MediaRecorder.AudioSource.MIC);
					rec.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
					rec.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
					
					sd = Environment.getExternalStorageDirectory().getAbsolutePath();				
					
					rec.setOutputFile(sd + "/audio.mp3");
					rec.prepare();
					rec.start();
					
				} catch (IllegalStateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			else{
				rec.stop();
				rec.release();
			}
			
			break;
			
		case R.id.toggleButton2:
			if(tbtn_play.isChecked()){
				bgm.seekTo(0);
				bgm.start();
			}
			
			else{
				bgm.pause();
			}
			
			break;
		}		
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		rec.stop();
		rec.release();
		bgm.stop();
		bgm.release();
	}
}