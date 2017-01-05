package at.ac.tuwien.ims.svn.phantasmophobia;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

/**
 * shows the start menu
 * Created by Carla Jancik on 25.12.2015.
 */
public class MenuActivity extends Activity implements DialogInterface.OnDismissListener, View.OnClickListener {

    private Dialog dialog;
    private Dialog helpDialog;
    private static final String TAG = MainActivity.class.getSimpleName();

    /**
     * creates different buttons and dialogs for help and levels
     * @author Carla Jancik
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // requesting to turn the title OFF
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // making it full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        this.dialog = new Dialog(this,android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        dialog.setContentView(R.layout.dialog_level);
        dialog.hide();
        dialog.setOnDismissListener(this);

        ImageButton level1 = (ImageButton) dialog.findViewById(R.id.level1);
        level1.setOnClickListener(this);
        ImageButton level2 = (ImageButton) dialog.findViewById(R.id.level2);
        level2.setOnClickListener(this);

       this.helpDialog = new Dialog(this,android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        helpDialog.setContentView(R.layout.dialog_help);
        helpDialog.hide();
        helpDialog.setOnDismissListener(this);

        //sets the Layout of the activity
        setContentView(R.layout.activity_menu);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    /**
     * after pressing the start button, the game starts
     * @author Carla Jancik
     */

    public void startGame(View view) {
        Intent play = new Intent(this, MainActivity.class);
        startActivity(play);
    }

    /**
     * @deprecated
     */
    public void setMusic(View v){
        Log.d(TAG, "setMusic..");
        dialog.show();
    }

    /**
     * shows highscore
     * @author Carla Jancik
     */

    public void showHighscore(View view){
        Intent score = new Intent(this, ReadScoreActivity.class);
        startActivity(score);
    }

    /**
     * on vclicklistener for level buttons
     * @author Carla Jancik
     */

    @Override
    public void onClick(View v) {
        switch (v.getId()) {


            case R.id.level1:
                Intent main = new Intent(this, MainActivity.class);
                main.putExtra("Level", 1);
                startActivity(main);
                break;

            case R.id.level2:
                Intent mainGame = new Intent(this, MainActivity.class);
                mainGame.putExtra("Level", 2);
                startActivity(mainGame);
                break;
        }
    }

    /**
     * shows the 2 different level buttons after clicking on level
     * @author Carla Jancik
     */
    public void setLevel(View v){
        dialog.show();
    }

    /**
     * shows help instructions
     * @author Carla Jancik
     */

    public void showHelp(View v){
        Log.d(TAG, "help..");
       TextView titel = (TextView) helpDialog.findViewById(R.id.textView2);
        /**
        titel.setText("Hilfe:\n" +
                "Obere Hälfte: Springen.\n" +
                "Links Unten: nach Links bewegen.\n"+
                "Rechts Unten: nach Rechts bewegen.\n" +
                "Nicht am Boden ankommen!\n" +
                "Vögel sind rein Dekorativ.\n");
         */
        titel.setTextColor(Color.parseColor("BLACK"));


        helpDialog.show();
    }

    /**
     * shows the video
     * @author Carla Jancik
     */

    public void showVideo(View v){
        Intent video = new Intent(this, VideoActivity.class);
        startActivity(video);
    }

    @Override
    public void onDismiss(DialogInterface dialog) {

    }
}
