package MVC.src;

import java.sql.*;

public class Model {
    static Statement sqlSt;

    static String validateInput(String id) {
        if (!id.matches("\\d+")) {
            return "Only number is allowed";
        } else if (id.length() != 6) {
            return "ID must be 6 characters";
        } else if (id.charAt(0) == '0') {
            return "First character must not be zero";
        } else if (!idExist(id)) {
            return "this id doesn't exist";
        } else if (durabilityCheck(id)) {
            return "not broke yet";
        }
        return "";
    }

    private static boolean idExist(String id) {
        try {
            ResultSet result = sqlSt.executeQuery("select id from items where id = " + id);
            if (result.next() != false) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }

    }

    private static boolean durabilityCheck(String id) {
        try {
            ResultSet result = sqlSt.executeQuery("select * from items where id = " + id);
            result.next();
            switch (result.getString(2)) {
                case "powerful":
                    if (Integer.parseInt(result.getString(3)) <= 70) {
                        return false;
                    }
                    return true;
                case "stealth":
                    if (Integer.parseInt(result.getString(3)) <= 50) {
                        return false;
                    }
                    return true;
                case "invisible":
                    String durability = result.getString(3);
                    if (durability.charAt(durability.length() - 1) == '3'
                            || durability.charAt(durability.length() - 1) == '7') {
                        return false;
                    }
                    return true;
            }
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    static void fix(String id) {
        try {
            ResultSet result = sqlSt.executeQuery("select * from items where id = " + id);
            result.next();
            int durability = Integer.parseInt(result.getString(3));
            if ((durability + 25) > 100) {
                durability = 100;
                sqlSt.executeUpdate("UPDATE items SET durability = " + durability + " WHERE id = " + id);
            } else {
                durability += 25;
                sqlSt.executeUpdate("UPDATE items SET durability = " + durability + " WHERE id = " + id);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    static void connectDB() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String dbURL = "jdbc:mysql://localhost:3306/MVC";
            Connection dbConnect = DriverManager.getConnection(dbURL, "root", "root");
            System.out.println("connected!");
            sqlSt = dbConnect.createStatement();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    static String getInfo(String id) {
        try {
            ResultSet result = sqlSt.executeQuery("select * from items where id = " + id);
            result.next();
            return "type: " + result.getString(2) + " durability: " + result.getString(3);
        } catch (Exception e) {
            System.out.println(e);
            return "";
        }
    }

}
