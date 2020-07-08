package com.example.listadetarefas.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.listadetarefas.model.Tarefa;

import java.util.ArrayList;
import java.util.List;

public class TarefaDAO implements ITarefaDAO {

    private SQLiteDatabase escrever;
    private SQLiteDatabase ler;

    public TarefaDAO(Context context) {
        DBHelper db = new DBHelper(context);
        escrever = db.getWritableDatabase();
        ler = db.getReadableDatabase();
    }

    @Override
    public boolean salvar(Tarefa tarefa) {

        ContentValues cv = new ContentValues();
        cv.put("nome", tarefa.getNomeTarefa());

        try{
            escrever.insert(DBHelper.TABELA_TAREFAS,null, cv);
            Log.e("INFO", "Tarefa salva com sucesso");
        }catch (Exception e){
            Log.e("INFO", "Erro ao salvar tarefa " + e.getMessage());
            return false;
        }

        return true;
    }

    @Override
    public boolean atuallizar(Tarefa tarefa) {

        ContentValues cv = new ContentValues();
        cv.put("nome", tarefa.getNomeTarefa());

        try{
            String[] args = {tarefa.getId().toString()};
            escrever.update(DBHelper.TABELA_TAREFAS, cv, "id=?", args);
            Log.e("INFO", "Tarefa atualizada com sucesso");
        }catch (Exception e){
            Log.e("INFO", "Erro ao atuaizar tarefa " + e.getMessage());
            return false;
        }

        return true;
    }

    @Override
    public boolean deletar(Tarefa tarefa) {

        String[] args = {tarefa.getId().toString()};

        try{
            escrever.delete(DBHelper.TABELA_TAREFAS, "id=?", args);
            Log.e("INFO", "Tarefa removida com sucesso");
        }catch (Exception e){
            Log.e("INFO", "Erro ao remover tarefa " + e.getMessage());
            return false;
        }

        return true;
    }

    @Override
    public List<Tarefa> listar() {

        List<Tarefa> tarefas = new ArrayList<>();

        String sql = "SELECT * FROM " + DBHelper.TABELA_TAREFAS + ";";
        Cursor c = ler.rawQuery(sql, null);

        while( c.moveToNext()){
            Tarefa tarefa = new Tarefa();

           Long id = c.getLong(c.getColumnIndex("id"));
            String nomeTarefa = c.getString(c.getColumnIndex("nome"));

            tarefa.setId(id);
            tarefa.setNomeTarefa(nomeTarefa);

            tarefas.add(tarefa);

        }

        return tarefas;
    }
}
