package com.example.tcc_barbearia_cliente;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    ImageView aliasTwitter, aliasInsta, aliasFace;
    EditText aliasEmail, aliasSenha;
    TextView aliasCadastro;
    CardView aliasLogin;
    ProgressBar progressBar;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        aliasLogin = findViewById(R.id.cardView);
        aliasEmail = findViewById(R.id.editText);
        aliasSenha = findViewById(R.id.editText2);
        aliasCadastro = findViewById(R.id.textView2);

        progressBar = (ProgressBar) findViewById(R.id.progressBarLogin);
        progressBar.setVisibility(View.INVISIBLE);

        aliasLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = aliasEmail.getText().toString().trim();
                String senha = aliasSenha.getText().toString().trim();
                login(email, senha);
            }
        });

        aliasCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Cadastro.class);
                startActivity(intent);
            }
        });

    }

    private void login(String email, String senha) {
        progressBar.setVisibility(View.VISIBLE);
        auth.signInWithEmailAndPassword(email, senha)
                .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Intent i=new Intent(MainActivity.this, InicialCliente.class);
                            startActivity(i);
                        }
                        else
                        {
                            Toast.makeText(MainActivity.this, "Usuário ou senha incorretos", Toast.LENGTH_SHORT).show();
                        }
                        progressBar.setVisibility(View.GONE);
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
        auth= Conexao.getFirebaseAuth();
    }


    private Context getContext(){return this;}
    private void alert(String s)
    {
        Toast.makeText(this,s, Toast.LENGTH_SHORT).show();
    }
}
