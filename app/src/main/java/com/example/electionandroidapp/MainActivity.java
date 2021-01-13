package com.example.electionandroidapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
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
        Collections.sort(candidatos, new Comparator<Candidato>() {
            @Override
            public int compare(Candidato o1, Candidato o2) {
                int retorno;
                if(o1.getVotos() > o2.getVotos()) {
                    retorno = -1;
                }else{
                    retorno = 1;
                }
                return retorno;
            }

        });
        candidatosFilter.addAll(candidatos);
        CandidatoAdapter adapter = new CandidatoAdapter(this, candidatosFilter);
        listView.setAdapter(adapter);
        registerForContextMenu(listView);
    }

    public void onResume() {
        super.onResume();
        candidatos = dao.obterLista();
        Collections.sort(candidatos, new Comparator<Candidato>() {
            @Override
            public int compare(Candidato o1, Candidato o2) {
                int retorno;
                if(o1.getVotos() > o2.getVotos()) {
                    retorno = -1;
                }else{
                    retorno = 1;
                }
                return retorno;
            }

        });
        candidatosFilter.clear();
        candidatosFilter.addAll(candidatos);
        listView.invalidateViews();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
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

    public void atualizar(MenuItem item){
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Candidato candidatoAtualizar = candidatos.get(menuInfo.position);
        Intent intent = new Intent(this, AdicionarActivity.class).putExtra("candidatoAtualizar", candidatoAtualizar);
        startActivity(intent);
    }

    public void excluir(MenuItem item){
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        AlertDialog alert = new AlertDialog.Builder(this).setTitle("Atenção!").setMessage("Deseja mesmo excluir este candidato?").setNegativeButton("Não", null)
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dao.excluir(candidatos.get(menuInfo.position));
                        candidatos = dao.obterLista();
                        Collections.sort(candidatos, new Comparator<Candidato>() {
                            @Override
                            public int compare(Candidato o1, Candidato o2) {
                                int retorno;
                                if(o1.getVotos() > o2.getVotos()) {
                                    retorno = -1;
                                }else{
                                    retorno = 1;
                                }
                                return retorno;
                            }

                        });
                        candidatosFilter.clear();
                        candidatosFilter.addAll(candidatos);
                        listView.invalidateViews();
                    }
                }).create();
        alert.show();

    }
}