package com.lf.sinosinovo.ui.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lf.sinosinovo.R;
import com.lf.sinosinovo.model.Denuncia;
import com.lf.sinosinovo.retrofit.RetrofitConfig;
import com.lf.sinosinovo.ui.adapters.ListaDenunciaAdapter;
import com.lf.sinosinovo.ui.adapters.ListaDenunciaAdapterNovo;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

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
                enviarDenuncia();
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
//        System.out.println("Foto na tela de resumo: " + denuncia.getCaminhoDaFoto());
//
//        String caminhoFoto = "/storage/emulated/0/Pictures/foto_sinosi15744797725588195568334378744374.jpg";
//
//        Bitmap bitmap = BitmapFactory.decodeFile(caminhoFoto);
//        Bitmap bitmapReduzido = Bitmap.createScaledBitmap(bitmap,300,300,true);
//        ImageView foto = new ImageView(this);
//        foto.setImageBitmap(bitmapReduzido);;
//        foto.setScaleType(ImageView.ScaleType.FIT_XY);
//        foto.setTag(caminhoFoto);

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
        listaDeDenuncias.setAdapter(new ListaDenunciaAdapterNovo(this, denuncias));
    }

}
