package service;
import model.Evento;
import java.time.LocalDate;
import java.util.List;

public class AgendaService {
    private final EventoService eventoService;
    public AgendaService(EventoService eventoService){
        this.eventoService = eventoService;
    }

    // mostrar agenda
    public List<Evento> mostrarAgenda() {
        return eventoService.listarTodos();
    }
    // lidar com os filtros de dia, semana e mês
    public List<Evento> filtrarPorDia(LocalDate dia) {
        return eventoService.listarPorDia(dia);
    }

    // conetar com evento service para puxar os dados

}
