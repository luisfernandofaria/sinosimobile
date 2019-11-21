package com.lf.sinosinovo.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class LocalAcidente implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private Integer id;
    private String latitude;
    private String longitude;
    private String endereco;
    private Municipio municipio;
    private String cep;

    public LocalAcidente() {
    }

    public LocalAcidente(Integer id, String latitude, String longitude, String endereco, Municipio municipio, String cep) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.endereco = endereco;
        this.municipio = municipio;
        this.cep = cep;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public Municipio getMunicipio() {
        return municipio;
    }

    public void setMunicipio(Municipio municipio) {
        this.municipio = municipio;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    @Override
    public String toString() {
        return "LocalAcidente{" +
                "id=" + id +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", endereco='" + endereco + '\'' +
                ", municipio=" + municipio +
                ", cep='" + cep + '\'' +
                '}';
    }
}
