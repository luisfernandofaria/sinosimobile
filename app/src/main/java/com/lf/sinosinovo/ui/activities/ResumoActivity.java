package com.lf.sinosinovo.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.lf.sinosinovo.R;
import com.lf.sinosinovo.model.Denuncia;
import com.lf.sinosinovo.retrofit.RetrofitConfig;
import com.lf.sinosinovo.ui.adapters.ListaDenunciaAdapter;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResumoActivity extends AppCompatActivity {

    private ProgressBar mProgressBar;
    private String mImageUrl = "";

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
                enviarDenunciaComImagem();
            }
        });
    }

    private void enviarDenunciaComImagem() {

        Denuncia denuncia = recuperarDenuncia();

        String str = denuncia.getCaminhoDaFoto();
        str = str.substring(5);

        File file = new File (str);

        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);

        MultipartBody.Part body =
                MultipartBody.Part.createFormData("file", file.getName(), requestFile);

        Call call = new RetrofitConfig().getDenunciaService().enviarDenunciaComImagem(denuncia, body);

        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                Log.i("onResponse", "sucesso");
                Toast.makeText(getApplicationContext(), "Denúncia enviada com sucesso!", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Log.e("onFailure", t.getMessage());
                Toast.makeText(getApplicationContext(), "Não foi possível enviar a denúncia. Por favor, verifique sua conexão.", Toast.LENGTH_LONG).show();
            }
        });
    }


    private void enviarDenuncia() {

        Denuncia denuncia = recuperarDenuncia();
        Call call = new RetrofitConfig().getDenunciaService().enviarDenuncia(denuncia);

        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                Log.i("onResponse", "sucesso");
                Toast.makeText(getApplicationContext(), "Denúncia enviada com sucesso!", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Log.e("onFailure", t.getMessage());
                Toast.makeText(getApplicationContext(), "Não foi possível enviar a denúncia. Por favor, verifique sua conexão.", Toast.LENGTH_LONG).show();
            }
        });
    }

    private Denuncia recuperarDenuncia() {

        Intent intent = getIntent();
        Denuncia denuncia = (Denuncia) intent.getSerializableExtra("denuncia");
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

        RecyclerView listaDeDenuncias = findViewById(R.id.activity_resumo_lista_denuncia_recycler_view);
        List<Denuncia> denuncias = new ArrayList<>();
        denuncias.add(denuncia);

        configurarAdapter(listaDeDenuncias, denuncias);
    }

    private void configurarAdapter(RecyclerView listaDeDenuncias, List<Denuncia> denuncias) {
        listaDeDenuncias.setAdapter(new ListaDenunciaAdapter(this, denuncias));
    }

}
