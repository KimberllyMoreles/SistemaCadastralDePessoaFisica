package com.example.aluno.sistemacadastraldepessoafisica;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class InserirPessoaActivity extends AppCompatActivity {

    PessoaFisicaServiceBD pessoaFisicaServiceBD;
    private static PessoaFisica pessoaFisica = null;

    private EditText etNome;
    private EditText etCpf;
    private EditText etIdade;
    private EditText etTelefone;
    private EditText etEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inserir_pessoa);

        //obtém a instância do objeto de acesso ao banco de dados
        pessoaFisicaServiceBD = pessoaFisicaServiceBD.getInstance(this);
        //constrói uma instância da classe de modelo
        pessoaFisica = new PessoaFisica();

        //mapeia os componentes de UI
        etNome = (EditText) findViewById(R.id.editText_Nome);
        etCpf = (EditText) findViewById(R.id.editText_Cpf);
        etIdade = (EditText) findViewById(R.id.editText_Idade);
        etTelefone = (EditText) findViewById(R.id.editText_Telefone);
        etEmail = (EditText) findViewById(R.id.editText_Email);
        etNome.requestFocus();
    }
    private void limparFormulario(){
        etNome.setText(null);
        etCpf.setText(null);
        etIdade.setText(null);
        etTelefone.setText(null);
        etEmail.setText(null);
        etNome.requestFocus();
        pessoaFisica = new PessoaFisica(); //apaga dados antigos
    }

    public void salvar(View view){
        if(!etNome.getText().toString().isEmpty() &&
                !etCpf.getText().toString().isEmpty() &&
                !etTelefone.getText().toString().isEmpty() &&
                !etIdade.getText().toString().isEmpty() &&
                !etEmail.getText().toString().isEmpty()
                ) {
            if(pessoaFisica._id == null){ //se é uma inclusão
                pessoaFisica = new PessoaFisica(); //apaga dados antigos
            }
            pessoaFisica.nome = etNome.getText().toString();
            pessoaFisica.cpf = etCpf.getText().toString();
            pessoaFisica.idade = Integer.parseInt(etIdade.getText().toString());
            pessoaFisica.telefone = etTelefone.getText().toString();
            pessoaFisica.email = etEmail.getText().toString();

            Log.d("Status", "Contato que será salvo: " + pessoaFisica.toString());
            pessoaFisicaServiceBD.save(pessoaFisica);
            limparFormulario();
            Toast.makeText(InserirPessoaActivity.this, "Salvo com sucesso", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(InserirPessoaActivity.this, "Preencha todos os campos.", Toast.LENGTH_SHORT).show();
        }
    }

    public void buscarPorNome(View view){
        pessoaFisica = new PessoaFisica();

        pessoaFisica = pessoaFisicaServiceBD.getByname(String.valueOf(etNome.getText()));
        etCpf.setText(pessoaFisica.cpf);
        etIdade.setText(String.valueOf(pessoaFisica.idade));
        etTelefone.setText(pessoaFisica.telefone);
        etEmail.setText(pessoaFisica.email);

    }

    public void excluir(View v){

        if(pessoaFisica._id != null && pessoaFisica._id > 0) {

            pessoaFisicaServiceBD.delete(pessoaFisica);
            limparFormulario();
            Toast.makeText(InserirPessoaActivity.this, "Excluído com sucesso", Toast.LENGTH_SHORT).show();
        }
        else {

            Toast.makeText(InserirPessoaActivity.this, "Nunhuma pessoa selecionada", Toast.LENGTH_SHORT).show();
        }

    }

    public void listar(View v){
        Intent intent = new Intent(InserirPessoaActivity.this, ListaActivity.class);
        startActivity(intent);
    }
}
