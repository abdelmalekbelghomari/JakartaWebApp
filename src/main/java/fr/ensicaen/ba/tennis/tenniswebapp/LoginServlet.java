package fr.ensicaen.ba.tennis.tenniswebapp;

import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import fr.ensicaen.ba.tennis.appli.Database;
import fr.ensicaen.ba.tennis.entities.AdherentEntity;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "loginServlet", value = "/login-servlet")
public class LoginServlet extends HttpServlet {
    private String message;
    private Map<String, AdherentEntity> _adherentInfoMap;
    private Map<String, String> _usersInfoMap;
    private RequestDispatcher _dispatcher;
    public void init() {
        message = "Hello World!";
        getFromDatabase();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        boolean isAuthenticated = authenticateUser(email, password);
        if(isAuthenticated){
            HttpSession session = request.getSession();
            session.setAttribute("email", email);
            _dispatcher = request.getRequestDispatcher("/Menu.jsp");
            _dispatcher.forward(request,response);
        } else {
            String errorMessage = "Erreur: Veuillez réessayer";
            if(_usersInfoMap.get(email)!=null){
                errorMessage = "Le mot de passe est incorrect";
            }
            if (!_usersInfoMap.containsKey(email)){
                errorMessage = "L'email entré est incorrect";
            }
            response.sendRedirect(request.getContextPath() +
                    "/Login.html?error=" +
                    URLEncoder.encode(errorMessage, "UTF-8"));
        }


    }

    public static String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1)
                    hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void getFromDatabase(){
        Database db = new Database();
        ArrayList<AdherentEntity> usersInfo = db.getUsersInfo();
        _usersInfoMap = new HashMap<>();
        _adherentInfoMap = new HashMap<>();
        for(AdherentEntity user : usersInfo ){
            _usersInfoMap.put(user.getEmail(), user.getPassword());
            _adherentInfoMap.put(user.getEmail(), user);
        }
    }

    public boolean authenticateUser(String email, String password) {
        if(_usersInfoMap.get(email) == null) return false;
        String authenticationPassword = _usersInfoMap.get(email);
        String connectionPassword = hashPassword(password);
        if(authenticationPassword.equals(connectionPassword)) {
            getServletContext().setAttribute("adherent", _adherentInfoMap.get(email));
            return true;
        }
        return false;
    }

    public void destroy() {
    }
}