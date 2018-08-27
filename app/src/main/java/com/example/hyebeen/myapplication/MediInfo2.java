package com.example.hyebeen.myapplication;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MediInfo2 extends AppCompatActivity {

    String[] items={"","","","","","","","","","","","","","","","","","","","","","","","",""
            ,"","","","","","","","","","","","","","","","","","","","","","","",""};


    private static String IP_ADDRESS = "192.168.0.225";
    private static String TAG = "phptest";

    private EditText mEditTextName;
    private EditText mEditTextCountry;
    private TextView mTextViewResult;
    private ArrayList<MediData> mArrayList;
    private UsersAdapter mAdapter;
    private EditText mEditTextSearchKeyword;
    private String mJsonString;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.medi2_set);




        final AutoCompleteTextView edit = (AutoCompleteTextView) findViewById(R.id.edit);

        //mTextViewResult = (TextView)findViewById(R.id.textView_main_result);
        //mTextViewResult.setMovementMethod(new ScrollingMovementMethod());

        mArrayList = new ArrayList<>();
        mAdapter = new UsersAdapter(this, mArrayList);

        final TextView all=(TextView)findViewById(R.id.all);
        final TextView one=(TextView)findViewById(R.id.one);



        GetData task = new GetData();
        task.execute( "http://" + IP_ADDRESS + "/getjson.php", "");

        edit.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, items));



        Button regis=(Button)findViewById(R.id.regis) ;

        Button check=(Button)findViewById(R.id.check);

        final boolean[] nameCheck = {false};
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(mArrayList.size());
                for(int i=0;i<mArrayList.size();i++) {
                    if (edit.getText().toString().equals(mArrayList.get(i).getMember_name().toString())) {
                        Toast.makeText(getApplicationContext(), "약이 확인 되었습니다.", Toast.LENGTH_SHORT).show();
                        nameCheck[0] = true;
                        break;
                    }
                }
                if(nameCheck[0]==false)
                    Toast.makeText(getApplicationContext(),"약을 찾을 수 없습니다.\n약 이름을 확인해주세요.",Toast.LENGTH_LONG).show();
            }
        });



        regis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int num=0;

                final int allsize =all.getText().toString().length();
                final int onesize =one.getText().toString().length();
                final int namesize = edit.getText().toString().length();

                MediData dataNum=new MediData();

                if(allsize == 0||onesize==0||namesize==0){
                    Toast.makeText(getApplicationContext(),"값을 입력해 주세요.",Toast.LENGTH_LONG).show();
                } else {
                    if(nameCheck[0]==false){
                        Toast.makeText(getApplicationContext(),"약 이름을 확인해주세요.",Toast.LENGTH_LONG).show();
                    } else {

                        for(int i=0;i<mArrayList.size();i++) {
                            if (edit.getText().toString().equals(mArrayList.get(i).getMember_name().toString())) {
                                num = i;
                                break;
                            }
                        }

                        dataNum.setMember_info(mArrayList.get(num).getMember_info());
                        dataNum.setMember_all(Integer.parseInt(all.getText().toString()));
                        dataNum.setMember_one(Integer.parseInt(one.getText().toString()));
                        dataNum.setMember_name(edit.getText().toString());

                        phpDown task = new phpDown();
                        task.execute("http://" + IP_ADDRESS + "/reset.php");
                    }
                }
            }
        });


    }

    private class phpDown extends AsyncTask<String, Integer,String> {



        @Override
        protected String doInBackground(String... urls) {
            StringBuilder jsonHtml = new StringBuilder();
            try{
                // 연결 url 설정
                URL url = new URL(urls[0]);
                // 커넥션 객체 생성
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                // 연결되었으면.
                if(conn != null){
                    conn.setConnectTimeout(10000);
                    conn.setUseCaches(false);
                    // 연결되었음 코드가 리턴되면.
                    if(conn.getResponseCode() == HttpURLConnection.HTTP_OK){
                        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                        for(;;){
                            // 웹상에 보여지는 텍스트를 라인단위로 읽어 저장.
                            String line = br.readLine();
                            if(line == null) break;
                            // 저장된 텍스트 라인을 jsonHtml에 붙여넣음
                            jsonHtml.append(line + "\n");
                        }
                        br.close();
                    }
                    conn.disconnect();
                }
            } catch(Exception ex){
                ex.printStackTrace();
            }
            return jsonHtml.toString();

        }

        protected void onPostExecute(String str){

            Toast.makeText(getApplicationContext(),"등록되었습니다.",Toast.LENGTH_LONG).show();
        }


    }



    private class GetData extends AsyncTask<String, Void, String>{

        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(MediInfo2.this,
                    "Please Wait", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            //mTextViewResult.setText(result);
            Log.d(TAG, "response - " + result);

            if (result == null){

                //mTextViewResult.setText(errorString);
            }
            else {

                mJsonString = result;
                showResult();
            }
        }


        @Override
        protected String doInBackground(String... params) {

            String serverURL = params[0];
            String postParameters = "country=" + params[1];


            try {

                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();


                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();


                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();


                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "response code - " + responseStatusCode);

                InputStream inputStream;
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                }
                else{
                    inputStream = httpURLConnection.getErrorStream();
                }


                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }

                bufferedReader.close();

                return sb.toString().trim();


            } catch (Exception e) {

                Log.d(TAG, "GetData : Error ", e);
                errorString = e.toString();

                return null;
            }

        }

    }


    private void showResult(){

        String TAG_JSON="medicine";
        String TAG_info = "info";
        String TAG_NAME = "name";
        String TAG_caution ="caution";
        String TAG_donot="donot";


        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for(int i=0;i<jsonArray.length();i++){

                JSONObject item = jsonArray.getJSONObject(i);

                String info = item.getString(TAG_info);
                String name = item.getString(TAG_NAME);
                String caution = item.getString(TAG_caution);
                String donot = item.getString(TAG_donot);

                MediData mediData = new MediData();

                mediData.setMember_info(info);
                mediData.setMember_name(name);


                items[i]=name;

                mArrayList.add(mediData);
                mAdapter.notifyDataSetChanged();
            }

        } catch (JSONException e) {

            Log.d(TAG, "showResult : ", e);
        }
    }


}

