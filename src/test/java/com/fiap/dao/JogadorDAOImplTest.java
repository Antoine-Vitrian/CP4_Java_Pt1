package com.fiap.dao;

import com.fiap.factory.DAOFactory;
import com.fiap.model.Jogador;

import java.util.List;

public class JogadorDAOImplTest {

    public static void main(String[] args) {
        deveSalvarEConsultarJogador();
        deveListarTodosJogadores();
        deveAtualizarJogador();
        deveExcluirJogador();
        System.out.println("Todos os testes passaram.");
    }

    private static void deveSalvarEConsultarJogador() {
        JogadorDAO dao = DAOFactory.criarJogadorDAO();

        Jogador jogador = new Jogador("Rodrygo", "Atacante", 11, "Real Madrid");
        Jogador salvo = dao.salvar(jogador);

        assertNotNull(salvo.getId(), "ID do jogador nao pode ser nulo");
        assertEquals("Rodrygo", salvo.getNome(), "Nome do jogador invalido");

        Jogador encontrado = dao.buscarPorId(salvo.getId());
        assertNotNull(encontrado, "Jogador nao encontrado");
        assertEquals("Rodrygo", encontrado.getNome(), "Jogador encontrado com nome incorreto");
        assertEquals("Atacante", encontrado.getPosicao(), "Posicao incorreta");
        assertEquals(11, encontrado.getNumero(), "Numero da camisa incorreto");
        assertEquals("Real Madrid", encontrado.getClube(), "Clube incorreto");

        dao.excluir(salvo.getId());
    }

    private static void deveListarTodosJogadores() {
        JogadorDAO dao = DAOFactory.criarJogadorDAO();
        Jogador primeiro = dao.salvar(new Jogador("Alisson", "Goleiro", 1, "Liverpool"));
        Jogador segundo = dao.salvar(new Jogador("Marquinhos", "Zagueiro", 4, "PSG"));

        List<Jogador> jogadores = dao.listarTodos();

        assertTrue(jogadores.size() >= 2, "Lista de jogadores deve ter pelo menos 2 itens");

        dao.excluir(primeiro.getId());
        dao.excluir(segundo.getId());
    }

    private static void deveAtualizarJogador() {
        JogadorDAO dao = DAOFactory.criarJogadorDAO();
        Jogador jogador = dao.salvar(new Jogador("Vini Jr", "Ponta", 20, "Real Madrid"));

        jogador.setNome("Vinicius Junior");
        jogador.setPosicao("Atacante");
        jogador.setNumero(7);
        jogador.setClube("Selecao Brasileira");

        Jogador atualizado = dao.atualizar(jogador);

        assertEquals("Vinicius Junior", atualizado.getNome(), "Nome nao foi atualizado");
        assertEquals("Atacante", atualizado.getPosicao(), "Posicao nao foi atualizada");
        assertEquals(7, atualizado.getNumero(), "Numero nao foi atualizado");
        assertEquals("Selecao Brasileira", atualizado.getClube(), "Clube nao foi atualizado");

        Jogador persistido = dao.buscarPorId(jogador.getId());
        assertEquals("Vinicius Junior", persistido.getNome(), "Nome persistido incorreto");
        assertEquals(7, persistido.getNumero(), "Numero persistido incorreto");

        dao.excluir(jogador.getId());
    }

    private static void deveExcluirJogador() {
        JogadorDAO dao = DAOFactory.criarJogadorDAO();
        Jogador jogador = dao.salvar(new Jogador("Endrick", "Atacante", 9, "Palmeiras"));

        dao.excluir(jogador.getId());

        assertNull(dao.buscarPorId(jogador.getId()), "Jogador ainda existe apos exclusao");
    }

    private static void assertNotNull(Object value, String message) {
        if (value == null) {
            throw new AssertionError(message);
        }
    }

    private static void assertEquals(Object expected, Object actual, String message) {
        if (expected == null ? actual != null : !expected.equals(actual)) {
            throw new AssertionError(message + " | esperado=" + expected + ", atual=" + actual);
        }
    }

    private static void assertTrue(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    private static void assertNull(Object value, String message) {
        if (value != null) {
            throw new AssertionError(message + " | valor atual=" + value);
        }
    }
}
