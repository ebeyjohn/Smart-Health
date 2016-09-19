package com.tcs.smarthealth;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Diecese extends AppCompatActivity {
    public final static String MESSAGE_KEY2="com.tcs.smarthealth.msgkey";
    TextView t1,t2;
    int id;
    Cursor c;
    DatabaseHelper dbhelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diecese);
        t1=(TextView)findViewById(R.id.textView2);
        t2=(TextView)findViewById(R.id.textView3);
        dbhelper=new DatabaseHelper(getApplicationContext());
        SQLiteDatabase db=dbhelper.getWritableDatabase();
        Intent i1=getIntent();
        String msg=i1.getStringExtra(MESSAGE_KEY2);
        try {
            id= new Integer(msg);
        } catch (Exception ignore) { }
        c=db.rawQuery("SELECT * FROM sickness where sid=="+id,null);
        c.moveToFirst();
        String msg1=c.getString(1);
       t1.setText(msg1);
        String msg2=c.getString(2);
        t2.setText(msg2);



    }
    public void homepage(View view)
    {Intent i1=new Intent(Diecese.this,MainActivity.class);
        startActivity(i1);}
}
