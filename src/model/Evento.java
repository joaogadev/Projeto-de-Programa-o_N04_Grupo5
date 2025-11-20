package model;
import java.time.LocalDateTime;


public class Evento {
    private int id;
    private int admi_id;
    private String nome;
    private String descricao;
    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;
    private String local;
    private String categoria;
    private String status;

  public Evento(){
  }
  public Evento(int id, String nome, String descricao, LocalDateTime dataInicio, LocalDateTime dataFim, String local, String categoria, String status, int admin) {
      this.id = id;
      this.nome = nome;
      this.descricao = descricao;
      this.dataInicio = dataInicio;
      this.dataFim = dataFim;
      this.local = local;
      this.categoria = categoria;
      this.status = status;
      this.admi_id = admin;
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

    public LocalDateTime getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDateTime dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDateTime getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDateTime dataFim) {
        this.dataFim = dataFim;
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

    public int getAdmin() {
        return admi_id;
    }

    public void setAdmin(int admin) {
        this.admi_id = admin;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    @Override
    public String toString() {
        return "\n--- EVENTO ---" +
                "\nID: " + id +
                "\nadmi_id: " + admi_id +
                "\nNome: " + nome +
                "\nDescrição: " + descricao +
                "\nLocal: " + local +
                "\nCategoria: " + categoria +
                "\nStatus: " + status +
                "\nInício: " + dataInicio +
                "\nFim: " + dataFim +
                "\n----------------------------";
    }
}
