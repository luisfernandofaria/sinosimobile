package com.lf.sinosinovo.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.lf.sinosinovo.R;

public class MainActivity extends AppCompatActivity {

    public static final String TITULO_APPBAR = "Sinosi";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle(TITULO_APPBAR);
        configurarBotaoAcompanhar();
        configurarBotaoDenunciar();
    }

    private void configurarBotaoAcompanhar() {
        Button botaoAcompanhar = findViewById(R.id.activity_main_botao_acompanhar_denuncia);
        botaoAcompanhar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                definirIntent(AcompanharDenunciaActivity.class);
            }
        });
    }

    private void configurarBotaoDenunciar() {

        Button botaoDenunciar = findViewById(R.id.activity_main_botao_denunciar);
        botaoDenunciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                definirIntent(CategoriaLocalizacaoActivity.class);
            }
        });
    }

    private void definirIntent(Class classe) {
        Intent intent = new Intent(this, classe);
        startActivity(intent);
    }


}
