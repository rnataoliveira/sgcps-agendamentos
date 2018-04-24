package com.example.renataoliveira.sgcps_agendamentos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class AgendamentoDB extends SQLiteOpenHelper{
    public static final String NOME_BANCO = "Agendamentos_sqlite";
    public static final int VERSAO_BANCO = 1;

    public AgendamentoDB(Context contexto) {
        super(contexto, NOME_BANCO, null, VERSAO_BANCO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists agendamento (" +
                "_id integer primary key autoincrement, " +
                "nome text, data text, " +
                "hora text, local text, lembrar integer);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int versaoAntiga, int versaoNova) {
        if (versaoAntiga == 1 && versaoNova == 2) {
            db.execSQL("alter table cerveja add column latitude text;");
            db.execSQL("alter table cerveja add column longitude text;");
        }
    }

    public long save(Agendamento agendamento) {
        long id = agendamento.id;
        SQLiteDatabase db = getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put("nome", agendamento.nome);
            values.put("data", agendamento.data);
            values.put("hora", agendamento.hora);
            values.put("local", agendamento.local);
            values.put("lembrar", agendamento.lembrar);

            if (id != 0) {
                String _id = String.valueOf(id);
                String [] argsFiltro = new String[]{_id};
                // criar update agendamento set values = ... where _id = ?
                int count = db.update("agendamento", values, "_id=?", argsFiltro);
                return count;
            } else {
                // criar insert into agendamento values (...)
                id = db.insert("agendamento","", values);
                return id;
            }
        } finally {
            db.close();
        }
    }

    public int delete(Agendamento agendamento){
        SQLiteDatabase db = getWritableDatabase();
        try{
            // criar delete from agendamento where _id = ?
            int count = db.delete("agendamento", "_id=?", new String[]{String.valueOf(agendamento.id)});
            return count;
        } finally {
            db.close();
        }
    }

    //Retornar todos os agendamentos
    public List<Agendamento> findAll() {
        SQLiteDatabase db =  getWritableDatabase();
        try {
            // criar select * from cerveja
            Cursor c = db.query("agendamento", null, null, null, null, null, null, null);
            return  toList(c);
        } finally {
            db.close();
        }
    }

    private List<Agendamento> toList(Cursor c) {
        List<Agendamento> agendamentos = new ArrayList<Agendamento>();
        if (c.moveToFirst()) {
            do {
                Agendamento agendamento = new Agendamento();
                agendamentos.add(agendamento);
                agendamento.id = c.getLong(c.getColumnIndex("_id"));
                agendamento.nome = c.getString(c.getColumnIndex("nome"));
                agendamento.data = c.getString(c.getColumnIndex("data"));
                agendamento.hora = c.getString(c.getColumnIndex("hora"));
                agendamento.local = c.getString(c.getColumnIndex("local"));

            } while (c.moveToNext());
        }
        return agendamentos;
    }

    // executa um SQL qualquer
    public void execSQL(String sql) {
        SQLiteDatabase db = getWritableDatabase();
        try {
            db.execSQL(sql);
        }finally {
            db.close();
        }
    }

    // executa um SQL qualquer, com parâmetros
    public void execSQL(String sql, Object [] args) {
        SQLiteDatabase db = getWritableDatabase();
        try {
            db.execSQL(sql, args);
        }finally {
            db.close();
        }
    }
}
