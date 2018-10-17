package com.study.jeng.a1017thread;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class HandlerDialog extends AppCompatActivity {

    //진행률을 표시하기 위한 대화상자
    ProgressDialog progressDialog;
    //값을 나타낼 변수
    int value;
    boolean isQuit;

    Button downLoad;
    ProgressBar progressBar;
    TextView textView;

    public void download(){
        try{
            Thread.sleep(100);
            Toast.makeText(this, "다운로드 완료",Toast.LENGTH_SHORT).show();
        }catch (Exception e){}
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler_dialog);

        downLoad = (Button)findViewById(R.id.downLoad);
        downLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(HandlerDialog.this)
                        .setTitle("다운로드")
                        .setMessage("다운로드하시겠습니까?")
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                value = 0;
                                isQuit = false;
                                progressBar.setMax(100);
                                handler.sendEmptyMessage(0);
                            }
                        })
                        .setNegativeButton("아니오", null)
                        .show();


            }
        });


        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        textView = (TextView)findViewById(R.id.textView);

    }

    Handler handler = new Handler(){
        public void handleMessage(Message message){

            value++;
            try{
                Thread.sleep(50);
                if(value <= 100){
                    progressBar.setProgress(value);
                    textView.setText(""+value+"/"+progressBar.getMax());
                    handler.sendEmptyMessage(0);
                }else {
                    isQuit = true;
                    progressBar.setVisibility(View.INVISIBLE);
                    textView.setVisibility(View.INVISIBLE);
                    download();
                }
            }catch (Exception e){

            }
        }
    };
}
