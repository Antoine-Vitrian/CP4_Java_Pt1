package com.fiap.view;

import com.fiap.dao.JogadorDAO;
import com.fiap.factory.DAOFactory;
import com.fiap.model.Jogador;
import com.fiap.singleton.ConexaoSingleton;

import java.sql.Connection;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== CRUD de Jogadores (DAO + Factory + Singleton) ===");

        Connection conexaoSingleton = ConexaoSingleton.getInstancia().getConexao();
        Connection mesmaConexao = ConexaoSingleton.getInstancia().getConexao();
        System.out.println("Singleton ativo: mesma conexao compartilhada? " + (conexaoSingleton == mesmaConexao));

        JogadorDAO dao = DAOFactory.criarJogadorDAO();

        System.out.println("\n-- CREATE --");
        Jogador neymar = dao.salvar(new Jogador("Neymar Jr", "Atacante", 10, "Santos"));
        Jogador casemiro = dao.salvar(new Jogador("Casemiro", "Volante", 5, "Real Madrid"));
        System.out.println("Salvo: " + neymar);
        System.out.println("Salvo: " + casemiro);

        System.out.println("\n-- READ (buscar por id) --");
        Jogador encontrado = dao.buscarPorId(neymar.getId());
        System.out.println("Encontrado: " + encontrado);

        System.out.println("\n-- READ (listar todos) --");
        List<Jogador> jogadores = dao.listarTodos();
        for (Jogador jogador : jogadores) {
            System.out.println(jogador);
        }

        System.out.println("\n-- UPDATE --");
        encontrado.setClube("Al-Hilal");
        encontrado.setNumero(11);
        Jogador atualizado = dao.atualizar(encontrado);
        System.out.println("Atualizado: " + atualizado);

        System.out.println("\n-- DELETE --");
        dao.excluir(casemiro.getId());
        System.out.println("Jogador id=" + casemiro.getId() + " excluido. Ainda existe? " + (dao.buscarPorId(casemiro.getId()) != null));

        System.out.println("\n-- Lista final --");
        for (Jogador jogador : dao.listarTodos()) {
            System.out.println(jogador);
        }
    }
}
