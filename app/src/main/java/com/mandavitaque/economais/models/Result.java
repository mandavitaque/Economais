package com.mandavitaque.economais.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by vinic on 01/04/2018.
 */

public class Result {

    @SerializedName("error")
    private Boolean error;

    @SerializedName("message")
    private String message;

    @SerializedName("usuario")
    private Usuario usuario;

    @SerializedName("produto")
    private Produto produto;

    @SerializedName("prodMercado")
    private ProdMercado prodMercado;

    @SerializedName("categoria")
    private Categoria categoria;

    @SerializedName("mercado")
    private Mercado mercado;

    public Result(Boolean error, String message, Usuario usuario, Produto produto, ProdMercado prodMercado, Categoria categoria, Mercado mercado) {
        this.error = error;
        this.message = message;
        this.usuario = usuario;
        this.produto = produto;
        this.prodMercado = prodMercado;
        this.categoria = categoria;
        this.mercado = mercado;
    }

    public Boolean getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public Usuario getUser() {
        return usuario;
    }

    public Produto getProduto() { return produto; }

    public ProdMercado getProdMercado() {
        return prodMercado;
    }

    public Mercado getMercado() { return mercado; }
}
