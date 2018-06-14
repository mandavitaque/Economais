package com.mandavitaque.economais.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mandavitaque.economais.R;
import com.mandavitaque.economais.api.APIService;
import com.mandavitaque.economais.api.APIUrl;
import com.mandavitaque.economais.models.Produto;
import com.mandavitaque.economais.models.Result;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CadastroProdutoActivity extends AppCompatActivity {

    private EditText nomeProduto;
    private EditText pesoProduto;
    private EditText categoriaProduto;
    private String codBarra;
    private Button btnCadastroProduto;


    private void criarProduto() {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Criando produto...");
        progressDialog.show();

        //getting the user values
        String nome = nomeProduto.getText().toString().trim();
        String peso = pesoProduto.getText().toString().trim();
        String codbarra = codBarra;
        int categoria = Integer.parseInt(categoriaProduto.getText().toString());


        //building retrofit object
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //Defining retrofit api service
        APIService service = retrofit.create(APIService.class);

        //Definindo objeto usuário pra passar com a chamada
        Produto produto = new Produto(nome, peso, codbarra, categoria);

        //definindo a chamada
        Call<Result> call = service.criarProduto(
                produto.getNome(),
                produto.getPeso(),
                produto.getcodBarra(),
                produto.getIdCategoria()
        );

        //chamando a api
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                //hiding progress dialog
                progressDialog.dismiss();

                //displaying the message from the response as toast
                Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();

                //if there is no error
                if (!response.body().getError()) {
                    Toast.makeText(getApplicationContext(), "funcionou!!!!", Toast.LENGTH_LONG).show();
                    nomeProduto.setText("");
                    pesoProduto.setText("");
                    categoriaProduto.setText("");
                    finish();

                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_produto);

        setTitle("Cadastrar Produto");
        Bundle extras = getIntent().getExtras();
        if (extras != null){
            codBarra = extras.getString("prodCodBarra");
        }

        nomeProduto = findViewById(R.id.txtNomeProdutoID);
        pesoProduto = findViewById(R.id.txtPesoID);
        categoriaProduto = findViewById(R.id.txtCategoriaID);
        btnCadastroProduto = findViewById(R.id.btnCadastrarProdutoId);

        btnCadastroProduto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                criarProduto();
            }
        });


    }
}
