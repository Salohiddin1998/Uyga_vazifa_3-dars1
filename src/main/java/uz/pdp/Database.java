package uz.pdp;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {
    private String url = "jdbc:postgresql://localhost:5432/app-auth";
    private String dbUser = "postgres";
    private String dbPassword = "root123";
    
    public List<User> userList(){
        List<User> users = new ArrayList<>();
        try {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(url,dbUser,dbPassword);
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM users;";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                int id = resultSet.getInt(1);
                String userName = resultSet.getString(2);
                String firstName = resultSet.getString(3);
                String lastName = resultSet.getString(4);
                String phoneNumber = resultSet.getString(5);
                String password = resultSet.getString(6);
                User user = new User(id,userName,firstName,lastName,phoneNumber,password);
                users.add(user);
            }

        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }

        return users;
    }

    public void saveUser(User savedUser){
        try {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(url,dbUser,dbPassword);
            String query = "INSERT INTO users(username,first_name,last_name,phone_number,password) values (?,?,?,?,?);";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,savedUser.getUserName());
            preparedStatement.setString(2,savedUser.getFirstName());
            preparedStatement.setString(3,savedUser.getLastName());
            preparedStatement.setString(4,savedUser.getPhoneNumber());
            preparedStatement.setString(5,savedUser.getPassword());
            preparedStatement.execute();

        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public  boolean isCorrectPassword(String password) {
        if (password.length() < 8) {
            return false;
        }
        int count = 0;
        for (int p = 0; p < password.length(); p++) {
                if (Character.isUpperCase(password.charAt(p))) {
                    count++;
                    break;
                }
        }
        for (int q = 0; q < password.length(); q++) {
                if (Character.isLowerCase(password.charAt(q))) {
                    count++;
                    break;
                }
        }
        for (int r = 0; r < password.length(); r++) {
                if (Character.isDigit(password.charAt(r))) {
                    count++;
                    break;
                }
        }
        if (count == 3){
            return true;
        }
        return  false;

    }


}
