package de.koo.other;

/**
 * @author Shyqu
 * @created 30/03/2021 - 17:56
 * @project HololiveBot
 */

import java.io.File;
import java.io.IOException;
import java.sql.*;

public class SQLite {

    private static Connection conn;
    private static Statement stmt;

    public static void connect() {

        try {
            File file = new File("yagoo.db");
            if (!file.exists()) {
                file.createNewFile();
            }

            String url = "jdbc:sqlite:" + file.getPath();
            conn = DriverManager.getConnection(url);

            System.out.println("Verbindung zur Datenbank hergestellt.");

            stmt = conn.createStatement();

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    public static void disconnect() {
        try {
            if (conn != null) {
                conn.close();
                System.out.println("Verbindung zur Datenbank getrennt.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void onUpdate(String sql) {
        try {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void onUpdateRAW(String sql) throws SQLException {
        stmt.execute(sql);
    }

    public static ResultSet onQuery(String sql) {

        try {
            return stmt.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static ResultSet onQueryRAW(String sql) throws SQLException {
        return stmt.executeQuery(sql);
    }

    public static void onCreate() {
        // String name, String group, String gen, String gender, String age, String birthday, String height, String weight, String bloodtype, String zodiac_sign, String emoji
        onUpdate("CREATE TABLE IF NOT EXISTS holomembers(id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, name TEXT, grp TEXT, gen TEXT, gender TEXT, age TEXT, birthday TEXT, height TEXT, weight TEXT, bloodtype TEXT, zodiac_sign TEXT, emoji TEXT, picture TEXT)");
        onUpdate("CREATE TABLE IF NOT EXISTS coinsystem(id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, userid TEXT, wallet TEXT, bank TEXT)");
    }

}