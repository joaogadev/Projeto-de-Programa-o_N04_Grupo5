package repository;

import model.Evento;
import model.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
    public String inscrever(Evento evento, Usuario usuario) {
        String sql = "INSERT INTO inscricao " +
                "(Usuario_id, Evento_id, status, dataInscricao, descricao, tipo) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = MyJDBC.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, usuario.getId());
            stmt.setInt(2, evento.getId());
            stmt.setString(3, "INSCRITO");  // exemplo de status
            stmt.setDate(4, new java.sql.Date(System.currentTimeMillis()));
            stmt.setString(5, "Inscrição pelo sistema");
            stmt.setString(6, "NORMAL");

            stmt.executeUpdate();
            return "A inscrição foi bem-sucedida!";

        } catch (SQLException e) {
            e.printStackTrace();
            return "Ocorreu um erro na inscrição ";
        }
    }

}
