package com.example.electionandroidapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity {

    List<Candidato> candidatos;
    List<Candidato> candidatosFilter = new ArrayList<>();
    CandidatoDAO dao;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.listView);
        dao = new CandidatoDAO(this);
        candidatos = dao.obterLista();
        candidatosFilter.addAll(candidatos);
        CandidatoAdapter adapter = new CandidatoAdapter(this, candidatosFilter);
        listView.setAdapter(adapter);
    }

    public void onResume() {
        super.onResume();
        candidatos = dao.obterLista();
        candidatosFilter.clear();
        candidatosFilter.addAll(candidatos);
        listView.invalidateViews();
    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_principal, menu);
        return true;
    }

    public void adicionar(MenuItem item){
        Intent intent = new Intent(this, AdicionarActivity.class);
        startActivity(intent);
    }

    public void votar(MenuItem item){
        Intent intent = new Intent(this, VotarActivity.class);
        startActivity(intent);
    }
}