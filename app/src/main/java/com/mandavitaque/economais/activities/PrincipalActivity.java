package com.mandavitaque.economais.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import com.mandavitaque.economais.adapter.CategoriaAdapter;
import com.mandavitaque.economais.adapter.ProdMercadoAdapter;
import com.mandavitaque.economais.api.APIService;
import com.mandavitaque.economais.api.APIUrl;
import com.mandavitaque.economais.models.Categorias;
import com.mandavitaque.economais.models.ProdMercados;
import com.mandavitaque.economais.models.Produto;


import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.mandavitaque.economais.R;
import com.mandavitaque.economais.helper.SharedPrefManager;
import com.mandavitaque.economais.models.Result;

import java.util.concurrent.TimeUnit;

import static android.support.design.widget.Snackbar.LENGTH_LONG;

public class PrincipalActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, SwipeRefreshLayout.OnRefreshListener {

    SwipeRefreshLayout mSwipeRefreshLayout;
    private TextView txtNomeUsuario;
    private TextView txtEmailUsuario;
    private RecyclerView listaProdutos;
    private ImageView fotoPerfil;
    private RecyclerView.Adapter adapter;
    private View view;
    private RecyclerView listaCategoria;
    private RecyclerView.Adapter adapterCategoria;

    public void listaCategoria(){


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //Defining retrofit api service
        APIService service = retrofit.create(APIService.class);

        Call<Categorias> call = service.getCategorias();

        call.enqueue(new Callback<Categorias>() {
            @Override
            public void onResponse(Call<Categorias> call, Response<Categorias> response) {
                adapterCategoria = new CategoriaAdapter(response.body().getCategorias(), getApplicationContext());
                listaCategoria.setAdapter(adapterCategoria);


            }

            @Override
            public void onFailure(Call<Categorias> call, Throwable t) {
                abreSnack(view, "Erro ao conectar", Snackbar.LENGTH_INDEFINITE);

            }
        });
    }

    public void listaProdutos(){


        mSwipeRefreshLayout.setRefreshing(true);
        //building retrofit object
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //Defining retrofit api service
        APIService service = retrofit.create(APIService.class);

        Call<ProdMercados> call = service.getProdMercados();

        call.enqueue(new Callback<ProdMercados>(){
            @Override
            public void onResponse(Call<ProdMercados> call, Response<ProdMercados> response){
               // progressDialog.dismiss();
                mSwipeRefreshLayout.setRefreshing(false);
                adapter = new ProdMercadoAdapter(response.body().getProdMercados(), getApplicationContext());
                listaProdutos.setAdapter(adapter);
                abreSnack(view, "Sucesso!", Snackbar.LENGTH_INDEFINITE);

            }

            @Override
            public void onFailure(Call<ProdMercados> call, Throwable t) {
                mSwipeRefreshLayout.setRefreshing(false);
                abreSnack(view, "Erro ao conectar", Snackbar.LENGTH_INDEFINITE);
            }
        });
    }

    //Consultar Produto

    public void consultaProduto(String msg){
        final String codBarra = msg;
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Verificando...");
        progressDialog.show();

        //building retrofit object
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //Defining retrofit api service
        APIService service = retrofit.create(APIService.class);

        Produto produto = new Produto(codBarra);

        //defining the call
        Call<Result> call = service.getProdutoCodBarra(
                produto.getcodBarra()
        );

        //calling the api
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {

                progressDialog.dismiss();
                //displaying the message from the response as toast
                Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                if (!response.body().getError()) {
                    String prodNome = response.body().getProduto().getNome();
                    String prodPeso = response.body().getProduto().getPeso();
                    int prodCategoria = response.body().getProduto().getIdCategoria();
                    int prodID = response.body().getProduto().getId();
                    Intent atualiza = new Intent(PrincipalActivity.this, AtualizaPrecoActivity.class);
                    atualiza.putExtra("prodNome", prodNome);
                    atualiza.putExtra("prodPeso", prodPeso);
                    atualiza.putExtra("prodCategoria", prodCategoria);
                    atualiza.putExtra("prodID", prodID);
                    startActivity(atualiza);

                }
                else {
                    Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                    Intent cadastra = new Intent(PrincipalActivity.this, CadastroProdutoActivity.class);
                    cadastra.putExtra("prodCodBarra", codBarra);
                    startActivity(cadastra);
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                abreSnack(view, "Erro ao conectar", Snackbar.LENGTH_INDEFINITE);
            }


        });
    }

    public void abreSnack(View viewSnack, String mensagemSnack, int duracaoSnack){

        final Snackbar snackbar = Snackbar
                .make(viewSnack, mensagemSnack, duracaoSnack);
        snackbar.setActionTextColor(getResources().getColor(R.color.primaryLightColor));

        snackbar.setAction("DISMISS", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snackbar.dismiss();
            }
        });
        snackbar.show();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        final Activity activity = this;
        //verificando se usuário está logado
        if (!SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        //Definindo varíaveis de botões
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefreshID);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark);
        view = findViewById(R.id.layoutPrincipal);

        setTitle("Economais");
        listaCategoria = findViewById(R.id.listaCategoriasID);
        listaCategoria.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        listaCategoria.setLayoutManager(layoutManager);
        listaCategoria();
        listaProdutos = findViewById(R.id.listaProdutosID);
        listaProdutos.setHasFixedSize(true);
        listaProdutos.setLayoutManager(new LinearLayoutManager(this));
        listaProdutos();


        //Pegando valores salvos do usuário
        SharedPreferences pref = getSharedPreferences("economaissharedpref", Context.MODE_PRIVATE);
        String username = pref.getString("keyusername", "");
        String email = pref.getString("keyuseremail", "");



        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Botão de ação
        //Ao clicar no botão, começa a fazer a leitura de código de barras
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator integrator = new IntentIntegrator(activity);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.PRODUCT_CODE_TYPES);
                integrator.setPrompt("Scaneie o Produto!");
                integrator.setCameraId(0);
                integrator.initiateScan();
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        //Setando o nome e o e-mail do usuário na barra de navegação
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        txtEmailUsuario = headerView.findViewById(R.id.txtBemVindo);
        txtNomeUsuario = headerView.findViewById(R.id.txtNomeUsuario);
        fotoPerfil = headerView.findViewById(R.id.fotoPerfilID);
        txtEmailUsuario.setText(email);
        txtNomeUsuario.setText(username);

        fotoPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PrincipalActivity.this, PerfilUsuarioActivity.class));
            }
        });

    }


    //Resultado do leitor de código de barras
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() != null) {
                alert(result.getContents());
            }else {
                alert("Scan cancelado");
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }

    }

    //resultado da leitura, faz a consulta para ver se o produto existe
    private void alert(String msg){
        if (msg.equals("Scan cancelado")){
            Toast.makeText(this, "Scan cancelado", Toast.LENGTH_LONG).show();
        }
        else {
            consultaProduto(msg);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //Deslogar o usuário
    private void logout() {
        SharedPrefManager.getInstance(this).logout();
        finish();
        startActivity(new Intent(this, LoginActivity.class));
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_mercados) {
            startActivity(new Intent(this, LocalizacaoActivity.class));
        } else if (id == R.id.nav_carrinho) {

        } else if (id == R.id.nav_relatorio) {

        } else if (id == R.id.nav_locais) {

        } else if (id == R.id.nav_configuracoes) {

        }
          else if (id == R.id.nav_logout) {
            logout();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onRefresh() {
        listaProdutos();
    }
}

