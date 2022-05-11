import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DataDB {

    public static Connection conn;
    private static String url;
    public static Statement stmt;

    public DataDB(String nomeUtente, String passw, String nomeDB) throws Exception{
        Class.forName("com.mysql.cj.jdbc.Driver");
        url = "jdbc:mysql://127.0.0.1/"+nomeDB;
        conn = DriverManager.getConnection(url, nomeUtente, passw);
        stmt = conn.createStatement();
    }

}
