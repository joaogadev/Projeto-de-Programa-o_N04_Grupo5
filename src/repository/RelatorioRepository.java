package repository;

import model.Evento;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RelatorioRepository {
    private EventoRepository eventoRepository;

    public RelatorioRepository(){
        this.eventoRepository = new EventoRepository();
    }

    public List<Evento> eventosRelatorio(int usuario_id){
        List<Evento> eventosVisitados = new ArrayList<>();
        String sql = """
            SELECT e.Evento_id, e.Admin_id, e.nome, e.descricao, e.dataInicio, e.dataFim, e.local, e.categoria, e.status
            FROM HistoricoEventos h
            JOIN Evento e ON h.Evento_id = e.Evento_id
            WHERE h.Usuario_id = ? 
              AND h.status = 1
              AND e.dataInicio >= DATE_SUB(CURDATE(), INTERVAL 7 DAY)
            ORDER BY e.dataInicio DESC;
        """;

        try (Connection conexao = MyJDBC.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, usuario_id);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                eventosVisitados.add(eventoRepository.mapResultSetEvento(rs));
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return eventosVisitados;
    }
}
