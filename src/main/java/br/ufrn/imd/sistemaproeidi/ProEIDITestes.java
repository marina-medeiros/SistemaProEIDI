//package br.ufrn.imd.sistemaproeidi;
//import br.ufrn.imd.sistemaproeidi.model.*;
//import br.ufrn.imd.sistemaproeidi.model.enums.*;
//
//
//import java.time.LocalDate;
//
//public class ProEIDITestes {
//    public static void main(String[] args) {
//        // Instância do BancoDAO
//        BancoDAO banco = BancoDAO.getInstance();
//
//        // Criar objetos para teste
//        MembroEquipe professor = new MembroEquipe("Carlos Silva", "12345678901", Genero.MASC, "987654321", "12345", "Engenharia de Software", "carlos.silva@ufrn.br", Cargo.PROFESSOR);
//        banco.getArrayPessoas().add(professor);
//
//        Aluno aluno = new Aluno("Maria Oliveira", "98765432100", Genero.FEM, "123456789", LocalDate.of(1950, 5, 20), Escolaridade.FUNDAMENTAL_INCOMPLETO , "Nenhuma", true, true, true, SistemaOperacional.IOS);
//        banco.getArrayPessoas().add(aluno);
//
//        Turma turma = new Turma();
//        turma.setNome("Turma A");
//        turma.setCurso(Curso.PENSAMENTO_COMPUTACIONAL_I);
//        turma.setHorario(Horario.DEZ_E_TRINTA);
//        turma.setNumeroVagas(2);
//        turma.setDataInicio(LocalDate.of(2025, 1, 15));
//        turma.setDataTermino(LocalDate.of(2025, 6, 15));
//        banco.getArrayTurmas().add(turma);
//
//        // Testar cadastro de aluno
//        System.out.println("Teste: Matricular Aluno");
//        professor.matricularAluno();
//
//        // Testar cadastro de membro da equipe
//        System.out.println("\nTeste: Cadastrar Membro da Equipe");
//        professor.cadastrarMembroEquipe();
//
//        // Testar busca de pessoa
//        System.out.println("\nTeste: Buscar Pessoa pelo CPF");
//        Pessoa encontrada = professor.buscarPessoa("12345678901");
//        if (encontrada != null) {
//            System.out.println("Pessoa encontrada: " + encontrada.getNome());
//        }
//
//        // Testar busca de turma
//        System.out.println("\nTeste: Buscar Turma pelo Nome");
//        professor.buscarTurma("Turma A");
//
//        // Testar listar turmas
//        System.out.println("\nTeste: Listar Turmas");
//        professor.listarTurmas();
//
//        // Testar chamada de alunos
//        System.out.println("\nTeste: Chamada de Alunos");
//        professor.chamadaAlunos(turma);
//
//        // Testar edição de aluno
//        System.out.println("\nTeste: Editar Aluno");
//        professor.editarAluno(aluno);
//
//        // Testar edição de membro da equipe
//        System.out.println("\nTeste: Editar Membro da Equipe");
//        professor.editarMembroEquipe(professor);
//
//        // Testar adicionar membro da equipe à turma
//        System.out.println("\nTeste: Adicionar Membro da Equipe à Turma");
//        professor.adicionarMembroDaEquipeATurma(turma, professor);
//
//        // Testar edição de turma
//        System.out.println("\nTeste: Editar Turma");
//        professor.editarTurma(turma);
//
//        // Testar remoção de pessoa da turma
//        System.out.println("\nTeste: Remover Pessoa da Turma");
//        professor.removerPessoaDaTurma(aluno, turma);
//
//        // Listar turmas novamente para confirmar alterações
//        System.out.println("\nTurmas após alterações:");
//        professor.listarTurmas();
//    }
//}
