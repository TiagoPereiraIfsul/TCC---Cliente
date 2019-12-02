package com.example.tcc_barbearia_cliente;

import java.io.Serializable;
import java.util.Date;

public class ModeloEstilo implements Serializable {
    private String id;
    private String nome;
    private String categoria;
    private String barbeiro;
    private String foto;
    private String horario;
    private String cabelo;
    private String barba;
    private String sobrancelha;
    private String data;
    private String valorTotal;
    //private String HorariosDisponiveis;

    public ModeloEstilo(){}

    public ModeloEstilo(String nome) {
        this.nome = nome;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getBarbeiro() {
        return barbeiro;
    }

    public void setBarbeiro(String barbeiro) {
        this.barbeiro = barbeiro;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return nome;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getCabelo() {
        return cabelo;
    }

    public void setCabelo(String cabelo) {
        this.cabelo = cabelo;
    }

    public String getBarba() {
        return barba;
    }

    public void setBarba(String barba) {
        this.barba = barba;
    }

    public String getSobrancelha() {
        return sobrancelha;
    }

    public void setSobrancelha(String sobrancelha) {
        this.sobrancelha = sobrancelha;
    }

    public String getValorTotal() {

        int v = 0;

        if(sobrancelha != null && !sobrancelha.equals("Nenhum")) {
            v += 5;
        }

        if(cabelo != null && !cabelo.equals("Nenhum")) {
            v += 25;
        }

        if(barba != null && !barba.equals("Nenhum")) {
            v += 10;
        }

        return String.valueOf(v);
    }

    public void setValorTotal(String valorTotal) {
        this.valorTotal = valorTotal;
    }


}
