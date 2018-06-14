package com.mandavitaque.economais.api;

import com.mandavitaque.economais.models.Categorias;
import com.mandavitaque.economais.models.Mercado;
import com.mandavitaque.economais.models.Mercados;
import com.mandavitaque.economais.models.ProdMercados;
import com.mandavitaque.economais.models.Produtos;
import com.mandavitaque.economais.models.Result;
import com.mandavitaque.economais.models.Usuario;
import com.mandavitaque.economais.models.Usuarios;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
/**
 * Created by vinic on 01/04/2018.
 */

public interface APIService {

    @FormUrlEncoded
    @POST("register")
    Call<Result> createUser(
            @Field("nome") String nome,
            @Field("email") String email,
            @Field("password") String password);


    @FormUrlEncoded
    @POST("login")
    Call<Result> userLogin(
            @Field("email") String email,
            @Field("password") String password
    );


    @GET("usuarios")
    Call<Usuarios> getUsers();

    @FormUrlEncoded
    @POST("update/{id}")
    Call<Result> updateUser(
            @Path("id") int id,
            @Field("nome") String nome,
            @Field("email") String email,
            @Field("password") String password
    );

    @GET("produtos")
    Call<Produtos>getProdutos();

    @FormUrlEncoded
    @POST("produtocodbarra")
    Call<Result> getProdutoCodBarra(
            @Field("codBarra") String codBarra);

    @FormUrlEncoded
    @POST("registerProduto")
    Call<Result> criarProduto(
            @Field("nome") String nome,
            @Field("peso") String peso,
            @Field("codBarra") String codBarra,
            @Field("idCategoria") int idCategoria);

    @FormUrlEncoded
    @POST("atualizarPreco")
    Call<Result>atualizarPreco(
            @Field("idProduto") int idProduto,
            @Field("idMercado") int idMercado,
            @Field("preco") float preco);

    @GET("produtosByPreco")
    Call<ProdMercados>getProdMercados();

    @GET("categorias")
    Call<Categorias>getCategorias();

    @GET("mercados")
    Call<Mercados>getMercados();
}
