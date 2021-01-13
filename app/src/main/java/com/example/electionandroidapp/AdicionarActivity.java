package com.example.electionandroidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;
import java.util.concurrent.TimeUnit;

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
        Intent intent = getIntent();
        if(intent.hasExtra("candidatoAtualizar")){
            candidato = (Candidato) intent.getSerializableExtra("candidatoAtualizar");
            nome.setText(candidato.getNome());
            numero.setText(String.valueOf(candidato.getNumero()));
        }
    }

    public void adicionar(View view){
        if(nome.getText().toString().equals("")){
            nome.setHint("Digite um nome válido!");
            return;
        }
        if(numero.getText().toString().equals("")){
            numero.setHint("Digite um número válido!");
            return;
        }
        dao = new CandidatoDAO(this);
        List<Candidato> lista = dao.obterLista();
        int error = 0;
        for (Candidato c : lista){
            if(nome.getText().toString().equals(c.getNome())){
                error = 1;
            }
            if(Integer.parseInt(numero.getText().toString()) == c.getNumero()){
                if(error == 0){
                    error = 2;
                }else{
                    error = 3;
                }
            }
        }
        if(error == 1){
            nome.setText("");
            nome.setHint("Este nome já existe!");
            return;
        }else if(error == 2){
            numero.setText("");
            numero.setHint("Número pertence a um candidato existente!");
            return;
        }else if(error == 3){
            nome.setText("");
            nome.setHint("Este nome já existe!");
            numero.setText("");
            numero.setHint("Número pertence a um candidato existente!");
            return;
        }else if(error == 0) {
            Intent intent = new Intent(this, MainActivity.class);
            if (candidato == null) {

                candidato = new Candidato();
                candidato.setNome(nome.getText().toString());
                candidato.setNumero(Integer.parseInt(numero.getText().toString()));
                long id = dao.adicionar(candidato);
                Toast.makeText(this, "Candidato adicionado com o ID: " + id, Toast.LENGTH_SHORT).show();
                SystemClock.sleep(1000);
                startActivity(intent);
            } else {
                candidato.setNome(nome.getText().toString());
                candidato.setNumero(Integer.parseInt(numero.getText().toString()));
                dao.atualizar(candidato);
                Toast.makeText(this, "Candidato atualizado com sucesso!", Toast.LENGTH_SHORT).show();
                SystemClock.sleep(1000);
                startActivity(intent);
            }
        }
    }
}