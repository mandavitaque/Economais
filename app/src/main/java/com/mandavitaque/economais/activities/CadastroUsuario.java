package com.mandavitaque.economais.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.mandavitaque.economais.R;
import com.mandavitaque.economais.helper.SharedPrefManager;
import com.mandavitaque.economais.api.APIService;
import com.mandavitaque.economais.api.APIUrl;
import android.app.ProgressDialog;
import android.widget.Toast;
import com.mandavitaque.economais.models.Result;
import com.mandavitaque.economais.models.Usuario;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class CadastroUsuario extends AppCompatActivity {

    private Button btnCadastro;
    private Button btnCancelar;
    private EditText txtEmail;
    private EditText txtSenha;
    private EditText txtNome;
    private AlertDialog.Builder confirmacao;


    private void criarUsuario(){

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Criando conta...");
        progressDialog.show();

        //getting the user values
        String nome = txtNome.getText().toString().trim();
        String email = txtEmail.getText().toString().trim();
        String password = txtSenha.getText().toString().trim();


        //building retrofit object
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //Defining retrofit api service
        APIService service = retrofit.create(APIService.class);

        //Definindo objeto usuário pra passar com a chamada
        Usuario usuario = new Usuario(nome, email, password);

        //definindo a chamada
        Call<Result> call = service.createUser(
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getPassword()
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
                    //starting activity principal
                    SharedPrefManager.getInstance(getApplicationContext()).userLogin(response.body().getUser());
                    Intent intent = new Intent(CadastroUsuario.this, PrincipalActivity.class);
                    startActivity(intent);
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
        setContentView(R.layout.activity_cadastro_usuario);

        btnCadastro = findViewById(R.id.btnCadastrarId);
        btnCancelar = findViewById(R.id.btnCancelarID);
        txtEmail = findViewById(R.id.txtEmailID);
        txtSenha = findViewById(R.id.txtSenhaID);
        txtNome = findViewById(R.id.txtNomeID);


        btnCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                criarUsuario();
            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                confirmacao = new AlertDialog.Builder(CadastroUsuario.this);
                confirmacao.setTitle("Confirmar a ação");
                confirmacao.setMessage("Deseja realmente cancelar?");
                confirmacao.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                confirmacao.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(CadastroUsuario.this, LoginActivity.class);
                        startActivity(intent);
                        finish();

                    }
                });
                confirmacao.create();
                confirmacao.show();

            }
        });
    }
}
