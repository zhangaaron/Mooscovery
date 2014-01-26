package com.example.mooscover;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Stack;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.Menu;
import android.view.View;

public class DiscoverSongs extends Activity {
	public static Stack<String> favoritesList = new Stack();
	MediaPlayer mPlayer;
	final Handler h = new Handler();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_discover_songs);
		
		File musicDirectory = new File( "/storage/emulated/0/Music/moosic/");
		File[] songList = musicDirectory.listFiles();
		
		//String[] files = {"/storage/emulated/0/Music/moosic/song1.mp3", "/storage/emulaint randomFile = new Double(Math.random() * (songList.length + 1)).intValue();
		mPlayer = new MediaPlayer();
		if(mPlayer.isPlaying()) {
		    mPlayer.pause();
		}
		
		int randomFileNum = Double.valueOf(Math.random() * (songList.length)).intValue();
		try {
			
			File mediaToPlay = songList[randomFileNum];
			FileInputStream fstream = new FileInputStream(mediaToPlay);
			mPlayer.setDataSource(fstream.getFD());         
			fstream.close();
			mPlayer.prepare();
			mPlayer.start();
			mPlayer.prepare();
			
			
			
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//mPlayer.start();
		mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
		mPlayer.seekTo(60*1000);
		mPlayer.start();
		

		if (!(favoritesList.contains(songList[randomFileNum].getAbsolutePath())))
				favoritesList.push(songList[randomFileNum].getAbsolutePath());
		
		
		h.postDelayed(new Runnable(){
			@Override
			public void run(){
				dislikeButton(null);
			}
		}, 15000);
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.discover_songs, menu);
		return true;
	}
	
	public void dislikeButton(View view){
		mPlayer.stop();
		mPlayer.release();
		if (!(favoritesList.isEmpty()))
			favoritesList.pop();
		h.removeMessages(0);
		startActivity(new Intent(this, DiscoverSongs.class));
	}
	
	public void likeButton(View view){		
		mPlayer.stop();
		mPlayer.release();
		
		h.removeMessages(0);
		
		startActivity(new Intent(this, DiscoverSongs.class));
	}
	
	public void showList(View view){
		favoritesList.pop();
		mPlayer.stop();
		mPlayer.release();
		h.removeMessages(0);
		startActivity(new Intent(this, ShowList.class));
	}

}
