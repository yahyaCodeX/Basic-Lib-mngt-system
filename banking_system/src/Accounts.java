import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import javax.management.RuntimeErrorException;

import java.sql.Statement;


public class Accounts {
    private Connection connection;
    private Scanner scanner;

    public Accounts(Connection connection, Scanner scanner) {
        this.connection = connection;
        this.scanner = scanner;
    }
    //Open Account
    public int open_account(String email){
        if(!account_exist(email)){
            String open_account_query="insert into accounts(account_number,full_name,email,balance, security_pin) values(?,?,?,?,?);";
            scanner.nextLine();
            System.out.print("Enter Full Name: ");
            String fullName=scanner.nextLine();
            System.out.print("Enter Initial Amount: ");
            double balance=scanner.nextDouble();
            scanner.nextLine();
            System.out.print("Enter Security Pin: ");
            String security_pin=scanner.nextLine();
            try{
                int account_number=generate_accountnumber();
                PreparedStatement preparedStatement=connection.prepareStatement(open_account_query);
                preparedStatement.setInt(1, account_number);
                preparedStatement.setString(2, fullName);
                preparedStatement.setString(3, email);
                preparedStatement.setDouble(4, balance);
                preparedStatement.setString(5, security_pin);
                int rowsAffected=preparedStatement.executeUpdate();
                if(rowsAffected>0){
                    return account_number;
                }else{
                    System.out.println("Acoount Creation Failed");
                }
            }catch(SQLException e){
                System.out.println(e.getMessage());
            }
        
        }
        throw new RuntimeException("Account Already Exists");
    }
    
    //Get Account Number Function
    public int getAccount_number(String email){
        String get_Accountnumber_query="select account_number from accounts where email=?;";
        try{
            PreparedStatement preparedStatement=connection.prepareStatement(get_Accountnumber_query);
            preparedStatement.setString(1, email);
            ResultSet resultSet=preparedStatement.executeQuery();
            if(resultSet.next()){
                return resultSet.getInt("account_number");
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
      throw new RuntimeException("Account Number Does not exist");
    }
    //Generate Acoount
    public int generate_accountnumber(){
       try{
        Statement statement=connection.createStatement();
        ResultSet resultSet=statement.executeQuery("select account_number from accounts order by account_number desc limit 1;");
        if(resultSet.next()){
            int  last_accoun_number=resultSet.getInt("account_number");
            return last_accoun_number+1;
        }else{
            return 1000000100;
        }
        
       }catch(SQLException e){
        System.out.println(e.getMessage());
       }
       return 1000000100;

    }
    //Account Exist function
    public boolean account_exist(String email){
        String account_existquery="select account_number from accounts where email=?;";
        try{
            PreparedStatement preparedStatement=connection.prepareStatement(account_existquery);
            preparedStatement.setString(1, email);
            ResultSet resultSet=preparedStatement.executeQuery();
            if(resultSet.next()){
                return true;
            }else{
                return false;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return false;
    }
}
