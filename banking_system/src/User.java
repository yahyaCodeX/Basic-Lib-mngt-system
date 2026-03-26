import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
public class User {
    private Connection connection;
    private Scanner scanner;

        public  User(Connection connection,Scanner scanner) {
            this.connection=connection;
            this.scanner=scanner;
    }

    //Register function
    public void Register(){
        scanner.nextLine();
        System.out.print("Full Name: ");
        String full_name=scanner.nextLine();
        System.out.print("Email: ");
        String email=scanner.nextLine();
        System.out.print("Password: ");
        String password=scanner.nextLine();
        if(user_exist(email)){
            System.out.println("Email already exists");
            return;
        }

        String registerquery="insert into user values(?,?,?);";
        try{
            PreparedStatement preparedStatement=connection.prepareStatement(registerquery);
            preparedStatement.setString(1, full_name);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, password);
            int rowsaffected=preparedStatement.executeUpdate();
            if(rowsaffected>0){
                System.out.println("Registration successful");
            }else{
                System.out.println("Registration failed");
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    //Login function
    public String login(){
        scanner.nextLine();
        System.out.print("Email: ");
        String email=scanner.nextLine();
        System.out.print("Password: ");
        String password=scanner.nextLine();
        String loginquery="select * from user where email=? and password=?;";
        try{
            PreparedStatement preparedStatement=connection.prepareStatement(loginquery);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            ResultSet resultSet=preparedStatement.executeQuery();
            if(resultSet.next()){
                return email;
            }else{
                return null;
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
    }
    return null;
    
}

//User exist funtion
public boolean user_exist(String email){
    String user_existquery="select * from user where email=?;";
    try{
        PreparedStatement preparedStatement=connection.prepareStatement(user_existquery);
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