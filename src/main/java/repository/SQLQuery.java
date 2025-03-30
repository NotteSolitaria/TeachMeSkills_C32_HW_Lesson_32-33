package repository;

public class SQLQuery {
    public final static String ACCOUNT_SQL = "INSERT INTO accounts (account_number, balance) VALUES (?, ?)";
    public final static String BALANCE_SQL = "SELECT balance FROM accounts WHERE account_number = ?";
    public final static String DEBIT_SQL = "UPDATE accounts SET balance = balance - ? WHERE account_number = ?";
    public final static String CREDIT_SQL = "UPDATE accounts SET balance = balance + ? WHERE account_number = ?";
    public final static String LOG_SQL = "INSERT INTO transactions (from_account, to_account, amount) VALUES (?, ?, ?)";
}
