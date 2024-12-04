package com.scsa.andr.memo4;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class MemoAdapter extends BaseAdapter {
    private final Context context;
    List<MemoDto> memos;
    MemoAdapter(Context context, List<MemoDto> memos){
        this.context = context;
        this.memos = memos;
    }
    @Override
    public int getCount() {
        return memos.size();
    }

    @Override
    public Object getItem(int position) {
        return memos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = LayoutInflater.from(context)
                    .inflate(R.layout.item_row, parent, false);
        }

        TextView title = convertView.findViewById(R.id.row_title);
        TextView regDate = convertView.findViewById(R.id.row_reg_date);
        MemoDto memo = memos.get(position);
        title.setText(memo.getTitle());
        long regDateTimestamp = memo.getRegDate();
        // 날짜 포맷팅
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            LocalDateTime dateTime =LocalDateTime.ofEpochSecond(regDateTimestamp / 1000, 0, ZoneOffset.UTC);
            String formattedDate = dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            regDate.setText(formattedDate);
        } else {
            regDate.setText(String.valueOf(regDateTimestamp)); // API 26 미만 기본 처리
        }

        return convertView;
    }
}
