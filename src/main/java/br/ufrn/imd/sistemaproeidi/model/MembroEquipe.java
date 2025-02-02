package br.ufrn.imd.sistemaproeidi.model;

import br.ufrn.imd.sistemaproeidi.model.enums.*;
import java.util.logging.Logger;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;
import java.util.Vector;

public class MembroEquipe extends Pessoa implements Serializable {
    private String matricula;
    private String cursoUFRN;
    private String email;
    private Cargo cargo;
    private Vector<String> codigosTurmas;
    private static BancoDAO banco = BancoDAO.getInstance();
    private static final Logger logger = Logger.getLogger(MembroEquipe.class.getName());

    public MembroEquipe(String nome, String CPF, Genero genero, String numeroCelular, String matricula, String cursoUFRN, String email, Cargo cargo) {
        super(nome, CPF, genero, numeroCelular);
        this.matricula = matricula;
        this.cursoUFRN = cursoUFRN;
        this.email = email;
        this.cargo = cargo;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public String getEmail() {
        return email;
    }

    public String getCursoUFRN() {
        return cursoUFRN;
    }

    public String getMatricula() {
        return matricula;
    }

    public Vector<String> getCodigosTurmas() {
        if (codigosTurmas == null) {
            codigosTurmas = new Vector<>();
        }
        return codigosTurmas;
    }

    public boolean matricularAluno(String nome, String cpf, Genero genero, LocalDate dataNascimento, String numeroCelular, Escolaridade escolaridade, String obsSaude, boolean temInternet, boolean temComputador, boolean temSmartphone, SistemaOperacional SO, Turma turma) {
        if (Period.between(dataNascimento, LocalDate.now()).getYears() < 60) {
            logger.info("É necessário ser idoso para participar do projeto.");
            return false;
        }

        Aluno alunoExistente = null;
        for (Pessoa pessoa : banco.getArrayPessoas()) {
            if (pessoa instanceof Aluno && pessoa.getCPF().equals(cpf)) {
                alunoExistente = (Aluno) pessoa;
                break;
            }
        }

        if (alunoExistente != null) {
            alunoExistente.setNome(nome);
            alunoExistente.setGenero(genero);
            alunoExistente.setNumeroCelular(numeroCelular);
            alunoExistente.setDataNascimento(dataNascimento);
            alunoExistente.setEscolaridade(escolaridade);
            alunoExistente.setObsSaude(obsSaude);
            alunoExistente.setTemInternet(temInternet);
            alunoExistente.setTemComputador(temComputador);
            alunoExistente.setTemSmartphone(temSmartphone);
            alunoExistente.setSistemaOperacional(SO);

            logger.info("Informações do aluno " + alunoExistente.getNome() + " atualizadas com sucesso!");
        } else {
            Aluno novoAluno = new Aluno(nome, cpf, genero, numeroCelular, dataNascimento, escolaridade, obsSaude, temInternet, temComputador, temSmartphone, SO);

            if (!banco.getArrayPessoas().add(novoAluno)) {
                logger.info("Erro ao matricular!");
                return false;
            }
            alunoExistente = novoAluno;
            logger.info("Novo aluno matriculado com sucesso!");
        }

        if (Objects.equals(alunoExistente.getCodigoTurma(), turma.getCodigo())) {
            logger.info("Esse aluno ja está nessa turma!");

        }else{
            if (adicionarAlunoATurma(turma, alunoExistente)) {
                logger.info("Aluno " + alunoExistente.getNome() + " adicionado à turma " + turma.getNome() + " com sucesso!");
            }else{
                logger.info("Erro ao adicionar aluno!");
                return false;
            }

        }

        return true;
    }

    public boolean  cadastrarMembroEquipe(String nome, String cpf, Genero genero, String numeroCelular, String matricula, String cursoUFRN, String email, Cargo cargo) {
        MembroEquipe novoMembro = new MembroEquipe(nome, cpf, genero, numeroCelular, matricula, cursoUFRN, email, cargo);

        if (banco.getArrayPessoas().add(novoMembro)) {
            logger.info("Membro da equipe cadastrado com sucesso!");
        } else {
            logger.info("Erro ao cadastrar o membro da equipe!");
            return false;
        }
        return true;

    }

    public boolean adicionarAlunoATurma(Turma turma, Aluno aluno){
        if(turma.getNumeroVagas() > 0){
            aluno.setCursoAtual(turma.getCurso());
            aluno.setCodigoTurma(turma.getCodigo());
            Vector<Aluno> alunos = turma.getAlunos();
            if(alunos.add(aluno)){
                logger.info("Membro adicionado com sucesso à turma " + turma.getNome());
            }
            turma.setAlunos(alunos);
            int numeroDeVagas = turma.getNumeroVagas() - 1;
            turma.setNumeroVagas(numeroDeVagas);
            return true;
        }

        logger.info("Turma cheia");
        return false;
    }

    public void adicionarMembroDaEquipeATurma(Turma turma, MembroEquipe membro){
        Vector<MembroEquipe> membros = turma.getEquipe();
        if(membros.add(membro)){
            membro.getCodigosTurmas().add(turma.getCodigo());
            logger.info("Membro adicionado com sucesso à turma " + turma.getNome());
        }
        turma.setEquipe(membros);
    }

    public void removerMembroDaEquipeDaTurma(Turma turma, MembroEquipe membro){
        Vector<MembroEquipe> membros = turma.getEquipe();
        if(membros.remove(membro)){
            membro.getCodigosTurmas().remove(turma.getCodigo());
            logger.info("Membro removido com sucesso da turma " + turma.getNome());
        }
        turma.setEquipe(membros);
    }

    public boolean cadastrarTurma(String nome, Curso curso, Horario horario, Integer numeroVagas, LocalDate dataInicio, LocalDate dataTermino){
        Turma turma = new Turma(nome, curso, horario, numeroVagas, dataInicio, dataTermino);

        if(banco.getArrayTurmas().add(turma)){
            logger.info("Turma cadastrada com sucesso!");
        }else{
            logger.info("Erro ao cadastrar turma!");
            return false;
        }
        return true;
    }

    public void detalharMembroEquipe() {
        logger.info("=== Detalhes do Membro da Equipe ===");
        logger.info("Nome: " + getNome());
        logger.info("CPF: " + getCPF());
        logger.info("Gênero: " + getGenero());
        logger.info("Número de Celular: " + getNumeroCelular());
        logger.info("Matrícula: " + matricula);
        logger.info("Curso na UFRN: " + cursoUFRN);
        logger.info("E-mail: " + email);
        logger.info("Cargo: " + cargo);
    }

    public void removerPessoaDasTurmas(Pessoa pessoa){
        if(pessoa instanceof Aluno){
            Aluno aluno = (Aluno) pessoa;
            String codigoTurma = aluno.getCodigoTurma();
            for(Turma turma : banco.getArrayTurmas()){
                if(Objects.equals(codigoTurma, turma.getCodigo())){
                    turma.getAlunos().remove(aluno);
                    int vagas = turma.getNumeroVagas();
                    vagas++;
                    turma.setNumeroVagas(vagas);
                }
            }
            logger.info("Aluno removido com sucesso");
        } else if(pessoa instanceof MembroEquipe){
            MembroEquipe membroEquipe = (MembroEquipe) pessoa;

            for(Turma turma : banco.getArrayTurmas()){
                for(String codigo : membroEquipe.getCodigosTurmas()){
                    if(Objects.equals(codigo, turma.getCodigo())){
                        turma.getEquipe().remove(membroEquipe);
                    }
                }
            }

            logger.info("Membro da equipe removido com sucesso");
        }

    }

    public void concluirTurma(Turma turma) {
        turma.setConcluido(true);
        for(Aluno aluno : turma.getAlunos()){
            aluno.getFaltas().clear();
            aluno.getCursosFeitos().add(turma.getCurso());
            aluno.setCursoAtual(Curso.NENHUM);
        }
        logger.info("Turma concluida com sucesso");
    }

    public void removerTurma(Turma turma){
        banco.getArrayTurmas().remove(turma);
        for(Aluno aluno : turma.getAlunos()){
            Vector<Curso> cursosAluno = aluno.getCursosFeitos();
            cursosAluno.remove(turma.getCurso());
            aluno.setCursosFeitos(cursosAluno);
            if(aluno.getCursoAtual() == turma.getCurso()){
                aluno.setCursoAtual(Curso.NENHUM);
            }
        }
        logger.info("Removida com sucesso");
    }
}
