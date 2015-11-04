<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<link href="css/bootstrap.min.css" rel="stylesheet" type="text/css" />
<link href="css/bootstrap-theme.min.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<script src="js/jquery-2.1.4.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script src="js/HearthHeadData.js"></script>
	<h2>Decks Browsing</h2>
	<hr />
	<ul>
		<s:iterator value="decksToShow" var="deck">
		<li><a href="deck?id=<s:property value="#deck.deckId" />"><s:property value="#deck.name" /></a></li>
		</s:iterator>
	</ul>
	<nav>
		<ul class="pagination">
			<li><a href="#" aria-label="Previous"> <span aria-hidden="true">&laquo;</span>
			</a></li>
			<li><a href="decks?page=1">1</a></li>
			<li><a href="decks?page=2">2</a></li>
			<li><a href="decks?page=3">3</a></li>
			<li><a href="decks?page=4">4</a></li>
			<li><a href="decks?page=5">5</a></li>
			<li><a href="#" aria-label="Next"> <span aria-hidden="true">&raquo;</span>
			</a></li>
		</ul>
	</nav>
</body>
</html>