package com.example.firstapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private final String TAG ="MainActivity";
    private int count = 0; //클릭횟수

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        //res폴더의 layout 폴더 내에 있는 xml 파일
        setContentView(R.layout.activity_main);
        Log.i(TAG, ""+R.id.textView);
        TextView textView = findViewById(R.id.textView);
        Log.i(TAG, textView.getText().toString());
//        String appName = getText(R.string.app_name).toString();
//        textView.setText(appName);
        Button bt = findViewById(R.id.button);
//        bt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
        bt.setOnClickListener(v -> {
            count++;
            Log.i(TAG, "버튼이 "+count+"회 클릭되었습니다.");
        });
        Toast.makeText(getApplicationContext(),
                "버튼이 "+count+"회 클릭되었습니다.", Toast.LENGTH_SHORT).show();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}