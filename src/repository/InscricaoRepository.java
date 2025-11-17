package repository;

import model.Evento;
import model.Inscricao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InscricaoRepository {
    private final EventoRepository eventoRepository;

    public InscricaoRepository(){
        this.eventoRepository = new EventoRepository();
    }

    // listar eventos em que o usuário está inscrito com status Confirmado
    public List<Evento> listarPorUsuario(int usuarioId) {
        List<Evento> eventos = new ArrayList<>();

        String sql = """
                SELECT e.Evento_id,
                    e.Admin_id,
                    e.nome ,
                    e.descricao,
                    e.dataInicio,
                    e.dataFim,
                    e.local,
                    e.categoria,
                    i.status
                FROM Inscricao i
                JOIN Evento e ON i.Evento_id = e.Evento_id
                WHERE i.Usuario_id = ? and i.status = "Confirmado";
                """;

        try (Connection conexao = MyJDBC.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, usuarioId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                eventos.add(eventoRepository.mapResultSetEvento(rs));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar eventos do usuário: " + e.getMessage());
            e.printStackTrace();
        }
        return eventos;
    }
}
