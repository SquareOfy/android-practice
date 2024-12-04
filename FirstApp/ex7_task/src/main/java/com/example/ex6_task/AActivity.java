package com.example.ex6_task;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public class AActivity extends AppCompatActivity {
    final private String TAG = "AActivity";
    public AActivity(){
        Log.i(TAG, "객체생성" + this);
    }
    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart()"+ this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume()"+ this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause()"+ this);
    }
    Button btA, btA_SINGLE_TOP, btA_NEW_TASK, btA_CLEAR_TASK,btB;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate()"+ this);
        setContentView(R.layout.activity_a);

        btA = findViewById(R.id.btA);
        btA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AActivity.class);
                startActivity(intent);
            }
        });

        btA_SINGLE_TOP = findViewById(R.id.btA_SINGLE_TOP);
        btA_SINGLE_TOP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });

        btB = findViewById(R.id.btB);
        btB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), BActivity.class);
                startActivity(intent);
            }
        });

        btA_NEW_TASK = findViewById(R.id.btA_NEW_TASK);
        btA_NEW_TASK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        btA_CLEAR_TASK = findViewById(R.id.btA_CLEAR_TASK);
        btA_CLEAR_TASK.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), AActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });

    }

    @Override
    public void onPostCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
        Log.i(TAG, "onPostCreate()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop()" + this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy()"+ this);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "onRestart()" + this);
    }
}