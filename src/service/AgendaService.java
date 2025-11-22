package service;

import jdk.jfr.Event;
import model.Evento;
import repository.EventoRepository;
import java.time.LocalDate;
import java.util.List;

public class AgendaService {
    private final EventoRepository eventoRepository;

    public AgendaService(){
        this.eventoRepository = new EventoRepository();
    }

    public List<Evento> mostrarAgenda() {
        return eventoRepository.findAll();
    }

    public List<Evento> filtrarPorDia(LocalDate data) {
        return eventoRepository.encontrarDias(data);
    }

    public List<Evento> filtrarPorSemana(LocalDate data) {
        return eventoRepository.econtrarSemanas(data);
    }

    public List<Evento> filtrarPorMes(int mes, int ano) {
        return eventoRepository.econtrarMeses(mes, ano);
    }

    public List<Evento> filtrarPorUsuario(int usuario_id){return eventoRepository.listarPorUsuario(usuario_id);}
    public List<Evento> listarInscricoesUsuario(LocalDate data, int usuarioId){return eventoRepository.encontrarInscricoes(data, usuarioId);};
    /*
    public static void main(String[] args) {
        AgendaService service = new AgendaService();

        System.out.println("==== Todos os eventos ====");
        service.mostrarAgenda().forEach(System.out::println);

        System.out.println("\n==== Eventos do dia ====");
        service.filtrarPorDia(LocalDate.of(2025, 11, 10)).forEach(System.out::println);

        System.out.println("\n==== Eventos semanais ====");
        service.filtrarPorSemana(LocalDate.of(2025, 11, 10)).forEach(System.out::println);

        System.out.println("\n==== Eventos do mês ====");
        service.filtrarPorMes(11, 2025).forEach(System.out::println);
    }*/
}