package com.scsa.andr.memo4;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MemoRepository {
    private final List<MemoDto> memoList;
    private static MemoRepository instance;
    public static MemoRepository getInstance(){
        if (instance == null){
            instance = new MemoRepository();
        }

        return instance;
    }
    private MemoRepository(){
        memoList = new ArrayList<>();
        memoList.add(new MemoDto("부서회의", "부서회의 내용"));
        memoList.add(new MemoDto("개발미팅", "개발미팅 내용"));
        memoList.add(new MemoDto("소개팅", "소개팅 내용"));

    }

    public void addMemo(MemoDto memo) {
        memoList.add(memo);
    }
    public List<MemoDto> getMemos() {
        return memoList;
    }


    public void removeMemo(int position) {
        this.memoList.remove(position);
    }
}
