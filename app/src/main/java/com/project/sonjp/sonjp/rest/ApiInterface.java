package com.project.sonjp.sonjp.rest;

import com.project.sonjp.sonjp.model.ServerResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiInterface {

    //?username=ereyesca&password=milena01
    //@GET("/login/dologin")
    //Call<ServerResponse> operation(@Body ServerRequest request);

    @GET("/rest3/login/{username}/{password}")
    Call<ServerResponse> operation(@Path("username") String username, @Path("password") String password);


}