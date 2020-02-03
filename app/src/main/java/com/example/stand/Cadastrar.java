package com.example.stand;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Cadastrar extends AppCompatActivity {

    private Button mbtnSalvar, mbtnCancelar;
    private EditText medtNome, medtCelular, medtCelularop;
    private Date date;
    private String dataTratada;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private Cliente cliente;
    private String formatoCelular;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar);

        mFirebaseDatabase = FirebaseDatabase.getInstance();


        mbtnSalvar = findViewById(R.id.btnSalvar);
        mbtnCancelar = findViewById(R.id.btnCancelar);

        medtCelularop = findViewById(R.id.edtCelularop);
        medtNome = findViewById(R.id.edtNome);
        medtCelular = findViewById(R.id.edtCelular);

        formatoCelular = "(NN) NNNNN-NNNN";
        SimpleMaskFormatter smf = new SimpleMaskFormatter(formatoCelular);
        MaskTextWatcher mtw = new MaskTextWatcher(medtCelular, smf);
        MaskTextWatcher mtw2 = new MaskTextWatcher(medtCelularop, smf);
        medtCelularop.addTextChangedListener(mtw2);
        medtCelular.addTextChangedListener(mtw);

        date = new Date();
        dataTratada = new SimpleDateFormat("HH:mm:ss_dd/MM/yyyy", Locale.getDefault()).format(date);

        mbtnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!medtNome.getText().toString().isEmpty()
                        && !medtCelular.getText().toString().isEmpty()) {
                    saveFirebase();
                    finish();
                } else
                    Toast.makeText(getApplicationContext(), "Há campos vazios", Toast.LENGTH_LONG).show();
            }
        });

        mbtnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void saveFirebase() {
        cliente = new Cliente(medtNome.getText().toString(),
                medtCelular.getText().toString(), dataTratada, date.toString(), medtCelularop.getText().toString());
        mDatabaseReference = mFirebaseDatabase.getReference("Clientes");
        DatabaseReference databaseReference = mDatabaseReference.child(date.toString());
        databaseReference.setValue(cliente);
    }
}
