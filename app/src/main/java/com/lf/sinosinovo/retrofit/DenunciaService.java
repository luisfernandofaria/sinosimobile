package com.lf.sinosinovo.retrofit;

import com.lf.sinosinovo.model.Denuncia;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface DenunciaService {

    @GET("listar")
    Call<List<Denuncia>> buscarDenuncias();

    @POST("denuncia")
    Call<Void> enviarDenuncia(@Body Denuncia denuncia);


    @Multipart
    @POST("/images/upload")
    Call<Response> uploadImage(@Part MultipartBody.Part image);
}


