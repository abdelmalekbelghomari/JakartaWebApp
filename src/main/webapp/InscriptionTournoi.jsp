<%@ page import="fr.ensicaen.ba.tennis.entities.TournoiEntity" %>
<%@ page import="java.util.List" %>
<%@ page import="fr.ensicaen.ba.tennis.appli.Database" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Liste des Tournois</title>
    <jsp:include page="header.jsp"/>
    <link rel="stylesheet" href="styles/InscriptionTournoi_style.css">
</head>
<body>
<h2>Liste des Tournois Disponibles</h2>
<table>
    <thead>
    <tr>
        <th>Nom du Tournoi</th>
        <th>Date</th>
        <th>Lieu</th>
        <th>Inscription</th>
    </tr>
    </thead>
    <tbody>
    <%
        Database db = new Database();
        String userNumber = (String) session.getAttribute("userNumber");
        List<TournoiEntity> tournamentList = (List<TournoiEntity>) request.getAttribute("tournamentList");
        boolean isSignedUp;
        if (tournamentList != null) {
            for (TournoiEntity tournoiEntity : tournamentList) {
                isSignedUp = false;
                if(db.isSignedUpToTournament(userNumber, String.valueOf(tournoiEntity.getCodetournoi()))){
                    isSignedUp = true;
                }
    %>
    <tr>
        <td><%= tournoiEntity.getNom() %></td>
        <td><%= tournoiEntity.getDate() %></td>
        <td><%= tournoiEntity.getLieu() %></td>
        <td>
            <form action="action-servlet" method="post">
                <input type="hidden" name="codeTournoi" value="<%= tournoiEntity.getCodetournoi() %>">
                <input type="hidden" name="codeName" value="I">
                <% if(!isSignedUp){
                    out.println("<input type=\"submit\" value=\"S'inscrire\">\n");
                } else {
                    out.println("<p> Déjà Inscrit </p>");
                }

                %>
            </form>
        </td>
    </tr>
    <%
            }
        }
    %>
    </tbody>
</table>
</body>
</html>
