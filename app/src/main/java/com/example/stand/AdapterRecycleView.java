package com.example.stand;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterRecycleView extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<Cliente> clientes;
    private OnLongNoteListener mOnLongNoteListener;

    public AdapterRecycleView(Context context, ArrayList<Cliente> clientes,
                              OnLongNoteListener onLongNoteListener) {
        this.context = context;
        this.clientes = clientes;
        this.mOnLongNoteListener = onLongNoteListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View row = inflater.inflate(R.layout.cardview, parent, false);
        Item item = new Item(row, mOnLongNoteListener);
        return item;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        ((Item) holder).linearLayout.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_transition_anim));

        ((Item) holder).nome.setText(clientes.get(position).getNome());
        ((Item) holder).telefone.setText(clientes.get(position).getTelefone());
        ((Item) holder).data.setText(clientes.get(position).getData());
    }

    @Override
    public int getItemCount() {
        return clientes.size();
    }

    public class Item extends RecyclerView.ViewHolder implements View.OnLongClickListener {
        private TextView nome, telefone, data;
        private OnLongNoteListener onLongNoteListener;
        private LinearLayout linearLayout;

        public Item(View itemView, OnLongNoteListener onLongNoteListener) {
            super(itemView);
            nome = itemView.findViewById(R.id.txtNome);
            telefone = itemView.findViewById(R.id.txtCelular);
            data = itemView.findViewById(R.id.txtData);
            linearLayout = itemView.findViewById(R.id.meuItemRecycleView);

            this.onLongNoteListener = onLongNoteListener;
            itemView.setOnLongClickListener(this);
        }

        @Override
        public boolean onLongClick(View view) {
            onLongNoteListener.onLongNoteClick(getAdapterPosition());
            return false;
        }
    }

    public interface OnLongNoteListener {
        void onLongNoteClick(int position);
    }
}