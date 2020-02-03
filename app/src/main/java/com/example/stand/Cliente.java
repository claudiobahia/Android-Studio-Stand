package com.example.stand;

public class Cliente {
    private String nome, telefone, data, id, telefoneOpcional;

    public Cliente(String nome, String telefone, String data, String id, String telefoneOpcional) {
        this.nome = nome;
        this.telefone = telefone;
        this.data = data;
        this.id = id;
        this.telefoneOpcional = telefoneOpcional;
    }

    public String getTelefoneOpcional() {
        return telefoneOpcional;
    }

    public String getNome() {
        return nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getData() {
        return data;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return getNome() + "\n" +
                getTelefone() + "\n" +
                getData() + "\n" +
                getTelefoneOpcional() + "\n" +
                getId();
    }
}
