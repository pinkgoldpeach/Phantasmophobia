package at.ac.tuwien.ims.svn.phantasmophobia;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;


import android.content.ContentValues;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import at.ac.tuwien.ims.svn.phantasmophobia.R;
import at.ac.tuwien.ims.svn.phantasmophobia.ScoreContract;
import at.ac.tuwien.ims.svn.phantasmophobia.ScoreProvider;

/**
 * Created by Carla Jancik on 30.12.2015
 * created with the code and the tutorial from the lecture (vorlesung)
 * @author Carla Jancik
 */

public class CreateHighScoreActivity extends AppCompatActivity {

    private Button addButton;
    private EditText nameEditText;
    private TextView scoreEditText;
    private int score;
    private int level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_high_score);

        nameEditText = (EditText) findViewById(R.id.editText);
        scoreEditText = (TextView) findViewById(R.id.editText2);
        addButton = (Button) findViewById(R.id.button);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            level = (int) bundle.getInt("Level");
            score = (int) bundle.getInt("Score");
            scoreEditText.setText(""+score);
        }

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveScore();
            }
        });


    }

    private void saveScore() {
        String username = nameEditText.getText().toString();
        String scoreStr = scoreEditText.getText().toString();
        int score = Integer.parseInt(scoreStr);

        ContentValues values = new ContentValues();
        values.put(ScoreContract.ScoreEntry.COLUMN_NAME_USERNAME, username);
        values.put(ScoreContract.ScoreEntry.COLUMN_NAME_SCORE, score);

        getContentResolver().insert(ScoreProvider.CONTENT_URI, values);
        Intent highScore = new Intent(this, ReadScoreActivity.class);
        finish();
        startActivity(highScore);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_high_score, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
