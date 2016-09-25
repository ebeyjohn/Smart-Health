package com.tcs.smarthealth;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
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
    Cursor qs,ins,c,count1;
    String name,msg;
    int ndid,nodeyes,nodeno,dsid,ins_id,couter,symcount=1;
    Integer iCorrectCounter = new Integer(0);
    int[] myIntArray = new int[100];
    int[] dieArray=new int[100];
    Button yes,no,b1,b2;
    public final static String MESSAGE_KEY2="com.tcs.smarthealth.msgkey";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_symptoms);
        t1=(TextView)findViewById(R.id.textView);
        yes=(Button)findViewById(R.id.buttonyes);
       no=(Button)findViewById(R.id.buttonno);
        b1=(Button)findViewById(R.id.button1);
        b1.setVisibility(View.GONE);
        b2=(Button)findViewById(R.id.button2);
        b2.setVisibility(View.GONE);
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
        symcount=symcount+1;
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


        if(qs.isAfterLast())

        {

            yes.setVisibility(view.GONE);
            no.setVisibility(view.GONE);
            Cursor cd,sd;
            int sc;
            float avg,a1,a2;
            sd=db.rawQuery("SELECT * FROM sick_sym where sid="+dsid,null);
            sc=sd.getCount();
            a1=sc;
            a2=symcount;
            avg=(a2/a1)*100;
            int chk= (int) avg;
            if(chk>=50)
            {
                if(chk==100)
                    chk=95;
            cd=db.rawQuery("SELECT * FROM sickness where sid="+dsid,null);
            cd.moveToFirst();
            name="Diagnosed Disease is "+cd.getString(1)+" : Chance by "+chk+"%";
            t1.setTextColor(Color.RED);
                b1.setVisibility(View.VISIBLE);
                b2.setVisibility(View.VISIBLE);
            }
            else
            {
                b2.setVisibility(View.VISIBLE);
                t1.setTextColor(Color.MAGENTA);
                name="Cannot diagnose - TRY AGAIN";
            }
        }
        else {
            //t1.setTextColor(Color.GREEN);
        }


    t1.setText(""+name);

    }



    public void Clickno(View view)
    {
        int i=0,f=0,cs=0;
        SQLiteDatabase db=dbhelper.getWritableDatabase();
        try {
            count1 = db.rawQuery("select * from new", null);
            cs = count1.getCount();
        }catch (Exception e) {
        }
        if(cs>1) {
            try {
                ins = db.rawQuery("SELECT sid FROM new", null);
                ins.moveToFirst();
                ins.moveToNext();
                do {
                    ins_id = ins.getInt(0);
                    dieArray[i] = ins_id;
                    i = i + 1;
                } while (ins.moveToNext());
                ins.close();
            } catch (Exception e) {
            }
            db.execSQL("delete from new");
            int j = 0;
            do {
                ins_id = dieArray[j];
                j = j + 1;
                db.execSQL("insert into new (sid)" + "values(" + ins_id + ") ;");
            } while (j < i);

        }


        try{
            c=db.rawQuery("SELECT * FROM new",null);
            c.moveToFirst();
            dsid=c.getInt(1);
            c.close();

            qs=db.rawQuery("SELECT * FROM sick_sym where sid="+dsid,null);
            qs.moveToFirst();
            int k=0;
            do{
                int j=0;
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

        if(qs.isAfterLast())

        {   yes.setVisibility(view.GONE);
            no.setVisibility(view.GONE);
            Cursor cd,sd;
            int sc;
            float avg,a1,a2;
            sd=db.rawQuery("SELECT * FROM sick_sym where sid="+dsid,null);
            sc=sd.getCount();
            a1=sc;
            a2=symcount;
            avg=(a2/a1)*100;
            int chk= (int) avg;
            if(chk>=50)
            {
                if(chk==100)
                    chk=95;
                cd=db.rawQuery("SELECT * FROM sickness where sid="+dsid,null);
                cd.moveToFirst();
                name="Diagnosed Disease is "+cd.getString(1)+" : Chance by "+chk+"%";
                t1.setTextColor(Color.RED);
                b1.setVisibility(View.VISIBLE);
                b2.setVisibility(View.VISIBLE);
            }
            else
            {   t1.setTextColor(Color.MAGENTA);
                t1.setTextSize(12);
                name="Cannot diagnos - TRY AGAIN";
                b2.setVisibility(View.VISIBLE);
            }
        }
        else {

        }


        t1.setText(""+name);




    }

    public void diecese(View view)
    {Intent i1=new Intent(Symptoms.this,Diecese.class);
        i1.putExtra(MESSAGE_KEY,String.valueOf(dsid));
        startActivity(i1);}
    public void homepage(View view)
    {Intent i1=new Intent(Symptoms.this,MainActivity.class);
        startActivity(i1);}
}
