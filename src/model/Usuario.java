package model;

public class Usuario {
  private int id;
  private String nome;
  private String email;
  private String senha;
  private Permissao permissao; // para acessos como ADMIN, VISUALIZADOR E USUARIO

  public Usuario(){
  }
  
  public Usuario(int id, String nome, String email, String senha, Permissao permissao) {
    this.id = id;
    this.nome = nome;
    this.email = email;
    this.senha = senha;
    this.permissao = permissao;
    }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public String getSenha() {
    return senha;
  }

  public void setSenha(String senha) {
    this.senha = senha;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Permissao getPermissao() {
    return permissao;
  }

  public void setPermissao(Permissao permissao) {
    this.permissao = permissao;
  }

  public String toString(){
        return "Usuário [Id: " + getId() + ", Nome: " + getNome()+ ", Email: " + getEmail() + "]";
  }
}
