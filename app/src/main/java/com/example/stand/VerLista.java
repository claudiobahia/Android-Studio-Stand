package com.example.stand;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Random;

public class VerLista extends AppCompatActivity implements AdapterRecycleView.OnLongNoteListener {

    private Button mbtnSortear;
    private TextView mtxtNumero;
    private RecyclerView recyclerView;
    private ArrayList<Cliente> clientes = new ArrayList<>();
    private AdapterRecycleView adapterRecycleView;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private Dialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_lista);

        mDialog = new Dialog(this);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference("Clientes");

        mbtnSortear = findViewById(R.id.btnSortear);
        mtxtNumero = findViewById(R.id.txtNumero);
        recyclerView = findViewById(R.id.recycleView);
        recyclerView.setLayoutManager(new WrapContentLinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));

        clientes = loadFirebase();

        adapterRecycleView = new AdapterRecycleView(getApplicationContext(), clientes, this);
        recyclerView.setAdapter(adapterRecycleView);
    }

    protected void ajustarNAss() {
        mtxtNumero.setText(String.format("%s%d", "Número de cadastrados: ", clientes.size()));
    }

    private ArrayList<Cliente> loadFirebase() {
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                clientes.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Cliente cliente = new Cliente(postSnapshot.child("nome").getValue().toString(),
                            postSnapshot.child("telefone").getValue().toString(),
                            postSnapshot.child("data").getValue().toString(),
                            postSnapshot.child("id").getValue().toString(),
                            postSnapshot.child("telefoneOpcional").getValue().toString());
                    clientes.add(cliente);
                    adapterRecycleView.notifyItemInserted(clientes.indexOf(cliente));
                    adapterRecycleView.notifyItemRangeChanged(clientes.indexOf(cliente), clientes.size());
                }
                adapterRecycleView.notifyDataSetChanged();
                ajustarNAss();
                sortearVisible(mbtnSortear);
                if (clientes.size() == 0) {
                    sortearInvisible(mbtnSortear);
                    Toast.makeText(getApplicationContext(), "EMPTY FIREBASE DATABASE!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        return clientes;
    }

    @Override
    public void onLongNoteClick(final int position) {
        System.out.println(position);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Deseja excluir o item?")
                .setCancelable(false)
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Cliente cliente = clientes.get(position);
                        clientes.remove(position);

                        adapterRecycleView.notifyItemRangeChanged(0, clientes.size() + 1);
                        adapterRecycleView.notifyItemRemoved(position);

                        mDatabaseReference.child(cliente.getId()).removeValue();
                        ajustarNAss();

                        Toast.makeText(getApplicationContext(), "Dado removido.", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void Popup(View v) {
        TextView txtPopNome, txtPopCelular, txtPopHora, txtPopData;
        ImageView gif;
        Button btnLigar;

        mDialog.setContentView(R.layout.popup);
        txtPopNome = mDialog.findViewById(R.id.txtPopNome);
        txtPopCelular = mDialog.findViewById(R.id.txtPopCelular);
        txtPopHora = mDialog.findViewById(R.id.txtPopHora);
        txtPopData = mDialog.findViewById(R.id.txtPopData);
        btnLigar = mDialog.findViewById(R.id.btnLigar);
        gif = mDialog.findViewById(R.id.imgGiff);

        btnLigar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo LIGAR numero
                Toast.makeText(getApplicationContext(), "Ligando.", Toast.LENGTH_SHORT).show();
            }
        });
        String l1, l2, l3, l4, gifDaVez = "";
        l1 = "https://media.giphy.com/media/YRuFixSNWFVcXaxpmX/200w_d.gif";
        l2 = "https://media.giphy.com/media/xTiTnk7cRvop40CYLu/200w_d.gif";
        l3 = "https://media.giphy.com/media/l49JCSwMXyxHnYJws/200w_d.gif";
        l4 = "https://media.giphy.com/media/10XCfc3wkj2rD2/200w_d.gif";

        switch (new Random().nextInt(4)) {
            case 0:
                gifDaVez = l1;
                break;
            case 1:
                gifDaVez = l2;
                break;
            case 2:
                gifDaVez = l3;
                break;
            case 3:
                gifDaVez = l4;
                break;
        }

        Glide.with(getApplicationContext())
                .load(gifDaVez)
                .into(gif);

        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mDialog.show();

        int random = new Random().nextInt(clientes.size());
        String hora, data;
        Cliente c = clientes.get(random);
        System.out.println("kaka " + c);
        hora = c.getData().split("_")[0];
        data = c.getData().split("_")[1];

        txtPopNome.setText(String.format("Nome: %s", c.getNome()));
        txtPopCelular.setText(String.format("Telefone: %s %s", c.getTelefone(), c.getTelefoneOpcional()));
        txtPopHora.setText(String.format("Hora: %s", hora));
        txtPopData.setText(String.format("Data: %s", data));
    }

    private void sortearVisible(View v) {
        v.setVisibility(View.VISIBLE);
    }

    private void sortearInvisible(View v) {
        v.setVisibility(View.GONE);
    }
}
