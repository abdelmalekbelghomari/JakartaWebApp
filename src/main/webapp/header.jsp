<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Nom du Club</title>
    <link rel="stylesheet" href="styles/header_style.css">
</head>
<body>
<div class="header">
    <h1 class="header-h1">Tennis Club de Caen</h1>
    <br>
    <p class="header-p">1234 Avenue du Tennis <br>
        75000 Paris, France <br>
        Tel: 01 23 45 67 89</p>
    <form id="home-button" action="action-servlet" method="post">
        <input type="hidden" name="codeName" value="Menu">
        <input type="submit" value="Retour au Menu">
    </form>
</div>
</body>
</html>
