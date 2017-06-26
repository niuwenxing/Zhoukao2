package com.example.homework;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MySearchView mySearchView=new MySearchView();
        mySearchView.execute("http://www.qubaobei.com/ios/cf/dish_list.php?stage_id=1&limit=20&page=1");

    }
    class MySearchView extends AsyncTask<String,Integer,String>{

        @Override
        protected String doInBackground(String... params) {
            try {
                URL url=new URL(params[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setConnectTimeout(5000);
                urlConnection.connect();
                InputStream inputStream = urlConnection.getInputStream();
                byte[] b=new byte[1024];
                int len=0;
                ByteArrayOutputStream OutputStream=new ByteArrayOutputStream();
                while((len=inputStream.read(b))!=-1){
                    OutputStream.write(b,0,len);
                }
                String s = OutputStream.toString();
                return s;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Gson gson=new Gson();
            javabean javabean = gson.fromJson(s, javabean.class);
            List<com.example.homework.javabean.DataBean> data = javabean.getData();


            Adapter adapter=new Adapter();
        }
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }
    }


}
