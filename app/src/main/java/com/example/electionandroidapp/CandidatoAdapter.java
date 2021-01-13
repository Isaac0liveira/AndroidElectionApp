package com.example.electionandroidapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class CandidatoAdapter extends BaseAdapter {

    Activity activity;
    List<Candidato> candidatos;

    public CandidatoAdapter(Activity activity, List<Candidato> candidatos){
        super();
        this.activity = activity;
        this.candidatos = candidatos;
    }

    @Override
    public int getCount() {
        return candidatos.size();
    }

    @Override
    public Object getItem(int position) {
        return candidatos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return candidatos.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        @SuppressLint("ViewHolder") View view = activity.getLayoutInflater().inflate(R.layout.item, parent, false);
        TextView nome = view.findViewById(R.id.candidatoNome);
        TextView numero = view.findViewById(R.id.candidatoNumero);
        TextView votos = view.findViewById(R.id.candidatoVotos);
        Candidato candidato = candidatos.get(position);
        nome.setText(candidato.getNome());
        numero.setText(String.valueOf(candidato.getNumero()));
        votos.setText(String.valueOf(candidato.getVotos()));
        return view;
    }
}
