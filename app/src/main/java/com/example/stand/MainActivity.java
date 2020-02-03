package com.example.stand;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button mbtnCadastrar, mbtnVerLista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mbtnCadastrar = findViewById(R.id.btnCadastrar);
        mbtnVerLista = findViewById(R.id.btnVerLista);

        mbtnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Cadastrar.class);
                startActivity(intent);
            }
        });

        mbtnVerLista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), VerLista.class);
                startActivity(intent);
            }
        });
    }
}
