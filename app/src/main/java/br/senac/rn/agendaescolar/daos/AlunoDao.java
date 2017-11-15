package br.senac.rn.agendaescolar.daos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AlunoDao extends SQLiteOpenHelper {

    public AlunoDao(Context context) {
        super(context, "Agenda", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "";
        sql += "CREATE TABLE Alunos(";
        sql += "id INTEGER PRIMARY KEY, ";
        sql += "nome TEXT, ";
        sql += "endereco TEXT, ";
        sql += "fone TEXT, ";
        sql += "site TEXT, ";
        sql += "nota REAL";
        sql += ");";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

}
