package kr.jaen.android.framelayout;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView iv01 = findViewById(R.id.imageView01);
        ImageView iv02 = findViewById(R.id.imageView02);
        Button btnChangeImage = findViewById(R.id.btnChangeImage);

        btnChangeImage.setOnClickListener(v -> {
            if (iv01.getVisibility() == View.VISIBLE) {
                iv01.setVisibility(View.INVISIBLE);
                iv02.setVisibility(View.VISIBLE);
            }
            else if (iv01.getVisibility() == View.INVISIBLE) {
                iv01.setVisibility(View.VISIBLE);
                iv02.setVisibility(View.INVISIBLE);
            }
        });
    }
}