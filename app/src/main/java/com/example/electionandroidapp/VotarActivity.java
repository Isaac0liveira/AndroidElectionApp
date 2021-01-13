package com.example.electionandroidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class VotarActivity extends AppCompatActivity {

    TextView nome;
    EditText numero;
    Button confirma;
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
        confirma = findViewById(R.id.votoBotao);
        dao = new CandidatoDAO(this);
        candidatos = dao.obterLista();
        candidatosFilter.addAll(candidatos);
        if(!search) {
            numero.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }


                @Override
                public void afterTextChanged(Editable s) {
                    if(String.valueOf(s).length() < 5){
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    if(String.valueOf(s).length() != 0) {
                                        search = true;
                                        procurar(Integer.parseInt(String.valueOf(s)));
                                    }else{
                                        return;
                                    }
                                }
                            }, 1800);

                    }else{
                        numero.setText("");
                        numero.setHint("Inválido!");
                    }
                }
            });
        }
    }

    public void procurar(Integer s){
        if(search) {
            for (Candidato c : candidatos) {
                if (s == c.getNumero()) {
                    candidatoSelecionado = c;
                    nome.setText(c.getNome());
                    confirma.setVisibility(View.VISIBLE);
                    break;
                } else {
                    candidatoSelecionado = null;
                    nome.setText("Candidato não encontrado.");
                    confirma.setVisibility(View.INVISIBLE);
                }
            }
            search = false;
        }
    }

    public void votar(View view){
        dao.votar(candidatoSelecionado);
        Toast.makeText(this, "Voto computado com sucesso!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}