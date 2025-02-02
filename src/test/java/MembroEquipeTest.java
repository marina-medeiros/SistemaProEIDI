import br.ufrn.imd.sistemaproeidi.model.*;
import br.ufrn.imd.sistemaproeidi.model.enums.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Vector;

import static org.junit.jupiter.api.Assertions.*;

public class MembroEquipeTest {

    private MembroEquipe membroEquipe;
    private BancoDAO banco;
    private Turma turma;

    @BeforeEach
    void setUp() {
        banco = BancoDAO.getInstance();
        banco.getArrayPessoas().clear();
        banco.getArrayTurmas().clear();

        membroEquipe = new MembroEquipe(
                "João Silva",
                "12345678900",
                Genero.MASCULINO,
                "84999999999",
                "20230001",
                "Engenharia de Computação",
                "joao.silva@ufrn.br",
                Cargo.PROFESSOR
        );

        turma = new Turma(
                "Turma 1",
                Curso.PENSAMENTO_COMPUTACIONAL_I,
                Horario.DEZ_E_TRINTA,
                10,
                LocalDate.now(),
                LocalDate.now().plusMonths(1)
        );
    }

    @Test
    void testCadastrarMembroEquipe() {
        boolean resultado = membroEquipe.cadastrarMembroEquipe(
                "Maria Santos",
                "09876543211",
                Genero.FEMININO,
                "84988888888",
                "20230002",
                "Engenharia de Software",
                "maria.santos@ufrn.br",
                Cargo.PROFESSOR
        );

        assertTrue(resultado);
        assertEquals(1, banco.getArrayPessoas().size());
    }

    @Test
    void testCadastrarTurma() {
        boolean resultado = membroEquipe.cadastrarTurma(
                "Turma 2",
                Curso.COMPUTADOR,
                Horario.OITO_E_TRINTA,
                15,
                LocalDate.now(),
                LocalDate.now().plusMonths(1)
        );

        assertTrue(resultado);
        assertEquals(1, banco.getArrayTurmas().size());
    }

    @Test
    void testAdicionarAlunoATurma() {
        Aluno aluno = new Aluno(
                "Carlos Lima",
                "11122233344",
                Genero.MASCULINO,
                "84977777777",
                LocalDate.of(1960, 5, 15),
                Escolaridade.ALFABETIZADO,
                "Sem observações",
                true,
                true,
                true,
                SistemaOperacional.ANDROID
        );

        banco.getArrayPessoas().add(aluno);
        boolean resultado = membroEquipe.adicionarAlunoATurma(turma, aluno);

        assertTrue(resultado);
        assertEquals(1, turma.getAlunos().size());
        assertEquals(9, turma.getNumeroVagas());
    }

    @Test
    void testMatricularAlunoJaExistente() {
        Aluno aluno = new Aluno(
                "Carlos Lima",
                "11122233344",
                Genero.MASCULINO,
                "84977777777",
                LocalDate.of(1960, 5, 15),
                Escolaridade.ALFABETIZADO,
                "Sem observações",
                true,
                true,
                true,
                SistemaOperacional.ANDROID
        );

        banco.getArrayPessoas().add(aluno);

        boolean resultado = membroEquipe.matricularAluno(
                "Carlos Lima",
                "11122233344",
                Genero.MASCULINO,
                LocalDate.of(1960, 5, 15),
                "84977777777",
                Escolaridade.ALFABETIZADO,
                "Sem observações",
                true,
                true,
                true,
                SistemaOperacional.ANDROID,
                turma
        );

        assertTrue(resultado);
        assertEquals(1, turma.getAlunos().size());
    }

    @Test
    void testAdicionarMembroDaEquipeATurma() {
        membroEquipe.adicionarMembroDaEquipeATurma(turma, membroEquipe);

        assertEquals(1, turma.getEquipe().size());
        assertTrue(membroEquipe.getCodigosTurmas().contains(turma.getCodigo()));
    }

    @Test
    void testRemoverMembroDaEquipeDaTurma() {
        membroEquipe.adicionarMembroDaEquipeATurma(turma, membroEquipe);
        membroEquipe.removerMembroDaEquipeDaTurma(turma, membroEquipe);

        assertEquals(0, turma.getEquipe().size());
        assertFalse(membroEquipe.getCodigosTurmas().contains(turma.getCodigo()));
    }

    @Test
    void testConcluirTurma() {
        Aluno aluno = new Aluno(
                "Carlos Lima",
                "11122233344",
                Genero.MASCULINO,
                "84977777777",
                LocalDate.of(1960, 5, 15),
                Escolaridade.ALFABETIZADO,
                "Sem observações",
                true,
                true,
                true,
                SistemaOperacional.ANDROID
        );

        turma.getAlunos().add(aluno);
        membroEquipe.concluirTurma(turma);

        assertTrue(turma.getConcluido());
        assertTrue(aluno.getCursosFeitos().contains(turma.getCurso()));
        assertEquals(Curso.NENHUM, aluno.getCursoAtual());
    }
}
