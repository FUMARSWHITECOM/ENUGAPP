package com.example.eungapp;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MyAPI {
    @FormUrlEncoded
    @POST("login/")
    Call<AuthenticateItem> login(@Field("email") String email,
                       @Field("password") String password);

    @FormUrlEncoded
    @POST("signup/")
    Call<AuthenticateItem> signup(@Field("username") String username,
                                  @Field("email") String email,
                                  @Field("password") String password);

    @FormUrlEncoded
    @POST("Sensor/")
    Call<SensorItem> sensor(@Field("serial_number") String serial_number,
                            @Field("location") String location,
                            @Field("user") int user,
                            @Field("status") Boolean status,
                            @Field("model") String model);

    @FormUrlEncoded
    @POST("check/")
    Call<CheckItem> check(@Field("serial_number") String serial_number,
                        @Field("timestamp") String timestamp);

    @GET("/CryDetection/")
    Call<List<CryDetectionItem>> get_cryDetections();

    @GET("/Sensor/")
    Call<List<SensorItem>> get_sensors();
}