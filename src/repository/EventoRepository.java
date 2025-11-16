package repository;
import model.Evento;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EventoRepository {

    // retorna todos os eventos
    public  List<Evento> findAll(){
        List<Evento> todosEventos = new ArrayList<>();
        String sql =  "select * from Evento order by dataInicio";

        try(Connection conexao = MyJDBC.getConnection(); PreparedStatement stmt = conexao.prepareStatement(sql); ResultSet rs = stmt.executeQuery()){

            while(rs.next()){
                todosEventos.add(mapResultSetEvento(rs));
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return todosEventos;
    }
    public void salvar(Evento evento) {
        String sql = "INSERT INTO Evento (nome, descricao, dataInicio, dataFim, local, categoria, status) " +
                "VALUES ()";
        try (Connection conexao = MyJDBC.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, evento.getNome());
            stmt.setString(2, evento.getDescricao());

            if (evento.getDataInicio() != null) {stmt.setTimestamp(3, java.sql.Timestamp.valueOf(evento.getDataInicio()));
            } else {stmt.setNull(3, Types.TIMESTAMP);}

            if (evento.getDataFim() != null) {stmt.setTimestamp(4, java.sql.Timestamp.valueOf(evento.getDataFim()));
            } else {stmt.setNull(4, java.sql.Types.DATE);}

            stmt.setString(5, evento.getLocal());
            stmt.setString(6, evento.getCategoria());
            stmt.setString(7, evento.getStatus());

            stmt.executeUpdate();
            System.out.println("Evento salvo com sucesso no Banco!");

        } catch (SQLException e) {
            System.err.println("Erro ao salvar evento: " + e.getMessage());
            e.printStackTrace();
        }
    }


    public boolean remover(int id) {
        String sql = "DELETE FROM Evento WHERE Evento_id = ?";

        try (Connection conexao = MyJDBC.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Evento removido com sucesso!");
                return true;
            } else {
                System.out.println("Nenhum evento encontrado com o ID informado.");
                return false;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao remover evento: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    public List<Evento> listarTodos() {return findAll();}

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
                eventos.add(mapResultSetEvento(rs));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar eventos do usuário: " + e.getMessage());
            e.printStackTrace();
        }
        return eventos;
    }

    public List<Evento> buscarPorData(java.sql.Timestamp data) {
        List<Evento> eventos = new ArrayList<>();
        String sql = "SELECT * FROM Evento WHERE DATE(dataInicio) = ?";

        try (Connection conn = MyJDBC.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setTimestamp(1, data);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                mapResultSetEvento(rs);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return eventos;
    }
    // mostrar todos os eventos apenas com filtro por dia
    public List<Evento>  encontrarDias(LocalDate data){
        List<Evento> eventos = new ArrayList<>();
        String sql = "SELECT * FROM Evento WHERE DATE(dataInicio) = ? ORDER BY dataInicio";

        try (Connection conn = MyJDBC.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, Date.valueOf(data));
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                eventos.add(mapResultSetEvento(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return eventos;
    }

    public List<Evento> econtrarSemanas(LocalDate data){
        List<Evento> eventos = new ArrayList<>();
        String sql = "SELECT * FROM Evento WHERE YEARWEEK(dataInicio, 1) = YEARWEEK(?, 1) ORDER BY dataInicio";

        try (Connection conn = MyJDBC.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, Date.valueOf(data));
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                eventos.add(mapResultSetEvento(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return eventos;
    }
    public List<Evento> econtrarMeses(int mes, int ano){
        List<Evento> eventos = new ArrayList<>();
        String sql = "SELECT * FROM Evento WHERE MONTH(dataInicio) = ? AND YEAR(dataInicio) = ? ORDER BY dataInicio";

        try (Connection conn = MyJDBC.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, mes);
            stmt.setInt(2, ano);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                eventos.add(mapResultSetEvento(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return eventos;
    }
    // metodo auxilar cadastro
    public Evento mapResultSetEvento(ResultSet rs) throws SQLException{
        Evento e = new Evento();
        e.setId(rs.getInt("Evento_id"));
        e.setAdmin(rs.getInt("Admin_id"));
        e.setNome(rs.getString("nome"));
        e.setDescricao(rs.getString("descricao"));
        e.setDataInicio(rs.getTimestamp("dataInicio").toLocalDateTime());
        e.setDataFim(rs.getTimestamp("dataFim").toLocalDateTime());
        e.setLocal(rs.getString("local"));
        e.setCategoria(rs.getString("categoria"));
        e.setStatus(rs.getString("status"));

        return e;
    }

}
