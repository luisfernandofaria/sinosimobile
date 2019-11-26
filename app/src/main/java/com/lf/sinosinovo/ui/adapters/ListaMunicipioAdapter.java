package com.lf.sinosinovo.ui.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.lf.sinosinovo.R;
import com.lf.sinosinovo.model.Municipio;

import java.util.List;

public class ListaMunicipioAdapter extends ArrayAdapter<Municipio> {

    private final List<Municipio> municipiosList;
    private LayoutInflater flater;

    public ListaMunicipioAdapter(Activity context, int resourceId, int textviewId, List<Municipio> municipiosList) {

        super(context, resourceId, textviewId, municipiosList);
        this.municipiosList = municipiosList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return gerarSpinnerView(convertView, position);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return gerarSpinnerView(convertView, position);
    }

    private View gerarSpinnerView(View convertView, int position) {

        Municipio municipioSelecionado = municipiosList.get(position);

        viewHolder holder;
        View municipioView = convertView;
        if (municipioView == null) {

            holder = new viewHolder();
            flater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            municipioView = flater.inflate(R.layout.spinner_layout, null, false);

            holder.txtTitle = municipioView.findViewById(R.id.spinner_modelo_texto);
            municipioView.setTag(holder);
        } else {
            holder = (viewHolder) municipioView.getTag();
        }
        holder.txtTitle.setText(municipioSelecionado.getUf() + " - " + municipioSelecionado.getNome());

        return municipioView;
    }

    private class viewHolder {
        TextView txtTitle;
    }
}
