package com.lf.sinosinovo.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitConfig {

    private final Retrofit retrofit;

    public RetrofitConfig() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.level(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.addInterceptor(interceptor);

        Gson gson = new GsonBuilder()
                .setDateFormat("dd/MM/yyyy HH:mm")
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.16:8080/rest/denuncias/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client.build())
                .build();
    }

    public DenunciaService getDenunciaService() {
        return retrofit.create(DenunciaService.class);
    }
}
