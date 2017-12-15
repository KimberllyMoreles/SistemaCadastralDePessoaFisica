package com.example.aluno.sistemacadastraldepessoafisica;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class ListaActivity extends AppCompatActivity {
    PessoaFisicaServiceBD pessoaFisicaServiceBD;

    ListView lvLista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);

        //obtém a instância do objeto de acesso ao banco de dados
        pessoaFisicaServiceBD = PessoaFisicaServiceBD.getInstance(this);
        //constrói uma instância da classe de modelo

        List<PessoaFisica> pessoaFisicas = pessoaFisicaServiceBD.getAll();



        lvLista = (ListView) findViewById(R.id.lvLista);

        ArrayAdapter<PessoaFisica> adapter = new ArrayAdapter<PessoaFisica>(this,
                android.R.layout.simple_list_item_1, pessoaFisicas);

        lvLista.setAdapter(adapter);
    }
}
