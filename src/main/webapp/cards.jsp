<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<link href="css/bootstrap.min.css" ref="stylesheet" type="text/css" />
<link href="css/bootstrap-theme.min.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<script src="js/jquery-2.1.4.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script src="js/HearthHeadData.js"></script>
	<h2>卡牌展示</h2>
	<hr />
	
	<table id="cards-gallery">
		<%
			out.println("\t\t<tr>");
			// TODO insert cards here
			out.println("\t\t</tr>");
		%>
	</table>
	
	<nav>
		<ul class="pagination">
			<li><a href="#" aria-label="Previous"> <span
					aria-hidden="true">&laquo;</span>
			</a></li>
			<li><a href="/cards?page=1">1</a></li>
			<li><a href="/cards?page=2">2</a></li>
			<li><a href="/cards?page=3">3</a></li>
			<li><a href="/cards?page=4">4</a></li>
			<li><a href="/cards?page=5">5</a></li>
			<li><a href="#" aria-label="Next"> <span aria-hidden="true">&raquo;</span>
			</a></li>
		</ul>
	</nav>
</body>
</html>
