
import com.javier.estudy.connection.DatabaseConnection;


public class Main {
    public static void main(String[] args) {
        DatabaseConnection db = new DatabaseConnection();
        db.getConnection();
    }
}
