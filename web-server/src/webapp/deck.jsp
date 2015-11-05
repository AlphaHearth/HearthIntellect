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
	<h2>Deck Info</h2>
	<hr />
	<h3><s:property value="deck.name" /></h3>
	<ul>
		<s:iterator  value="cards" var="card" >
		<li><s:property value="#card.cost" />&nbsp;&nbsp;<s:property value="#card.name" />&nbsp;&nbsp;x<s:property value="#card.count" /></li>
		</s:iterator>
	</ul>
</body>
</html>
