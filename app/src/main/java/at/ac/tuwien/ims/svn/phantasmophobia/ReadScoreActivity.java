package at.ac.tuwien.ims.svn.phantasmophobia;

        import android.app.LoaderManager;
        import android.content.CursorLoader;
        import android.content.Intent;
        import android.content.Loader;
        import android.database.Cursor;
        import android.net.Uri;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.ContextMenu;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.view.View;
        import android.widget.AdapterView;
        import android.widget.Button;
        import android.widget.ImageButton;
        import android.widget.ListView;
        import android.widget.SimpleCursorAdapter;

        import at.ac.tuwien.ims.svn.phantasmophobia.R;
        import at.ac.tuwien.ims.svn.phantasmophobia.ScoreContract;
        import at.ac.tuwien.ims.svn.phantasmophobia.ScoreProvider;

/**
 * Created by Carla Jancik on 30.12.2015
 * created with the code and the tutorial from the lecture (vorlesung)
 * @author Carla Jancik
 */

public class ReadScoreActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>, View.OnClickListener {

    private ListView scoreListView;
    private View progressView;
    private SimpleCursorAdapter cursorAdapter;
    private ImageButton mainMenu;

    private static final int DELETE_ID = Menu.FIRST + 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_score);

        scoreListView = (ListView) findViewById(R.id.scoreListView);
        progressView = findViewById(R.id.progressView);

        this.mainMenu = (ImageButton) findViewById(R.id.mainMenu);
        mainMenu.setOnClickListener(this);

        fillData();
        registerForContextMenu(scoreListView);
    }

    private void showProgress(boolean progress) {
        scoreListView.setVisibility(progress ? View.GONE : View.VISIBLE);
        progressView.setVisibility(progress ? View.VISIBLE : View.GONE);
    }

    private void fillData() {

        showProgress(true);

        // Fields from the database (projection)
        // Must include the _id column for the adapter to work
        String[] from = new String[] {ScoreContract.ScoreEntry.COLUMN_NAME_USERNAME, ScoreContract.ScoreEntry.COLUMN_NAME_SCORE};
        // Fields on the UI to which we map
        int[] to = new int[] { R.id.textViewName, R.id.textViewScore };

        //start a new loader or re-connect to existing one
        getLoaderManager().initLoader(0, null, this);
        cursorAdapter = new SimpleCursorAdapter(this, R.layout.list_item_score, null, from, to, 0);

        scoreListView.setAdapter(cursorAdapter);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, DELETE_ID, 0, R.string.Delete);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case DELETE_ID:
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
                        .getMenuInfo();
                Uri uri = Uri.parse(ScoreProvider.CONTENT_URI + "/"
                        + info.id);
                getContentResolver().delete(uri, null, null);
                fillData();
                return true;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_read_score, menu);
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

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = { ScoreContract.ScoreEntry._ID, ScoreContract.ScoreEntry.COLUMN_NAME_USERNAME, ScoreContract.ScoreEntry.COLUMN_NAME_SCORE};
        CursorLoader cursorLoader = new CursorLoader(this,
                ScoreProvider.CONTENT_URI, projection, null, null, ScoreContract.ScoreEntry.COLUMN_NAME_SCORE + " DESC");
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        cursorAdapter.swapCursor(data);//notifies the SimpleCursorAdapter about new data
        showProgress(false);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // data is not available anymore, delete reference
        cursorAdapter.swapCursor(null);
    }

    @Override
    public void onBackPressed(){
        Intent mainMenu = new Intent(this, MenuActivity.class);
        startActivity(mainMenu);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.mainMenu:
                mainMeu();
                break;
        }
    }

    public void mainMeu(){
        Intent mainMenu = new Intent(this, MenuActivity.class);
        startActivity(mainMenu);
    }
}

