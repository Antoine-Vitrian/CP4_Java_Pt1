package com.fiap.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class ProdutoDAOImpl implements ProdutoDAO {
    private final Map<Long, Produto> produtos = new HashMap<>();
    private final AtomicLong sequencia = new AtomicLong(1L);

    @Override
    public Produto salvar(Produto produto) {
        if (produto.getId() == null) {
            produto.setId(sequencia.getAndIncrement());
        }
        produtos.put(produto.getId(), produto);
        return produto;
    }

    @Override
    public Produto buscarPorId(Long id) {
        return produtos.get(id);
    }

    @Override
    public List<Produto> listarTodos() {
        return new ArrayList<>(produtos.values());
    }

    @Override
    public Produto atualizar(Produto produto) {
        if (produto.getId() == null) {
            return salvar(produto);
        }
        produtos.put(produto.getId(), produto);
        return produto;
    }

    @Override
    public void excluir(Long id) {
        produtos.remove(id);
    }
}
