package com.lf.sinosinovo.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lf.sinosinovo.R;
import com.lf.sinosinovo.model.Denuncia;

import java.text.SimpleDateFormat;
import java.util.List;

public class ListaDenunciaAdapter extends BaseAdapter {

    private final List<Denuncia> denuncias;
    private final Context context;

    public ListaDenunciaAdapter(List<Denuncia> denuncias, Context context) {
        this.denuncias = denuncias;
        this.context = context;
    }

    @Override
    public int getCount() {
        return denuncias.size();
    }

    @Override
    public Denuncia getItem(int position) {
        return denuncias.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = LayoutInflater.from(context).inflate(R.layout.lista_denuncia, parent, false);

        Denuncia denuncia = denuncias.get(position);

        pegarCategoria(view, denuncia);
        pegarLocal(view, denuncia);
        pegarAutor(view, denuncia);
        pegarDescricao(view, denuncia);
        pegarData(view, denuncia);

        return view;
    }

    private void pegarCategoria(View view, Denuncia denuncia) {
        TextView categoria = view.findViewById(R.id.lista_denuncia_categoria);
        categoria.setText(denuncia.getCategoria());
    }

    private void pegarLocal(View view, Denuncia denuncia) {
        TextView local = view.findViewById(R.id.lista_denuncia_localizacao);
        local.setText(denuncia.getLocalAcidente().getMunicipio().getNome());
    }

    private void pegarAutor(View view, Denuncia denuncia) {
        TextView autor = view.findViewById(R.id.lista_denuncia_autor);
        autor.setText(denuncia.getAutorDano());
    }

    private void pegarDescricao(View view, Denuncia denuncia) {
        TextView categoria = view.findViewById(R.id.lista_denuncia_descricao);
        categoria.setText(denuncia.getDescricao());
    }

    private void pegarData(View view, Denuncia denuncia) {
        TextView data = view.findViewById(R.id.lista_denuncia_data);
        SimpleDateFormat dataFormatada = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        data.setText(dataFormatada.format(denuncia.getDataDenuncia()));
    }
}
