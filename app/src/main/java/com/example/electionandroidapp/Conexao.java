package com.example.electionandroidapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Conexao extends SQLiteOpenHelper {

    private static final String nome = "eleicao.db";
    private static final int version = 1;

    public Conexao(Context context) {
        super(context, nome, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table candidatos(id integer primary key autoincrement," +
                "nome varchar(50)," +
                "votos int," +
                "numero int)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
