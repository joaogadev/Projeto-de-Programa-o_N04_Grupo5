// criar lembretes (agendar lembretes)
// metodo enviar notificação

package service;
import jdk.jfr.Event;
import model.Evento;
import repository.EventoRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class NotificacaoService {
    private final EventoRepository eventoRepository = new EventoRepository();

    public NotificacaoService(){}

    // lista de eventos nas próximas 24 horas
    public List<Evento> Eventos24horas(){
        List<Evento> eventosProximos = new ArrayList<>();
        List<Evento> todosEventos = eventoRepository.listarTodos();

        LocalDateTime agora = LocalDateTime.now();
        LocalDateTime maximo = agora.plusHours(24); // diferença máxima de 24 horas

        for(Evento e : todosEventos){
            // mesmo eventos que já começaram mas ainda não acabaram vão ser inseridos
            boolean dentrode24h = (e.getDataInicio().isAfter(agora) && e.getDataInicio().isBefore(maximo)) ||
                    (e.getDataInicio().isBefore(agora) && e.getDataFim().isAfter(agora));
            if (dentrode24h){
                eventosProximos.add(e);
            }
        }
        return eventosProximos;
    }
    public List<Evento> Eventos24horas(int usuario_id){
        List<Evento> eventosProximos = new ArrayList<>();
        List<Evento> todosEventos = eventoRepository.listarPorUsuario(usuario_id);

        LocalDateTime agora = LocalDateTime.now();
        LocalDateTime maximo = agora.plusHours(24); // diferença máxima de 24 horas

        for(Evento e : todosEventos){
            // mesmo eventos que já começaram mas ainda não acabaram vão ser inseridos
            boolean dentrode24h = (e.getDataInicio().isAfter(agora) && e.getDataInicio().isBefore(maximo)) ||
                    (e.getDataInicio().isBefore(agora) && e.getDataFim().isAfter(agora));
            if (dentrode24h){
                eventosProximos.add(e);
            }
        }
        return eventosProximos;
    }
    public String gerarNotificacao(){
        List<Evento> eventos = Eventos24horas();

        if (eventos.isEmpty()){
            return "Sem eventos nas próximas 24 horas";
        }
        StringBuilder eventosProximos = new StringBuilder();
        eventosProximos.append("Eventos nas próximas 24 horas\n\n");

        for(Evento e : eventos){
            eventosProximos.append("• ").append(e.getNome()).append("\n");
            eventosProximos.append(" Início: ").append(e.getDataInicio().toLocalDate()).append(" às ").append(e.getDataInicio().toLocalTime()).append("\n");
            eventosProximos.append(" Local: ").append(e.getLocal()).append("\n\n");
        }
        return eventosProximos.toString();
    }
    // gerar mensagem
    public String gerarNotificacao(int usuario_id){
        List<Evento> eventos = Eventos24horas(usuario_id);

        if (eventos.isEmpty()){
            return "Sem eventos nas próximas 24 horas";
        }
        StringBuilder eventosProximos = new StringBuilder();
        eventosProximos.append("Eventos nas próximas 24 horas\n\n");

        for(Evento e : eventos){
            eventosProximos.append("• ").append(e.getNome()).append("\n");
            eventosProximos.append(" Início: ").append(e.getDataInicio().toLocalDate()).append(" às ").append(e.getDataInicio().toLocalTime()).append("\n");
            eventosProximos.append(" Local: ").append(e.getLocal()).append("\n\n");
        }
        return eventosProximos.toString();
    }


    public void enviarNotificacao(){

    }

    public void criarLembretes(){
        
    }
}
