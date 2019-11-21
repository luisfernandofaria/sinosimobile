package com.lf.sinosinovo.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.lf.sinosinovo.R;
import com.lf.sinosinovo.model.Denuncia;
import com.lf.sinosinovo.model.LocalAcidente;
import com.lf.sinosinovo.model.Municipio;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class CategoriaLocalizacaoActivity extends AppCompatActivity {

    private ArrayAdapter spinnerAdapter;
    private List<String> listaCidades;
    private List<String> listaEstados;
    private List<String> listaCategorias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoria_localizacao);

        setTitle("Tipo e Localização");

        popularSpinner();
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

        EditText lat = findViewById(R.id.activity_cat_loc_latitude);
        local.setLatitude(lat.getText().toString());

        EditText lon = findViewById(R.id.activity_cat_loc_longitude);
        local.setLongitude(lon.getText().toString());

        EditText end = findViewById(R.id.activity_cat_loc_endereco);
        local.setEndereco(end.getText().toString());

        local.setMunicipio(pegarMunicipio());

        EditText cep = findViewById(R.id.activity_cat_loc_cep);
        local.setCep(cep.getText().toString());

        return local;

    }

    private Municipio pegarMunicipio() {

        Spinner spinnerMunicipio = findViewById(R.id.activity_cat_loc_spinner_cidade);
        spinnerAdapter.setDropDownViewResource(R.layout.spinner_layout);

        Municipio municipio = new Municipio();
        municipio.setNome(spinnerMunicipio.getSelectedItem().toString());
        municipio.setUf("GO");
        municipio.setCodigo(52);

        return municipio;
    }

    private void vaiParaDescricao(Denuncia denuncia) {

        Intent intent = new Intent(this, DescricaoActivity.class);
        intent.putExtra("denuncia", denuncia);
        startActivity(intent);
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

    private void popularSpinner() {

        preencherListas();

        configurarSpinner("spinnerCategoriaTag", listaCategorias);
        configurarSpinner("spinnerCidadeTag", listaCidades);
//        configurarSpinner("spinnerEstadoTag", listaEstados);
    }

    private Spinner configurarSpinner(String tag, List<String> itens) {
        View viewAtual = findViewById(R.id.viewCategoriaLocalizacao);
        spinnerAdapter = new ArrayAdapter(this, R.layout.spinner_layout, R.id.spinner_modelo_texto, itens);
        Spinner spinner = viewAtual.findViewWithTag(tag);
        spinner.setAdapter(spinnerAdapter);

        return spinner;
    }

    private void preencherListas() {

        listaCidades = retornarArrayListComXML("cidades.xml", "CIDADE", "NOME");
//        listaEstados = retornarArrayListComXML("estados.xml", "ESTADO", "NOME");
        listaCategorias = Arrays.asList(getResources().getStringArray(R.array.array_categorias));
    }

    private List<String> retornarArrayListComXML(String arquivo, String tag1, String tag2) {

        List<String> listaLocais = new ArrayList<>();
        try {
            InputStream is = getAssets().open(arquivo);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(is);

            Element element = doc.getDocumentElement();
            element.normalize();

            NodeList nList = doc.getElementsByTagName(tag1);

            for (int i = 0; i < nList.getLength(); i++) {
                Node node = nList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element2 = (Element) node;
                    listaLocais.add(getValue(tag2, element2) + "\n");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listaLocais;
    }

    private static String getValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = nodeList.item(0);
        return node.getNodeValue();
    }
}

