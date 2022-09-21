package uz.pdp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(value = "/login")
public class Login extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("login.html");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userName = req.getParameter("userName");
        String password = req.getParameter("password");
        Database database = new Database();
        List<User> userList = database.userList();
        boolean isUser = false;
        for (User user: userList){
            if (userName.equals(user.getUserName()) && password.equals(user.getPassword())){
                isUser = true;
            }
        }
        if (isUser){
            resp.sendRedirect("/cabinet");
        }
        else {
            PrintWriter printWriter = resp.getWriter();
            printWriter.write("<h1>Bunday user mavjud emas </h1>");
        }
    }
}
