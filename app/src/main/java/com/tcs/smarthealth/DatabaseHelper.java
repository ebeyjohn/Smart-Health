package com.tcs.smarthealth;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.util.Pair;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ebey John on 8/2/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    String DB_PATH=null;
    private static String DB_NAME="Health.db";
    private SQLiteDatabase myDatabase;
    private final Context myContext;


    public DatabaseHelper(Context context) {
        super(context,DB_NAME,null,10);
        this.myContext=context;
        this.DB_PATH="/data/data/" + context.getPackageName() + "/" + "databases/";
        Log.e("Path 1",DB_PATH);
    }

    public void createDatabase() throws IOException{

        boolean dbExist = checkDatabase();
        if (dbExist)
        {}
        else {
            this.getReadableDatabase();
            try{
                copyDatabase();
            }catch (IOException e){throw new RuntimeException(e);}
        }
    }

    private boolean checkDatabase(){
        SQLiteDatabase chkDB=null;
        try {
            String myPath=DB_PATH + DB_NAME ;
            chkDB=SQLiteDatabase.openDatabase(myPath,null,SQLiteDatabase.OPEN_READONLY);
        }catch (SQLiteException e){}
        if (chkDB != null)
            chkDB.close();
        return chkDB !=null ?true : false ;
    }

    private void copyDatabase() throws  IOException{

        InputStream myInput=myContext.getAssets().open(DB_NAME);
        String Outfilename= DB_PATH + DB_NAME ;
        OutputStream myOutput=new FileOutputStream(Outfilename);
        byte[] buffer= new byte[10];
        int length;
        while ((length=myInput.read(buffer))>0){
            myOutput.write(buffer,0,length);
        }
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }
    public void openDataBase() throws  SQLiteException{
        String myPath= DB_PATH + DB_NAME;
        myDatabase= SQLiteDatabase.openDatabase(myPath,null,SQLiteDatabase.OPEN_READONLY);
    }
    public synchronized void close()
    {
        if(myDatabase != null)
            myDatabase.close();
        super.close();
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        if (newVersion > oldVersion)
            try {
                copyDatabase();
            } catch (IOException e) {
                e.printStackTrace();

            }}

}
