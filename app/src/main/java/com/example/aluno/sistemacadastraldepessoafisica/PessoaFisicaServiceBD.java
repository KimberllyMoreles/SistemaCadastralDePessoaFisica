package com.example.aluno.sistemacadastraldepessoafisica;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by aluno on 14/12/16.
 */
public class PessoaFisicaServiceBD extends SQLiteOpenHelper {

    private static String TAG = "PessoaFisicaserviceBD";
    private static String NAME = "PessoaFisica.sqlite";
    private static int VERSION = 1;
    private static PessoaFisicaServiceBD PessoaFisicaServiceBD = null;

    private PessoaFisicaServiceBD(Context context) {
        super(context, NAME, null, VERSION);
        getWritableDatabase();
    }

    public static PessoaFisicaServiceBD getInstance(Context context){
        if (PessoaFisicaServiceBD == null){
            PessoaFisicaServiceBD = new PessoaFisicaServiceBD(context);
            return PessoaFisicaServiceBD;
        }
        return PessoaFisicaServiceBD;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "create table if not exists PessoaFisica" +
                "( _id integer primary key autoincrement, " +
                " nome text not null, " +
                "cpf text not null, " +
                "telefone text not null, " +
                "idade integer not null, " +
                "email text not null);";
        Log.d(TAG, "Criando a tabela PessoaFisica. Aguarde ...");
        sqLiteDatabase.execSQL(sql);
        Log.d(TAG, "Tabela PessoaFisica criada com sucesso.");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    /*
        CRUD
     */
    public List<PessoaFisica> getAll(){
        //abre a conexão com o bd
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        try {
            return toList(sqLiteDatabase.rawQuery("select * from PessoaFisica", null));
        }finally {
            sqLiteDatabase.close(); //libera o recurso
        }

    }
    

    public long save(PessoaFisica PessoaFisica){

        SQLiteDatabase db = getWritableDatabase(); //abre a conexão com o banco

        try{
            //tupla com: chave, valor
            ContentValues values = new ContentValues();
            values.put("nome", PessoaFisica.nome);
            values.put("cpf", PessoaFisica.cpf);
            values.put("telefone", PessoaFisica.telefone);
            values.put("idade", PessoaFisica.idade);
            values.put("email", PessoaFisica.email);

            //realiza a operação
            if(PessoaFisica._id == null){
                //insere no banco de dados
                return db.insert("PessoaFisica", null, values);
            }else{
                //altera no banco de dadoswrap_content
                values.put("_id", PessoaFisica._id);
                return db.update("PessoaFisica", values, "_id=" + PessoaFisica._id, null);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            db.close(); //libera o recurso
        }

        return 0L; //caso não realize a operação
    }

    public long delete(PessoaFisica PessoaFisica){
        SQLiteDatabase db = getWritableDatabase(); //abre a conexão com o banco
        try{
            return db.delete("PessoaFisica", "_id=?", new String[]{String.valueOf(PessoaFisica._id)});
        }
        finally {
            db.close(); //libera o recurso
        }
    }

    /*
        Utilitários
     */
    //converte de Cursor em uma List
    private List<PessoaFisica> toList(Cursor c) {
        List<PessoaFisica> PessoaFisicas = new ArrayList<>();

        if (c.moveToFirst()) {
            do {
                PessoaFisica PessoaFisica = new PessoaFisica();

                // recupera os atributos do cursor para o PessoaFisica
                PessoaFisica._id = c.getLong(c.getColumnIndex("_id"));
                PessoaFisica.nome = c.getString(c.getColumnIndex("nome"));
                PessoaFisica.cpf = c.getString(c.getColumnIndex("cpf"));
                PessoaFisica.telefone = c.getString(c.getColumnIndex("telefone"));
                PessoaFisica.idade = c.getInt(c.getColumnIndex("idade"));
                PessoaFisica.email = c.getString(c.getColumnIndex("email"));

                PessoaFisicas.add(PessoaFisica);

            } while (c.moveToNext());
        }

        return PessoaFisicas;
    }

    public PessoaFisica getByname(String nome){
        SQLiteDatabase db = getReadableDatabase();
        PessoaFisica PessoaFisica = new PessoaFisica();;
        try {
            //retorna uma List para os registros contidos no banco de dados
            // select * from carro
            Cursor c = db.rawQuery("SELECT  * FROM PessoaFisica where nome LIKE'" + nome + "'", null);

            if(c.moveToFirst()) {
                PessoaFisica.cpf = c.getString(2);
                PessoaFisica.telefone = c.getString(4);
                PessoaFisica.nome = c.getString(1);
                PessoaFisica._id = c.getLong(0);
                PessoaFisica.idade = c.getInt(3);
                PessoaFisica.email = c.getString(5);
            }
        } finally {
            db.close();
        }
        return PessoaFisica;
    }
}