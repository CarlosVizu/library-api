package com.interview.libraryapi.unit.service;

import com.interview.libraryapi.data.dto.v1.EmprestimoDTO;
import com.interview.libraryapi.data.dto.v1.LivroDTO;
import com.interview.libraryapi.exceptions.EmprestimoNaoRealizadoException;
import com.interview.libraryapi.exceptions.ParameterUpdateInvalid;
import com.interview.libraryapi.exceptions.ResourceNotFoundException;
import com.interview.libraryapi.model.Emprestimo;
import com.interview.libraryapi.model.Livro;
import com.interview.libraryapi.model.Usuario;
import com.interview.libraryapi.repositories.EmprestimoRepository;
import com.interview.libraryapi.repositories.LivroRepository;
import com.interview.libraryapi.repositories.UsuarioRepository;
import com.interview.libraryapi.service.LivroService;
import com.interview.libraryapi.service.impl.EmprestimoServiceImpl;
import com.interview.libraryapi.service.impl.LivroServiceImpl;
import com.interview.libraryapi.service.impl.UsuarioServiceImpl;
import com.interview.libraryapi.unit.mock.EmprestimoMock;
import com.interview.libraryapi.unit.mock.LivroMock;
import com.interview.libraryapi.unit.mock.UsuarioMock;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.*;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
public class EmprestimoServiceTest {

    @Mock
    private EmprestimoRepository repository;

    @InjectMocks
    private EmprestimoServiceImpl emprestimoService;

    @Mock
    private LivroServiceImpl livroService;

    @Mock
    private UsuarioServiceImpl usuarioService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private LivroRepository livroRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this); // Initializes the mocks
    }

    @Test
    public void EmprestimoService_salvarEmprestimo_RetornaDto() {
        EmprestimoDTO emprestimoDTO = EmprestimoMock.emprestimoDTOMock();
        Usuario usuario = UsuarioMock.usuarioValido();
        Livro livro = LivroMock.livroValido();
        Page<Emprestimo> emprestimoDisponivel = EmprestimoMock.emprestimoPageMockDisponivel();

        Emprestimo emprestimo = new Emprestimo(emprestimoDTO, usuario, livro);

        when(usuarioService.validarUsuarioExiste(emprestimoDTO.getUsuarioId())).thenReturn(usuario);
        when(livroService.validarLivroExiste(emprestimoDTO.getLivroId())).thenReturn(livro);

        when(repository.procurarUltimoEmprestimoPorLivro(livro.getId(), PageRequest.of(0, 1)))
                .thenReturn(emprestimoDisponivel);

        when(repository.save(Mockito.any(Emprestimo.class))).thenReturn(emprestimo);

        EmprestimoDTO emprestimoDTOSalvo = emprestimoService.salvarEmprestimo(emprestimoDTO);

        Assertions.assertThat(emprestimoDTOSalvo).isNotNull();
    }

    @Test
    public void EmprestimoService_salvarEmprestimo_FalhaLivroNaoDisponivel(){
        EmprestimoDTO emprestimoDTO = EmprestimoMock.emprestimoDTOMock();
        Usuario usuario = UsuarioMock.usuarioValido();
        Livro livro = LivroMock.livroValido();
        Page<Emprestimo> emprestimoIndisponivel = EmprestimoMock.emprestimoPageMockIndisponivel();

        when(usuarioService.validarUsuarioExiste(emprestimoDTO.getUsuarioId())).thenReturn(usuario);
        when(livroService.validarLivroExiste(emprestimoDTO.getLivroId())).thenReturn(livro);

        when(repository.procurarUltimoEmprestimoPorLivro(livro.getId(), PageRequest.of(0, 1)))
                .thenReturn(emprestimoIndisponivel);

        Throwable exception = assertThrows(EmprestimoNaoRealizadoException.class,
                () -> emprestimoService.salvarEmprestimo(emprestimoDTO));

        assertEquals("O Livro informado não está disponível para empréstimo.", exception.getMessage());
    }

    @Test
    public void EmprestimoService_salvarEmprestimo_FalhaStatusIncompatível(){
        EmprestimoDTO emprestimoDTO = EmprestimoMock.emprestimoDTOMock();
        Usuario usuario = UsuarioMock.usuarioValido();
        Livro livro = LivroMock.livroValido();
        Page<Emprestimo> emprestimoDisponivel = EmprestimoMock.emprestimoPageMockDisponivel();

        emprestimoDTO.setStatus("INCOMPATÍVEL");

        when(usuarioService.validarUsuarioExiste(emprestimoDTO.getUsuarioId())).thenReturn(usuario);
        when(livroService.validarLivroExiste(emprestimoDTO.getLivroId())).thenReturn(livro);
        when(repository.procurarUltimoEmprestimoPorLivro(livro.getId(), PageRequest.of(0, 1)))
                .thenReturn(emprestimoDisponivel);

        Throwable exception = assertThrows(ParameterUpdateInvalid.class,
                () -> emprestimoService.salvarEmprestimo(emprestimoDTO));

        assertEquals("Emprestimo inválido: Informar 'DISPONIVEL' ou 'INDISPONIVEL' no campo Status", exception.getMessage());
    }

    @Test
    public void EmprestimoService_atualizarEmprestimo_RetornaDTO() {
        EmprestimoDTO emprestimoDTO = EmprestimoMock.emprestimoDTOMock();
        Usuario usuario = UsuarioMock.usuarioValido();
        Livro livro = LivroMock.livroValido();
        Emprestimo emprestimo = EmprestimoMock.emprestimoMock();

        emprestimoDTO.setStatus("DISPONIVEL");
        emprestimoDTO.setDataDevolucao(new Date());

        when(usuarioService.validarUsuarioExiste(emprestimoDTO.getUsuarioId())).thenReturn(usuario);
        when(livroService.validarLivroExiste(emprestimoDTO.getLivroId())).thenReturn(livro);
        when(repository.findById(emprestimoDTO.getId())).thenReturn(Optional.of(emprestimo));

        EmprestimoDTO emprestimoDTOSalvo = emprestimoService.atualizarEmprestimo(emprestimoDTO);

        assertEquals(emprestimoDTO, emprestimoDTOSalvo);
    }

    @Test
    public void EmprestimoService_atualizarEmprestimo_EmprestimoNaoEncontrado() {
        EmprestimoDTO emprestimoDTO = EmprestimoMock.emprestimoDTOMock();
        Usuario usuario = UsuarioMock.usuarioValido();
        Livro livro = LivroMock.livroValido();
        Emprestimo emprestimo = EmprestimoMock.emprestimoMock();

        emprestimoDTO.setStatus("DISPONIVEL");
        emprestimoDTO.setDataDevolucao(new Date());

        when(usuarioService.validarUsuarioExiste(emprestimoDTO.getUsuarioId())).thenReturn(usuario);
        when(livroService.validarLivroExiste(emprestimoDTO.getLivroId())).thenReturn(livro);

        when(repository.findById(emprestimoDTO.getId())).thenReturn(Optional.empty());

        Throwable exception = assertThrows(ResourceNotFoundException.class,
                () -> emprestimoService.atualizarEmprestimo(emprestimoDTO));
        assertEquals("Nenhum empréstimo encontrado.", exception.getMessage());

    }

    @Test
    public void EmprestimoService_atualizarEmprestimo_AtualizacaoInvalidaLivro() {
        EmprestimoDTO emprestimoDTO = EmprestimoMock.emprestimoDTOMock();
        Usuario usuario = UsuarioMock.usuarioValido();
        Livro livro = LivroMock.livroValido();
        Emprestimo emprestimo = EmprestimoMock.emprestimoMock();

        emprestimoDTO.setLivroId(2L);

        when(usuarioService.validarUsuarioExiste(emprestimoDTO.getUsuarioId())).thenReturn(usuario);
        when(livroService.validarLivroExiste(emprestimoDTO.getLivroId())).thenReturn(livro);
        when(repository.findById(emprestimoDTO.getId())).thenReturn(Optional.of(emprestimo));

        Throwable exception = assertThrows(ParameterUpdateInvalid.class,
                () -> emprestimoService.atualizarEmprestimo(emprestimoDTO));
        assertEquals("Atualização inválida: Não é possível alterar o Livro emprestado.", exception.getMessage());
    }

    @Test
    public void EmprestimoService_atualizarEmprestimo_AtualizacaoInvalidaUsuario() {
        EmprestimoDTO emprestimoDTO = EmprestimoMock.emprestimoDTOMock();
        Usuario usuario = UsuarioMock.usuarioValido();
        Livro livro = LivroMock.livroValido();
        Emprestimo emprestimo = EmprestimoMock.emprestimoMock();

        emprestimoDTO.setUsuarioId(2L);

        when(usuarioService.validarUsuarioExiste(emprestimoDTO.getUsuarioId())).thenReturn(usuario);
        when(livroService.validarLivroExiste(emprestimoDTO.getLivroId())).thenReturn(livro);
        when(repository.findById(emprestimoDTO.getId())).thenReturn(Optional.of(emprestimo));

        Throwable exception = assertThrows(ParameterUpdateInvalid.class,
                () -> emprestimoService.atualizarEmprestimo(emprestimoDTO));
        assertEquals("Atualização inválida: Não é possível alterar o Usuário de empréstimo.", exception.getMessage());
    }

    @Test
    public void EmprestimoService_atualizarEmprestimo_AtualizacaoInvalidaStatus() {
        EmprestimoDTO emprestimoDTO = EmprestimoMock.emprestimoDTOMock();
        Usuario usuario = UsuarioMock.usuarioValido();
        Livro livro = LivroMock.livroValido();
        Emprestimo emprestimo = EmprestimoMock.emprestimoMock();

        emprestimoDTO.setStatus("INEXISTENTE");

        when(usuarioService.validarUsuarioExiste(emprestimoDTO.getUsuarioId())).thenReturn(usuario);
        when(livroService.validarLivroExiste(emprestimoDTO.getLivroId())).thenReturn(livro);
        when(repository.findById(emprestimoDTO.getId())).thenReturn(Optional.of(emprestimo));

        Throwable exception = assertThrows(ParameterUpdateInvalid.class,
                () -> emprestimoService.atualizarEmprestimo(emprestimoDTO));
        assertEquals("Atualização inválida: Informar 'DISPONIVEL' ou 'INDISPONIVEL' no campo Status", exception.getMessage());
    }

    @Test
    public void EmprestimoService_atualizarEmprestimo_AtualizacaoInvalidaDataDevolucao() {
        EmprestimoDTO emprestimoDTO = EmprestimoMock.emprestimoDTOMock();
        Usuario usuario = UsuarioMock.usuarioValido();
        Livro livro = LivroMock.livroValido();
        Emprestimo emprestimo = EmprestimoMock.emprestimoMock();

        Calendar calendario = Calendar.getInstance();
        calendario.add(Calendar.DAY_OF_YEAR, -1);
        emprestimoDTO.setDataDevolucao(calendario.getTime());

        when(usuarioService.validarUsuarioExiste(emprestimoDTO.getUsuarioId())).thenReturn(usuario);
        when(livroService.validarLivroExiste(emprestimoDTO.getLivroId())).thenReturn(livro);
        when(repository.findById(emprestimoDTO.getId())).thenReturn(Optional.of(emprestimo));

        Throwable exception = assertThrows(ParameterUpdateInvalid.class,
                () -> emprestimoService.atualizarEmprestimo(emprestimoDTO));
        assertEquals("Atualização inválida: A data de devolução deve ser igual ou posterior à data atual.", exception.getMessage());
    }

    @Test
    public void EmprestimoService_atualizarEmprestimo_AtualizacaoInvalidaDataEmprestimo() {
        EmprestimoDTO emprestimoDTO = EmprestimoMock.emprestimoDTOMock();
        Usuario usuario = UsuarioMock.usuarioValido();
        Livro livro = LivroMock.livroValido();
        Emprestimo emprestimo = EmprestimoMock.emprestimoMock();

        Calendar calendario = Calendar.getInstance();
        calendario.add(Calendar.DAY_OF_YEAR, +1);
        emprestimoDTO.setDataEmprestimo(calendario.getTime());

        when(usuarioService.validarUsuarioExiste(emprestimoDTO.getUsuarioId())).thenReturn(usuario);
        when(livroService.validarLivroExiste(emprestimoDTO.getLivroId())).thenReturn(livro);
        when(repository.findById(emprestimoDTO.getId())).thenReturn(Optional.of(emprestimo));

        Throwable exception = assertThrows(ParameterUpdateInvalid.class,
                () -> emprestimoService.atualizarEmprestimo(emprestimoDTO));
        assertEquals("Atualização inválida: Não é possível alterar a Data de Empréstimo.", exception.getMessage());
    }

    @Test
    public void EmprestimoService_recomendarLivros_RetornarListaLivrosDTO() {
        List<Livro> historicoLivros = LivroMock.listaDeHistoricoLivrosEmprestados();
        Usuario usuario = UsuarioMock.usuarioValidoEmprestimo();

        when(repository.procurarTodosLivrosEmprestadosPorUsuario(usuario.getId())).thenReturn(historicoLivros);

        List<LivroDTO> livrosRecomendadosDTO = emprestimoService.recomendarLivros(usuario.getId());

        Assertions.assertThat(livrosRecomendadosDTO).isNotNull();
    }


}
