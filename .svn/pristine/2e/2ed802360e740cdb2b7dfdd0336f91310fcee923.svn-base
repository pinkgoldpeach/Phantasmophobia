package at.ac.tuwien.ims.svn.phantasmophobia;

/**
 * Created by Carla Jancik on 30.12.2015
 * created with the code and the tutorial from the lecture (vorlesung)
 * @author Carla Jancik
 */

import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import at.ac.tuwien.ims.svn.phantasmophobia.ScoreContract.ScoreEntry;

    public class MySQLiteHelper extends SQLiteOpenHelper {
        // If you change the database schema, you must increment the database version.
        public static final int DATABASE_VERSION = 1;
        public static final String DATABASE_NAME = "ScoreGame.db";

        private final String SQL_CREATE_ENTRIES = "CREATE TABLE IF NOT EXISTS " + ScoreEntry.TABLE_NAME +
                " (" + ScoreEntry._ID + " " + ScoreEntry.COLUMN_TYPE_ID + " autoincrement," +
                ScoreEntry.COLUMN_NAME_USERNAME + " " + ScoreEntry.COLUMN_TYPE_USERNAME + "," +
                ScoreEntry.COLUMN_NAME_SCORE + " " + ScoreEntry.COLUMN_TYPE_SCORE + ");";

        private final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + ScoreEntry.TABLE_NAME;

        public MySQLiteHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        /* Wird beim ersten DB Zugriff aufgerufen*/
        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.d(MySQLiteHelper.class.getName(), "Creating tables in DB");
            db.execSQL(SQL_CREATE_ENTRIES);
        }

        /**/
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(MySQLiteHelper.class.getName(), "Upgrading database from version " +
                    oldVersion + " to " + newVersion);
            dropTables(db);
            onCreate(db);
        }

        private void dropTables(SQLiteDatabase db) {
            Log.d(MySQLiteHelper.class.getName(), "Dropping all tables");
            db.execSQL(SQL_DELETE_ENTRIES);
        }

}
