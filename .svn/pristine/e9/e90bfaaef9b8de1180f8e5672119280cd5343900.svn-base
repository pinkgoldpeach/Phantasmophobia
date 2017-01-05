package at.ac.tuwien.ims.svn.phantasmophobia;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.SparseLongArray;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * Created by Emil Etlinger on 29.12.2015.
 * reference: https://www.youtube.com/watch?v=XwOuTjUFexE
 * Creates the Splashscreen. Shows an older version of Sven.png and makes him rotate.
 * @author Emil Etlinger
 */
public class SplashActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        final ImageView imgView = (ImageView) findViewById(R.id.imageView);
        final Animation ani = AnimationUtils.loadAnimation(getBaseContext(), R.anim.rotate);

        imgView.startAnimation(ani);
        ani.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                finish();
                Intent i = new Intent(getBaseContext(),VideoActivity.class);
                startActivity(i);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}
    /*

    Older try for
    @Override
    protected void onCreate(Bundle savedInstance)
    {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_splash);
        Thread logoTimer = new Thread(){
            public void run(){
                try{
                    sleep(2000);
                    Intent play = new Intent(SplashActivity.this, MenuActivity.class);
                    startActivity(play);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally{

                }
            }
        };
        logoTimer.start();
    }
}
*/