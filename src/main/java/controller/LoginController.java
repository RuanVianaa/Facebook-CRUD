package controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.ModelException;
import model.User;
import model.dao.DAOFactory;
import model.dao.UserDAO;
import model.utils.PasswordEncryptor;

@WebServlet(urlPatterns = {"/login", "/logout"})
public class LoginController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        
        String action = req.getServletPath();
        if ("/logout".equals(action)) {
            HttpSession session = req.getSession(false);
            if (session != null) {
                session.invalidate();
            }
        }
        
        req.getRequestDispatcher("/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        
        String userEmail = req.getParameter("user_login");
        String userPw = req.getParameter("user_pw");
        

        if (userEmail == null || userEmail.isBlank() || userPw == null || userPw.isBlank()) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp?erro=true");
            return;
        }

        UserDAO dao = DAOFactory.createDAO(UserDAO.class);
        User userToLogin = null;
        
        try {
            userToLogin = dao.findByEmail(userEmail);
            if (userToLogin != null) {
              
            }
        } catch (ModelException e) {
            e.printStackTrace();
        }

        if (userToLogin == null) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp?erro=true");
            return;
        }
        if (PasswordEncryptor.checkPassword(
                userPw, userToLogin.getPasswordHash())) {

            req.getSession().setAttribute("user_logged", userToLogin);
            resp.sendRedirect(req.getContextPath() + "/index.jsp");

        } else {
            resp.sendRedirect(req.getContextPath() + "/login.jsp?erro=true");
        }
    }
}