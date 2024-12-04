package com.scsa.andr.memo4;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.scsa.andr.memo4.databinding.ActivityMemoMainBinding;
import com.scsa.andr.memo4.db.DBHelper;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MemoMainActivity extends AppCompatActivity {
    private MemoAdapter adapter; // 어댑터를 멤버 변수로 선언
    private final String TAG = "MemoMain_SCSA";
//    private MemoRepository repository;

    private ActivityMemoMainBinding binding;
//    private DBHelper dbHelper;
    private LocalMemoService service;
    private List<MemoDto> memoList = new ArrayList<>();

    private static final int REQUEST_PERMISSIONS = 100;
    boolean hasPermission = false;

    private final String [] REQUIRED_PERMISSIONS = new String []{
            "android.permission.RECEIVE_SMS"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_memo_main);

        //SMS
        // 1. 권한이 있는지 확인.
        int permissionCheck = ContextCompat.checkSelfPermission(this,
                REQUIRED_PERMISSIONS[0]);

        // 2. 권한이 없으면 런타임 퍼미션 창 띄우기. 있으면 정상진행.
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, REQUEST_PERMISSIONS);
        }else{
            hasPermission = true;
        }

        //Binding
        binding = ActivityMemoMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //API Service
        service = ApplicationClass.retrofit.create(LocalMemoService.class);

        adapter = new MemoAdapter(this, memoList);
        binding.memoListView.setAdapter(adapter);
        //전체 메모 리스트 불러오기
        refresh();
        registerBroadcastReceiver();

        // Item Click Listener
        binding.memoListView.setOnItemClickListener((parent, view, position, id) -> {
            MemoDto selectedMemo = memoList.get(position);
            Intent intent = new Intent(MemoMainActivity.this, MemoEditActivity.class);
            intent.putExtra("memo_id", selectedMemo.getId());
            Log.d(TAG, "CLICK MEMO DETAIL ID : " + selectedMemo.getId());
            startActivity(intent);
        });


        //툴바 +
        androidx.appcompat.widget.Toolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);


        //FAB 버튼 +
        FloatingActionButton fabAdd = binding.fabAdd;
        fabAdd.setOnClickListener(view -> {
            Intent intent = new Intent(MemoMainActivity.this, MemoEditActivity.class);
            startActivity(intent);
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void refresh(){
        service.getAllMemos().enqueue(new Callback<List<MemoDto>>() {
            @Override
            public void onResponse(Call<List<MemoDto>> call, Response<List<MemoDto>> response) {
                if (response.isSuccessful()){
                    memoList = response.body();

                    ListView listView = binding.memoListView;
                    adapter = new MemoAdapter(MemoMainActivity.this, memoList);

                    listView.setAdapter(adapter);
                    listView.setOnItemClickListener((parent, view, position, id) -> {
                        MemoDto selectedMemo = memoList.get(position);
                        Intent intent = new Intent(MemoMainActivity.this, MemoEditActivity.class);
                        intent.putExtra("memo_id", selectedMemo.getId());
                        Log.d(TAG, "CLICK MEMO DETAIL ID : "+ selectedMemo.getId());
                        startActivity(intent);
                    });

                    //context menu
                    registerForContextMenu(listView);

                }else{
                    Log.d(TAG, "Fail Get Memo Detail ");
                    Toast.makeText(MemoMainActivity.this, "메모 불러오기 서버에러", Toast.LENGTH_SHORT);


                }
            }

            @Override
            public void onFailure(Call<List<MemoDto>> call, Throwable t) {
                Log.e(TAG, "Get MemoList Error");
                Log.e(TAG, t.getMessage());
                Toast.makeText(MemoMainActivity.this, "메모 불러오기 실패", Toast.LENGTH_SHORT);
            }
        });
    }

    // requestPermissions의 call back method
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean allPermissionGranted = true;

        if (requestCode == REQUEST_PERMISSIONS) {
            for (int grantResult : grantResults) {
                if (grantResult == PackageManager.PERMISSION_DENIED) {
                    allPermissionGranted = false;
                    break;
                }
            }
            if ( allPermissionGranted) {
                Toast.makeText(this,
                        "권한 획득 성공!", Toast.LENGTH_SHORT).show();
                hasPermission = true;

            }
            else {
                Toast.makeText(this,
                        "권한 획득 실패!", Toast.LENGTH_SHORT).show();

                showDialog();

            }
        }
    }
    private void showDialog(){
        android.app.AlertDialog dialog = new android.app.AlertDialog.Builder(MemoMainActivity.this)
                .setTitle("권한확인")
                .setMessage("서비스를 정상적으로 이용하려면, 권한이 필요합니다. 설정화면으로 이동합니다.")
                .setPositiveButton("예", (dialogInterface, i) -> {
                    //권한설정화면으로 이동.
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                            .setData(Uri.parse("package:" + getPackageName()));
                    startActivity(intent);
                })
                .setNegativeButton("아니오", (dialogInterface, which) -> {
                    Toast.makeText(MemoMainActivity.this, "권한이 필요합니다.", Toast.LENGTH_SHORT).show();
                })
                .create();
        dialog.show();
    }

    //Option Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.register_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_add) {
            Intent intent = new Intent(MemoMainActivity.this, MemoEditActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //Context Menu


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.delete_menu, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    //메뉴 item이 선택됐을 때 호출
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        //삭제
        if (item.getItemId() == R.id.delete_item){
            new AlertDialog.Builder(this)
                    .setTitle("삭제 확인")
                    .setMessage("정말로 삭제하시겠습니까?")
                    .setIcon(R.drawable.outline_warning_24)
                    .setPositiveButton("예", (dialog, which) -> {
                        // 메모 삭제
                        AdapterView.AdapterContextMenuInfo info =
                                    (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                        int position = info.position;
                        long memoId = memoList.get(position).getId();
                        deleteMemo(memoId);
//                        adapter.notifyDataSetChanged();
                        Intent intent = new Intent("com.scsa.andr.memo4.UPDATE_MEMOS");
                        LocalBroadcastManager.getInstance(MemoMainActivity.this).sendBroadcast(intent);
                    })
                    .setNegativeButton("아니오", (dialog, which) -> {
                        dialog.dismiss();
                    })
                    .create()
                    .show();
            return true;
        }
        return super.onContextItemSelected(item);
    }



    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }

    private void deleteMemo(long memoId) {
        service.deleteMemo(String.valueOf(memoId)).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()){
                    Toast.makeText(MemoMainActivity.this, "삭제되었습니다.", Toast.LENGTH_SHORT)
                            .show();
                    Log.d(TAG, "deleteMemo:: id : "+ memoId);

                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e(TAG, "deleteMemo Error");
            }
        });
    }

    private void registerBroadcastReceiver() {
        LocalBroadcastManager.getInstance(this).registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                refresh();
            }
        }, new IntentFilter("com.scsa.andr.memo4.UPDATE_MEMOS"));
    }


}