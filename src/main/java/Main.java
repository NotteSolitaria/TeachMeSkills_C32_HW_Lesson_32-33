import service.Account;
import service.Transfer;

import java.math.BigDecimal;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        try {
            Account account = new Account();

            account.createAccount("1", new BigDecimal("500"));
            account.createAccount("2", new BigDecimal("700"));
            account.createAccount("3", new BigDecimal("1200"));

            Transfer transfer = new Transfer();
            transfer.transferOperation("1", "2", new BigDecimal("300"));

            System.out.println("Account balance 1 : " + account.getBalance("1"));
            System.out.println("Account balance 2: " + account.getBalance("2"));
            System.out.println("Account balance 3: " + account.getBalance("3"));

            transfer.transferOperation("2", "3", new BigDecimal("200"));

            System.out.println("Account balance 2 after transfer: " + account.getBalance("2"));
            System.out.println("Account balance 3 after transfer:" + account.getBalance("3"));

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
