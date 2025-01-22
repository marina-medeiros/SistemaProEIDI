package br.ufrn.imd.sistemaproeidi.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

public class BancoDAO implements Serializable {
    private static BancoDAO instance;
    private ArrayList<Pessoa> pessoas;
    private ArrayList<Turma> turmas;

    private BancoDAO() {
        pessoas = new ArrayList<>();
        turmas = new ArrayList<>();
    }

    public static BancoDAO getInstance(){
        if(instance == null){
            instance = new BancoDAO();
        }
        return instance;
    }

    public String gerarCodigoTurma(String cursoNome, LocalDate dataInicio) {
        int sequencial = turmas.size() + 1; // Número sequencial baseado no tamanho da lista de turmas
        String dataFormatada = dataInicio.toString().replace("-", ""); // AAAAMMDD
        return cursoNome.toUpperCase() + "-" + dataFormatada + "-" + String.format("%03d", sequencial);
    }

    public ArrayList<Pessoa> getArrayPessoas() {
        return pessoas;
    }
    public ArrayList<Turma> getArrayTurmas() {
        return turmas;
    }
}
