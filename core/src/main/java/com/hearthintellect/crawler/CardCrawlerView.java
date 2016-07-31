package com.hearthintellect.crawler;

import com.hearthintellect.model.Card;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * JavaFX View for results of {@link CardCrawler}. Only launched from {@code CardCrawler}.
 */
public class CardCrawlerView extends Application {
    static List<Card> cards;
    static int currentIndex;
    static int version;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("CardCrawlerView.fxml"));

        Scene scene = new Scene(root);

        currentIndex = 0;

        primaryStage.setTitle("Card Crawler Results (Version " + version + ")");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    static void launch(List<Card> cards, int version) {
        CardCrawlerView.cards = cards;
        CardCrawlerView.version = version;
        launch();
    }

}
