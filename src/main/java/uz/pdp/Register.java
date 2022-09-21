package uz.pdp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(value = "/register")
public class Register extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("register.html");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter printWriter = resp.getWriter();
        String userName = req.getParameter("userName");
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String phoneNumber = req.getParameter("phoneNumber");
        String password = req.getParameter("password");
        String prePassword = req.getParameter("prePassword");
        Database database = new Database();
        List<User> userList = database.userList();

        boolean isExistent = false;
        for (User user: userList){
            if (user.getUserName().equals(userName)){
                printWriter.write("<h1>user Name already exists!</h1>");
                isExistent = true;
                break;
            }
        }
        if (firstName.isEmpty()){
            printWriter.write("<h1>first name is not entered!</h1>");
            isExistent = true;
        }
        if (lastName.isEmpty()){
            printWriter.write("<h1>last Name is not entered!</h1>");
            isExistent = true;
        }
        int lengthOfPhoneNumber = phoneNumber.length();
        if (lengthOfPhoneNumber == 13){
            if (phoneNumber.endsWith("+")){
                printWriter.write("<h1> to'gri</h1>");
            }
        }
        else if (lengthOfPhoneNumber == 9){
            phoneNumber = "+998"+phoneNumber;
        }
        else if (lengthOfPhoneNumber == 10){
            phoneNumber = "+" + phoneNumber;
        }
        else {
            printWriter.write("<h1>You entered the wrong phone number!</h1>");
            printWriter.write("<h1>Phone Number example: +998901234567, 998901234567, 901234567</h1>");
            isExistent = true;
        }
        for (User user: userList){
            if (user.getPhoneNumber().equals(phoneNumber)){
                printWriter.write("<h1>phone Number already exists!</h1>");
                isExistent = true;
                break;
            }
        }
        boolean correctPassword = database.isCorrectPassword(password);
        if (!correctPassword){
            printWriter.write("<h1>Password does not meet requirements!</h1>");
            isExistent = true;
        }

        if (!password.equals(prePassword)){
            printWriter.write("<h1>password is not equal to prePassword!</h1>");
            isExistent = true;
        }

        if (isExistent){
            printWriter.write("<body text =FF0000>");
            printWriter.write("<h1> User not saved! Please try again! </h1> </body>");
        }
        if (!isExistent){
            User user = new User(userName,firstName,lastName,phoneNumber,password);
            database.saveUser(user);
            printWriter.write("<body text =#00FF00>");
            printWriter.write("<h1> User saved successfully! </h1> </body>");
            resp.sendRedirect("/cabinet");
        }


    }
}
