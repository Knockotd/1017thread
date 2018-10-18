package com.study.jeng.a1017thread;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class HandlerAndThread extends AppCompatActivity {
    //핸들러
    Handler handler = new Handler(){
      @Override
      public void handleMessage(Message msg){
          String url = (String)msg.obj;
          Toast.makeText(HandlerAndThread.this,url,Toast.LENGTH_LONG).show();
      }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler_and_thread);


        class ThreadEx extends Thread{
            String url;
            public ThreadEx(String url){
                this.url = url;
            }
            public void run(){
                try{
                    //url 을 다운로드
                    Message message = new Message();
                    message.obj = url;
                    handler.sendMessageDelayed(message,10000);


                }catch (Exception e){}
            }
        }
        Button btn = (Button)findViewById(R.id.btn);
        final EditText editText = (EditText)findViewById(R.id.editText);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = editText.getText()+"";
                ThreadEx th1 = new ThreadEx(url);
                th1.start();
            }
        });

    }
}
