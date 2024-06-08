package com.example.lms;

import com.example.lms.DataBase.DataBaseHandler;
import com.example.lms.DataBase.DataHelper;
import com.example.lms.Datas.BookUpdate;
import com.example.lms.Session.SessionManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class UpdatedBook implements Initializable {

    ObservableList<BookUpdate> list = FXCollections.observableArrayList();

    @FXML
    private TableColumn<BookUpdate, String> AuthorColumn;

    @FXML
    private TextField pageField;

    @FXML
    private TableColumn<BookUpdate, String> CategoriesColumn;

    @FXML
    private TextField titleField;

    @FXML
    private TableColumn<BookUpdate, String> TitleColumn;

    @FXML
    private TableColumn<BookUpdate, Integer> IDColumn;
    @FXML
    private Button UpdateBookButton;

    @FXML
    private TableView<BookUpdate> table_1;

    @FXML
    private TableColumn<BookUpdate, String> PublisherColumn;

    @FXML
    private TextField authorField;

    @FXML
    private TableColumn<BookUpdate, String> PageColumn;

    @FXML
    private ComboBox<String> categoriesBox;

    @FXML
    private TextField publisherField;

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
    public void initialize(URL location, ResourceBundle resources) {
        initCol();
        loadData();
        setColumnResizePolicy();
        categoriesBox.getItems().addAll(bookCategories);
        table_1.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                String title = newValue.getBookTitle();
                String author = newValue.getAuthor();
                String publisher = newValue.getPublisher();
                String page = newValue.getPage_number();
                String categories = newValue.getCategories();

                titleField.setText(title);
                authorField.setText(author);
                publisherField.setText(publisher);
                pageField.setText(page);
                categoriesBox.setValue(categories);
            }
        });
    }

    private void initCol() {
        IDColumn.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        TitleColumn.setCellValueFactory(new PropertyValueFactory<>("bookTitle"));
        AuthorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        PublisherColumn.setCellValueFactory(new PropertyValueFactory<>("publisher"));
        PageColumn.setCellValueFactory(new PropertyValueFactory<>("page_number"));
        CategoriesColumn.setCellValueFactory(new PropertyValueFactory<>("categories"));
    }

    @FXML
    void UpdateAction(ActionEvent event) {
        BookUpdate selectedData = table_1.getSelectionModel().getSelectedItem();
        if (selectedData != null) {
            String title = titleField.getText();
            String author = authorField.getText();
            String publisher = publisherField.getText();
            String page = pageField.getText();
            String categories = categoriesBox.getSelectionModel().getSelectedItem();
            int bookId = selectedData.getBookId();

            BookUpdate book = new BookUpdate(bookId,title, author, publisher, page,categories , selectedData.getUserId());

            boolean success = DataHelper.UpdateBooks(book);
            if (success) {
                showAlert("Book Updated");
                loadData();
            } else {
                showAlert("Book Not Updated");
            }
        }
    }


    private void loadData() {
        list.clear();
        String sql = "SELECT * FROM booktable WHERE user_id = ?";
        try {
            PreparedStatement statement = DataBaseHandler.getInstance().getConnection().prepareStatement(sql);
            statement.setInt(1, SessionManager.getInstance().getLoggedInUserId());
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                int book_id = rs.getInt("book_id");
                String title = rs.getString("book_title");
                String author = rs.getString("author");
                String publisher = rs.getString("publisher");
                String page = rs.getString("page_number");
                String categories = rs.getString("categories");
                int user_id = rs.getInt("user_id");

                list.add(new BookUpdate(book_id, title, author, publisher, page, categories, user_id));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        table_1.setItems(list);
    }


    private void setColumnResizePolicy(){
        table_1.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        IDColumn.prefWidthProperty().bind(table_1.widthProperty().multiply(0.1));
        TitleColumn.prefWidthProperty().bind(table_1.widthProperty().multiply(0.2));
        AuthorColumn.prefWidthProperty().bind(table_1.widthProperty().multiply(0.2));
        PublisherColumn.prefWidthProperty().bind(table_1.widthProperty().multiply(0.2));
        PageColumn.prefWidthProperty().bind(table_1.widthProperty().multiply(0.1));
        CategoriesColumn.prefWidthProperty().bind(table_1.widthProperty().multiply(0.2));
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
