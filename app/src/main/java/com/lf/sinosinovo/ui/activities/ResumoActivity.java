package com.lf.sinosinovo.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.lf.sinosinovo.R;
import com.lf.sinosinovo.model.Denuncia;
import com.lf.sinosinovo.retrofit.RetrofitConfig;
import com.lf.sinosinovo.ui.adapters.ListaDenunciaAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResumoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resumo);

        setTitle("Confirme os dados");
        atribuirDenunciaResumo();
        configuraBotaoVoltar();
        configuraBotaoFinalizar();
    }

    private void configuraBotaoFinalizar() {

        Button botaoVoltar = findViewById(R.id.activity_resumo_botao_finalizar);
        botaoVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enviarDenuncia();
            }
        });
    }

    private void enviarDenuncia() {

        Denuncia denuncia = recuperarDenuncia();
        //denuncia.setDataDenuncia(null);
        Call call = new RetrofitConfig().getDenunciaService().enviarDenuncia(denuncia);

        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                Log.i("onResponse", "sucesso");
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Log.e("onFailure", t.getMessage());

            }
        });
    }

    private Denuncia recuperarDenuncia() {

        Intent intent = getIntent();
        Denuncia denuncia = (Denuncia) intent.getSerializableExtra("denuncia");
        System.out.println("Foto: " + denuncia.getCaminhoDaFoto());
        return denuncia;
    }

    private void configuraBotaoVoltar() {

        Button botaoVoltar = findViewById(R.id.activity_resumo_botao_voltar);
        botaoVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void atribuirDenunciaResumo() {

        Denuncia denuncia = recuperarDenuncia();

        ListView listaDeDenuncias = findViewById(R.id.activity_resumo_list_view_lista_denuncia);
        List<Denuncia> denuncias = new ArrayList<>();
        denuncias.add(denuncia);

        listaDeDenuncias.setAdapter(new ListaDenunciaAdapter(denuncias, this));
    }
}
