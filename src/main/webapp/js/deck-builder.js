$('#deckTab a').click(function (e) {
	e.preventDefault()
	$(this).tab('show')
});

var cardsSum = 0;
var cardsList = [];
function addCard(cardsList, cardTd) {
	var cardId = parseInt($(cardTd).attr("cardid"));
	var cardInJs = g_hearthstone_cards[cardId];
	var card = new Object();
	card.id = cardId;
	card.cost = cardInJs.cost;
	card.quality = cardInJs.quality;
	card.name = cardInJs.name;
	card.count = 1;
	var index = $.grep(cardsList, function(cur,i){
        return cur["id"] == cardId;
    });
	if (cardsSum == 30) {
		alert("A deck can only has 30 cards");
	} else {
		if (index.length == 0) {
			cardsList.push(card);
			cardsSum++;
			cardsList.sort(function(a, b) {
				if (a.cost != b.cost)
					return a.cost - b.cost;
				else
					return a.name == b.name ? 0 : a.name > b.name;
			});
		} else {
			index = $.inArray(index[0], cardsList);
			if (cardsList[index].quality < 5 && cardsList[index].count == 2) {
				alert("This card cannot have more than 2 in one deck");
			} else if (cardsList[index].quality == 5 && cardsList[index].count == 1) {
				alert("This card cannot have more than 1 in one deck");
			} else {
				cardsList[index].count++;
				cardsSum++;
			}
		}
	}
}
function renderList() {
	var targetList = $("#deck-member");
	targetList.find("li").remove();
	if (cardsList.length > 0) {
		$("#right-wrapper").show();
		var i = 0;
		$(cardsList).each(function(index) {
			targetList.append("<li class=\"listItem\" index=\"" + i + "\">" + this.cost + "&nbsp;&nbsp;" + this.name + "&nbsp;&nbsp;x" + this.count + "</li>");
			i++;
		});
		$(".listItem").click(function(e) {
			var index = parseInt($(e.target).attr("index"));
			if (cardsList[index].count == 2) {
				cardsList[index].count--;
			} else {
				cardsList.splice(index, 1);
			}
			cardsSum--;
			renderList();
		});
	} else {
		$("#right-wrapper").hide();
	}
}
$('.deck-choice').click(function (e) {
	addCard(cardsList, e.target);
	renderList();
});

$('#clear').click(function (e) {
	cardsList = [];
	cardsSum = 0;
	$("#right-wrapper").hide();
});
