package repository;
import jdk.jfr.Event;
import model.Evento;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
//achar todos os eventos no banco de dados
public class EventoRepository {
    public static List<Evento> buscarEventos(){
        List<Evento> todosEventos = new ArrayList<>();
        String sql =  "select * from Evento";
        try(Connection conexao = MyJDBC.getConnection(); PreparedStatement stmt = conexao.prepareStatement(sql); ResultSet resultSet = stmt.executeQuery()){
            while(resultSet.next()){
                var evento = new Evento();
                evento.setId(resultSet.getInt("Evento_id"));
                evento.setNome(resultSet.getString("nome"));
                evento.setDescricao(resultSet.getString("descricao"));
                evento.setDataInicio(LocalDate.from(resultSet.getTimestamp("dataInicio").toLocalDateTime()));
                evento.setDataFim(LocalDate.from(resultSet.getTimestamp("dataFim").toLocalDateTime()));
                evento.setLocal(resultSet.getString("local"));
                evento.setCategoria(resultSet.getString("categoria"));
                evento.setStatus(resultSet.getString("status"));
                todosEventos.add(evento);
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return todosEventos;
    }
}
