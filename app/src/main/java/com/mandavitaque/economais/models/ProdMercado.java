package com.mandavitaque.economais.models;

public class ProdMercado {

    private int idProduto;
    private int idMercado;
    private float preco;
    private String nomeMercado;
    private String nomeProduto;
    private String pesoProduto;
    private float precoProduto;

    public ProdMercado(int idProduto, int idMercado, float preco) {
        this.idMercado = idMercado;
        this.idProduto = idProduto;
        this.preco = preco;
    }

    public ProdMercado(String nomeProduto, String pesoProduto, float precoProduto, String nomeMercado){
        this.nomeProduto = nomeProduto;
        this.pesoProduto = pesoProduto;
        this.precoProduto = precoProduto;
        this.nomeMercado = nomeMercado;
    }

    public int getIdProduto() {
        return idProduto;
    }

    public int getIdMercado() {
        return idMercado;
    }

    public float getPreco() {
        return preco;
    }

    public String getNomeProduto(){ return nomeProduto;}

    public String getPesoProduto(){ return pesoProduto;}

    public float getPrecoProduto(){ return precoProduto;}

    public String getNomeMercado() { return nomeMercado; }
}
