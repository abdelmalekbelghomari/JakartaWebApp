<%@ page import="fr.ensicaen.ba.tennis.entities.TournoiEntity" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="fr.ensicaen.ba.tennis.appli.Database" %><%--
  Created by IntelliJ IDEA.
  User: vbdel
  Date: 4/11/24
  Time: 5:48 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Liste des Tournois</title>
    <jsp:include page="header.jsp"/>
    <link rel="stylesheet" href="styles/adherent_style.css">
</head>

<%
    Map<String, String> userInfos = (Map<String, String>) request.getAttribute("userInfos");
    if (userInfos != null) {
        String userName = (String) session.getAttribute("userName");
        String userSurname = (String) session.getAttribute("userSurname");
%>
<h2>Données sur le Joueur : <%= userName %> <%= userSurname %></h2>
<div class="info-container">
    <div class="info-label">Numéro d'Adhérent : <%= userInfos.get("adherentNumber") %></div><br>
    <div class="info-label">Prénom : <%= userInfos.get("name") %></div><br>
    <div class="info-label">Nom : <%= userInfos.get("surname") %></div><br>
    <div class="info-label">E-mail : <%= userInfos.get("email") %></div><br>
    <div class="info-label">Adresse : <%= userInfos.get("address") %></div><br>
    <div class="info-label">Numéro de Téléphone : <%= userInfos.get("phoneNumber") %></div>
</div>
<%
    } else {
        out.println("<p>Informations sur l'utilisateur non disponibles.</p>");
    }
%>
<h2>Liste des Tournois</h2>
<table border="1">
    <thead>
    <tr>
        <th>Nom du Tournoi</th>
        <th>Date</th>
        <th>Lieu</th>
        <th>Se désinscrire ?</th>
    </tr>
    </thead>
    <tbody>
    <%
        ArrayList<TournoiEntity> listOfTournaments = (ArrayList<TournoiEntity>) request.getAttribute("listOfTournaments");
        if(listOfTournaments != null) {
            for(TournoiEntity tournament : listOfTournaments) {
                out.println("<tr>");
                out.println("<td>" + tournament.getNom() + "</td>");
                out.println("<td>" + tournament.getDate() + "</td>");
                out.println("<td>" + tournament.getLieu() + "</td>");
                out.println("<td>");
                out.println("<form method='post' action='action-servlet'>");
                out.println("<input type='hidden' name='unsignedTournament' value='" + tournament.getCodetournoi() + "' />");
                out.println("<input type='hidden' name='codeName' value='A' />");
                out.println("<input type='submit' value='Se Désinscrire' />");
                out.println("</form>");
                out.println("</td>");
                out.println("</tr>");
            }
        }
    %>
    </tb
    </tbody>
</table>
</body>
</html>
