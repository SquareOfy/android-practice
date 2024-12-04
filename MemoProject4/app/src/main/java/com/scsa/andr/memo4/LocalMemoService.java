package com.scsa.andr.memo4;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface LocalMemoService {

    @GET("api/memo")
    Call<List<MemoDto>> getAllMemos();

    @GET("api/memo/{id}")
    Call<MemoDto> getMemoById(@Path("id") String id);

    @POST("api/memo")
    Call<String> postMemo(@Body MemoDto memo);

    @PUT("api/memo")
    Call<String> putMemo(@Body MemoDto memo);


    @DELETE("api/memo/{id}")
    Call<String> deleteMemo(@Path("id") String id);
}
