package com.alonsorios.myapplication.retrofit;

import com.alonsorios.myapplication.common.Constantes;

import java.util.concurrent.TimeUnit;


import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GlucoAppClient {

    private static GlucoAppClient instace = null;
    private  GlucoAppService glucoAppService;
    private Retrofit retrofit;


    public GlucoAppClient(){

        OkHttpClient okHttpClientBuilder = new OkHttpClient.Builder()
                .connectTimeout(1,TimeUnit.MINUTES)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(Constantes.API_GLUCOAPP_BASE_URL)
                .client(okHttpClientBuilder)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        glucoAppService = retrofit.create(GlucoAppService.class);
    }

    //Patron singleton
    public  static  GlucoAppClient getInstace(){
        if(instace == null){
            instace = new GlucoAppClient();
        }
        return instace;
    }

    public GlucoAppService getGlucoAppService(){
        return glucoAppService;
    }

}
