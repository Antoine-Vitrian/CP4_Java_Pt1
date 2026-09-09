package com.fiap.model;

import java.util.Objects;

public class Jogador {
    private Long id;
    private String nome;
    private String posicao;
    private int numero;
    private String clube;

    public Jogador() {
    }

    public Jogador(String nome, String posicao, int numero, String clube) {
        this.nome = nome;
        this.posicao = posicao;
        this.numero = numero;
        this.clube = clube;
    }

    public Jogador(Long id, String nome, String posicao, int numero, String clube) {
        this.id = id;
        this.nome = nome;
        this.posicao = posicao;
        this.numero = numero;
        this.clube = clube;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPosicao() {
        return posicao;
    }

    public void setPosicao(String posicao) {
        this.posicao = posicao;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getClube() {
        return clube;
    }

    public void setClube(String clube) {
        this.clube = clube;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Jogador jogador = (Jogador) o;
        return numero == jogador.numero &&
                Objects.equals(id, jogador.id) &&
                Objects.equals(nome, jogador.nome) &&
                Objects.equals(posicao, jogador.posicao) &&
                Objects.equals(clube, jogador.clube);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, posicao, numero, clube);
    }

    @Override
    public String toString() {
        return "Jogador{id=" + id +
                ", nome='" + nome + '\'' +
                ", posicao='" + posicao + '\'' +
                ", numero=" + numero +
                ", clube='" + clube + '\'' +
                '}';
    }
}
