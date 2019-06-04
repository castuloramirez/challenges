package io.anyline.android.challenge.animation;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationSet;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

public class KnightRiderFragment extends Fragment {

    private ObjectAnimator animation1;
    private ObjectAnimator animation2;
    private ImageView button, button2, button3, button4;
    private Random randon;
    private int width;
    private int height;
    private AnimatorSet set;

    private Collection<AnimationSet> kittsCol = new ArrayList<AnimationSet>();

    public KnightRiderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(io.anyline.android.challenge.animation.R.layout.fragment_knightrider, container, false);

        button = (ImageView) v.findViewById(io.anyline.android.challenge.animation.R.id.button1);
        button2 = (ImageView) v.findViewById(io.anyline.android.challenge.animation.R.id.button2);
        moveKitt(button);
        moveKitt(button2);
        button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        createAnimation();
                    }
                });
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        width = displaymetrics.widthPixels;
        height = displaymetrics.heightPixels;
        randon = new Random();
        createAnimation();

        return v;
    }


    public void onClick(View view) {
        //String string = button.getText().toString();
        //int hitTarget = Integer.valueOf(string) + 1;
        //button.setText(String.valueOf(hitTarget));
    }

    /**
     * @param splashImageView
     */
    private void moveKitt(ImageView splashImageView) {
        // Start animating the image
        splashImageView.setBackgroundResource(io.anyline.android.challenge.animation.R.drawable.kitt);
        final AnimationDrawable frameAnimation = (AnimationDrawable) splashImageView.getBackground();
        splashImageView.post(new Runnable() {
            @Override
            public void run() {
                frameAnimation.start();
            }
        });

    }

    private void createAnimation() {
        set = buildAnimation();
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

    private AnimatorSet buildAnimation() {
        int nextX = randon.nextInt(width);
        int nextY = randon.nextInt(height);
        animation1 = ObjectAnimator.ofFloat(button, "x", nextX);
        animation1.setDuration(1400);
        animation2 = ObjectAnimator.ofFloat(button, "y", nextY);
        animation2.setDuration(1400);
        AnimatorSet set = new AnimatorSet();
        set.playTogether(animation1, animation2);
        return set;
    }


}
