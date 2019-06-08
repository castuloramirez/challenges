package io.anyline.android.challenge.animation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

public class KnightRiderActivity extends Activity {
    private ObjectAnimator animation1;
    private ObjectAnimator animation2;
    private int width;
    private int height;

    private Collection<AnimatorSet> kittsCol = new ArrayList<AnimatorSet>();

    private Collection<ImageView> kittsImage = new ArrayList<ImageView>();

    private Animation oldA;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(io.anyline.android.challenge.animation.R.layout.target);
        this.getActionBar().setDisplayShowHomeEnabled(true);
        this.getActionBar().setIcon(R.drawable.icon);
        loadImages();
        //button = button_[0]; //first car.
        getKittsImage().add(button_[0]);
        getKittsImage().add(button_[1]);
        getKittsImage().add(button_[2]);

        button_[0].setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        createAnimation(button_[0]);
                    }
                });
        DisplayMetrics displaymetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        width = displaymetrics.widthPixels;
        height = displaymetrics.heightPixels;
        //randon = new Random();
        initAnimation();

        // If we have a saved state then we can restore it now
        if (savedInstanceState != null) {
            Intent svc = new Intent(this, BackgroundSoundService.class);
            startService(svc);
        }
    }

    private void loadImages() {
        Collection<Integer> col = new ArrayList<Integer>();
        for (int i = 0; i < 9; i++) {
            button_[i] = (ImageView) findViewById(imgId[i]);
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
        getMenuInflater().inflate(io.anyline.android.challenge.animation.R.menu.animationmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case io.anyline.android.challenge.animation.R.id.action_stop:
                toStop();
                break;
            case io.anyline.android.challenge.animation.R.id.action_start:
                toStart();
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
                Toast msg = Toast.makeText(this, "By Castulo Ramirez 2017"+"\n" +"Modified 2019", Toast.LENGTH_LONG);
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

            initAnimation();
        }
    }

    public void toAdd() {
        if (getKittsImage().size() < 9) {
            int i = getKittsImage().size();
            getKittsImage().add(button_[i]);
            moveKitt(button_[i]);
            createAnimation(button_[i]);
        }
    }

    public void toStop() {
        for (AnimatorSet s : kittsCol) {
            s.end();
        }
    }

    public void toStart() {
        for (AnimatorSet s : kittsCol) {
            s.start();
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

    public void setKittsCol(Collection<AnimatorSet> kittsCol) {
        this.kittsCol = kittsCol;
    }


    @Override
    public void onDestroy() {
        stopService(new Intent(this, BackgroundSoundService.class));
        super.onDestroy();
    }

    public Collection<ImageView> getKittsImage() {
        return kittsImage;
    }

    public void setKittsImage(Collection<ImageView> kittsImage) {
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