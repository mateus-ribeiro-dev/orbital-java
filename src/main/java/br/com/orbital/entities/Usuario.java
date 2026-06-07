package br.com.orbital.entities;

public class Usuario {

    private Long id;
    private String nome;
    private String email;
    private String organizacao;
    private String senha;

    public Usuario() {}

    public Usuario(Long id, String nome, String email, String organizacao, String senha) {
        this.id           = id;
        this.nome         = nome;
        this.email        = email;
        this.organizacao  = organizacao;
        this.senha        = senha;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOrganizacao() {
        return organizacao;
    }

    public void setOrganizacao(String organizacao) {
        this.organizacao = organizacao;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
