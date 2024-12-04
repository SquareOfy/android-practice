package com.example.android.e_widget;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
//import android.widget.SearchView;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ProgressBar progressBar = findViewById(R.id.progressBar);

        // 작업 시작 시 ProgressBar 보이기
        progressBar.setVisibility(View.VISIBLE);
        new Thread(new Runnable() {
            @Override
            public void run() {
                //안드로이드에서는 UI 요소에 대한 모든 업데이트가 메인 스레드에서만 수행되어야 한다.
                // runOnUiThread() : 데이터 로딩이나 네트워크 요청 등의 비동기 작업을 수행할 때, runOnUiThread()는 Main스레드에서 작동하여
                // 작업이 완료된 후 UI를 업데이트해야 하는 경우에 사용.

                // 작업 시뮬레이션 (10초 대기)
                try {
                    Thread.sleep(10000);
                    Log.i(TAG, "10초 후 : Thread Name=" + Thread.currentThread().getName());
//                    ProgressBar 숨기기
//                     작업 완료 후 ProgressBar 숨기기
//                    새로운 스레드에서는 UI업데이트 효과가 없다. runOnUiThread()가 필요
//                    progressBar.setVisibility(View.GONE); //새로운 스레드에서는 UI업데이트 효과가 없다.
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // ProgressBar 숨기기
                // 작업 완료 후 ProgressBar 숨기기
                runOnUiThread(() -> {
                    Log.i(TAG, "runOnUiThread(): Thread Name=" + Thread.currentThread().getName());
                    progressBar.setVisibility(View.GONE);
                });
            }
        }).start();


        //progress bar style="?android:attr/progressBarStyleHorizontal"
        //작업의 진행 상황을 숫자로 표시
        ProgressBar progressBar2 = findViewById(R.id.progressBar2);
        progressBar2.setProgress(50); // 50% 진행


        SeekBar seekBar = findViewById(R.id.seekBar);

        // SeekBar의 값 변경 리스너 설정
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // progress 값이 변경될 때마다 호출됨
                // 여기서 progress 값을 사용하여 작업 수행
                Log.i(TAG, "onProgressChanged() progress=" + progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // 사용자가 터치 시작할 때 호출됨
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // 사용자가 터치 종료할 때 호출됨
            }
        });

        RatingBar ratingBar = findViewById(R.id.ratingBar);

        // RatingBar의 값 변경 리스너 설정
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                // 현재 별점 출력
                Log.i(TAG, "onRatingChanged() rating=" + rating);
            }
        });

        /*
         SearchView의 텍스트 변화에 대한 리스너
         테스트할 때는 1. onQueryTextChange(), 2.onQueryTextSubmit() 둘 중 1개씩 테스트한다
         */
        SearchView searchView = findViewById(R.id.searchView);
        searchView.setMaxWidth(Integer.MAX_VALUE);
        ListView listView = findViewById(R.id.listView);

        // Sample data
        ArrayList<String> originList = new ArrayList<>();
        originList.add("Apple");
        originList.add("Banana");
        originList.add("Cherry");

        // 빈 리스트 생성
        ArrayList<String> copiedList = new ArrayList<>();
        copiedList.addAll(originList);

        // Set up the adapter
        ArrayAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, copiedList);
        listView.setAdapter(adapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            /**
             * 2.
             *  엔터를 눌렀을 때 호출
             * @param query  사용자가 입력한 검색어
             * @return true를 반환하면 SearchView가 기본 검색어 제출 동작(사용자가 검색어를 제출했을 때 SearchView가 기본적으로 제공하는 행동(예: 검색 결과를 보여주는 것))을 중단
             *         false를 반환하면  기본 검색어 제출 동작을 수행
             */
            @Override
            public boolean onQueryTextSubmit(String query) {
//                if (query.isEmpty()) {
//                    return false; // 검색어가 없으면 기본 동작을 수행하도록 허용
//                }
//                // 예를 들어, 단어를 포함한 검색어에 따라 필터링된 결과를 보여주는 방법
//                // (이 부분은 실제 데이터에 따라 다르게 구현될 수 있습니다)
//                ArrayList<String> results = new ArrayList<>();
//                for (String item : copiedList) {
//                    if (item.toLowerCase().contains(query.toLowerCase())) {
//                        results.add(item);
//                    }
//                }
//                Toast.makeText(MainActivity.this, "엔터로 검색", Toast.LENGTH_SHORT).show();
//
//                adapter.getFilter().filter(query);
////
////                // 필터링된 결과를 ListView에 업데이트하는 방법
//                adapter.clear(); // 기존 데이터를 지우고
//                adapter.addAll(results); // 새로운 결과를 추가
//                adapter.notifyDataSetChanged(); // 변경된 내용을 ListView에 반영

//                return false;
                return true;
            }

            /**
             * 1.
             * 사용자가 입력할 때마다 호출
             * @param newText newText는 사용자가 입력한 텍스트
             * @return true이면 입력한 텍스트에 대한 기본 동작을 수행하지 않도록한다.
             * 하지만 onQueryTextChange()는 기본 동작이 없기 때문에 보통은 기본 동작을 제어하지 않고 true로 설정하는 것이 일반적이다
             */
            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.isEmpty()) {
                    // 검색어가 지워졌을 때의 동작
                    Toast.makeText(MainActivity.this, "onQueryTextChange 검색어가 삭제되었습니다.", Toast.LENGTH_SHORT).show();
                }
                adapter.getFilter().filter(newText);
                return  true;
            }
        });

        //webview를 사용하려면 AndroidManifest.xml에서 인터넷 사용 권한을 설정해야한다<uses-permission android:name="android.permission.INTERNET"/>
        WebView webView = findViewById(R.id.webview);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("https://news.samsung.com/kr/");


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

}