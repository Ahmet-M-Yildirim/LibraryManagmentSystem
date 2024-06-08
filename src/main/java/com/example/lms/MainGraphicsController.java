package com.example.lms;

import com.example.lms.DataBase.DataBaseHandler;
import com.example.lms.Datas.BookBC;
import com.example.lms.Datas.MemberRB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import com.example.lms.DataBase.DataHelper;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class MainGraphicsController implements Initializable {

    @FXML
    private TableView<BookBC> Table_2;

    @FXML
    private TableView<MemberRB> Table_1;

    @FXML
    private TableColumn<MemberRB, Integer> noColumn;
    @FXML
    private TableColumn<MemberRB, String> usernameColumn;
    @FXML
    private TableColumn<MemberRB, String> readColumn;

    @FXML
    private TableColumn<BookBC, String> bookColumn;

    @FXML
    private Button BooksButton;

    @FXML
    private TableColumn<BookBC, String> authorColumn;

    @FXML
    private Button UserButton;
    @FXML
    private TableColumn<BookBC, Integer> bookCountColumn;

    @FXML
    private TableColumn<BookBC, Integer> idColumn;
    @FXML
    private PieChart pieChart;

    private ObservableList<MemberRB> userData = FXCollections.observableArrayList();
    private ObservableList<BookBC> bookData = FXCollections.observableArrayList();

    private String userCount, bookCount;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userCount = String.valueOf(DataHelper.getUserCount());
        UserButton.setText(userCount);

        bookCount = String.valueOf(DataHelper.getUserBooks());
        BooksButton.setText(bookCount);

        PieChart.Data slice1 = new PieChart.Data("Users", DataHelper.getUserCount());
        PieChart.Data slice2 = new PieChart.Data("Books", DataHelper.getUserBooks());

        pieChart.getData().addAll(slice1, slice2);

        initCol();
        setColumnResizePolicyTable_2();
        setColumnResizePolicyTable_1();
        loadMostReadUsers();
        loadMostReadBooks();
    }

    private void initCol() {
        noColumn.setCellValueFactory(new PropertyValueFactory<>("No"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("Username"));
        readColumn.setCellValueFactory(new PropertyValueFactory<>("read_books"));

        idColumn.setCellValueFactory(new PropertyValueFactory<>("No"));
        bookColumn.setCellValueFactory(new PropertyValueFactory<>("book_title"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        bookCountColumn.setCellValueFactory(new PropertyValueFactory<>("bookCount"));
    }

    private void loadMostReadUsers() {
        String sql = "SELECT Username, read_books FROM user ORDER BY read_books DESC LIMIT 10";
        int no = 1;
        DataHelper.UpdateRB();
        try (PreparedStatement statement = DataBaseHandler.getInstance().getConnection().prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {

            userData.clear();
            while (rs.next()) {
                String username = rs.getString("Username");
                int readBooks = rs.getInt("read_books");
                userData.add(new MemberRB(no, username, readBooks));
                no++;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        Table_1.setItems(userData);
    }

    private void loadMostReadBooks() {
        int no = 1;
        try (PreparedStatement statement = DataBaseHandler.getInstance().getConnection().prepareStatement(
                "SELECT DISTINCT book_title, author, book_count FROM booktable ORDER BY book_count DESC LIMIT 20");
             ResultSet rs = statement.executeQuery()) {

            bookData.clear();
            while (rs.next()) {
                String booktitle = rs.getString("book_title");
                String author = rs.getString("author");
                int bookCount = rs.getInt("book_count");
                bookData.add(new BookBC(no, booktitle, author, bookCount));

                DataHelper.UpdateBC(booktitle);

                no++;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        Table_2.setItems(bookData);
    }

    private void setColumnResizePolicyTable_2() {
        Table_2.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        idColumn.prefWidthProperty().bind(Table_2.widthProperty().multiply(0.2));
        bookColumn.prefWidthProperty().bind(Table_2.widthProperty().multiply(0.3));
        authorColumn.prefWidthProperty().bind(Table_2.widthProperty().multiply(0.3));
        bookCountColumn.prefWidthProperty().bind(Table_2.widthProperty().multiply(0.2));
    }

    private void setColumnResizePolicyTable_1() {
        Table_1.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        noColumn.prefWidthProperty().bind(Table_1.widthProperty().multiply(0.2));
        usernameColumn.prefWidthProperty().bind(Table_1.widthProperty().multiply(0.5));
        readColumn.prefWidthProperty().bind(Table_1.widthProperty().multiply(0.3));

    }
}