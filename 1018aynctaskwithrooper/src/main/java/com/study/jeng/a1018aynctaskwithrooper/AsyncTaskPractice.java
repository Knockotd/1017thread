package com.study.jeng.a1018aynctaskwithrooper;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

class AsyncTaskEx extends AsyncTask<Integer, Integer, Integer>{
    //MainActivity 의 데이터를 저장하기 위한 변수
    ProgressBar progressBar;
    TextView textView;
    int value;


    //클래스를 메인 클래스 밖에 만들면 메인 클래스에 있는 변수를 사용할 수 없는데 생성자를 새로 만들어서 매개변수로 받아 연결 시켜주면 쓸 수 있다.
    //즉, 메인 클래스 안에 클래스를 만드는 편이 더 편리하다.

    //생성자 - MainActivity 에서 데이터를 넘겨받기 위해 생성
    public AsyncTaskEx(ProgressBar progressBar, TextView textView, int value){
        this.progressBar=progressBar;
        this.textView=textView;
        this.value=value;
        Log.e("문제 발생", "");
    }

    //인스턴스가 생성되면 가장 먼저 호출되는 메소드 - 메인 스레드에서 수행 = 화면을 갱신하는 코드를 작성해도 됨
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //UI 초기화
        value = 0;
        progressBar.setProgress(value);
    }

    //비동기적 작업을 처리하는 메소드
    //매개변수는 클래스를 생성할 때 적용한 제너릭의 두번째 자료형과 일치해야 함
    //리턴타입은 세번째 자료형과 일치해야 함
    //메인스레드에서 동작하지 않음 - UI 갱신하는 코드는 작성할 수 없음
    @Override
    protected Integer doInBackground(Integer... integers) {
        while(isCancelled() == false){
            value = value + 1;
            if(value >= 100){
                break;
            }else {
                //onProgressUpdate 호출
                publishProgress(value);
            }

            try{
                Thread.sleep(100);
            } catch (Exception e){
                Log.e("문제 발생", e.getMessage());
            }

        }
        return value;
    }

    //doInBackground 에서 publishProgress 를 호출하면 자동으로 호출되는 메소드
    //메인스레드에서 수행
    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        progressBar.setProgress(value);
        textView.setText(String.format("현재 값 : %d", value));
    }

    //인스턴스가 cencelled를 호출했을 때 호출되는 메소드
    //메인스레드에서 실행
    @Override
    protected void onCancelled(Integer integer) {
        super.onCancelled(integer);
        textView.setText("스레드 중지");
    }

    //doInBackground가 작업을 종료했을 때 호출되는 메소드
    //메인 스레드에서 실행
    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        value = 0;
        progressBar.setProgress(value);
        textView.setText("스레드 종료");
    }

}

public class AsyncTaskPractice extends AppCompatActivity {
    //프로그래스 바와 텍스트뷰 변수 선언
    ProgressBar progressBar;
    TextView textview;
    //프로그래스 바의 값을 표시할 정수 변수 선언
    int value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_task_practice);

        Button start = (Button)findViewById(R.id.start);
        Button stop = (Button)findViewById(R.id.stop);
        progressBar = (ProgressBar)findViewById(R.id.progress);
        textview = (TextView)findViewById(R.id.textView);

        View.OnClickListener clickListener = new View.OnClickListener() {
            AsyncTaskEx task = null;
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.start:
                        task = new AsyncTaskEx(progressBar, textview, value);
                        task.execute(100);
                        break;
                    case R.id.stop:
                        task.cancel(true);
                        break;
                }
            }
        };

        start.setOnClickListener(clickListener);
        stop.setOnClickListener(clickListener);
    }
}
