package service;

import model.Evento;
import repository.EventoRepository;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

public class EventoService {
    private final EventoRepository eventoRepository;

    public EventoService(){
        this.eventoRepository = new EventoRepository();
    }

    public boolean cadastrarEvento (Evento novoEvento, int usuarioId) {
        try {
            if (temConflito(novoEvento.getDataInicio(), novoEvento.getDataFim(), usuarioId)) {
                System.out.println("Confilito detectado: já existe um evento nesse período!");
                return false;
            }
            eventoRepository.salvar(novoEvento);
            System.out.println("Evento cadastrado com sucesso!");
            return true;
        } catch (Exception e) {
            System.err.println("Erro ao cadastrar evento: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @param dataInicio pega data de início de um novo evento a ser inserido para comprar com outros eventos de incrição
     * @param dataFim o mesmo mas para o fim
     */
    private boolean temConflito(LocalDateTime dataInicio, LocalDateTime dataFim, int usuarioId) {
        try {
            List<Evento> eventosUsuario = eventoRepository.listarPorUsuario(usuarioId);
            for (Evento e : eventosUsuario){
                boolean conflito = !(dataFim.isBefore(e.getDataInicio()) || dataInicio.isAfter(e.getDataFim()));
                if (conflito) {
                    return true;
                }
            }
        } catch (Exception e) {
            System.err.println("Erro ao verificar conflito de eventos: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public List<Evento> filtrarPorCategoria(String categoria) {
        List<Evento> filtrados = new ArrayList<>();
        try {
            for (Evento e : eventoRepository.listarTodos()) {
                if (e.getCategoria() != null && e.getCategoria().equalsIgnoreCase(categoria)) {
                    filtrados.add(e);
                }
            }
        } catch (Exception e) {
            System.err.println("Erro ao filtrar evento por categoria: " + e.getMessage());
            e.printStackTrace();
        }
        return filtrados;
    }

    public boolean removerEvento(int id) {
        try {
            return eventoRepository.remover(id);
        } catch (Exception e) {
            System.err.println("Erro ao remover evento: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public List<Evento> buscarPorData(Date data) {
        try {
            Timestamp dataTimestamp = new Timestamp(data.getTime());
            return eventoRepository.buscarPorData(dataTimestamp);
        } catch (Exception e) {
            System.err.println("Erro ao buscar evento por data: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}