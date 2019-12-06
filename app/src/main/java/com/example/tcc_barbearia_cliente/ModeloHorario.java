package com.example.tcc_barbearia_cliente;

import java.io.Serializable;

public class ModeloHorario implements Serializable {

    private String _id;
    //private String nome;
    //private String foto;
    private String barba;
    private String cabelo;
    private String sobrancelha;
    private String horario;
    private String valorTotal;
    private String data;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getBarba() {
        return barba;
    }

    public void setBarba(String barba) {
        this.barba = barba;
    }

    public String getCabelo() {
        return cabelo;
    }

    public void setCabelo(String cabelo) {
        this.cabelo = cabelo;
    }

    public String getSobrancelha() {
        return sobrancelha;
    }

    public void setSobrancelha(String sobrancelha) {
        this.sobrancelha = sobrancelha;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(String valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
