package com.example.electionandroidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        procurar(Integer.parseInt(String.valueOf(s)));
                    }
                }, 5000);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void procurar(Integer s){
        for(Candidato c : candidatos){
            if(s == c.getNumero() ){
                candidatoSelecionado = c;
                nome.setText(c.getNome());
                break;
            }
        }
    }
}