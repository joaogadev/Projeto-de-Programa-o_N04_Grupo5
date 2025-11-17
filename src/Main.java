import model.Usuario;
import service.EventoService;
import service.UsuarioService;
import ui.AgendaControl;
import ui.AgendaView;

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
        AgendaControl b = new AgendaControl(a, user);
    }
}