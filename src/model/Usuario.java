package model;

public class Usuario {
  private int id;
  private String nome;
  private String email;
  private String senha;
  private Permissao permissao; // para acessos como ADMIN, VISUALIZADOR E CLIENTE

  public Usuario(){
  }
  
  public Usuario(int id, String nome, String email, String senha, Permissao permissao) {
    this.id = id;
    this.nome = nome;
    this.email = email;
    this.senha = senha;
    this.permissao = permissao;
    }
  
}
