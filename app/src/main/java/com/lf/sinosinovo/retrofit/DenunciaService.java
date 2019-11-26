package com.lf.sinosinovo.retrofit;

import com.lf.sinosinovo.model.Denuncia;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
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
    @POST("singleSave")
    Call<ResponseBody> enviarDenunciaComImagem(@Part("denuncia") Denuncia denuncia,
                                                    @Part MultipartBody.Part file);
}


