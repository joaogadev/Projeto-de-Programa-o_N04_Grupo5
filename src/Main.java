import jdk.jfr.Event;
import model.Evento;
import model.Usuario;
import service.EventoService;
import service.UsuarioService;
import ui.AgendaView;

import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        EventoService eventoService = new EventoService();
        UsuarioService usuarioService = new UsuarioService();

        List<Usuario> usuarios = usuarioService.listarTodos();
        Usuario user = usuarios.get(7);

        // teste da ui
        AgendaView a = new AgendaView();

        //usuario não logado
        //AgendaControl ag = new AgendaControl(a);

        //usuario logado
        //AgendaControl b = new AgendaControl(a, user);

        // fitros de eventos
        /*
        String local = "São Paulo - SP";
        List<Evento> eventosLocal = eventoService.filtrarPorLocal(local);
        for(int i = 0; i < eventosLocal.size(); i++){
            System.out.println(eventosLocal.get(i).toString());
        }

        String categoria = "Tecnologia";
        List<Evento> eventosCategoria = eventoService.filtrarPorCategoria(categoria);
        for (Evento e : eventosCategoria){
            System.out.println(e.toString());
        }*/
    }
}