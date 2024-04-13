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
                forward(request, response,"/login-servlet");
            } else {
                forward(request, response,"/Login.html");

            }
        } else {
            switch (action) {
                case "L":
                    if (!email.equals(session.getAttribute("email"))) {
                        forward(request, response,"/errorPage.html");
                    } else {
                        forward(request, response,"login-servlet");
                    }
                    break;
                case "A":
                    String unsignedTournament = request.getParameter("unsignedTournament");
                    request.setAttribute("unsignedTournament", unsignedTournament);
                    forward(request, response,"adherent-servlet");
                    break;
                case "I":
                    String codeTournoi = request.getParameter("codeTournoi");
                    request.setAttribute("codeTournoi", codeTournoi);
                    forward(request, response,"inscription-servlet");
                    break;
                default:
                    forward(request,response,"/Menu.jsp");
                    break;
            }
        }
    }

    private void forward(HttpServletRequest request, HttpServletResponse response, String page) throws ServletException, IOException {
        _dispatcher = request.getRequestDispatcher(page);
        _dispatcher.forward(request, response);
    }

    public void destroy() {
    }
}