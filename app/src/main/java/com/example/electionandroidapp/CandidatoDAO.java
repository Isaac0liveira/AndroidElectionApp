package com.example.electionandroidapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class CandidatoDAO {

    private SQLiteDatabase banco;
    private Conexao conexao;

    public CandidatoDAO(Context context){
        conexao = new Conexao(context);
        banco = conexao.getWritableDatabase();
    }

    public List<Candidato> obterLista(){
        List<Candidato> candidatos = new ArrayList<>();
        Cursor cursor = banco.query("candidatos", new String[]{"id", "nome", "numero", "votos"}, null, null, null, null, null);
        while(cursor.moveToNext()){
            Candidato candidato = new Candidato();
            candidato.setId(cursor.getInt(0));
            candidato.setNome(cursor.getString(1));
            candidato.setNumero(cursor.getInt(2));
            candidato.setVotos(cursor.getInt(3));
            candidatos.add(candidato);
        }
        return candidatos;
    }

    public long adicionar(Candidato candidato){
        ContentValues values = new ContentValues();
        values.put("nome", candidato.getNome());
        values.put("numero", candidato.getNumero());
        values.put("votos", 0);
        return banco.insert("candidatos", null, values);
    }
}
