package fr.ensicaen.ba.tennis.tenniswebapp;

import fr.ensicaen.ba.tennis.appli.Database;
import fr.ensicaen.ba.tennis.entities.AdherentEntity;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "disconnectServlet", value = "/disconnect-servlet")
public class DisconnectServlet extends HttpServlet {
    private String message;
    RequestDispatcher _dispatcher;

    public void init() {
        message = "Hello World!";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        _dispatcher = request.getRequestDispatcher("Login.html");
        _dispatcher.forward(request,response);
    }

    public void destroy() {
    }
}