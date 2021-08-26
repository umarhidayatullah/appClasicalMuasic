package com.example.jodoh.service;

import com.example.jodoh.entity.JodohEntity;
import com.example.jodoh.entity.UserEntity;

import java.util.List;

import javax.annotation.PostConstruct;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface UserInterface {
    @Multipart
    @POST("/register")
    Call<String> daftarUser(@Part MultipartBody.Part file, @Part("data") RequestBody data);

    @POST("/login")
    Call<String> loginUser(@Body UserEntity user);

    @GET("/gender")
    Call<List<UserEntity>> searchCalonGender(@Header("Authorization") String token);

    @POST("/calon/save")
    Call<String> addCalon(@Body JodohEntity jodoh, @Header("Authorization") String token);

}
