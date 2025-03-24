import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB {
    private static final String URL = "jdbc:postgresql://database.inf.upol.cz:5432/balcma00";
    private static final String USER = "balcma00";
    private static final String PASSWORD = "Maty1760";

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
