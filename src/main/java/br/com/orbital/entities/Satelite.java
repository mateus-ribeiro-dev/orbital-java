package br.com.orbital.entities;

public class Satelite {
    private Long id;
    private String nomeSatelite;
    private String noradId;
    private String cosparId;
    private String orbita;
    private double altitude;
    private double combustivel;
    private double inclinacao;
    private String proximaJanela;
    private double probColisao;
    private double deltaV;
    private String statusRisco;

    public Satelite() {}

    public Satelite(Long id, String nomeSatelite, String noradId, String cosparId, String orbita, double altitude, double combustivel, double inclinacao, String proximaJanela, double probColisao, double deltaV, String statusRisco) {
        this.id = id;
        this.nomeSatelite = nomeSatelite;
        this.noradId = noradId;
        this.cosparId = cosparId;
        this.orbita = orbita;
        this.altitude = altitude;
        this.combustivel = combustivel;
        this.inclinacao = inclinacao;
        this.proximaJanela = proximaJanela;
        this.probColisao = probColisao;
        this.deltaV = deltaV;
        this.statusRisco = statusRisco;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeSatelite() {
        return nomeSatelite;
    }

    public void setNomeSatelite(String nomeSatelite) {
        this.nomeSatelite = nomeSatelite;
    }

    public String getNoradId() {
        return noradId;
    }

    public void setNoradId(String noradId) {
        this.noradId = noradId;
    }

    public String getCosparId() {
        return cosparId;
    }

    public void setCosparId(String cosparId) {
        this.cosparId = cosparId;
    }

    public String getOrbita() {
        return orbita;
    }

    public void setOrbita(String orbita) {
        this.orbita = orbita;
    }

    public double getAltitude() {
        return altitude;
    }

    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }

    public double getCombustivel() {
        return combustivel;
    }

    public void setCombustivel(double combustivel) {
        this.combustivel = combustivel;
    }

    public double getInclinacao() {
        return inclinacao;
    }

    public void setInclinacao(double inclinacao) {
        this.inclinacao = inclinacao;
    }

    public String getProximaJanela() {
        return proximaJanela;
    }

    public void setProximaJanela(String proximaJanela) {
        this.proximaJanela = proximaJanela;
    }

    public double getProbColisao() {
        return probColisao;
    }

    public void setProbColisao(double probColisao) {
        this.probColisao = probColisao;
    }

    public double getDeltaV() {
        return deltaV;
    }

    public void setDeltaV(double deltaV) {
        this.deltaV = deltaV;
    }

    public String getStatusRisco() {
        return statusRisco;
    }

    public void setStatusRisco(String statusRisco) {
        this.statusRisco = statusRisco;
    }
}
