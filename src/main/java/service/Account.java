package service;

import repository.DatabaseService;
import repository.SQLQuery;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Account {

    public void createAccount(String accountNumber, BigDecimal initialBalance) {
        String sql = SQLQuery.ACCOUNT_SQL;

        try (Connection connection = DatabaseService.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, accountNumber);
            preparedStatement.setBigDecimal(2, initialBalance);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public BigDecimal getBalance(String accountNumber) throws SQLException {
        String sql = SQLQuery.BALANCE_SQL;

        try(Connection connection = DatabaseService.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, accountNumber);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                return resultSet.getBigDecimal("balance");
            } else {
                throw new SQLException("Account not found");
            }
        }
    }
}
