package com.fiap.dao;

import java.util.List;

public interface ProdutoDAO {
    Produto salvar(Produto produto);

    Produto buscarPorId(Long id);

    List<Produto> listarTodos();

    Produto atualizar(Produto produto);

    void excluir(Long id);
}
