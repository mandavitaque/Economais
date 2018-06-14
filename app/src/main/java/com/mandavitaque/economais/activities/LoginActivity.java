package com.mandavitaque.economais.activities;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.mandavitaque.economais.R;
import com.mandavitaque.economais.api.APIService;
import com.mandavitaque.economais.api.APIUrl;
import android.app.ProgressDialog;
import com.mandavitaque.economais.models.Result;
import com.mandavitaque.economais.helper.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class LoginActivity extends AppCompatActivity {

    private Button btnLogin;
    private TextView txtCadastro;
    private EditText txtLogin;
    private EditText txtSenha;
    private Switch swManterLogado;

    //área de criação de funções


    private void abreTelaCadastroUsuario()
    {
        Intent intent = new Intent(LoginActivity.this, CadastroUsuario.class);
        startActivity(intent);
        finish();
    }

    private void loginUsuario() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Fazendo Login...");
        progressDialog.show();

        String email = txtLogin.getText().toString().trim();
        String password = txtSenha.getText().toString().trim();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService service = retrofit.create(APIService.class);


        Call<Result> call = service.userLogin(email, password);

        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                progressDialog.dismiss();

                Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();

                    if (!response.body().getError()) {

                        SharedPrefManager.getInstance(getApplicationContext()).userLogin(response.body().getUser());
                        Intent intent = new Intent(LoginActivity.this, PrincipalActivity.class);
                        startActivity(intent);
                        finish();


                    } else {
                       // View contextView = findViewById(R.id.activity_login);
                       // Snackbar.make(contextView, "E-mail ou senha invalida", Snackbar.LENGTH_LONG).show();
                        //Toast.makeText(getApplicationContext(), "Invalid email or password", Toast.LENGTH_LONG).show();
                    }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show()
                ;
            }
        });
    }
    //fim da área de criação de funções


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //verificando se usuário está logado
        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, PrincipalActivity.class));
        }


        btnLogin = findViewById(R.id.btnLogin);
        txtCadastro = findViewById(R.id.txtCadastro);
        txtLogin = findViewById(R.id.txtLogin);
        txtSenha = findViewById(R.id.txtSenha);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUsuario();
            }
        });

        txtCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abreTelaCadastroUsuario();

            }
        });
    }

    }


