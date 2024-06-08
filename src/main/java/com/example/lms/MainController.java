package com.example.lms;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MainController implements Initializable {

    @FXML
    private Button HomeButton;
    @FXML
    private Button BookListButton;
    @FXML
    private Button MemberListButton;
    @FXML
    private Button AccountButton;
    @FXML
    private Button MenuItem;
    @FXML
    private StackPane mainPane;
    @FXML
    private AnchorPane opacityPane;
    @FXML
    private AnchorPane DrawerPane;
    @FXML
    private Button newBookButton1;
    @FXML
    private Button SearchButton1;
    @FXML
    private Button newBookButton;
    @FXML
    private Button SearchButton;
    @FXML
    private Button SettingButton;
    @FXML
    private Button InfoButton;
    @FXML
    private Button SettingButton1;
    @FXML
    private Button InfoButton1;
    @FXML
    private ImageView newBookImage;
    @FXML
    private ImageView SearchImage;
    @FXML
    private ImageView SettingImage;
    @FXML
    private ImageView InfoImage;
    @FXML
    private ImageView newBookImage_1;
    @FXML
    private ImageView SearchImage_1;
    @FXML
    private ImageView SettingImage_1;
    @FXML
    private ImageView InfoImage_1;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            loadMain();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        setupDrawerAnimations();
    }
    private void setupDrawerAnimations() {
        opacityPane.setVisible(false);

        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.5), opacityPane);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.play();

        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.5), DrawerPane);
        translateTransition.setByY(-165);
        translateTransition.play();

        AccountButton.setOnMouseClicked(event -> {
            opacityPane.setVisible(true);

            FadeTransition fadeTransition1 = new FadeTransition(Duration.seconds(0.5), opacityPane);
            fadeTransition1.setFromValue(0);
            fadeTransition1.setToValue(0.15);
            fadeTransition1.play();

            TranslateTransition translateTransition1 = new TranslateTransition(Duration.seconds(0.5), DrawerPane);
            translateTransition1.setByY(+165);
            translateTransition1.play();
        });

        opacityPane.setOnMouseClicked(event -> {
            FadeTransition fadeTransition1 = new FadeTransition(Duration.seconds(0.5), opacityPane);
            fadeTransition1.setFromValue(0.15);
            fadeTransition1.setToValue(0);
            fadeTransition1.play();

            fadeTransition1.setOnFinished(event1 -> opacityPane.setVisible(false));
            TranslateTransition translateTransition1 = new TranslateTransition(Duration.seconds(0.5), DrawerPane);
            translateTransition1.setByY(-165);
            translateTransition1.play();
        });

    }

    private void loadMain() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("mainGraphics.fxml"));
        mainPane.getChildren().clear();
        mainPane.getChildren().add(root);
    }


    @FXML
    private void LogOutAction(ActionEvent event) {
        Platform.runLater(() -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
                Parent root = loader.load();

                Stage stage = new Stage();
                stage.setTitle("Login");
                stage.setResizable(false);
                stage.setScene(new Scene(root));
                stage.show();

                ((Node) (event.getSource())).getScene().getWindow().hide();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    private void HomeAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(MainGraphicsController.class.getResource("mainGraphics.fxml"));
        mainPane.getChildren().clear();
        mainPane.getChildren().add(root);
    }

    @FXML
    private void BookListAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(BooksController.class.getResource("Books.fxml"));
        Parent root = loader.load();

        Stage stage = new Stage();
        stage.setTitle("Book Transactions");
        stage.setResizable(false);
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    private void MemberListAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(MemberController.class.getResource("member.fxml"));
        mainPane.getChildren().clear();
        mainPane.getChildren().add(root);
    }

    @FXML
    private void AccountAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(AccountController.class.getResource("account.fxml"));
        Parent root = loader.load();

        Stage stage = new Stage();
        stage.setTitle("Account Details");
        stage.setResizable(false);
        stage.setScene(new Scene(root));
        stage.show();
    }
}
