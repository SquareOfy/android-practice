package com.scsa.andr.memo4.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import androidx.annotation.Nullable;

import com.scsa.andr.memo4.MemoDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    private static final String TAG = "DBHelper_SCSA";
    private static final String TABLE_NAME = "memo_table";
    private static final String[] COLUMNS = {"_id", "title", "body", "reg_date"};
    private SQLiteDatabase db;

    public DBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE IF NOT EXISTS ").append(TABLE_NAME).append(" (\n");
        sb.append(COLUMNS[0]).append(" integer primary key autoincrement, \n");
        sb.append(COLUMNS[1]).append(" text, \n");
        sb.append(COLUMNS[2]).append(" text, \n");
        sb.append(COLUMNS[3]).append(" text default (datetime('now', 'localtime')) \n);");
        db.execSQL(sb.toString());
        Log.d(TAG, "onCreate() :: 테이블 생성");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(sql);
        onCreate(db);
        Log.d(TAG, "onUpgrade()::업그레이드 완료");
    }

    public void open(){
        db = getWritableDatabase();
    }

    public void insert(MemoDto memo){
        String title = memo.getTitle();
        String body = memo.getBody();

        ContentValues values = new ContentValues();
        values.put(COLUMNS[1], title);
        values.put(COLUMNS[2], body);

        db.beginTransaction();
        long result = db.insert(TABLE_NAME, null, values);
        try {
            if (result > 0) {
                db.setTransactionSuccessful();
                Log.d(TAG, "insert() :: Success Insert New Memo");
            } else {
                Log.d(TAG, "insert() :: Fail Insert New Memo");
            }
        }catch (Exception e){
            Log.e(TAG, "insert() :: Error in insert()");

        }finally {
            db.endTransaction();
        }

    }

    public void update(MemoDto memo) {
        String title = memo.getTitle();
        String body = memo.getBody();
        long id = memo.getId();

        ContentValues values = new ContentValues();
        values.put("title", title);
        values.put("body", body);

        db.beginTransaction();
        try {
            int result = db.update(TABLE_NAME, values, "_id = ?", new String[]{String.valueOf(id)});
            if (result > 0) {
                db.setTransactionSuccessful();
                Log.d(TAG, "update() :: Success Update Memo with id: " + id);
            } else {
                Log.d(TAG, "update() :: Fail Update Memo with id: " + id);
            }
        } catch (Exception e) {
            Log.e(TAG, "update() :: Error updating memo", e);
        } finally {
            db.endTransaction();
        }
    }

    public void delete(long id) {
        db.beginTransaction();
        try {
            int result = db.delete(TABLE_NAME, "_id = ?", new String[]{String.valueOf(id)});
            if (result > 0) {
                db.setTransactionSuccessful();
                Log.d(TAG, "delete() :: Success Delete Memo with id: " + id);
            } else {
                Log.d(TAG, "delete() :: Fail Delete Memo with id: " + id);
            }
        } catch (Exception e) {
            Log.e(TAG, "delete() :: Error deleting memo", e);
        } finally {
            db.endTransaction();
        }
    }

    public List<MemoDto> selectAll(){
        List<MemoDto> memoList = new ArrayList<>();

        Cursor cursor = db.query(TABLE_NAME, COLUMNS,
                null, null, null, null, null);

        while(cursor.moveToNext()){
            long id = cursor.getLong(cursor.getColumnIndexOrThrow("_id"));
            String title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
            String body = cursor.getString(cursor.getColumnIndexOrThrow("body"));
            String regDateStr = cursor.getString(cursor.getColumnIndexOrThrow("reg_date"));
            LocalDateTime regDate = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                regDate = LocalDateTime.parse(regDateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            }

            MemoDto memo = new MemoDto(title, body);
            memo.setId(id);
//            memo.setRegDate(regDate);

            memoList.add(memo);
        }
        cursor.close();
        return memoList;

    }

    public MemoDto selectById(long memoId) {
        MemoDto memo = null;
        Cursor cursor = db.query(TABLE_NAME, COLUMNS, "_id = ?",
                new String[]{String.valueOf(memoId)}, null, null, null );
        if (cursor.moveToFirst()){
            long id = cursor.getLong(cursor.getColumnIndexOrThrow("_id"));
            String title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
            String body = cursor.getString(cursor.getColumnIndexOrThrow("body"));
            String regDateStr = cursor.getString(cursor.getColumnIndexOrThrow("reg_date"));

            LocalDateTime regDate = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                regDate = LocalDateTime.parse(regDateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            }

            memo = new MemoDto(title, body);
            memo.setId(id);
            Log.d(TAG, "SELECT BY ID : "+id);
//            memo.setRegDate(regDate);
        }
        cursor.close(); // 커서 닫기
        return memo; // 메모 반환 (없으면 null 반환)
    }
}
