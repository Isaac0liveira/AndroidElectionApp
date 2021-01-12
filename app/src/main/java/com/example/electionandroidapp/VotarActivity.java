package com.example.electionandroidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.SystemClock;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class VotarActivity extends AppCompatActivity {

    TextView nome;
    EditText numero;
    CandidatoDAO dao;
    List<Candidato> candidatos;
    Candidato candidatoSelecionado;
    List<Candidato> candidatosFilter = new ArrayList<>();
    boolean search = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_votar);
        nome = findViewById(R.id.votoNome);
        numero = findViewById(R.id.votoNumero);
        dao = new CandidatoDAO(this);
        candidatos = dao.obterLista();
        candidatosFilter.addAll(candidatos);
        numero.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                for(Candidato c : candidatos){
                    if(Integer.parseInt(String.valueOf(s)) == c.getNumero() ){
                        candidatoSelecionado = c;
                        nome.setText(c.getNome());
                        break;
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}