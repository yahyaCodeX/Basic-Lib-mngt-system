import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
public class AccountManager{
    private Connection connection;
    private Scanner scanner;

    public AccountManager(Connection connection,Scanner scanner){
        this.connection=connection;
        this.scanner=scanner;
    }

    //Credit Money Function------------------------------------------------
    public void credit_money(int account_number) throws SQLException{
        scanner.nextLine();
        System.out.print("Enter Amount: ");
        double amount=scanner.nextDouble();
        scanner.nextLine();
        System.out.print("Enter Security_pin: ");
        String security_pin=scanner.nextLine();
        try{
            connection.setAutoCommit(false);
            if(account_number!=0){
              PreparedStatement preparedStatement=connection.prepareStatement("select * from accounts where account_number=? and security_pin=?;");
              preparedStatement.setInt(1, account_number);
              preparedStatement.setString(2, security_pin);
              ResultSet resultSet=preparedStatement.executeQuery();
              if(resultSet.next()){
                String credit_query="update accounts set balance=balance+? where account_number=?;";
                PreparedStatement preparedStatement1=connection.prepareStatement(credit_query);
                preparedStatement1.setDouble(1, amount);
                preparedStatement1.setInt(2, account_number);
                int rowsaffected=preparedStatement1.executeUpdate();
                if(rowsaffected>0){
                    System.out.println("RS "+amount+"Credited Successfully");
                    connection.commit();
                    connection.setAutoCommit(true);
                    return;
                }else{
                    System.out.println("transaction failed");
                    connection.rollback();;
                    connection.setAutoCommit(true);
                }
              }
            }else{
                System.out.println("Invalid Security Pin");
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
            connection.setAutoCommit(true);
    }

    //Debit Money Function---------------------------------------------------
    public void debit_money(int account_number)throws SQLException{
        scanner.nextLine();
        System.out.print("Enter Amount: ");
        double amount=scanner.nextDouble();
        scanner.nextLine();
        System.out.print("Enter Security_pin: ");
        String security_pin=scanner.nextLine();
        try{
        connection.setAutoCommit(false);
        if(account_number!=0){
        PreparedStatement preparedStatement=connection.prepareStatement("select * from accounts where account_number=? and security_pin=?;");
        preparedStatement.setInt(1, account_number);
        preparedStatement.setString(2, security_pin);
        ResultSet resultSet=preparedStatement.executeQuery();
        if(resultSet.next()){
           double cuurent_balance=resultSet.getDouble("balance");
        if(cuurent_balance>=amount){
            String debit_query="update accounts set balance=balance-? where account_number=?;";
            PreparedStatement preparedStatement1=connection.prepareStatement(debit_query);
            preparedStatement1.setDouble(1,amount);
            preparedStatement1.setInt(2, account_number);
            int rowsAffected=preparedStatement1.executeUpdate();
            if(rowsAffected>0){
                System.out.println("RS "+amount+"Debited Successfully");
                connection.commit();
                connection.setAutoCommit(true);
            }else{
                System.out.println("Transaction failed");
                connection.rollback();
                connection.setAutoCommit(true);
            }
            }else{
                System.out.println("Insufficient Balance");
            }
        }else{
            System.out.println("Invalid Pin");
        }
    }
}catch(SQLException e){
    System.out.println(e.getMessage());
}
    connection.setAutoCommit(true);                
    }

//Transfer Money Functions ------------------------
public void transfer_money(int sender_account_number)throws SQLException{
    scanner.nextLine();
    System.out.print("Enter Reciever Account Number: ");
    int receiver_account_number=scanner.nextInt();
    scanner.nextLine();
    System.out.print("Enter Amount: ");
    double amount=scanner.nextDouble();
    scanner.nextLine();
    System.out.print("Enter Security_pin: ");
    String security_pin=scanner.nextLine();
    try{
        connection.setAutoCommit(false);
        if(sender_account_number!=0 && receiver_account_number!=0){
            PreparedStatement preparedStatement=connection.prepareStatement("select * from accounts where account_number=? and security_pin=?;");
            preparedStatement.setInt(1, sender_account_number);
            preparedStatement.setString(2, security_pin);
            ResultSet resultSet=preparedStatement.executeQuery();
            if(resultSet.next()){
              double current_balance=resultSet.getDouble("balance");
              if(current_balance>=amount){
                String debit_query="update accounts set balance=balance-? where  account_number=?;";
                String credit_query="update accounts set balance=balance + ? where account_number=?;";
                PreparedStatement creditpreparedStatement=connection.prepareStatement(credit_query);
                PreparedStatement debitpreparedStatement=connection.prepareStatement(debit_query);
                creditpreparedStatement.setDouble(1, amount);
                creditpreparedStatement.setInt(2, receiver_account_number);
                debitpreparedStatement.setDouble(1, amount);
                debitpreparedStatement.setInt(2, sender_account_number);
                int rowsAffected1=creditpreparedStatement.executeUpdate();
                int rowsAffected2=debitpreparedStatement.executeUpdate();
                if(rowsAffected1>0 && rowsAffected2>0){
                    System.out.println("Transaction Successful");
                    System.out.println("RS "+amount+"Transfered Succssfully");
                    connection.commit();
                    connection.setAutoCommit(true);
                    return;
                }else{
                    System.out.println("Transaction Failed");
                    connection.rollback();
                    connection.setAutoCommit(true);
                }
            }else{
                System.out.println("Insufficient Balance");
            }
        }else{
            System.out.println("Invalid Security Pin!");
        }
    }else{
        System.out.println("Invalid account number");
    }

     }catch(SQLException e){
        System.out.println(e.getMessage());
    }
    connection.setAutoCommit(true);
}

//Get Money Function---------------------------------------------
public void get_balance(int account_number){
    scanner.nextLine();
    System.out.print("Enter Security_pin: ");
    String security_pin=scanner.nextLine();
    try{
        PreparedStatement preparedStatement=connection.prepareStatement("select * from accounts where account_number=? and security_pin=?;");
        preparedStatement.setInt(1, account_number);
        preparedStatement.setString(2, security_pin);
        ResultSet resultSet=preparedStatement.executeQuery();
        if(resultSet.next()){
            double balance=resultSet.getDouble("balance");
            System.out.println("Your Current Balance is: RS "+balance);
        }else{
            System.out.println("Invalid Security Pin!");
        }
    }catch(SQLException e){
        System.out.println(e.getMessage());
    }

}
     
}
    