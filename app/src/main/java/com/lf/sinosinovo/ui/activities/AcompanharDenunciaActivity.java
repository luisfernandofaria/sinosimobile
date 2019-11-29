package com.lf.sinosinovo.ui.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.lf.sinosinovo.R;
import com.lf.sinosinovo.model.Denuncia;
import com.lf.sinosinovo.retrofit.RetrofitConfig;
import com.lf.sinosinovo.ui.adapters.ListaDenunciaAdapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AcompanharDenunciaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acompanhar_denuncia);

        configuraBotaoVoltar();
        buscarDenuncias();
    }

    private void configuraBotaoVoltar() {

        Button botaoVoltar = findViewById(R.id.activity_acomp_denuncia_botao_voltar);
        botaoVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void buscarDenuncias() {

        Call<List<Denuncia>> call = new RetrofitConfig().getDenunciaService().buscarDenuncias();

        call.enqueue(new Callback<List<Denuncia>>() {
            @Override
            public void onResponse(Call<List<Denuncia>> call, Response<List<Denuncia>> response) {
                Log.i("onResponse", "sucesso ???");
                atribuirDenuncias(response.body());
            }

            @Override
            public void onFailure(Call<List<Denuncia>> call, Throwable t) {
                Log.i("onFailure", "requisição falhou");
                t.printStackTrace();
            }
        });

    }

    private void atribuirDenuncias(List<Denuncia> lista) {

        RecyclerView listaDeDenuncias = findViewById(R.id.activity_acomp_denuncia_recycler_view);
        List<Denuncia> denuncias = lista;

        configurarAdapter(listaDeDenuncias, denuncias);
    }

    private void configurarAdapter(RecyclerView listaDeDenuncias, List<Denuncia> denuncias) {
        listaDeDenuncias.setAdapter(new ListaDenunciaAdapter(this, denuncias));
    }

}
