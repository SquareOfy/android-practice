package com.example.ex4_compactivityapp2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class NextActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_next);

        Intent intent = getIntent();
        String msg = intent.getStringExtra("message");
        if (msg!= null) {
            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
        }
        String name = intent.getStringExtra("name");
        if (name!=null) {
            Toast.makeText(getApplicationContext(), name, Toast.LENGTH_LONG).show();
        }

        Button btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(view -> {
            setResult(NextActivity.RESULT_OK);
            finish();
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}