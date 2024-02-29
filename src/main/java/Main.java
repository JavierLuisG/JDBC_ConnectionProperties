
import com.javier.estudy.connection.DatabaseConnection;
import com.javier.estudy.controller.ConnectionController;
import com.javier.estudy.view.TestConnectionView;


public class Main {
    public static void main(String[] args) {
        TestConnectionView connectionView = new TestConnectionView();
        DatabaseConnection db = DatabaseConnection.getInstance();
        
        ConnectionController cc = new ConnectionController(connectionView, db);
        cc.start();
    }
}
