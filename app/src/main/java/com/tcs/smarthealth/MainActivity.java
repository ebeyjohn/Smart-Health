package com.tcs.smarthealth;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.opengl.EGLExt;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper dbhelper;
    ListView LDieces;
    ListAdapter adapter;
    public final static String MESSAGE_KEY="com.tcs.smarthealth.msgkey";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbhelper=new DatabaseHelper(getApplicationContext());
        try{
            dbhelper.createDatabase();
        }catch (IOException e) {
            e.printStackTrace();
        }
        LDieces=(ListView)findViewById(R.id.listView);
        List<Pair<Integer, String>> values;
        values=new ArrayList<Pair<Integer, String>>();
        SQLiteDatabase db=dbhelper.getWritableDatabase();
        Cursor c;
        try{
            c=db.rawQuery("SELECT * FROM symptoms",null);
            String name;
            c.moveToFirst();
            do {

                name=c.getString(2);
                int id = c.getInt(1);
                Pair<Integer, String> pair = new Pair<Integer, String>(id, name);
                values.add(pair);
            }while (c.moveToNext());
            c.close();
        }catch (Exception e){}
        db.close();
        CoustomAdapter adapter = new CoustomAdapter(MainActivity.this,values);
        LDieces.setAdapter(adapter);
        LDieces.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CoustomAdapter adapter = (CoustomAdapter)parent.getAdapter();
                Pair<Integer, String> pair = adapter.getItem(position);
                Log.e("the information you want","id:" + pair.first + " name:" + pair.second);
                Intent i1=new Intent(MainActivity.this,Symptoms.class);
                i1.putExtra(MESSAGE_KEY,pair.first.toString());
                startActivity(i1);
            }

        });

        }



    }


