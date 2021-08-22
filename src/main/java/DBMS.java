import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBMS {

    Connection connection = null;
    BufferedReader reader = null;

    DBMS() {
        try {
            reader = new BufferedReader(new InputStreamReader(System.in));
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(
                    "jdbc:sqlite:well.db"
            );
            System.out.println("Connect to DB :" + connection.getMetaData().getDatabaseProductName());

            if(connection == null) {
                System.out.println("Базаданных не содержит какой либо информации.");
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    void open() {
        try {
            Class.forName("org.sql.JDBC");
            Connection connection = DriverManager.getConnection(
                    "jdbc:sqlite:well.db"
            );
            System.out.println("Connect to DB" + connection.getMetaData().getDatabaseProductName());
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

    }

    void insert() throws SQLException {
        try {
            System.out.println("Enter well name : ");
            String name = reader.readLine();
            System.out.println("Enter number of equipment : ");
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

    void close() throws SQLException {
        try {
            connection.close();
            reader.close();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
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
