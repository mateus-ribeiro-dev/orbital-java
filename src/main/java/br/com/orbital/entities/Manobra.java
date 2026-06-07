package br.com.orbital.entities;

public class Manobra {

    private String janelaExecucao;
    private double deltaV;
    private String objetoRisco;
    private String descricao;

    public Manobra() {}

    public Manobra(String janelaExecucao, double deltaV,
                   String objetoRisco, String descricao) {
        this.janelaExecucao = janelaExecucao;
        this.deltaV         = deltaV;
        this.objetoRisco    = objetoRisco;
        this.descricao      = descricao;
    }

    public String getJanelaExecucao() {
        return janelaExecucao;
    }

    public void setJanelaExecucao(String janelaExecucao) {
        this.janelaExecucao = janelaExecucao;
    }

    public double getDeltaV() {
        return deltaV;
    }

    public void setDeltaV(double deltaV) {
        this.deltaV = deltaV;
    }

    public String getObjetoRisco() {
        return objetoRisco;
    }

    public void setObjetoRisco(String objetoRisco) {
        this.objetoRisco = objetoRisco;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
