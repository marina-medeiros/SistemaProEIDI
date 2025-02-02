import br.ufrn.imd.sistemaproeidi.model.*;
import br.ufrn.imd.sistemaproeidi.model.enums.*;
import org.junit.jupiter.api.*;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class GerenciadorTest {

    private static BancoDAO banco;

    @BeforeEach
    void setUp() {
        banco = BancoDAO.getInstance();
        banco.getArrayPessoas().clear();
        banco.getArrayTurmas().clear();
    }

    @AfterEach
    void tearDown() {
        File arquivoBinario = new File("banco.bin");
        if (arquivoBinario.exists()) {
            assertTrue(arquivoBinario.delete());
        }
    }

    @Test
    void testSalvarECarregarBinario() {
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

        Turma turma = new Turma(
                "Turma 1",
                Curso.PENSAMENTO_COMPUTACIONAL_I,
                Horario.DEZ_E_TRINTA,
                10,
                LocalDate.now(),
                LocalDate.now().plusMonths(1)
        );

        banco.getArrayPessoas().add(aluno);
        banco.getArrayTurmas().add(turma);

        Gerenciador.salvarBinario();

        banco.getArrayPessoas().clear();
        banco.getArrayTurmas().clear();

        Gerenciador.carregarBinario();

        assertEquals(1, banco.getArrayPessoas().size());
        assertEquals(1, banco.getArrayTurmas().size());
        assertEquals("Carlos Lima", banco.getArrayPessoas().get(0).getNome());
        assertEquals("Turma 1", banco.getArrayTurmas().get(0).getNome());
    }

    @Test
    void testBuscarPessoa() {
        Aluno aluno = new Aluno(
                "Maria Santos",
                "12345678900",
                Genero.FEMININO,
                "84988888888",
                LocalDate.of(1995, 8, 20),
                Escolaridade.P0S_GRADUADO,
                "Sem observações",
                true,
                true,
                true,
                SistemaOperacional.IOS
        );

        banco.getArrayPessoas().add(aluno);

        Pessoa pessoaEncontrada = Gerenciador.buscarPessoa("12345678900");
        assertNotNull(pessoaEncontrada);
        assertEquals("Maria Santos", pessoaEncontrada.getNome());
    }

    @Test
    void testBuscarPessoaNaoExistente() {
        Pessoa pessoaEncontrada = Gerenciador.buscarPessoa("00000000000");
        assertNull(pessoaEncontrada);
    }

    @Test
    void testBuscarTurma() {
        Turma turma = new Turma(
                "Turma 2",
                Curso.COMPUTADOR,
                Horario.OITO_E_TRINTA,
                15,
                LocalDate.now(),
                LocalDate.now().plusMonths(1)
        );

        banco.getArrayTurmas().add(turma);

        Turma turmaEncontrada = Gerenciador.buscarTurma(turma.getCodigo());
        assertNotNull(turmaEncontrada);
        assertEquals("Turma 2", turmaEncontrada.getNome());
    }

    @Test
    void testBuscarTurmaNaoExistente() {
        Turma turmaEncontrada = Gerenciador.buscarTurma("Turma 99");
        assertNull(turmaEncontrada);
    }
}
