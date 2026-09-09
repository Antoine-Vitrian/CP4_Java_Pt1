package com.fiap.dao;

import com.fiap.model.Jogador;

import java.util.List;

public interface JogadorDAO {
    Jogador salvar(Jogador jogador);

    Jogador buscarPorId(Long id);

    List<Jogador> listarTodos();

    Jogador atualizar(Jogador jogador);

    void excluir(Long id);
}
