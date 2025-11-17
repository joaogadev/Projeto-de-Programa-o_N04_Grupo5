package service;

import model.Evento;
import model.Inscricao;
import repository.InscricaoRepository;

import java.util.List;
import service.EventoService;

public class InscricoesService {
    InscricaoRepository inscricaoRepository;
    EventoService es;


    public boolean conflitoEvento(int userId) {
        List<Evento> eventos = inscricaoRepository.listarPorUsuario(userId);

        for (int i = 0; i < eventos.size(); i++) {
            Evento e1 = eventos.get(i);
            for (int j = i + 1; j < eventos.size(); j++) {
                Evento e2 = eventos.get(j);

                // Verifica sobreposição de datas
                boolean conflito =
                        !(e1.getDataFim().isBefore(e2.getDataInicio()) ||
                                e1.getDataInicio().isAfter(e2.getDataFim()));

                if (conflito) {
                    return true;
                }
            }
        }

        return false; // nenhum conflito encontrado
    }

}
