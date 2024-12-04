package com.scsa.andr.memo4;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.scsa.andr.memo4.databinding.ActivityMemoEditBinding;
import com.scsa.andr.memo4.databinding.ActivityMemoMainBinding;
import com.scsa.andr.memo4.db.DBHelper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MemoEditActivity extends AppCompatActivity {
    private MemoDto selectedMemo; // 선택된 메모
    private boolean isEditMode = false; // 수정 모드 여부
//    private DBHelper dbHelper;
    private final String TAG = "MEMO EDIT_SCSA";
    private LocalMemoService service;
    private ActivityMemoEditBinding binding;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_memo_edit);

        //binding
        binding = ActivityMemoEditBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //api service
        service = ApplicationClass.retrofit.create(LocalMemoService.class);

        EditText etTitle = binding.etTitle;
        EditText etBody = binding.etBody;
        Button btnSave = binding.btnSave;
        Button btnDelete = binding.btnDelete;
        Button btnCancel = binding.btnCancel;

        if (getIntent().hasExtra("memo_id")) {

            long memoId = getIntent().getLongExtra("memo_id", -1);

            service.getMemoById(String.valueOf(memoId)).enqueue(new Callback<MemoDto>() {
                @Override
                public void onResponse(Call<MemoDto> call, Response<MemoDto> response) {

                    if (response.isSuccessful()){
                        selectedMemo = response.body();

                        if (selectedMemo != null){
                            isEditMode = true;
                            etTitle.setText(selectedMemo.getTitle());
                            etBody.setText(selectedMemo.getBody());
                            btnDelete.setVisibility(View.VISIBLE);
                        }else{
                            Toast.makeText(MemoEditActivity.this,
                                    "메모를 찾을 수 없습니다.",
                                    Toast.LENGTH_SHORT).show();
                            finish(); // 화면 종료
                        }
                    }else{
                        Log.d(TAG, "Fail Get Memo Detail ");
                    }
                }

                @Override
                public void onFailure(Call<MemoDto> call, Throwable t) {
                    Log.e(TAG, "ERROR in EditActivity getMemoById");
                }
            });

        }
        btnSave.setOnClickListener(view -> {
            String title = etTitle.getText().toString();
            String body = etBody.getText().toString();

            if (title.isEmpty() || body.isEmpty()) {
                Toast.makeText(this, "제목과 내용을 모두 입력해주세요.", Toast.LENGTH_SHORT).show();
                return;
            }

            if (isEditMode) {
                // 수정 모드: 기존 메모 수정
                selectedMemo.setTitle(title);
                selectedMemo.setBody(body);
                service.putMemo(selectedMemo).enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (response.isSuccessful()){
                            Toast.makeText(MemoEditActivity.this, "메모가 수정되었습니다.", Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "Success to putMemo in MemoEditActivity");
//                            sendBroadcast(new Intent("com.scsa.andr.memo4.UPDATE_MEMOS"));
                            Intent intent = new Intent("com.scsa.andr.memo4.UPDATE_MEMOS");
                            LocalBroadcastManager.getInstance(MemoEditActivity.this).sendBroadcast(intent);
                        }else{
                            Toast.makeText(MemoEditActivity.this, "메모 수정에 실패했습니다.", Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "Fail to putMemo in MemoEditActivity");

                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Log.e(TAG, "ERROR in EditActivity putMemo::");

                    }
                });
            } else {
                // 등록 모드: 새 메모 추가
                MemoDto newMemo = new MemoDto(title, body);
                service.postMemo(newMemo).enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if(response.isSuccessful()){
                            Toast.makeText(MemoEditActivity.this, "메모가 저장되었습니다.", Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "Success to postMemo in MemoEditActivity");
//                            sendBroadcast(new Intent("com.scsa.andr.memo4.UPDATE_MEMOS"));
                            Intent intent = new Intent("com.scsa.andr.memo4.UPDATE_MEMOS");
                            LocalBroadcastManager.getInstance(MemoEditActivity.this).sendBroadcast(intent);

                        }else{
                            Toast.makeText(MemoEditActivity.this, "메모가 저장에 실패했습니다.", Toast.LENGTH_SHORT).show();

                            Log.d(TAG, "Fail to postMemo in MemoEditActivity");

                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Log.e(TAG, "ERROR in EditActivity postMemo::");

                        Log.e(TAG, t.getMessage());
                    }
                });
            }

            finish();
        });

        // 삭제 버튼 클릭 이벤트
        btnDelete.setOnClickListener(view -> {
            if (isEditMode) {
//                dbHelper.delete(selectedMemo.getId());
                deleteMemo(selectedMemo.getId());
                Toast.makeText(this, "메모가 삭제되었습니다.", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        // 취소 버튼 클릭 이벤트
        btnCancel.setOnClickListener(view -> finish());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    private void deleteMemo(long memoId) {
        service.deleteMemo(String.valueOf(memoId)).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()){
                    Toast.makeText(MemoEditActivity.this, "삭제되었습니다.", Toast.LENGTH_SHORT)
                            .show();
                    Log.d(TAG, "deleteMemo:: id : "+ memoId);
//                    sendBroadcast(new Intent("com.scsa.andr.memo4.UPDATE_MEMOS"));
                    Intent intent = new Intent("com.scsa.andr.memo4.UPDATE_MEMOS");
                    LocalBroadcastManager.getInstance(MemoEditActivity.this).sendBroadcast(intent);

                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e(TAG, "deleteMemo Error");
            }
        });
    }
}