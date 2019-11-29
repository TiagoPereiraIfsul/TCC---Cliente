package com.example.tcc_barbearia_cliente;

import java.io.Serializable;

public class ModeloBarbeiro implements Serializable {

    private String _id;
    private String nome;
    private String foto;


    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    @Override
    public String toString() {
        return nome;
    }
}
