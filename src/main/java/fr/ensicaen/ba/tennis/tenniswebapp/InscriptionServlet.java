package fr.ensicaen.ba.tennis.tenniswebapp;

import fr.ensicaen.ba.tennis.appli.Database;
import fr.ensicaen.ba.tennis.entities.TournoiEntity;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "inscriptionServlet", value = "/inscription-servlet")
public class InscriptionServlet extends HttpServlet {
    private Database db;

    @Override
    public void init() {
        db = new Database();  // Ensure database is properly instantiated.
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userNumber") == null) {
            redirectToLogin(response);
            return;
        }

        String codeTournoi = request.getParameter("codeTournoi");
        if (codeTournoi == null || codeTournoi.isEmpty()) {
            displayTournamentList(request, response);
        } else {
            handleTournamentRegistration(request, response, codeTournoi, session);
        }
    }

    private void redirectToLogin(HttpServletResponse response) throws IOException {
        response.sendRedirect("Login.html");
    }

    private void displayTournamentList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<TournoiEntity> tournamentList = db.getTournamentList();
        request.setAttribute("tournamentList", tournamentList);
        forward(request, response, "/InscriptionTournoi.jsp");
    }

    private void handleTournamentRegistration(HttpServletRequest request, HttpServletResponse response, String codeTournoi, HttpSession session) throws ServletException, IOException {
        String adherentID = session.getAttribute("userNumber").toString();
        boolean signupSuccess = db.signUserToTournament(adherentID, codeTournoi);
        request.setAttribute("status", signupSuccess);

        if (signupSuccess) {
            TournoiEntity tournament = db.getTournoi(Integer.parseInt(codeTournoi));
            if (tournament != null) {
                request.setAttribute("tournamentName", tournament.getNom());
                request.setAttribute("tournamentDate", tournament.getDate().toString());
                request.setAttribute("tournamentPlace", tournament.getLieu());
            }
        }

        forward(request, response, "/InscriptionStatus.jsp");
    }

    private void forward(HttpServletRequest request, HttpServletResponse response, String path) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher(path);
        dispatcher.forward(request, response);
    }

    @Override
    public void destroy() {
        // Cleanup resources if necessary
    }
}
