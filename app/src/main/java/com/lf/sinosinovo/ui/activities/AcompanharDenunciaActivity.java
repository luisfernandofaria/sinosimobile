package com.lf.sinosinovo.ui.activities;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.lf.sinosinovo.R;
import com.lf.sinosinovo.retrofit.RetrofitConfig;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AcompanharDenunciaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acompanhar_denuncia);

        buscarDenuncia();
    }

        private void buscarDenuncia() {

        Call call = new RetrofitConfig().getDenunciaService().buscarDenuncias();

        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                Log.i("onResponse", "sucesso ???");
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Log.i("onFailure", "requisição falhou");
                t.printStackTrace();
            }
        });
    }
}
