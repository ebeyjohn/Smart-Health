package com.tcs.smarthealth;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Pair;
import android.widget.TextView;

public class Symptoms extends AppCompatActivity {
    public final static String MESSAGE_KEY="com.tcs.smarthealth.msgkey";
    DatabaseHelper dbhelper;
    TextView t1;
    String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_symptoms);
        t1=(TextView)findViewById(R.id.textView);
        dbhelper=new DatabaseHelper(getApplicationContext());
        SQLiteDatabase db=dbhelper.getWritableDatabase();
        Intent i1=getIntent();
        String msg=i1.getStringExtra(MESSAGE_KEY);
        Integer iCorrectCounter = new Integer(0);
        try {
            iCorrectCounter = new Integer(msg);
        } catch (Exception ignore) { }

        Cursor c;
        try{
            c=db.rawQuery("SELECT * FROM nodes where symptomID="+iCorrectCounter,null);

            c.moveToFirst();
            name=c.getString(3);
            c.close();
        }catch (Exception e){}
        db.close();

        t1.setText(name);
       // setContentView(t1);

    }
}
