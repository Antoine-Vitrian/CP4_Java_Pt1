package com.fiap.dao;

import java.math.BigDecimal;
import java.util.List;

public class ProdutoDAOImplTest {

    public static void main(String[] args) {
        deveSalvarEConsultarProduto();
        deveListarTodosProdutos();
        deveAtualizarProduto();
        deveExcluirProduto();
        System.out.println("Todos os testes passaram.");
    }

    private static void deveSalvarEConsultarProduto() {
        ProdutoDAO dao = new ProdutoDAOImpl();

        Produto produto = new Produto("Mouse", 12, new BigDecimal("89.90"));
        Produto salvo = dao.salvar(produto);

        assertNotNull(salvo.getId(), "ID do produto não pode ser nulo");
        assertEquals("Mouse", salvo.getNome(), "Nome do produto inválido");

        Produto encontrado = dao.buscarPorId(salvo.getId());
        assertNotNull(encontrado, "Produto não encontrado");
        assertEquals("Mouse", encontrado.getNome(), "Produto encontrado com nome incorreto");
    }

    private static void deveListarTodosProdutos() {
        ProdutoDAO dao = new ProdutoDAOImpl();
        dao.salvar(new Produto("Teclado", 5, new BigDecimal("199.90")));
        dao.salvar(new Produto("Monitor", 3, new BigDecimal("899.00")));

        List<Produto> produtos = dao.listarTodos();

        assertTrue(produtos.size() >= 2, "Lista de produtos deve ter pelo menos 2 itens");
    }

    private static void deveAtualizarProduto() {
        ProdutoDAO dao = new ProdutoDAOImpl();
        Produto produto = dao.salvar(new Produto("Cabo USB", 10, new BigDecimal("25.00")));

        produto.setNome("Cabo USB-C");
        produto.setQuantidade(15);
        produto.setPreco(new BigDecimal("30.00"));

        Produto atualizado = dao.atualizar(produto);

        assertEquals("Cabo USB-C", atualizado.getNome(), "Nome não foi atualizado");
        assertEquals(15, atualizado.getQuantidade(), "Quantidade não foi atualizada");
        assertEquals(new BigDecimal("30.00"), atualizado.getPreco(), "Preço não foi atualizado");
    }

    private static void deveExcluirProduto() {
        ProdutoDAO dao = new ProdutoDAOImpl();
        Produto produto = dao.salvar(new Produto("Webcam", 7, new BigDecimal("320.00")));

        dao.excluir(produto.getId());

        assertNull(dao.buscarPorId(produto.getId()), "Produto ainda existe após exclusão");
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
