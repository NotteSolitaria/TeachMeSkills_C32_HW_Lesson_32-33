package service;

import repository.DatabaseService;
import repository.SQLQuery;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Transfer {

    public void transferOperation(String from, String to, BigDecimal amount) throws SQLException {

        Connection con = null;

        try{
            con = DatabaseService.getConnection();
            con.setAutoCommit(false);

            Account account = new Account();
            BigDecimal balance = account.getBalance(from);

            if (balance.compareTo(amount) < 0) {
                throw new SQLException("Not enough balance");
            }

            executeDebit(con, from, amount);
            executeCredit(con, to, amount);
            executeLog(con, from, to, amount);

            con.commit();

        } catch (SQLException e) {
            if (con != null) {
                con.rollback();
            }
            throw e; // Пропускаем исключение для обработки в вызывающем коде
        } finally {
            if (con != null) {
                con.setAutoCommit(true);
                con.close();
            }
        }
    }

    private void executeDebit(Connection con, String account, BigDecimal amount) throws SQLException {
        String sql = SQLQuery.DEBIT_SQL;
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setBigDecimal(1, amount);
            stmt.setString(2, account);
            stmt.executeUpdate();
        }
    }

    private void executeCredit(Connection con, String account, BigDecimal amount) throws SQLException {
        String sql = SQLQuery.CREDIT_SQL;
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setBigDecimal(1, amount);
            stmt.setString(2, account);
            stmt.executeUpdate();
        }
    }

    private void executeLog(Connection con, String from, String to, BigDecimal amount) throws SQLException {
        String sql = SQLQuery.LOG_SQL;
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, from);
            stmt.setString(2, to);
            stmt.setBigDecimal(3, amount);
            stmt.executeUpdate();
        }
    }
}
