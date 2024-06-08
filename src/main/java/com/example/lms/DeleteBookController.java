package com.example.lms;

import com.example.lms.DataBase.DataBaseHandler;
import com.example.lms.DataBase.DataHelper;
import com.example.lms.Datas.BookDelete;
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

public class DeleteBookController implements Initializable {

    ObservableList<BookDelete> list = FXCollections.observableArrayList();

    @FXML
    private TableColumn<BookDelete, Integer> IDColumn;

    @FXML
    private TableColumn<BookDelete, String> AuthorColumn;

    @FXML
    private TableColumn<BookDelete, String> PublisherColumn;

    @FXML
    private TableColumn<BookDelete, String> CategoriesColumn;

    @FXML
    private TableColumn<BookDelete, String> PageColumn;

    @FXML
    private Button DeleteButton;

    @FXML
    private TableColumn<BookDelete, String> TitleColumn;
    @FXML
    private TableView<BookDelete> table_1;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initCol();
        loadData();
        setColumnResizePolicy();
    }
    @FXML
    void DeleteAction(ActionEvent event) {
        BookDelete selectedData = table_1.getSelectionModel().getSelectedItem();
        try{
            boolean success = DataHelper.DeleteBook(selectedData.getBookId());
            if(success){
                showAlert("Book Deleted");
                loadData();
            }
            else{
                showAlert("Book Not Deleted");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void initCol() {
        IDColumn.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        TitleColumn.setCellValueFactory(new PropertyValueFactory<>("bookTitle"));
        AuthorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        PublisherColumn.setCellValueFactory(new PropertyValueFactory<>("publisher"));
        PageColumn.setCellValueFactory(new PropertyValueFactory<>("page_number"));
        CategoriesColumn.setCellValueFactory(new PropertyValueFactory<>("categories"));
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

                list.add(new BookDelete(book_id, title, author, publisher, page, categories, user_id));
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
