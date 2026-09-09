package com.fiap.singleton;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConexaoSingleton {
    private static final String URL = "jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL";
    private static final String USUARIO = "RMXXXXXX";
    private static final String SENHA = "XXXXXX";

    private static ConexaoSingleton instancia;
    private final Connection conexao;

    private ConexaoSingleton() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            this.conexao = DriverManager.getConnection(URL, USUARIO, SENHA);
            garantirEstrutura();
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException("Nao foi possivel obter a conexao com o banco", e);
        }
    }

    public static synchronized ConexaoSingleton getInstancia() {
        if (instancia == null) {
            instancia = new ConexaoSingleton();
        }
        return instancia;
    }

    public Connection getConexao() {
        return conexao;
    }

    private void garantirEstrutura() throws SQLException {
        try (Statement st = conexao.createStatement()) {
            if (!existe(st, "USER_TABLES", "TABLE_NAME", "JOGADOR")) {
                st.executeUpdate(
                        "CREATE TABLE JOGADOR (" +
                                "ID NUMBER PRIMARY KEY, " +
                                "NOME VARCHAR2(100) NOT NULL, " +
                                "POSICAO VARCHAR2(50) NOT NULL, " +
                                "NUMERO NUMBER(3) NOT NULL, " +
                                "CLUBE VARCHAR2(80) NOT NULL)");
            }
            if (!existe(st, "USER_SEQUENCES", "SEQUENCE_NAME", "SEQ_JOGADOR")) {
                st.executeUpdate("CREATE SEQUENCE SEQ_JOGADOR START WITH 1 INCREMENT BY 1");
            }
        }
    }

    private boolean existe(Statement st, String visao, String coluna, String nome) throws SQLException {
        String sql = "SELECT COUNT(*) FROM " + visao + " WHERE " + coluna + " = '" + nome + "'";
        try (ResultSet rs = st.executeQuery(sql)) {
            return rs.next() && rs.getInt(1) > 0;
        }
    }
}
