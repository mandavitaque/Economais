package com.mandavitaque.economais.models;

public class Categoria {
    int id;
    String nome;

    public Categoria(int id, String nome){
        this.id = id;
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }
}
