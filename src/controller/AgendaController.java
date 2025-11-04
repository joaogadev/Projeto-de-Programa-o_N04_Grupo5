package controller;

 import model.Evento;
 import repository.AgendaRepository;
 import java.sql.Connection;
 import java.sql.DriverManager;
 import java.sql.SQLException;
 import java.time.LocalDate;
 import java.time.LocalDateTime;
 import java.util.List;

public class AgendaController {
    private AgendaRepository agendaRepository;

    public AgendaController(AgendaRepository agendaRepository) {
        this.agendaRepository = agendaRepository;
    }


    public void tentarAdicionarEvento(Evento newEvento) {
        try {
            //converte localdate pata localdatetime(armengo)
            LocalDateTime dataInicioDateTime = newEvento.getDataInicio();
            //Verifica se já existe evento no mesmo local e data
            boolean ocu = agendaRepository.estaOcupado(dataInicioDateTime, newEvento.getLocal());
            if (ocu) {
                //lista eventos conflitantes
                List<Evento> conflitos = agendaRepository.eventosConflitantes(dataInicioDateTime, newEvento.getLocal());
                System.out.println("Conflito detectado!\n" +
                        newEvento.getNome() + "não pode ser adicionado, já exite outro no mesmo local e horário");
                for (Evento evento : conflitos) {
                    System.out.println("- " + evento.getNome() + ", Local: " + evento.getLocal() + ", Inicio: " + evento.getDataInicio());
                }
            } else {
                System.out.println("Nenhum conflito encontrado!");
            }
        } catch (Exception e) {
            System.err.println("Erro ao verificar conflitos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sistemaeventos", "root", "joao@2020");
        AgendaRepository repo = new AgendaRepository(conn);
        AgendaController controller = new AgendaController(repo);

        Evento novoEvento = new Evento();
        novoEvento.setNome("Workshop de Criatividade");
        novoEvento.setLocal("São Paulo - SP");
        novoEvento.setDataInicio(LocalDateTime.of(2025, 11, 10,9,0));
        novoEvento.setDataFim(LocalDateTime.of(2025, 11, 10,17,0));
        novoEvento.setCategoria("Tecnologia");
        novoEvento.setStatus("Planejado");

        controller.tentarAdicionarEvento(novoEvento);
    }
}
