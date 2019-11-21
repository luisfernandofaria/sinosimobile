package com.lf.sinosinovo.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Municipio implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private Integer id;
    private String nome;
    private String uf;
    private Integer codigo;

    public Municipio() {
    }

    public Municipio(Integer id, String descricao, String uf, Integer codigoIbge) {
        this.id = id;
        this.nome = descricao;
        this.uf = uf;
        this.codigo = codigoIbge;
    }

    public float getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    @Override
    public String toString() {
        return "Municipio{" +
                "nome='" + nome + '\'' +
                '}';
    }
}
