package com.study.jeng.a1017thread;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class HandlerActivity extends AppCompatActivity {
    TextView textView;
    Handler handler = new Handler(){
        int i = 0;

        @Override
        public void handleMessage(Message msg) {
            //super.handleMessage(msg);
            textView.setText(i+"");
            i=i+1;
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler);
        textView = (TextView)findViewById(R.id.textView);

        //-> 주기적으로 TextView의 값이 바뀜.
        new Thread(new Runnable(){
          public void run(){
              for(int i = 0; i < 10; i = i + 1){
                  try{
                      Thread.sleep(1000);
                      handler.sendEmptyMessage(0);
                  }catch (Exception e){
                      e.printStackTrace();
                  }
              }
          }
        }).start();
    }
}
