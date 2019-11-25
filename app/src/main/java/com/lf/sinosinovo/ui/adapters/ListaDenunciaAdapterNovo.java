package com.lf.sinosinovo.ui.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lf.sinosinovo.R;
import com.lf.sinosinovo.model.Denuncia;

import java.text.SimpleDateFormat;
import java.util.List;

public class ListaDenunciaAdapterNovo extends RecyclerView.Adapter<ListaDenunciaAdapterNovo.DenunciaViewHolder> {

    private final List<Denuncia> denuncias;
    private final Context context;

    public ListaDenunciaAdapterNovo(Context context, List<Denuncia> denuncias) {
        this.context = context;
        this.denuncias = denuncias;
    }

    @Override
    public ListaDenunciaAdapterNovo.DenunciaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.lista_denuncia, parent, false);
        return new DenunciaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListaDenunciaAdapterNovo.DenunciaViewHolder holder, int position) {
        Denuncia denuncia = denuncias.get(position);
        holder.vincularDenuncia(denuncia);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return denuncias.size();
    }

    class DenunciaViewHolder extends RecyclerView.ViewHolder {

        private final TextView categoria;
        private final TextView localDoAcidente;
        private final TextView autorDano;
        private final TextView descricao;
        private final TextView data;

        public DenunciaViewHolder(View itemView) {
            super(itemView);

            categoria = itemView.findViewById(R.id.lista_denuncia_categoria);
            localDoAcidente = itemView.findViewById(R.id.lista_denuncia_localizacao);
            autorDano = itemView.findViewById(R.id.lista_denuncia_autor);
            descricao = itemView.findViewById(R.id.lista_denuncia_descricao);
            data = itemView.findViewById(R.id.lista_denuncia_data);
        }

        private void vincularDenuncia(Denuncia denuncia) {

            categoria.setText(denuncia.getCategoria());
            localDoAcidente.setText(denuncia.getLocalAcidente().getMunicipio().getNome());
            autorDano.setText(denuncia.getAutorDano());
            descricao.setText(denuncia.getDescricao());

            SimpleDateFormat dataFormatada = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            data.setText(dataFormatada.format(denuncia.getDataDenuncia()));
        }

    }

}
