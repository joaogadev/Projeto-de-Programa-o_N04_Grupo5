package model;

public class Evento {
    private int id;
    private String titulo;
    private String descricao;



  public Evento(){
  }
  public Evento(int id, String titulo, String descricao, LocalDate data, LocalTime hora, String local, String categoria, String status, Usuario criador) {
      this.id = id;
      this.titulo = titulo;
      this.descricao = descricao;
      this.data = data;
      this.hora = hora;
      this.local = local;
      this.categoria = categoria;
      this.status = status;
      this.criador = criador;
    }
    
}
