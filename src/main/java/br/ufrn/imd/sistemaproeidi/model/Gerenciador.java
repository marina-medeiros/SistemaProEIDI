package br.ufrn.imd.sistemaproeidi.model;

import br.ufrn.imd.sistemaproeidi.model.enums.*;
import java.util.logging.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Gerenciador {

    private static BancoDAO banco = BancoDAO.getInstance();
    private static final String ARQUIVO_BINARIO = "banco.bin";
    private static final Scanner scanner = new Scanner(System.in);
    private static final Logger logger = Logger.getLogger(Gerenciador.class.getName());

    public static void salvarBinario() {
        ArrayList<Pessoa> pessoas = banco.getArrayPessoas();
        ArrayList<Turma> turmas = banco.getArrayTurmas();

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARQUIVO_BINARIO))) {
            // Salvando os arrays de pessoas e turmas
            oos.writeObject(pessoas);
            oos.writeObject(turmas);
             logger.info("Dados salvos com sucesso no arquivo binário!");
        } catch (IOException e) {
             logger.info("Erro ao salvar os dados: " + e.getMessage());
        }
    }
    @SuppressWarnings("unchecked")
    public static void carregarBinario() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ARQUIVO_BINARIO))) {
            // Carregando os arrays de pessoas e turmas
            ArrayList<Pessoa> pessoas = (ArrayList<Pessoa>) ois.readObject();
            ArrayList<Turma> turmas = (ArrayList<Turma>) ois.readObject();

            // Atualizando os arrays no BancoDAO
            banco.getArrayPessoas().clear();
            banco.getArrayPessoas().addAll(pessoas);

            banco.getArrayTurmas().clear();
            banco.getArrayTurmas().addAll(turmas);

             logger.info("Dados carregados com sucesso do arquivo binário!");
        } catch (FileNotFoundException e) {
             logger.info("Arquivo binário não encontrado. Ele será criado ao salvar os dados.");
        } catch (IOException | ClassNotFoundException e) {
             logger.info("Erro ao carregar os dados: " + e.getMessage());
        }
    }

    public static Pessoa buscarPessoa(String CPF) {
        for (Pessoa pessoa : banco.getArrayPessoas()) {
            if (pessoa.getCPF().equals(CPF)) {
                if (pessoa instanceof Aluno) {
                    Aluno aluno = (Aluno) pessoa;
                } else if (pessoa instanceof MembroEquipe) {
                    MembroEquipe membro = (MembroEquipe) pessoa;
                    membro.detalharMembroEquipe();
                }
                return pessoa;
            }
        }

         logger.info("Nenhuma pessoa com esse CPF foi encontrada...");
        return null;
    }

    public static Turma buscarTurma(String codigo){
        for (Turma turma : banco.getArrayTurmas()) {
            if (turma.getCodigo().equals(codigo)) {
                return turma;
            }
        }

         logger.info("Nenhuma turma com esse código foi encontrada...");
        return null;
    }

    public static void criarMembroPadrao() {
        String nomePadrao = "João Silva";
        String cpfPadrao = "12345678910";
        Genero generoPadrao = Genero.MASCULINO;
        String numeroCelularPadrao = "(84) 99982-1212";
        String matriculaPadrao = "20230001";
        String cursoPadrao = "Engenharia de Computação";
        String emailPadrao = "joao.silva@ufrn.br";
        Cargo cargoPadrao = Cargo.PROFESSOR;
        // Criando um objeto MembroEquipe com os valores padrão
        MembroEquipe membroPadrao = new MembroEquipe(
                nomePadrao,
                cpfPadrao,
                generoPadrao,
                numeroCelularPadrao,
                matriculaPadrao,
                cursoPadrao,
                emailPadrao,
                cargoPadrao
        );
        banco.getArrayPessoas().add(membroPadrao);
    }

}
