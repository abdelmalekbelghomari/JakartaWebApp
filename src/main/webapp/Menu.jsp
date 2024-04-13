<%@ page import="javax.xml.crypto.Data" %>
<%@ page import="fr.ensicaen.ba.tennis.appli.Database" %><%--
  Created by IntelliJ IDEA.
  User: vbdel
  Date: 4/11/24
  Time: 10:28 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Menu</title>
    <jsp:include page="header.jsp" />
    <link rel="stylesheet" href="styles/Menu_style.css">

</head>
    <body>
        <div class="container">
            <%
                Database db = new Database();
                if(session != null){
                    String email = (String) session.getAttribute("email");
                    String userSurname = db.getUserSurnameFromEmail(email);
                    String userName = db.getUserNameFromEmail(email);
                    String userNumber = db.getUserAdherentNumberFromEmail(email);
                    session.setAttribute("userName", userName);
                    session.setAttribute("userSurname", userSurname);
                    session.setAttribute("userNumber", userNumber);
                    out.println("<h2>Bonjour, " + userName + " " + userSurname + "!</h2>");
                }
            %>

            <form action="action-servlet" method="post">
                <input type="hidden" name="codeName" value="A">
                <button type="submit">Dossier Adherent</button>
            </form>

            <form action="action-servlet" method="post">
                <input type="hidden" name="codeName" value="I">
                <p style="text-align: center">Entrez le code du tournoi si vous le connaissez, <br> sinon cliquez sur le bouton d'inscription :</p>
                <input type="text" name="codeTournoi" placeholder="Code du Tournoi">
                <button type="submit">Inscription Tournoi</button>
            </form>
        </div>

    </body>
</html>
