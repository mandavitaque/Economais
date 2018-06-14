package com.mandavitaque.economais.models;

public class Mercado {

    private int id;
    private int distancia;
    private String nome;
    private String endereco;
    private String cnpj;
    private String latitude;
    private String longitude;

    public Mercado(int id, String nome, String endereco, String cnpj, String latitude, String longitude) {
        this.id = id;
        this.nome = nome;
        this.endereco = endereco;
        this.cnpj = cnpj;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Mercado(int id, String nome, String endereco, String cnpj, String latitude, String longitude, int distancia) {
        this.id = id;
        this.nome = nome;
        this.endereco = endereco;
        this.cnpj = cnpj;
        this.latitude = latitude;
        this.longitude = longitude;
        this.distancia = distancia;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getCnpj() { return cnpj; }

    public String getEndereco() { return endereco; }

    public String getLatitude() { return latitude; }

    public String getLongitude() { return longitude; }

    public int getDistancia(){return distancia;}

    public void setDistancia(int distanciaMercado){
        distancia = distanciaMercado;
    }
}
