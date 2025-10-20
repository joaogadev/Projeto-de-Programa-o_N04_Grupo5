package model;
import java.util.List;

public class AgendaView { //somente os dados - a parte gráfica estará na /ui
    private Usuario usuario;
    private List<Evento> eventos;

    public AgendaView() {
    }

    public AgendaView(Usuario usuario, List<Evento> eventos) {
        this.usuario = usuario;
        this.eventos = eventos;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Evento> getEventos() {
        return eventos;
    }

    public void setEventos(List<Evento> eventos) {
        this.eventos = eventos;
    }
}
