package service;

import model.Evento;
import repository.MyJDBC;
import java.sql.*;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AgendaService {

    public List<Evento> mostrarAgenda() {
        List<Evento> eventos = new ArrayList<>();
        String sql = "SELECT * FROM Evento ORDER BY dataInicio";

        try (Connection conn = MyJDBC.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Evento evento = new Evento();
                evento.setId(rs.getInt("Evento_id"));
                evento.setNome(rs.getString("nome"));
                evento.setDescricao(rs.getString("descricao"));
                evento.setLocal(rs.getString("local"));
                evento.setCategoria(rs.getString("categoria"));
                evento.setStatus(rs.getString("status"));
                evento.setDataInicio(rs.getTimestamp("dataInicio").toLocalDateTime());
                evento.setDataFim(rs.getTimestamp("dataFim").toLocalDateTime());
                eventos.add(evento);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return eventos;
    }

    public List<Evento> filtrarPorDia(LocalDate data) {
        List<Evento> eventos = new ArrayList<>();
        String sql = "SELECT * FROM Evento WHERE DATE(dataInicio) = ? ORDER BY dataInicio";

        try (Connection conn = MyJDBC.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, Date.valueOf(data));
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Evento evento = new Evento();
                evento.setId(rs.getInt("Evento_id"));
                evento.setNome(rs.getString("nome"));
                evento.setDescricao(rs.getString("descricao"));
                evento.setLocal(rs.getString("local"));
                evento.setCategoria(rs.getString("categoria"));
                evento.setStatus(rs.getString("status"));
                evento.setDataInicio(rs.getTimestamp("dataInicio").toLocalDateTime());
                evento.setDataFim(rs.getTimestamp("dataFim").toLocalDateTime());
                eventos.add(evento);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return eventos;
    }

    public List<Evento> filtrarPorSemana(LocalDate data) {
        List<Evento> eventos = new ArrayList<>();
        String sql = "SELECT * FROM Evento WHERE YEARWEEK(dataInicio, 1) = YEARWEEK(?, 1) ORDER BY dataInicio";

        try (Connection conn = MyJDBC.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, Date.valueOf(data));
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Evento evento = new Evento();
                evento.setId(rs.getInt("Evento_id"));
                evento.setNome(rs.getString("nome"));
                evento.setDescricao(rs.getString("descricao"));
                evento.setLocal(rs.getString("local"));
                evento.setCategoria(rs.getString("categoria"));
                evento.setStatus(rs.getString("status"));
                evento.setDataInicio(rs.getTimestamp("dataInicio").toLocalDateTime());
                evento.setDataFim(rs.getTimestamp("dataFim").toLocalDateTime());
                eventos.add(evento);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return eventos;
    }

    public List<Evento> filtrarPorMes(int mes, int ano) {
        List<Evento> eventos = new ArrayList<>();
        String sql = "SELECT * FROM Evento WHERE MONTH(dataInicio) = ? AND YEAR(dataInicio) = ? ORDER BY dataInicio";

        try (Connection conn = MyJDBC.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, mes);
            stmt.setInt(2, ano);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Evento evento = new Evento();
                evento.setId(rs.getInt("Evento_id"));
                evento.setNome(rs.getString("nome"));
                evento.setDescricao(rs.getString("descricao"));
                evento.setLocal(rs.getString("local"));
                evento.setCategoria(rs.getString("categoria"));
                evento.setStatus(rs.getString("status"));
                evento.setDataInicio(rs.getTimestamp("dataInicio").toLocalDateTime());
                evento.setDataFim(rs.getTimestamp("dataFim").toLocalDateTime());
                eventos.add(evento);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return eventos;
    }

    public static void main(String[] args) {
        AgendaService service = new AgendaService();

        System.out.println("==== TODOS OS EVENTOS ====");
        service.mostrarAgenda().forEach(System.out::println);

        System.out.println("\n==== EVENTOS DO DIA ====");
        service.filtrarPorDia(LocalDate.of(2025, 11, 10)).forEach(System.out::println);

        System.out.println("\n==== EVENTOS DA SEMANA ====");
        service.filtrarPorSemana(LocalDate.of(2025, 11, 10)).forEach(System.out::println);

        System.out.println("\n==== EVENTOS DO MÊS ====");
        service.filtrarPorMes(12, 2025).forEach(System.out::println);
    }
}