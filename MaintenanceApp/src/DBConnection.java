import java.sql.*;
public interface DBConnection {
    Connection connect();
}

final class PostgresConnection implements DBConnection {

    private static PostgresConnection instance;
    private Connection conn;

    private PostgresConnection() {
        try {
            String url = "jdbc:postgresql://localhost:5432/SITE_MAIN_DB";
            String user = "postgres";
            String pass = "TempCodeNew09";

            conn = DriverManager.getConnection(url, user, pass);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static PostgresConnection getInstance() {
        if(instance == null) {
            instance = new PostgresConnection();
        }
        return instance;
    }

    @Override
    public Connection connect() {
        return conn;
    }
}

