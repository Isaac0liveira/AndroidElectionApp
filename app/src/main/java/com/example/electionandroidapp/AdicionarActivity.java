package com.example.electionandroidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AdicionarActivity extends AppCompatActivity {

    CandidatoDAO dao;
    Candidato candidato;
    EditText nome, numero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar);
        nome = findViewById(R.id.txtNome);
        numero = findViewById(R.id.txtNumero);
    }

    public void adicionar(View view){
        dao = new CandidatoDAO(this);
        candidato = new Candidato();
        candidato.setNome(nome.getText().toString());
        candidato.setNumero(Integer.parseInt(numero.getText().toString()));
        long id = dao.adicionar(candidato);
        Toast.makeText(this, "Candidato adicionado com o ID: " + id, Toast.LENGTH_SHORT).show();
    }
}