package at.ac.tuwien.ims.svn.phantasmophobia;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.VideoView;

import at.ac.tuwien.ims.svn.phantasmophobia.MenuActivity;

/**
 * Created by Carla Jancik on 05.01.2016.
 * created with the code and the tutorial from the lecture (vorlesung)
 * @author Carla Jancik
 */

public class VideoActivity extends AppCompatActivity implements MediaPlayer.OnCompletionListener, View.OnClickListener {

    VideoView myVideoView;
    Button skipButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Fullscreen setzen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        super.onCreate(savedInstanceState);


        //Layout setzen
        setContentView(R.layout.activity_video);

        //VideoView holen
        myVideoView=(VideoView)findViewById(R.id.intro_video1);
        skipButton=(Button)findViewById(R.id.skipButton);

        //Video Uri setzen
        Uri video = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.intro_video1);

        myVideoView.setVideoURI(video);

        skipButton.setOnClickListener(this);

        //end of media listener
        myVideoView.setOnCompletionListener(this);

        //start the video
        myVideoView.start();

    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        skipVideo();
    }

    @Override
    public void onClick(View v) {
        skipVideo();
    }

    private void skipVideo(){
        startActivity(new Intent(this, MenuActivity.class));

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();


        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
