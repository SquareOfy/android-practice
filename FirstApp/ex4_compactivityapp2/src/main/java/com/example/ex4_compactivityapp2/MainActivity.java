package com.example.ex4_compactivityapp2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private final ActivityResultLauncher<Intent> startActivityResultLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result ->{
                if (result.getResultCode() == Activity.RESULT_OK){
                    Toast.makeText(this, "다시 호출되기", Toast.LENGTH_SHORT).show();
                }
            });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        Button btnNext = findViewById(R.id.btn_next);
        btnNext.setOnClickListener(view -> {
            Intent intent = new Intent(this, NextActivity.class);
            intent.putExtra("message", "두번째 페이지입니다.");
            startActivity(intent);
        });
        Button btnNextForResult = findViewById(R.id.btn_next_for_result);
        btnNextForResult.setOnClickListener(view ->{
            Intent intent = new Intent(this, NextActivity.class);
            intent.putExtra("name", "황윤영");
            startActivityResultLauncher.launch(intent);
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}