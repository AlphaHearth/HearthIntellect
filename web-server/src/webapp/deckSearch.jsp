<%@ page import="java.util.List, com.hearthintellect.model.Card"
	contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
	<link href="css/bootstrap.min.css" rel="stylesheet" type="text/css" />
	<link href="css/bootstrap-theme.min.css" rel="stylesheet" type="text/css" />
	<link href="css/deck-builder.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<script src="js/jquery-2.1.4.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script src="js/HearthHeadData.js"></script>
	<h1>Deck Search</h1>
	<hr />
	<div id="content-wrapper">
	<div id="right-wrapper" style="display: none">
		<h2>Key Cards:</h2>
		<ul id="deck-member"></ul>
		<button id="confirm">Confirm</button><button id="clear">Clear</button>
	</div>
	<div id="left-wrapper">
	<s:if test='%{classs == 0}'>
		<h2>Choose your hero</h2>
		<ul>
			<li><a href="deckSearch?classs=1">Warrior</a></li>
			<li><a href="deckSearch?classs=2">Paladin</a></li>
			<li><a href="deckSearch?classs=3">Hunter</a></li>
			<li><a href="deckSearch?classs=7">Shaman</a></li>
			<li><a href="deckSearch?classs=4">Rogue</a></li>
			<li><a href="deckSearch?classs=11">Druid</a></li>
			<li><a href="deckSearch?classs=8">Mage</a></li>
			<li><a href="deckSearch?classs=9">Warlock</a></li>
			<li><a href="deckSearch?classs=5">Priest</a></li>
		</ul>
	</s:if>
	<s:else>
		<a href="deckSearch?classs=0">&lt;&lt;&nbsp;Back</a><br />
		<ul id="deckTab" class="nav nav-tabs" role="tablist">
      		<li role="presentation" class="effective">
      			<%
					out.print("\t\t\t\t<a href=\"#classs\" id=\"classs-tab\" role=\"tab\" data-toggle=\"tab\" aria-controls=\"classs\" aria-expanded=\"true\">");
					Integer classs = (Integer) request.getAttribute("classs");
					switch (classs) {
					case 1: out.print("Warrior"); break;
					case 2: out.print("Paladin"); break;
					case 3: out.print("Hunter"); break;
					case 4: out.print("Rogue"); break;
					case 5: out.print("Priest"); break;
					case 7: out.print("Shaman"); break;
					case 8: out.print("Mage"); break;
					case 9: out.print("Warlock"); break;
					case 11: out.print("Druid"); break;
					}
					out.println("</a>");
				%>
      		</li>
      		<li role="neutral"><a href="#neutral" role="tab" id="neutral-tab" data-toggle="tab" aria-controls="neutral">Neutral</a></li>
   		</ul>
		<div id="deckTabContent" class="tab-content">
			<div role="tabpanel" class="tab-pane fade in effective" id="classs" aria-labelledBy="classs-tab">
				<table style="margin: 0 auto;" class="cards-gallery">
					<tr>
					<%
						List<Card> classCards = (List<Card>) request.getAttribute("classCards");
						int i = 1;
						for (Card card : classCards) {
							out.println("\t\t\t\t<td class=\"gallery-card deck-choice\"><img cardId=\"" + card.getCardId() + "\" src=\"http://wow.zamimg.com/images/hearthstone/cards/enus/medium/"
									+ card.getImageUrl() + ".png\" /></td>");
							if (i % 5 == 0 && i != 0)
								out.println("\t\t\t</tr>\n\t\t<tr>");
							i++;
						}
					%>
					</tr>
				</table>
			</div>
			<div role="tabpanel" class="tab-pane fade" id="neutral" aria-labelledBy="neutral-tab">
				<table style="margin: 0 auto;" class="cards-gallery">
					<tr>
					<%
						List<Card> neutralCards = (List<Card>) request.getAttribute("neutralCards");
						i = 1;
						for (Card card : neutralCards) {
							out.println("\t\t\t\t<td class=\"gallery-card deck-choice\"><img cardId=\"" + card.getCardId() + "\" src=\"http://wow.zamimg.com/images/hearthstone/cards/enus/medium/"
									+ card.getImageUrl() + ".png\" /></td>");
							if (i % 5 == 0 && i != 0)
								out.println("\t\t\t</tr>\n\t\t<tr>");
							i++;
						}
					%>
					</tr>
				</table>
			</div>
		</div>
	</s:else>
	</div>
	</div>
	<script src="js/gallery.js"></script>
	<script src="js/deck-builder.js"></script>
	<script>
		$('#confirm').click(function (e) {
			var targetAction = "deckSearchResult?cards=";
			cardsList.sort(function(a, b) {
				return a.id - b.id;
			});
			$(cardsList).each(function(index) {
				if (index != 0)
					targetAction = targetAction + ";"
				targetAction = targetAction + this.id + "," + this.count;
			});
			location.href = targetAction;
		});
	</script>
</body>
</html>
