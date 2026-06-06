package br.com.orbital.entities;

// Representa um evento de conjunção do Space-Track
// Usado como tabela de referência para validar NORAD IDs
public class Conjuncao {

    private Long id;
    private Long cdmId;
    private String tca;
    private Double minRng;       // distância mínima em metros
    private Double pc;           // probabilidade de colisão (pode ser nula)
    private Long sat1Id;         // NORAD ID do satélite 1
    private String sat1Name;
    private String sat1Type;
    private Long sat2Id;         // NORAD ID do satélite 2
    private String sat2Name;
    private String sat2Type;
    private String statusRisco;  // ok / warn / danger

    public Conjuncao() {}

    public Conjuncao(Long id, Long cdmId, String tca, Double minRng, Double pc, Long sat1Id, String sat1Name, String sat1Type, Long sat2Id, String sat2Name, String sat2Type, String statusRisco) {
        this.id = id;
        this.cdmId = cdmId;
        this.tca = tca;
        this.minRng = minRng;
        this.pc = pc;
        this.sat1Id = sat1Id;
        this.sat1Name = sat1Name;
        this.sat1Type = sat1Type;
        this.sat2Id = sat2Id;
        this.sat2Name = sat2Name;
        this.sat2Type = sat2Type;
        this.statusRisco = statusRisco;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCdmId() {
        return cdmId;
    }

    public void setCdmId(Long cdmId) {
        this.cdmId = cdmId;
    }

    public String getTca() {
        return tca;
    }

    public void setTca(String tca) {
        this.tca = tca;
    }

    public Double getMinRng() {
        return minRng;
    }

    public void setMinRng(Double minRng) {
        this.minRng = minRng;
    }

    public Double getPc() {
        return pc;
    }

    public void setPc(Double pc) {
        this.pc = pc;
    }

    public Long getSat1Id() {
        return sat1Id;
    }

    public void setSat1Id(Long sat1Id) {
        this.sat1Id = sat1Id;
    }

    public String getSat1Name() {
        return sat1Name;
    }

    public void setSat1Name(String sat1Name) {
        this.sat1Name = sat1Name;
    }

    public String getSat1Type() {
        return sat1Type;
    }

    public void setSat1Type(String sat1Type) {
        this.sat1Type = sat1Type;
    }

    public Long getSat2Id() {
        return sat2Id;
    }

    public void setSat2Id(Long sat2Id) {
        this.sat2Id = sat2Id;
    }

    public String getSat2Name() {
        return sat2Name;
    }

    public void setSat2Name(String sat2Name) {
        this.sat2Name = sat2Name;
    }

    public String getSat2Type() {
        return sat2Type;
    }

    public void setSat2Type(String sat2Type) {
        this.sat2Type = sat2Type;
    }

    public String getStatusRisco() {
        return statusRisco;
    }

    public void setStatusRisco(String statusRisco) {
        this.statusRisco = statusRisco;
    }
}
