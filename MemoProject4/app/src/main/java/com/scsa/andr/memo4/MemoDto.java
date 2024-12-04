package com.scsa.andr.memo4;

import android.os.Build;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MemoDto {
    private Long id;
    private String title;
    private String body;
    private long regDate; // 타임스탬프를 long으로 저장
    public MemoDto(String title, String body) {
        this.title = title;
        this.body = body;
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            this.regDate = LocalDateTime.now(); // 문자열로 포맷팅
//        }
    }

    public MemoDto(Long id, String title, String body) {
        this.title = title;
        this.body = body;
        this.id = id;

    }
    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public long getRegDate() {
        return regDate;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setRegDate(long regDate) {
        this.regDate = regDate;
    }

}
