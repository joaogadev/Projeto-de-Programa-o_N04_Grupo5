package repository;
import java.sql.*;

public class MyJDBC {
    private static final String url = "jdbc:mysql://localhost:3306/sistemaeventos";
    private static final String user = "root";
    private static final String password = "joaoguilhermemendes";

    //conexão com o banco
    public static Connection getConnection() throws SQLException{
        return DriverManager.getConnection(url, user, password);
    }
}
