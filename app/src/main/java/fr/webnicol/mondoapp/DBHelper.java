package fr.webnicol.mondoapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by patex on 11/04/16.
 */
public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 4;
    private static final String DICTIONARY_TABLE_NAME = "credentials";
    private static final String DATABASE_NAME = "mondo";
    private static final String DICTIONARY_TABLE_CREATE =
            "CREATE TABLE " + DICTIONARY_TABLE_NAME + " (" +
                    "id" + " integer primary key, " +
                    "access_token" + " TEXT, " +
                    "user_id" + " TEXT, " +
                    "client_id" + " TEXT, " +
                    "account_id" + " TEXT, " +
                    "refresh_token" + " TEXT, " +
                    "username" + " TEXT);";

    DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DICTIONARY_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS credentials");
        onCreate(db);
    }

    public Cursor getData(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from credentials where id="+id+"", null );
        return res;
    }

    public long insertCred  (String accessToken, String userId, String clientId, String accountId, String refreshToken, String username)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("access_token", accessToken);
        contentValues.put("user_id", userId);
        contentValues.put("client_id", clientId);
        contentValues.put("account_id", accountId);
        contentValues.put("refresh_token", refreshToken);
        contentValues.put("username", username);

        return db.insert("credentials", null, contentValues);
    }

    public int updateCred (Integer id, String accessToken, String userId, String clientId, String accountId, String refreshToken, String username)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("access_token", accessToken);
        contentValues.put("user_id", userId);
        contentValues.put("client_id", clientId);
        contentValues.put("account_id", accountId);
        contentValues.put("refresh_token", refreshToken);
        contentValues.put("username", username);

        return db.update("credentials", contentValues, "id = ? ", new String[] { Integer.toString(id) } );
    }

    public Integer deleteCred (Integer id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("credentials",
                "id = ? ",
                new String[] { Integer.toString(id) });
    }


}