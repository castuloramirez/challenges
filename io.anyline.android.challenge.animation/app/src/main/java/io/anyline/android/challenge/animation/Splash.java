package io.anyline.android.challenge.animation;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MotionEvent;
import android.widget.ImageView;

public class Splash extends Activity {
    /**
     * The thread to process splash screen events
     */
    private Thread mSplashThread;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(io.anyline.android.challenge.animation.R.layout.activity_splash);
        // Start animating the image
        final ImageView splashImageView = (ImageView) findViewById(io.anyline.android.challenge.animation.R.id.SplashImageView);
        splashImageView.setBackgroundResource(io.anyline.android.challenge.animation.R.drawable.kitt);
        final AnimationDrawable frameAnimation = (AnimationDrawable) splashImageView.getBackground();
        splashImageView.post(new Runnable() {
            @Override
            public void run() {
                frameAnimation.start();
            }
        });
        Intent svc = new Intent(this, BackgroundSoundService.class);
        startService(svc);
        final Splash sPlashScreen = this;
        mSplashThread = new Thread() {
            @Override
            public void run() {
                try {
                    synchronized (this) {
                        //Wait given period of time or exit on touch
                        wait(6000);
                    }
                } catch (InterruptedException ex) {
                }
                finish();

                Intent intent = new Intent();
                intent.setClass(sPlashScreen, KnightRiderActivity.class);
                startActivity(intent);
            }
        };
        mSplashThread.start();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        return false;
    }

    /**
     * Processes splash screen touch events
     */
    @Override
    public boolean onTouchEvent(MotionEvent evt) {
        if (evt.getAction() == MotionEvent.ACTION_DOWN) {
            synchronized (mSplashThread) {
                mSplashThread.notifyAll();
            }
        }
        return true;
    }
}