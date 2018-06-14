package com.mandavitaque.economais.models;

/**
 * Created by vinic on 01/04/2018.
 */

public class Usuario {

    private int id;
    private String nome;
    private String email;
    private String password;


    public Usuario(int id, String nome, String email, String password) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.password = password;

    }

   public Usuario(String nome, String email, String password){
        this.nome = nome;
        this.email = email;
        this.password = password;
    }

    public Usuario(int id, String nome, String email) {
        this.id = id;
        this.nome = nome;
        this.email = email;

    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword(){ return password; }

}
