package com.lf.sinosinovo.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;

@Entity
public class Denuncia implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private Integer id; //adicionar depois
    private LocalAcidente localAcidente;
    private String descricao= "descricao";
    private Date dataDenuncia;
    private String caminhoDaFoto = "caminho";
    private String autorDano = "não identificado";
    private String emailUsuario = "email@email.com";
    private String categoria = "a";

    public Denuncia(LocalAcidente localAcidente, Date data, String autorDano, String categoria) {
        this.localAcidente = localAcidente;
        this.dataDenuncia = data;
        this.autorDano = autorDano;
        this.categoria = categoria;
    }

    public Denuncia() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalAcidente getLocalAcidente() {
        return localAcidente;
    }

    public void setLocalAcidente(LocalAcidente localAcidente) {
        this.localAcidente = localAcidente;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Date getDataDenuncia() {
        return dataDenuncia;
    }

    public void setDataDenuncia(Date dataDenuncia) {
        this.dataDenuncia = dataDenuncia;
    }

    public String getCaminhoDaFoto() {
        return caminhoDaFoto;
    }

    public void setCaminhoDaFoto(String caminhoDaFoto) {
        this.caminhoDaFoto = caminhoDaFoto;
    }

    public String getAutorDano() {
        return autorDano;
    }

    public void setAutorDano(String autorDano) {
        this.autorDano = autorDano;
    }

    public String getEmailUsuario() {
        return emailUsuario;
    }

    public void setEmailUsuario(String emailUsuario) {
        this.emailUsuario = emailUsuario;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    @Override
    public String toString() {
        return "Denuncia{" +
                "id=" + id +
                ", localAcidente=" + localAcidente +
                ", descricao='" + descricao + '\'' +
                ", dataDenuncia=" + dataDenuncia +
                ", caminhoDaFoto='" + caminhoDaFoto + '\'' +
                ", autorDano='" + autorDano + '\'' +
                ", emailUsuario='" + emailUsuario + '\'' +
                ", categoria='" + categoria + '\'' +
                '}';
    }
}
