package model;

import java.time.LocalDate;

public class Notificacao {
    private int id;
    private Usuario usuario;
    private Evento evento;
    private String descricao;
    private LocalDate horarioEnvio;
    private boolean enviada;

    public Notificacao() {
    }

    public Notificacao(int id, Usuario usuario, Evento evento, String descricao, LocalDate horarioEnvio, boolean enviada) {
        this.id = id;
        this.usuario = usuario;
        this.evento = evento;
        this.descricao = descricao;
        this.horarioEnvio = horarioEnvio;
        this.enviada = enviada;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public LocalDate getHorarioEnvio() {
        return horarioEnvio;
    }

    public void setHorarioEnvio(LocalDate horarioEnvio) {
        this.horarioEnvio = horarioEnvio;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public boolean isEnviada() {
        return enviada;
    }

    public void setEnviada(boolean enviada) {
        this.enviada = enviada;
    }
}
