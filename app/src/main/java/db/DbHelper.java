package db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import model.Match;

public class DbHelper extends SQLiteOpenHelper {

    public static String DATABASE_NAME = "db_PremierLeague";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_MATCH = "match_table";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //--TABLE ITEM ------------------------------------------------------------------------------//
    private static final String MATCH_ID = "match_id";
    private static final String MATCH_CLUBNAME_1 = "match_clubname_1";
    private static final String MATCH_CLUBNAME_2 = "match_clubname_2";
    private static final String MATCH_CLUBLOGO_1 = "match_clublogo_1";
    private static final String MATCH_CLUBLOGO_2 = "match_clublogo_2";
    private static final String MATCH_TANGGAL = "match_tanggal";
    private static final String MATCH_WAKTU = "match_waktu";
    private static final String MATCH_DESKRIPSI = "match_deskripsi";

    private static final String CREATE_TABLE_MATCH = "CREATE TABLE "
            +TABLE_MATCH+ "("+MATCH_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+MATCH_CLUBNAME_1+" TEXT, "+MATCH_CLUBNAME_2+" TEXT," +
            " "+MATCH_CLUBLOGO_1+" BLOB, "+MATCH_CLUBLOGO_2+" BLOB," +
            " "+MATCH_TANGGAL+" TEXT, "+MATCH_WAKTU+" TEXT, "+MATCH_DESKRIPSI+" TEXT );";

    //--TABLE ORDER CRUD CODE---------------------------------------------------------------------//
    public long createMatch(String match_clubname_1, String match_clubname_2,
                            byte[] match_clublogo_1, byte[] match_clublogo_2,
                            String match_tanggal, String match_waktu, String match_deskripsi) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(MATCH_CLUBNAME_1, match_clubname_1);
        values.put(MATCH_CLUBNAME_2, match_clubname_2);
        values.put(MATCH_CLUBLOGO_1, match_clublogo_1);
        values.put(MATCH_CLUBLOGO_2, match_clublogo_2);
        values.put(MATCH_TANGGAL, match_tanggal);
        values.put(MATCH_WAKTU, match_waktu);
        values.put(MATCH_DESKRIPSI, match_deskripsi);

        long insert = db.insert(TABLE_MATCH, null, values);
        return insert;
    }

    @SuppressLint("Range")
    public ArrayList<Match> readMatch() {
        ArrayList<Match> userModelArrayList = new ArrayList<Match>();

        String selectQuery = "SELECT * FROM " + TABLE_MATCH;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                Match match = new Match();
                match.setMatch_id(c.getInt(c.getColumnIndex(MATCH_ID)));
                match.setMatch_clubname_1(c.getString(c.getColumnIndex(MATCH_CLUBNAME_1)));
                match.setMatch_clubname_2(c.getString(c.getColumnIndex(MATCH_CLUBNAME_2)));
                match.setMatch_clublogo_1(c.getBlob(c.getColumnIndex(MATCH_CLUBLOGO_1)));
                match.setMatch_clublogo_2(c.getBlob(c.getColumnIndex(MATCH_CLUBLOGO_2)));
                match.setMatch_tanggal(c.getString(c.getColumnIndex(MATCH_TANGGAL)));
                match.setMatch_waktu(c.getString(c.getColumnIndex(MATCH_WAKTU)));
                match.setMatch_deskripsi(c.getString(c.getColumnIndex(MATCH_DESKRIPSI)));
                userModelArrayList.add(match);
            } while (c.moveToNext());
        }
        return userModelArrayList;
    }

    public int updateMatch(int match_id, String match_clubname_1, String match_clubname_2,
                           byte[] match_clublogo_1, byte[] match_clublogo_2,
                           String match_tanggal, String match_waktu, String match_deskripsi) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(MATCH_ID, match_id);
        values.put(MATCH_CLUBNAME_1, match_clubname_1);
        values.put(MATCH_CLUBNAME_2, match_clubname_2);
        values.put(MATCH_CLUBLOGO_1, match_clublogo_1);
        values.put(MATCH_CLUBLOGO_2, match_clublogo_2);
        values.put(MATCH_TANGGAL, match_tanggal);
        values.put(MATCH_WAKTU, match_waktu);
        values.put(MATCH_DESKRIPSI, match_deskripsi);

        return db.update(TABLE_MATCH, values, MATCH_ID + " = ?", new String[]{String.valueOf(match_id)});
    }

    public void deleteMatch(int match_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_MATCH, MATCH_ID + " = ?", new String[]{String.valueOf(match_id)});
    }
    //--------------------------------------------------------------------------------------------//
    //--------------------------------------------------------------------------------------------//

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_MATCH);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS '" + TABLE_MATCH + "'");
        onCreate(db);
    }
}
