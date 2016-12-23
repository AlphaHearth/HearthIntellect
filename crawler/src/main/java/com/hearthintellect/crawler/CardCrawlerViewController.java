package com.hearthintellect.crawler;

import com.hearthintellect.model.Card;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.util.Locale;

public class CardCrawlerViewController {
    private static final String IMAGE_URL = "http://wow.zamimg.com/images/hearthstone/cards/%s%s/original/%s.png?%d";
    private Locale locale = new Locale("zh", "CN");

    @FXML private Text cardTitle;
    @FXML private Text cardType;
    @FXML private Text cardCost;
    @FXML private Text cardAttack;
    @FXML private Text cardHealth;
    @FXML private Text cardEffect;
    @FXML private ImageView cardImage;
    @FXML private Button nextBtn;
    @FXML private Button previousBtn;

    @FXML protected void previousCard() {
        CardCrawlerView.currentIndex =
            CardCrawlerView.currentIndex == 0 ? 0 : CardCrawlerView.currentIndex - 1;
        load();
    }

    @FXML protected void nextCard() {
        CardCrawlerView.currentIndex =
            CardCrawlerView.currentIndex == CardCrawlerView.cards.size() - 1 ?
                CardCrawlerView.currentIndex : CardCrawlerView.currentIndex + 1;
        load();
    }

    private void load() {
        previousBtn.setDisable(false);
        nextBtn.setDisable(false);
        if (CardCrawlerView.currentIndex == 0)
            previousBtn.setDisable(true);
        else if (CardCrawlerView.currentIndex == CardCrawlerView.cards.size() - 1)
            nextBtn.setDisable(true);

        Card card = CardCrawlerView.cards.get(CardCrawlerView.currentIndex);

        cardTitle.setText(card.getName().get(locale));
        cardEffect.setText(card.getEffect().get(locale));
        cardCost.setText(String.valueOf(card.getCost()));
        cardAttack.setText(String.valueOf(card.getAttack()));
        cardHealth.setText(String.valueOf(card.getHealth()));

        String type;
        switch (card.getType()) {
            case HERO: type = "Hero"; break;
            case MINION: type = "Minion"; break;
            case SPELL: type = "Spell"; break;
            case WEAPON: type = "Weapon"; break;
            case POWER: type = "Hero Power"; break;
            default: type = "";
        }
        cardType.setText(type);

        String url = String.format(IMAGE_URL, locale.getLanguage(), locale.getCountry().toLowerCase(),
            card.getImageUrl(), CardCrawlerView.version);
        Image image = new Image(url);
        cardImage.setImage(image);
    }

}
