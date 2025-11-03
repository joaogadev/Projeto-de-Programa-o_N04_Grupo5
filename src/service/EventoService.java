package service;

import model.Evento;
import repository.EventoRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EventoService {
    private EventoRepository eventoRepository = new EventoRepository();

    public boolean cadastrarEvento(Evento novoEvento, int usuarioId) {
        try {
            if (temConflito(novoEvento.getDataInicio(), novoEvento.getDataFim(), usuarioId)) {
                System.out.println("Conflito detectado: já existe um evento nesse período para este usuário!");
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

    public boolean temConflito(LocalDate dataInicio, LocalDate dataFim, int usuarioId) {
        try {
            List<Evento> eventosUsuario = eventoRepository.listarPorUsuario(usuarioId);

            for (Evento e : eventosUsuario) {
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

    public List<Evento> listarEventos() {
        try {
            return eventoRepository.listarTodos();
        } catch (Exception e) {
            System.err.println("Erro ao listar eventos: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
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
            System.err.println("Erro ao filtrar eventos por categoria: " + e.getMessage());
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
}