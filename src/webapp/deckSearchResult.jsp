<%@ page import="com.hearthintellect.model.Card" contentType="text/html; charset=UTF-8"%>
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
	<h2>Search Result</h2>
	<hr />
	<ul id="searchResult">
		<s:iterator value="searchResult">
		<li><a href="deck?id=${deckId}">${name}</a></li>
		</s:iterator>
		<s:property value="searchResult" />
	</ul>
</body>
</html>