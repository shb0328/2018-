package com.example.hyebeen.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button b1;
    Button b2;
    Button b3;
    Button b4;

    //localDB




    /*********************Begin of OnCreate*************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //DBHelper생성

        final DBHelper dbHelper = new DBHelper(getApplicationContext(), "MoneyBooks.db", null, 1);

        //button1
        b1 = (Button)findViewById(R.id.button1);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int buttonNum = 1;
                //TODO:  if(DB에 약정보 있는지 확인)
                if(dbHelper.isExist(buttonNum)){
                    //T등록된 정보가 있다면,
                    Intent intent_o = new Intent(getApplicationContext(), MediInfo2.class);
                    startActivity(intent_o);
                }
                else {
                    //등록된 정보가 없다면,
                    Toast.makeText(getApplicationContext(),
                            "등록된 약 정보가 없습니다.\n새로운 약을 등록해주세요.", Toast.LENGTH_LONG).show();

                    Intent intent_x = new Intent(getApplicationContext(), Medi_setting.class);
                    intent_x.putExtra("ButtonNum",buttonNum);
                    startActivity(intent_x);
                }

            }
        });

        //button2
        b2 = (Button) findViewById(R.id.button2);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str;
                str=dbHelper.getResult();
                Log.d("확인",""+str);
                int buttonNum = 2;
                //TODO:  if(DB에 약정보 있는지 확인)
                if(dbHelper.isExist(buttonNum)){
                    //T등록된 정보가 있다면,
                    Intent intent_o = new Intent(getApplicationContext(), MediInfo2.class);
                    startActivity(intent_o);
                }
                else {
                    //등록된 정보가 없다면,
                    Toast.makeText(getApplicationContext(),
                            "등록된 약 정보가 없습니다.\n새로운 약을 등록해주세요.", Toast.LENGTH_LONG).show();

                    Intent intent_x = new Intent(getApplicationContext(), Medi_setting.class);
                    intent_x.putExtra("ButtonNum",buttonNum);
                    startActivity(intent_x);
                }
            }
        });

        //button3
        b3 = (Button)findViewById(R.id.button3);
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int buttonNum = 3;
                //TODO:  if(DB에 약정보 있는지 확인)
                if(dbHelper.isExist(buttonNum)){
                    //T등록된 정보가 있다면,
                    Intent intent_o = new Intent(getApplicationContext(), MediInfo2.class);
                    startActivity(intent_o);
                }
                else {
                    //등록된 정보가 없다면,
                    Toast.makeText(getApplicationContext(),
                            "등록된 약 정보가 없습니다.\n새로운 약을 등록해주세요.", Toast.LENGTH_LONG).show();

                    Intent intent_x = new Intent(getApplicationContext(), Medi_setting.class);
                    intent_x.putExtra("ButtonNum",buttonNum);
                    startActivity(intent_x);
                }

            }
        });


        //button4
        b4 = (Button)findViewById(R.id.button4);
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int buttonNum = 4;
                //TODO:  if(DB에 약정보 있는지 확인)
                if(dbHelper.isExist(buttonNum)){
                    //T등록된 정보가 있다면,
                    Intent intent_o = new Intent(getApplicationContext(), MediInfo2.class);
                    startActivity(intent_o);
                }
                else {
                    //등록된 정보가 없다면,
                    Toast.makeText(getApplicationContext(),
                            "등록된 약 정보가 없습니다.\n새로운 약을 등록해주세요.", Toast.LENGTH_LONG).show();

                    Intent intent_x = new Intent(getApplicationContext(), Medi_setting.class);
                    intent_x.putExtra("ButtonNum",buttonNum);
                    startActivity(intent_x);
                }

            }
        });

        
    }
    /**********************END of OnCreate*************************/

}
