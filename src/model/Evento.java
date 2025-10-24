package model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;


public class Evento {
    private int id;
    private String nome;
    private String descricao;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private LocalTime horarioInicio;
    private LocalTime horarioFim;
    private String local;
    private String categoria;
    private Usuario admin;
    private String status;



  public Evento(){
  }
  public Evento(int id, String nome, String descricao, LocalDate dataInicio, LocalDate dataFim,LocalTime horarioInicio,LocalTime horarioFim, String local, String categoria, String status, Usuario admin) {
      this.id = id;
      this.nome = nome;
      this.descricao = descricao;
      this.dataInicio = dataInicio;
      this.dataFim = dataFim;
      this.horarioInicio = horarioInicio;
      this.horarioFim = horarioFim;
      this.local = local;
      this.categoria = categoria;
      this.status = status;
      this.admin = admin;
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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
    }

    public LocalTime getHorarioInicio(){
        return horarioInicio;
    }

    public void setHorarioInicio(LocalTime horarioInicio){
        this.horarioInicio = horarioInicio;
    }

    public LocalTime getHorarioFim(){
        return horarioFim;
    }

    public void setHorarioFim(LocalTime horarioFim){
        this.horarioFim = horarioFim;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Usuario getAdmin() {
        return admin;
    }

    public void setAdmin(Usuario admin) {
        this.admin = admin;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void exibirEvento(){
        System.out.println("Id evento: "+getId());
        System.out.println("Nome do evento: "+getNome());
        System.out.println("Descrição do evento: "+getDescricao());
        System.out.println("Data de início: "+getDataInicio());
        System.out.println("Data de termino: "+getDataFim());
        System.out.println("Local do evento: "+getLocal());
        System.out.println("Categoria: "+getCategoria());
        System.out.println("Status: "+getStatus());
    }
}
