package at.ac.tuwien.ims.svn.phantasmophobia;

import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ToggleButton;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;

/**
 * Created by Carla Jancik on 24.10.2015.
 * wir haben uns hier am code der vorlesung und an diesem tutorial http://obviam.net/index.php/the-android-game-loop/ orientiert.
 * in android you always need an activity, everything happens in an activity.
 * set window to full screen and create a surface view
 * @author Carla Jancik
 */

public class MainActivity extends AppCompatActivity implements DialogInterface.OnDismissListener, View.OnClickListener {

    private GameSurfaceView gameView;
    private Dialog dialog;
    private static final String TAG = MainActivity.class.getSimpleName();
    private long startTime;
    private long pauseDuration;
    private long pauseStartTime;
    private MediaPlayer mPlayer;

    /**
     * creates a pause button and a dialog for pause, with buttons
     * @author Carla Jancik
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //turn off the title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);

        //set to full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // // Log.d(TAG, "Creating...");

        mPlayer = MediaPlayer.create(this, R.raw.backgroundlesatre);
        mPlayer.setLooping(true);

        FrameLayout game = new FrameLayout(this);
     //   gameView = GameSurfaceView.getInstance(this);
        Bundle bundle = getIntent().getExtras();
        int level = 1;
        if(bundle != null){
            level = (int) bundle.getInt("Level");
        }

        gameView = new GameSurfaceView(this, level);
        LinearLayout gameWidgets = new LinearLayout (this);
        gameWidgets.setGravity(Gravity.BOTTOM | Gravity.RIGHT);

        this.dialog = new Dialog(this,android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        dialog.setContentView(R.layout.activity_pause);
        dialog.hide();
        dialog.setOnDismissListener(this);

        FloatingActionButton pause = new FloatingActionButton(this);
        pause.setImageResource(R.drawable.pause);
        pause.setBackgroundTintList(ColorStateList.valueOf(Color.BLUE));
 //       pause.setRippleColor(R.color.holo_blue_light);
        gameWidgets.addView(pause);

        game.addView(gameView);
        game.addView(gameWidgets);

        setContentView(game);

        pause.setOnClickListener(this);

        ImageButton resume = (ImageButton) dialog.findViewById(R.id.resume);
        resume.setOnClickListener(this);
        ImageButton exit = (ImageButton) dialog.findViewById(R.id.exit);
        exit.setOnClickListener(this);
        ToggleButton music = (ToggleButton) dialog.findViewById(R.id.music);
        music.setOnClickListener(this);
        playBackgroundSound();

        this.pauseDuration = 0;
        this.pauseStartTime = 0;


    }


    @Override
    protected void onDestroy(){
        // // Log.d(TAG, "Destroying...");
        super.onDestroy();

    }

    @Override
    protected  void onPause(){
        // // Log.d(TAG, "Pausing...");
        super.onPause();

    }

    @Override
    protected void onResume(){
        // // Log.d(TAG, "Resuming...");
        super.onResume();
    }

    @Override
    protected void onStop(){
        super.onStop();
    }

    /**
     * on clicklistener for the buttons in dialog
     * @author Carla Jancik
     */

    @Override
    public void onClick(View v) {
        switch(v.getId()) {

            case R.id.resume:
                resume();
                break;
/**
            case R.id.restart:
                restartMain();
                break;
 */

            case R.id.exit:
                exit();
                break;

            case R.id.music:
                toggleSound();
                break;

            default:
                pause();
                break;
        }
    }

    /**
     * pauses game, opens pause dialog
     * @author Carla Jancik
     */

    public void pause(){
        gameView.pause();
        this.pauseStartTime = System.currentTimeMillis();
        dialog.show();
    }


      /**
     * resumes game
     * @author Carla Jancik
     */


    public void resume(){
        gameView.resume();
        this.pauseDuration += (System.currentTimeMillis() - pauseStartTime);
        dialog.hide();
    }

    /**
     * game over - sets score
     * @author Carla Jancik
     */

    public void gameOver(int level){
        // Log.d(TAG, "Resuming...");
        int points = (int) (System.currentTimeMillis() - startTime - pauseDuration);
        points = Math.round(points/1000);
        Intent highScore = new Intent(this, CreateHighScoreActivity.class);
        Bundle extras = new Bundle();
        extras.putInt("Score", points);
        extras.putInt("Level", level);
        highScore.putExtras(extras);
        startActivity(highScore);

    }
/*
    public void restartMain(){
        Log.d(TAG, "restart...");
        gameView.exit();
        Intent intent = getIntent();
        dialog.dismiss();
        finish();
        startActivity(intent);

        dialog.dismiss();
        recreate();
        }
         */

    /**
     * back to main menu
     * @author Carla Jancik
     */

    public void exit(){
        gameView.exit();
        dialog.dismiss();
        Intent menu = new Intent(this, MenuActivity.class);
        finish();
        startActivity(menu);
    }

    public void setStartTime(long startTime){
        this.startTime = startTime;
    }



    @Override
    public void onDismiss(DialogInterface dialog) {

    }

    /**
     * sets sound
     * @author Emil Etlinger
     */

    public void playBackgroundSound()
    {
        if (mPlayer != null && !mPlayer.isPlaying())
        {
            try{
                mPlayer.start();
            }
            catch (IllegalStateException e)
            {
                e.printStackTrace();
            }
        }
    }

    /**
     * toggles sound
     * @author Emil Etlinger
     */

    public void toggleSound()
    {
        if(mPlayer!= null && mPlayer.isPlaying())
            mPlayer.pause();
        else
        {
            mPlayer.start();
        }
    }

    /**
     * back to mainmenu
     * @author Carla Jancik
     */

    @Override
    public void onBackPressed(){
        Intent mainMenu = new Intent(this, MenuActivity.class);
        startActivity(mainMenu);
    }
}

