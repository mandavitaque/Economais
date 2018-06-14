package com.mandavitaque.economais.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.mandavitaque.economais.R;
import com.mandavitaque.economais.api.APIService;
import com.mandavitaque.economais.api.APIUrl;
import com.mandavitaque.economais.helper.SharedPrefManager;
import com.mandavitaque.economais.models.Result;
import com.mandavitaque.economais.models.Usuario;
import com.tapadoo.alerter.Alerter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PerfilUsuarioActivity extends AppCompatActivity {

    private EditText nomeUsuario;
    private EditText emailUsuario;
    private EditText senhaUsuario;
    private ImageButton editarUsuario;
    private Button salvarEdicao;
    private Button btnTeste;

    private void setControles(boolean status){
        nomeUsuario.setFocusable(status);
        nomeUsuario.setFocusableInTouchMode(status);
        nomeUsuario.setClickable(status);
        emailUsuario.setClickable(status);
        emailUsuario.setClickable(status);
        emailUsuario.setFocusableInTouchMode(status);
        senhaUsuario.setFocusableInTouchMode(status);
        senhaUsuario.setFocusable(status);
        senhaUsuario.setClickable(status);
    }

    private void salvarEdicao(){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Atualizando...");
        progressDialog.show();

        String nome = nomeUsuario.getText().toString().trim();
        String email = emailUsuario.getText().toString().trim();
        String senha = senhaUsuario.getText().toString().trim();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService service = retrofit.create(APIService.class);

        Usuario usuario = new Usuario(SharedPrefManager.getInstance(getApplicationContext()).getUser().getId(), nome, email, senha);

        Call<Result> call = service.updateUser(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getPassword());
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                if (!response.body().getError()) {
                    SharedPrefManager.getInstance(getApplicationContext()).userLogin(response.body().getUser());
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
        setContentView(R.layout.activity_perfil_usuario);
        setTitle("Editar Perfil");

        SharedPreferences pref = getSharedPreferences("economaissharedpref", Context.MODE_PRIVATE);
        final String username = pref.getString("keyusername", "");
        final String email = pref.getString("keyuseremail", "");
        int id = pref.getInt("keyuserid", 0);

        nomeUsuario = findViewById(R.id.txtPerNomeUsuarioID);
        emailUsuario = findViewById(R.id.txtPerEmailUsuarioID);
        senhaUsuario = findViewById(R.id.txtPerSenhaUsuarioID);
        editarUsuario = findViewById(R.id.btnEditarPerfilID);
        salvarEdicao = findViewById(R.id.btnSalvarEdicaoID);
        btnTeste = findViewById(R.id.btnTesteID);


        nomeUsuario.setText(username);
        emailUsuario.setText(email);
        setControles(false);

        editarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvarEdicao.setVisibility(View.VISIBLE);
                setControles(true);

            }
        });

        btnTeste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Alerter.create(PerfilUsuarioActivity.this)
                        .setTitle(username)
                        .setText(email)
                        .show();
            }
        });

        salvarEdicao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvarEdicao();
            }
        });


    }
}
