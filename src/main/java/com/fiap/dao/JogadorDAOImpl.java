package com.fiap.dao;

import com.fiap.model.Jogador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JogadorDAOImpl implements JogadorDAO {
    private final Connection conexao;

    public JogadorDAOImpl(Connection conexao) {
        this.conexao = conexao;
    }

    @Override
    public Jogador salvar(Jogador jogador) {
        String sqlId = "SELECT SEQ_JOGADOR.NEXTVAL FROM DUAL";
        String sql = "INSERT INTO JOGADOR (ID, NOME, POSICAO, NUMERO, CLUBE) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement psId = conexao.prepareStatement(sqlId);
             ResultSet rs = psId.executeQuery()) {
            if (!rs.next()) {
                throw new SQLException("Nao foi possivel gerar o ID do jogador");
            }
            jogador.setId(rs.getLong(1));
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao gerar ID do jogador", e);
        }

        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            preencherDados(ps, jogador, 2);
            ps.setLong(1, jogador.getId());
            ps.executeUpdate();
            return jogador;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar jogador", e);
        }
    }

    @Override
    public Jogador buscarPorId(Long id) {
        String sql = "SELECT ID, NOME, POSICAO, NUMERO, CLUBE FROM JOGADOR WHERE ID = ?";
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapear(rs);
                }
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar jogador por id", e);
        }
    }

    @Override
    public List<Jogador> listarTodos() {
        String sql = "SELECT ID, NOME, POSICAO, NUMERO, CLUBE FROM JOGADOR ORDER BY ID";
        List<Jogador> jogadores = new ArrayList<>();
        try (PreparedStatement ps = conexao.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                jogadores.add(mapear(rs));
            }
            return jogadores;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar jogadores", e);
        }
    }

    @Override
    public Jogador atualizar(Jogador jogador) {
        if (jogador.getId() == null) {
            return salvar(jogador);
        }

        String sql = "UPDATE JOGADOR SET NOME = ?, POSICAO = ?, NUMERO = ?, CLUBE = ? WHERE ID = ?";
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            preencherDados(ps, jogador, 1);
            ps.setLong(5, jogador.getId());
            int linhas = ps.executeUpdate();
            if (linhas == 0) {
                throw new RuntimeException("Jogador nao encontrado para atualizacao: id=" + jogador.getId());
            }
            return jogador;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar jogador", e);
        }
    }

    @Override
    public void excluir(Long id) {
        String sql = "DELETE FROM JOGADOR WHERE ID = ?";
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir jogador", e);
        }
    }

    private void preencherDados(PreparedStatement ps, Jogador jogador, int inicio) throws SQLException {
        ps.setString(inicio, jogador.getNome());
        ps.setString(inicio + 1, jogador.getPosicao());
        ps.setInt(inicio + 2, jogador.getNumero());
        ps.setString(inicio + 3, jogador.getClube());
    }

    private Jogador mapear(ResultSet rs) throws SQLException {
        Jogador jogador = new Jogador();
        jogador.setId(rs.getLong("ID"));
        jogador.setNome(rs.getString("NOME"));
        jogador.setPosicao(rs.getString("POSICAO"));
        jogador.setNumero(rs.getInt("NUMERO"));
        jogador.setClube(rs.getString("CLUBE"));
        return jogador;
    }
}
