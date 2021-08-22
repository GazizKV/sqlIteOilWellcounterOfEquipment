import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;


public class ProgramForCountingEquipment {

    public static void main(String[] args) throws SQLException, IOException {
        BufferedReader reader = null;
        String inCommand = null;


        DBMS DBMS = new DBMS();
        reader = new BufferedReader(new InputStreamReader(System.in));


        Boolean nextCommand = true;
        while (nextCommand) {
            try {
                System.out.println("МЕНЮ");
                System.out.println("1. Добавить обарудование на скважину.");
                System.out.println("2. Вывести общую информацию об обарудовании на скважинах.");
                System.out.println("3. Экспорт всех данных в XML.");
                System.out.println("4. Закрыть программу.");
                System.out.print("Наберите нужный номер комманды и нажмите Enter.");
                inCommand = reader.readLine();

                switch (inCommand) {
                    case "1":
                        DBMS.insert();
                        break;
                    case "2":
                        DBMS.showVewOfAllInfo();
                        break;
                    case "3":
                        DBMS.exportInXML();
                        break;
                    case "4":
                        nextCommand = false;
                        DBMS.close();
                        System.out.println("DBMS closed");
                        reader.close();
                        break;
                    default:
                        System.out.println("Вы ввели неверную комманду.");
                        break;
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
