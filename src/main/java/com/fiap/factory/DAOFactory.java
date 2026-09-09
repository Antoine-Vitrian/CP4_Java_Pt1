package com.fiap.factory;

import com.fiap.dao.JogadorDAO;
import com.fiap.dao.JogadorDAOImpl;
import com.fiap.singleton.ConexaoSingleton;

import java.sql.Connection;

public class DAOFactory {
    private DAOFactory() {
    }

    public static JogadorDAO criarJogadorDAO() {
        Connection conexao = ConexaoSingleton.getInstancia().getConexao();
        return new JogadorDAOImpl(conexao);
    }
}
