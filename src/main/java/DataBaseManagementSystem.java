import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.SecureRandom;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class DataBaseManagementSystem {

    private static DataBaseManagementSystem DBMS = null;
    private Connection connection = null;
    private BufferedReader reader = null;
    private Statement statement = null;
    static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    private DataBaseManagementSystem() {
        try {
            reader = new BufferedReader(new InputStreamReader(System.in));
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(
                    "jdbc:sqlite:well.db"
            );
            System.out.println("Соединение с базой данных установленно.");
            statement = connection.createStatement();
            createTableIfNotExist();
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private void createTableIfNotExist() {
        String creatingTableWells = "create table if not exists wells(" +
                "id integer primary key autoincrement not null," +
                "name varchar(10) not null," +
                "numberEquipment integer not null," +
                "unique(id, name)" +
                ");";
        try {
            statement.executeUpdate(creatingTableWells);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static DataBaseManagementSystem getDBMS() {
        if (DBMS == null)
            return new DataBaseManagementSystem();
        else
            return DBMS;
    }

    void insert() {
        try {
            System.out.println("Введите название скважины : ");
            String name = reader.readLine().toLowerCase(Locale.ROOT);
            System.out.println("Введите количество оборудования : ");
            int numberEquipment = Integer.parseInt(reader.readLine());
            if(isExist(name)) {
                System.out.println("Такая скважина числиться в БД.");
                System.out.println("Введите 1 что бы добавить количество оборудования к существующей скважине.");
                System.out.println("Нажмите 2 для повтора ввода.");
                String command = reader.readLine();
                if(command.equals("1")) {
                    addToExistingWell(name, numberEquipment);
                } else if(command.equals("2")) {
                    insert();
                }
            } else {

                // Добавление строк в таблицу скважины.
                String insertIntoWellsNewWellWithEquipment =
                        "INSERT INTO wells (name, numberEquipment) " +
                                "VALUES ( '" + name + "', '" + numberEquipment + "')";
                statement.executeUpdate(insertIntoWellsNewWellWithEquipment);

                // Создание таблицы оборудование если она не существует.
                String createTableEquipmentIfNotExist = "create table if not exists '" + name + "'(" +
                        "id integer primary key autoincrement not null," +
                        "name varchar(10) not null," +
                        "unique(id, name)" +
                        ");";
                statement.executeUpdate(createTableEquipmentIfNotExist);


                // Добавление строк в таблицу оборудование.
                ArrayList<String> uniqueNames = getArrayaListOfNamesWithUniqueName(numberEquipment);
                for (String uniqueName  : uniqueNames) {
                    String insertIntoEquipment = "" +
                            "INSERT INTO equipment(name) " +
                            "values('" + uniqueName +  "'" +
                            ");";
                    statement.executeUpdate(insertIntoEquipment);
                }

                System.out.println("Rows added.");

            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    private ArrayList<String> getArrayaListOfNamesWithUniqueName(int number) {
        ArrayList<String> result = new ArrayList<>();
        SecureRandom rnd = new SecureRandom();
        StringBuilder stringBuilder = new StringBuilder();
        while (number > 0) {
            stringBuilder = null;
            for (int i = 0; i < 10; i++) {
                stringBuilder.append(AB.charAt(rnd.nextInt(AB.length())));
            }
            result.add(stringBuilder.toString());
            number--;
        }
        return result;
    }

    private void addToExistingWell(String name, int numberForAdd) {
        try {
            String queryGetNumberOfWell = "select numberEquipment from wells where name='" + name + "'";
            ResultSet rs = statement.executeQuery(queryGetNumberOfWell);
            int equipmentNumber = rs.getInt("numberEquipment") + numberForAdd;
            String stringUpdatedNumberOfName = "" +
                    "update wells " +
                    "set number = " + equipmentNumber + " " +
                    "where name = '" + name + "'";
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean isExist(String name) {
        try {
            Boolean result = false;
            String query = "select name from wells where name='" + name + "'";
            ResultSet rs = statement.executeQuery(query);
            if(rs.next())
                result = true;
            else
                result = false;
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    void close() throws SQLException {
        try {
            connection.close();
            reader.close();
            statement.close();
            System.out.println("Connection, reader, statement is closed.");
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    void showVewOfAllInfo() throws IOException, SQLException {
        try {
            Statement statement = connection.createStatement();
            String query = "select id, name, number" +
                    " from wells" +
                    "order by number";
            ResultSet rs = statement.executeQuery(query);
            System.out.println("id  name    number");
            while(rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String number = rs.getString("number");
                System.out.println(id + "\t" +
                        name + "\t" +
                        number);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void exportInXML() {

    }

}
