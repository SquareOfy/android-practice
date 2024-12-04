package com.example.ex2;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);

//---------------------------------------------------------
//      3. 직접 객체를 생성하여 문자열을 수정하여 테스트 해보자

        // 3-1. ConstraintLayout 생성
        ConstraintLayout constraintLayout = new ConstraintLayout(this);
        constraintLayout.setLayoutParams(new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.MATCH_PARENT
        ));
        //--------------------------------
        // 3-2. 첫 번째 TextView 생성
        TextView textView1 = new TextView(this);
        textView1.setId(View.generateViewId());  // 텍스트뷰 ID 생성
        textView1.setText(R.string.hello_world);

        // 3-3. 두 번째 TextView 생성
        TextView textView2 = new TextView(this);
        textView2.setId(View.generateViewId());  // 텍스트뷰 ID 생성
        textView2.setText("TEST TEST");
        //--------------------------------
        // 3-4. ConstraintLayout에 TextView 추가
        constraintLayout.addView(textView1);
        constraintLayout.addView(textView2);

        // 3-5. ConstraintSet을 사용하여 제약 조건 설정
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(constraintLayout);

        // 첫 번째 TextView의 제약 조건 설정 (중앙 배치)
        constraintSet.connect(textView1.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, 0);
        constraintSet.connect(textView1.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM, 0);
        constraintSet.connect(textView1.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, 0);
        constraintSet.connect(textView1.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END, 0);

        // 두 번째 TextView의 제약 조건 설정 (첫 번째 TextView 아래에 위치)
        constraintSet.connect(textView2.getId(), ConstraintSet.START, textView1.getId(), ConstraintSet.START, 0);
        constraintSet.connect(textView2.getId(), ConstraintSet.TOP, textView1.getId(), ConstraintSet.BOTTOM, (int)(100 * getResources().getDisplayMetrics().density)); // 100dp to px

        // 3-6. 제약 조건을 ConstraintLayout에 적용
        constraintSet.applyTo(constraintLayout);

        // 3-7. 최종적으로 ConstraintLayout을 Activity의 콘텐츠 뷰로 설정
        setContentView(constraintLayout);

    }
}