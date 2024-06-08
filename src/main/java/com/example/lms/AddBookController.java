package com.example.lms;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import com.example.lms.DataBase.DataHelper;
import com.example.lms.Datas.Book;
import com.example.lms.Session.SessionManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class AddBookController implements Initializable {

    @FXML
    private TextField titleField;
    @FXML
    private TextField authorField;
    @FXML
    private TextField publisherField;
    @FXML
    private TextField PageField;
    @FXML
    private ComboBox<String> CategoriesBox;
    @FXML
    private Button SaveButton;
    @FXML
    private Button CancelButton;

    private int userId;
    String[] bookCategories = {
            "Adventure",
            "Biography",
            "Business",
            "Children",
            "Classics",
            "Dystopian",
            "Fantasy",
            "Fiction",
            "Historical Fiction",
            "Horror",
            "Humor",
            "Mystery",
            "Poetry",
            "Psychological",
            "Romance",
            "Satire",
            "Science Fiction",
            "Self-help",
            "Thriller",
            "Young Adult"
    };


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        userId = fetchUserId();
        CategoriesBox.getItems().addAll(bookCategories);

    }

    private int fetchUserId() {
        return SessionManager.getInstance().getLoggedInUserId();
    }

    @FXML
    private void SaveAction(ActionEvent event) throws InterruptedException {
        String title = titleField.getText();
        String author = authorField.getText();
        String publisher = publisherField.getText();
        String page = PageField.getText();
        String categories = CategoriesBox.getSelectionModel().getSelectedItem();

        if (title.isEmpty() || author.isEmpty() || publisher.isEmpty() || page.isEmpty() || categories.isEmpty()) {
            showAlert("All fields must be filled correctly!");
        } else {
            Book book = new Book(title, author, publisher, page,categories ,userId);
            boolean success = DataHelper.insertBook(book);
            DataHelper.UpdateRB();
            if (success) {
                Thread.sleep(500);
                showAlert("Book added successfully!");
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.close();
            } else {
                showAlert("Failed to add book. Please try again.");
            }
        }
    }

    @FXML
    private void CancelAction(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setTitle("Information Message");
        alert.setContentText(message);

        ButtonType okbutton = new ButtonType("OK");
        alert.getButtonTypes().setAll(okbutton);

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("/css/dark_Theme.css").toExternalForm());

        alert.showAndWait();
    }
}

