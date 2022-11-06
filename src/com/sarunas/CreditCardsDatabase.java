package com.sarunas;

import org.sqlite.SQLiteDataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CreditCardsDatabase {

    public CreditCardsDatabase(){}

    public void createDB() {
        String url = "jdbc:sqlite:cards.db";
        String createTable = "CREATE TABLE IF NOT EXISTS cards(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "number TEXT UNIQUE NOT NULL," +
                "pin TEXT NOT NULL," +
                "balance INTEGER DEFAULT 0)";
        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl(url);
        try (Connection con = dataSource.getConnection()) {
            try (Statement statement = con.createStatement()) {
                statement.executeUpdate(createTable);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertData(Connection con, String cardNumber, String cardPin, int balance) throws SQLException {
        String insertValues = String.format("INSERT or IGNORE INTO cards(number, pin, balance) VALUES('%s', '%s', %d)", cardNumber, cardPin, balance);
        Statement statement = con.createStatement();
        statement.executeUpdate(insertValues);
    }

    public int getBalance(Connection con, String number) throws SQLException {
        int balance = 0;
        String query = String.format("SELECT balance FROM cards WHERE number = '%s'", number);
        Statement statement = con.createStatement();
        ResultSet rs = statement.executeQuery(query);
        while (rs.next()) {
            balance = rs.getInt("balance");
        }
        rs.close();
        return balance;
    }

    public String getPin(Connection con, String number) throws SQLException {
        String pin = "";
        String query = String.format("SELECT pin FROM cards WHERE number = '%s'", number);
        Statement statement = con.createStatement();
        ResultSet rs = statement.executeQuery(query);
        while (rs.next()) {
            pin = rs.getString("pin");
        }
        rs.close();
        return pin;
    }
}
