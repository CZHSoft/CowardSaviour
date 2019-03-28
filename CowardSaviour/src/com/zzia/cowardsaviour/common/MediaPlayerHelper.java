package com.zzia.cowardsaviour.common;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;

public class MediaPlayerHelper 
{
	private Context context;
	private MediaPlayer mMediaPlayer=null;  
	
    private int state = IDLE;  
    private static final int PLAYING = 0;  
    private static final int PAUSE = 1;  
    private static final int STOP = 2;  
    private static final int IDLE = 3;  
    public static final int UPDATE = 2;  
	
    private int source=0;
    
	public MediaPlayerHelper(Context parent,int mp3_Source)
	{
		context=parent;
		source=mp3_Source;
	}
	
    public void pause() {  
        if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {  
            mMediaPlayer.pause();  
            state = PAUSE;  
        }  
        //��ͣ״̬ 
  
    }  
  
    public void start() {  
    	
    	if(mMediaPlayer == null)
    	{
    		return;
    	}
    	
        if (state == STOP) {  
            play();  
        } else if (state == PAUSE) {  
            mMediaPlayer.start();  
            state = PLAYING;  
        }  
        // ��ʼ
    }  
  
    public void stop() { 
    	
    	if(mMediaPlayer == null)
    	{
    		return;
    	}
    	
        mMediaPlayer.stop();  
        state = STOP;  
        //ֹͣ״̬
        
    }  
  
    // MediaPlayer����prepared״̬��ʼ����  
    private OnPreparedListener preListener = new OnPreparedListener() {  
        public void onPrepared(MediaPlayer arg0) {  
            mMediaPlayer.start();  
            state = PLAYING;  
        }  
  
    };  
  
    public void play() {  
        try {  
            if (mMediaPlayer == null || state == STOP) {  
                // ����MediaPlayer��������Listener  
                mMediaPlayer = MediaPlayer.create(context, source);  
                mMediaPlayer.setOnPreparedListener(preListener);  
            } 
            else 
            {  
                mMediaPlayer.reset();
                mMediaPlayer = MediaPlayer.create(context, source);  
                mMediaPlayer.setOnPreparedListener(preListener);
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
	
}
