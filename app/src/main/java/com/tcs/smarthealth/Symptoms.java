package com.tcs.smarthealth;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Symptoms extends AppCompatActivity {
    public final static String MESSAGE_KEY="com.tcs.smarthealth.msgkey";
    DatabaseHelper dbhelper;
    TextView t1;
    Cursor qs,ins,c;
    String name,msg;
    int ndid,nodeyes,nodeno,dsid,ins_id,couter;
    Integer iCorrectCounter = new Integer(0);
    int[] myIntArray = new int[15];
    int[] dieArray=new int[100];
    Button yes,no;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_symptoms);
        t1=(TextView)findViewById(R.id.textView);
        yes=(Button)findViewById(R.id.buttonyes);
       no=(Button)findViewById(R.id.buttonno);
        dbhelper=new DatabaseHelper(getApplicationContext());
        SQLiteDatabase db=dbhelper.getWritableDatabase();
        Intent i1=getIntent();
        String msg=i1.getStringExtra(MESSAGE_KEY);

        try {
            iCorrectCounter = new Integer(msg);
        } catch (Exception ignore) { }
        db.execSQL("delete from new");

        myIntArray[0]=iCorrectCounter;
        couter=0;
        try{
            c=db.rawQuery("SELECT * FROM sick_sym where symptomID="+iCorrectCounter,null);
            c.moveToFirst();
            do {
                ins_id=c.getInt(1);
                db.execSQL("insert into new (sid)" + "values("+ins_id+") ;");
            }while (c.moveToNext());
            c.close();
            c=db.rawQuery("SELECT * FROM new",null);
            c.moveToFirst();
            dsid=c.getInt(1);
            Log.e("nnn","symptom id "+dsid+";");
            c.close();
        }catch (Exception e){}

        try{
            qs=db.rawQuery("SELECT * FROM sick_sym where sid="+dsid+" and symptomID!="+iCorrectCounter,null);
            qs.moveToFirst();
            name=qs.getString(3);
            dsid=qs.getInt(1);
            myIntArray[1]=qs.getInt(2);
            couter=1;
        }catch (Exception e){}

        t1.setText(name);
       // setContentView(t1);

    }












    public void Clickyes(View view)
    {
        yes.setVisibility(view.GONE);
        int i=0,f=0;
        SQLiteDatabase db=dbhelper.getWritableDatabase();
        try{
            ins=db.rawQuery("SELECT new.sid FROM sick_sym,new where sick_sym.sid=new.sid and sick_sym.symptomID="+myIntArray[couter],null);
            ins.moveToFirst();
            do {
                ins_id=ins.getInt(0);
                dieArray[i]=ins_id;
                i=i+1;
            }while (ins.moveToNext());
            ins.close();
        }catch (Exception e){}
        db.execSQL("delete from new");
        int j=0;
        do {
            ins_id=dieArray[j];
            j=j+1;
            db.execSQL("insert into new (sid)" + "values("+ins_id+") ;");
        }while (j<i);

        try{
        c=db.rawQuery("SELECT * FROM new",null);
        c.moveToFirst();
        dsid=c.getInt(1);
            c.close();

        qs=db.rawQuery("SELECT * FROM sick_sym where sid="+dsid,null);
        qs.moveToFirst();
            int k=0;
            do{
                j=0;
                f=0;
                while (j<=couter)
                {
                    if (myIntArray[j]==qs.getInt(2))
                        f=1;
                    j=j+1;
                }
                if (f==1)
                    qs.moveToNext();
                else
                    k=1;
            }while (k==0);
        name=qs.getString(3);
        dsid=qs.getInt(1);
            couter=couter+1;
            myIntArray[couter]=qs.getInt(2);


    }catch (Exception e){}
        yes.setVisibility(view.VISIBLE);
    t1.setText(name);

    }



























    public void Clickno(View view)
    {
        int i=0,f=0;
        SQLiteDatabase db=dbhelper.getWritableDatabase();
        try{
            ins=db.rawQuery("SELECT sid FROM new",null);
            ins.moveToFirst();
            ins.moveToNext();
            do {
                ins_id=ins.getInt(0);
                dieArray[i]=ins_id;
                i=i+1;
            }while (ins.moveToNext());
            ins.close();
        }catch (Exception e){}
        db.execSQL("delete from new");
        int j=0;
        do {
            ins_id=dieArray[j];
            j=j+1;
            db.execSQL("insert into new (sid)" + "values("+ins_id+") ;");
        }while (j<i);

        try{
            c=db.rawQuery("SELECT * FROM new",null);
            c.moveToFirst();
            dsid=c.getInt(1);
            c.close();

            qs=db.rawQuery("SELECT * FROM sick_sym where sid="+dsid,null);
            qs.moveToFirst();
            int k=0;
            do{
                j=0;
                f=0;
                while (j<=couter)
                {
                    if (myIntArray[j]==qs.getInt(2))
                        f=1;
                    j=j+1;
                }
                if (f==1)
                    qs.moveToNext();
                else
                    k=1;
            }while (k==0);
            name=qs.getString(3);
            dsid=qs.getInt(1);
            couter=couter+1;
            myIntArray[couter]=qs.getInt(2);

        }catch (Exception e){}
        yes.setVisibility(view.VISIBLE);
        t1.setText(name);
        db.close();

    }
}
