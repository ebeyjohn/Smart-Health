package com.tcs.smarthealth;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.TextView;

public class Symptoms extends AppCompatActivity {
    public final static String MESSAGE_KEY="com.tcs.smarthealth.msgkey";
    DatabaseHelper dbhelper;
    TextView t1;
    String name,msg;
    int ndid,nodeyes,nodeno;
    Integer iCorrectCounter = new Integer(0);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_symptoms);
        t1=(TextView)findViewById(R.id.textView);
        dbhelper=new DatabaseHelper(getApplicationContext());
        SQLiteDatabase db=dbhelper.getWritableDatabase();
        Intent i1=getIntent();
        String msg=i1.getStringExtra(MESSAGE_KEY);

        try {
            iCorrectCounter = new Integer(msg);
        } catch (Exception ignore) { }

        Cursor c;
        try{
            c=db.rawQuery("SELECT * FROM nodes where symptomID="+iCorrectCounter,null);

            c.moveToFirst();
            name=c.getString(3);
            ndid=c.getInt(2);
            c.close();
        }catch (Exception e){}
        db.close();

        t1.setText(name);
       // setContentView(t1);

    }
    public void Clickyes(View view)
    {
        SQLiteDatabase db=dbhelper.getWritableDatabase();
        Cursor c1;

        try{
            c1=db.rawQuery("SELECT * FROM nodes where symptomID="+iCorrectCounter+" and nodeId="+ndid,null);
            c1.moveToFirst();
            nodeyes=c1.getInt(4);
            c1.close();
        }catch (Exception e){}
Cursor c2;
        try{
            c2=db.rawQuery("SELECT * FROM nodes where symptomID="+iCorrectCounter+" and nodeId="+nodeyes,null);
            c2.moveToFirst();
            msg=c2.getString(3);
            ndid=c2.getInt(2);
            c2.close();
        }catch (Exception e){}

        db.close();
        t1.setText(msg);

    }
    public void Clickno(View view)
    {
        SQLiteDatabase db=dbhelper.getWritableDatabase();
        Cursor c1;

        try{
            c1=db.rawQuery("SELECT * FROM nodes where symptomID="+iCorrectCounter+" and nodeId="+ndid,null);
            c1.moveToFirst();
            nodeno=c1.getInt(5);
            c1.close();
        }catch (Exception e){}
        Cursor c2;
        try{
            c2=db.rawQuery("SELECT * FROM nodes where symptomID="+iCorrectCounter+" and nodeId="+nodeno,null);
            c2.moveToFirst();
            msg=c2.getString(3);
            ndid=c2.getInt(2);
            c2.close();
        }catch (Exception e){}

        db.close();
        t1.setText(msg);
    }
}
