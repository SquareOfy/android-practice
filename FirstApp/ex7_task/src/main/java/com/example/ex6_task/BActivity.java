package com.example.ex6_task;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class BActivity extends AppCompatActivity {
    final private String TAG = "BActivity";
    public BActivity(){
        Log.i(TAG, "객체생성");
    }


    Button btA_CLEAR_TOP;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate()"+ this);
        setContentView(R.layout.activity_b);


        btA_CLEAR_TOP = findViewById(R.id.btA_CLEAR_TOP);
        btA_CLEAR_TOP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onPostCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
        Log.i(TAG, "onPostCreate()"+ this);
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
        Log.i(TAG, "onPause()" + this);
    }
    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop()"+ this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy()" + this);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "onRestart()"+ this);
    }
}