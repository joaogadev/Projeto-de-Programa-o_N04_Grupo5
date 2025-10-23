// mostrar agenda -- FEITO (ou não)
// lidar com os filtros de dia, semana e mês
// conetar com evento service para puxar os dados

package service;

import java.util.List;
import model.AgendaView;
import model.Evento;
import model.Usuario;

public class AgendaService {

    private EventoService eventoService;

    public AgendaService(EventoService eventoService) {
        this.eventoService = eventoService;
    }

    public AgendaView mostrarAgenda(Usuario usuario) throws Exception { //Mostrando a agenda do usuario
        List<Evento> eventos = eventoService.listarEventosDoUsuario(usuario);

        if (eventos == null || eventos.isEmpty()) {
            throw new Exception("A agenda está vazia.");
        }

        return new AgendaView(usuario, eventos);
    }

}
