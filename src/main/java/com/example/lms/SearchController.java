package com.example.lms;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import com.example.lms.DataBase.DataBaseHandler;
import com.example.lms.Datas.BookSearch;
import com.example.lms.Session.SessionManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.scene.control.cell.PropertyValueFactory;


public class SearchController implements Initializable {

    ObservableList<BookSearch> list = FXCollections.observableArrayList();

    @FXML
    private TableView<BookSearch> Table;
    @FXML
    private TableColumn<BookSearch, Integer> IDColumn;
    @FXML
    private TableColumn<BookSearch, String> TitleColumn;
    @FXML
    private TableColumn<BookSearch, String> AuthorColumn;
    @FXML
    private TableColumn<BookSearch, String> PublisherColumn;
    @FXML
    private ComboBox<String> comboBox;
    @FXML
    private TextField textField;
    @FXML
    private Button searchButton;

    public void initialize(URL url, ResourceBundle rb) {
        initCol();
        comboBox.getItems().addAll("Title", "Author", "Publisher");
    }

    private void initCol(){
        IDColumn.setCellValueFactory(new PropertyValueFactory<>("ID"));
        TitleColumn.setCellValueFactory(new PropertyValueFactory<>("Title"));
        AuthorColumn.setCellValueFactory(new PropertyValueFactory<>("Author"));
        PublisherColumn.setCellValueFactory(new PropertyValueFactory<>("Publisher"));
    }

    @FXML
    private void searchAction(ActionEvent event) {
        String selectedItem = comboBox.getSelectionModel().getSelectedItem();
        int userId = SessionManager.getInstance().getLoggedInUserId();

        if(selectedItem.equals("Title")){
            findTitle(userId);
        }
        else if(selectedItem.equals("Author")){
            findAuthor(userId);
        }
        else if(selectedItem.equals("Publisher")){
            findPublisher(userId);
        }
    }

    void findTitle(int userId){
        list.clear();
        String title = textField.getText();
        String sql = "SELECT * FROM booktable WHERE book_title LIKE '%" + title + "%' AND user_id = ?";
        try{
            PreparedStatement statement = DataBaseHandler.getInstance().getConnection().prepareStatement(sql);
            statement.setInt(1, userId);
            ResultSet rs = statement.executeQuery();

            while(rs.next()){
                int id = rs.getInt("book_id");
                String bookTitle = rs.getString("book_title");
                String author = rs.getString("author");
                String publisher = rs.getString("publisher");
                list.add(new BookSearch(id, bookTitle, author, publisher, userId));
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        Table.setItems(list);
    }

    void findAuthor(int userId){
        list.clear();
        String auth = textField.getText();
        String sql = "SELECT * FROM booktable WHERE author LIKE '%" + auth + "%' AND user_id = ?";
        try{
            PreparedStatement statement = DataBaseHandler.getInstance().getConnection().prepareStatement(sql);
            statement.setInt(1, userId);
            ResultSet rs = statement.executeQuery();

            while(rs.next()){
                int id = rs.getInt("book_id");
                String bookTitle = rs.getString("book_title");
                String author = rs.getString("author");
                String publisher = rs.getString("publisher");
                list.add(new BookSearch(id, bookTitle, author, publisher, userId));
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        Table.setItems(list);
    }

    void findPublisher(int userId){
        list.clear();
        String publish = textField.getText();
        String sql = "SELECT * FROM booktable WHERE publisher LIKE '%" + publish + "%' AND user_id = ?";
        try{
            PreparedStatement statement = DataBaseHandler.getInstance().getConnection().prepareStatement(sql);
            statement.setInt(1, userId);
            ResultSet rs = statement.executeQuery();

            while(rs.next()){
                int id = rs.getInt("book_id");
                String bookTitle = rs.getString("book_title");
                String author = rs.getString("author");
                String publisher = rs.getString("publisher");
                list.add(new BookSearch(id, bookTitle, author, publisher, userId));
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        Table.setItems(list);
    }
}
