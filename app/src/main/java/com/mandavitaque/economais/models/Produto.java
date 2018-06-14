package com.mandavitaque.economais.models;

public class Produto {

    private int id;
    private String nome;
    private String peso;
    private String codBarra;
    private int idCategoria;


    public Produto(int id, String nome, String peso, String codBarra, int idCategoria) {
        this.id = id;
        this.nome = nome;
        this.peso = peso;
        this.codBarra = codBarra;
        this.idCategoria = idCategoria;
    }

    public Produto(String nome, String peso, String codBarra, int idCategoria){
        this.nome = nome;
        this.peso = peso;
        this.codBarra = codBarra;
        this.idCategoria = idCategoria;
    }

    public Produto(String codBarra){
        this.codBarra = codBarra;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getPeso() {
        return peso;
    }

    public String getcodBarra(){
        return codBarra;
    }

    public int getIdCategoria(){ return  idCategoria;}
}