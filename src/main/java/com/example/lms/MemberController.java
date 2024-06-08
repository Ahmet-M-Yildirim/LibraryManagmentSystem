package com.example.lms;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.example.lms.DataBase.DataBaseHandler;
import com.example.lms.Datas.Member_Table;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;


public class MemberController implements Initializable {

    ObservableList<Member_Table> list = FXCollections.observableArrayList();

    @FXML
    private TableView<Member_Table> memberTable;
    @FXML
    private TableColumn<Member_Table, Integer> idColumn;
    @FXML
    private TableColumn<Member_Table, String> usernameColumn;
    @FXML
    private TableColumn<Member_Table, String> mailColumn;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initCol();
        loadData();
        setColumnResizePolicy();
    }

    private void initCol() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        mailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
    }

    private void loadData() {
        list.clear();
        String sql = "SELECT * FROM user";
        try (PreparedStatement statement = DataBaseHandler.getInstance().getConnection().prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("user_id");
                String username = rs.getString("Username");
                String email = rs.getString("email");
                list.add(new Member_Table(id, username, email));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        Platform.runLater(() -> {
            memberTable.setItems(list);
        });
    }

    private void setColumnResizePolicy(){
        memberTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        idColumn.prefWidthProperty().bind(memberTable.widthProperty().multiply(0.2));
        usernameColumn.prefWidthProperty().bind(memberTable.widthProperty().multiply(0.3));
        mailColumn.prefWidthProperty().bind(memberTable.widthProperty().multiply(0.5));
    }



}

