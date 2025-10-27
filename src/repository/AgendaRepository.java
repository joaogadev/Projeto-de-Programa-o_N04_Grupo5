package repository;

import model.Evento;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AgendaRepository {
    private Connection con;

    public AgendaRepository(Connection con) {
        this.con = con;
    }

    /* verifica se já existe evento no mesmo local e mesmo horário d einício*/
    public boolean estaOcupado(LocalDateTime dataInicio, String local) throws SQLException{
        String sql = "SELECT Evento_id FROM Evento WHERE dataInicio = ? AND Local = ? AND status = 'Ativo'";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setTimestamp(1, Timestamp.valueOf(dataInicio)); //LocalDateTime pata Timestamp
            ps.setString(2, local);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        }
        return false;
    }

    /* retorna uma lista de eventos com conflito */
    public List<Evento> eventosConflitantes(LocalDateTime dataInicio, String local) throws SQLException{
        String sql = "SELECT Evento_id FROM Evento WHERE dataInicio = ? AND Local = ? AND status = 'Ativo'";
        List<Evento> cof = new ArrayList<>();
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setTimestamp(1, Timestamp.valueOf(dataInicio)); /* converte localdatetime pra sql */
            ps.setString(2, local);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Evento ev = new Evento();
                ev.setId(rs.getInt("Evento_id"));
                ev.setNome(rs.getString("nome"));
                // Converte Timestamp → LocalDateTime → LocalDate
                ev.setDataInicio(rs.getTimestamp("dataInicio").toLocalDateTime().toLocalDate());
                ev.setDataFim(rs.getTimestamp("dataFim").toLocalDateTime().toLocalDate());
                ev.setLocal(rs.getString("local"));
                ev.setCategoria(rs.getString("categoria"));
                ev.setStatus(rs.getString("status"));
                cof.add(ev);
            }
        }
        return cof;
    }
}
