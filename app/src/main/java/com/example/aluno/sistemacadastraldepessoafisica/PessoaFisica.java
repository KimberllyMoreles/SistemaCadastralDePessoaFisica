package com.example.aluno.sistemacadastraldepessoafisica;

import java.io.Serializable;

/**
 * Created by aluno on 14/12/17.
 */

public class PessoaFisica  implements Serializable {
    public Long _id;
    public String nome;
    public String cpf;
    public int idade;
    public String telefone;
    public String email;

    @Override
    public String toString() {
        return nome;
    }
}
