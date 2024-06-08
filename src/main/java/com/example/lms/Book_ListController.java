package com.example.lms;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import com.example.lms.DataBase.DataBaseHandler;
import com.example.lms.Datas.BookList;
import com.example.lms.Session.SessionManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class Book_ListController implements Initializable {

    ObservableList<BookList> list = FXCollections.observableArrayList();

    @FXML
    private MenuItem pdfItem;
    @FXML
    private TableView<BookList> Table;
    @FXML
    private TableColumn<BookList, Integer> IDColumn;
    @FXML
    private TableColumn<BookList, String> TitleColumn;
    @FXML
    private TableColumn<BookList, String> AuthorColumn;
    @FXML
    private TableColumn<BookList, String> PublisherColumn;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initCol();
        loadData();
        setColumnResizePolicy();
    }

    private void initCol(){
        IDColumn.setCellValueFactory(new PropertyValueFactory<>("book_id"));
        TitleColumn.setCellValueFactory(new PropertyValueFactory<>("book_title"));
        AuthorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        PublisherColumn.setCellValueFactory(new PropertyValueFactory<>("publisher"));
    }

    private void loadData(){
        list.clear();
        String sql = "SELECT * FROM booktable WHERE user_id = ?";
        try{
            PreparedStatement statement = DataBaseHandler.getInstance().getConnection().prepareStatement(sql);
            statement.setInt(1, SessionManager.getInstance().getLoggedInUserId());
            ResultSet rs = statement.executeQuery();

            while(rs.next()){
                int id = rs.getInt("book_id");
                String title = rs.getString("book_title");
                String author = rs.getString("author");
                String publisher = rs.getString("publisher");
                int user_id = rs.getInt("user_id");
                list.add(new BookList(id, title, author, publisher, user_id));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        Table.setItems(list);
    }

    private void setColumnResizePolicy(){
        Table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        IDColumn.prefWidthProperty().bind(Table.widthProperty().multiply(0.2));
        TitleColumn.prefWidthProperty().bind(Table.widthProperty().multiply(0.3));
        AuthorColumn.prefWidthProperty().bind(Table.widthProperty().multiply(0.3));
        PublisherColumn.prefWidthProperty().bind(Table.widthProperty().multiply(0.2));

    }
}