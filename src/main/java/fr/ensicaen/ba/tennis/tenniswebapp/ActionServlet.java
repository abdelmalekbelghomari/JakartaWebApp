package fr.ensicaen.ba.tennis.tenniswebapp;

import java.io.*;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "actionServlet", value = "/action-servlet")
public class ActionServlet extends HttpServlet {
    RequestDispatcher _dispatcher;
    public void init() {}

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("codeName");
        String email = request.getParameter("email");

        HttpSession session = request.getSession(false);
        if(session == null) {
            if (action.equals("L")) {
                _dispatcher = request.getRequestDispatcher("login-servlet");
                _dispatcher.forward(request,response);

            } else {
                _dispatcher = request.getRequestDispatcher("/Login.html");
                _dispatcher.forward(request,response);
            }
        } else {
            switch (action) {
                case "L":
                    if (!email.equals(session.getAttribute("email"))) {
                        _dispatcher = request.getRequestDispatcher("/errorPage.html");
                        _dispatcher.forward(request, response);
                    } else {
                        _dispatcher = request.getRequestDispatcher("login-servlet");
                        _dispatcher.forward(request, response);
                    }
                    break;
                case "A":
                    String unsignedTournament = request.getParameter("unsignedTournament");
                    request.setAttribute("unsignedTournament", unsignedTournament);
                    _dispatcher = request.getRequestDispatcher("adherent-servlet");
                    _dispatcher.forward(request, response);
                    break;
                case "I":
                    String codeTournoi = request.getParameter("codeTournoi");
                    request.setAttribute("codeTournoi", codeTournoi);
                    _dispatcher = request.getRequestDispatcher("inscription-servlet");
                    _dispatcher.forward(request, response);
                    break;
                default:
                    _dispatcher = request.getRequestDispatcher("/Menu.jsp");
                    _dispatcher.forward(request, response);
                    break;
            }
        }
    }

    public void destroy() {
    }
}