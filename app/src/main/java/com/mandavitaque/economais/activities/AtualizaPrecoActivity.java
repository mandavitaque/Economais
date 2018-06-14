package com.mandavitaque.economais.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.mandavitaque.economais.R;
import com.mandavitaque.economais.adapter.MercadosArrayAdapter;
import com.mandavitaque.economais.api.APIService;
import com.mandavitaque.economais.api.APIUrl;
import com.mandavitaque.economais.helper.SharedPrefManager;
import com.mandavitaque.economais.models.Mercado;
import com.mandavitaque.economais.models.Mercados;
import com.mandavitaque.economais.models.ProdMercado;
import com.mandavitaque.economais.models.Produto;
import com.mandavitaque.economais.models.Result;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class AtualizaPrecoActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private String prodNome;
    private String prodPeso;
    private int prodCategoria;
    private int prodID;
    private Spinner spMercados;
    private TextView txtProdNomeAtu;
    private TextView txtProdPesoAtu;
    private TextView txtProdCategoriaAtu;
    private EditText txtNovoPreco;
    private SpinnerAdapter adapter;
    private int mercID = 0;
    private Button btnAtualizaPreco;
    private List<ArrayList<Mercado>> spinnerData;

    private void populaSpinner(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //Defining retrofit api service
        APIService service = retrofit.create(APIService.class);

        Call<Mercados> call = service.getMercados();

        call.enqueue(new Callback<Mercados>() {
            @Override
            public void onResponse(Call<Mercados> call, Response<Mercados> response) {

                   adapter = new MercadosArrayAdapter(response.body().getMercados(), getApplicationContext());
                   spMercados.setAdapter(adapter);

                }



            @Override
            public void onFailure(Call<Mercados> call, Throwable t) {

            }
        });



    }

    private void AtualizaPrecoProd(){

       float novoPreco = Float.parseFloat(txtNovoPreco.getText().toString());
       int IDMerc = (mercID);

        //building retrofit object
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //Defining retrofit api service
        APIService service = retrofit.create(APIService.class);

        ProdMercado prodmercado = new ProdMercado(prodID, IDMerc, novoPreco );

        Call<Result> call = service.atualizarPreco(
                prodmercado.getIdProduto(),
                prodmercado.getIdMercado(),
                prodmercado.getPreco()
        );

        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {


                //displaying the message from the response as toast
                Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();

                //if there is no error
                if (!response.body().getError()) {
                    //starting activity principal
                    Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                    finish();

                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atualiza_preco);

        txtProdNomeAtu = findViewById(R.id.txtNomeProdAtuID);
        txtProdCategoriaAtu = findViewById(R.id.txtPesoProdAtuID);
        txtProdPesoAtu = findViewById(R.id.txtCategoriaProdAtuID);
        txtNovoPreco = findViewById(R.id.txtPrecoProdID);
        btnAtualizaPreco = findViewById(R.id.btnAtualizaPrecoId);
        spMercados = findViewById(R.id.spMercadosID);
        spMercados.setOnItemSelectedListener(this);


        Bundle extras = getIntent().getExtras();
        if (extras != null){
            prodNome = extras.getString("prodNome");
            prodPeso = extras.getString("prodPeso");
            prodCategoria = extras.getInt("prodCategoria");
            prodID = extras.getInt("prodID");
        }


        txtProdPesoAtu.setText("Peso(g): " + prodPeso);
        txtProdCategoriaAtu.setText("Categoria: " + prodCategoria);
        txtProdNomeAtu.setText("Nome do Produto: " + prodNome);
        populaSpinner();


        btnAtualizaPreco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(txtNovoPreco.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Insira um preço!", Toast.LENGTH_LONG).show();

                }
                else
                    AtualizaPrecoProd();
            }
        });



    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
       Mercado mercado = (Mercado)parent.getItemAtPosition(position);
        mercID =  mercado.getId();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
