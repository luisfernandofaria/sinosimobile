package com.lf.sinosinovo.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.lf.sinosinovo.R;
import com.lf.sinosinovo.model.Denuncia;
import com.lf.sinosinovo.model.LocalAcidente;
import com.lf.sinosinovo.model.Municipio;
import com.lf.sinosinovo.retrofit.RetrofitConfig;
import com.lf.sinosinovo.ui.adapters.ListaMunicipioAdapter;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoriaLocalizacaoActivity extends AppCompatActivity {

    private ArrayAdapter spinnerAdapter;
    private List<String> listaCategorias;
    private List<Municipio> listaDeMunicipios;

    EditText end;
    EditText cep;
    EditText lat;
    EditText lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoria_localizacao);

        setTitle("Tipo e Localização");

        popularSpinnerCategoria();
        buscarMunicipios();
        configurarBotaoAvancar();
        configuraBotaoVoltar();
    }

    private Denuncia criarDenuncia() {

        Denuncia denuncia = new Denuncia(pegarLocalizacaoAcidente(), new Date(), pegarAutorDano(), pegarCategoria());
        return denuncia;
    }

    private String pegarAutorDano() {

        EditText autorDano = findViewById(R.id.activity_cat_loc_autor_dano);
        return autorDano.getText().toString();
    }

    private String pegarCategoria() {
        Spinner spinnerCategoria;

        spinnerCategoria = findViewById(R.id.activity_cat_loc_spinner_categorias);
        spinnerAdapter.setDropDownViewResource(R.layout.spinner_layout);
        return spinnerCategoria.getSelectedItem().toString();
    }

    private LocalAcidente pegarLocalizacaoAcidente() {

        LocalAcidente local = new LocalAcidente();

        lat = findViewById(R.id.activity_cat_loc_latitude);
        local.setLatitude(lat.getText().toString());

        EditText lng = findViewById(R.id.activity_cat_loc_longitude);
        local.setLongitude(lng.getText().toString());

        end = findViewById(R.id.activity_cat_loc_endereco);
        local.setEndereco(end.getText().toString());

        local.setMunicipio(pegarMunicipio());

        EditText cep = findViewById(R.id.activity_cat_loc_cep);
        if (isCEP(cep)) {
            local.setCep(cep.getText().toString());
        }
        return local;
    }

    private void buscarMunicipios() {

        Call<List<Municipio>> call = new RetrofitConfig().getDenunciaService().buscarMunicipios();

        call.enqueue(new Callback<List<Municipio>>() {
            @Override
            public void onResponse(Call<List<Municipio>> call, Response<List<Municipio>> response) {
                Log.i("onResponse", "sucesso ???");
                popularSpinnerMunicipio(response.body());
            }

            @Override
            public void onFailure(Call<List<Municipio>> call, Throwable t) {
                Log.i("onFailure", "requisição falhou");
                t.printStackTrace();
            }
        });
    }

    private void popularSpinnerMunicipio(List<Municipio> lista) {

        Collections.sort(lista, new Comparator<Municipio>() {

            public int compare(Municipio m1, Municipio m2) {

                String uf1 = m1.getUf();
                String uf2 = m2.getUf();
                int ufComp = uf1.compareTo(uf2);

                if (ufComp != 0) {
                    return ufComp;
                }
                String nome1 = m1.getNome();
                String nome2 = m2.getNome();
                return nome1.compareTo(nome2);
            }
        });


        listaDeMunicipios = lista;

        Spinner spinner = findViewById(R.id.activity_cat_loc_spinner_cidade);
        ListaMunicipioAdapter adapter = new ListaMunicipioAdapter(this,
                R.layout.spinner_layout, R.id.spinner_modelo_texto, listaDeMunicipios);
        spinner.setAdapter(adapter);
    }

    private Municipio pegarMunicipio() {

        Spinner spinnerMunicipio = findViewById(R.id.activity_cat_loc_spinner_cidade);
        spinnerAdapter.setDropDownViewResource(R.layout.spinner_layout);
        Municipio municipio = (Municipio) spinnerMunicipio.getSelectedItem();
        return municipio;
    }

    private void vaiParaDescricao(Denuncia denuncia) {

        if (validarCampos()) {
            Intent intent = new Intent(this, DescricaoActivity.class);
            intent.putExtra("denuncia", denuncia);
            startActivity(intent);
        }
    }

    private void configurarBotaoAvancar() {

        Button botaoAvancar = findViewById(R.id.activity_cat_loc_botao_avancar);
        botaoAvancar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vaiParaDescricao(criarDenuncia());
            }
        });
    }

    private void configuraBotaoVoltar() {

        Button botaoVoltar = findViewById(R.id.activity_cat_loc_botao_voltar);
        botaoVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void popularSpinnerCategoria() {
        preencherListas();
        configurarSpinner("spinnerCategoriaTag", listaCategorias);
    }

    private Spinner configurarSpinner(String tag, List<String> itens) {
        View viewAtual = findViewById(R.id.viewCategoriaLocalizacao);
        spinnerAdapter = new ArrayAdapter(this, R.layout.spinner_layout, R.id.spinner_modelo_texto, itens);
        Spinner spinner = viewAtual.findViewWithTag(tag);
        spinner.setAdapter(spinnerAdapter);

        return spinner;
    }

    private void preencherListas() {
        listaCategorias = Arrays.asList(getResources().getStringArray(R.array.array_categorias));
    }

    private boolean validarCampos() {

        if (isEmpty(end)) {
            end.setError("Preencha pelo menos um nome de rua ou referência!");
            end.requestFocus();
            return false;
        }

        if (cep != null && !isCEP(cep)) {
            cep.setError("Verifique o formato do CEP!");
            cep.requestFocus();
            return false;
        }

        if (lat != null && !isLatitudeValid(lat)) {
            lat.setError("Verifique a latitude! (Graus e decimais do grau (DDDºDDDDD))");
            lat.requestFocus();
            return false;
        }

        if (lng != null && !isLongitudeValid(lng)) {
            lng.setError("Verifique a longitude! (Graus e decimais do grau (DDDºDDDDD))");
            lng.requestFocus();
            return false;
        }

        return true;
    }

    public boolean isEmpty(EditText editText) {
        String input = editText.getText().toString().trim();
        return input.length() == 0;
    }

    public boolean isLatitudeValid(EditText editText) {
        if (!isEmpty(editText)) {
            if (!editText.getText().toString().matches("^(\\+|-)?(?:90(?:(?:\\.0{1,6})?)|(?:[0-9]|[1-8][0-9])(?:(?:\\.[0-9]{1,6})?))$")) {
                editText.setError("Verifique a latitude e/ou longitude!");
                editText.requestFocus();
            }
        }
        return true;
    }

    public boolean isLongitudeValid(EditText editText) {
        if (!isEmpty(editText)) {
            if (!editText.getText().toString().matches("^(\\\\+|-)?(?:180(?:(?:\\\\.0{1,6})?)|(?:[0-9]|[1-9][0-9]|1[0-7][0-9])(?:(?:\\\\.[0-9]{1,6})?))$")) {
                editText.setError("Verifique a latitude e/ou longitude!");
                editText.requestFocus();
            }
        }
        return true;
    }

    public boolean isCEP(EditText editText) {
        if (!isEmpty(editText)) {
            if (!editText.getText().toString().matches("^(([0-9]{2}\\\\.[0-9]{3}-[0-9]{3})|([0-9]{2}[0-9]{3}-[0-9]{3})|([0-9]{8}))$")) {
                editText.setError("Verifique o CEP!");
                editText.requestFocus();
            }
        }
        return true;
    }
}