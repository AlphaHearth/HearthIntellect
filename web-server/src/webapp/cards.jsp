<%@ page import="com.hearthintellect.model.Card, java.util.List" contentType="text/html; charset=UTF-8"%>
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
	<h2>Cards Gallery</h2>
	<hr />
	<table class="cards-gallery">
		<%
			List<Card> cardsToShow = (List<Card>) request.getAttribute("cardsToShow");
			int i = 1;
			for (Card card : cardsToShow) {
				out.println("\t\t\t<td class=\"gallery-card\"><img src=\"http://wow.zamimg.com/images/hearthstone/cards/enus/medium/" + card.getImageUrl() + ".png\" /></td>");
				if (i % 5 == 0 && i != 0)
					out.println("\t\t</tr>\n\t\t<tr>");
				i++;
			}
		%>
	</table>
	<nav>
		<ul class="pagination">
			<li><a href="#" aria-label="Previous"> <span aria-hidden="true">&laquo;</span>
			</a></li>
			<li><a href="cards?page=1">1</a></li>
			<li><a href="cards?page=2">2</a></li>
			<li><a href="cards?page=3">3</a></li>
			<li><a href="cards?page=4">4</a></li>
			<li><a href="cards?page=5">5</a></li>
			<li><a href="#" aria-label="Next"> <span aria-hidden="true">&raquo;</span>
			</a></li>
		</ul>
	</nav>
	<script src="js/gallery.js"></script>
</body>
</html>
