package fr.ensicaen.ba.tennis.tenniswebapp;

import fr.ensicaen.ba.tennis.appli.Database;
import fr.ensicaen.ba.tennis.entities.TournoiEntity;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "adherentServlet", value = "/adherent-servlet")
public class AdherentServlet extends HttpServlet {
    RequestDispatcher _dispatcher;
    Database db;
    List<TournoiEntity> listOfTournaments;
    Map<String, String> userInfos;

    public void init() {
        db = new Database();
        listOfTournaments = new ArrayList<>();
        userInfos = new HashMap<>();

    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null) {
            redirectToLogin(response);
            return;
        }

        String userEmail = (String) session.getAttribute("email");
        if (userEmail == null) {
            redirectToLogin(response);
            return;
        }

        loadUserDetails(request, userEmail);
        processTournamentUnsigning(request);
        forwardToPage(request, response, "adherent.jsp");
    }

    private void redirectToLogin(HttpServletResponse response) throws IOException {
        response.sendRedirect("Login.html");
    }

    private void loadUserDetails(HttpServletRequest request, String userEmail) {
        List<TournoiEntity> listOfTournaments = db.getTournamentByAdherentEmail(userEmail);
        request.setAttribute("listOfTournaments", listOfTournaments);

        Map<String, String> userInfos = db.getUserInfo(userEmail);
        if (userInfos == null) {
            request.setAttribute("userInfos", "Erreur : userInfos est null pour l'email " + userEmail);
        } else {
            request.setAttribute("userInfos", userInfos);
        }
    }

    private void processTournamentUnsigning(HttpServletRequest request) {
        String unsignedTournament = (String) request.getAttribute("unsignedTournament");
        String adherentID = (String) request.getSession().getAttribute("userNumber");

        if (unsignedTournament != null && !unsignedTournament.isEmpty()) {
            if (!unsign(unsignedTournament, adherentID)) {
                System.out.println("Unsigning failed for tournament: " + unsignedTournament + " and user: " + adherentID);
            }
        }
    }

    private void forwardToPage(HttpServletRequest request, HttpServletResponse response, String page) throws ServletException, IOException {
        _dispatcher = request.getRequestDispatcher(page);
        _dispatcher.forward(request, response);
    }

    private boolean unsign(String tournamentCode, String adherentCode) {
        return db.unsignUserToTournament(adherentCode, tournamentCode);
    }


    public void destroy() {
    }
}