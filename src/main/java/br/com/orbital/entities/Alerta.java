package br.com.orbital.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Alerta {

    private Long id;

    // front espera "sateliteNome"
    @JsonProperty("sateliteNome")
    private String sateliteNome;

    private String norad;

    // front espera "CRITICO" ou "ATENCAO"
    private String nivel;

    private String descricao;
    private double probabilidade;
    private String distancia;
    private String tempo;

    public Alerta() {}

    public Alerta(Long id, String sateliteNome, String norad, String nivel,
                  String descricao, double probabilidade, String distancia, String tempo) {
        this.id           = id;
        this.sateliteNome = sateliteNome;
        this.norad        = norad;
        this.nivel        = nivel;
        this.descricao    = descricao;
        this.probabilidade = probabilidade;
        this.distancia    = distancia;
        this.tempo        = tempo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSateliteNome() {
        return sateliteNome;
    }

    public void setSateliteNome(String sateliteNome) {
        this.sateliteNome = sateliteNome;
    }

    public String getNorad() {
        return norad;
    }

    public void setNorad(String norad) {
        this.norad = norad;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getProbabilidade() {
        return probabilidade;
    }

    public void setProbabilidade(double probabilidade) {
        this.probabilidade = probabilidade;
    }

    public String getDistancia() {
        return distancia;
    }

    public void setDistancia(String distancia) {
        this.distancia = distancia;
    }

    public String getTempo() {
        return tempo;
    }

    public void setTempo(String tempo) {
        this.tempo = tempo;
    }
}
