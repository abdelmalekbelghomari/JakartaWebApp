<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Statut de l'inscription</title>
    <jsp:include page="header.jsp"/>
    <link rel="stylesheet" href="styles/InscriptionStatus_style.css">
</head>
<body>
<%
    String tournamentName = (String) request.getAttribute("tournamentName");
    String tournamentPlace = (String) request.getAttribute("tournamentPlace");
    String tournamentDate = (String) request.getAttribute("tournamentDate");
    boolean signingSucceeded = (boolean) request.getAttribute("status");
    if (signingSucceeded) {
%>
<div class="message success">
    <h1>Inscription Réussie!</h1>
    <p>Félicitations, votre inscription au tournoi : <% out.println(tournamentName); %>
        du  <% out.println(tournamentDate); %> à  <% out.println(tournamentPlace); %>  a été enregistrée avec succès.</p>
</div>
<%
} else {
%>
<div class="message failure">
    <h1>Inscription Échouée</h1>
    <p>Nous sommes désolés, mais nous n'avons pas pu traiter votre inscription. Vous êtes sûrement déjà inscrit à ce tournoi.
        Sinon veuillez réessayer plus tard ou contacter le support.</p>
</div>
<%
    }
%>
<form id="home-button-page" action="action-servlet" method="post">
    <input type="hidden" name="codeName" value="Menu">
    <input type="submit" value="Retour au Menu">
</form>
</body>
</html>
