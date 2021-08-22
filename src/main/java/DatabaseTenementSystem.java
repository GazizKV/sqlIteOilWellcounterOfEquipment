import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseTenementSystem {

    Connection connection = null;
    BufferedReader reader = null;

    static {
        BufferedReader reader = null;
        Connection connection = null;

        try {
            reader = new BufferedReader(new InputStreamReader(System.in));
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(
                    "jdbc:sqlite:well.db"
            );
            if (connection.getCatalog() != null)
                System.out.println(connection.getCatalog().toString());
            System.out.println("Connect!");
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }



    void open() {

    }

    void insert() throws SQLException {
        try {
            System.out.println("Enter well name : ");
            String name = reader.readLine();
            System.out.println("Enter well number : ");
            String number = reader.readLine();

            String query =
                    "INSERT INTO wells (name, number) " +
                            "VALUES ('" + name + "', '" + number + "')";

            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
            System.out.println("Rows added.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


    }

    void showDbTableWells() throws SQLException {
    }

    void upDate() {

    }

    void close() {
        try {
            connection.close();
            reader.close();
            System.exit(0);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    void showVewOfAllInfo() throws IOException, SQLException {
        try {
            connection.createStatement();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    void exportInXML() {

    }

}
