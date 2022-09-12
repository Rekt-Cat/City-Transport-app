package com.maid.citytranspor;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

public class MainActivity extends AppCompatActivity {
    LottieAnimationView lview;
    Animation alpha,scale;
    TextView appName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        appName =findViewById(R.id.cityTransport);
        alpha = AnimationUtils.loadAnimation(this,R.anim.alpha);
        scale = AnimationUtils.loadAnimation(this,R.anim.scale);
        appName.startAnimation(alpha);
        lview = findViewById(R.id.lottie);
        lview.setAnimation(R.raw.busj);
        lview.startAnimation(scale);
        lview.playAnimation();
        lview.loop(true);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(MainActivity.this,Main.class);
                Pair[] pairs = new Pair[2];
                pairs[0]= new Pair<View, String>(findViewById(R.id.lottie),"busGo");
                pairs[1]= new Pair<View,String>(findViewById(R.id.cityTransport),"appName");
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this,pairs);
                startActivity(i,options.toBundle());
            }
        },3000);

    }
}