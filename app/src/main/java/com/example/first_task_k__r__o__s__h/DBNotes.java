package com.example.first_task_k__r__o__s__h;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DBNotes extends SQLiteOpenHelper {
    private final static int DB_VERSION=10;

    public final static String KEY_TITLE="title";
    public final static String KEY_NUMBER="number";
    public final static String KEY_CREATE_DATE="CreateDate";
    public final static String KEY_LOGIN="login";
    public final static String KEY_CONTEXT="context";
    public final static String KEY_TEXT_NOTE="textNote";
    public final static String KEY_IMAGE_PATH="imagePath";
    public final static String KEY_LOCATION="location";
    public final static String KEY_ACCESS="access";
    public final static String KEY_TABLE_NAME="notes";

    public DBNotes(Context context) {
        super(context, "DBNotes_.db", null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String table="create table " + KEY_TABLE_NAME + " (" + KEY_TITLE + " text," + KEY_NUMBER + " Integer," + KEY_CREATE_DATE + " Long," + KEY_LOGIN + " text," + KEY_CONTEXT + " text," + KEY_TEXT_NOTE + " text," + KEY_IMAGE_PATH + " text," + KEY_LOCATION + " text," + KEY_ACCESS + " Integer)";
        sqLiteDatabase.execSQL(table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        try{
            System.out.println("UPGRADE DB oldVersion="+oldVersion+" - newVersion="+newVersion);
            recreateDb(sqLiteDatabase);
            if (oldVersion<10){
                String query="create table " + KEY_TABLE_NAME + " (" + KEY_TITLE + " text," + KEY_NUMBER + " Integer," + KEY_CREATE_DATE + " Long," + KEY_LOGIN + " text," + KEY_CONTEXT + " text," + KEY_TEXT_NOTE + " text," + KEY_IMAGE_PATH + " text," + KEY_LOCATION + " text," + KEY_ACCESS + " Integer)";
                sqLiteDatabase.execSQL(query);
            }
        }
        catch (Exception e){e.printStackTrace();}
    }

    public void insertNote (ToDoDocuments queryValues){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_TITLE, queryValues.getTitle());
        values.put(KEY_NUMBER, queryValues.getNumber());
        values.put(KEY_CREATE_DATE, queryValues.getCreateDate().getTime());
        values.put(KEY_LOGIN, queryValues.getLogin());
        values.put(KEY_CONTEXT, queryValues.getContext());
        values.put(KEY_TEXT_NOTE, queryValues.getTextNote());
        values.put(KEY_IMAGE_PATH, queryValues.getImagePath().toString());
        values.put(KEY_LOCATION, queryValues.getLocation());
        values.put(KEY_ACCESS, queryValues.getAccess());
        database.insert(KEY_TABLE_NAME, null, values);

        database.close();

    }

    private void recreateDb(SQLiteDatabase sqLiteDatabase) {
        onCreate(sqLiteDatabase);
    }

    public  List<ToDoDocuments> getNote (String login){
        List<ToDoDocuments> read = new ArrayList<ToDoDocuments>();

        String query = "select * from "+KEY_TABLE_NAME+" where login = '"+login+"'";

        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(query, null);

        if (cursor.moveToFirst()){
            do {
                ToDoDocuments myNote = new ToDoDocuments();
                myNote.setTitle(cursor.getString(0));
                myNote.setNumber(cursor.getInt(1));
                myNote.setCreateDate(new Date(cursor.getLong(2)));
                myNote.setLogin(cursor.getString(3));
                myNote.setContext(cursor.getString(4));
                myNote.setTextNote(cursor.getString(5));
                myNote.setImagePath(Uri.parse(cursor.getString(6)));
                myNote.setLocation(cursor.getString(7));
                myNote.setAccess(cursor.getInt(8));
                read.add(myNote);
            } while (cursor.moveToNext());
        }
        return read;
    }

    public void deleteNote (ToDoDocuments doc){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(KEY_TABLE_NAME, KEY_CREATE_DATE+" = " + doc.getCreateDate().getTime(), null);
        db.close();
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // super.onDowngrade(db, oldVersion, newVersion);
        System.out.println("DOWNGRADE DB oldVersion="+oldVersion+" - newVersion="+newVersion);
    }


}
