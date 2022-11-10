package com.sarunas;

import org.sqlite.SQLiteDataSource;
import java.sql.*;

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


    public void addIncome(Connection connection, String cardNumber, int amount) {
        String updateBalance = "UPDATE cards SET balance = balance + ? WHERE number = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateBalance)) {
            preparedStatement.setInt(1, amount);
            preparedStatement.setString(2, cardNumber);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public void closeAccount(Connection connection, String cardNumber) {
        String deleteAccount = "DELETE FROM cards WHERE number = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteAccount)) {
            preparedStatement.setString(1, cardNumber);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public boolean checkCardNumberExists(Connection connection, String cardNumber) {
        String query = "SELECT number FROM cards WHERE number = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, cardNumber);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }


    public void transferMoney(Connection connection, String cardNumberToLogin, String cardNumberToTransfer, int amount) throws SQLException {
        String accountTransferMoneyFrom = "UPDATE cards SET balance = balance - ? WHERE number = ?";
        String accountTransferMoneyTo = "UPDATE cards SET balance = balance + ? WHERE number = ?";
        connection.setAutoCommit(false);
        try (PreparedStatement transferFrom = connection.prepareStatement(accountTransferMoneyFrom);
             PreparedStatement transferTo = connection.prepareStatement(accountTransferMoneyTo)) {

            transferFrom.setInt(1, amount);
            transferFrom.setString(2, cardNumberToLogin);
            transferFrom.executeUpdate();

            transferTo.setInt(1, amount);
            transferTo.setString(2, cardNumberToTransfer);
            transferTo.executeUpdate();

            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
