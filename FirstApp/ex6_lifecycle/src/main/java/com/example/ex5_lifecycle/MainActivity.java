package com.example.ex5_lifecycle;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Activity가 처음부터 시작되면 다음 세 가지 수명 주기 콜백이 순서대로 모두 호출.
 *
 * onCreate(): 시스템이 앱을 생성할 때 호출.
 * onStart(): 앱이 화면에 표시되도록 하지만 사용자는 아직 앱과 상호작용할 수 없음.
 * onResume(): 앱을 포그라운드로 가져오고 사용자는 이제 앱과 상호작용할 수 있음.
 * 이름과 달리 onResume() 메서드는 다시 시작할 대상이 없어도 시작 시 호출.
 */
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";


    private int dessertCount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate");

        setContentView(R.layout.activity_main);

        TextView tvCount = findViewById(R.id.tvCount); //클릭된 디저트 개수용 TextView

        /*
         *화면 전환시 유지 2. 유지할 데이터값 얻기
         */
        if (savedInstanceState != null) {
            dessertCount = savedInstanceState.getInt("dessertCount", 0); // 기본값 0
        }
        tvCount.setText(dessertCount+"개");


        /*
         *디저트를 클릭 할 때
         */
        ImageView dessert = findViewById(R.id.imageDessert);


        dessert.setOnClickListener(view ->{
            dessertCount++;
            tvCount.setText(dessertCount+"개");
        });

        /*
         * 공유버튼 클릭 할 때
         */
        ImageButton shareButton = findViewById(R.id.share_button);
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareText("공유텍스트입니다");
            }
        });
    }

    private void shareText(String text) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, text);
        sendIntent.setType("text/plain");

        Intent shareIntent = Intent.createChooser(sendIntent, null);
        startActivity(shareIntent);
    }



    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart");
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        Log.i(TAG, "onReStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause");
    }
    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy");
    }

    /*
     *화면 전환시 유지 1. 유지할 데이터값 설정하기
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TAG, "onSaveInstanceState");
        outState.putInt("dessertCount", dessertCount); // 정수값 저장
    }
}