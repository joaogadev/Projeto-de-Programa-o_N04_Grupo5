package service;
import model.Evento;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EventoService {
    // permitir inscrição e cancelamento de participantes
    public List<Evento> listarTodos() {
        List<Evento> eventos = new ArrayList<>();
        String sql = "SELECT * FROM Evento";
        try (Connection conn = Conexao.getConexao();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Evento evento = new Evento();
                evento.setId(rs.getInt("Evento_id"));
                evento.setAdminId(rs.getInt("Admin_id"));
                evento.setNome(rs.getString("nome"));
                evento.setDescricao(rs.getString("descricao"));
                evento.setDataInicio(LocalDate.from(rs.getTimestamp("dataInicio").toLocalDateTime()));
                evento.setDataFim(LocalDate.from(rs.getTimestamp("dataFim").toLocalDateTime()));
                evento.setLocal(rs.getString("local"));
                evento.setCategoria(rs.getString("categoria"));
                evento.setStatus(rs.getString("status"));
                eventos.add(evento);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return eventos;
    }

    public List<Evento> listarPorDia(LocalDate dia) {
        List<Evento> eventos = new ArrayList<>();
        String sql = "SELECT * FROM Evento WHERE DATE(dataInicio) <= ? AND DATE(dataFim) >= ?";

        try (Connection conn = Conexao.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDate(1, Date.valueOf(dia));
            ps.setDate(2, Date.valueOf(dia));

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Evento evento = new Evento();
                evento.setId(rs.getInt("Evento_id"));
                evento.setAdminId(rs.getInt("Admin_id"));
                evento.setNome(rs.getString("nome"));
                evento.setDescricao(rs.getString("descricao"));
                evento.setDataInicio(LocalDate.from(rs.getTimestamp("dataInicio").toLocalDateTime()));
                evento.setDataFim(LocalDate.from(rs.getTimestamp("dataFim").toLocalDateTime()));
                evento.setLocal(rs.getString("local"));
                evento.setCategoria(rs.getString("categoria"));
                evento.setStatus(rs.getString("status"));
                eventos.add(evento);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return eventos;
    }
}
    // evitar conflitos de horários
    // filtrar eventos
    // listar eventos

