package io.anyline.android.challenge.animation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Random;

import io.anyline.android.challenge.animation.common.DialogHelper;
import io.anyline.android.challenge.animation.common.Game;

public class KnightRiderActivity extends AppCompatActivity {
    private ObjectAnimator animation1;
    private ObjectAnimator animation2;
    private int width;
    private int height;

    private ArrayList<AnimatorSet> kittsCol = new ArrayList<AnimatorSet>();

    private ArrayList<ImageView> kittsImage = new ArrayList<ImageView>();

    private Animation oldA;

    private Game game = new Game();


    private int[] imgId = {
            io.anyline.android.challenge.animation.R.id.button1,
            io.anyline.android.challenge.animation.R.id.button2,
            io.anyline.android.challenge.animation.R.id.button3,
            io.anyline.android.challenge.animation.R.id.button4,
            io.anyline.android.challenge.animation.R.id.button5,
            io.anyline.android.challenge.animation.R.id.button6,
            io.anyline.android.challenge.animation.R.id.button7,
            io.anyline.android.challenge.animation.R.id.button8,
            io.anyline.android.challenge.animation.R.id.button9,
    };

    private ImageView[] button_ = new ImageView[9];

    private Vibrator vibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set fullscreen
        //     getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // Set No Title
        //   this.requestWindowFeature(Window.FEATURE_NO_TITLE);


        setContentView(io.anyline.android.challenge.animation.R.layout.target);
        this.getSupportActionBar().setDisplayShowHomeEnabled(true);
        this.getSupportActionBar().setIcon(R.drawable.icon);

        loadImages();
        final ImageView button = button_[0]; //first car.
        button.setTag("Car Number :"+0);
        getKittsImage().add(button);
        //  getKittsImage().add(button_[1]);
        //  getKittsImage().add(button_[2]);

        button_[0].setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //createAnimation(button_[0]);
                        DialogHelper.alertView(v.getTag()+"",KnightRiderActivity.this);
                    }
                });
        DisplayMetrics displaymetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        width = displaymetrics.widthPixels;
        height = displaymetrics.heightPixels;

        initAnimation();

        // If we have a saved state then we can restore it now
        if (savedInstanceState != null) {
            Intent svc = new Intent(this, BackgroundSoundService.class);
            startService(svc);
        }

        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
    }

    private void loadImages() {
        Collection<Integer> col = new ArrayList<Integer>();
        for (int i = 0; i < 9; i++) {
            button_[i] =  findViewById(imgId[i]);
        }
    }

    /**
     * @param view
     */
    public void onClick(View view) {
        //String string = button.getText().toString();
        //int hitTarget = Integer.valueOf(string) + 1;
        //button.setText(String.valueOf(hitTarget));
    }

    private void moveKitts() {
        // Start animating the image
        for (int i = 0; i < getKittsImage().size(); i++) {
            button_[i].setBackgroundResource(io.anyline.android.challenge.animation.R.drawable.kitt);
            final AnimationDrawable frameAnimation = (AnimationDrawable) button_[i].getBackground();
            button_[i].post(new Runnable() {
                @Override
                public void run() {
                    frameAnimation.start();
                }
            });
        }
    }


    private void initAnimation() {

        moveKitts();
        // Start animating the image
        for (int i = 0; i < getKittsImage().size(); i++) {
            createAnimation(button_[i]);
        }
    }

    private void createAnimation(final ImageView button) {
        final AnimatorSet set = buildAnimation(button);
        final Random randon = new Random();
        set.start();
        if (game.isGameStatusPause()){
            set.pause();
        }
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                int nextX = randon.nextInt(width);
                int nextY = randon.nextInt(height);
                animation1 = ObjectAnimator.ofFloat(button, "x", button.getX(), nextX);
                animation1.setDuration(1400);
                animation2 = ObjectAnimator.ofFloat(button, "y", button.getY(), nextY);
                animation2.setDuration(1400);
                set.playTogether(animation1, animation2);
                set.start();
            }
        });
    }

    private AnimatorSet buildAnimation(ImageView button) {
        Random randon = new Random();
        int nextX = randon.nextInt(width);
        int nextY = randon.nextInt(height);
        animation1 = ObjectAnimator.ofFloat(button, "x", nextX);
        animation1.setDuration(1400);
        animation2 = ObjectAnimator.ofFloat(button, "y", nextY);
        animation2.setDuration(1400);
        AnimatorSet set = new AnimatorSet();
        set.playTogether(animation1, animation2);
        getKittsCol().add(set);
        return set;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(io.anyline.android.challenge.animation.R.menu.animationmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case io.anyline.android.challenge.animation.R.id.action_stop:
                toStop();
                break;
            case io.anyline.android.challenge.animation.R.id.action_start:
                toContinue();
                break;

            case io.anyline.android.challenge.animation.R.id.action_speedup:
                toSpeedUp(200);
                break;
            case io.anyline.android.challenge.animation.R.id.action_slowdown:
                toSpeedUp(2600);
                break;
            case io.anyline.android.challenge.animation.R.id.action_defaultspeed:
                toSpeedUp(1400);
                break;
            case R.id.action_addCar:
                toAdd();
                break;
            case R.id.action_lessCar:
                toRemove();
                break;
            case R.id.action_horizontal:
                // direction(0);
                break;
            case R.id.action_about:
                Toast msg = Toast.makeText(this, "By Castulo Ramirez", Toast.LENGTH_LONG);
                msg.show();
                break;
        }
        return true;
    }

    public void toRemove() {
        if (getKittsImage().size() > 1) {
            int i = getKittsImage().size();
            stopKitt(button_[i]);
            getKittsImage().remove(button_[i]);

            //  initAnimation();   Commented 20/06/2020
            Iterator<AnimatorSet> iter = getKittsCol().iterator();
            if(iter.hasNext()) {
                AnimatorSet set =  iter.next();
                set.removeAllListeners();
                set.end();
                set.cancel();
                iter.remove();
            }
        }
    }

    public void toAdd() {
        if (getKittsImage().size() < 9) {
            int i = getKittsImage().size();
            getKittsImage().add(button_[i]);
            moveKitt(button_[i]);
            createAnimation(button_[i]);
            if (Build.VERSION.SDK_INT >= 26) {
                vibrator.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE));
            } else {
                vibrator.vibrate(200);
            }
        }
    }

    public void toStop() {
        for (AnimatorSet animatorSet : kittsCol) {
            animatorSet.pause();
            game.setGameStatusPause(animatorSet.isPaused());
        }
    }

    public void toContinue() {
        for (AnimatorSet animatorSet : kittsCol) {
            if (animatorSet.isPaused())
                animatorSet.resume();
            game.setGameStatusPause(animatorSet.isPaused());
        }
    }

    public void toSpeedUp(long d) {
        for (AnimatorSet s : kittsCol) {
            s.setDuration(d);
        }
    }

    private void direction(int i) {
        for (ImageView s : getKittsImage()) {
            oldA = s.getAnimation();
            TranslateAnimation animation = new TranslateAnimation(0.0f, 0.0f, 0.0f, 1500.0f); // new TranslateAnimation (float fromXDelta,float toXDelta, float fromYDelta, float toYDelta)
            animation.setDuration(1500); // animation duration
            animation.setRepeatCount(8); // animation repeat count
            animation.setRepeatMode(2); // repeat animation (left to right, right to left)
            animation.setFillAfter(true);
            s.startAnimation(animation);//your_view for mine is imageView
        }
    }

    public Collection<AnimatorSet> getKittsCol() {
        return kittsCol;
    }

    public void setKittsCol(ArrayList<AnimatorSet> kittsCol) {
        this.kittsCol = kittsCol;
    }


    @Override
    public void onDestroy() {
        stopService(new Intent(this, BackgroundSoundService.class));
        super.onDestroy();
    }

    public ArrayList<ImageView> getKittsImage() {
        return kittsImage;
    }

    public void setKittsImage(ArrayList<ImageView> kittsImage) {
        this.kittsImage = kittsImage;
    }


    private void moveKitt(ImageView splashImageView) {
        // Start animating the image
        splashImageView.setBackgroundResource(R.drawable.kitt);
        final AnimationDrawable frameAnimation = (AnimationDrawable) splashImageView.getBackground();
        splashImageView.post(new Runnable() {
            @Override
            public void run() {
                frameAnimation.start();
            }
        });
    }


    private void stopKitt(ImageView splashImageView) {
/*
        for (AnimatorSet s : kittsCol) {
            s.cancel();
        }
        getKittsCol().clear();
        initAnimation();

*/

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }
}
