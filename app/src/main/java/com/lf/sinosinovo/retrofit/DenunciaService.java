package com.lf.sinosinovo.retrofit;

import com.lf.sinosinovo.model.Denuncia;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface DenunciaService {

    @GET("listar")
    Call<List<Denuncia>> buscarDenuncias();

    @POST("denuncia")
    Call<Void> enviarDenuncia(@Body Denuncia denuncia);


}
