package com.example.ex6_task;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    final private String TAG = "MainActivity";
    Button btA;
    public MainActivity(){
        Log.i(TAG, "객체생성" + this);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate()" + this);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        btA = findViewById(R.id.btA);
        btA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AActivity.class);
                startActivity(intent);
            }
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart()" + this);
    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume()" + this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause()" + this);
    }
    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop()" + this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy()" + this);
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "onRestart()" + this);
    }

}